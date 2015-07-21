/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.workspace.project.story;


import javafx.scene.paint.Color;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Element;
import seng302.group2.Global;
import seng302.group2.util.reporting.ReportGenerator;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.story.acceptanceCriteria.AcceptanceCriteria;
import seng302.group2.workspace.project.story.estimation.EstimationScalesDictionary;

import java.util.ArrayList;

/**
 * A series of tests relating to Story
 *
 * @author swi67
 */
public class StoryTest {
    Story story = new Story();
    AcceptanceCriteria ac = new AcceptanceCriteria("requirement", story);
    ArrayList<AcceptanceCriteria> acList = new ArrayList<>();

    /**
     * Test for the story constructors
     */
    @Test
    public void testStoryConstructors() {
        Project project = new Project();
        Assert.assertEquals("Untitled Story", story.getShortName());
        Assert.assertEquals("", story.getDescription());
        Assert.assertEquals("Untitled Story", story.toString());
        Assert.assertEquals(null, story.getCreator());

        Story testStory = new Story("Test Story", "A long Name", "test description",
                "Tyler the Creator", project, 1);
        Assert.assertEquals("Test Story", testStory.getShortName());
        Assert.assertEquals("A long Name", testStory.getLongName());
        Assert.assertEquals("test description", testStory.getDescription());
        Assert.assertEquals(1, testStory.getPriority(), 0);
        Assert.assertEquals("Tyler the Creator", testStory.getCreator());
        Assert.assertEquals("Test Story", testStory.toString());
        Assert.assertEquals(project, testStory.getProject());

        Assert.assertNull(testStory.getChildren());
    }

    /**
     * Test Story setters
     */
    @Test
    public void testStorySetters() {
        story.setShortName("Test Story");
        story.setLongName("Test Long Name");
        story.setDescription("description");
        story.setPriority(5);
        story.setCreator("Shinobu");

        Assert.assertEquals("Test Story", story.getShortName());
        Assert.assertEquals("Test Long Name", story.getLongName());
        Assert.assertEquals("description", story.getDescription());
        Assert.assertEquals(5, story.getPriority(), 0);
        Assert.assertEquals("Shinobu", story.getCreator());
    }


    @Test
    public void testComparators() {
        Story defaultStory = new Story();

        // Short name comparator
        story.setShortName("a story");
        Assert.assertTrue(0 < Story.StoryNameComparator.compare(story, defaultStory));

        // Priority comparator
        story.setPriority(1);
        defaultStory.setPriority(5);
        Assert.assertTrue(0 < Story.StoryPriorityComparator.compare(story, defaultStory));
    }


    @Test
    public void testDeleteStory() {
        Project proj = new Project();
        Backlog back = new Backlog();
        proj.add(back);

        Story story1 = new Story("short", "long", "desc", "creator", null, 5);
        Story story2 = new Story("short2", "long", "desc", "creator", null, 5);
        story1.setProject(proj);
        story2.setProject(proj);
        story2.setBacklog(back);

        proj.add(story1);
        back.add(story2);

        Assert.assertTrue(back.getStories().contains(story2));
        Assert.assertTrue(proj.getUnallocatedStories().contains(story1));

        story1.deleteStory();
        story2.deleteStory();

        Assert.assertFalse(back.getStories().contains(story2));
        Assert.assertFalse(proj.getUnallocatedStories().contains(story1));

        Global.commandManager.undo();
        Global.commandManager.undo();

        Assert.assertTrue(back.getStories().contains(story2));
        Assert.assertTrue(proj.getUnallocatedStories().contains(story1));
    }


    /**
     * Tests that a story properly prepares for serialization
     */
    @Test
    public void testPrepSerialization() {
        story.getSerializableAc().clear();
        story.getAcceptanceCriteria().clear();

        story.getAcceptanceCriteria().add(ac);
        story.prepSerialization();

        acList.add(ac);

        Assert.assertEquals(acList, story.getSerializableAc());

    }

    /**
     * Tests that a story properly post-pares after deserialization
     */
    @Test
    public void testPostSerialization() {
        story.getSerializableAc().clear();
        story.getAcceptanceCriteria().clear();
        story.getSerializableAc().add(ac);
        story.postSerialization();

        acList.add(ac);
        Assert.assertEquals(acList, story.getAcceptanceCriteria());
    }


