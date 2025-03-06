package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

public class Spike {

    private String name;
    private String description;
    private String us;
    private String id;

    private double minProbability;

    private double maxProbability;



    public Spike(String name, String description, String blockedUSId, int id, Double minProbability, Double maxProbability) {
        this.name = name;
        this.description = description;
        this.id = String.valueOf(id);
        this.us = blockedUSId;
        this.minProbability = minProbability;
        this.maxProbability = maxProbability;
    }

    public String getId() {
        // TODO Auto-generated method stub
        return id;
    }


    public String getName() {
        // TODO Auto-generated method stub
        return name;
    }


    public String getDescription() {
        // TODO Auto-generated method stub
        return description;
    }

    public Double getMinVal() {
        // TODO Auto-generated method stub
        return minProbability;
    }
    public Double getMaxVal() {
        // TODO Auto-generated method stub
        return maxProbability;
    }

    public String getBlockedUserstory() {
        // TODO Auto-generated method stub
        return us;
    }

    /*
    * Return a random value from the spike range
    */
    public Double getSuccessRate() {
        return (minProbability + (Math.random() * (maxProbability - minProbability)));
    }
}
