package seng302.group2.scenes.information.project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.dialog.CreateProjectDialog;
import seng302.group2.scenes.dialog.DeleteDialog;
import seng302.group2.scenes.information.team.TeamCategoryTab;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.workspace.Workspace;

/**
 * A class for displaying the project category. Contains information
 * about all the projects in the workspace.
 *
 * Created by btm38 on 14/07/15.
 */
public class ProjectCategoryScene extends TrackedTabPane {
    /**
     * Constructor for the ProjectCategoryScene class. Creates a tab
     * of ProjectCategoryTab and displays it.
     * @param currentWorkspace the current workspace
     */
    public ProjectCategoryScene(Workspace currentWorkspace) {
        super(TrackedTabPane.ContentScene.PROJECT_CATEGORY);

        // Define and add the tabs
        Tab categoryTab = new ProjectCategoryTab(currentWorkspace);

        this.getTabs().addAll(categoryTab);
    }
}