    /**
     * Test undo/redo for adding Acceptance Criteria
     */
    @Test
    public void testAdd() {
        story.add(ac);
        Assert.assertTrue(story.getAcceptanceCriteria().contains(ac));
        Global.commandManager.undo();
        Assert.assertTrue(story.getAcceptanceCriteria().size() == 0);
        Global.commandManager.redo();
        Assert.assertTrue(story.getAcceptanceCriteria().contains(ac));
    }


    /**
     * Test Ready and Ready State methods
     */
    @Test
    public void testReadyState() {
        Story story = new Story();
        Assert.assertFalse(story.getReady());
        Assert.assertEquals(Story.stateNotReady, story.getReadyState());

        story.setReady(true);
        Assert.assertTrue(story.getReady());
        Assert.assertEquals(Story.stateReady, story.getReadyState());

        story.setReady(false);
        Assert.assertFalse(story.getReady());
        Assert.assertEquals(Story.stateNotReady, story.getReadyState());
    }


    /**
     * Test AC interaction
     */
    @Test
    public void testAcInteraction() {
        Story story = new Story();
        Assert.assertTrue(story.getAcceptanceCriteria().size() == 0);

        AcceptanceCriteria ac1 = new AcceptanceCriteria("AC1", story);
        AcceptanceCriteria ac2 = new AcceptanceCriteria("AC2", story);

        story.add(ac1);
        story.add(ac2);
        Assert.assertTrue(story.getAcceptanceCriteria().size() == 2);
        Assert.assertTrue(story.getAcceptanceCriteria().contains(ac1) && story.getAcceptanceCriteria().contains(ac2));

        story.delete(ac2);
        Assert.assertTrue(story.getAcceptanceCriteria().size() == 1);
        Assert.assertTrue(story.getAcceptanceCriteria().contains(ac1) && !story.getAcceptanceCriteria().contains(ac2));
    }

    /**
     * Tests for Roles' XML generator method.
     */
    @Test
    public void testGenerateXML() {
        new ReportGenerator();
        Story story = new Story("short", "long", "desc", "creator", null, 5);
        story.setEstimate("10");

        Element storyElement = story.generateXML();
        Assert.assertEquals("[#text: short]", storyElement.getChildNodes().item(1).getChildNodes().item(0).toString());
        Assert.assertEquals("[#text: long]", storyElement.getChildNodes().item(2).getChildNodes().item(0).toString());
        Assert.assertEquals("[#text: desc]", storyElement.getChildNodes().item(3).getChildNodes().item(0).toString());
        Assert.assertEquals("[#text: creator]", storyElement.getChildNodes().item(4).getChildNodes().item(0).toString());
        Assert.assertEquals("[#text: 5]", storyElement.getChildNodes().item(5).getChildNodes().item(0).toString());
        Assert.assertEquals("[#text: 10]", storyElement.getChildNodes().item(6).getChildNodes().item(0).toString());
        Assert.assertEquals("[#text: Not Ready]", storyElement.getChildNodes().item(7).getChildNodes().item(0).toString());
        Assert.assertEquals(0, storyElement.getChildNodes().item(8).getChildNodes().getLength());
        Assert.assertEquals(9, storyElement.getChildNodes().getLength());

    }

    /**
     * Tests the highlightColour and getColour methods
     */
    @Test
    public void testColourMethods() {
        Assert.assertEquals("transparent", story.getColour());

        story.setPriority(200);
        Story lowPriorityStory = new Story();
        lowPriorityStory.setPriority(100);
        story.addDependentOnThis(lowPriorityStory);
        Assert.assertEquals("transparent", story.getColour()); //red

        story.setPriority(3);
        story.getAcceptanceCriteria().add(ac);
        story.setEstimate(EstimationScalesDictionary.getScaleValue(EstimationScalesDictionary.DefaultValues.NONE));
        Assert.assertEquals(Story.toRGBCode(Story.orangeHighlight), story.getColour()); // orange

        story.setReady(true);
        Assert.assertEquals(Story.toRGBCode(Story.greenHighlight), story.getColour()); //green
    }

    @Test
    public void testToRGBCode() {
        Assert.assertEquals("#FF0000FF", Story.toRGBCode(Color.RED));
        Assert.assertEquals("#008000FF", Story.toRGBCode(Color.GREEN));
        Assert.assertEquals("#0000FFFF", Story.toRGBCode(Color.BLUE));
        Assert.assertEquals("#80808080", Story.toRGBCode(Color.color(128/255.0, 128/255.0, 128/255.0, 128/255.0)));
    }
}
