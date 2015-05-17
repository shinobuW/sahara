package seng302.group2.workspace.person;

import javafx.collections.ObservableList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by Jordane on 27/04/2015.
 */
public class PersonTest
{

    // Build some test objects for test operations
    Team unassignedTeam = null;

    Team testTeam = new Team();
    Skill testSkill = new Skill();
    Role testRole = new Role();

    LocalDate testDate;
    LocalDate testDate2;

    Person basicPerson = new Person();
    Person extendedPerson = new Person("btm38", "Bronson", "McNaughton", "btm38@gmail.com",
            "A really cool dude", testDate2);


    /**
     * Prepares test objects before executing the Person tests
     */
    @Before
    public void setUp()
    {
        // Find the unassigned team
        for (TreeViewItem team : Global.currentWorkspace.getTeams())
        {
            Team castedTeam = (Team) team;
            if (castedTeam.isUnassignedTeam())
            {
                unassignedTeam = castedTeam;
                break; // Only one unassigned team
            }
        }

        // Set the test date
        try
        {
            testDate = LocalDate.parse("06/03/1995", Global.dateFormatter);
            testDate2 = LocalDate.parse("19/12/1994", Global.dateFormatter);
        }
        catch (DateTimeParseException e)
        {
            Assert.fail("The date was not parsed correctly, please review:\n" + e.toString());
        }
    }

    /**
     * Tests the getter and setter for a persons short name member
     */
    @Test
    public void testGetSetShortName()
    {
        Assert.assertEquals("unnamed", basicPerson.getShortName());
        Assert.assertEquals("btm38", extendedPerson.getShortName());
        basicPerson.setShortName("newShortName");
        Assert.assertEquals("newShortName", basicPerson.getShortName());
    }

    /**
     * Tests the getter and setter for a persons first name member
     */
    @Test
    public void testGetSetFirstName()
    {
        Assert.assertEquals("firstName", basicPerson.getFirstName());
        Assert.assertEquals("Bronson", extendedPerson.getFirstName());
        basicPerson.setFirstName("newFirstName");
        Assert.assertEquals("newFirstName", basicPerson.getFirstName());
    }

    /**
     * Tests the getter and setter for a persons last name member
     */
    @Test
    public void testGetSetLastName()
    {
        Assert.assertEquals("lastName", basicPerson.getLastName());
        Assert.assertEquals("McNaughton", extendedPerson.getLastName());
        basicPerson.setLastName("newLastName");
        Assert.assertEquals("newLastName", basicPerson.getLastName());
    }

    /**
     * Tests the getter and setter for a persons email member
     */
    @Test
    public void testGetSetEmail()
    {
        Assert.assertEquals("", basicPerson.getEmail());
        Assert.assertEquals("btm38@gmail.com", extendedPerson.getEmail());
        basicPerson.setEmail("newEmail");
        Assert.assertEquals("newEmail", basicPerson.getEmail());
    }

    /**
     * Tests the getter and setter for a persons description member
     */
    @Test
    public void testGetSetDescription()
    {
        Assert.assertEquals("", basicPerson.getDescription());
        Assert.assertEquals("A really cool dude", extendedPerson.getDescription());
        basicPerson.setDescription("newDesc");
        Assert.assertEquals("newDesc", basicPerson.getDescription());
    }

    /**
     * Tests the getter and setter for a persons birth date member
     */
    @Test
    public void testGetSetBirthDate()
    {
        extendedPerson.setBirthDate(testDate2);
        Assert.assertEquals(null, basicPerson.getBirthDate());
        Assert.assertEquals("", basicPerson.getDateString());
        Assert.assertEquals(testDate2, extendedPerson.getBirthDate());

        basicPerson.setBirthDate(testDate);
        Assert.assertEquals(testDate, basicPerson.getBirthDate());
        Assert.assertEquals("06/03/1995", basicPerson.getDateString());
    }

    /**
     * Tests the getter for a persons team name
     */
    @Test
    public void testGetSetTeamName()
    {
        basicPerson.setTeam(unassignedTeam);
        Assert.assertEquals("Unassigned", basicPerson.getTeamName());
        Assert.assertEquals("Unassigned", extendedPerson.getTeamName());
        basicPerson.setTeam(testTeam);
        Assert.assertEquals(testTeam.getShortName(), basicPerson.getTeamName());
    }

    /**
     * Tests the getter and setter for a persons team member
     */
    @Test
    public void testGetSetTeam()
    {
        basicPerson.setTeam(unassignedTeam);
        Assert.assertEquals(unassignedTeam, basicPerson.getTeam());
        Assert.assertEquals(unassignedTeam, extendedPerson.getTeam());
        basicPerson.setTeam(testTeam);
        Assert.assertEquals(testTeam, basicPerson.getTeam());
    }

