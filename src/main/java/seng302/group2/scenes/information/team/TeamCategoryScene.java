package seng302.group2.scenes.information.team;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.dialog.CreateTeamDialog;
import seng302.group2.scenes.dialog.DeleteDialog;
import seng302.group2.scenes.information.skill.SkillCategoryTab;
import seng302.group2.workspace.SaharaItem;
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