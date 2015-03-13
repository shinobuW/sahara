/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.listdisplay;

import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;

/**
 * The basic structure of a TreeView item
 * @author Jordane
 */
public class TreeViewItem implements HierarchyData<TreeViewItem>
{
    private String itemName;
    private ObservableList<TreeViewItem> children = observableArrayList();

    
    /**
     * Constructor for a TreeViewItem
     * @param itemName The name of the TreeViewItem
     */
    public TreeViewItem(String itemName)
    {
        this.itemName = itemName;
    }
    
    
    /**
     * Gets the children of the TreeViewItem
     * @return The items of the TreeViewItem
     */
    @Override
    public ObservableList<TreeViewItem> getChildren()
    {
        return children;
    }
    
    
    /**
     * Gets the string representation of the TreeViewItem
     * @return The TreeViewItem name
     */
    @Override
    public String toString()
    {
        return this.itemName;
    }
}
