package seng302.group2.scenes.information.project.story.task;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.util.validation.ShortNameValidator;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.story.tasks.Log;
import seng302.group2.workspace.project.story.tasks.Task;

import java.util.ArrayList;

/**
 * A class for displaying the Task edit scene.
 * Created by cvs20 on 28/07/15.
 */
@Deprecated
public class TaskEditScene {
    /**
     * Gets the Task Edit information scene.
     *
     * @param currentTask The task to show the information of
     * @return The task Edit information display
     */
    public static ScrollPane getTaskEditScene(Task currentTask) {
        Pane informationPane = new VBox(10);
        informationPane.setPadding(new Insets(25, 25, 25, 25));

        Button btnCancel = new Button("Cancel");
        Button btnSave = new Button("Done");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.TOP_LEFT);
        buttons.getChildren().addAll(btnSave, btnCancel);

        RequiredField shortNameCustomField = new RequiredField("Task Name:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Task Description:", 300);
        CustomTextArea impedimentsTextArea = new CustomTextArea("Task Impediments:", 300);

        shortNameCustomField.setMaxWidth(275);
        descriptionTextArea.setMaxWidth(275);
        impedimentsTextArea.setMaxWidth(275);

        shortNameCustomField.setText(currentTask.getShortName());
        descriptionTextArea.setText(currentTask.getDescription());
        impedimentsTextArea.setText(currentTask.getImpediments());

        informationPane.getChildren().addAll(shortNameCustomField,
                descriptionTextArea,
                impedimentsTextArea,
                buttons);

        btnSave.setOnAction((event) -> {
                boolean shortNameUnchanged = shortNameCustomField.getText().equals(
                        currentTask.getShortName());

                boolean descriptionUnchanged = descriptionTextArea.getText().equals(
                        currentTask.getDescription());

                boolean impedimentsUnchanged = impedimentsTextArea.getText().equals(
                        currentTask.getImpediments());



                if (shortNameUnchanged &&  descriptionUnchanged
                        && impedimentsUnchanged) {
                    // No changes
                    currentTask.switchToInfoScene();
                    return;
                }

                boolean correctShortName = ShortNameValidator.validateShortName(shortNameCustomField,
                        currentTask.getShortName());


                if (correctShortName) {
//                    Valid short name, make the edit
                    currentTask.edit(shortNameCustomField.getText(),
                            descriptionTextArea.getText(),
                            impedimentsTextArea.getText(),
                            Task.TASKSTATE.NOT_STARTED,
                            new ArrayList<Person>(), new ArrayList<Log>());

                    currentTask.switchToInfoScene();
                    App.mainPane.refreshTree();
                }
                else {
                    event.consume();
                }

            });

        btnCancel.setOnAction((event) -> {
                currentTask.switchToInfoScene();
            });

        ScrollPane wrapper = new ScrollPane(informationPane);
        wrapper.setStyle("-fx-background-color:transparent;");
        return wrapper;
    }
}
