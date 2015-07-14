package seng302.group2.scenes.information.project.story;

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
import seng302.group2.scenes.information.project.release.ReleaseCategoryTab;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.categories.subCategory.project.ReleaseCategory;
import seng302.group2.workspace.categories.subCategory.project.StoryCategory;
import seng302.group2.workspace.project.release.Release;

/**
 * A class for displaying the story category. Contains information
 * about all the stories in the project.
 *
 * Created by btm38 on 14/07/15.
 */
public class StoryCategoryScene extends TrackedTabPane {
    /**
     * Constructor for the StoryCategoryScene class. Creates a tab
     * of StoryCategoryTab and displays it.
     * @param selectedCategory the current workspace
     */
    public StoryCategoryScene(StoryCategory selectedCategory) {
        super(ContentScene.RELEASE_CATEGORY);

        // Define and add the tabs
        Tab informationTab = new StoryCategoryTab(selectedCategory);

        this.getTabs().addAll(informationTab);  // Add the tabs to the pane
    }
}