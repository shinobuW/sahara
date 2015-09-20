package seng302.group2.scenes.information.project.story.task;

import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.categories.subCategory.project.task.TaskCategory;

import java.util.Collections;

/**
 * Created by cvs20 on 28/07/15.
 */
public class TaskCategoryScene extends TrackedTabPane {

    TaskCategory selectedCategory;

    SearchableTab categoryTab;

    /**
     * Constructor for the StoryCategoryScene class. Creates a tab
     * of StoryCategoryTab and displays it.
     * @param selectedCategory the current workspace
     */
    public TaskCategoryScene(TaskCategory selectedCategory) {
        super(TrackedTabPane.ContentScene.STORY_CATEGORY, selectedCategory.getSprint());

        this.selectedCategory = selectedCategory;

        // Define and add the tabs
        categoryTab = new TaskCategoryTab(selectedCategory);

        Collections.addAll(getSearchableTabs(), categoryTab);
        this.getTabs().addAll(getSearchableTabs());  // Add the tabs to the pane
    }

    @Override
    public void done() {

    }

    @Override
    public void edit() {

    }

    @Override
    public void cancel() {

    }
}
