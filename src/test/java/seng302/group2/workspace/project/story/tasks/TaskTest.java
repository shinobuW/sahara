package seng302.group2.workspace.project.story.tasks;

import javafx.collections.ObservableList;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Element;
import seng302.group2.Global;
import seng302.group2.util.reporting.ReportGenerator;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.sprint.Sprint;
import seng302.group2.workspace.project.story.Story;

import static javafx.collections.FXCollections.observableArrayList;

public class TaskTest {
    Task task = new Task();

    /**
     * Test for the task constructors
     */
    @Test
    public  void testTaskConstructors() {
        Story story_ = new Story();
        Assert.assertEquals("Untitled Task", task.getShortName());
        Assert.assertEquals("", task.getDescription());
        Assert.assertEquals("", task.getImpediments());
        Assert.assertEquals("Untitled Task", task.toString());

        Task testTask = new Task("Test Task", "test description", story_, null);
        Assert.assertEquals("Test Task", testTask.getShortName());
        Assert.assertEquals("test description", testTask.getDescription());
        Assert.assertEquals("", testTask.getImpediments());
        Assert.assertEquals(Task.TASKSTATE.NOT_STARTED, testTask.getState());
        Assert.assertEquals(story_, testTask.getStory());
    }

    /**
     * Test Task setters
     */
    @Test
    public void testTaskSetters() {
        task.setShortName("Test Task");
        task.setDescription("Test Description");
        task.setImpediments("Test Impediments");
        task.setState(Task.TASKSTATE.IN_PROGRESS);

        Assert.assertEquals("Test Task", task.getShortName());
        Assert.assertEquals("Test Description", task.getDescription());
        Assert.assertEquals("Test Impediments", task.getImpediments());
        Assert.assertEquals(Task.TASKSTATE.IN_PROGRESS, task.getState());
    }

    @Test
    public void testComparators() {
        Task defaultTask = new Task();

        //Short name comparator
        task.setShortName("a task");
        Assert.assertTrue(0 < Task.TaskNameComparator.compare(task, defaultTask));
    }


    @Test
    public void testDeleteTask() {
        Project project = new Project();
        Backlog backlog = new Backlog();
        Sprint sprint = new Sprint();
        Story story = new Story();
        project.add(backlog);
        backlog.add(story);

        Task task = new Task("short", "desc", story, null);
        Task task2 = new Task("short2", "desc",  null, null);

        story.add(task);
        sprint.getUnallocatedTasks().add(task2);

        Assert.assertTrue(story.getTasks().contains(task));
        Assert.assertTrue(sprint.getUnallocatedTasks().contains(task2));

        task.deleteTask();
        task2.deleteTask();

        Assert.assertFalse(story.getTasks().contains(task));
        Assert.assertFalse(sprint.getUnallocatedTasks().contains(task));

        Global.commandManager.undo();
        Global.commandManager.undo();

        Assert.assertTrue(story.getTasks().contains(task));
        Assert.assertTrue(sprint.getUnallocatedTasks().contains(task2));
    }


    /**
     * Test Task state methods
     */
    @Test
    public void testTaskStates() {
        Task task = new Task();

        Assert.assertEquals(Task.TASKSTATE.NOT_STARTED, task.getState());

        task.setState(Task.TASKSTATE.IN_PROGRESS);
        Assert.assertEquals(Task.TASKSTATE.IN_PROGRESS, task.getState());

        task.setState(Task.TASKSTATE.BLOCKED);
        Assert.assertEquals(Task.TASKSTATE.BLOCKED, task.getState());

        task.setState(Task.TASKSTATE.DONE);
        Assert.assertEquals(Task.TASKSTATE.DONE, task.getState());

        task.setState(Task.TASKSTATE.DEFERRED);
        Assert.assertEquals(Task.TASKSTATE.DEFERRED, task.getState());

    }

    /**
     * Tests for Task XML generator method.
     */
    @Test
    public void testGenerateXML() {
        new ReportGenerator();
        ObservableList<Person> people = observableArrayList();
        Task story = new Task("short", "desc", null, people);

        Element storyElement = story.generateXML();
        Assert.assertEquals("[#text: short]", storyElement.getChildNodes().item(1).getChildNodes().item(0).toString());
        Assert.assertEquals("[#text: desc]", storyElement.getChildNodes().item(2).getChildNodes().item(0).toString());

    }
}