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

public class SpikeStore {
    private static SpikeStore spikeStore;

    private static final String FILE_PATH_SPIKES = "src/main/resources/Spikes.json";


    public static SpikeStore getInstance() {
        if (spikeStore == null) {
            spikeStore = new SpikeStore();
        }
        return spikeStore;
    }

    //private List<Sprint> sprints;

    public SpikeStore() {
        //sprints = new ArrayList<>();
    }

    public /*static*/ List<Spike> addSpike(Spike spike) {

        List<Spike> spikes = getSpikes();
        // check if sprint already exists
        for (Spike s : spikes) {
            if (s.getId() == spike.getId() && s.getName().equals(spike.getName())){
                return spikes;
            }
        }
        spikes.add(spike);
        updateSpikes(spikes);
        List<Spike> updatedData = getSpikes();
        return updatedData;
    }

    public void removeSpike(Spike spike) {
        List<Spike> spikes = getSpikes();

        //spikes.remove(spike);

        List<Spike> updatedSpikes = new ArrayList<>();

        for (Spike oldSpike: spikes){
            if(!Objects.equals(oldSpike.getName(), spike.getName())){
                updatedSpikes.add(oldSpike);
            }
        }


        updateSpikes(updatedSpikes);
    }

    public List<Spike> updateSpike(Spike spike){
        List<Spike> spikes = getSpikes();

        for (int i = 0; i < spikes.size(); i++) {
            Spike sp = spikes.get(i);

            System.out.println(" the curr sprint: "+ sp.getName());
            System.out.println(" the sprint that needs to be updated: " + spike.getName());

            if(Objects.equals(sp.getId(), spike.getId())){
                spikes.set(i, spike);
                break;
            }
        }
        updateSpikes(spikes);
        return spikes;
    }
    
    public static String getUserStoriesCancatenatedString(List<String> userStories) {
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

    private static String convertToJson(List<Spike> updatedData) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\"Spikes\": [\n");

        for (int i = 0; i < updatedData.size(); i++) {
            Spike spike = updatedData.get(i);

            System.out.println(spike.getName()+" showing user stories: ");



            jsonBuilder.append("  {\n");
            jsonBuilder.append("    \"ID\": \"").append(spike.getId()).append("\",\n");
            jsonBuilder.append("    \"Name\": \"").append(spike.getName()).append("\",\n");
            jsonBuilder.append("    \"Description\": \"").append(spike.getDescription()).append("\",\n");
            jsonBuilder.append("    \"MinVal\": \"").append(spike.getMinVal()).append("\",\n");
            jsonBuilder.append("    \"MaxVal\": \"").append(spike.getMaxVal()).append("\",\n");

            //jsonBuilder.append("    \"BlockedUS_Id\":\"").append(spike.getBlockedUserstory()).append("\",\n");
            //jsonBuilder.append("    \"Status\": \"").append(sprint.getStatus()).append("\",\n");
            jsonBuilder.append("    \"BlockedUS_Id\": \"").append(spike.getBlockedUserstory()).append("\"\n");

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

    public static void updateSpikes(List<Spike> spikes) {
        try (OutputStreamWriter writer =
                     new OutputStreamWriter(
                             new FileOutputStream(FILE_PATH_SPIKES), StandardCharsets.UTF_8)) {
            //String json = gson.toJson(updatedData);  // Convert the list to JSON
            //json = "{\"UserStories\": " + json + "}";  // Wrap the JSON in "UserStories
            //System.out.println(updatedData);
            String j = convertToJson(spikes);

            // print to console
            System.out.println(j);
            writer.write(j);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to simulation.JSON");
        }
    }

    public /*static*/ List<Spike> getSpikes() {

        List<Spike> spikes = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(new File(FILE_PATH_SPIKES));

            JsonNode spikesNode = root.path("Spikes");


            for (JsonNode spikenode : spikesNode) {
                String spikeName = spikenode.path("Name").asText();
                String spikeDescription = spikenode.path("Description").asText();
                String blockedUserStoriesId = spikenode.path("BlockedUS_Id").asText();
                String minStrVal = spikenode.path("MinVal").asText();
                String maxStrVal = spikenode.path("MaxVal").asText();

                Double maxVal = Double.parseDouble(minStrVal);
                Double minVal = Double.parseDouble(maxStrVal);


                int Id = spikenode.path("ID").asInt();

                Spike spike = new Spike(spikeName, spikeDescription, blockedUserStoriesId, Id, maxVal, minVal);

                spikes.add(spike);

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return spikes;

    }
}
