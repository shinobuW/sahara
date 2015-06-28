package seng302.group2.scenes.listdisplay.categories;

import javafx.collections.ObservableList;
import org.apache.commons.lang.builder.HashCodeBuilder;
import seng302.group2.scenes.listdisplay.TreeViewItem;

/**
 * Category item in the list display, uses as a header for all the elements of one type, ie
 * "People" for all persons within the workspace.
 *
 * @author Jordane
 */
public abstract class Category extends TreeViewItem {
    private String name;

    /**
     * Basic constructor for a TreeView category
     *
     * @param name The name of the category
     */
    public Category(String name) {
        super(name);
        this.name = name;
    }


    /**
     * An overridden version for the String representation of a Person
     *
     * @return The short name of the Person
     */
    @Override
    public String toString() {
        return this.name;
    }


    /**
     * Gets the children of the category
     *
     * @return the children of the category
     */
    @Override
    public abstract ObservableList<TreeViewItem> getChildren();


    /**
     * Shows the creation dialog for the types of items of the category
     */
    public abstract void showCreationDialog();


    /**
     * Overrides that a category is equal if it has the same children
     *
     * @param obj the object to compare
     * @return if the objects are equal/equivalent
     */
    @Override
    public boolean equivalentTo(Object obj) {
        return obj.getClass().equals(this.getClass())
                && ((Category) obj).getChildren() == this.getChildren()
                && ((Category) obj).name.equals(this.name);
    }
}
