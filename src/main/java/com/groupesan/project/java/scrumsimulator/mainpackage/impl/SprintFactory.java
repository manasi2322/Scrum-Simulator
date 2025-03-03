package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

public class SprintFactory {
    private static SprintFactory sprintFactory;

    public static SprintFactory getSprintFactory() {
        if (sprintFactory == null) {
            sprintFactory = new SprintFactory();
        }

        return sprintFactory;
    }

    private int numSprints;

    private SprintFactory() {
        numSprints = 0;
    }

    public Sprint createNewSprint(String name, String description, int length) {
        // generate a new sprint with a random ID
        int id = (int) (Math.random() * 1000000);
        Sprint newSprint = new Sprint(name, description, length, id);
        return newSprint;
    }

    public Sprint createNewSprint(String name, String description, int length, int id) {
        // generate a new sprint with a random ID
        Sprint newSprint = new Sprint(name, description, length, id);
        return newSprint;
    }
}
