package seng302.group2.workspace.acceptanceCriteria;

import junit.framework.Assert;
import org.junit.Test;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.story.Story;

/**
 * A series of tests relating to AcceptanceCriteria class
 * Created by swi67 on 31/05/15.
 */
public class AcceptanceCriteriaTest
{
    Story testStory = new Story();
    AcceptanceCriteria ac = new AcceptanceCriteria("This is a Demo Text", AcceptanceCriteria.AcState.DONE, testStory);

    /**
     * Tests the validity of the constructor
     */
    @Test
    public void testConstructor()
    {
        Assert.assertEquals("This is a Demo Text", ac.getText());
        Assert.assertEquals(AcceptanceCriteria.AcState.DONE, ac.getState());
        Assert.assertEquals(testStory, ac.getStory());
    }


    @Test
    public void testSetters()
    {
        Story anotherStory = new Story("Story One", "Long Name", "Description", "creator",
                new Project(), 1);
        ac.setText("Testing");
        ac.setStory(anotherStory);
        ac.setAcState(AcceptanceCriteria.AcState.ACCEPTED);

        Assert.assertEquals("Testing", ac.getText());
        Assert.assertEquals(anotherStory, ac.getStory());
        Assert.assertEquals(AcceptanceCriteria.AcState.ACCEPTED, ac.getState());
    }


}
