package seng302.group2.workspace.skills;

import org.junit.Assert;
import org.junit.Test;
import seng302.group2.Global;

/**
 * A series of tests relating to Skills
 *
 * @author Cameron Williams (crw73)
 * @author Bronson McNaughton (btm38)
 */
public class SkillTest {
    /**
     * A simple test for the Skill constructors.
     */
    @Test
    public void testSkillConstructors() {
        Skill skill = new Skill();
        Assert.assertEquals("unnamed", skill.getShortName());
        Assert.assertEquals("no description", skill.getDescription());
        Assert.assertEquals("unnamed", skill.toString());
        Assert.assertEquals(null, skill.getChildren());

        Skill skill2 = new Skill("C#", "A better language than Java");
        Assert.assertEquals("C#", skill2.getShortName());
        Assert.assertEquals("A better language than Java", skill2.getDescription());
        Assert.assertEquals("C#", skill2.toString());

    }

    /**
     * Tests for Skills' setter methods.
     */
    @Test
    public void testSkillsSetters() {
        Skill skill = new Skill();
        skill.setShortName("C#");
        skill.setDescription("A better language than Java");

        Assert.assertEquals("C#", skill.getShortName());
        Assert.assertEquals("A better language than Java", skill.getDescription());
        Assert.assertEquals("C#", skill.toString());
    }

    /**
     * Tests the compareTo method of Skill to ensure it correctly returns an int representing if a
     * shortName is larger or not.
     */
    @Test
    public void testCompareTo() {
        Skill skill1 = new Skill();
        Skill skill2 = new Skill();
        skill1.setShortName("A");
        skill2.setShortName("Z");

        Assert.assertTrue(skill1.compareTo(skill2) <= 0);
        Assert.assertTrue(skill2.compareTo(skill1) >= 0);
        Assert.assertTrue(skill1.compareTo(skill1) == 0);
    }

    @Test
    public void testEdit() {
        Skill skill = new Skill("C#", "A better language than Java");

        skill.edit("Java", "A better language than C#");
        Assert.assertEquals("Java", skill.getShortName());
        Assert.assertEquals("A better language than C#", skill.getDescription());

        Global.commandManager.undo();

        Assert.assertEquals("C#", skill.getShortName());
        Assert.assertEquals("A better language than Java", skill.getDescription());
    }

    @Test
    public void testDeleteSkill() {
        Skill skill = new Skill("C#", "A better language than Java");

        skill.deleteSkill();
        Assert.assertFalse(Global.currentWorkspace.getSkills().contains(skill));

        Global.commandManager.undo();

        Assert.assertTrue(Global.currentWorkspace.getSkills().contains(skill));
    }
}
