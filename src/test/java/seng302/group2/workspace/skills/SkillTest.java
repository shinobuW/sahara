package seng302.group2.workspace.skills;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * A series of tests relating to Skills
 * @author Cameron Williams (crw73)
 */
public class SkillTest extends TestCase
{
    /**
     * Create the test case.
     * @param testName name of the test case
     */
    public SkillTest(String testName)
    {
        super(testName);
    }

    /**
     * @return the suite of tests being tested.
     */
    public static Test suite()
    {
        return new TestSuite(SkillTest.class);
    }

    /**
     * A simple test for the Skill constructors.
     */
    public void testSkillConstructors()
    {
        Skill skill = new Skill();
        assertEquals("unnamed", skill.getShortName());
        assertEquals("no description", skill.getDescription());
        assertEquals("unnamed", skill.toString());
        assertEquals(null, skill.getChildren());
        
        Skill skill2 = new Skill("C#", "A better language than Java"); 
        assertEquals("C#", skill2.getShortName());
        assertEquals("A better language than Java", skill2.getDescription());
        assertEquals("C#", skill2.toString());
        
    }
    
    /**
     * Tests for Skills' setter methods.
     */
    public void testSkillsSetters()
    {
        Skill skill = new Skill();
        skill.setShortName("C#");
        skill.setDescription("A better language than Java");
        
        assertEquals("C#", skill.getShortName());
        assertEquals("A better language than Java", skill.getDescription());
        assertEquals("C#", skill.toString());
    }
}
