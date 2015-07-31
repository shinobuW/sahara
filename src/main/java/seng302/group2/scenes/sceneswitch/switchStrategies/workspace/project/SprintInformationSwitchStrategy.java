package seng302.group2.scenes.sceneswitch.switchStrategies.workspace.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seng302.group2.App;
import seng302.group2.scenes.information.project.sprint.SprintScene;
import seng302.group2.scenes.sceneswitch.switchStrategies.InformationSwitchStrategy;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.project.sprint.Sprint;

/**
 * A switch strategy for sprint information and edit scenes
 * Created by drm127 on 29/07/15.
 */
public class SprintInformationSwitchStrategy implements InformationSwitchStrategy {
    transient Logger logger = LoggerFactory.getLogger(SprintInformationSwitchStrategy.class);

    /**
     * Sets the main pane to be an instance of the Sprint Scene.
     * @param item The SaharaItem for the scene to be constructed with.
     */
    @Override
    public void switchScene(SaharaItem item) {
        if (item instanceof Sprint) {
            App.mainPane.setContent(new SprintScene((Sprint) item));
        }
        else {
            // Bad call
            logger.warn("Tried changing to sprint scene with a non-sprint instance");
        }
    }

    /**
     * Sets the main pane to be an instance of the Sprint Edit Scene.
     * @param item The SaharaItem for the scene to be constructed with.
     * @param editScene Whether the edit scene is to be shown.
     */
    @Override
    public void switchScene(SaharaItem item, boolean editScene) {
        if (item instanceof Sprint) {
            if (editScene) {
                App.mainPane.setContent(new SprintScene((Sprint) item, true));
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
