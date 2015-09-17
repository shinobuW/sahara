package seng302.group2.scenes.information.person;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.information.project.backlog.BacklogEditTab;
import seng302.group2.scenes.information.project.backlog.BacklogInfoTab;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.backlog.Backlog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A class for displaying the Person Scene
 *
 * @author crw73
 */
public class PersonScene extends TrackedTabPane {

    Collection<SearchableTab> searchableTabs = new ArrayList<>();

    Person currentPerson;
    boolean editScene = false;

    SearchableTab informationTab;
    SearchableTab loggingTab;
    SearchableTab editTab;


    /**
     * Basic constructor for the PersonScene.
     * @param currentPerson the person for which the information will be displayed.
     */
    public PersonScene(Person currentPerson) {
        super(ContentScene.PERSON, currentPerson);

        this.currentPerson = currentPerson;

        // Define and add the tabs
        updateAllTabs();

        Collections.addAll(searchableTabs, informationTab, loggingTab);
        this.getTabs().addAll(searchableTabs);  // Add the tabs to the pane
    }

    /**
     * Constructor for the PersonScene. This creates an instance of the PersonEditTab tab and displays it.
     * @param currentPerson the person who will be edited
     * @param editScene a boolean - if the scene is an edit scene
     */
    public PersonScene(Person currentPerson, boolean editScene) {
        super(ContentScene.PERSON_EDIT, currentPerson);

        this.currentPerson = currentPerson;
        this.editScene = editScene;

        // Define and add the tabs
        updateAllTabs();

        Collections.addAll(searchableTabs, editTab);
        this.getTabs().addAll(searchableTabs);  // Add the tabs to the pane
    }

    /**
     * Gets all the SearchableTabs on this scene
     * @return collection of SearchableTabs
     */
    @Override
    public Collection<SearchableTab> getSearchableTabs() {
        return searchableTabs;
    }


    @Override
    public void updateTabs() {
        Tab selectedTab = this.getSelectionModel().getSelectedItem();

        if (editScene) {
            if (editTab != selectedTab) {
                editTab = new PersonEditTab(currentPerson);
            }
        }
        else {
            if (informationTab != selectedTab) {
                informationTab = new PersonInfoTab(currentPerson);
            }
            if (loggingTab != selectedTab) {
                loggingTab = new PersonLoggingTab(currentPerson);
            }
        }
    }

    @Override
    public void updateAllTabs() {
        if (editScene) {
            editTab = new PersonEditTab(currentPerson);
        }
        else {
            informationTab = new PersonInfoTab(currentPerson);
            loggingTab = new PersonLoggingTab(currentPerson);
        }
    }
}