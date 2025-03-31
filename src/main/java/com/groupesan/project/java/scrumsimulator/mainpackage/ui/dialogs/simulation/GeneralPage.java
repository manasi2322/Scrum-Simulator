package com.groupesan.project.java.scrumsimulator.mainpackage.ui.dialogs.simulation;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import com.groupesan.project.java.scrumsimulator.mainpackage.ui.utils.DataModel;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.utils.GridBagConstraintsBuilder;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.ResuableHeader;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.SpinnerInput;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.TextInput;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.Wizard;

class GeneralPage extends Wizard.WizardPage {
    private final DataModel<String> simulationModel;
    private final DataModel<Object> sprintModel;
    private final DataModel<Object> sprintDuration;

    public GeneralPage(DataModel<String> simulationModel, DataModel<Object> sprintModel, DataModel<Object> sprintDuration) {
        this.simulationModel = simulationModel;
        this.sprintModel = sprintModel;
        this.sprintDuration = sprintDuration;
    }

    @Override
    protected String getId() {
        return "General";
    }

    @Override
    protected JPanel render() {
        JPanel container = new JPanel(new BorderLayout());
        ResuableHeader resuableHeader =
                new ResuableHeader("General", "General simulation settings");

        JPanel inputs = new JPanel(new GridBagLayout());
        TextInput simulationInput =
                new TextInput(
                        "Name: ", new JTextField(simulationModel.getData(), 5), simulationModel);
        SpinnerInput sprintInput =
                new SpinnerInput(
                        "Sprints: ",
                        new JSpinner(new SpinnerNumberModel(1, 1, 20, 1)),
                        sprintModel);

        SpinnerInput sprintInputDuration =
                new SpinnerInput(
                        "Sprints Duration: ",
                        new JSpinner(new SpinnerNumberModel(1, 1, 20, 1)),
                        sprintModel);

        inputs.add(
                resuableHeader,
                new GridBagConstraintsBuilder()
                        .setGridX(0)
                        .setGridY(0)
                        .setWeightX(1)
                        .setFill(GridBagConstraints.HORIZONTAL)
                        .setInsets(new Insets(0, 0, 5, 0)));
        inputs.add(
                simulationInput,
                new GridBagConstraintsBuilder()
                        .setGridX(0)
                        .setGridY(1)
                        .setWeightX(1)
                        .setFill(GridBagConstraints.HORIZONTAL));
        inputs.add(
                sprintInput,
                new GridBagConstraintsBuilder()
                        .setGridX(0)
                        .setGridY(2)
                        .setWeightX(1)
                        .setFill(GridBagConstraints.HORIZONTAL));

        inputs.add(
                sprintInput,
                new GridBagConstraintsBuilder()
                        .setGridX(0)
                        .setGridY(2)
                        .setWeightX(1)
                        .setFill(GridBagConstraints.HORIZONTAL));

        inputs.add(
                sprintInputDuration,
                new GridBagConstraintsBuilder()
                        .setGridX(0)
                        .setGridY(3)
                        .setWeightX(1)
                        .setFill(GridBagConstraints.HORIZONTAL));

        container.add(inputs, BorderLayout.NORTH);
        return container;
    }
}
