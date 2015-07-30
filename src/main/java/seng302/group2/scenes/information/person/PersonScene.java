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
        super(ContentScene.PERSON, currentPerson);

        // Define and add the tabs
        Tab informationTab = new PersonInfoTab(currentPerson);

        this.getTabs().addAll(informationTab);  // Add the tabs to the pane
    }

    /**
     * Constructor for the PersonScene. This should only be used to display an edit tab.
     * @param currentPerson the person who will be edited
     * @param editScene boolean - if the scene is an edit scene
     */
    public PersonScene(Person currentPerson, boolean editScene) {
        super(ContentScene.PERSON_EDIT, currentPerson);

        // Define and add the tabs
        Tab editTab = new PersonEditTab(currentPerson);

        this.getTabs().addAll(editTab);  // Add the tabs to the pane
    }
}