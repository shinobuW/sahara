package seng302.group2.scenes.sceneswitch.switchStrategies.category.subCategory.project.subCategory.sprint;

import seng302.group2.App;
import seng302.group2.scenes.information.project.story.task.TaskCategoryScene;
import seng302.group2.scenes.sceneswitch.switchStrategies.SubCategorySwitchStrategy;
import seng302.group2.workspace.categories.Category;
import seng302.group2.workspace.categories.subCategory.project.task.TaskCategory;

/**
 * Created by cvs20 on 28/07/15.
 */
public class TaskCategorySwitchStrategy implements SubCategorySwitchStrategy {

    /**
     * Sets the Main Pane to be an instance of the StoryCategoryScene.
     *
     * @param taskCategory The Story Category to display.
     */
    @Override
    public void switchScene(Category taskCategory) {
        if (taskCategory instanceof TaskCategory) {
            App.mainPane.setContent(new TaskCategoryScene((TaskCategory) taskCategory));
        }
    }

}
