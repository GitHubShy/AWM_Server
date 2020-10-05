package com.inft.awm.controller;

import com.inft.awm.custom.NeedToken;
import com.inft.awm.domain.Aircraft;
import com.inft.awm.domain.Component;
import com.inft.awm.domain.Job;
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
    public List<Job> getAllJobs(HttpServletRequest httpServletRequest, int id) {
        final List<Job> allJobs = workshopService.getAllJobs(id);
        return allJobs;
    }

    @PostMapping(value = "/createJob")
    public SimpleResult createAllJobs(HttpServletRequest httpServletRequest, @RequestBody Job job) {
        workshopService.createJob(job);
        return new SimpleResult("Success");
    }
}
