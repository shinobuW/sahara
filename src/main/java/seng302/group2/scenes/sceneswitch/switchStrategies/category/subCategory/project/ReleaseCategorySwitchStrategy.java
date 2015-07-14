package seng302.group2.scenes.sceneswitch.switchStrategies.category.subCategory.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seng302.group2.App;
import seng302.group2.scenes.information.project.release.ReleaseCategoryScene;
import seng302.group2.scenes.sceneswitch.switchStrategies.SubCategorySwitchStrategy;
import seng302.group2.workspace.categories.Category;
import seng302.group2.workspace.categories.subCategory.project.ReleaseCategory;

/**
 * A switch strategy for project release categories
 * Created by Jordane on 8/06/2015.
 */
public class ReleaseCategorySwitchStrategy implements SubCategorySwitchStrategy {
    transient Logger logger = LoggerFactory.getLogger(ReleaseCategorySwitchStrategy.class);

    @Override
    public void switchScene(Category releaseCategory) {
        if (releaseCategory instanceof ReleaseCategory) {
            App.mainPane.setContent(new ReleaseCategoryScene((ReleaseCategory) releaseCategory));
        }
        else {
            logger.warn("Tried changing to release cat scene with a non-release cat instance");
        }
    }
}
