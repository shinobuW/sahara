package seng302.group2.scenes.listdisplay;

import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;

/**
 * The basic structure of a TreeView item
 * @author Jordane
 */
public class TreeViewItem implements HierarchyData<TreeViewItem>
{
    private String itemName = "";
    private transient ObservableList<TreeViewItem> children = observableArrayList();

    
    /**
     * Blank constructor
     */
    public TreeViewItem()
    {
    }
    
    
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
