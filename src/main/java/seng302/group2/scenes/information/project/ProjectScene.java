package seng302.group2.scenes.information.project;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.information.roadMap.RoadMapEditTab;
import seng302.group2.scenes.information.roadMap.RoadMapInfoTab;
import seng302.group2.workspace.project.Project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A class for displaying the project scene
 *
 * @author jml168
 */
public class ProjectScene extends TrackedTabPane {

    Collection<SearchableTab> searchableTabs = new ArrayList<>();

    SearchableTab informationTab;
    SearchableTab allocation;
    SearchableTab projectLogs;
    SearchableTab editTab;

    Project currentProject;
    boolean editScene = false;

    /**
     * Constructor for the Project Scene tab
     * 
     * @param currentProject The currently selected project. 
     */
    public ProjectScene(Project currentProject) {
        super(ContentScene.PROJECT, currentProject);

        this.currentProject = currentProject;

        // Define and add the tabs
        informationTab = new ProjectInfoTab(currentProject);
        allocation = new ProjectHistoryTab(currentProject);
        projectLogs = new ProjectLoggingTab(currentProject);

        //updateAllTabs();

        Collections.addAll(searchableTabs, informationTab, allocation, projectLogs);

        this.getTabs().addAll(searchableTabs);  // Add the tabs to the pane
    }

    /**
     * Constructor for the ProjectScene. This creates an instance of the ProjectEditTab tab and displays it.
     * @param currentProject the project to be edited.
     * @param editScene a boolean - if the scene is an edit scene
     */
    public ProjectScene(Project currentProject, boolean editScene) {
        super(ContentScene.PROJECT_EDIT, currentProject);

        this.currentProject = currentProject;
        this.editScene = editScene;

        // Define and add the tabs
        editTab = new ProjectEditTab(currentProject);
        //updateAllTabs();

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

}
