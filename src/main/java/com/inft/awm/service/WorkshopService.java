package com.inft.awm.service;

import com.inft.awm.domain.*;
import com.inft.awm.domain.request.RequestCreateTemplate;
import com.inft.awm.domain.response.ResponseAircraft;
import com.inft.awm.repository.*;
import com.inft.awm.response.ResponseEmployeeType;
import com.inft.awm.utils.StringUtils;
import com.inft.awm.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * Handle customer workshop logic
 *
 * @author Yao Shi
 * @version 1.0
 * @date 30/10/2020 11:47 pm
 */
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

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ReceiptRepository receiptRepository;


    /** Register a aircraft
     * @param aircraft The aircraft
     * @return
     */
    public Aircraft registerAircraft(Aircraft aircraft) {
        String nextTime = TimeUtils.addDateHours(aircraft.getLast_modify_time(), aircraft.getMaintenance_cycle(), "yyyy-MM-dd");
        aircraft.setNext_modify_time(nextTime);
        Aircraft saved = aircraftRepository.save(aircraft);
        return saved;
    }

    /** Register a component for a aircraft
     * @param components The component
     */
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


    /** Get a aircraft by aircraft id
     * @param aircraftId The id for a aircraft
     * @return The aircraft
     */
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

    /** Get all aircraft that belong to a customer
     * @param customerId The customer id
     * @return The list of the aircraft
     */
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

    /** Get all jobs, or jobs for a employee
     * @param employeeId  if employeeid = 0 , return all
     * @param jobStatus job status
     * @return job list
     */
    public List<Job> getAllJobs(int employeeId,Integer jobStatus) {
        ArrayList<Job> jobs = new ArrayList<>();
        Iterable<Job> jobIterator = null;
        if (employeeId != 0) {
            jobIterator = jobRepository.findJobsByEmployee(employeeId);
        } else {
            jobIterator = jobRepository.findAllDesc(jobStatus == -1 ? null:jobStatus);
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
                    job.setTemplate_title(template.getTitle());
                }
            }

            jobs.add(job);
        }
        return jobs;
    }

    /** Get a job by id
     * @param jobId
     * @return job
     */
    public Job getJob(Integer jobId) {
        Job job = jobRepository.findById(jobId).get();
        return job;
    }

    /**Create a job
     * @param job
     */
    public void createJob(Job job) {
        //all created job should be set status=0
        job.setStatus(0);

        //Set planned cost hours
        String start_time = job.getStart_time();
        String due_time = job.getDue_time();
        job.setPlanned_cost_time(TimeUtils.getDateDiffHours(start_time, due_time, "yyyy-MM-dd"));

        Job jobSaved = jobRepository.save(job);

        //change the related aircraft status to maintaining
        Aircraft aircraft = aircraftRepository.findById(jobSaved.getAircraft_id()).get();
        aircraft.setStatus(2);
        aircraftRepository.save(aircraft);

        Integer template_id = job.getTemplate_id();
        //Query Corresponding sub-task-types for a template
        Iterable<TemplateTask> subTaskTypes = templateTaskRepository.findSubTaskType(template_id);

        Iterator<TemplateTask> iterator = subTaskTypes.iterator();
        //List used to save all subTask
        ArrayList<SubTask> subTaskList = new ArrayList<>();

        //Create sub tasks
        while (iterator.hasNext()) {
            TemplateTask subTaskId = iterator.next();
            SubTask subTask = new SubTask(jobSaved.getId(), subTaskId.getSub_task_type_id(), jobSaved.getStart_time(), jobSaved.getDue_time(),
                    TimeUtils.getDateDiffHours(start_time, due_time, "yyyy-MM-dd"), jobSaved.getEmployee_id(), 0, jobSaved.getAircraft_id(),"");

            SubTaskType subTaskType = subTaskTypeRepository.findSubTaskType(subTask.getSub_task_type_id());

            //Set SubTask description
            subTask.setDescription(subTaskType.getTitle());

            //Set SubTask materials
            subTask.setMaterials(subTaskType.getMaterials());

            subTaskList.add(subTask);
        }

        //Save all sub tasks
        subTaskRepository.saveAll(subTaskList);

        //Create a default comment
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
        Employee employee = employeeService.findEmployeeById(1);
        Comment comment = new Comment();
        comment.setContent_time(time);
        comment.setJob_id(jobSaved.getId());
        comment.setContent("Welcome to this job");
        comment.setEmployee_id(1);
        comment.setEmployee_name(employee.getFirst_name() + " " + employee.getSurname());
        commentRepository.save(comment);

    }

    /** Get all aircraft with components
     * @param allAircraft aircraft list
     * @return
     */
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

    /** Find all available templated for a employee
     * @param employeeId 0:return system default templates   otherwise return templates belongs to the employee
     * @return all available templates
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

    /** Get tasks for a job
     * @param jobId
     * @return all tasks
     */
    public List<SubTask> getSubTasksForJob(Integer jobId) {
        final Iterable<SubTask> subTasksIterable = subTaskRepository.findSubTasksByJob(jobId);
        ArrayList<SubTask> subTasks = new ArrayList<>();

        //Used for match employee name with employee id
        List<ResponseEmployeeType> allEmployees = employeeService.getEmployeeByType(-1);

        Iterator<SubTask> iterator = subTasksIterable.iterator();
        while (iterator.hasNext()) {
            SubTask subTask = iterator.next();

//            //find the sub task type
//            SubTaskType subTaskType = subTaskTypeRepository.findSubTaskType(subTask.getSub_task_type_id());
//            subTask.setDescription(subTaskType.getTitle());
//            subTask.setMaterials(subTaskType.getMaterials());

            //find employee name
            for (ResponseEmployeeType employee : allEmployees) {
                if (subTask.getEmployee_id() == employee.getId()) {
                    subTask.setEmployee_name(employee.getName());
                    break;
                }
            }
            subTasks.add(subTask);
        }
        return subTasks;
    }

    /**Get tasks for a employee
     * @param employeeId
     * @return all tasks
     */
    public List<SubTask> getTasksForEmployee(Integer employeeId) {
        final Iterable<SubTask> subTasksIterable = subTaskRepository.findSubTaskByEmployee(employeeId);
        ArrayList<SubTask> subTasks = new ArrayList<>();

        //Used for match employee name with employee id
        List<ResponseEmployeeType> allEmployees = employeeService.getEmployeeByType(-1);

        Iterator<SubTask> iterator = subTasksIterable.iterator();
        while (iterator.hasNext()) {
            SubTask subTask = iterator.next();

//            //find the sub task type
//            SubTaskType subTaskType = subTaskTypeRepository.findSubTaskType(subTask.getSub_task_type_id());
//            subTask.setDescription(subTaskType.getTitle());
//            subTask.setMaterials(subTaskType.getMaterials());

            //find employee name
            for (ResponseEmployeeType employee : allEmployees) {
                if (subTask.getEmployee_id() == employee.getId()) {
                    subTask.setEmployee_name(employee.getName());
                    break;
                }
            }
            subTasks.add(subTask);
        }
        return subTasks;
    }

    /** Update a task status
     * @param subTask
     */
    public void updateSubTask(SubTask subTask) {
        final Integer subTaskId = subTask.getId();
        final Integer job_id = subTask.getJob_id();
        Job parentJob = jobRepository.findById(job_id).get();
        //other sub tasks that belong to the same job
        Iterable<SubTask> subTasksByJob = subTaskRepository.findSubTasksByJob(job_id);
        final SubTask originalSubTask = subTaskRepository.findById(subTaskId).get();
        if (originalSubTask == null) {
            throw new RuntimeException("This sub task do not exist in the database");
        } else {
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

            //update planned hours
            originalSubTask.setPlanned_cost_time(TimeUtils.getDateDiffHours(originalSubTask.getStart_time(), originalSubTask.getDue_time(), "yyyy-MM-dd"));

            //update status
            if (subTask.getStatus() != null && subTask.getStatus() != 0) {

                //If user want to set the status from 'over due' to 'Started', reject.
                if (originalSubTask.getStatus() == 2 && subTask.getStatus() < 2) {
                    throw new RuntimeException("Can not set from 'over due' to 'Started'");
                }

                originalSubTask.setStatus(subTask.getStatus());

                //If this sub task is finished, check if all the sub tasks that belongs to the same parent job are all finished
                // If yes, set the parent job status to "need confirm"
                boolean isAllFinished = true;
                if (originalSubTask.getStatus() == 5) {// finished
                    Iterator<SubTask> iterator = subTasksByJob.iterator();
                    while (iterator.hasNext()) {
                        SubTask next = iterator.next();
                        if (next.getStatus() != 5) {
                            isAllFinished = false;
                            break;
                        }
                    }
                    if (isAllFinished) {
                        parentJob.setStatus(4);
                    }
                    originalSubTask.setPercentage(100);

                    //set finished date
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String endTime = df.format(new Date());
                    originalSubTask.setEnd_time(endTime);

                    //set actual finish hours
                    originalSubTask.setActual_cost_time(TimeUtils.getDateDiffHours(originalSubTask.getStart_time(), originalSubTask.getEnd_time(), "yyyy-MM-dd"));
                } else if (originalSubTask.getStatus() == 1) {//If this sub task is started, set parent job started
                    parentJob.setStatus(1);
                }

            }

            //Update percentage
            if (subTask.getPercentage() != null && subTask.getPercentage() != originalSubTask.getPercentage()) {
                if (originalSubTask.getStatus() == 0) {
                    throw new RuntimeException("Please start the task firstly");
                }
                if (subTask.getPercentage() > 100 || subTask.getPercentage() < 0) {
                    throw new RuntimeException("Please input the percentage between 0-100");
                }
                if (originalSubTask.getStatus() ==5) {
                    originalSubTask.setPercentage(100);
                } else {
                    originalSubTask.setPercentage(subTask.getPercentage());
                }
            }


            if (originalSubTask.getStatus() == 3) {
                originalSubTask.setPercentage(100);
            }

            //update materials
            if (!StringUtils.isEmpty(subTask.getMaterials())) {
                originalSubTask.setMaterials(subTask.getMaterials());
            }
            
            originalSubTask.setPlanned_cost_time(TimeUtils.getDateDiffHours(originalSubTask.getStart_time(), originalSubTask.getDue_time(), "yyyy-MM-dd"));

        }
        subTaskRepository.save(originalSubTask);
    }

    /** Update a job status
     * @param job
     */
    public void updateJob(Job job) {
        Job savedJob = jobRepository.findById(job.getId()).get();

        if (job.getStatus() != savedJob.getStatus()) {
            if (job.getStatus() == 5) {//want close the job
                if (savedJob.getStatus() != 4) {
                    throw new RuntimeException("All sub tasks have not been finished");
                }
                //update close time
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String endTime = df.format(new Date());
                savedJob.setEnd_time(endTime);
                //update actual hours
                savedJob.setActual_cost_time(TimeUtils.getDateDiffHours(savedJob.getStart_time(), savedJob.getEnd_time(), "yyyy-MM-dd"));

                //change aircraft status to flying:0
                Aircraft aircraft = aircraftRepository.findById(savedJob.getAircraft_id()).get();
                aircraft.setStatus(0);
                aircraft.setLast_modify_time(endTime);
                aircraft.setNext_modify_time(TimeUtils.addDateHours(aircraft.getLast_modify_time(),aircraft.getMaintenance_cycle(),"yyyy-MM-dd"));
                aircraftRepository.save(aircraft);
            }
            savedJob.setStatus(job.getStatus());
        }
        jobRepository.save(savedJob);

        if (savedJob.getStatus() == 5) {
            createReceipt(savedJob);
        }
    }

    /** Create a sub task
     * @param subTask
     */
    public void createSubTask(SubTask subTask) {
        //Set planned time
        subTask.setPlanned_cost_time(TimeUtils.getDateDiffHours(subTask.getStart_time(), subTask.getDue_time(), "yyyy-MM-dd"));

        SubTaskType subTaskType = subTaskTypeRepository.findSubTaskType(subTask.getSub_task_type_id());

        //Set status to 0(Created)
        subTask.setStatus(0);

        //Set SubTask description
        subTask.setDescription(subTaskType.getTitle());

        //Set SubTask materials
        subTask.setMaterials(subTaskType.getMaterials());

        subTaskRepository.save(subTask);
    }

    /**Delete a sub task
     * @param subTaskId
     */
    public void deleteSubTask(Integer subTaskId) {
        subTaskRepository.deleteById(subTaskId);
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

    /**Create a new template for a employee
     * @param newTemplate
     */
    public void createNewTemplate(RequestCreateTemplate newTemplate) {

        ArrayList<Integer> subTaskTypeIds = newTemplate.getSubTaskTypeIds();

        if (subTaskTypeIds == null || subTaskTypeIds.size() == 0) {
            throw new RuntimeException("No sub tasks provided");
        }

        Template template = new Template();
        template.setTitle(newTemplate.getTitle());
        template.setDescription(newTemplate.getDescription());
        template.setEmployee_id(newTemplate.getEmployee_id());
        final Template savedTemplate = templateRepository.save(template);

        ArrayList<TemplateTask> templateTasks = new ArrayList<>();

        for (Integer id : subTaskTypeIds) {
            TemplateTask tt = new TemplateTask(savedTemplate.getId(),id);
            templateTasks.add(tt);
        }
        templateTaskRepository.saveAll(templateTasks);

    }

    /**Get all comments for a job
     * @param id
     * @return all comments
     */
    public List<Comment> getCommentsForJob(Integer id) {
        if (id == null || id == 0) {
            throw new RuntimeException("Job id is wrong="+id);
        }
        Iterable<Comment> comments = commentRepository.findCommentsByJob(id);
        Iterator<Comment> iterator = comments.iterator();
        ArrayList<Comment> result = new ArrayList<>();
        while(iterator.hasNext()) {
            Comment comment = iterator.next();
            Integer employee_id = comment.getEmployee_id();
            Employee employee = employeeService.findEmployeeById(employee_id);
            comment.setEmployee_name(employee.getFirst_name()+employee.getSurname());
            result.add(comment);
        }
        return result;
    }

    /** Create a comment
     * @param httpServletReques
     * @param comment
     */
    public void createComment(HttpServletRequest httpServletReques,Comment comment) {
        if (comment.getEmployee_id() == null) {
            comment.setEmployee_id(Integer.valueOf((String)httpServletReques.getAttribute("id")));
        }
        final Employee employee = employeeService.findEmployeeById(comment.getEmployee_id());
        comment.setEmployee_name(employee.getFirst_name()+""+employee.getSurname());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
        comment.setContent_time(time);
        commentRepository.save(comment);
    }

    /**Create a receipt for a job
     * @param job
     */
    private void createReceipt(Job job) {
        if (job.getStatus() != 5) {
            throw new RuntimeException("The job has not been closed");
        }
        final Integer jobId = job.getId();
        Integer aircraft_id = job.getAircraft_id();
        Aircraft aircraft = aircraftRepository.findById(aircraft_id).get();
        Integer customer_id = aircraft.getCustomer_id();

        //create time
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = df.format(new Date());

        //Delivery time
        String deliveryTime = TimeUtils.addDateHours(createTime,96,"yyyy-MM-dd HH:mm:ss");

        //crn
        String crn = createTime.replaceAll("[[\\s-:punct:]]","") + jobId;

        //price
        Double price = job.getActual_cost_time() * 500;

        Receipt receipt = new Receipt(jobId,customer_id,deliveryTime,createTime,price,crn);

        receiptRepository.save(receipt);
    }

    /**Update a aircraft
     * @param aircraft
     */
    public void updateAircraft(Aircraft aircraft) {
        if (aircraft == null) {
            throw new RuntimeException("The aircraft is null");
        }

        Aircraft orginalAircraft = aircraftRepository.findById(aircraft.getId()).get();

        if (aircraft.getAircraft_pic() != null) {
            orginalAircraft.setAircraft_pic(aircraft.getAircraft_pic());
        }

        //update status
        //0:Servicing  1:Need maintaining 2:Maintaining 99:need staff to confirm register information
        if (aircraft.getStatus() != null) {
            orginalAircraft.setStatus(aircraft.getStatus());
        }

        if (aircraft.getLast_modify_time() != null) {
            orginalAircraft.setLast_modify_time(aircraft.getLast_modify_time());
            //update next service time
            orginalAircraft.setNext_modify_time(TimeUtils.addDateHours(orginalAircraft.getLast_modify_time(),orginalAircraft.getMaintenance_cycle(),"yyyy-MM-dd"));
        }
        if (aircraft.getMaintenance_cycle() != null) {
            orginalAircraft.setMaintenance_cycle(aircraft.getMaintenance_cycle());
            //update next service time
            orginalAircraft.setNext_modify_time(TimeUtils.addDateHours(orginalAircraft.getLast_modify_time(),orginalAircraft.getMaintenance_cycle(),"yyyy-MM-dd"));
        }

        //Judge if need maintain
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = df.parse(orginalAircraft.getNext_modify_time());
            Date currentDate = new Date();
            if (parse.before(currentDate)) {
                orginalAircraft.setStatus(1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        aircraftRepository.save(orginalAircraft);
    }


}
