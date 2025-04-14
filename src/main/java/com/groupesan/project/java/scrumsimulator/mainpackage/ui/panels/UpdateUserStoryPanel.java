package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStoryStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.state.UserStoryStateManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.List;

public class UpdateUserStoryPanel extends JFrame {

    public UpdateUserStoryPanel() {
        init();
    }

    private void init() {
        setTitle("Update User Story Status");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        placeComponents(panel);
        add(panel);

        setLocationRelativeTo(null);
    }

    private String getSelectedStoryStatus(String userStoryName){
        List<UserStory> userStories = UserStoryStore.getInstance().getUserStories();
        for(UserStory userStory: userStories){
            if(userStory.getName().equals(userStoryName)){
                return userStory.getStatus();
            }
        }
        return null;
    }

    private UserStory getUpdatedUserStory(String userStoryName, String userStoryStatus){
        List<UserStory> userStories = UserStoryStore.getInstance().getUserStories();
        UserStory updatedUs = null;
        for(UserStory userStory: userStories){
            if(userStory.getName().equals(userStoryName)){
                updatedUs = userStory;
                updatedUs.setStatus(userStoryStatus);
            }
        }

        return updatedUs;

    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userStoryLabel = new JLabel("Select User Story:");
        userStoryLabel.setBounds(10, 20, 120, 25);
        panel.add(userStoryLabel);

        //List<String> userStories = UserStoryStateManager.getUserStories();
        JComboBox<String> userStoryComboBox = new JComboBox<>();

        List<UserStory> userStories =UserStoryStore.getInstance().getUserStories();
        for(UserStory userStory: userStories){
            userStoryComboBox.addItem(userStory.getName());
            userStoryComboBox.setName( String.valueOf(userStory.getStandardId()));
        }

        String userStoryName = (String) userStoryComboBox.getSelectedItem();
        String userStoryStatus = getSelectedStoryStatus(userStoryName);



        userStoryComboBox.setBounds(150, 20, 200, 25);
        panel.add(userStoryComboBox);

        JLabel statusLabel = new JLabel("Select Status:");
        statusLabel.setBounds(10, 50, 120, 25);
        panel.add(statusLabel);

        String[] statusOptions = {"todo", "new", "in progress", "ready for test", "completed", "blocked"};
        JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);
        int statusIndex = Arrays.asList(statusOptions).indexOf(userStoryStatus);

        statusComboBox.setSelectedIndex(statusIndex);
        statusComboBox.setBounds(150, 50, 200, 25);
        panel.add(statusComboBox);
        
        userStoryComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedUserStory = e.getItem().toString();
                    String selectedStoryStatus = getSelectedStoryStatus(selectedUserStory);

                    if (selectedStoryStatus != null) {
                        statusComboBox.setSelectedItem(selectedStoryStatus);
                    }
                    else {
                        JOptionPane.showMessageDialog(
                                    null, "Invalid Status read");
                    }
                }
            }
        });

        JButton updateButton = new JButton("Update Status");
        updateButton.setBounds(150, 80, 150, 25);
        panel.add(updateButton);

        updateButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String selectedUserStory = (String) userStoryComboBox.getSelectedItem();
                        String selectedStatus = (String) statusComboBox.getSelectedItem();

                        UserStory us = getUpdatedUserStory(selectedUserStory, selectedStatus);

                        if (selectedUserStory != null && selectedStatus != null) {
                            UserStoryStore.getInstance().updateUserStoryByName(selectedUserStory, us);
                            UserStoryStateManager.updateUserStoryStatus(
                                    selectedUserStory, selectedStatus);
                            JOptionPane.showMessageDialog(null, "Status updated successfully!");
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(
                                    null, "Please select a User Story and Status");
                        }
                    }
                });
    }
}
