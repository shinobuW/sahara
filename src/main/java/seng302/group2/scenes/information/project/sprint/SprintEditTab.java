package seng302.group2.scenes.information.project.sprint;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomDatePicker;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableListView;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.util.validation.ShortNameValidator;
import seng302.group2.workspace.allocation.Allocation;
import seng302.group2.workspace.project.release.Release;
import seng302.group2.workspace.project.sprint.Sprint;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.team.Team;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static seng302.group2.util.validation.NameValidator.validateName;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * A class for displaying a tab used to edit people.
 * Created by btm38 on 30/07/15.
 */
public class SprintEditTab extends SearchableTab {

    Set<SearchableControl> searchControls = new HashSet<>();

    static Boolean correctGoal = Boolean.FALSE;
    static Boolean correctLongName = Boolean.FALSE;

    Button btnDone;

    RequiredField goalCustomField;
    RequiredField longNameCustomField;
    CustomComboBox<Team> teamComboBox;
    CustomComboBox<Release> releaseComboBox;

    CustomDatePicker sprintStartDatePicker;
    CustomDatePicker sprintEndDatePicker;

    /**
     * Constructor for the SprintEditTab class. This constructor creates a JavaFX ScrollPane
     * which is populated with relevant controls then shown.
     *
     * @param currentSprint the sprint being edited
     */
    public SprintEditTab(Sprint currentSprint) {
        this.setText("Edit Sprint");
        Pane editPane = new VBox(10);
        editPane.setBorder(null);
        editPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(editPane);
        this.setContent(wrapper);

        Button btnCancel = new Button("Cancel");
        btnDone = new Button("Done");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.TOP_LEFT);
        buttons.getChildren().addAll(btnDone, btnCancel);

        goalCustomField = new RequiredField("Goal:", searchControls);
        longNameCustomField = new RequiredField("Long Name:", searchControls);
        CustomTextArea descriptionTextArea = new CustomTextArea("Sprint Description:", 300, searchControls);

        teamComboBox = new CustomComboBox<>("Team:", true, searchControls);
        releaseComboBox = new CustomComboBox<>("Release:", true, searchControls);

        sprintStartDatePicker = new CustomDatePicker("Start Date:", false, searchControls);
        sprintEndDatePicker  = new CustomDatePicker("End Date:", false, searchControls);


        goalCustomField.setMaxWidth(275);
        longNameCustomField.setMaxWidth(275);
        descriptionTextArea.setMaxWidth(275);
        teamComboBox.setMaxWidth(275);
        releaseComboBox.setMaxWidth(275);
        sprintStartDatePicker.setMaxWidth(275);
        sprintEndDatePicker.setMaxWidth(275);

        goalCustomField.setText(currentSprint.getGoal());
        longNameCustomField.setText(currentSprint.getLongName());
        descriptionTextArea.setText(currentSprint.getDescription());

        for (Team team : currentSprint.getProject().getCurrentTeams()) {
            teamComboBox.addToComboBox(team);
        }
        teamComboBox.setValue(currentSprint.getTeam());

        for (Release release : currentSprint.getProject().getReleases()) {
            releaseComboBox.addToComboBox(release);
        }
        releaseComboBox.setValue(currentSprint.getRelease());

        sprintStartDatePicker.setValue(currentSprint.getStartDate());
        sprintEndDatePicker.setValue(currentSprint.getEndDate());



        // Story assignment buttons
        Button btnAssign = new Button("<");
        Button btnUnassign = new Button(">");
        VBox assignmentButtons = new VBox();
        assignmentButtons.spacingProperty().setValue(10);
        assignmentButtons.getChildren().addAll(btnAssign, btnUnassign);
        assignmentButtons.setAlignment(Pos.CENTER);

        // Story list view setup
        ObservableList<Story> storiesInSprint = FXCollections.observableArrayList();
        ObservableList<Story> availableStories = FXCollections.observableArrayList();
        SearchableListView<Story> storiesInSprintView = new SearchableListView<>();
        SearchableListView<Story> availableStoriesView = new SearchableListView<>();
        storiesInSprintView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        availableStoriesView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        VBox inSprintVBox = new VBox();
        VBox availableVBox = new VBox();
        inSprintVBox.getChildren().addAll(new SearchableText("Sprint Stories: ", searchControls), storiesInSprintView);
        availableVBox.getChildren().addAll(new SearchableText("Available Stories: ", searchControls),
                availableStoriesView);
        HBox storyHBox = new HBox(10);
        storyHBox.setPrefHeight(192);
        storyHBox.getChildren().addAll(inSprintVBox, assignmentButtons, availableVBox);

