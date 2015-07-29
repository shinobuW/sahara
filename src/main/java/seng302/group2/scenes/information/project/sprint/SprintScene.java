package seng302.group2.scenes.information.project.sprint;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.workspace.project.sprint.Sprint;

/**
 * A class for displaying the sprint scene.
 *
 * Created by drm127 on 29/07/15.
 */
public class SprintScene extends TrackedTabPane {

    /**
     * Basic constructor for the sprint scene.
     * @param currentSprint the sprint for which information will be displayed.
     */
    public SprintScene(Sprint currentSprint) {
        super(ContentScene.SPRINT, currentSprint);

        //Define and add the tabs
        Tab informationTab = new SprintInfoTab(currentSprint);

        this.getTabs().addAll(informationTab);  // Add the tabs to the pane
    }
}
