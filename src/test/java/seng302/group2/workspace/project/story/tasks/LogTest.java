package seng302.group2.workspace.project.story.tasks;

import org.junit.Assert;
import org.junit.Test;
import seng302.group2.Global;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.story.Story;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LogTest {
    Log log = new Log();
    /**
     * Test Log constructors
     */
    @Test
    public void testLogConstructors() {
        Story story = new Story();
        Person aPerson = new Person();
        Task task = new Task("test task", "", story, aPerson, 0);
        task.setEffortLeft(600);
        story.add(task);
        
        
        Assert.assertEquals("", log.getDescription());
        Assert.assertEquals(0, log.getDurationInMinutes(), 0);
        Assert.assertEquals(0, log.getDurationInHours(), 0);

        Log log1 = new Log(task, "A new Log", aPerson, 122, LocalDateTime.now(), 122);
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

    /**
     * Tests the Edit logs command of the Log class.
     */
    @Test
    public void testEditLog() {
        Person aPerson = new Person();

        Project proj = new Project("A new Project", "Proj", "Proj");
        Story story = new Story();

        Task task = new Task("test task", "", story, aPerson, 0);
        task.setEffortLeft(600);

        story.add(task);
        proj.add(story);
        story.setProject(proj);

        Person person = new Person();

        LocalDateTime date = LocalDateTime.now();

        Log log = new Log(task, "", aPerson, 40, date, 40);
        task.add(log, 560);

        LocalDateTime newDate = LocalDateTime.now();
        log.edit(person, newDate, 50, "Desc", 550);
        Assert.assertEquals(person, log.getLogger());
        Assert.assertEquals(newDate, log.getStartDate());
        Assert.assertEquals(50, log.getDurationInMinutes(), 0);
        Assert.assertEquals("Desc", log.getDescription());
        Assert.assertEquals(560, task.getEffortLeft(), 0);
        Assert.assertEquals(50, task.getEffortSpent(), 0);
        Global.commandManager.undo();
        Assert.assertEquals(aPerson, log.getLogger());
        Assert.assertEquals(date, log.getStartDate());
        Assert.assertEquals(40, log.getDurationInMinutes(), 0);
        Assert.assertEquals("", log.getDescription());
        Assert.assertEquals(560, task.getEffortLeft(), 0);
        Assert.assertEquals(40, task.getEffortSpent(), 0);
        Global.commandManager.redo();
        Assert.assertEquals(person, log.getLogger());
        Assert.assertEquals(newDate, log.getStartDate());
        Assert.assertEquals(50, log.getDurationInMinutes(), 0);
        Assert.assertEquals("Desc", log.getDescription());
        Assert.assertEquals(560, task.getEffortLeft(), 0);
        Assert.assertEquals(50, task.getEffortSpent(), 0);
    }

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

        Log log1 = new Log(task, "", aPerson, 40, LocalDateTime.now(), 40);
        Log log2 = new Log(task, "", aPerson, 70, LocalDateTime.now(), 40);
        Log log3 = new Log(task, "", aPerson, 90, LocalDateTime.now(), 40);

        task.add(log1, 560);
        task.add(log2, 490);
        task.add(log3, 400);
        Assert.assertEquals(3, task.getLogs().size());
        Assert.assertEquals(400, task.getEffortLeft(), 0);
        Assert.assertEquals(200, task.getEffortSpent(), 0);

        log1.deleteLog();
        Assert.assertEquals(2, task.getLogs().size());
        Assert.assertEquals(400, task.getEffortLeft(), 0);
        Assert.assertEquals(160, task.getEffortSpent(), 0);
        Global.commandManager.undo();
        Assert.assertEquals(3, task.getLogs().size());
        Assert.assertEquals(400, task.getEffortLeft(), 0);
        Assert.assertEquals(200, task.getEffortSpent(), 0);
        Global.commandManager.redo();
        Assert.assertEquals(2, task.getLogs().size());
        Assert.assertEquals(400, task.getEffortLeft(), 0);
        Assert.assertEquals(160, task.getEffortSpent(), 0);
        
        log3.deleteLog();
        Assert.assertEquals(1, task.getLogs().size());
        Assert.assertEquals(400, task.getEffortLeft(), 0);
        Assert.assertEquals(70, task.getEffortSpent(), 0);
        
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

        Log log = new Log(task, "", aPerson, 40, LocalDateTime.now(), 40);
        Assert.assertEquals(null, log.generateXML());
        
    }
}