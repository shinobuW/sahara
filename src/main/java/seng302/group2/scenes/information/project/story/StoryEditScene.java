package seng302.group2.scenes.information.project.story;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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
import seng302.group2.workspace.project.story.estimation.EstimationScalesDictionary;

import java.util.ArrayList;

import static javafx.collections.FXCollections.observableArrayList;
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

        Button btnAssign = new Button("<");
        Button btnUnassign = new Button(">");
        VBox assignmentButtons = new VBox();
        assignmentButtons.spacingProperty().setValue(10);
        assignmentButtons.getChildren().addAll(btnAssign, btnUnassign);
        assignmentButtons.setAlignment(Pos.CENTER);

        String key = currentStory.getBacklog().getScale();
        ArrayList<String> valueList = Global.currentWorkspace.getEstimationScales().getEstimationScaleDict().get(key);
        for (String value : valueList) {
            estimateComboBox.addToComboBox(value);
        }

        estimateComboBox.setValue(currentStory.getEstimate());


        readyStateCheck.setSelected(currentStory.getReady());
        if (currentStory.getBacklog() == null
                || currentStory.getEstimate().equals(EstimationScalesDictionary.getScaleValue(
                EstimationScalesDictionary.DefaultValues.NONE))
                || currentStory.getAcceptanceCriteria().isEmpty()) {
            readyStateCheck.setSelected(false);
            readyStateCheck.setDisable(true);
        }
        else {
            readyStateCheck.setDisable(false);
        }

        estimateComboBox.getComboBox().valueProperty().addListener((observable, oldValue, newValue) -> {
                if (currentStory.getBacklog() == null
                        || (newValue != null && newValue.equals(EstimationScalesDictionary.getScaleValue(
                        EstimationScalesDictionary.DefaultValues.NONE)))
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
            estimateComboBox.setValue(EstimationScalesDictionary.getScaleValue(
                    EstimationScalesDictionary.DefaultValues.NONE));
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

        ObservableList<Story> dependantStoryList = observableArrayList();
        dependantStoryList.addAll(currentStory.getDependencies());

        ObservableList<Story> availableStoryList = observableArrayList();
        for (Story story : currentStory.getBacklog().getProject().getUnallocatedStories()) {
            if (story.getBacklog() == null) {
                availableStoryList.add(story);
            }
        }
        availableStoryList.removeAll(dependantStoryList);

        ListView<Story> dependantStoriesListView = new ListView<>(dependantStoryList);
        dependantStoriesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        dependantStoriesListView.getSelectionModel().select(0);

        ListView<Story> availableStoryListView = new ListView<>(availableStoryList);
        availableStoryListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        availableStoryListView.getSelectionModel().select(0);

        VBox backlogStoryBox = new VBox(10);
        backlogStoryBox.getChildren().add(new Label("Dependant Stories: "));
        backlogStoryBox.getChildren().add(dependantStoriesListView);

        VBox availableStoryBox = new VBox(10);
        availableStoryBox.getChildren().add(new Label("Available Stories: "));
        availableStoryBox.getChildren().add(availableStoryListView);

        HBox storyListViews = new HBox(10);
        storyListViews.getChildren().addAll(backlogStoryBox, assignmentButtons, availableStoryBox);
        storyListViews.setPrefHeight(192);



        informationPane.getChildren().addAll(shortNameCustomField,
                longNameTextField,
                descriptionTextArea,
                priorityNumberField,
                estimateHBox,
                readyStateCheck,
                storyListViews,
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