        storiesInSprintView.setItems(storiesInSprint);
        availableStoriesView.setItems(availableStories);

        storiesInSprint.addAll(currentSprint.getStories());
        //availableStories.addAll(Global.currentWorkspace.getAllStories());
        for (Story story : Global.currentWorkspace.getAllStories()) {
            if (story.getProject() == currentSprint.getProject()) {
                if (story.getSprint() == null) {
                    availableStories.add(story);
                }
            }
        }
        availableStories.removeAll(currentSprint.getStories());

        
        
        editPane.getChildren().addAll(goalCustomField, longNameCustomField, descriptionTextArea,
                releaseComboBox, sprintStartDatePicker, sprintEndDatePicker, teamComboBox, storyHBox, buttons);

        Collections.addAll(searchControls, storiesInSprintView, availableStoriesView);






        // Actions and events

        final Callback<DatePicker, DateCell> startDateCellFactory =
            new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item.isAfter(releaseComboBox.getValue().getEstimatedDate())) {
                                setDisable(true);
                                setStyle("-fx-background-color: #ffc0cb;");
                            }
                        }
                    };
                }
            };

        sprintStartDatePicker.getDatePicker().setDayCellFactory(startDateCellFactory);

        final Callback<DatePicker, DateCell> endDateCellFactory =
            new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            try {
                                if (item.isBefore(sprintStartDatePicker.getValue().plusDays(1))) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                                if (item.isAfter(releaseComboBox.getValue().getEstimatedDate())) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                                long p = ChronoUnit.DAYS.between(
                                        sprintStartDatePicker.getValue(), item
                                );
                                setTooltip(new Tooltip(
                                                "Sprint duration: " + p + " days.")
                                );
                            }
                            catch (NullPointerException e) {

                            }
                        }
                    };
                }
            };
        sprintEndDatePicker.getDatePicker().setDayCellFactory(endDateCellFactory);

        goalCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctGoal = validateShortName(goalCustomField, null);
                toggleDone();
            });

        longNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctLongName = validateName(longNameCustomField);
                toggleDone();
            });

        teamComboBox.getComboBox().valueProperty().addListener((observable, oldValue, newValue) -> {
                toggleDone();
            });

        releaseComboBox.getComboBox().valueProperty().addListener(new ChangeListener<Release>() {
            @Override
            public void changed(ObservableValue<? extends Release> observable,
                                Release oldValue, Release newValue) {
                sprintEndDatePicker.setDisable(true);
                sprintStartDatePicker.setDisable(true);
                teamComboBox.setValue(null);
                teamComboBox.setDisable(true);

                if (newValue != null) {
                    sprintStartDatePicker.setDisable(false);
                    sprintStartDatePicker.setValue(null);
                    sprintEndDatePicker.setValue(null);
                    sprintEndDatePicker.setDisable(true);
                    toggleDone();
                }
            }
        });

        sprintStartDatePicker.getDatePicker().valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable,
                                LocalDate oldValue, LocalDate newValue) {
                if ((sprintEndDatePicker.getValue() != null) && (newValue != null)
                        && newValue.isAfter(sprintEndDatePicker.getValue())) {
                    sprintEndDatePicker.setDisable(false);
                    sprintEndDatePicker.setValue(null);
                    teamComboBox.setValue(null);
                    teamComboBox.setDisable(true);
                }
                else if (newValue != null) {
                    sprintEndDatePicker.setDisable(false);
                    teamComboBox.clear();
                    outer: for (Team team : currentSprint.getProject().getAllTeams()) {
                        for (Allocation alloc : team.getProjectAllocations()) {

                            if (alloc.getStartDate().isBefore((sprintStartDatePicker.getValue().plusDays(1)))
                                    && alloc.getEndDate().isAfter(sprintEndDatePicker.getValue())) {
                                teamComboBox.addToComboBox(team);
                                continue outer;

                            }
                        }
                    }
                    teamComboBox.setDisable(false);
                    teamComboBox.setValue(null);
                }
                toggleDone();
            }
        });


        sprintEndDatePicker.getDatePicker().valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable,
                    LocalDate oldValue, LocalDate newValue) {

                if (newValue != null) {
                    teamComboBox.clear();
                    outer: for (Team team : currentSprint.getProject().getAllTeams()) {
                        for (Allocation alloc : team.getProjectAllocations()) {

                            if (alloc.getStartDate().isBefore((sprintStartDatePicker.getValue().plusDays(1)))
                                    && alloc.getEndDate().isAfter(sprintEndDatePicker.getValue())) {
                                teamComboBox.addToComboBox(team);
                                continue outer;

                            }
                        }
                    }
                    teamComboBox.setDisable(false);
                    teamComboBox.setValue(null);
                    toggleDone();
                }
            }
        });

        // Button events
        btnAssign.setOnAction((event) -> {
                Collection<Story> selectedStories = new ArrayList<>();
                selectedStories.addAll(availableStoriesView.getSelectionModel().
                        getSelectedItems());

                storiesInSprint.addAll(
                        availableStoriesView.getSelectionModel().getSelectedItems());
                availableStories.removeAll(
                        availableStoriesView.getSelectionModel().getSelectedItems());
            });

        btnUnassign.setOnAction((event) -> {
                Collection<Story> selectedPeople = new ArrayList<>();
                selectedPeople.addAll(storiesInSprintView.getSelectionModel().
                        getSelectedItems());
                availableStories.addAll(selectedPeople);
                storiesInSprint.removeAll(selectedPeople);
            });





        btnDone.setOnAction((event) -> {
                boolean goalUnchanged = goalCustomField.getText().equals(
                        currentSprint.getGoal());
                boolean longNameUnchanged = longNameCustomField.getText().equals(
                        currentSprint.getLongName());
                boolean descriptionUnchanged = descriptionTextArea.getText().equals(
                        currentSprint.getDescription());
                boolean teamUnchanged = teamComboBox.getValue().equals(
                        currentSprint.getTeam());
                boolean releaseUnchanged = releaseComboBox.getValue().equals(
                        currentSprint.getRelease());
                boolean startDateUnchanged = sprintStartDatePicker.getValue().equals(
                        currentSprint.getStartDate());
                boolean endDateUnchanged = sprintEndDatePicker.getValue().equals(
                        currentSprint.getEndDate());
                boolean storiesUnchanged = storiesInSprint.equals(currentSprint.getStories());
                if (goalUnchanged && longNameUnchanged && descriptionUnchanged
                        && teamUnchanged && releaseUnchanged && startDateUnchanged && endDateUnchanged
                        && storiesUnchanged) {
                    // No fields have been changed
                    currentSprint.switchToInfoScene();
                    return;
                }

                boolean correctGoal = ShortNameValidator.validateShortName(goalCustomField,
                        currentSprint.getGoal());
                // The short name is the same or valid
                if (correctGoal) {

                    currentSprint.edit(goalCustomField.getText(),
                            longNameCustomField.getText(),
                            descriptionTextArea.getText(),
                            sprintStartDatePicker.getValue(),
                            sprintEndDatePicker.getValue(),
                            teamComboBox.getValue(),
                            releaseComboBox.getValue(),
                            storiesInSprint //This line just a placeholder for now
                    );

                    currentSprint.switchToInfoScene();
                    App.mainPane.refreshTree();
                }
                else {
                    // One or more fields incorrectly validated, stay on the edit scene
                    event.consume();
                }
            });

        btnCancel.setOnAction((event) -> {
                currentSprint.switchToInfoScene();
            });
    }

    private Boolean teamSelected() {
        return !(teamComboBox.getValue() == null);
    }

    private Boolean releaseSelected() {
        return !(releaseComboBox.getValue() == null);
    }

    private Boolean startDateSelected() {
        return !(sprintStartDatePicker.getValue() == null);
    }

    private Boolean endDateSelected() {
        return !(sprintEndDatePicker.getValue() == null);
    }

    private void toggleDone() {

        correctGoal = validateShortName(goalCustomField, null);
        correctLongName = validateName(longNameCustomField);

        btnDone.setDisable(!(correctGoal && correctLongName && teamSelected() && releaseSelected()
                && startDateSelected() && endDateSelected()));
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
