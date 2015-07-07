package seng302.group2.scenes.sceneswitch.switchStrategies.category.subCategory.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.information.project.story.StoryCategoryScene;
import seng302.group2.scenes.listdisplay.categories.Category;
import seng302.group2.scenes.listdisplay.categories.subCategory.project.StoryCategory;
import seng302.group2.scenes.sceneswitch.switchStrategies.SubCategorySwitchStrategy;

/**
 * A switch strategy for project story categories
 * Created by Jordane on 8/06/2015.
 */
public class StoryCategorySwitchStrategy implements SubCategorySwitchStrategy {
    transient Logger logger = LoggerFactory.getLogger(StoryCategorySwitchStrategy.class);

    @Override
    public void switchScene(Category storyCategory) {
        if (storyCategory instanceof StoryCategory) {
            MainScene.contentPane.setContent(StoryCategoryScene.getStoryCategoryScene(
                    (StoryCategory) storyCategory));
        }
        else {
            logger.warn("Tried changing to story cat scene with a non-story cat instance");
        }
    }
}
