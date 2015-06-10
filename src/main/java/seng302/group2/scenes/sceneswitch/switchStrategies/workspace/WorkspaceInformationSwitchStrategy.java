package seng302.group2.scenes.sceneswitch.switchStrategies.workspace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.information.workspace.WorkspaceEditScene;
import seng302.group2.scenes.information.workspace.WorkspaceScene;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.sceneswitch.switchStrategies.InformationSwitchStrategy;
import seng302.group2.workspace.Workspace;

/**
 * An switch strategy for people information and edit scenes
 * Created by Jordane on 8/06/2015.
 */
public class WorkspaceInformationSwitchStrategy implements InformationSwitchStrategy {
    transient Logger logger = LoggerFactory.getLogger(WorkspaceInformationSwitchStrategy.class);

    @Override
    public void switchScene(TreeViewItem item) {
        if (item instanceof Workspace) {
            MainScene.contentPane.setContent(new WorkspaceScene((Workspace) item));
        }
        else {
            // Bad call
            logger.warn("Tried changing to ws scene with a non-ws instance");
        }
    }

    @Override
    public void switchScene(TreeViewItem item, boolean editScene) {
        if (item instanceof Workspace) {
            if (editScene) {
                MainScene.contentPane.setContent(WorkspaceEditScene.getWorkspaceEditScene((Workspace) item));
            }
            else {
                switchScene(item);
            }
        }
        else {
            // Bad call
            logger.warn("Tried changing to ws scene with a non-ws instance");
        }
    }
}
