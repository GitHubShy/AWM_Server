package com.inft.awm.controller;

import com.inft.awm.custom.NeedToken;
import com.inft.awm.domain.*;
import com.inft.awm.domain.response.ResponseAircraft;
import com.inft.awm.response.SimpleResult;
import com.inft.awm.service.WorkshopService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/awm_server/workshop")
@Api(tags = "Interfaces for workshop")
@CrossOrigin
@NeedToken
public class WorkshopController {

    @Autowired
    private WorkshopService workshopService;

    @PostMapping(value = "/getAircraft")
    @NeedToken
    public List<ResponseAircraft> getAllAircraft(HttpServletRequest httpServletRequest,Integer id) {
        return workshopService.getAircraft(id);
    }

    @PostMapping(value = "/getCustomerAircraft")
    public List<ResponseAircraft> getCustomerAircraft(HttpServletRequest httpServletRequest,Integer id) {
        return workshopService.getCustomerAircraft(id);
    }

    @PostMapping(value = "/registerAircraft")
    public Aircraft registerAircraft(HttpServletRequest httpServletRequest, @RequestBody Aircraft aircraft) {
        Aircraft registerAircraft = workshopService.registerAircraft(aircraft);
        return registerAircraft;
    }

    @PostMapping(value = "/registerComponents")
    public SimpleResult registerComponents(HttpServletRequest httpServletRequest, @RequestBody List<Component> components) {
        workshopService.registerComponents(components);
        return new SimpleResult("Success");
    }

    @PostMapping(value = "/getAllJobs")
    @NeedToken
    public List<Job> getAllJobs(HttpServletRequest httpServletRequest, int id) {
        final List<Job> allJobs = workshopService.getAllJobs(id);
        return allJobs;
    }

    @PostMapping(value = "/getAllSubTasks")
    @NeedToken
    public List<SubTask> getAllSubTasks(HttpServletRequest httpServletRequest, int id) {
        List<SubTask> subTasksForJob = workshopService.getSubTasksForJob(id);
        return subTasksForJob;
    }

    @PostMapping(value = "/getAllSubTaskType")
    @NeedToken
    public List<SubTaskType> getAllSubTaskType() {
        List<SubTaskType> subTasksForJob = workshopService.getAllSubTaskType();
        return subTasksForJob;
    }

    @PostMapping(value = "/updateSubTask")
    @NeedToken
    public SimpleResult updateSubTask(HttpServletRequest httpServletRequest, @RequestBody SubTask subTask) {
        workshopService.updateSubTask(subTask);
        return new SimpleResult("Success");
    }

    @PostMapping(value = "/createJob")
    @NeedToken
    public SimpleResult createJob(HttpServletRequest httpServletRequest, @RequestBody Job job) {
        workshopService.createJob(job);
        return new SimpleResult("Success");
    }

    @PostMapping(value = "/createSubTask")
    @NeedToken
    public SimpleResult createSubTask(HttpServletRequest httpServletRequest, @RequestBody SubTask subTask) {
        workshopService.createSubTask(subTask);
        return new SimpleResult("Success");
    }

    @PostMapping(value = "/findAvailableTemplates")
    @NeedToken
    public List<Template> findAvailableTemplates(HttpServletRequest httpServletRequest) {
        String id = (String) httpServletRequest.getAttribute("id");
        List<Template> availableTemplates = workshopService.findAvailableTemplates(Integer.valueOf(id));
        return availableTemplates;
    }
}
