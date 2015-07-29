package seng302.group2.scenes.dialog;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.workspace.SaharaItem;
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

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);

        // Add elements to grid
        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        CustomComboBox projectComboBox = new CustomComboBox("Project:", true);
        projectComboBox.getComboBox().setPrefWidth(180);
        CustomComboBox backlogComboBox = new CustomComboBox("Backlog:", true);
        backlogComboBox.getComboBox().setPrefWidth(180);
        CustomComboBox storyComboBox = new CustomComboBox("Story:", true);
        storyComboBox.getComboBox().setPrefWidth(180);
        CustomTextArea descriptionTextArea = new CustomTextArea("Description:");

        String firstPItem = Global.currentWorkspace.getProjects().get(0).toString();
        projectComboBox.setValue(firstPItem);

        for (SaharaItem project : Global.currentWorkspace.getProjects()) {
            projectComboBox.addToComboBox(project.toString());
        }

        grid.getChildren().addAll(shortNameCustomField, descriptionTextArea,projectComboBox,
                backlogComboBox,storyComboBox, buttons);

        this.getDialogPane().setContent(grid);
        Platform.runLater(() -> shortNameCustomField.getTextField().requestFocus());
        Node createButton = this.getDialogPane().lookupButton(btnTypeCreate);
        createButton.setDisable(true);

        shortNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctShortName = validateShortName(shortNameCustomField, null);
                createButton.setDisable(!(correctShortName));
            });

        projectComboBox.getComboBox().valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observable, String oldValue, String newValue) {
                Project selectedProject = new Project();
                for (Project project : Global.currentWorkspace.getProjects()) {
                    if (project.getShortName().equals(newValue)) {
                        selectedProject = project;
                    }
                }

                backlogComboBox.getComboBox().getItems().removeAll();
                for (Backlog backlog : selectedProject.getBacklogs()) {
                    backlogComboBox.addToComboBox(backlog.toString());
                }
            }
        });

        backlogComboBox.getComboBox().valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observable, String oldValue, String newValue) {
                Project selectedProject = new Project();
                for (Project project : Global.currentWorkspace.getProjects()) {
                    if (project.getShortName().equals(projectComboBox.getValue())) {
                        selectedProject = project;
                    }
                }

                Backlog selectedBacklog = new Backlog();
                for (Backlog backlog : selectedProject.getBacklogs()) {
                    if (backlog.getShortName().equals(newValue)) {
                        selectedBacklog = backlog;
                    }
                }

                storyComboBox.getComboBox().getItems().removeAll();
                for (Story story : selectedBacklog.getStories()) {
                    storyComboBox.addToComboBox(story.toString());
                }
            }
        });

        this.setResultConverter(b -> {
                if (b == btnTypeCreate) {
                    if (correctShortName) {
                        //get user input
                        String shortName = shortNameCustomField.getText();
                        String description = descriptionTextArea.getText();
                        Story story = new Story();
                        Task task = new Task(shortName, description);
                        story.add(task);
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
