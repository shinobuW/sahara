package seng302.group2.scenes.sceneswitch.switchStrategies.workspace;

import seng302.group2.App;
import seng302.group2.scenes.information.team.TeamScene;
import seng302.group2.scenes.sceneswitch.switchStrategies.InformationSwitchStrategy;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.team.Team;

/**
 * An switch strategy for people information and edit scenes
 * Created by Jordane on 8/06/2015.
 */
public class TeamInformationSwitchStrategy implements InformationSwitchStrategy {

    /**
     * Sets the main pane to be an instance of the Team Scene. 
     * @param item The SaharaItem for the scene to be constructed with. 
     */
    @Override
    public void switchScene(SaharaItem item) {
        if (item instanceof Team) {
            App.mainPane.setContent(new TeamScene((Team) item));
        }
        else {
            // Bad call
        }
    }

    /**
     * Sets the main pane to be an instance of the Team Scene.
     * @param item The SaharaItem for the scene to be constructed with.
     * @param editScene Whether the edit scene is to be shown.
     */
    @Override
    public void switchScene(SaharaItem item, boolean editScene) {
        if (item instanceof Team) {
            if (editScene) {
                App.mainPane.setContent(new TeamScene((Team) item, true));
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
