package com.inft.awm.domain.response;

import com.inft.awm.domain.Aircraft;
import com.inft.awm.domain.Component;

import java.util.ArrayList;
import java.util.List;

public class ResponseAircraft extends Aircraft {

    private List<Component> components = new ArrayList<>();

    public ResponseAircraft(Aircraft ac) {
        this.id = ac.getId();
        this.aircraft_pic = ac.getAircraft_pic();
        this.customer_id = ac.getCustomer_id();
        this.last_modify_time = ac.getLast_modify_time();
        this.next_modify_time = ac.getNext_modify_time();
        this.maintenance_cycle = ac.getMaintenance_cycle();
        this.registration = ac.getRegistration();
        this.serial = ac.getSerial();
        this.status = ac.getStatus();
        this.type = ac.getType();
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }
}
