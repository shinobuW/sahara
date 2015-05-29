package seng302.group2.workspace.acceptanceCriteria;

import seng302.group2.workspace.story.Story;

/**
 * Created by Shinobu on 30/05/2015.
 */
public class AcceptanceCriteria
{
    private String text;
    private AcState state;
    private Story story;
    private enum AcState
    {
        DONE,
        ACCEPTED,
        UNACCEPTED
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
}


