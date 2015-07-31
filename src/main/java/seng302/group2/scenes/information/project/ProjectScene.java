package seng302.group2.scenes.information.project;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.project.Project;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * A class for displaying the project scene
 *
 * @author jml168
 */
public class ProjectScene extends TrackedTabPane {

    Collection<SearchableTab> searchableTabs = new HashSet<>();

    /**
     * Constructor for the Project Scene tab
     * 
     * @param currentProject The currently selected project. 
     */
    public ProjectScene(Project currentProject) {
        super(ContentScene.PROJECT, currentProject);

        // Define and add the tabs
        SearchableTab informationTab = new ProjectInfoTab(currentProject);
        SearchableTab allocation = new ProjectHistoryTab(currentProject);
        Collections.addAll(searchableTabs, informationTab, allocation);

        this.getTabs().addAll(searchableTabs);  // Add the tabs to the pane
    }

    /**
     * Constructor for the ProjectScene. This creates an instance of the ProjectEditTab tab and displays it.
     * @param currentProject the project to be edited.
     * @param editScene a boolean - if the scene is an edit scene
     */
    public ProjectScene(Project currentProject, boolean editScene) {
        super(ContentScene.PROJECT_EDIT, currentProject);

        // Define and add the tabs
        Tab editTab = new ProjectEditTab(currentProject);

        this.getTabs().addAll(editTab);  // Add the tabs to the pane
    }

    @Override
    public Collection<SearchableTab> getSearchableTabs() {
        return searchableTabs;
    }
}
