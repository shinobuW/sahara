package seng302.group2.scenes.information.backlog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.dialog.CreateBacklogDialog;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.listdisplay.categories.subCategory.project.BacklogCategory;
import seng302.group2.workspace.project.backlog.Backlog;

import static seng302.group2.scenes.MainScene.informationPane;
import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;

/**
 * Created by cvs20 on 19/05/15.
 */
public class BacklogCategoryScene extends TrackedTabPane {
    public BacklogCategoryScene(BacklogCategory selectedCategory) {
        super(ContentScene.BACKLOG_CATEGORY);

        // Define and add the tabs
        Tab categoryTab = new BacklogCategoryTab(selectedCategory);

        this.getTabs().addAll(categoryTab);  // Add the tabs to the pane
    }
}
