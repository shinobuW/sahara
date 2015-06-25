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
    public ProjectScene(Project currentProject) {
        super(ContentScene.PROJECT);

        // Define and add the tabs
        Tab informationTab = new ProjectInfoTab(currentProject);
        Tab allocation = new ProjectHistoryTab(currentProject);

        this.getTabs().addAll(informationTab, allocation);  // Add the tabs to the pane
    }
}
