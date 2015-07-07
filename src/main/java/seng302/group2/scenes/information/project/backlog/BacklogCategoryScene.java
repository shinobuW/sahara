package seng302.group2.scenes.information.project.backlog;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.listdisplay.categories.subCategory.project.BacklogCategory;

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
