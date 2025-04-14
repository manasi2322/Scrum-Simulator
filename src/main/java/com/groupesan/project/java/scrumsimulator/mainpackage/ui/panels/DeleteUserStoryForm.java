package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStoryStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

import javax.swing.*;
import java.awt.*;

public class DeleteUserStoryForm extends JFrame implements BaseComponent{
    
    public DeleteUserStoryForm()
    {
        this.init();
    }

    public void init()
    {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Delete User Story");
        setSize(350, 150);
        setMinimumSize(new Dimension(350, 150));
        
        GridBagLayout myGridbagLayout = new GridBagLayout();
        setLayout(myGridbagLayout);
        JPanel myJpanel = new JPanel();
        myJpanel.setLayout(myGridbagLayout);

        JLabel nameLabel = new JLabel("User Story: ");
        JComboBox<String> userStoryComboBox = new JComboBox<>();
        for(UserStory userStory: UserStoryStore.getInstance().getUserStories()){
            userStoryComboBox.addItem(userStory.getName());
        }
        
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(
            e -> {
                String userStoryName = (String) userStoryComboBox.getSelectedItem();
                UserStoryStore.getInstance().deleteUerStory(userStoryName);
                this.dispose();
            });


        myJpanel.add (
            nameLabel,
            new CustomConstraints(0, 0, GridBagConstraints.WEST, 0.5, 0.2, GridBagConstraints.HORIZONTAL));
        
        myJpanel.add(
            userStoryComboBox,
            new CustomConstraints(1, 0, GridBagConstraints.EAST, 0.5, 0.2, GridBagConstraints.HORIZONTAL));
        
        add(
            deleteButton,
            new CustomConstraints(0, 1, GridBagConstraints.SOUTH, 1, 0, GridBagConstraints.HORIZONTAL));
        
        add(
            myJpanel,
            new CustomConstraints(0, 0, GridBagConstraints.WEST, 0, 0, GridBagConstraints.HORIZONTAL));
    }
}