    /**
     * Tests the getter and setter for a persons role member
     */
    @Test
    public void testGetSetRole()
    {
        Assert.assertEquals(null, basicPerson.getRole());
        Assert.assertEquals(null, extendedPerson.getRole());
        basicPerson.setRole(testRole);
        Assert.assertEquals(testRole, basicPerson.getRole());
    }

    /**
     * Tests the getter and setter for a persons skill member
     */
    @Test
    public void testGetSetSkills()
    {
        Assert.assertEquals(observableArrayList(), basicPerson.getSkills());
        Assert.assertEquals(observableArrayList(), extendedPerson.getSkills());

        ObservableList<Skill> skills = observableArrayList();
        skills.add(testSkill);

        basicPerson.addSkillToPerson(testSkill, false);
        Assert.assertEquals(skills, basicPerson.getSkills());
    }

    /**
     * Tests that a person properly prepares for serialization
     */
    @Test
    public void testPrepSerialization()
    {
        basicPerson.getSerializableSkills().clear();
        basicPerson.getSkills().clear();
        Assert.assertEquals(new ArrayList<Skill>(), basicPerson.getSerializableSkills());
        Assert.assertEquals(new ArrayList<Skill>(), basicPerson.getSkills());

        basicPerson.addSkillToPerson(testSkill, false);
        basicPerson.prepSerialization();

        ArrayList<Skill> skills = new ArrayList<>();
        skills.add(testSkill);

        Assert.assertEquals(skills, basicPerson.getSerializableSkills());
    }

    /**
     * Tests that a person properly post-pares after deserialization
     */
    @Test
    public void testPostSerialization()
    {
        basicPerson.getSerializableSkills().clear();
        basicPerson.getSkills().clear();
        Assert.assertEquals(new ArrayList<Skill>(), basicPerson.getSerializableSkills());
        Assert.assertEquals(new ArrayList<Skill>(), basicPerson.getSkills());

        basicPerson.getSerializableSkills().add(testSkill);
        basicPerson.postSerialization();

        ArrayList<Skill> skills = new ArrayList<>();
        skills.add(testSkill);

        Assert.assertEquals(skills, basicPerson.getSkills());
    }

    /**
     * Tests that a persons skills are removed correctly
     */
    @Test
    public void testRemoveSkill()
    {
        // Clear
        basicPerson.getSkills().clear();

        // Add
        basicPerson.getSkills().add(testSkill);
        ArrayList<Skill> skills = new ArrayList<>();
        skills.add(testSkill);
        Assert.assertEquals(skills, basicPerson.getSkills());

        // Remove
        basicPerson.removeSkillFromPerson(testSkill, false);
        Assert.assertTrue(basicPerson.getSkills().isEmpty());
    }

    @Test
    public void testGetChildren() throws Exception
    {
        Assert.assertNull(basicPerson.getChildren());
    }

    @Test
    public void testToString() throws Exception
    {
        basicPerson.setShortName("unnamed");
        Assert.assertEquals("unnamed", basicPerson.toString());
        Assert.assertEquals("btm38", extendedPerson.toString());
    }
    
    /**
     * Tests the compareTo method of Person to ensure it correctly returns an int representing if a 
     * shortName is larger or not.
     */
    @Test
    public void testCompareTo()
    {
        Person zedd = new Person("z","first", "last", "", "", null);
        Person zeddDupl = new Person("Z","first", "last", "", "", null);
        Assert.assertTrue(extendedPerson.compareTo(zedd) <= 0);
        Assert.assertTrue(zedd.compareTo(extendedPerson) >= 0);
        Assert.assertTrue(zedd.compareTo(zeddDupl) == 0);
    }


    @Test
    public void testEdit()
    {
        Person person = new Person("btm38", "Bronson", "McNaughton", "btm38@gmail.com",
                "A really cool dude", testDate2);
        person.edit("shortName", "firstName",
                "lastName", "email", LocalDate.now(), "Desc", null, null);

        Assert.assertEquals("shortName", person.getShortName());
        Assert.assertEquals("firstName", person.getFirstName());
        Assert.assertEquals("lastName", person.getLastName());
        Assert.assertEquals("email", person.getEmail());
        Assert.assertEquals(LocalDate.now(), person.getBirthDate());
        Assert.assertEquals("Desc", person.getDescription());

        Global.commandManager.undo();

        Assert.assertEquals("btm38", person.getShortName());
        Assert.assertEquals("Bronson", person.getFirstName());
        Assert.assertEquals("McNaughton", person.getLastName());
        Assert.assertEquals("btm38@gmail.com", person.getEmail());
        Assert.assertEquals(testDate2, person.getBirthDate());
        Assert.assertEquals("A really cool dude", person.getDescription());
    }


    @Test
    public void testDeletePerson()
    {
        Global.commandManager.clear();
        Person person = new Person();
        Global.currentWorkspace.add(person);

        person.deletePerson();
        Assert.assertFalse(Global.currentWorkspace.getPeople().contains(person));
        Global.commandManager.undo();
        Assert.assertTrue(Global.currentWorkspace.getPeople().contains(person));
    }
}