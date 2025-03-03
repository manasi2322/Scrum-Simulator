package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SprintStore {
    private static SprintStore sprintStore;

    private static final String FILE_PATH_SPRINTS = "src/main/resources/Sprints.json";


    public static SprintStore getInstance() {
        if (sprintStore == null) {
            sprintStore = new SprintStore();
        }
        return sprintStore;
    }

    //private List<Sprint> sprints;

    public SprintStore() {
        //sprints = new ArrayList<>();
    }

    public List<Sprint> addSprint(Sprint sprint) {

        List<Sprint> sprints = getSprints();
        // check if sprint already exists
        for (Sprint s : sprints) {
            if (s.getId() == sprint.getId() && s.getName().equals(sprint.getName())){
                return sprints;
            }
        }
        sprints.add(sprint);
        updateSprints(sprints);
        List<Sprint> updatedData = getSprints();
        return updatedData;
    }

    public List<Sprint> updateSprint(Sprint sprint){
        List<Sprint> sprints = getSprints();

        for (int i = 0; i < sprints.size(); i++) {
            Sprint sp = sprints.get(i);

            System.out.println(" the curr sprint: "+ sp.getName());
            System.out.println(" the sprint that needs to be updated: " + sprint.getName());

            if(Objects.equals(sp.getId(), sprint.getId())){
                sprints.set(i, sprint);
                break;
            }


        }
        updateSprints(sprints);
        return sprints;
    }

    public String getUserStoriesCancatenatedString(List<String> userStories) {
        StringBuilder sb = new StringBuilder();

        for( int i = 0; i < userStories.size(); i++ ) {
            String userStory = userStories.get(i);

            if (i == userStories.size() - 1) {
                sb.append(userStory);
            } else {
                sb.append(userStory).append(",");
            }

        }

        return sb.toString();
    }

    private String convertToJson(List<Sprint> updatedData) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\"Sprints\": [\n");

        for (int i = 0; i < updatedData.size(); i++) {
            Sprint sprint = updatedData.get(i);

            System.out.println(sprint.getName()+" showing user stories: ");

            String userStories = getUserStoriesCancatenatedString(sprint.getUserStories());
            System.out.println(userStories);


            jsonBuilder.append("  {\n");
            jsonBuilder.append("    \"ID\": \"").append(sprint.getId()).append("\",\n");
            jsonBuilder.append("    \"Name\": \"").append(sprint.getName()).append("\",\n");
            jsonBuilder.append("    \"Description\": \"").append(sprint.getDescription()).append("\",\n");
            jsonBuilder.append("    \"Length\":\"").append(sprint.getLength()).append("\",\n");
            jsonBuilder.append("    \"Remaining\":\"").append(sprint.getDaysRemaining()).append("\",\n");
            jsonBuilder.append("    \"UserStories\": \"").append(userStories).append("\"\n");
            //jsonBuilder.append("    \"Status\": \"").append(sprint.getStatus()).append("\",\n");
            //jsonBuilder.append("    \"DurationOfSprint\": \"").append(sprint.getDurationOfSprint()).append("\",\n");

            jsonBuilder.append("  }");

            // Add a comma if it's not the last element
            if (i < updatedData.size() - 1) {
                jsonBuilder.append(",");
            }   
            jsonBuilder.append("\n");
        }

        jsonBuilder.append("]}");

        return jsonBuilder.toString();
    }

    public void updateSprints(List<Sprint> sprints) {
        try (OutputStreamWriter writer =
                     new OutputStreamWriter(
                             new FileOutputStream(FILE_PATH_SPRINTS), StandardCharsets.UTF_8)) {
            //String json = gson.toJson(updatedData);  // Convert the list to JSON
            //json = "{\"UserStories\": " + json + "}";  // Wrap the JSON in "UserStories
            //System.out.println(updatedData);
            String j = convertToJson(sprints);

            // print to console
            System.out.println(j);
            writer.write(j);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to simulation.JSON");
        }
    }

    public List<Sprint> getSprintsByNames(List<String> sprintNames) {
        List<Sprint> sprints = getSprints();

        List<Sprint> selectedSprints = new ArrayList<>();


        for (int i = 0; i < sprints.size(); i++) {
            Sprint sprint = sprints.get(i);

            for (int j = 0; j < sprintNames.size(); j++) {
                String name = sprintNames.get(j);

                if(Objects.equals(sprint.getName(), name)){
                    selectedSprints.add(sprint);
                }

            }

        }
        return selectedSprints;

    }



        public List<Sprint> getSprints() {

        List<Sprint> sprints = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(new File(FILE_PATH_SPRINTS));

            JsonNode sprintsNode = root.path("Sprints");

            // each sprint looks like this:
//            {
//                "id": 1,
//                    "name": "Sprint 1",
//                    "description": "This is the first sprint",
//                    "userStories": [],
//                "durationOfSprint": "2"
//            }


            for (JsonNode sprintNode : sprintsNode) {
                String name = sprintNode.path("Name").asText();
                String status = sprintNode.path("Status").asText();
                String description = sprintNode.path("Description").asText();
                String userStories = sprintNode.path("UserStories").asText();
                int length = sprintNode.path("Length").asInt();


                int duration = sprintNode.path("DurationOfSprint").asInt();
                int id = sprintNode.path("ID").asInt();
                Sprint sprint = new Sprint(name, description, length, id, duration);

                for (String us : userStories.split(",")) {
                    sprint.addUserStory(us);
                }

                ArrayList<String> usNames = new ArrayList<>();

                List<UserStory> usList = UserStoryStore.getInstance().getUserStories();


                for (String usNameStamp : sprint.getUserStories()) {
                    if (usNameStamp.contains("- ")) {
                        String usName = usNameStamp.split("- ")[1];
                        usNames.add(usName);
                    }


                    ArrayList<UserStory> usListInSprint = new ArrayList<UserStory>();

                    for (UserStory userstory : usList) {

                        for (String usName : usNames) {

                            if (Objects.equals(userstory.getName(), usName)) {
                                usListInSprint.add(userstory);
                                break;
                            }


                        }
                    }


                    sprint.setUserStoriesObjectList(usListInSprint);


                }

                sprints.add(sprint);

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        return sprints;

    }


}
