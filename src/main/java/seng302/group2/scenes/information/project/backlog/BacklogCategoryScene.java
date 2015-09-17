package seng302.group2.scenes.information.project.backlog;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.information.person.PersonCategoryTab;
import seng302.group2.workspace.categories.subCategory.project.BacklogCategory;
import seng302.group2.workspace.workspace.Workspace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A class for displaying the backlog category. Contains information
 * about all the backlogs in the project.
 *
 * Created by btm38 on 14/07/15.
 */
public class BacklogCategoryScene extends TrackedTabPane {

    BacklogCategory selectedCategory;
    SearchableTab categoryTab;
    SearchableTab scaleInfoTab;

    /**
     * Constructor for the BacklogCategoryScene class. Creates a tab
     * of BacklogCategoryTab and displays it.
     * @param selectedCategory the current workspace
     */
    public BacklogCategoryScene(BacklogCategory selectedCategory) {
        super(ContentScene.BACKLOG_CATEGORY, selectedCategory.getProject());

        this.selectedCategory = selectedCategory;

        // Define and add the tabs
        updateAllTabs();

        Collections.addAll(getSearchableTabs(), categoryTab, scaleInfoTab);

        this.getTabs().addAll(getSearchableTabs());  // Add the tabs to the pane
    }

    @Override
    public void updateTabs() {
        Tab selectedTab = this.getSelectionModel().getSelectedItem();

        if (categoryTab != selectedTab) {
            categoryTab = new BacklogCategoryTab(selectedCategory);
        }
        if (scaleInfoTab != selectedTab) {
            scaleInfoTab = new StoryEstimationScaleInfoTab();
        }
    }

    @Override
    public void updateAllTabs() {
        categoryTab = new BacklogCategoryTab(selectedCategory);
        scaleInfoTab = new StoryEstimationScaleInfoTab();
    }
}
