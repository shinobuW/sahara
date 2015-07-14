package seng302.group2.scenes.information.team;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.workspace.workspace.Workspace;

/**
 * A class for displaying all teams currently created in a workspace.
 *
 * @author Bronson McNaughton
 */
public class TeamCategoryScene extends TrackedTabPane {
    /**
     * Constructor for the TeamCategoryScene class.
     * @param currentWorkspace the current workspace
     */
    public TeamCategoryScene(Workspace currentWorkspace) {
        super(TrackedTabPane.ContentScene.TEAM_CATEGORY);

        // Define and add the tabs
        Tab categoryTab = new TeamCategoryTab(currentWorkspace);

        this.getTabs().addAll(categoryTab);
    }
}