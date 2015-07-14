package seng302.group2.scenes.sceneswitch.switchStrategies.category.subCategory.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seng302.group2.App;
import seng302.group2.scenes.information.project.story.StoryCategoryScene;
import seng302.group2.scenes.sceneswitch.switchStrategies.SubCategorySwitchStrategy;
import seng302.group2.workspace.categories.Category;
import seng302.group2.workspace.categories.subCategory.project.StoryCategory;

/**
 * A switch strategy for project story categories
 * Created by Jordane on 8/06/2015.
 */
public class StoryCategorySwitchStrategy implements SubCategorySwitchStrategy {
    transient Logger logger = LoggerFactory.getLogger(StoryCategorySwitchStrategy.class);

    @Override
    public void switchScene(Category storyCategory) {
        if (storyCategory instanceof StoryCategory) {
            App.mainPane.setContent(new StoryCategoryScene((StoryCategory) storyCategory));
        }
        else {
            logger.warn("Tried changing to story cat scene with a non-story cat instance");
        }
    }
}
