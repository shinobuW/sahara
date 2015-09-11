package seng302.group2.scenes.information.roadMap;

import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.roadMap.RoadMap;
import seng302.group2.workspace.skills.Skill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomTextField;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.control.search.SearchableListView;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.dialog.CustomDialog;
import seng302.group2.util.validation.ShortNameValidator;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.release.Release;
import seng302.group2.workspace.project.story.Story;

/**
 * The RoadMap edit tab
 * Created by cvs20 on 11/09/15.
 */
public class RoadMapEditTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();
    private RequiredField shortNameField;
    private CustomComboBox<String> scaleComboBox;

    /**
     * Constructor for the RoadMapEditTab class. This constructor creates a JavaFX ScrollPane
     * which is populated with relevant controls and then shown.
     *
     * @param currentRoadMap The skill being edited
     */
    public RoadMapEditTab(RoadMap currentRoadMap) {
        this.setText("Edit RoadMap");
        Pane editPane = new VBox(10);
        editPane.setBorder(null);
        editPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(editPane);
        this.setContent(wrapper);
        SearchableText errorField = new SearchableText("");
        shortNameField = new RequiredField("Short Name:");
        shortNameField.setText(currentRoadMap.getShortName());
        shortNameField.setMaxWidth(275);
        CustomTextField longNameField = new CustomTextField("Long Name:");
        longNameField.setText(currentRoadMap.getLongName());
        longNameField.setMaxWidth(275);

        Button btnAssign = new Button("<");
        Button btnUnassign = new Button(">");
        VBox assignmentButtons = new VBox();
        assignmentButtons.spacingProperty().setValue(10);
        assignmentButtons.getChildren().addAll(btnAssign, btnUnassign);
        assignmentButtons.setAlignment(Pos.CENTER);


        // Buttons for the scene
        Button btnDone = new Button("Done");
        Button btnCancel = new Button("Cancel");
        HBox sceneButtons = new HBox();
        sceneButtons.spacingProperty().setValue(10);
        sceneButtons.alignmentProperty().set(Pos.TOP_LEFT);
        sceneButtons.getChildren().addAll(btnDone, btnCancel);

        // Draft member and available people lists
        ObservableList<Release> roadMapList = observableArrayList();
        roadMapList.addAll(currentRoadMap.getReleases());

        ObservableList<Release> availableReleases = observableArrayList();
        for (Project proj : Global.currentWorkspace.getProjects()) {
            for (Release release : proj.getReleases()) {
                if (!roadMapList.contains(release)) {
                    availableReleases.add(release);
                }
            }
        }
        availableReleases.removeAll(roadMapList);


        // List views
        SearchableListView<Release> roadMapReleaseListView = new SearchableListView<>(roadMapList);
        roadMapReleaseListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        roadMapReleaseListView.getSelectionModel().select(0);

        SearchableListView<Release> availableReleaseListView = new SearchableListView<>(availableReleases);
        availableReleaseListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        availableReleaseListView.getSelectionModel().select(0);

        VBox roadMapReleaseBox = new VBox(10);
        SearchableText backlogStoriesLabel = new SearchableText("RoadMap Releases: ");
        backlogStoriesLabel.setStyle("-fx-font-weight: bold");
        roadMapReleaseBox.getChildren().addAll(backlogStoriesLabel, roadMapReleaseListView);

        VBox availableReleaseBox = new VBox(10);
        SearchableText availableStoriesLabel = new SearchableText("Available Releases: ");
        availableStoriesLabel.setStyle("-fx-font-weight: bold");
        availableReleaseBox.getChildren().addAll(availableStoriesLabel, availableReleaseListView);

        HBox storyListViews = new HBox(10);
        storyListViews.getChildren().addAll(roadMapReleaseBox, assignmentButtons, availableReleaseBox);
        storyListViews.setPrefHeight(192);

        // Events
        btnAssign.setOnAction((event) -> {
                boolean uniquePriority = true;
                Release errorRelease = null;
                errorField.setText("");
                outerloop:
                for (Release release : roadMapList) {
                    for (Release addedStory : availableReleaseListView.getSelectionModel().getSelectedItems()) {
                        uniquePriority = false;
                        errorRelease = addedStory;
                        break outerloop;
                    }
                }

                if (uniquePriority) {
                    roadMapList.addAll(
                            availableReleaseListView.getSelectionModel().getSelectedItems());
                    availableReleases.removeAll(
                            availableReleaseListView.getSelectionModel().getSelectedItems());
                }
                else {
                    errorField.setStyle("-fx-text-fill: #ff0000;");
                    errorField.setText("* Story \"" + errorRelease.getShortName() + "\" must have a unique priority.");
                }

            });

        btnUnassign.setOnAction((event) -> {
                errorField.setText("");
                availableReleases.addAll(
                        roadMapReleaseListView.getSelectionModel().getSelectedItems());
                roadMapList.removeAll(
                        roadMapReleaseListView.getSelectionModel().getSelectedItems());
            });


        btnCancel.setOnAction((event) -> currentRoadMap.switchToInfoScene());

        btnDone.setOnAction((event) -> {
                if (shortNameField.getText().equals(currentRoadMap.getShortName()) 
                        || ShortNameValidator.validateShortName(shortNameField, null)) { // validation
                    // Edit Command.
                    currentRoadMap.edit(shortNameField.getText(),
                            roadMapList
                    );

                    currentRoadMap.switchToInfoScene();
                    App.mainPane.refreshTree();
                }
                else {
                    event.consume();
                }
            });

        editPane.getChildren().addAll(
                shortNameField,
                longNameField,
                storyListViews,
                errorField,
                sceneButtons
        );

        // Add items to pane & search collection
        Collections.addAll(searchControls,
                shortNameField,
                longNameField,
                availableReleaseListView,
                availableStoriesLabel,
                roadMapReleaseListView,
                backlogStoriesLabel,
                errorField
        );
    }

    /**
     * Gets all the searchable controls on this tab.
     * @return a collection of all the searchable controls on this tab.
     */
    @Override
    public Collection<SearchableControl> getSearchableControls() {
        return searchControls;
    }

  
}
