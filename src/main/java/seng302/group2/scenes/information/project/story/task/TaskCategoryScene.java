package seng302.group2.scenes.information.project.story.task;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.workspace.categories.subCategory.project.task.TaskCategory;

/**
 * Created by cvs20 on 28/07/15.
 */
public class TaskCategoryScene extends TrackedTabPane {
    /**
     * Constructor for the StoryCategoryScene class. Creates a tab
     * of StoryCategoryTab and displays it.
     * @param selectedCategory the current workspace
     */
    public TaskCategoryScene(TaskCategory selectedCategory) {
        super(TrackedTabPane.ContentScene.STORY_CATEGORY, selectedCategory.getSprint());

        // Define and add the tabs
        Tab informationTab = new TaskCategoryTab(selectedCategory);

        this.getTabs().addAll(informationTab);  // Add the tabs to the pane
    }
}
