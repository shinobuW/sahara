package seng302.group2.scenes.information.role;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.information.project.backlog.BacklogCategoryTab;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.categories.subCategory.project.BacklogCategory;
import seng302.group2.workspace.workspace.Workspace;

import static seng302.group2.scenes.MainScene.informationPane;

/**
 * A class for displaying all roles currently created in a workspace.
 *
 * @author David Moseley
 */
public class RoleCategoryScene extends TrackedTabPane {
    public RoleCategoryScene(Workspace currentWorkspace) {
        super(ContentScene.ROLE_CATEGORY);

        // Define and add the tabs
        Tab categoryTab = new RoleCategoryTab(currentWorkspace);

        this.getTabs().addAll(categoryTab);  // Add the tabs to the pane
    }
}