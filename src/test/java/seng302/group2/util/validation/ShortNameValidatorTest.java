/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.validation;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import seng302.group2.Global;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.release.Release;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

import java.text.ParseException;
import java.util.Date;


/**
 * Tests that the short name validator performs correctly.
 * @author Jordane
 */
public class ShortNameValidatorTest
{
    /**
     * Performs the initialization state before tests are run
     */
    @BeforeClass
    public static void setUp()
    {
        Date dob = new Date();
        try
        {
            dob = Global.datePattern.parse("19/12/1994");
        }
        catch (ParseException e)
        {
            Assert.fail("Date parsing error, needs fixing");
        }

        Workspace ws = new Workspace("WS", "Workspace", "Desc");
        Global.currentWorkspace = ws;

        Person pers = new Person("btm38", "McNaughton", "Bronson", "btm38@gmail.com",
                "A really cool dude", dob);
        Global.currentWorkspace.add(pers);

        Project proj = new Project("PROJ", "Project", "Desc");
        Release rel = new Release();
        rel.setShortName("RELEASE");
        Team team = new Team("TEAM", "Desc");
        Person apers = new Person();
        apers.setShortName("PERS");
        Skill skill = new Skill("SKILL", "Desc");
        Role role = new Role("ROLE", Role.RoleType.Others);

        proj.add(rel, false);
        ws.addWithoutUndo(proj);
        ws.add(team, false);
        ws.add(apers, false);
        ws.add(skill, false);
        ws.add(role, false);
    }


    /**
     * Test of validateShortName method, of class ShortNameValidator.
     */
    @Test
    public void testValidateShortName()
    {
        // People
        Assert.assertEquals(ValidationStatus.INVALID, ShortNameValidator.validateShortName(""));
        Assert.assertEquals(ValidationStatus.NON_UNIQUE,
                ShortNameValidator.validateShortName("btm38"));
        Assert.assertEquals(ValidationStatus.VALID, ShortNameValidator.validateShortName("new"));
        Assert.assertEquals(ValidationStatus.OUT_OF_RANGE, ShortNameValidator.validateShortName(
                "this is much more than 20 characters long"));

        Assert.assertEquals(ValidationStatus.NON_UNIQUE, ShortNameValidator.validateShortName("WS"));
        Assert.assertEquals(ValidationStatus.NON_UNIQUE, ShortNameValidator.validateShortName("PROJ"));
        Assert.assertEquals(ValidationStatus.NON_UNIQUE, ShortNameValidator.validateShortName("TEAM"));
        Assert.assertEquals(ValidationStatus.NON_UNIQUE, ShortNameValidator.validateShortName("PERS"));
        Assert.assertEquals(ValidationStatus.NON_UNIQUE, ShortNameValidator.validateShortName("SKILL"));
        Assert.assertEquals(ValidationStatus.NON_UNIQUE, ShortNameValidator.validateShortName("ROLE"));
        Assert.assertEquals(ValidationStatus.NON_UNIQUE, ShortNameValidator.validateShortName("RELEASE"));
    }
}
