package seng302.group2.scenes.information.person;

import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.workspace.Workspace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A class for displaying the person category. Contains information
 * about all the persons in the workspace.
 *
 * Created by btm38 on 14/07/15.
 */
public class PersonCategoryScene extends TrackedTabPane {

    Collection<SearchableTab> searchableTabs = new ArrayList<>();

    /**
     * Constructor for the PersonCategoryScene class. Creates a tab
     * of PersonCategoryTab and displays it.
     * @param currentWorkspace the current workspace
     */
    public PersonCategoryScene(Workspace currentWorkspace) {
        super(ContentScene.PERSON_CATEGORY, currentWorkspace);

        // Define and add the tabs
        SearchableTab categoryTab = new PersonCategoryTab(currentWorkspace);

        Collections.addAll(searchableTabs, categoryTab);
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
}
