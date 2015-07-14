package seng302.group2.scenes.information.project.backlog;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.workspace.categories.subCategory.project.BacklogCategory;

/**
 * A class for displaying the backlog category. Contains information
 * about all the backlogs in the project.
 *
 * Created by btm38 on 14/07/15.
 */
public class BacklogCategoryScene extends TrackedTabPane {
    /**
     * Constructor for the BacklogCategoryScene class. Creates a tab
     * of BacklogCategoryTab and displays it.
     * @param selectedCategory the current workspace
     */
    public BacklogCategoryScene(BacklogCategory selectedCategory) {
        super(ContentScene.BACKLOG_CATEGORY);

        // Define and add the tabs
        Tab categoryTab = new BacklogCategoryTab(selectedCategory);

        this.getTabs().addAll(categoryTab);  // Add the tabs to the pane
    }
}
