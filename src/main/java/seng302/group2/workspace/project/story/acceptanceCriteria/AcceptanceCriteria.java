package seng302.group2.workspace.project.story.acceptanceCriteria;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.project.story.Story;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A model class for acceptance criteria items inside of stories
 * Created by Shinobu on 30/05/2015.
 */
public class AcceptanceCriteria extends TreeViewItem implements Serializable, Comparable<AcceptanceCriteria> {
    private String description;
    private AcState state;
    private Story story;

    /**
     * Basic constructor
     *
     * @param description the description of the AC
     * @param story       the AC belongs to
     */
    public AcceptanceCriteria(String description, Story story) {
        this.description = description;
        this.state = AcState.UNACCEPTED;
        this.story = story;
    }

    /**
     * Sets the text of the AC
     *
     * @param text text to set
     */
    public void setText(String text) {
        this.description = text;
    }

    /**
     * Sets the state of the AC to done, accepted or unaccepted
     *
     * @param state the state to set
     */
    public void setAcState(AcState state) {
        this.state = state;
    }

    /**
     * Get the text
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets the state
     */
    public AcState getState() {
        return this.state;
    }

    /**
     * Gets the story the AC belongs to
     */
    public Story getStory() {
        return this.story;
    }

    /**
     * Sets the story the AC belongs to
     *
     * @param story to set
     */
    public void setStory(Story story) {
        this.story = story;
    }

    /**
     * Delete the acceptance maintenance and removes it from story
     */
    public void delete() {
        Command deleteAc = new DeleteAcCommand(this, this.story);
        Global.commandManager.executeCommand(deleteAc);
    }

    /**
     * Edits the description to the one given
     *
     * @param desc the description to be set to
     */
    public void edit(String desc) {
        Command editAc = new EditAcCommand(this, this.description, desc);
        Global.commandManager.executeCommand(editAc);
    }

    @Override
    public int compareTo(AcceptanceCriteria ac) {
        return 0; //TODO
    }

    @Override
    public boolean equivalentTo(Object object) {
        if (!(object instanceof AcceptanceCriteria)) {
            return false;
        }
        if (object == this) {
            return true;
        }

        AcceptanceCriteria ac = (AcceptanceCriteria) object;
        return new EqualsBuilder()
                .append(description, ac.description)
                .append(state, ac.state)
                .append(story, ac.story)
                .isEquals();
    }


    @Override
    public Set<TreeViewItem> getItemsSet() {
        return new HashSet<>();
    }

    public enum AcState {
        ACCEPTED,
        UNACCEPTED
    }

    private class DeleteAcCommand implements Command {
        private AcceptanceCriteria acceptanceCriteria;
        private Story story;

        DeleteAcCommand(AcceptanceCriteria ac, Story story) {
            this.acceptanceCriteria = ac;
            this.story = story;
        }

        public void execute() {
            story.getAcceptanceCriteria().remove(acceptanceCriteria);
        }

        public void undo() {
            story.getAcceptanceCriteria().add(acceptanceCriteria);
        }

        /**
         * Searches the stateObjects to find an equal model class to map to
         * @param stateObjects A set of objects to search through
         * @return If the item was successfully mapped
         */
        @Override
        public boolean map(Set<TreeViewItem> stateObjects) {
            boolean mapped_ac = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(acceptanceCriteria)) {
                    this.acceptanceCriteria = (AcceptanceCriteria) item;
                    mapped_ac = true;
                }
            }
            boolean mapped_story = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(story)) {
                    this.story = (Story) item;
                    mapped_story = true;
                }
            }
            return mapped_ac && mapped_story;
        }
    }

    /**
     * A command class that allows the executing and undoing of story edits
     */
    private class EditAcCommand implements Command {
        private AcceptanceCriteria ac;
        private String newDescription;
        private String oldDescription;

        private EditAcCommand(AcceptanceCriteria ac, String old, String newD) {
            this.ac = ac;
            this.oldDescription = old;
            this.newDescription = newD;
        }

        /**
         * Executes/Redoes the changes of the story edit
         */
        public void execute() {
            ac.description = newDescription;
        }

        /**
         * Undoes the changes of the story edit
         */
        public void undo() {
            ac.description = oldDescription;
        }

        /**
         * Searches the stateObjects to find an equal model class to map to
         * @param stateObjects A set of objects to search through
         * @return If the item was successfully mapped
         */
        @Override
        public boolean map(Set<TreeViewItem> stateObjects) {
            boolean mapped = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(ac)) {
                    this.ac = (AcceptanceCriteria) item;
                    mapped = true;
                }
            }
            return mapped;
        }

    }
}