/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.listdisplay;

import javafx.collections.ObservableList;
import junit.framework.TestCase;

import static org.junit.Assert.assertNotEquals;

/**
 *
 * @author Jordane
 */
public class CategoryTest extends TestCase
{
    
    public CategoryTest(String testName)
    {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    /**
     * Test of toString method, of class Category.
     */
    public void testToString()
    {
        Category instance = new Category("aCategory");
        String expResult = "aCategory";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of getChildren method, of class Category.
     */
    public void testGetChildren()
    {
        Category instance = new Category("");
        ObservableList<TreeViewItem> expResult = null;
        ObservableList<TreeViewItem> result = instance.getChildren();
        assertEquals(expResult, result);
        
        instance = new Category("People");
        expResult = null;
        result = instance.getChildren();
        assertNotEquals(expResult, result);
    }
    
}
