/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.workspace.role;

import javafx.collections.ObservableList;
import org.junit.Assert;
import org.junit.Test;
import seng302.group2.Global;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.skills.Skill;

import static javafx.collections.FXCollections.observableArrayList;

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
        
        Role role2 = new Role("QA", Role.RoleType.OTHER);
        Assert.assertEquals("QA", role2.getShortName());
        Assert.assertEquals(Role.RoleType.OTHER, role2.getType());

        Role role3 = new Role("Dev2", Role.RoleType.DEVELOPMENT_TEAM_MEMBER, "Developer");
        Assert.assertEquals("Dev2", role3.getShortName());
        Assert.assertEquals(Role.RoleType.DEVELOPMENT_TEAM_MEMBER, role3.getType());
        Assert.assertEquals("Developer", role3.getDescription());

        ObservableList<Skill> skills = observableArrayList();
        Role role4 = new Role("Role", Role.RoleType.OTHER, skills);
        Assert.assertEquals("Role", role4.getShortName());
        Assert.assertEquals(Role.RoleType.OTHER, role4.getType());
        Assert.assertEquals(skills, role4.getRequiredSkills());
    }


    @Test
    public void testToString()
    {
        Role role = new Role();
        Role nullRole = null;
        Assert.assertEquals("role Name", role.toString());
    }


    @Test
    public void testGetChildren()
    {
        Role role = new Role();
        Assert.assertNull(role.getChildren());
    }


    @Test
    public void testGetRoleType()
    {
        Global.currentWorkspace = new Workspace();
        Role poRole = null;
        for (Role role : Global.currentWorkspace.getRoles())
        {
            if (role.getType() == Role.RoleType.PRODUCT_OWNER)
            {
                poRole = role;
            }
        }
        Assert.assertTrue(Role.getRoleType(Role.RoleType.PRODUCT_OWNER) == poRole);
    }


    @Test
    public void testPostSerialization()
    {
        Role role = new Role();
        Skill skill = new Skill();
        role.getSerializableRequiredSkills().add(skill);

        role.getRequiredSkills().clear();
        Assert.assertTrue(role.getRequiredSkills().isEmpty());

        role.getSerializableRequiredSkills().add(skill);
        role.postSerialization();
        Assert.assertTrue(role.getRequiredSkills().contains(skill));
    }


    @Test
    public void testPrepSerialization()
    {
        Role role = new Role();
        Skill skill = new Skill();
        role.getRequiredSkills().add(skill);

        role.getSerializableRequiredSkills().clear();
        Assert.assertTrue(role.getSerializableRequiredSkills().isEmpty());

        role.getRequiredSkills().add(skill);
        role.prepSerialization();
        Assert.assertTrue(role.getSerializableRequiredSkills().contains(skill));
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
        role.setType(Role.RoleType.OTHER);
        
        Assert.assertEquals("Tester", role.getShortName());
        Assert.assertEquals("Manual Testing", role.getDescription());
        Assert.assertEquals(Role.RoleType.OTHER, role.getType());
    }

}
