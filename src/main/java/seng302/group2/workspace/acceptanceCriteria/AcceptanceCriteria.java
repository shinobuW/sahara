package seng302.group2.workspace.acceptanceCriteria;

import seng302.group2.Global;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.story.Story;

/**
 * Created by Shinobu on 30/05/2015.
 */
public class AcceptanceCriteria
{
    private String description;
    private AcState state;
    private Story story;
    public enum AcState
    {
        ACCEPTED,
        UNACCEPTED
    }

    /**
     * Basic constructor
     * @param description the description of the AC
     * @param story the AC belongs to
     */
    public AcceptanceCriteria(String description, Story story)
    {
        this.description = description;
        this.state = AcState.UNACCEPTED;
        this.story = story;
    }

    /**
     * Sets the text of the AC
     * @param text text to set
     */
    public void setText(String text)
    {
        this.description = text;
    }


    /**
     * Sets the state of the AC to done, accepted or unaccepted
     * @param state the state to set
     */
    public void setAcState(AcState state)
    {
        this.state = state;
    }


    /**
     * Sets the story the AC belongs to
     * @param story to set
     */
    public void setStory(Story story)
    {
        this.story = story;
    }


    /**
     * Get the text
     */
    public String getDescription()
    {
        return this.description;
    }


    /**
     * Gets the state
     */
    public AcState getState()
    {
        return this.state;
    }


    /**
     * Gets the story the AC belongs to
     */
    public Story getStory()
    {
        return this.story;
    }

    /**
     * Delete the acceptance maintenance and removes it from story
     */
    public void delete()
    {
        Command deleteAc = new DeleteAcCommand(this, this.story);
        Global.commandManager.executeCommand(deleteAc);
    }

    /**
     * Edits the description to the one given
     * @param desc the description to be set to
     */
    public void edit(String desc)
    {
        Command editAc = new EditAcCommand(this, this.description, desc);
        Global.commandManager.executeCommand(editAc);
    }

    private class DeleteAcCommand implements Command
    {
        private AcceptanceCriteria acceptanceCriteria;
        private Story story;

        DeleteAcCommand(AcceptanceCriteria ac, Story story)
        {
            this.acceptanceCriteria = ac;
            this.story = story;
        }

        public void execute()
        {
            story.getAcceptanceCriteria().remove(acceptanceCriteria);
        }

        public void undo()
        {
            story.getAcceptanceCriteria().add(acceptanceCriteria);
        }
    }

    /**
     * A command class that allows the executing and undoing of story edits
     */
    private class EditAcCommand implements Command
    {
        private AcceptanceCriteria ac;
        private String newDescription;
        private String oldDescription;

        private EditAcCommand(AcceptanceCriteria ac, String old, String newD)
        {
            this.ac = ac;
            this.oldDescription = old;
            this.newDescription = newD;
        }

        /**
         * Executes/Redoes the changes of the story edit
         */
        public void execute()
        {
            ac.description = newDescription;
        }

        /**
         * Undoes the changes of the story edit
         */
        public void undo()
        {
            ac.description = oldDescription;
        }

    }
}


