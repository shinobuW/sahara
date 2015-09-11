package seng302.group2.workspace.project.story.tasks;

import junit.framework.TestCase;
import org.junit.Test;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.story.Story;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PairLogTest extends TestCase {
    Story story = new Story("Story", "Long Story", "description", "Creator", new Project(), 2);
    Task task = new Task("Task", "description", story, new Person(), 20 );
    Person logger = new Person("Logger", "Logger", "Logger", "", "", LocalDate.now());
    Person partner = new Person("Partner", "Partner", "Partner", "", "", LocalDate.now());
    PairLog pLog = new PairLog(task,  "description", logger, partner, 0,
                   LocalDateTime.now(), 0);



    @Test
    public void testEditCommandLog() {


    }

    @Test
    public void testDeleteCommandLog() throws Exception {

    }
}