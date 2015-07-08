package seng302.group2.workspace.project.story.acceptanceCriteria;

import javafx.util.StringConverter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.w3c.dom.Element;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.util.reporting.ReportGenerator;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.estimation.EstimationScalesDictionary;

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
     * An enumeration to represent the states of acceptance criteria
     */
    public enum AcState {
        ACCEPTED,
        UNACCEPTED
    }

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
        Command deleteAc = new DeleteAcCommand(this);
        Global.commandManager.executeCommand(deleteAc);
    }

    /**
     * Edits the description to the one given
     *
     * @param desc the description to be set to
     */
    public void edit(String desc) {
        Command editAc = new EditAcCommand(this, desc);
        Global.commandManager.executeCommand(editAc);
    }

    /**
     * Edits the state to the one given
     *
     * @param state the state to be set to
     */
    public void edit(AcState state) {
        Command editAc = new EditAcStateCommand(this, state);
        Global.commandManager.executeCommand(editAc);
    }

    /**
     * Method for creating an XML element for the Team within report generation
     * @return element for XML generation
     */
    @Override
    public Element generateXML() {
        Element acceptanceElement = ReportGenerator.doc.createElement("acceptance-criteria");

        //WorkSpace Elements
        Element acceptanceCriteria = ReportGenerator.doc.createElement("description");
        acceptanceCriteria.appendChild(ReportGenerator.doc.createTextNode(description));
        acceptanceElement.appendChild(acceptanceCriteria);

        Element state = ReportGenerator.doc.createElement("state");
        StringConverter<AcState> converter = new AcEnumStringConverter();

        state.appendChild(ReportGenerator.doc.createTextNode(converter.toString(this.state)));
        acceptanceElement.appendChild(state);

        return acceptanceElement;
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

    private class DeleteAcCommand implements Command {
        private AcceptanceCriteria acceptanceCriteria;
        private Story story;
        private boolean oldStoryReady = false;
        private String oldEstimate;

        DeleteAcCommand(AcceptanceCriteria ac) {
            this.acceptanceCriteria = ac;
            this.story = ac.story;
            if (story != null) {
                oldStoryReady = story.getReady();
                oldEstimate = story.getEstimate();
            }
        }

        public void execute() {
            story.getAcceptanceCriteria().remove(acceptanceCriteria);
            if (story.getAcceptanceCriteria().isEmpty()) {
                story.setReady(false);
                story.setEstimate(EstimationScalesDictionary.getScaleValue(
                        EstimationScalesDictionary.DefaultValues.NONE));
            }
        }

        public void undo() {
            story.getAcceptanceCriteria().add(acceptanceCriteria);
            story.setReady(oldStoryReady);
            story.setEstimate(oldEstimate);
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
    private class EditAcStateCommand implements Command {
        private AcceptanceCriteria ac;

        private AcState newAcState;
        private AcState oldAcState;

        private EditAcStateCommand(AcceptanceCriteria ac, AcState newState) {
            this.ac = ac;

            this.oldAcState = ac.state;
            this.newAcState = newState;
        }

        /**
         * Executes/Redoes the changes of the story edit
         */
        public void execute() {
            ac.state = newAcState;
        }

        /**
         * Undoes the changes of the story edit
         */
        public void undo() {
            ac.state = oldAcState;
        }

        /**
         * Searches the stateObjects to find an equal model class to map to
         *
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




    /**
     * A command class that allows the executing and undoing of story edits
     */
    private class EditAcCommand implements Command {
        private AcceptanceCriteria ac;

        private String newDescription;
        private String oldDescription;

        private EditAcCommand(AcceptanceCriteria ac, String newDesc) {
            this.ac = ac;

            this.oldDescription = ac.description;
            this.newDescription = newDesc;
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