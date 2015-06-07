package seng302.group2.workspace.acceptanceCriteria;

import seng302.group2.Global;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.allocation.Allocation;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.story.Story;
import seng302.group2.workspace.team.Team;

/**
 * Created by Shinobu on 30/05/2015.
 */
public class AcceptanceCriteria
{
    private String text;
    private AcState state;
    private Story story;
    public enum AcState
    {
        ACCEPTED,
        UNACCEPTED
    }

    /**
     * Basic constructor
     * @param text the description of the AC
     * @param state of the AC
     * @param story the AC belongs to
     */
    public AcceptanceCriteria(String text, Story story)
    {
        this.text = text;
        this.state = AcState.UNACCEPTED;
        this.story = story;
    }

    /**
     * Sets the text of the AC
     * @param text text to set
     */
    public void setText(String text)
    {
        this.text = text;
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
    public String getText()
    {
        return this.text;
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


}


