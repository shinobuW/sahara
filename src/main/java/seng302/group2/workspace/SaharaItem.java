package seng302.group2.workspace;

import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import seng302.group2.scenes.sceneswitch.switchStrategies.CategorySwitchStrategy;
import seng302.group2.scenes.sceneswitch.switchStrategies.InformationSwitchStrategy;
import seng302.group2.scenes.sceneswitch.switchStrategies.SubCategorySwitchStrategy;
import seng302.group2.workspace.categories.Category;

import java.util.Set;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * The basic structure of a TreeView item
 *
 * @author Jordane
 */
public abstract class SaharaItem implements HierarchyData<SaharaItem> {
    private String itemName = "";
    private transient ObservableList<SaharaItem> children = observableArrayList();

    private transient CategorySwitchStrategy categorySwitchStrategy;
    private transient InformationSwitchStrategy informationSwitchStrategy;
    private transient SubCategorySwitchStrategy subCategorySwitchStrategy;

    private transient Logger logger = LoggerFactory.getLogger(SaharaItem.class);


    /**
     * Blank constructor
     */
    public SaharaItem() {
    }


    /**
     * Constructor for a SaharaItem
     *
     * @param itemName The name of the SaharaItem
     */
    public SaharaItem(String itemName) {
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
     * Returns the items inside of the current SaharaItem
     * @return A set of items inside of this SaharaItem
     */
    public abstract Set<SaharaItem> getItemsSet();


    /**
     * Gets the children of the SaharaItem
     *
     * @return The items of the SaharaItem
     */
    @Override
    public ObservableList<SaharaItem> getChildren() {
        return children;
    }


    /**
     * Gets the string representation of the SaharaItem
     *
     * @return The SaharaItem name
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


    /**
     * Abstract method for creating an XML element for the report generation
     * @return element for XML generation
     */
    public abstract Element generateXML();

    /**
     * Checks whether or not <i>this</i> is <b>equivalent</b> to the passed object
     * @param object The object to compare to
     * @return true if <i>this</i> and the object are equivalent
     */
    public abstract boolean equivalentTo(Object object);


    /**
     * Checks whether or not <i>this</i> is <b>equivalent</b> to the passed object based only on the
     * short names and classes of the objects
     * @param object The object to compare to
     * @return true if <i>this</i> and the object are equivalent by name and type
     */
    public boolean semiEquivalentTo(Object object) {
        return this.getClass().equals(object.getClass())
                && ((SaharaItem) object).itemName.equals(this.itemName);
    }

}
