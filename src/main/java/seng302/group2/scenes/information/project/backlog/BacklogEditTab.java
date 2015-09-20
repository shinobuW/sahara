package seng302.group2.scenes.information.project.backlog;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.*;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableListView;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.dialog.CustomDialog;
import seng302.group2.util.validation.ShortNameValidator;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.tag.Tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * The backlog edit tab.
 * Created by btm38 on 30/07/15.
 */
public class BacklogEditTab extends SearchableTab {
    List<SearchableControl> searchControls = new ArrayList<>();
    private Backlog baseBacklog;
    private RequiredField shortNameField = new RequiredField("Short Name:", searchControls);
    private CustomComboBox<String> scaleComboBox = new CustomComboBox<>("Scale", true, searchControls);
    CustomTextField longNameField = new CustomTextField("Long Name:");
    CustomTextArea descriptionField = new CustomTextArea("Backlog Description:", 300);
    ObservableList<Story> backlogStoryList = observableArrayList();

    /**
     * Constructor for the BacklogEditTab class. This constructor creates a JavaFX ScrollPane
     * which is populated with relevant controls then shown.
     *
     * @param baseBacklog the backlog being edited
     */
    public BacklogEditTab(Backlog baseBacklog) {
        // Init
        this.baseBacklog = baseBacklog;
        construct();
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

    /**
     * Gets all the searchable controls on this tab.
     * @return a collection of all the searchable controls on this tab.
     */
    @Override
    public Collection<SearchableControl> getSearchableControls() {
        return searchControls;
    }

    @Override
    public void construct() {
        // Setup basic GUI
        this.setText("Edit Backlog");
        Pane editPane = new VBox(10);
        editPane.setBorder(null);
        editPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(editPane);
        this.setContent(wrapper);

        // Basic information fields
        shortNameField.setText(baseBacklog.getShortName());
        shortNameField.setMaxWidth(275);
        longNameField.setText(baseBacklog.getLongName());
        longNameField.setMaxWidth(275);
        descriptionField.setText(baseBacklog.getDescription());
        descriptionField.setMaxWidth(275);
        SearchableText errorField = new SearchableText("");

        scaleComboBox = new CustomComboBox<String>("Estimation Scale:", true);

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

        // Draft member and available people lists
        backlogStoryList.addAll(baseBacklog.getStories());

        ObservableList<Story> availableStoryList = observableArrayList();
        for (Story story : baseBacklog.getProject().getUnallocatedStories()) {
            if (story.getBacklog() == null) {
                availableStoryList.add(story);
            }
        }
        availableStoryList.removeAll(backlogStoryList);


        // List views
        FilteredListView<Story> backlogStoryListBox = new FilteredListView<Story>(backlogStoryList, "stories");
        SearchableListView<Story> backlogStoryListView = backlogStoryListBox.getListView();
        backlogStoryListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        backlogStoryListView.getSelectionModel().select(0);

        FilteredListView<Story> availableStoryListBox = new FilteredListView<Story>(availableStoryList, "stories");
        SearchableListView<Story> availableStoryListView = availableStoryListBox.getListView();
        availableStoryListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        availableStoryListView.getSelectionModel().select(0);

        VBox backlogStoryBox = new VBox(10);
        SearchableText backlogStoriesLabel = new SearchableText("Backlog Stories: ");
        backlogStoriesLabel.setStyle("-fx-font-weight: bold");
        backlogStoryBox.getChildren().addAll(backlogStoriesLabel, backlogStoryListBox);

        VBox availableStoryBox = new VBox(10);
        SearchableText availableStoriesLabel = new SearchableText("Available Stories: ");
        availableStoriesLabel.setStyle("-fx-font-weight: bold");
        availableStoryBox.getChildren().addAll(availableStoriesLabel, availableStoryListBox);

        HBox storyListViews = new HBox(10);
        storyListViews.getChildren().addAll(backlogStoryBox, assignmentButtons, availableStoryBox);
        storyListViews.setPrefHeight(192);

        // Events
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
                errorField.setStyle("-fx-text-fill: #ff0000;");
                errorField.setText("* Story \"" + errorStory.getShortName() + "\" must have a unique priority.");
            }
            availableStoryListBox.resetInputText();
            backlogStoryListBox.resetInputText();

        });

        btnUnassign.setOnAction((event) -> {
            errorField.setText("");
            availableStoryList.addAll(
                    backlogStoryListView.getSelectionModel().getSelectedItems());
            backlogStoryList.removeAll(
                    backlogStoryListView.getSelectionModel().getSelectedItems());
            availableStoryListBox.resetInputText();
            backlogStoryListBox.resetInputText();
        });


        editPane.getChildren().addAll(
                shortNameField,
                longNameField,
                descriptionField,
                scaleHBox,
                storyListViews,
                errorField
        );

        // Add items to pane & search collection
        Collections.addAll(searchControls,
                shortNameField,
                longNameField,
                descriptionField,
                scaleComboBox,
                availableStoryListView,
                availableStoriesLabel,
                backlogStoryListView,
                backlogStoriesLabel,
                errorField
        );
    }

    /**
     * Cancels the edit
     */
    public void cancel() {
        baseBacklog.switchToInfoScene();
    }

    /**
     * Changes the values depending on what the user edits
     */
    public void done() {
        if (isValidState()) { // validation
            // Edit Command.
            ArrayList<Tag> tags = new ArrayList<>();
            baseBacklog.edit(shortNameField.getText(),
                    longNameField.getText(),
                    descriptionField.getText(),
                    baseBacklog.getProductOwner(),
                    baseBacklog.getProject(),
                    scaleComboBox.getValue(),
                    backlogStoryList,
                    tags
            );

            Collections.sort(baseBacklog.getProject().getBacklogs());
            baseBacklog.switchToInfoScene();
            App.mainPane.refreshTree();
        }
    }
}

