/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.dialog;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.story.Story;

import java.util.Map;

import static seng302.group2.util.validation.NameValidator.validateName;
import static seng302.group2.util.validation.PriorityFieldValidator.validatePriorityField;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * Class to create a pop up dialog for creating a story
 *
 * @author swi67
 */
public class CreateStoryDialog {
    static Boolean correctShortName = Boolean.FALSE;
    static Boolean correctCreator = Boolean.FALSE;
    static Boolean correctLongName = Boolean.FALSE;
    static Boolean correctPriority = Boolean.FALSE;


    public CreateStoryDialog() {
        correctCreator = false;
        correctLongName = false;
        correctShortName = false;
        correctPriority = false;

        // Initialise Dialog
        javafx.scene.control.Dialog<Map<String, String>> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle("New Story");
        dialog.getDialogPane().setStyle(" -fx-max-width:600px; -fx-max-height: 500px; -fx-pref-width: 600px; "
                + "-fx-pref-height: 500px;");

        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);

        ButtonType btnTypeCreate = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnTypeCreate, ButtonType.CANCEL);

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);

        // Add elements to grid
        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        RequiredField longNameCustomField = new RequiredField("Long Name:");
        RequiredField creatorCustomField = new RequiredField("Creator:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Description:");
        CustomComboBox projectComboBox = new CustomComboBox("Project:", true);
        projectComboBox.getComboBox().setPrefWidth(180);
        RequiredField priorityNumberField = new RequiredField("Priority:");

        String firstItem = Global.currentWorkspace.getProjects().get(0).toString();
        projectComboBox.setValue(firstItem);

        for (SaharaItem project : Global.currentWorkspace.getProjects()) {
            projectComboBox.addToComboBox(project.toString());
        }

        grid.getChildren().addAll(shortNameCustomField, longNameCustomField, creatorCustomField,
                priorityNumberField, projectComboBox, descriptionTextArea, buttons);

        //Add grid of controls to dialog
        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> shortNameCustomField.getTextField().requestFocus());

        Node createButton = dialog.getDialogPane().lookupButton(btnTypeCreate);
        createButton.setDisable(true);

        //Validation
        shortNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctShortName = validateShortName(shortNameCustomField, null);
                createButton.setDisable(!(correctShortName && correctCreator && correctPriority && correctLongName));
            });

        creatorCustomField.getTextField().textProperty().addListener((observable, oldValue, newvalue) -> {
                correctCreator = validateName(creatorCustomField);
                createButton.setDisable(!(correctShortName && correctCreator && correctPriority && correctLongName));
            });

        longNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctLongName = validateName(longNameCustomField);
                createButton.setDisable(!(correctShortName && correctCreator && correctPriority && correctLongName));
            });

        priorityNumberField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctPriority = validatePriorityField(priorityNumberField, null, null);
                createButton.setDisable(!(correctShortName && correctCreator && correctPriority && correctLongName));
            });

        dialog.setResultConverter(b -> {
                if (b == btnTypeCreate) {
                    if (correctShortName && correctLongName && correctCreator && correctPriority) {
                        //get user input
                        String shortName = shortNameCustomField.getText();
                        String longName = longNameCustomField.getText();
                        String creator = creatorCustomField.getText();
                        String description = descriptionTextArea.getText();
                        Integer priority = Integer.parseInt(priorityNumberField.getText());

                        Project project = new Project();
                        for (SaharaItem item : Global.currentWorkspace.getProjects()) {
                            if (item.toString().equals(projectComboBox.getValue())) {
                                project = (Project) item;
                            }
                        }
                        Story story = new Story(shortName, longName, description, creator, project,
                                priority);
                        project.add(story);
                        App.mainPane.selectItem(story);
                        dialog.close();
                    }
                }
                return null;
            });
        dialog.setResizable(false);
        dialog.show();
    }

}
