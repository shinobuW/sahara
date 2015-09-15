package seng302.group2.workspace.project.story.tasks;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import seng302.group2.Global;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.workspace.Workspace;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PairLogTest extends TestCase {

    Workspace ws = new Workspace();
    Project proj = new Project();
    Story story = new Story("Story", "Long Story", "description", "Creator", proj, 2);
    Task task = new Task("Task", "description", story, new Person(), 20 );
    Person originalLogger = new Person("Logger", "Logger", "Logger", "", "", LocalDate.now());
    Person originalPartner = new Person("Partner", "Partner", "Partner", "", "", LocalDate.now());
    PairLog pLog = new PairLog(task,  "description", originalLogger, originalPartner, 0,
            LocalDateTime.now(), 0);

    @BeforeClass
    public void setupTest() {
        Global.currentWorkspace = ws;
        ws.add(proj);
        proj.add(story);
        proj.add(pLog);
    }


    /**
     * Tests the main PairLog edit command
     */
    @Test
    public void testMainEditCommandLog() {
        Person testPerson = new Person("Bron", "Bronson Taryn", "McNaughton", "swagger@", "alpha", LocalDate.now());
        pLog.edit(testPerson, pLog.getPartner(), LocalDateTime.now(), 20, "", 0);
        Assert.assertEquals(testPerson, pLog.getLogger());
        Global.commandManager.undo();
        Assert.assertEquals(originalLogger, pLog.getLogger());
    }


    /**
     * Test the PairLog partner edit method
     */
    @Test
    public void TestPartnerEditCommandLog() {
        Person partner = new Person("Cam", "Cameron", "Williams", "", "what's for dinner", LocalDate.now());
        pLog.editPartner(partner);
        Assert.assertEquals(partner, pLog.getPartner());
        Global.commandManager.undo();
        Assert.assertEquals(originalPartner, pLog.getPartner());
    }


    /**
     * Tests the logger edit method
     */
    @Test
    public void TestLoggerEditCommand() {
        Person logger = new Person("Jd", "Jordane", "Lew", "", "Just a boy", LocalDate.now());
        pLog.editLogger(logger);
        Assert.assertEquals(logger, pLog.getLogger());
        Global.commandManager.undo();
        Assert.assertEquals(originalLogger, pLog.getLogger());
    }


    /**
     * Tests the description edit method
     */
    @Test
    public void TestDescriptionEditCommand() {
        String desc = new String("New Description");
        pLog.editDescription(desc);
        Assert.assertEquals(desc, pLog.getDescription());
        Global.commandManager.undo();
        Assert.assertEquals("", pLog.getDescription());
    }


    /**
     * Tests the duration edit method
     */
    @Test
    public void TestDurationDescriptionCommand() {
        pLog.editDuration(10);
        Assert.assertEquals(10, pLog.getDurationInHours());
        Global.commandManager.undo();
        Assert.assertEquals(20, pLog.getDurationInHours());
    }

    //TODO: Shnob update
//    /**
//     * Tests the delete method
//     */
//    @Test
//    public void testDeleteLog() {
//        pLog.deleteLog();
//        Assert.assertTrue(task.getLogs().size() == 0);
//        Global.commandManager.undo();
//        Assert.assertTrue(task.getLogs().contains(pLog));
//    }
}