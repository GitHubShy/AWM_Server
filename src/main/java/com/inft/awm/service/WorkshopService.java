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

    public List<ResponseAircraft> getAircrafts(Integer airCraftId) {

        Iterable<Aircraft> allAircraft;
        if(airCraftId != null && airCraftId != 0) {
            allAircraft = aircraftRepository.findAircraft(airCraftId);
        } else {
            allAircraft = aircraftRepository.findAll();
        }


        Iterable<Component> allComponents = componentRepository.findAll();

        ArrayList<Component> components = new ArrayList<>();

        ArrayList<ResponseAircraft> responseAircraft = new ArrayList<>();

        Iterator<Component> componentIterator = allComponents.iterator();
        while(componentIterator.hasNext()) {
            components.add(componentIterator.next());
        }

        Iterator<Aircraft> aircraftIterator = allAircraft.iterator();
        while(aircraftIterator.hasNext()) {
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
