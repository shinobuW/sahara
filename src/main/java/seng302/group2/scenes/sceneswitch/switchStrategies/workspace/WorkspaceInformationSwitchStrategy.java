package seng302.group2.scenes.sceneswitch.switchStrategies.workspace;

import seng302.group2.App;
import seng302.group2.scenes.information.StickyBar;
import seng302.group2.scenes.information.workspace.WorkspaceScene;
import seng302.group2.scenes.sceneswitch.switchStrategies.InformationSwitchStrategy;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.workspace.Workspace;

/**
 * An switch strategy for people information and edit scenes
 * Created by Jordane on 8/06/2015.
 */
public class WorkspaceInformationSwitchStrategy implements InformationSwitchStrategy {

    /**
     * Sets the main pane to be an instance of the Workspace Scene. 
     * @param item The SaharaItem for the scene to be constructed with. 
     */
    @Override
    public void switchScene(SaharaItem item) {
        if (item instanceof Workspace) {
            App.mainPane.setContent(new WorkspaceScene((Workspace) item));
            App.mainPane.stickyBar.construct(StickyBar.STICKYTYPE.INFO);
        }
        else {
            // Bad call
        }
    }

    /**
     * Sets the main pane to be an instance of the Workspace Scene.
     * @param item The SaharaItem for the scene to be constructed with.
     * @param editScene Whether the edit scene is to be shown.
     */
    @Override
    public void switchScene(SaharaItem item, boolean editScene) {
        if (item instanceof Workspace) {
            if (editScene) {
                App.mainPane.setContent(new WorkspaceScene((Workspace) item, true));
                App.mainPane.stickyBar.construct(StickyBar.STICKYTYPE.EDIT);

            }
            else {
                switchScene(item);
                App.mainPane.stickyBar.construct(StickyBar.STICKYTYPE.INFO);
            }
        }
        else {
            // Bad call
        }
    }
}
