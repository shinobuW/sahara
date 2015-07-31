package seng302.group2.scenes.information.person;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.person.Person;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * A class for displaying the Person Scene
 *
 * @author crw73
 */
public class PersonScene extends TrackedTabPane {

    Collection<SearchableTab> searchableTabs = new HashSet<>();

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
     * Constructor for the PersonScene. This creates an instance of the PersonEditTab tab and displays it.
     * @param currentPerson the person who will be edited
     * @param editScene a boolean - if the scene is an edit scene
     */
    public PersonScene(Person currentPerson, boolean editScene) {

        super(ContentScene.PERSON_EDIT, currentPerson);

        // Define and add the tabs
        SearchableTab editTab = new PersonEditTab(currentPerson);
        Collections.addAll(searchableTabs, editTab);

        this.getTabs().addAll(searchableTabs);  // Add the tabs to the pane
    }

    @Override
    public Collection<SearchableTab> getSearchableTabs() {
        return searchableTabs;
    }
}