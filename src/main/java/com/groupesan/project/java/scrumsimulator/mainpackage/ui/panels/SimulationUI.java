package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.*;
import com.groupesan.project.java.scrumsimulator.mainpackage.state.SimulationStateManager;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Objects;

import java.awt.Dimension; 
import java.awt.GridBagConstraints; 
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

/**
* SimulationUI is the main user interface for the simulation. It displays different UI elements
* based on the user's selected role.
*/
public class SimulationUI extends JFrame implements BaseComponent {
   private String userRole;
   private String selectedSimulationId;
   private JPanel panel;
   private Spike spike;
   //private SpikeStore spikeStore;
   private String selectedSpikeID;

   /** Constructor for SimulationUI. It initializes the role selection process. */
   public SimulationUI() {
       init();
   }


   // Method to select a simulation
   private void selectSimulation() {
       JSONArray simulations = getSimulations();
       if (simulations != null) {
           // Create a dialog to choose a simulation
           // For simplicity, let's assume it's a list in a JOptionPane
           String[] simulationNames = new String[simulations.length()];
           String [] spikeNames = new String [simulations.length()];
           for (int i = 0; i < simulations.length(); i++) {
               JSONObject simulation = simulations.getJSONObject(i);
               simulationNames[i] =
                       simulation.getString("Name") + " - " + simulation.getString("ID");
                spikeNames[i] = simulation.getString("SpikeName");
           }
           String selectedSimulation =
                   (String)
                           JOptionPane.showInputDialog(
                                   null,
                                   "Select a Simulation:",
                                   "Simulation Selection",
                                   JOptionPane.QUESTION_MESSAGE,
                                   null,
                                   simulationNames,
                                   simulationNames[0]);


           // Store the selected simulation ID (extract from selectedSimulation)
           if (selectedSimulation != null) {
               this.selectedSimulationId = selectedSimulation.split(" - ")[1];
               String selectedSpikeName = spikeNames[Arrays.asList(simulationNames).indexOf(selectedSimulation)];
               this.selectedSpikeID = selectedSpikeName.split(":")[1];

               for (Spike s: SpikeStore.getInstance().getSpikes()){
                if(Objects.equals(s.getId(), selectedSpikeID)){
                        this.spike = s;
                        break;
                    }
               }
           }


           selectUserRole();
       }
   }


   // Method to read simulations from JSON file
   private JSONArray getSimulations() {
       try (FileInputStream fis = new FileInputStream("src/main/resources/simulation.JSON")) {
           JSONTokener tokener = new JSONTokener(fis);
           JSONObject obj = new JSONObject(tokener);
           return obj.getJSONArray("Simulations");
       } catch (Exception e) {
           e.printStackTrace(); 
           return null;
       }
   }


   /** Opens the RoleSelectionPane for the user to select their role. */
   private void selectUserRole() {
       RoleSelectionPane roleSelectionPane = new RoleSelectionPane(this::setUserRole);


       // Set the SimulationUI as the parent of RoleSelectionPane
       roleSelectionPane.setLocationRelativeTo(this);


       // Make the RoleSelectionPane stay on top
       roleSelectionPane.setAlwaysOnTop(true);


       roleSelectionPane.setVisible(true);
   }


   /**
    * Sets the user role for the simulation and initializes the UI accordingly.
    *
    * @param role The role selected by the user.
    */
   void setUserRole(String role) {
       this.userRole = role;
       updateUI();
   }


