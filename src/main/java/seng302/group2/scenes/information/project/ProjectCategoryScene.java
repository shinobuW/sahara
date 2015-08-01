package seng302.group2.scenes.information.project;

import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.workspace.Workspace;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * A class for displaying the project category. Contains information
 * about all the projects in the workspace.
 *
 * Created by btm38 on 14/07/15.
 */
public class ProjectCategoryScene extends TrackedTabPane {

    Collection<SearchableTab> searchableTabs = new HashSet<>();

    /**
     * Constructor for the ProjectCategoryScene class. Creates a tab
     * of ProjectCategoryTab and displays it.
     * @param currentWorkspace the current workspace
     */
    public ProjectCategoryScene(Workspace currentWorkspace) {
        super(TrackedTabPane.ContentScene.PROJECT_CATEGORY, currentWorkspace);

        // Define and add the tabs
        SearchableTab categoryTab = new ProjectCategoryTab(currentWorkspace);
        Collections.addAll(searchableTabs, categoryTab);

        this.getTabs().addAll(searchableTabs);
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
