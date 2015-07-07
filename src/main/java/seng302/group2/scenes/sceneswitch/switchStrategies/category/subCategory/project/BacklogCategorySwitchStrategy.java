package seng302.group2.scenes.sceneswitch.switchStrategies.category.subCategory.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.information.project.backlog.BacklogCategoryScene;
import seng302.group2.scenes.listdisplay.categories.Category;
import seng302.group2.scenes.listdisplay.categories.subCategory.project.BacklogCategory;
import seng302.group2.scenes.sceneswitch.switchStrategies.SubCategorySwitchStrategy;

/**
 * A switch strategy for project backlog categories
 * Created by Jordane on 8/06/2015.
 */
public class BacklogCategorySwitchStrategy implements SubCategorySwitchStrategy {
    transient Logger logger = LoggerFactory.getLogger(BacklogCategorySwitchStrategy.class);

    @Override
    public void switchScene(Category backlogCategory) {
        if (backlogCategory instanceof BacklogCategory) {
            MainScene.contentPane.setContent(new BacklogCategoryScene((BacklogCategory) backlogCategory));
        }
        else {
            logger.warn("Tried changing to backlog cat scene with a non-backlog cat instance");
        }
    }
}
