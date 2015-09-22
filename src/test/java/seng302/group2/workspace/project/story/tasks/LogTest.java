package seng302.group2.workspace.project.story.tasks;

import org.junit.Assert;
import org.junit.Test;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.story.Story;

import java.time.LocalDateTime;

public class LogTest {
    Log log = new Log();
    /**
     * Test Log constructors
     */
    @Test
    public void testLogConstructors() throws Exception {
        Story story = new Story();
        Person aPerson = new Person();
        Task task = new Task("test task", "", story, aPerson, 0);
        task.setEffortLeft(600);
        story.add(task);
        
        
        Assert.assertEquals("", log.getDescription());
        Assert.assertEquals(0, log.getDurationInMinutes(), 0);
        Assert.assertEquals(0, log.getDurationInHours(), 0);

        Log log1 = new Log(task, "A new Log", aPerson, new Person(), 122, LocalDateTime.now(), 122);
        Assert.assertEquals("A new Log", log1.getDescription());
        Assert.assertEquals(122, log1.getDurationInMinutes(), 0);
        Assert.assertEquals(2, log1.getDurationInHours(), 0.5);
        Assert.assertEquals(LocalDateTime.now().toLocalDate(), log1.getStartDate().toLocalDate());
        Assert.assertEquals(task, log1.getTask());
    }
    
    /**
     * Tests the GetDurationInHours Method in the log class.
     */
    @Test
    public void testGetDurationInHours() throws Exception {
        Log log = new Log();
        log.setDuration("2 hours 30 mins");
        Assert.assertTrue(log.getDurationInHours() == 2.5);
    }

    /**
     * Tests the GetDurationInMinutes Method in the log class.
     */
    @Test
    public void testGetDurationInMinutes() throws Exception {
        Log log = new Log();
        log.setDuration("2 hours 30 mins");
        Assert.assertTrue(log.getDurationInMinutes() == 150);
    }

    /**
     * Tests the GetDurationString Method in the log class.
     */
    @Test
    public void testGetDurationString() throws Exception {
        Log log = new Log();
        log.setDuration("2 hours 30 mins");
        Assert.assertEquals("2h 30min", log.getDurationString());
    }

    //ToDo: Update testEditLog




    /**
     * Tests the DeleteLogs Method in the log class.
     */
    @Test
    public void testDeleteLog() throws Exception {
        Person aPerson = new Person();

        Project proj = new Project("A new Project", "Proj", "Proj");
        Story story = new Story();

        Task task = new Task("test task", "", story, aPerson, 0);
        task.setEffortLeft(600);

        story.add(task);
        proj.add(story);
        story.setProject(proj);

        Log log1 = new Log(task, "", aPerson, new Person(), 10, LocalDateTime.now(), 40);
        Log log2 = new Log(task, "", aPerson, new Person(), 10, LocalDateTime.now(), 40);
        Log log3 = new Log(task, "", aPerson, new Person(), 10, LocalDateTime.now(), 40);

        proj.add(log1);
        proj.add(log2);
        proj.add(log3);
        System.out.println(task.getEffortLeft());
        log1.deleteLog();
        Assert.assertTrue(!proj.getLogs().contains(log1));
        Assert.assertEquals(20, task.getEffortSpent(), 0);
        System.out.println(task.getEffortLeft());

    }

    /**
     * Tests the GenerateXML Method in the log class.
     * Should remain null for now as logs don't need to be in reports.
     */
    @Test
    public void testGenerateXML() throws Exception {
        Person aPerson = new Person();
        Story story = new Story();
        Task task = new Task("test task", "", story, aPerson, 0);
        task.setEffortLeft(600);
        story.add(task);

        Log log = new Log(task, "", aPerson, new Person(), 40, LocalDateTime.now(), 40);
        Assert.assertEquals(null, log.generateXML());
        
    }
}