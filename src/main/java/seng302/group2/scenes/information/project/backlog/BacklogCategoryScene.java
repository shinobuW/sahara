package seng302.group2.scenes.information.project.backlog;

import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.categories.subCategory.project.BacklogCategory;

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

    Collection<SearchableTab> searchableTabs = new ArrayList<>();

    /**
     * Constructor for the BacklogCategoryScene class. Creates a tab
     * of BacklogCategoryTab and displays it.
     * @param selectedCategory the current workspace
     */
    public BacklogCategoryScene(BacklogCategory selectedCategory) {
        super(ContentScene.BACKLOG_CATEGORY, selectedCategory.getProject());

        // Define and add the tabs
        SearchableTab categoryTab = new BacklogCategoryTab(selectedCategory);
        SearchableTab scaleInfoTab = new StoryEstimationScaleInfoTab();

        Collections.addAll(searchableTabs, categoryTab, scaleInfoTab);

        this.getTabs().addAll(searchableTabs);  // Add the tabs to the pane
    }

    /**
     * Gets all the SearchableTabs on this scene
     * @return collection of SearchableTabs
     */
    @Override
    public Collection<SearchableTab> getSearchableTabs() {
        return searchableTabs;
    }
}
