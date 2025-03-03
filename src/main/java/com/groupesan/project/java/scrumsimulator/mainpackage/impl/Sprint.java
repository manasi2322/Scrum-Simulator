package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import java.util.ArrayList;
import java.util.List;

public class Sprint {
    private ArrayList<String> userStories = new ArrayList<>();

    private ArrayList<UserStory> userStoriesObjectList = new ArrayList<>();
    private String name;

    private String description;

    private int length;

    private int remainingDays;

    private int durationOfSprint;

    private int id;

    public Sprint(String name, String description, int length, int id) {
        this.name = name;
        this.description = description;
        this.length = length;
        this.remainingDays = length;
        this.id = id;
    }

    public Sprint(String name, String description, int length, int id, int durationOfSprint) {
        this.name = name;
        this.description = description;
        this.length = length;
        this.remainingDays = length;
        this.id = id;
        this.durationOfSprint = durationOfSprint;
    }

    public void addUserStory(String us) {
        userStories.add(us);
    }

    public void setUserStoriesObjectList(ArrayList<UserStory> userStoriesObjectList){
        this.userStoriesObjectList = userStoriesObjectList;

    }

    public List<String> getUserStories() {
        return userStories;
    }

    public List<UserStory> getUserStoriesObjectList() {
        return userStoriesObjectList;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getLength() {
        return length;
    }

    public int getDaysRemaining() {
        return remainingDays;
    }

    public void decrementRemainingDays() {
        if (remainingDays > 0) remainingDays--;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        String header = "Sprint " + this.id + ": " + this.name + "\n";
        StringBuilder USes = new StringBuilder();

        for (String us : userStories) {
            USes.append(us).append("\n");
        }
        return header + USes;
    }
}
