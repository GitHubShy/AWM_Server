package com.inft.awm.service;

import com.inft.awm.domain.Aircraft;
import com.inft.awm.domain.Component;
import com.inft.awm.domain.Job;
import com.inft.awm.domain.response.ResponseAircraft;
import com.inft.awm.repository.AircraftRepository;
import com.inft.awm.repository.ComponentRepository;
import com.inft.awm.repository.JobRepository;
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
        Iterator<Job> iterator = jobIterator.iterator();
        while (iterator.hasNext()) {
            jobs.add(iterator.next());
        }
        return jobs;
    }

    public void createJob(Job job) {
        //all created job should be set status=0
        job.setStatus(0);
        String start_time = job.getStart_time();
        String due_time = job.getDue_time();
        job.setPlanned_cost_time(TimeUtils.getDateDiffHours(start_time,due_time,"yyyy-MM-dd"));
        jobRepository.save(job);
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


}
