package seng302.group2.scenes.information.project.story;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.CustomTextField;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.util.validation.PriorityFieldValidator;
import seng302.group2.util.validation.ShortNameValidator;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.estimation.EstimationScalesDictionary;

import java.util.ArrayList;

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
        Pane informationPane = new VBox(10);
        /*informationPane.setAlignment(Pos.TOP_LEFT);
        informationPane.setHgap(10);
        informationPane.setVgap(10);*/
        informationPane.setPadding(new Insets(25, 25, 25, 25));
        Label errorField = new Label("");

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

        ObservableList<Story> dependentOnList = FXCollections.observableArrayList();
        dependentOnList.addAll(currentStory.getDependentOn());

        Backlog currentBacklog = currentStory.getBacklog();
        ObservableList<Story> availableStoryList = FXCollections.observableArrayList();
        availableStoryList.addAll(currentBacklog.getProject().getAllStories());

        availableStoryList.removeAll(dependentOnList);
        availableStoryList.remove(currentStory);

        ListView<Story> dependantStoriesListView = new ListView<>(dependentOnList);
        dependantStoriesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        dependantStoriesListView.getSelectionModel().select(0);

        ListView<Story> availableStoryListView = new ListView<>(availableStoryList);
        availableStoryListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        availableStoryListView.getSelectionModel().select(0);

        VBox backlogStoryBox = new VBox(10);
        backlogStoryBox.getChildren().add(new Label("Dependant On: "));
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

        btnAssign.setOnAction((event) -> {
                if (currentStory.checkAddCycle(availableStoryListView.getSelectionModel().getSelectedItem())) {
                    // TODO Implement dialog
                    System.out.println("Adds a cycle. Not added.");
                }
                else {
                    dependentOnList.addAll(
                            availableStoryListView.getSelectionModel().getSelectedItem());
                    availableStoryList.removeAll(
                            availableStoryListView.getSelectionModel().getSelectedItem());
                }
            });
        btnUnassign.setOnAction((event) -> {
                availableStoryList.addAll(
                        dependantStoriesListView.getSelectionModel().getSelectedItem());
                dependentOnList.removeAll(
                        dependantStoriesListView.getSelectionModel().getSelectedItem());

            });

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
                        currentStory.getPriority().toString());
                boolean readyUnchanged = readyStateCheck.isSelected() == currentStory.getReady();
                boolean estimateUnchanged = estimateComboBox.getValue().equals(currentStory.getEstimate());

                boolean dependentChanged = true;
                if (currentStory.getDependentOn().containsAll(dependentOnList)
                        && dependentOnList.containsAll(currentStory.getDependentOn())) {
                    dependentChanged = false;
                }


                if (shortNameUnchanged && longNameUnchanged && descriptionUnchanged
                        && priorityUnchanged && readyUnchanged && estimateUnchanged && !dependentChanged) {
                    // No changes
                    currentStory.switchToInfoScene();
                    return;
                }

                boolean correctShortName = ShortNameValidator.validateShortName(shortNameCustomField,
                        currentStory.getShortName());
                boolean correctPriority = PriorityFieldValidator.validatePriorityField(priorityNumberField,
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
                            readyStateCheck.selectedProperty().get(),
                            dependentOnList
                    );

                    currentStory.switchToInfoScene();
                    App.mainPane.refreshTree();
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
