package com.inft.awm.service;

import com.inft.awm.domain.*;
import com.inft.awm.domain.response.ResponseAircraft;
import com.inft.awm.repository.*;
import com.inft.awm.response.ResponseEmployeeType;
import com.inft.awm.utils.StringUtils;
import com.inft.awm.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class WorkshopService {

    @Autowired
    AircraftRepository aircraftRepository;

    @Autowired
    ComponentRepository componentRepository;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    TemplateRepository templateRepository;

    @Autowired
    TemplateTaskRepository templateTaskRepository;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    SubTaskRepository subTaskRepository;

    @Autowired
    SubTaskTypeRepository subTaskTypeRepository;


    public Aircraft registerAircraft(Aircraft aircraft) {
        String nextTime = TimeUtils.addDateHours(aircraft.getLast_modify_time(), aircraft.getMaintenance_cycle(), "yyyy-MM-dd");
        aircraft.setNext_modify_time(nextTime);
        Aircraft saved = aircraftRepository.save(aircraft);
        return saved;
    }

    public void registerComponents(List<Component> components) {
        Aircraft aircraft = null;
        for (Component c : components) {
            if (StringUtils.isEmpty(c.getLast_modify_time())) {
                if (aircraft == null) {
                    aircraft = aircraftRepository.findById(c.getId()).get();
                }
                c.setLast_modify_time(aircraft.getLast_modify_time());
            }
            c.setNext_modify_time(TimeUtils.addDateHours(c.getLast_modify_time(), c.getMaintenance_cycle(), "yyyy-MM-dd"));
        }
        componentRepository.saveAll(components);
    }


    public List<ResponseAircraft> getAircraft(Integer aircraftId) {

        Iterable<Aircraft> allAircraft;
        if (aircraftId != null && aircraftId != 0) {
            allAircraft = aircraftRepository.findAircraft(aircraftId);
        } else {
            allAircraft = aircraftRepository.findAll();
        }

        ArrayList<ResponseAircraft> responseAircraft = getResponseAircrafts(allAircraft);
        return responseAircraft;
    }

    public List<ResponseAircraft> getCustomerAircraft(Integer customerId) {

        Iterable<Aircraft> allAircraft;
        if (customerId != null && customerId != 0) {
            allAircraft = aircraftRepository.findAircraftByCustomer(customerId);
        } else {
            throw new RuntimeException("The customer id is invalid: " + customerId);
        }

        ArrayList<ResponseAircraft> responseAircraft = getResponseAircrafts(allAircraft);
        return responseAircraft;
    }

    public List<Job> getAllJobs(int employeeId) {
        ArrayList<Job> jobs = new ArrayList<>();
        Iterable<Job> jobIterator = null;
        if (employeeId != 0) {
            jobIterator = jobRepository.findJobsByEmployee(employeeId);
        } else {
            jobIterator = jobRepository.findAll();
        }
        final List<Template> allTemplates = findAvailableTemplates(0);
        final List<ResponseEmployeeType> employees = employeeService.getEmployeeByType(1);
        Iterator<Job> iterator = jobIterator.iterator();
        while (iterator.hasNext()) {
            Job job = iterator.next();

            //Query employee name from employee table
            for (ResponseEmployeeType employee : employees) {
                if (job.getEmployee_id() == employee.getId()) {
                    job.setEmployee_name(employee.getName());
                }
            }

            Integer status = job.getStatus();
            if (status == 1) {//已经开始的工作
                if (TimeUtils.isOverDue(job.getDue_time(), "yyyy-MM-dd")) {
                    job.setStatus(3);//设置成为已over due
                }
            }

            for (Template template : allTemplates) {
                if (template.getId() == job.getTemplate_id()) {
                    job.setDescription(template.getTitle());
                }
            }

            jobs.add(job);
        }
        return jobs;
    }

    public void createJob(Job job) {
        //all created job should be set status=0
        job.setStatus(0);

        //Set planned cost hours
        String start_time = job.getStart_time();
        String due_time = job.getDue_time();
        job.setPlanned_cost_time(TimeUtils.getDateDiffHours(start_time, due_time, "yyyy-MM-dd"));

        Job jobSaved = jobRepository.save(job);

        Integer template_id = job.getTemplate_id();
        //Query Corresponding sub-task-types for a template
        Iterable<TemplateTask> subTaskTypes = templateTaskRepository.findSubTaskType(template_id);
        //Create sub tasks
        Iterator<TemplateTask> iterator = subTaskTypes.iterator();
        //List used to save all subTask
        ArrayList<SubTask> subTaskList = new ArrayList<>();
        while(iterator.hasNext()) {
            TemplateTask subTaskId = iterator.next();
            SubTask subTask = new SubTask(jobSaved.getId(),subTaskId.getSub_task_type_id(),jobSaved.getStart_time(),jobSaved.getDue_time(),
                    TimeUtils.getDateDiffHours(start_time, due_time, "yyyy-MM-dd"),jobSaved.getEmployee_id(),0,jobSaved.getAircraft_id());
            subTaskList.add(subTask);
        }
        //Save all sub tasks
        subTaskRepository.saveAll(subTaskList);
    }

    private ArrayList<ResponseAircraft> getResponseAircrafts(Iterable<Aircraft> allAircraft) {
        Iterable<Component> allComponents = componentRepository.findAll();

        ArrayList<Component> components = new ArrayList<>();

        ArrayList<ResponseAircraft> responseAircraft = new ArrayList<>();

        Iterator<Component> componentIterator = allComponents.iterator();
        while (componentIterator.hasNext()) {
            components.add(componentIterator.next());
        }

        Iterator<Aircraft> aircraftIterator = allAircraft.iterator();
        while (aircraftIterator.hasNext()) {
            Aircraft aircraft = aircraftIterator.next();
            ResponseAircraft ra = new ResponseAircraft(aircraft);
            Integer aircraftId = aircraft.getId();
            for (Component cp : components) {
                if (cp.getAircraft_id() == aircraftId) {
                    ra.getComponents().add(cp);
                }
            }

            responseAircraft.add(ra);
        }
        return responseAircraft;
    }

    /**
     * @param employeeId  0:return system default templates   otherwise return templates belongs to the employee
     * @return
     */
    public List<Template> findAvailableTemplates(Integer employeeId) {
        Iterable<Template> all;
        if (employeeId == null || employeeId == 0) {
            all = templateRepository.findAll();
        } else {
            all = templateRepository.findAvailableTemplates(employeeId);
        }
        Iterator<Template> iterator = all.iterator();
        List<Template> templates = new ArrayList<>();
        while (iterator.hasNext()) {
            Template next = iterator.next();
            templates.add(next);
        }
        return templates;
    }

    public List<SubTask> getSubTasksForJob(Integer jobId) {
        final Iterable<SubTask> subTasksIterable = subTaskRepository.findSubTasksByJob(jobId);
        ArrayList<SubTask> subTasks = new ArrayList<>();

        //Used for match employee name with employee id
        List<ResponseEmployeeType> allEmployees = employeeService.getEmployeeByType(-1);

        Iterator<SubTask> iterator = subTasksIterable.iterator();
        while (iterator.hasNext()) {
            SubTask subTask = iterator.next();

            //find the sub task type
            SubTaskType subTaskType = subTaskTypeRepository.findSubTaskType(subTask.getSub_task_type_id());
            subTask.setDescription(subTaskType.getTitle());

            //find employee name
            for (ResponseEmployeeType employee:allEmployees) {
                if (subTask.getEmployee_id() == employee.getId()) {
                    subTask.setEmployee_name(employee.getName());
                    break;
                }
            }
            subTasks.add(subTask);
        }
        return subTasks;
    }

    public void updateSubTask(SubTask subTask) {
        final Integer subTaskId = subTask.getId();
        final SubTask originalSubTask = subTaskRepository.findById(subTaskId).get();
        if (originalSubTask == null) {
            throw new RuntimeException("This sub do not exist in the database");
        } else {
            //update employee
            if (subTask.getEmployee_id() != null && subTask.getEmployee_id() != 0) {
                originalSubTask.setEmployee_id(subTask.getEmployee_id());
            }
            //update employee
            if (subTask.getEmployee_id() != null && subTask.getEmployee_id() != 0) {
                originalSubTask.setEmployee_id(subTask.getEmployee_id());
            }
            //update start time
            if (!StringUtils.isEmpty(subTask.getStart_time())) {
                originalSubTask.setStart_time(subTask.getStart_time());
            }

            //update due time
            if (!StringUtils.isEmpty(subTask.getDue_time())) {
                originalSubTask.setDue_time(subTask.getDue_time());
            }
            originalSubTask.setPlanned_cost_time(TimeUtils.getDateDiffHours(originalSubTask.getStart_time(), originalSubTask.getDue_time(), "yyyy-MM-dd"));

        }
        subTaskRepository.save(originalSubTask);
    }

    public void createSubTask(SubTask subTask) {
        subTask.setPlanned_cost_time(TimeUtils.getDateDiffHours(subTask.getStart_time(), subTask.getDue_time(), "yyyy-MM-dd"));
        subTaskRepository.save(subTask);
    }

    public List<SubTaskType> getAllSubTaskType() {
        Iterable<SubTaskType> all = subTaskTypeRepository.findAll();
        Iterator<SubTaskType> iterator = all.iterator();
        ArrayList<SubTaskType> result = new ArrayList<>();
        while (iterator.hasNext()) {
            SubTaskType subTaskType = iterator.next();
            result.add(subTaskType);
        }
        return result;
    }


}
