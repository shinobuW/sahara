package seng302.group2.workspace.acceptanceCriteria;

import junit.framework.Assert;
import org.junit.Test;
import seng302.group2.Global;
import seng302.group2.workspace.story.Story;

/**
 * A series of tests relating to AcceptanceCriteria class
 * Created by swi67 on 31/05/15.
 */
public class AcceptanceCriteriaTest
{
    Story testStory = new Story();
    AcceptanceCriteria ac = new AcceptanceCriteria("This is a Demo Text", testStory);

    /**
     * Tests the validity of the constructor
     */
    @Test
    public void testConstructor()
    {
        Assert.assertEquals("This is a Demo Text", ac.getText());
        Assert.assertEquals(AcceptanceCriteria.AcState.UNACCEPTED, ac.getState());
        Assert.assertEquals(testStory, ac.getStory());
    }


    /**
     * Tests the undo/redo command for deleting acceptance maintenance
     */
    @Test
    public void testDelete()
    {
        testStory.add(ac);
        ac.delete();
        Assert.assertTrue(testStory.getAcceptanceCriteria().isEmpty());
        Global.commandManager.undo();
        Assert.assertTrue(testStory.getAcceptanceCriteria().contains(ac));
        Global.commandManager.redo();
        Assert.assertTrue(testStory.getAcceptanceCriteria().isEmpty());
    }


}
