package seng302.group2.scenes.listdisplay.categories.subCategory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.listdisplay.categories.Category;

import java.util.Set;

/**
 * Created by Jordane on 8/06/2015.
 */
public class SubCategory extends Category {
    protected TreeViewItem parent;

    /**
     * Basic constructor for a TreeView category
     *
     * @param name The name of the category
     */
    public SubCategory(String name) {
        super(name);
    }


    @Override
    public Set<TreeViewItem> getItemsSet() {
        return null;
    }


    /**
     * Basic constructor for a TreeView category
     *
     * @param name The name of the category
     */
    public SubCategory(String name, TreeViewItem parent) {
        super(name);
        this.parent = parent;
    }


    @Override
    public ObservableList<TreeViewItem> getChildren() {
        return FXCollections.observableArrayList();
    }
}
