package com.groupesan.project.java.scrumsimulator.mainpackage.impl;


public class SpikeStoryFactory {
    private static SpikeStoryFactory sprintFactory;

    public static SpikeStoryFactory getSprintFactory() {
        if (sprintFactory == null) {
            sprintFactory = new SpikeStoryFactory();
        }

        return sprintFactory;
    }


    private SpikeStoryFactory() {
    }

    public Spike createNewSpike(String name, String description,  String blockedUSId, Double minProbability, Double maxProbability) {
        // generate a new sprint with a random ID
        int id = (int) (Math.random() * 1000000);

        Spike spike = new Spike(name, description, blockedUSId, id, minProbability, maxProbability);
        return spike;
    }
}