package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import java.util.List;

public class Simulation {
    private String status;
    private List<Sprint> sprints;
    private String duration;
    private String spike;
    private String name;
    //status, sprints, duration, spike, name
    public Simulation(String status, List<Sprint> sprints, String duration, String spike, String name) {
        this.status = status;
        this.sprints = sprints;
        this.duration = duration;
        this.spike = spike;
        this.name = name;

    }

    public String getName(){
        return this.name;
    }

    public List<Sprint> getSprints(){
        return this.sprints;
    }


}
