package com.groupesan.project.java.scrumsimulator.mainpackage.state;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Sprint;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintStore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SprintStateTest {

    private SprintStore sprintStore;

    @BeforeEach
    public void setUp() {
        sprintStore = new SprintStore();
        List<Sprint> sprints = new ArrayList<>();
        sprintStore.updateSprints(sprints);
    }

    @AfterEach
    public void tearDown() {
        sprintStore = null;
    }

    @Test
    public void testInitialState() {
        List<Sprint> sprints = sprintStore.getSprints();
        assert(sprints.isEmpty());
    }

    @Test
    public void testAddSprint() {
        Sprint sprint = new Sprint("test", "desc", 1, 100);
        sprintStore.addSprint(sprint);
        List<Sprint> sprints = sprintStore.getSprints();
        System.out.println(sprints.size());
        assert(sprints.size() == 1);
    }


}
