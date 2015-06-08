package seng302.group2.scenes.listdisplay;

import javafx.collections.ObservableList;
import seng302.group2.scenes.sceneswitch.switchStrategies.CategorySwitchStrategy;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * The basic structure of a TreeView item
 *
 * @author Jordane
 */
public abstract class TreeViewItem implements HierarchyData<TreeViewItem> {
    private String itemName = "";
    private transient ObservableList<TreeViewItem> children = observableArrayList();

    private CategorySwitchStrategy categorySwitchStrategy;

    /**
     * Blank constructor
     */
    public TreeViewItem() {
    }


    /**
     * Constructor for a TreeViewItem
     *
     * @param itemName The name of the TreeViewItem
     */
    public TreeViewItem(String itemName) {
        this.itemName = itemName;
    }


    /**
     * Allows the setting of the switch strategy for children classes
     *
     * @param categorySwitchStrategy The strategy to set
     */
    protected void setCategorySwitchStrategy(CategorySwitchStrategy categorySwitchStrategy) {
        this.categorySwitchStrategy = categorySwitchStrategy;
    }


    /**
     * Gets the children of the TreeViewItem
     *
     * @return The items of the TreeViewItem
     */
    @Override
    public ObservableList<TreeViewItem> getChildren() {
        return children;
    }


    /**
     * Gets the string representation of the TreeViewItem
     *
     * @return The TreeViewItem name
     */
    @Override
    public String toString() {
        return this.itemName;
    }


    /**
     * Switches the scene based on the TVItem's switching strategy
     */
    public void switchToCategoryScene() {
        try {
            categorySwitchStrategy.switchScene();
        }
        catch (NullPointerException ex) {
            System.out.println("Switch strategy not implemented for this item yet");
        }
    }


    /**
     * Switches the scene based on the TVItem's switching strategy
     */
    public void switchToInfoScene() {
        try {
            //informationSwitchStrategy.switchScene(this);
        }
        catch (NullPointerException ex) {
            System.out.println("Switch strategy not implemented for this item yet");
        }
    }
}
