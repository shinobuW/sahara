package seng302.group2.scenes.dialog;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.tasks.Task;

import java.util.Map;

import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * Created by cvs20 on 29/07/15.
 */
public class CreateTaskDialog extends Dialog<Map<String, String>> {
    static Boolean correctShortName = Boolean.FALSE;
    static Boolean correctDescription = Boolean.FALSE;
    // TODO Add effort left when its implemented

    public CreateTaskDialog() {
        correctShortName = false;
        correctDescription = false;

        // Initialise Dialog
        this.setTitle("New Task");
        this.getDialogPane().setStyle(" -fx-max-width:600px; -fx-max-height: 400px; -fx-pref-width: 600px; "
                + "-fx-pref-height: 400px;");
        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);

        ButtonType btnTypeCreate = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(btnTypeCreate, ButtonType.CANCEL);

        // Add elements to grid
        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        ComboBox<Project> projectComboBox = new ComboBox<>();
        projectComboBox.setPrefWidth(180);
        ComboBox<Backlog> backlogComboBox = new ComboBox<>();
        backlogComboBox.setPrefWidth(180);
        backlogComboBox.setDisable(true);
        ComboBox<Story> storyComboBox = new ComboBox<>();
        storyComboBox.setPrefWidth(180);
        storyComboBox.setDisable(true);
        CustomTextArea descriptionTextArea = new CustomTextArea("Description:");

        //String firstPItem = Global.currentWorkspace.getProjects().get(0).toString();
        //projectComboBox.setValue(firstPItem);

        for (Project project : Global.currentWorkspace.getProjects()) {
            projectComboBox.getItems().add(project);
        }

        grid.getChildren().addAll(shortNameCustomField, projectComboBox,
                backlogComboBox,storyComboBox, descriptionTextArea);

        this.getDialogPane().setContent(grid);
        Platform.runLater(() -> shortNameCustomField.getTextField().requestFocus());
        Node createButton = this.getDialogPane().lookupButton(btnTypeCreate);
        createButton.setDisable(true);

        shortNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctShortName = validateShortName(shortNameCustomField, null);
                createButton.setDisable(!(correctShortName));
            });

        projectComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null) {
                    return;
                }

                backlogComboBox.getItems().clear();
                storyComboBox.getItems().clear();
                for (Backlog backlog : newValue.getBacklogs()) {
                    backlogComboBox.getItems().add(backlog);
                }

                backlogComboBox.setDisable(false);
            });

        backlogComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null) {
                    return;
                }
                storyComboBox.getItems().clear();
                for (Story story : newValue.getStories()) {
                    storyComboBox.getItems().add(story);
                }
                storyComboBox.setDisable(false);
            });

        this.setResultConverter(b -> {
                if (b == btnTypeCreate) {

                    if (correctShortName) {
                        //get user input
                        String shortName = shortNameCustomField.getText();
                        String description = descriptionTextArea.getText();
                        Task task = new Task(shortName, description);
                        storyComboBox.getSelectionModel().getSelectedItem().add(task);
                        App.mainPane.selectItem(task);
                        this.close();
                    }
                }
                return null;
            });
        this.setResizable(false);
        this.show();
    }
}
