package seng302.group2.scenes.information.backlog;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.CustomTextField;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.dialog.CustomDialog;
import seng302.group2.util.validation.ShortNameValidator;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.story.Story;

import java.util.Collections;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * The edit scene for a backlog
 * Created by cvs20 on 19/05/15.
 */
public class BacklogEditScene extends ScrollPane {
    private Backlog baseBacklog;
    private RequiredField shortNameField;
    private CustomTextField longNameField;
    private CustomTextArea descriptionField;
    private CustomComboBox scaleComboBox;

    public BacklogEditScene(Backlog baseBacklog) {
        // Init
        this.baseBacklog = baseBacklog;

        // Setup basic GUI
        VBox container = new VBox(10);
        container.setPadding(new Insets(25, 25, 25, 25));


        // Basic information fields
        shortNameField = new RequiredField("Short Name:");
        shortNameField.setText(baseBacklog.getShortName());
        shortNameField.setMaxWidth(275);
        longNameField = new CustomTextField("Long Name:");
        longNameField.setText(baseBacklog.getLongName());
        longNameField.setMaxWidth(275);
        descriptionField = new CustomTextArea("Backlog Description:", 300);
        descriptionField.setText(baseBacklog.getDescription());
        descriptionField.setMaxWidth(275);
        Label errorField = new Label("");

        scaleComboBox = new CustomComboBox("Estimation Scale:", true);

        for (String scaleName : Global.currentWorkspace.getEstimationScales().getEstimationScaleDict().keySet()) {
            scaleComboBox.addToComboBox(scaleName);
        }

        scaleComboBox.setValue(baseBacklog.getScale());

        HBox scaleHBox = new HBox();
        HBox.setHgrow(scaleHBox, Priority.ALWAYS);
        scaleHBox.getChildren().add(scaleComboBox);

        // Story assignment buttons
        Button btnAssign = new Button("<");
        Button btnUnassign = new Button(">");
        VBox assignmentButtons = new VBox();
        assignmentButtons.spacingProperty().setValue(10);
        assignmentButtons.getChildren().addAll(btnAssign, btnUnassign);
        assignmentButtons.setAlignment(Pos.CENTER);


        // Buttons for the scene
        Button btnSave = new Button("Done");
        Button btnCancel = new Button("Cancel");
        HBox sceneButtons = new HBox();
        sceneButtons.spacingProperty().setValue(10);
        sceneButtons.alignmentProperty().set(Pos.TOP_LEFT);
        sceneButtons.getChildren().addAll(btnSave, btnCancel);

        // Draft member and available people lists
        ObservableList<Story> backlogStoryList = observableArrayList();
        backlogStoryList.addAll(baseBacklog.getStories());

        ObservableList<Story> availableStoryList = observableArrayList();
        for (Story story : baseBacklog.getProject().getUnallocatedStories()) {
            if (story.getBacklog() == null) {
                availableStoryList.add(story);
            }
        }
        availableStoryList.removeAll(backlogStoryList);


        // List views
        ListView<Story> backlogStoryListView = new ListView<>(backlogStoryList);
        backlogStoryListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        backlogStoryListView.getSelectionModel().select(0);

        ListView<Story> availableStoryListView = new ListView<>(availableStoryList);
        availableStoryListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        availableStoryListView.getSelectionModel().select(0);

        VBox backlogStoryBox = new VBox(10);
        backlogStoryBox.getChildren().add(new Label("Backlog Stories: "));
        backlogStoryBox.getChildren().add(backlogStoryListView);

        VBox availableStoryBox = new VBox(10);
        availableStoryBox.getChildren().add(new Label("Available Stories: "));
        availableStoryBox.getChildren().add(availableStoryListView);

        HBox storyListViews = new HBox(10);
        storyListViews.getChildren().addAll(backlogStoryBox, assignmentButtons, availableStoryBox);
        storyListViews.setPrefHeight(192);

        // Adding of gui elements to the container (VBox)
        container.getChildren().addAll(
                shortNameField,
                longNameField,
                descriptionField,
                scaleHBox,
                storyListViews,
                errorField,
                sceneButtons
        );


        // Button events

        btnAssign.setOnAction((event) -> {
                boolean uniquePriority = true;
                Story errorStory = null;
                errorField.setText("");
                outerloop:
                for (Story story : backlogStoryList) {
                    for (Story addedStory : availableStoryListView.getSelectionModel().getSelectedItems()) {
                        if (story.getPriority().equals(addedStory.getPriority())) {
                            uniquePriority = false;
                            errorStory = addedStory;
                            break outerloop;
                        }
                    }
                }

                if (uniquePriority) {
                    backlogStoryList.addAll(
                            availableStoryListView.getSelectionModel().getSelectedItems());
                    availableStoryList.removeAll(
                            availableStoryListView.getSelectionModel().getSelectedItems());
                }
                else {
                    errorField.setTextFill(Color.web("#ff0000"));
                    errorField.setText("* Story \"" + errorStory.getShortName() + "\" must have a unique priority.");
                }

            });

        btnUnassign.setOnAction((event) -> {
                errorField.setText("");
                availableStoryList.addAll(
                        backlogStoryListView.getSelectionModel().getSelectedItems());
                backlogStoryList.removeAll(
                        backlogStoryListView.getSelectionModel().getSelectedItems());
            });


        btnCancel.setOnAction((event) -> {
                baseBacklog.switchToInfoScene();
            });

        btnSave.setOnAction((event) -> {
                if (isValidState()) { // validation
                    // Edit Command.
                    baseBacklog.edit(shortNameField.getText(),
                            longNameField.getText(),
                            descriptionField.getText(),
                            baseBacklog.getProductOwner(),
                            baseBacklog.getProject(),
                            scaleComboBox.getValue(),
                            backlogStoryList
                    );

                    Collections.sort(baseBacklog.getProject().getBacklogs());
                    baseBacklog.switchToInfoScene();
                    MainScene.treeView.refresh();
                }
                else {
                    event.consume();
                }
            });

        // Finally
        this.setStyle("-fx-background-color:transparent;");
        this.setContent(container);
    }


    /**
     * Checks if the changes in the scene are valid
     *
     * @return If the changes in the scene are valid
     */
    private boolean isValidState() {

        ButtonType confirm;
        if (!scaleComboBox.getValue().equals(baseBacklog.getScale())) {
            confirm = CustomDialog.showConfirmation("Estimation Scale", "All existing estimations "
            + "will be lost if the scale is changed. Continue?");
            if (confirm == ButtonType.CANCEL) {
                return false;
            }

        }
        return (shortNameField.getText().equals(baseBacklog.getShortName())  // Is the same,
                || ShortNameValidator.validateShortName(shortNameField, null)); // new name validate
    }

}
