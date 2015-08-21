package seng302.group2.scenes.sceneswitch.switchStrategies.workspace.project;

import seng302.group2.App;
import seng302.group2.scenes.information.project.backlog.BacklogScene;
import seng302.group2.scenes.sceneswitch.switchStrategies.InformationSwitchStrategy;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.project.backlog.Backlog;

/**
 * An switch strategy for people information and edit scenes
 * Created by Jordane on 8/06/2015.
 */
public class BacklogInformationSwitchStrategy implements InformationSwitchStrategy {

    /**
     * Sets the main pane to be an instance of the Backlog Scene. 
     * @param item The SaharaItem for the scene to be constructed with. 
     */
    @Override
    public void switchScene(SaharaItem item) {
        if (item instanceof Backlog) {
            App.mainPane.setContent(new BacklogScene((Backlog) item));
        }
        else {
            // Bad call
        }
    }

    /**
     * Sets the main pane to be an instance of the Backlog Scene.
     * @param item The SaharaItem for the scene to be constructed with.
     * @param editScene Whether the edit scene is to be shown.
     */
    @Override
    public void switchScene(SaharaItem item, boolean editScene) {
        if (item instanceof Backlog) {
            if (editScene) {
                App.mainPane.setContent(new BacklogScene((Backlog) item, true));
            }
            else {
                switchScene(item);
            }
        }
        else {
            // Bad call
        }
    }
}
