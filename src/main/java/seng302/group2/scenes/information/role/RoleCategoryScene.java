package seng302.group2.scenes.information.role;

import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.workspace.Workspace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A class for displaying the role category. Contains information
 * about all the roles in the workspace.
 *
 * Created by btm38 on 14/07/15.
 */
public class RoleCategoryScene extends TrackedTabPane {

    Collection<SearchableTab> searchableTabs = new ArrayList<>();

    /**
     * Constructor for the RoleCategoryScene class. Creates a tab
     * of RoleCategoryTab and displays it.
     * @param currentWorkspace the current workspace
     */
    public RoleCategoryScene(Workspace currentWorkspace) {
        super(ContentScene.ROLE_CATEGORY, currentWorkspace);

        // Define and add the tabs
        SearchableTab categoryTab = new RoleCategoryTab(currentWorkspace);
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