package seng302.group2.scenes.information.project.story;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.CustomTextField;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.workspace.project.story.Story;

import java.util.ArrayList;

import static seng302.group2.scenes.MainScene.informationPane;
import static seng302.group2.util.validation.PriorityFieldValidator.validatePriorityField;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * A class for displaying the Story edit scene.
 * Created by drm127 on 17/05/15.
 */
public class StoryEditScene {
    /**
     * Gets the Story Edit information scene.
     *
     * @param currentStory The story to show the information of
     * @return The story Edit information display
     */
    public static ScrollPane getStoryEditScene(Story currentStory) {
        informationPane = new VBox(10);
        /*informationPane.setAlignment(Pos.TOP_LEFT);
        informationPane.setHgap(10);
        informationPane.setVgap(10);*/
        informationPane.setPadding(new Insets(25, 25, 25, 25));

        Button btnCancel = new Button("Cancel");
        Button btnSave = new Button("Done");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.TOP_LEFT);
        buttons.getChildren().addAll(btnSave, btnCancel);

        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        CustomTextField longNameTextField = new CustomTextField("Long Name:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Story Description:", 300);
        RequiredField priorityNumberField = new RequiredField("Story Priority:");
        CustomComboBox estimateComboBox = new CustomComboBox("Estimate:", false);

        CheckBox readyStateCheck = new CheckBox("Ready?");


        String key = currentStory.getBacklog().getScale();
        ArrayList<String> valueList = Global.currentWorkspace.getEstimationScales().getEstimationScaleDict().get(key);
        for (String value : valueList) {
            estimateComboBox.addToComboBox(value);
        }

        estimateComboBox.setValue(currentStory.getEstimate());


        readyStateCheck.setSelected(currentStory.getReady());
        if (currentStory.getBacklog() == null
                || currentStory.getEstimate().equals("-")
                || currentStory.getAcceptanceCriteria().isEmpty()) {
            readyStateCheck.setSelected(false);
            readyStateCheck.setDisable(true);
        }
        else {
            readyStateCheck.setDisable(false);
        }

        estimateComboBox.getComboBox().valueProperty().addListener((observable, oldValue, newValue) -> {
                if (currentStory.getBacklog() == null
                        || (newValue != null && newValue.equals("-"))
                        || currentStory.getAcceptanceCriteria().isEmpty()) {
                    readyStateCheck.setSelected(false);
                    readyStateCheck.setDisable(true);
                }
                else {
                    readyStateCheck.setDisable(false);
                }
        });

        HBox estimateHBox = new HBox();
        HBox.setHgrow(estimateHBox, Priority.ALWAYS);
        estimateHBox.getChildren().add(estimateComboBox);

        if (currentStory.getAcceptanceCriteria().isEmpty() || currentStory.getBacklog() == null) {
            estimateComboBox.disable();
            estimateComboBox.setValue("-");
            Tooltip tool = new Tooltip("A Story must be assigned to a backlog and have at least one Acceptance "
                    + "criteria before an estimate can be given.");
            Tooltip.install(estimateHBox, tool);
            estimateComboBox.setTooltip(tool);
        }

        shortNameCustomField.setMaxWidth(275);
        descriptionTextArea.setMaxWidth(275);

        shortNameCustomField.setText(currentStory.getShortName());
        longNameTextField.setText(currentStory.getLongName());
        descriptionTextArea.setText(currentStory.getDescription());
        priorityNumberField.setText(currentStory.getPriority().toString());

        informationPane.getChildren().addAll(shortNameCustomField,
                longNameTextField,
                descriptionTextArea,
                priorityNumberField,
                estimateHBox,
                readyStateCheck,
                buttons);

        btnCancel.setOnAction((event) -> {
                currentStory.switchToInfoScene();
            });

        btnSave.setOnAction((event) -> {
                boolean shortNameUnchanged = shortNameCustomField.getText().equals(
                        currentStory.getShortName());
                boolean longNameUnchanged = longNameTextField.getText().equals(
                        currentStory.getLongName());
                boolean descriptionUnchanged = descriptionTextArea.getText().equals(
                        currentStory.getDescription());
                boolean priorityUnchanged = priorityNumberField.getText().equals(
                        currentStory.getPriority());

                if (shortNameUnchanged && longNameUnchanged && descriptionUnchanged
                        && priorityUnchanged) {
                    // No changes
                    currentStory.switchToInfoScene();
                    return;
                }

                boolean correctShortName = validateShortName(shortNameCustomField,
                        currentStory.getShortName());
                boolean correctPriority = validatePriorityField(priorityNumberField,
                        currentStory.getBacklog(), currentStory.getPriority());

                if (correctShortName && correctPriority) {
                    // Valid short name, make the edit
                    currentStory.edit(shortNameCustomField.getText(),
                            longNameTextField.getText(),
                            descriptionTextArea.getText(),
                            currentStory.getProject(),
                            Integer.parseInt(priorityNumberField.getText()),
                            currentStory.getBacklog(),
                            estimateComboBox.getValue(),
                            readyStateCheck.selectedProperty().get()
                    );

                    currentStory.switchToInfoScene();
                    MainScene.treeView.refresh();
                }
                else {
                    event.consume();
                }

            });

        ScrollPane wrapper = new ScrollPane(informationPane);
        wrapper.setStyle("-fx-background-color:transparent;");
        return wrapper;
    }
}
