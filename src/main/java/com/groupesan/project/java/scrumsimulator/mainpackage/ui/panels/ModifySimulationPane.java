package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.*;
import com.groupesan.project.java.scrumsimulator.mainpackage.state.SimulationManager;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.UUID;

/**
 * ModifySimulationPane is a UI component used by teachers to create or modify simulations. It
 * allows the generation of a new simulation ID and displays it on the UI.
 */
public class ModifySimulationPane extends JFrame implements BaseComponent {

    private SimulationManager simulationManager;
    private JTextField simulationNameField;
    private JSpinner numberOfSprintsField;
    private JSpinner durationOfSprintsField;
    private JComboBox spikeSelection;

    private JList<String> sprintList;

    DefaultListModel<String> listModel;

    private ArrayList<String> selectedItems = new ArrayList<String>();



    public ModifySimulationPane(SimulationManager manager) {
        this.simulationManager = manager;
        this.init();
    }

    /** Initializes the UI components of the ModifySimulationPane. */
    @Override
    public void init() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Create Simulation");
        setSize(400, 500);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        simulationNameField = new JTextField(20);
        numberOfSprintsField = new JSpinner(new SpinnerNumberModel(0, 0, 30, 1));
        durationOfSprintsField = new JSpinner(new SpinnerNumberModel(0, 0, 30, 1));
        spikeSelection = new JComboBox<>();

        for(Spike spike: SpikeStore.getInstance().getSpikes())
       { 
        spikeSelection.addItem(spike.getName()+"     :"+spike.getId());               
       }

        listModel = new DefaultListModel<>();
        for (Sprint sprint : SprintStore.getInstance().getSprints()) {
            listModel.addElement(sprint.getName());
        }

        sprintList = new JList<>(listModel);
        sprintList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        sprintList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    // Get selected values from the list
                    selectedItems = (ArrayList<String>) sprintList.getSelectedValuesList();

                    // Do something with the selected items
                    System.out.println("Selected items: " + selectedItems);
                }
            }
        });
        
        JLabel nameLabel = new JLabel("Simulation Name:");
        JLabel sprintsLabel = new JLabel("Number of Sprints:");
        JLabel sprintsLabel1 = new JLabel("Duration of Sprints:");
        JLabel spikeLabel = new JLabel("Spike:");


        panel.add(
                nameLabel,
                new CustomConstraints(
                        0, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));
        panel.add(
                simulationNameField,
                new CustomConstraints(
                        1, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        panel.add(
                sprintsLabel,
                new CustomConstraints(
                        0, 1, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));
        panel.add(
                numberOfSprintsField,
                new CustomConstraints(
                        1, 1, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        panel.add(
                sprintsLabel1,
                new CustomConstraints(
                        0, 2, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        panel.add(
                durationOfSprintsField,
                new CustomConstraints(
                        1, 2, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        panel.add(
                spikeLabel,
                new CustomConstraints(
                        0, 3, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));
        
         panel.add(
                spikeSelection,
                new CustomConstraints(
                        1, 3, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        

        JButton submitButton = new JButton("Create Simulation");
        submitButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        for (int i = 0; i < selectedItems.size(); i++) {
                            String userStoryEntry = selectedItems.get(i);

                            //String userStoryName = userStoryEntry.split("- ")[1];

                            selectedItems.set(i, userStoryEntry);

                        }


                        String simId = UUID.randomUUID().toString();
                        String simName = simulationNameField.getText();
                        String numberOfSprints = String.valueOf(numberOfSprintsField.getValue());
                        String durationOfSprints = String.valueOf(durationOfSprintsField.getValue());
                        String SelectionofSpike = (String)spikeSelection.getSelectedItem();
                        simulationManager.createSimulation(simId, simName, numberOfSprints, durationOfSprints,SelectionofSpike, selectedItems);

                        // Prepare a JTextField to display the Simulation ID
                        JTextField simIdField = new JTextField(simId);
                        simIdField.setEditable(false);
                        Object[] message = {
                            "A new simulation has been generated.\nSimulation ID:", simIdField
                        };
                                                                              
                        // Show a dialog with the JTextField containing the Simulation ID
                        JOptionPane.showMessageDialog(
                                ModifySimulationPane.this,
                                message,
                                "Simulation Created",
                                JOptionPane.INFORMATION_MESSAGE);

                        // Reset fields and simulation ID display to blank
                        simulationNameField.setText("");
                        numberOfSprintsField.setValue(0);
                        durationOfSprintsField.setValue(0);
                    }
                });

        panel.add(
                submitButton,
                new CustomConstraints(
                        0, 5, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        add(panel);

        JScrollPane scrollPane = new JScrollPane(sprintList);
        scrollPane.setPreferredSize(new Dimension(300, 100));

        JLabel userStoriesLabel = new JLabel("Sprints:");
        panel.add(
                userStoriesLabel,
                new CustomConstraints(
                        0, 4, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL));

        panel.add(
                sprintList,
                new CustomConstraints(
                        1, 4, GridBagConstraints.WEST, 1.0, 0.0, GridBagConstraints.NONE));
    }
}
