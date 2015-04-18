/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.listdisplay;

import javafx.collections.ObservableList;
import junit.framework.TestCase;

import static javafx.collections.FXCollections.observableArrayList;
import static org.junit.Assert.assertNotEquals;

/**
 *
 * @author Jordane
 */
public class TreeViewItemTest extends TestCase
{
    
    public TreeViewItemTest(String testName)
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
     * Test of getChildren method, of class TreeViewItem.
     */
    public void testGetChildren()
    {
        TreeViewItem instance = new TreeViewItem();
        ObservableList<TreeViewItem> expResult = observableArrayList();
        ObservableList<TreeViewItem> result = instance.getChildren();
        assertEquals(expResult, result);
        
        instance.getChildren().add(new TreeViewItem());
        assertNotEquals(expResult, result);
        assertEquals(1, instance.getChildren().size());
    }

    /**
     * Test of toString method, of class TreeViewItem.
     */
    public void testToString()
    {
        TreeViewItem instance = new TreeViewItem();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        
        instance = new TreeViewItem("aTreeViewItem");
        assertEquals("aTreeViewItem", instance.toString());
    }
    
}
