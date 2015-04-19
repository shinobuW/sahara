/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.workspace.team;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.role.RoleType;

/**
 * A series of tests relating to Role
 * @author swi67 
 */
public class RoleTest extends TestCase
{
    /**
     * Create the test case.
     * @param testName name of the test case
     */
    public RoleTest(String testName)
    {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(RoleTest.class);
    }

    /**
     * A simple test for the Role constructors
     */
    public void testRoleConstructors()
    {
        Role role = new Role();
        assertEquals("role Name", role.getShortName());
        assertEquals("", role.getDescription());
        assertEquals("role Name", role.toString());
        
        Role role2 = new Role("QA", RoleType.Others);
        assertEquals("QA", role2.getShortName());
        assertEquals(RoleType.Others, role2.getType());
        
        Role role3 = new Role("Dev2", RoleType.Dev, "Developer");
        assertEquals("Dev2", role3.getShortName());
        assertEquals(RoleType.Dev, role3.getType());
        assertEquals("Developer", role3.getDescription());
    }
    
    /**
     * Tests for Role setter methods.
     */
    public void testRoleSetters()
    {
        Role role = new Role();
        role.setShortName("Tester");
        role.setDescription("Manual Testing");
        role.setType(RoleType.Others);
        
        assertEquals("Tester", role.getShortName());
        assertEquals("Manual Testing", role.getDescription());
        assertEquals(RoleType.Others, role.getType());
    }
}
