package seng302.group2.scenes.information.project.sprint;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.workspace.project.sprint.Sprint;

/**
 * A class for displaying the sprint edit scene
 * Created by drm127 on 29/07/15.
 */

public class SprintEditScene {

    /**
     * Gets the Sprint Edit Scene
     *
     * @param currentSprint the sprint to edit
     * @return the Sprint Information edit scene
     */
    public static ScrollPane getSprintEditScene(Sprint currentSprint) {
        Pane informationPane = new VBox(10);

        //TODO

        ScrollPane wrapper = new ScrollPane(informationPane);
        wrapper.setStyle("-fx-background-color:transparent;");
        return wrapper;
    }
}
