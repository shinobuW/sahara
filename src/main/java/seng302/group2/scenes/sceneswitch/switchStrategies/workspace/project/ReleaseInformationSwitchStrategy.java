package seng302.group2.scenes.sceneswitch.switchStrategies.workspace.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.information.backlog.BacklogEditScene;
import seng302.group2.scenes.information.backlog.BacklogScene;
import seng302.group2.scenes.information.release.ReleaseScene;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.sceneswitch.switchStrategies.InformationSwitchStrategy;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.release.Release;

/**
 * An switch strategy for people information and edit scenes
 * Created by Jordane on 8/06/2015.
 */
public class ReleaseInformationSwitchStrategy implements InformationSwitchStrategy {
    Logger logger = LoggerFactory.getLogger(ReleaseInformationSwitchStrategy.class);

    @Override
    public void switchScene(TreeViewItem item) {
        if (item instanceof Release) {
            MainScene.contentPane.setContent(new ReleaseScene((Release) item));
        }
        else {
            // Bad call
            logger.warn("Tried changing to release scene with a non-release instance");
        }
    }

    @Override
    public void switchScene(TreeViewItem item, boolean editScene) {
        if (item instanceof Release) {
            if (editScene) {
                logger.warn("There is currently no release edit scene");
            }
            else {
                switchScene(item);
            }
        }
        else {
            // Bad call
            logger.warn("Tried changing to release scene with a non-release instance");
        }
    }
}
