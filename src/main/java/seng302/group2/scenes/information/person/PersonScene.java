package seng302.group2.scenes.information.person;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.workspace.person.Person;

/**
 * A class for displaying the Person Scene
 *
 * @author crw73
 */
public class PersonScene extends TrackedTabPane {
    /**
     * Basic constructor for the PersonScene.
     * @param currentPerson the person for which the information will be displayed.
     */
    public PersonScene(Person currentPerson) {
        super(ContentScene.PROJECT, currentPerson);

        // Define and add the tabs
        Tab informationTab = new PersonInfoTab(currentPerson);

        this.getTabs().addAll(informationTab);  // Add the tabs to the pane
    }
}