   private void updateUI() {
       panel.removeAll(); // Clear the initial prompt
       //Testing UI-----------------------------------------------------------------------------------
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Simulation");
        setSize(400, 300);
        setMinimumSize(new Dimension(400, 600));

        GridBagLayout myGridbagLayout = new GridBagLayout();
       // JPanel myJpanel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(myGridbagLayout);


       panel.add(new JLabel("Welcome to the Simulation")); 
       if (userRole != null) {
           panel.add(new JLabel("Your Role: " + userRole), 
           new CustomConstraints(0, 1, GridBagConstraints.WEST, 1.0, 0.02, GridBagConstraints.HORIZONTAL));
           // Add role-specific UI components here
       }
       if (selectedSimulationId != null) {
           panel.add(new JLabel("Selected Simulation ID: " + selectedSimulationId),
           new CustomConstraints(0, 2, GridBagConstraints.WEST, 1.0, 0.02, GridBagConstraints.HORIZONTAL));
           //Add logic to read from Simulation.java
        }
        //Adding the form: -----------------------------------------------------------------------------
        panel.add(new JLabel("Spike ID: " + spike.getId()),
         new CustomConstraints(0, 3, GridBagConstraints.WEST, 1.0, 0.02, GridBagConstraints.HORIZONTAL));
        panel.add(new JLabel("Spike Name: " + spike.getName()),
        new CustomConstraints(0, 4, GridBagConstraints.WEST, 1.0, 0.02, GridBagConstraints.HORIZONTAL));
        panel.add(new JLabel("Spike Description: " + spike.getDescription()),
        new CustomConstraints(0, 5, GridBagConstraints.WEST, 1.0, 0.02, GridBagConstraints.HORIZONTAL));
        panel.add(new JLabel("Spike MinVal: " + spike.getMinVal()), 
        new CustomConstraints(0, 6, GridBagConstraints.WEST, 1.0, 0.02, GridBagConstraints.HORIZONTAL));
        panel.add(new JLabel("Spike MaxVal: " + spike.getMaxVal()),
        new CustomConstraints(0, 7, GridBagConstraints.WEST, 1.0, 0.02, GridBagConstraints.HORIZONTAL));
        panel.add(new JLabel("Spike Blocked UserStory ID: " + spike.getBlockedUserstory()),
        new CustomConstraints(0, 8, GridBagConstraints.WEST, 1.0, 0.02, GridBagConstraints.HORIZONTAL));
       //Testing layout----------------------------------------------------------------------------------        

       JButton simulationBtn = new JButton("Run Simulation");

       simulationBtn.addActionListener(
        new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int successProbability = (int)Math.floor(spike.getSuccessRate());
                int randomValue = (int)(Math.random() * 100);
                if (successProbability <= randomValue) {
                    // Add logic to update blocked status to in progress for the blocked user story ID
                    // and remove the spike from spikes.JSON
                    UserStory userStory = null;
                    for (UserStory u: UserStoryStore.getInstance().getUserStories()) {
                        if (Objects.equals(String.valueOf(u.getStandardId()), spike.getBlockedUserstory())) {
                            userStory = u;
                            userStory.setStatus("in progress");
                            UserStoryStore.getInstance().updateUserStory(spike.getBlockedUserstory(), userStory);
                            break;
                        }
                    }

                    SpikeStore.getInstance().removeSpike(spike);

                    JOptionPane.
                    showMessageDialog(null,
                     "The issue was resolved. Status for user story ID " + spike.getBlockedUserstory() + " changed to in progress. Spike " + spike.getId() + " has also been deleted",
                      "Status",
                      JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    //Show message that it didn't work
                    JOptionPane.showMessageDialog(
                    null, "The issue failed resolved", "Status", JOptionPane.WARNING_MESSAGE);
                }
                dispose();
            }
       });

       Simulation sim = SimulationStateManager.getSimulationById(selectedSimulationId);
       int count = 9;
       if(sim!=null){
           for (Sprint sp: sim.getSprints()){
               count++;
               panel.add(new JLabel("Sprint: " + sp.getName()),
                       new CustomConstraints(0, count, GridBagConstraints.WEST, 1.0, 0.02, GridBagConstraints.HORIZONTAL));

               for (UserStory us: sp.getUserStoriesObjectList()){
                   count++;
                   panel.add(new JLabel("Name: " + us.getName()+"; Statue: " + us.getStatus()),
                           new CustomConstraints(0, count, GridBagConstraints.WEST, 1.0, 0.02, GridBagConstraints.HORIZONTAL));

               }

           }
       }

       count++;
       panel.add(
        simulationBtn,
        new CustomConstraints(0, count, GridBagConstraints.WEST, 1.0, 0.02, GridBagConstraints.HORIZONTAL));
       revalidate();
       repaint();
   }

   /**
    * Initializes the user interface components. This method is called after the user role has been
    * set.
    */
   @Override
   public void init() {
       setTitle("Simulation");
       setSize(400, 300);
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       panel = new JPanel();
       panel.add(
               new JLabel(
                       "Please select an active Simulation, then a role to join the Simulation"));
       setContentPane(panel);
       setVisible(true); // Make the UI visible first
       selectSimulation(); // Then start the simulation selection process
   }
}
