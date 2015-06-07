package seng302.group2.scenes.information.backlog;

import javafx.scene.control.Tab;
import seng302.group2.scenes.sceneswitch.SceneSwitcher;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.workspace.backlog.Backlog;

/**
 * Created by cvs20 on 19/05/15.
 */
public class BacklogScene extends TrackedTabPane
{
    public BacklogScene(Backlog currentBacklog)
    {
        super(SceneSwitcher.ContentScene.BACKLOG);

        // Define and add the tabs
        Tab informationTab = new BacklogInfoTab(currentBacklog);

        this.getTabs().addAll(informationTab);  // Add the tabs to the pane
    }
}
