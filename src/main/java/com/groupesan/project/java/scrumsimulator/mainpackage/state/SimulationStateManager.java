package com.groupesan.project.java.scrumsimulator.mainpackage.state;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Simulation;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Sprint;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintStore;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SimulationStateManager manages the state of a simulation, including whether it is running and
 * saving its ID.
 */
public class SimulationStateManager {
    private boolean running;
    private static final String JSON_FILE_PATH = "src/main/resources/simulation.JSON";

    /** Simulation State manager. Not running by default. */
    public SimulationStateManager() {
        this.running = false;
    }

    /**
     * Returns the current state of the simulation.
     *
     * @return boolean running
     */
    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    /** Method to set the simulation state to running. */
    public void startSimulation() {
        setRunning(true);
        // Add other logic for starting the simulation
    }

    /** Method to set the simulation state to not running. */
    public void stopSimulation() {
        setRunning(false);
        // Add other logic for stopping the simulation
    }

    /**
     * Saves the details of a new simulation to a JSON file.
     *
     * @param simId The ID of the simulation.
     * @param simName The name of the simulation.
     * @param numberOfSprints The number of sprints in the simulation.
     * @param durationOfSprints The duration of sprints in the simulation.
     */
    public static void saveNewSimulationDetails(
            String simId, String simName, String numberOfSprints, String durationOfSprints, String SpikeName, List<String> sprints) {
        JSONObject simulationData = getSimulationData();
        if (simulationData == null) {
            simulationData = new JSONObject();
        }

        JSONObject newSimulation = new JSONObject();
        newSimulation.put("ID", simId);
        newSimulation.put("Name", simName);
        newSimulation.put("Status", "New");
        newSimulation.put("NumberOfSprints", numberOfSprints);
        newSimulation.put("DurationOfSprints", durationOfSprints);
        newSimulation.put("Sprints", new JSONArray());
        newSimulation.put("Events", new JSONArray());
        newSimulation.put("Users", new JSONArray());
        newSimulation.put("SpikeName", SpikeName);
        newSimulation.put("Sprints",  sprints);


        JSONArray simulations = simulationData.optJSONArray("Simulations");
        if (simulations == null) {
            simulations = new JSONArray();
            simulationData.put("Simulations", simulations);
        }
        simulations.put(newSimulation);

        updateSimulationData(simulationData);
    }

    private static JSONObject getSimulationData() {
        try (FileInputStream fis = new FileInputStream(JSON_FILE_PATH)) {
            JSONTokener tokener = new JSONTokener(fis);
            return new JSONObject(tokener);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading from simulation.JSON");
            return null;
        }
    }

    public static Simulation getSimulationById(String id){

        Simulation sim;

        JSONObject obj = getSimulationData();

        System.out.println("printing out the sim data:  ");
        //System.out.println(obj);

        JSONObject selectedObject = null;

        if(obj.has("Simulations")){
            JSONArray jsonArray = obj.getJSONArray("Simulations");


            // Iterate over the JSONArray and cast each element to a JSONObject
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                System.out.println("the id: "+ object.get("ID"));

                // Do something with the object
                System.out.println(object.toString());
                if(Objects.equals(id,  object.get("ID"))){
                    System.out.println("found it!!");
                    System.out.println("here it is : "+ object.toString());

                    selectedObject = object;
                    break;

                }
            }

        }

        if(selectedObject==null){
            return null;
        }

        String name = (String) selectedObject.get("Name");
        String duration = (String) selectedObject.get("DurationOfSprints");
        String status = (String) selectedObject.get("Status");
        // needs to be changed:
        JSONArray jsonArray = selectedObject.getJSONArray("Sprints");

        List<String> sprintNames = new ArrayList<>();


        System.out.println("printing the sprintNames: ");
        for (int i = 0; i < jsonArray.length(); i++) {
            String sprintName = (String)jsonArray.get(i);
            System.out.println(sprintName);
            sprintNames.add(sprintName);
        }
        List<Sprint> sprints = SprintStore.getInstance().getSprintsByNames(sprintNames);
        String spike = (String) selectedObject.get("SpikeName");


        sim = new Simulation(status, sprints, duration, spike, name);

        return sim;
    }

    private static void updateSimulationData(JSONObject updatedData) {
        try (OutputStreamWriter writer =
                new OutputStreamWriter(
                        new FileOutputStream(JSON_FILE_PATH), StandardCharsets.UTF_8)) {
            writer.write(updatedData.toString(4));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to simulation.JSON");
        }
    }
}
