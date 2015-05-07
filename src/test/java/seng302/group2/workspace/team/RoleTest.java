/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.workspace.team;

import org.junit.Assert;
import org.junit.Test;
import seng302.group2.workspace.role.Role;

/**
 * A series of tests relating to Role
 * @author swi67 
 */
public class RoleTest
{
    /**
     * A simple test for the Role constructors
     */
    @Test
    public void testRoleConstructors()
    {
        Role role = new Role();
        Assert.assertEquals("role Name", role.getShortName());
        Assert.assertEquals("", role.getDescription());
        Assert.assertEquals("role Name", role.toString());
        
        Role role2 = new Role("QA", Role.RoleType.Others);
        Assert.assertEquals("QA", role2.getShortName());
        Assert.assertEquals(Role.RoleType.Others, role2.getType());
        
        Role role3 = new Role("Dev2", Role.RoleType.DevelopmentTeamMember, "Developer");
        Assert.assertEquals("Dev2", role3.getShortName());
        Assert.assertEquals(Role.RoleType.DevelopmentTeamMember, role3.getType());
        Assert.assertEquals("Developer", role3.getDescription());
    }
    
    /**
     * Tests for Role setter methods.
     */
    @Test
    public void testRoleSetters()
    {
        Role role = new Role();
        role.setShortName("Tester");
        role.setDescription("Manual Testing");
        role.setType(Role.RoleType.Others);
        
        Assert.assertEquals("Tester", role.getShortName());
        Assert.assertEquals("Manual Testing", role.getDescription());
        Assert.assertEquals(Role.RoleType.Others, role.getType());
    }
}
