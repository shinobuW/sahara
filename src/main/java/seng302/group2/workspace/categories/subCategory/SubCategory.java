package seng302.group2.workspace.categories.subCategory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.categories.Category;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jordane on 8/06/2015.
 */
public abstract class SubCategory extends Category {
    protected SaharaItem parent;

    /**
     * Basic constructor for a TreeView category
     *
     * @param name The name of the category
     */
    public SubCategory(String name) {
        super(name);
    }


    @Override
    public Set<SaharaItem> getItemsSet() {
        return new HashSet<>();
    }


    /**
     * Basic constructor for a TreeView category
     *
     * @param name The name of the category
     */
    public SubCategory(String name, SaharaItem parent) {
        super(name);
        this.parent = parent;
    }


    @Override
    public ObservableList<SaharaItem> getChildren() {
        return FXCollections.observableArrayList();
    }
}
