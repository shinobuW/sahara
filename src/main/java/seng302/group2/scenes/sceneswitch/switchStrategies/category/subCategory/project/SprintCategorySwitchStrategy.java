package seng302.group2.scenes.sceneswitch.switchStrategies.category.subCategory.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seng302.group2.App;
import seng302.group2.scenes.information.project.sprint.SprintCategoryScene;
import seng302.group2.scenes.sceneswitch.switchStrategies.SubCategorySwitchStrategy;
import seng302.group2.workspace.categories.Category;
import seng302.group2.workspace.categories.subCategory.project.SprintCategory;

/**
 * A switch strategy for project sprint categories
 * Created by drm127 on 29/07/15.
 */
public class SprintCategorySwitchStrategy implements SubCategorySwitchStrategy {
    transient Logger logger = LoggerFactory.getLogger(SprintCategorySwitchStrategy.class);

    /**
     * Sets the Main Pane to be an instance of the SprintCategoryScene.
     *
     * @param sprintCategory The Sprint Category to display.
     */
    @Override
    public void switchScene(Category sprintCategory) {
        if (sprintCategory instanceof SprintCategory) {
            App.mainPane.setContent(new SprintCategoryScene((SprintCategory) sprintCategory));
        }
        else {
            logger.warn("Tried changing to sprint cat scene with a non-sprint cat instance");
        }
    }
}
