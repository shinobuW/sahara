package seng302.group2.scenes.information.project.story.task;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.information.project.sprint.SprintCategoryTab;
import seng302.group2.workspace.categories.subCategory.project.task.TaskCategory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by cvs20 on 28/07/15.
 */
public class TaskCategoryScene extends TrackedTabPane {

    TaskCategory selectedCategory;

    SearchableTab informationTab;

    /**
     * Constructor for the StoryCategoryScene class. Creates a tab
     * of StoryCategoryTab and displays it.
     * @param selectedCategory the current workspace
     */
    public TaskCategoryScene(TaskCategory selectedCategory) {
        super(TrackedTabPane.ContentScene.STORY_CATEGORY, selectedCategory.getSprint());

        this.selectedCategory = selectedCategory;

        // Define and add the tabs
        updateAllTabs();

        Collections.addAll(getSearchableTabs(), informationTab);
        this.getTabs().addAll(getSearchableTabs());  // Add the tabs to the pane
    }

    @Override
    public void updateTabs() {
        Tab selectedTab = this.getSelectionModel().getSelectedItem();
        if (informationTab != selectedTab) {
            informationTab = new TaskCategoryTab(selectedCategory);
        }
    }

    @Override
    public void updateAllTabs() {
        informationTab = new TaskCategoryTab(selectedCategory);
    }
}
