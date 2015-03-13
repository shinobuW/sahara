/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.listdisplay;

import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;

/**
 *
 * @author Jordane
 */
public class TreeViewItem implements HierarchyData<TreeViewItem>
{
    private String itemName;
    private ObservableList<TreeViewItem> children = observableArrayList();

    
    public TreeViewItem(String itemName)
    {
        this.itemName = itemName;
    }
    
    
    @Override
    public ObservableList<TreeViewItem> getChildren()
    {
        return children;
    }
    
    
    @Override
    public String toString()
    {
        return this.itemName;
    }
}
