/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.listdisplay;

import javafx.collections.ObservableList;
import org.junit.Assert;
import org.junit.Test;
import seng302.group2.Global;

import static org.junit.Assert.assertNotEquals;

/**
 *
 * @author Jordane
 */
public class CategoryTest
{

    /**
     * Test of toString method, of class Category.
     */
    @Test
    public void testToString()
    {
        Category instance = new Category("aCategory");
        String expResult = "aCategory";
        String result = instance.toString();
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of getChildren method, of class Category.
     */
    @Test
    public void testGetChildren()
    {
        Category category = new Category("");
        ObservableList<TreeViewItem> expResult = null;
        ObservableList<TreeViewItem> result = category.getChildren();
        Assert.assertEquals(expResult, result);

        category = new Category("People");
        Assert.assertEquals(Global.currentWorkspace.getPeople(), category.getChildren());

        category = new Category("Projects");
        Assert.assertEquals(Global.currentWorkspace.getProjects(), category.getChildren());

        category = new Category("Skills");
        Assert.assertEquals(Global.currentWorkspace.getSkills(), category.getChildren());

        category = new Category("Teams");
        Assert.assertEquals(Global.currentWorkspace.getTeams(), category.getChildren());

        category = new Category("Roles");
        Assert.assertEquals(Global.currentWorkspace.getRoles(), category.getChildren());
    }

    /**
     * Tests that the equivalence of two Category instances are as expected
     */
    @Test
    public void testEquals()
    {
        Category cat1 = new Category("A Category");
        Category cat2 = new Category("Another Category");
        Category cat3 = new Category("A Category");

        Assert.assertFalse(cat1.equals(cat2));
        Assert.assertTrue(cat1.equals(cat3));
        Assert.assertNotEquals(cat1, "A Category");
    }
}
