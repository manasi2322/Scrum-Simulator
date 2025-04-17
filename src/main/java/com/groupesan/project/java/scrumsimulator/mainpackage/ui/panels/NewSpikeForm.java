package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;


import com.groupesan.project.java.scrumsimulator.mainpackage.impl.*;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;




public class NewSpikeForm extends JFrame implements BaseComponent {




   JTextField nameField = new JTextField();
   JTextArea descArea = new JTextArea();
   SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(5, 1, 999999, 1);
   JSpinner sprintDays = new JSpinner(spinnerNumberModel);
   JComboBox spikeuserStoryComboBox = new JComboBox();

    Double[] minimumRangeList = {10.0, 20.0, 30.0, 40.0, 50.0, 60.0, 70.0, 80.0, 90.0, 100.0};

    Double[] maximumRangeList = {10.0, 20.0, 30.0, 40.0, 50.0, 60.0, 70.0, 80.0, 90.0, 100.0};

    private JComboBox<Double> minimumRangeCombo = new JComboBox<>(minimumRangeList);

    private JComboBox<Double> maximumRangeCombo = new JComboBox<>(maximumRangeList);


    DefaultListModel<String> listModel;
   JList<String> usList;


   ArrayList<String> selectedItems = new ArrayList<>();




   public NewSpikeForm() {
       this.init();
   }


   public void init() {
       setTitle("New Spike");
       setSize(400, 300);

       
       GridBagLayout myGridbagLayout = new GridBagLayout();
       JPanel myJpanel = new JPanel();
       myJpanel.setBorder(new EmptyBorder(10, 10, 10, 10));
       myJpanel.setLayout(myGridbagLayout);



       JLabel nameLabel = new JLabel("Name:");

       myJpanel.add(
               nameLabel,
               new CustomConstraints(
                       0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
       myJpanel.add(
               nameField,
               new CustomConstraints(
                       1, 0, GridBagConstraints.EAST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));



       JLabel descLabel = new JLabel("Description:");
       myJpanel.add(
               descLabel,
               new CustomConstraints(
                       0, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL));
       myJpanel.add(
               new JScrollPane(descArea),
               new CustomConstraints(
                       1, 1, GridBagConstraints.EAST, 1.0, 0.3, GridBagConstraints.BOTH));
       JLabel userStoriesLabel = new JLabel("User Stories:");
       for(UserStory userStory: UserStoryStore.getInstance().getUserStories())
       { 
                   if(Objects.equals(userStory.getStatus(), "blocked") || Objects.equals(userStory.getStatus(), "Blocked")){
                   spikeuserStoryComboBox.addItem(userStory.getName()+"     :"+userStory.getStandardId());
                   }
              
       }
       myJpanel.add(
               userStoriesLabel,
               new CustomConstraints(
                       0, 3, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL));


       //JComboBox<String> userStoryComboBox = new JComboBox<>();
       myJpanel.add(
               spikeuserStoryComboBox,
               new CustomConstraints(
                       1, 3, GridBagConstraints.EAST, 1.0, 0.0, GridBagConstraints.BOTH));
//added empty spike button, added a small dialogbox for now.
       JLabel minProbabilityLabel = new JLabel("Minimum Probability:");
       JLabel maxProbabilityLabel = new JLabel("Maximum Probability:");
       myJpanel.add(
               minProbabilityLabel,
               new CustomConstraints(
                       0, 4, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));


       myJpanel.add(
               minimumRangeCombo,
               new CustomConstraints(
                       1, 4, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));




       myJpanel.add(
               maxProbabilityLabel,
               new CustomConstraints(
                       0, 5, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));

       myJpanel.add(
               maximumRangeCombo,
               new CustomConstraints(
                       1, 5, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));




       JButton spikeButton = new JButton("Spike");
   
           spikeButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String spikeName = nameField.getText();
                        String spikeDescription = descArea.getText();
                        String blockedUserStory = (String) spikeuserStoryComboBox.getSelectedItem();

                        Double minProbability = (Double) minimumRangeCombo.getSelectedItem();
                        Double maxProbability = (Double) maximumRangeCombo.getSelectedItem();


                        String blockedUSId = blockedUserStory.split(":")[1];



                        Spike spike = SpikeStoryFactory.getSprintFactory().createNewSpike(spikeName, spikeDescription, blockedUSId, minProbability, maxProbability);

                        if (spike != null ) {
                            if (minProbability <= maxProbability){
                                SpikeStore.getInstance().addSpike(spike);
                                dispose();
                            }
                            else {
                                JOptionPane.showMessageDialog(
                                    null, "Minimum probability should not be greater than Maximum probability");
                            }
                        } else {
                            JOptionPane.showMessageDialog(
                                    null, "Please enter Name, Description");
                        }
                    }
                });
       spikeButton.setSize(4000, 20);
       myJpanel.add(
           spikeButton,
           new CustomConstraints(0, 6, GridBagConstraints.SOUTH, 1.0, 0.0, GridBagConstraints.HORIZONTAL));
       add(myJpanel);
   }
}

