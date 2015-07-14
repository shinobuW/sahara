package seng302.group2.scenes.information.project.release;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.dialog.CreateReleaseDialog;
import seng302.group2.scenes.dialog.DeleteDialog;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.categories.subCategory.project.ReleaseCategory;
import seng302.group2.workspace.project.release.Release;

/**
 * A class for displaying the release category. Contains information
 * about all the releases in the project.
 *
 * Created by btm38 on 14/07/15.
 */
public class ReleaseCategoryScene extends TrackedTabPane {
    /**
     * Constructor for the ReleaseCategoryScene class. Creates a tab
     * of ReleaseCategoryTab and displays it.
     * @param selectedCategory the current workspace
     */
    public ReleaseCategoryScene(ReleaseCategory selectedCategory) {
        super(ContentScene.RELEASE_CATEGORY);

        // Define and add the tabs
        Tab informationTab = new ReleaseCategoryTab(selectedCategory);

        this.getTabs().addAll(informationTab);  // Add the tabs to the pane
    }
}