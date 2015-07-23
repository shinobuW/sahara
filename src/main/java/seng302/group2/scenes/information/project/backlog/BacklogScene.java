package seng302.group2.scenes.information.project.backlog;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.workspace.project.backlog.Backlog;

/**
 * Created by cvs20 on 19/05/15.
 */
public class BacklogScene extends TrackedTabPane {
    
    /**
     * Constructor for the Backlog scene
     * 
     * @param currentBacklog The currently selected Backlog
     */
    public BacklogScene(Backlog currentBacklog) {
        super(ContentScene.BACKLOG, currentBacklog.getProject());

        // Define and add the tabs
        Tab informationTab = new BacklogInfoTab(currentBacklog);
        Tab scaleInfoTab = new StoryEstimationScaleInfoTab();
        Tab testTab = new TESTING_CellTestTab(currentBacklog);

        this.getTabs().addAll(informationTab, scaleInfoTab, testTab);  // Add the tabs to the pane
    }
}
