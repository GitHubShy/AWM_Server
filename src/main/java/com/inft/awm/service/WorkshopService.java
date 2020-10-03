package com.inft.awm.service;

import com.inft.awm.domain.Aircraft;
import com.inft.awm.domain.Component;
import com.inft.awm.domain.response.ResponseAircraft;
import com.inft.awm.repository.AircraftRepository;
import com.inft.awm.repository.ComponentRepository;
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

    public Aircraft registerAircraft(Aircraft aircraft) {
        Aircraft saved = aircraftRepository.save(aircraft);
        return saved;
    }

    public void registerComponents(List<Component> components) {
        componentRepository.saveAll(components);
    }


    public List<ResponseAircraft> getAircraft(Integer aircraftId) {

        Iterable<Aircraft> allAircraft;
        if(aircraftId != null && aircraftId != 0) {
            allAircraft = aircraftRepository.findAircraft(aircraftId);
        } else {
            allAircraft = aircraftRepository.findAll();
        }


        ArrayList<ResponseAircraft> responseAircraft = getResponseAircrafts(allAircraft);
        return responseAircraft;
    }

    public List<ResponseAircraft> getCustomerAircraft(Integer customerId) {

        Iterable<Aircraft> allAircraft;
        if(customerId != null && customerId != 0) {
            allAircraft = aircraftRepository.findAircraftByCustomer(customerId);
        } else {
            throw new RuntimeException("The customer id is invalid: " + customerId);
        }

        ArrayList<ResponseAircraft> responseAircraft = getResponseAircrafts(allAircraft);
        return responseAircraft;
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
