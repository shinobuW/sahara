package seng302.group2.scenes.sceneswitch.switchStrategies.workspace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seng302.group2.App;
import seng302.group2.scenes.information.team.TeamEditScene;
import seng302.group2.scenes.information.team.TeamScene;
import seng302.group2.scenes.sceneswitch.switchStrategies.InformationSwitchStrategy;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.team.Team;

/**
 * An switch strategy for people information and edit scenes
 * Created by Jordane on 8/06/2015.
 */
public class TeamInformationSwitchStrategy implements InformationSwitchStrategy {
    transient Logger logger = LoggerFactory.getLogger(TeamInformationSwitchStrategy.class);

    @Override
    public void switchScene(SaharaItem item) {
        if (item instanceof Team) {
            App.mainPane.setContent(new TeamScene((Team) item));
        }
        else {
            // Bad call
            logger.warn("Tried changing to team scene with a non-person instance");
        }
    }

    @Override
    public void switchScene(SaharaItem item, boolean editScene) {
        if (item instanceof Team) {
            if (editScene) {
                App.mainPane.setContent(new TeamEditScene((Team) item));
            }
            else {
                switchScene(item);
            }
        }
        else {
            // Bad call
            logger.warn("Tried changing to team scene with a non-person instance");
        }
    }
}
