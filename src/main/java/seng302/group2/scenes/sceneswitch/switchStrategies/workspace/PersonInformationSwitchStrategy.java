package seng302.group2.scenes.sceneswitch.switchStrategies.workspace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seng302.group2.App;
import seng302.group2.scenes.information.person.PersonScene;
import seng302.group2.scenes.sceneswitch.switchStrategies.InformationSwitchStrategy;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.person.Person;

/**
 * An switch strategy for people information and edit scenes
 * Created by Jordane on 8/06/2015.
 */
public class PersonInformationSwitchStrategy implements InformationSwitchStrategy {
    transient Logger logger = LoggerFactory.getLogger(PersonInformationSwitchStrategy.class);

    /**
     * Sets the main pane to be an instance of the Person Scene. 
     * @param item The SaharaItem for the scene to be constructed with. 
     */
    @Override
    public void switchScene(SaharaItem item) {
        if (item instanceof Person) {
            App.mainPane.setContent(new PersonScene((Person) item));
        }
        else {
            // Bad call
            logger.warn("Tried changing to person scene with a non-person instance");
        }
    }

    /**
     * Sets the main pane to be an instance of the Person Scene.
     * @param item The SaharaItem for the scene to be constructed with.
     * @param editScene Whether the edit scene is to be shown.
     */
    @Override
    public void switchScene(SaharaItem item, boolean editScene) {
        if (item instanceof Person) {
            if (editScene) {
                App.mainPane.setContent(new PersonScene((Person)item, true));
            }
            else {
                switchScene(item);
            }
        }
        else {
            // Bad call
            logger.warn("Tried changing to person scene with a non-person instance");
        }
    }
}
