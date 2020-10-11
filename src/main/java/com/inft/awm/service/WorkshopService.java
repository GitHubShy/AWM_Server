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
                    TimeUtils.getDateDiffHours(start_time, due_time, "yyyy-MM-dd"),jobSaved.getEmployee_id(),0);
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


}
