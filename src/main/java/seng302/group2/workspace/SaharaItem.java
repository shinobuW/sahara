package seng302.group2.workspace;

import javafx.collections.ObservableList;
import org.w3c.dom.Element;
import seng302.group2.scenes.sceneswitch.switchStrategies.CategorySwitchStrategy;
import seng302.group2.scenes.sceneswitch.switchStrategies.InformationSwitchStrategy;
import seng302.group2.scenes.sceneswitch.switchStrategies.SubCategorySwitchStrategy;
import seng302.group2.workspace.categories.Category;
import seng302.group2.workspace.tag.Tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

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

    static final AtomicLong NEXT_ID = new AtomicLong(0);
    protected final long id = NEXT_ID.getAndIncrement();

    private transient ObservableList<SaharaItem> tags = observableArrayList();
    private List<Tag> serializableTags = new ArrayList<>();


    // The pool of all Sahara items
    static Set<SaharaItem> itemPool = new HashSet<>();


    /**
     * Blank constructor
     */
    public SaharaItem() {
        itemPool.add(this);
    }


    /**
     * Constructor for a SaharaItem
     *
     * @param itemName The name of the SaharaItem
     */
    public SaharaItem(String itemName) {
        this.itemName = itemName;
        itemPool.add(this);
    }


    /**
     * Gets the Sahara item's ID
     * @return The ID of this Sahara item
     */
    public long getId() {
        return id;
    }

    /**
     * Gets the SaharaItems list of tags
     * @return the list of tags of this Sahara item
     */
    public ObservableList<SaharaItem> getTags() {
        return tags;
    }


    /**
     * Checks all IDs in the current pool of Sahara items and sets the minimum ID for new items
     */
    public static void refreshIDs() {
        long maxID = 0;
        for (SaharaItem item : itemPool) {
            if (item.getId() > maxID) {
                maxID = item.getId();
            }
        }
        setStartId(maxID);
    }


    /**
     * Sets the new starting ID for new Sahara items
     * @param startId The ID to start from
     */
    public static void setStartId(long startId) {
        if (startId > NEXT_ID.get()) {
            NEXT_ID.set(startId);
        }
    }

    /**
     * Sets the new starting ID for new Sahara items
     * @param startId The ID to start from
     * @param force Whether or not to override the current ID regardless whether or not it is less than the current ID
     */
    public static void setStartId(long startId, boolean force) {
        if (force) {
            NEXT_ID.set(startId);
        }
        else {
            setStartId(startId);
        }
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
        categorySwitchStrategy.switchScene();
    }


    /**
     * Switches the scene based on the TVItem's switching strategy
     * @param subCategory the sub category to switch to
     */
    public void switchToCategoryScene(Category subCategory) {
        subCategorySwitchStrategy.switchScene(subCategory);
    }


    /**
     * Switches the scene based on the TVItem's switching strategy
     */
    public void switchToInfoScene() {
        informationSwitchStrategy.switchScene(this);
    }


    /**
     * Switches the scene based on the TVItem's switching strategy
     * @param edit Whether or not to switch to the edit scene
     */
    public void switchToInfoScene(boolean edit) {
        informationSwitchStrategy.switchScene(this, edit);
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
    public boolean equivalentTo(Object object) {
        return object instanceof SaharaItem
                && ((SaharaItem) object).getId() == this.getId();
    }



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
