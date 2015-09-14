package seng302.group2.workspace.tag;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import org.w3c.dom.Element;
import seng302.group2.Global;
import seng302.group2.util.reporting.ReportGenerator;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.SaharaItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * A class representation of tags, used to highlight and identify different states of other Sahara model objects.
 */
public class Tag extends SaharaItem implements Serializable {
    private String name;
    private Color color = Color.ROYALBLUE;

    private transient ObservableList<SaharaItem> items = observableArrayList();
    private List<SaharaItem> serializableItems = new ArrayList<>();



    /**
     * Basic constructor for a Tag
     * @param tagName The name of the tag
     */
    public Tag(String tagName) {
        this.name = tagName;
        this.getTags().clear();
    }

    /**
     * Sets the colour of the tag for use on the visual tag nodes
     * @param color The colour to set the tag
     */
    public void setColor(Color color) {
        this.color = color;
    }


    /**
     * Gets the colour of the tag for use on the visual tag nodes
     * @return The colour of the tag
     */
    public Color getColor() {
        return color;
    }

    /**
     * Deletes the tag from the workspace and removes it from every item that has the tag.
     */
    public void deleteGlobalTag() {
        Command deleteGlobalTag = new DeleteGlobalTagCommand(this);
        Global.commandManager.executeCommand(deleteGlobalTag);
    }

    /**
     * SaharaItems 'belonging' to the Tag
     *
     * @return Tag empty set as tags do not have any items belonging to
     */
    @Override
    public Set<SaharaItem> getItemsSet() {
        return new HashSet<>();
    }

    public ObservableList<SaharaItem> getItems() {
        return this.items;
    }


    /**
     * Gets the short name of the tag.
     * @return tags short name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method for creating an XML element for the Tag within report generation
     *
     * @return element for XML generation
     */
    @Override
    public Element generateXML() {
        return ReportGenerator.doc.createElement(name);
    }


    /**
     * Overridden method for displaying the tag as a string
     * @return The name of the tag
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Serialization pre-processing.
     */
    public void prepSerialization() {
        serializableItems.clear();
        for (SaharaItem item : items) {
            serializableItems.add(item);
        }
    }

    /**
     * Deserialization post-processing.
     */
    public void postDeserialization() {
        items.clear();
        for (SaharaItem item : serializableItems) {
            items.add(item);
        }
    }

    /**
     * A command class for allowing the deletion of Tags from a Workspace.
     */
    private class DeleteGlobalTagCommand implements Command {
        private Tag tag;

        /**
         * Constructor for the global tag deletion command
         * @param tag The tag to be removed from every item, and then the workspace.
         */
        DeleteGlobalTagCommand(Tag tag) {
            this.tag = tag;
        }

        /**
         * Executes the tag deletion command
         */
        public void execute() {
            for (SaharaItem item : items) {
                item.getTags().remove(tag);
            }
            Global.currentWorkspace.getGlobalTags().remove(tag);
        }

        /**
         * Undoes the global tag deletion command
         */
        public void undo() {
            Global.currentWorkspace.getGlobalTags().add(tag);
            for (SaharaItem item : items) {
                item.getTags().add(tag);
            }
        }

        /**
         * Searches the stateObjects to find an equal model class to map to
         * @param stateObjects A set of objects to search through
         * @return If the item was successfully mapped
         */
        @Override
        public boolean map(Set<SaharaItem> stateObjects) {
            boolean mapped = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(tag)) {
                    this.tag = (Tag) item;
                    mapped = true;
                }
            }
            return mapped;
        }
    }
}