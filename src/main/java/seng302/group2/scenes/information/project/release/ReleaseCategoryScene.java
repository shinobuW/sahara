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
 * A class for displaying all releases in a project.
 *
 * Created by btm38 on 14/07/15.
 */
public class ReleaseCategoryScene extends TrackedTabPane {
    /**
     * Constructor for the TeamCategoryScene class.
     * @param currentRelease the current release
     */
    public ReleaseCategoryScene(ReleaseCategory currentRelease) {
        super(ContentScene.RELEASE);

        // Define and add the tabs
        Tab informationTab = new ReleaseCategoryTab(currentRelease);

        this.getTabs().addAll(informationTab);  // Add the tabs to the pane
    }
}