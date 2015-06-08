package seng302.group2.scenes.listdisplay;

import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seng302.group2.scenes.listdisplay.categories.Category;
import seng302.group2.scenes.sceneswitch.switchStrategies.CategorySwitchStrategy;
import seng302.group2.scenes.sceneswitch.switchStrategies.InformationSwitchStrategy;
import seng302.group2.scenes.sceneswitch.switchStrategies.SubCategorySwitchStrategy;

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
    private InformationSwitchStrategy informationSwitchStrategy;
    private SubCategorySwitchStrategy subCategorySwitchStrategy;

    private Logger logger = LoggerFactory.getLogger(TreeViewItem.class);


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
     * Allows the setting of the category switch strategy for children classes
     *
     * @param categorySwitchStrategy The strategy to set
     */
    protected void setCategorySwitchStrategy(CategorySwitchStrategy categorySwitchStrategy) {
        this.categorySwitchStrategy = categorySwitchStrategy;
    }


    /**
     * Allows the setting of the category switch strategy for children classes
     *
     * @param subCategorySwitchStrategy The strategy to set
     */
    protected void setCategorySwitchStrategy(SubCategorySwitchStrategy subCategorySwitchStrategy) {
        this.subCategorySwitchStrategy = subCategorySwitchStrategy;
    }


    /**
     * Allows the setting of the information switch strategy for children classes
     *
     * @param switchStrategy The strategy to set
     */
    protected void setInformationSwitchStrategy(InformationSwitchStrategy switchStrategy) {
        this.informationSwitchStrategy = switchStrategy;
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
            logger.info("Switch strategy not implemented for this item yet: " + this.getClass());
        }
    }


    /**
     * Switches the scene based on the TVItem's switching strategy
     */
    public void switchToCategoryScene(Category subCategory) {
        try {
            subCategorySwitchStrategy.switchScene(subCategory);
        }
        catch (NullPointerException ex) {
            logger.info("Switch strategy not implemented for this item yet: " + this.getClass());
        }
    }


    /**
     * Switches the scene based on the TVItem's switching strategy
     */
    public void switchToInfoScene() {
        try {
            informationSwitchStrategy.switchScene(this);
        }
        catch (NullPointerException ex) {
            logger.info("Switch strategy not implemented for this item yet: " + this.getClass());
        }
    }


    /**
     * Switches the scene based on the TVItem's switching strategy
     * @param edit Whether or not to switch to the edit scene
     */
    public void switchToInfoScene(boolean edit) {
        try {
            informationSwitchStrategy.switchScene(this, edit);
        }
        catch (NullPointerException ex) {
            System.out.println("Switch strategy not implemented for this item yet");
        }
    }
}
