package seng302.group2.scenes.sceneswitch.switchStrategies.workspace.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.information.backlog.BacklogEditScene;
import seng302.group2.scenes.information.backlog.BacklogScene;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.sceneswitch.switchStrategies.InformationSwitchStrategy;
import seng302.group2.workspace.project.backlog.Backlog;

/**
 * An switch strategy for people information and edit scenes
 * Created by Jordane on 8/06/2015.
 */
public class BacklogInformationSwitchStrategy implements InformationSwitchStrategy {
    Logger logger = LoggerFactory.getLogger(BacklogInformationSwitchStrategy.class);

    @Override
    public void switchScene(TreeViewItem item) {
        if (item instanceof Backlog) {
            MainScene.contentPane.setContent(new BacklogScene((Backlog) item));
        }
        else {
            // Bad call
            logger.warn("Tried changing to backlog scene with a non-backlog instance");
        }
    }

    @Override
    public void switchScene(TreeViewItem item, boolean editScene) {
        if (item instanceof Backlog) {
            if (editScene) {
                MainScene.contentPane.setContent(new BacklogEditScene((Backlog) item));
            }
            else {
                switchScene(item);
            }
        }
        else {
            // Bad call
            logger.warn("Tried changing to backlog scene with a non-backlog instance");
        }
    }
}
