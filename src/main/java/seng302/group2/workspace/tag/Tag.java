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


    /**
     * Returns all of the Sahara Items that have been tagged with this tag
     * @return A set of the Sahara Items that have been tagged with this tag
     */
    // Was ObservableList based on this.items
    //TODO Bronson Does this need to be observable list now or nah
    public Set<SaharaItem> getTaggedItems() {
        // @BRONSON
        Set<SaharaItem> items = new HashSet<>();
        for (SaharaItem item : SaharaItem.getAllItems()) {
            if (item.getTags().contains(this)) {
                items.add(item);
            }
        }
        return items;
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
     * A command class for allowing the deletion of Tags from a Workspace.
     */
    private class DeleteGlobalTagCommand implements Command {
        private Tag tag;
        private boolean globalTag = false;
        private Set<SaharaItem> taggedItems = new HashSet<>();

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
            taggedItems.addAll(tag.getTaggedItems());
            if (Global.currentWorkspace.getAllTags().contains(tag)) {
                Global.currentWorkspace.getAllTags().remove(tag);
            }
            for (SaharaItem item : taggedItems) {
                item.getTags().remove(tag);
            }
        }

        /**
         * Undoes the global tag deletion command
         */
        public void undo() {
            for (SaharaItem item : taggedItems) {
                item.getTags().add(tag);
            }
            Global.currentWorkspace.getAllTags().add(tag);
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