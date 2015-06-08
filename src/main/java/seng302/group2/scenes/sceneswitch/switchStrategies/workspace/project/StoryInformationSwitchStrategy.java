package seng302.group2.scenes.sceneswitch.switchStrategies.workspace.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.information.project.ProjectEditScene;
import seng302.group2.scenes.information.project.ProjectScene;
import seng302.group2.scenes.information.story.StoryEditScene;
import seng302.group2.scenes.information.story.StoryScene;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.sceneswitch.switchStrategies.InformationSwitchStrategy;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.story.Story;

/**
 * An switch strategy for people information and edit scenes
 * Created by Jordane on 8/06/2015.
 */
public class StoryInformationSwitchStrategy implements InformationSwitchStrategy {
    Logger logger = LoggerFactory.getLogger(StoryInformationSwitchStrategy.class);

    @Override
    public void switchScene(TreeViewItem item) {
        if (item instanceof Story) {
            MainScene.contentPane.setContent(new StoryScene((Story) item));
        }
        else {
            // Bad call
            logger.warn("Tried changing to story scene with a non-story instance");
        }
    }

    @Override
    public void switchScene(TreeViewItem item, boolean editScene) {
        if (item instanceof Story) {
            if (editScene) {
                MainScene.contentPane.setContent(StoryEditScene.getStoryEditScene(
                        (Story) item));
            }
            else {
                switchScene(item);
            }
        }
        else {
            // Bad call
            logger.warn("Tried changing to story scene with a non-story instance");
        }
    }
}
