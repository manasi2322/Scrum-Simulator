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

public class UserStoryStore {

    private static UserStoryStore userStoryStore;
    private static final String FILE_PATH_USER_STORIES = "src/main/resources/UserStories.json";



    /**
     * returns the shared instance of the UserStoryStore which contains all user stories in the
     * system.
     *
     * @return
     */
    public static UserStoryStore getInstance() {
        if (userStoryStore == null) {
            userStoryStore = new UserStoryStore();
        }
        return userStoryStore;
    }


    private UserStoryStore() {
    }

    public static String convertToJson(List<UserStory> updatedData) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\"UserStories\": [\n");

        for (int i = 0; i < updatedData.size(); i++) {
            UserStory userStory = updatedData.get(i);

            jsonBuilder.append("  {\n");
            jsonBuilder.append("    \"Name\": \"").append(userStory.getName()).append("\",\n");
            jsonBuilder.append("    \"Id\": \"").append((userStory.getStandardId())).append("\",\n");
            jsonBuilder.append("    \"Points\": \"").append(userStory.getPointValue()).append("\",\n");
            jsonBuilder.append("    \"Status\": \"").append(userStory.getStatus()).append("\",\n");
            jsonBuilder.append("    \"BusinessValue\": \"").append(userStory.getBusinessValue()).append("\",\n");
            jsonBuilder.append("    \"Description\": \"").append(userStory.getDescription()).append("\"\n");

            jsonBuilder.append("  }");

            // Add a comma if it's not the last element
            if (i < updatedData.size() - 1) {
                jsonBuilder.append(",");
            }
            jsonBuilder.append("\n");
        }

        jsonBuilder.append("]");
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    private static void updateSimulationData(List<UserStory> updatedData) {
        try (OutputStreamWriter writer =
                     new OutputStreamWriter(
                             new FileOutputStream(FILE_PATH_USER_STORIES), StandardCharsets.UTF_8)) {
            //String json = gson.toJson(updatedData);  // Convert the list to JSON
            //json = "{\"UserStories\": " + json + "}";  // Wrap the JSON in "UserStories
            System.out.println(updatedData);
            String j = convertToJson(updatedData);

            // print to console
            System.out.println(j);
            writer.write(j);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to simulation.JSON");
        }
    }

    public void addUserStory(UserStory userStory) {
        
       List<UserStory> userStories = getUserStories(); //print
        System.out.println("this is the user stories that already exist: ");
        System.out.println(userStories);
        userStories.add(userStory);
        System.out.println("this is old and the new : ");
        System.out.println(userStories);


        updateSimulationData(userStories);

    }

     public void deleteUerStory(String name) {
         List<UserStory> userStories = getUserStories();

         for (UserStory userStory : userStories) {
             if (userStory.getName().equals(name)) {
                 userStories.remove(userStory);
                 break;
             }
         }
         updateSimulationData(userStories);
     }

     public void updateUserStory(String id, UserStory userStory) {
         List<UserStory> userStories = getUserStories();

         for (int i = 0; i < userStories.size(); i++) {
             if (String.valueOf(userStories.get(i).getStandardId()).equals(id)) {
                 userStories.set(i, userStory);
                 break;
             }
         }
         updateSimulationData(userStories);
     }

    public void updateUserStoryByName(String name, UserStory userStory) {
        List<UserStory> userStories = getUserStories();

        for (int i = 0; i < userStories.size(); i++) {
            if (userStories.get(i).getName().equals(name)) {
                userStories.set(i, userStory);
                break;
            }
        }
        updateSimulationData(userStories);
    }

    public List<UserStory> getUserStories() {
        return getUserStoriesFromPersistantStorage();
    }

    public static List<UserStory> getUserStoriesFromPersistantStorage() {
        List<UserStory> userStories = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(new File(FILE_PATH_USER_STORIES));

            JsonNode stories = root.path("UserStories");
            for (JsonNode story : stories) {
                UserStory a =
                        UserStoryFactory.getInstance().createNewUserStory(
                            story.path("Name").asText(),
                            story.path("Description").asText(),
                            story.path("Points").asDouble(),
                            story.path("BusinessValue").asDouble());
                a.setStatus(story.path("Status").asText());
                a.setStandardId(story.path("Id").asInt());
                a.doRegister();
                userStories.add(a);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userStories;
    }
}