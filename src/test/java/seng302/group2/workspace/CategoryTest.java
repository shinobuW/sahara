/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.workspace;

import org.junit.Assert;
import org.junit.Test;
import seng302.group2.scenes.listdisplay.Category;

/**
 * A series of tests relating to Category
 * @author Codie Stevens (cvs20)
 */
public class CategoryTest
{
    /**
     * A simple test for the Category constructors
     */
    @Test
    public void testCategoryConstructors()
    {
        Category cat = new Category("aName");
        Assert.assertEquals("aName", cat.toString());
    }
}