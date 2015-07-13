package seng302.group2.scenes.information.person;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.dialog.CreatePersonDialog;
import seng302.group2.scenes.information.project.backlog.BacklogCategoryTab;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.categories.subCategory.project.BacklogCategory;
import seng302.group2.workspace.workspace.Workspace;

import static seng302.group2.scenes.MainScene.informationPane;
import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;

/**
 * A class for displaying all people currently created in a workspace.
 *
 * @author David Moseley
 */
public class PersonCategoryScene extends TrackedTabPane {
    public PersonCategoryScene(Workspace currentWorkspace) {
        super(ContentScene.PERSON_CATEGORY);

        // Define and add the tabs
        Tab categoryTab = new PersonCategoryTab(currentWorkspace);

        this.getTabs().addAll(categoryTab);  // Add the tabs to the pane
    }
}
