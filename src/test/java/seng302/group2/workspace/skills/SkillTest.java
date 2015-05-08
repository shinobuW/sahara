package seng302.group2.workspace.skills;

import org.junit.Assert;
import org.junit.Test;

/**
 * A series of tests relating to Skills
 * @author Cameron Williams (crw73)
 */
public class SkillTest
{
    /**
     * A simple test for the Skill constructors.
     */
    @Test
    public void testSkillConstructors()
    {
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
    public void testSkillsSetters()
    {
        Skill skill = new Skill();
        skill.setShortName("C#");
        skill.setDescription("A better language than Java");

        Assert.assertEquals("C#", skill.getShortName());
        Assert.assertEquals("A better language than Java", skill.getDescription());
        Assert.assertEquals("C#", skill.toString());
    }
}
