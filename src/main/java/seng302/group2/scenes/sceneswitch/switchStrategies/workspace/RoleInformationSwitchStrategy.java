package seng302.group2.scenes.sceneswitch.switchStrategies.workspace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seng302.group2.App;
import seng302.group2.scenes.information.role.RoleScene;
import seng302.group2.scenes.sceneswitch.switchStrategies.InformationSwitchStrategy;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.role.Role;

/**
 * An switch strategy for people information and edit scenes
 * Created by Jordane on 8/06/2015.
 */
public class RoleInformationSwitchStrategy implements InformationSwitchStrategy {
    transient Logger logger = LoggerFactory.getLogger(RoleInformationSwitchStrategy.class);

    /**
     * Sets the main pane to be an instance of the Role Scene. 
     * @param item The SaharaItem for the scene to be constructed with. 
     */
    @Override
    public void switchScene(SaharaItem item) {
        if (item instanceof Role) {
            App.mainPane.setContent(new RoleScene((Role) item));
        }
        else {
            // Bad call
            logger.warn("Tried changing to role scene with a non-project instance");
        }
    }

    /**
     * Sets the main pane to be an instance of the Role Edit Scene.
     * @param item The SaharaItem for the scene to be constructed with.
     * @param editScene Whether the edit scene is to be shown.
     */
    @Override
    public void switchScene(SaharaItem item, boolean editScene) {
        if (item instanceof Role) {
            if (editScene) {
                logger.error("Currently there is no role edit scene");
            }
            else {
                switchScene(item);
            }
        }
        else {
            // Bad call
            logger.warn("Tried changing to project scene with a non-project instance");
        }
    }
}
