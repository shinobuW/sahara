package seng302.group2.scenes.information.person;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.workspace.Workspace;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * A class for displaying the person category. Contains information
 * about all the persons in the workspace.
 *
 * Created by btm38 on 14/07/15.
 */
public class PersonCategoryScene extends TrackedTabPane {

    Collection<SearchableTab> searchableTabs = new HashSet<>();

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

    @Override
    public Collection<SearchableTab> getSearchableTabs() {
        return searchableTabs;
    }
}
