package com.inft.awm.controller;

import com.inft.awm.custom.NeedToken;
import com.inft.awm.domain.response.ResponseAircraft;
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
    public List<ResponseAircraft> getAllAircraft(HttpServletRequest httpServletRequest,Integer id) {
        return workshopService.getAircrafts(id);
    }
}
