package seng302.group2.scenes.information.project.story.task;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.workspace.project.story.tasks.Task;

/**
 * The task information tab.
 * Created by cvs20 on 28/07/15.
 */
public class TaskInfoTab extends Tab {

    /**
     * Constructor for the Task Info Tab
     *
     * @param currentTask The currently selected Task
     */
    public TaskInfoTab(Task currentTask) {

        this.setText("Basic Information");
        Pane basicInfoPane = new VBox(10);

        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        Label title = new TitleLabel(currentTask.getShortName());

        Button btnEdit = new Button("Edit");

        basicInfoPane.getChildren().add(title);
        basicInfoPane.getChildren().add(new Label("Story Description: "
                + currentTask.getDescription()));
        basicInfoPane.getChildren().add(new Label("Impediments: "
                + currentTask.getImpediments()));
        basicInfoPane.getChildren().add(new Label("Task State: "
                + currentTask.getState().toString()));
        basicInfoPane.getChildren().add(new Label("Responsibilities: "
                + currentTask.getResponsibilities()));
        btnEdit.setOnAction((event) -> {
                currentTask.switchToInfoScene(true);
            });

    }
}