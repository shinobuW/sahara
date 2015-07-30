package seng302.group2.scenes.information.project;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.workspace.project.Project;

/**
 * A class for displaying the project scene
 *
 * @author jml168
 */
public class ProjectScene extends TrackedTabPane {
    /**
     * Constructor for the Project Scene tab
     * 
     * @param currentProject The currently selected project. 
     */
    public ProjectScene(Project currentProject) {
        super(ContentScene.PROJECT, currentProject);

        // Define and add the tabs
        Tab informationTab = new ProjectInfoTab(currentProject);
        Tab allocation = new ProjectHistoryTab(currentProject);

        this.getTabs().addAll(informationTab, allocation);  // Add the tabs to the pane
    }

    /**
     * Constructor for the ProjectScene. This should only be used to display an edit tab.
     * @param currentProject the project to be edited.
     * @param editScene boolean - if the scene is an edit scene
     */
    public ProjectScene(Project currentProject, boolean editScene) {
        super(ContentScene.PROJECT_EDIT, currentProject);

        // Define and add the tabs
        Tab informationTab = new ProjectInfoTab(currentProject);
        Tab allocation = new ProjectHistoryTab(currentProject);

        this.getTabs().addAll(informationTab, allocation);  // Add the tabs to the pane
    }
}
