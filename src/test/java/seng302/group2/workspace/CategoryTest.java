/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.workspace;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import seng302.group2.scenes.listdisplay.Category;

/**
 * A series of tests relating to Category
 * @author Codie Stevens (cvs20)
 */
public class CategoryTest extends TestCase
{
    /**
     * Create the test case
     * @param testName name of the test case
     */
    public CategoryTest(String testName)
    {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(CategoryTest.class);
    }

    /**
     * A simple test for the Category constructors
     */
    public void testCategoryConstructors()
    {
        Category cat = new Category("aName");
        assertEquals("aName", cat.toString());
    }
}