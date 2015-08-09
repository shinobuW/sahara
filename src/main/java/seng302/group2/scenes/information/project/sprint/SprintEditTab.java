package seng302.group2.scenes.information.project.sprint;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import seng302.group2.App;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomDatePicker;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.util.validation.ShortNameValidator;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.release.Release;
import seng302.group2.workspace.project.sprint.Sprint;
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

    Button btnSave;

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
        btnSave = new Button("Done");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.TOP_LEFT);
        buttons.getChildren().addAll(btnSave, btnCancel);

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

        editPane.getChildren().addAll(goalCustomField, longNameCustomField, descriptionTextArea,
                teamComboBox, releaseComboBox, sprintStartDatePicker, sprintEndDatePicker, buttons);

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

/*        backlogComboBox.getComboBox().valueProperty().addListener(new ChangeListener<Backlog>() {
            @Override
            public void changed(ObservableValue<? extends Backlog> observable,
                                Backlog oldValue, Backlog newValue) {
                sprintEndDatePicker.setDisable(true);
                sprintStartDatePicker.setDisable(true);
                teamComboBox.setValue(null);
                teamComboBox.setDisable(false);
                releaseComboBox.setValue(null);
                releaseComboBox.setDisable(false);
                btnSave.setDisable(false);
                teamComboBox.clear();

                for (Team team : newValue.getProject().getCurrentTeams()) {
                    teamComboBox.addToComboBox(team);
                }
                releaseComboBox.clear();
                for (Release release : newValue.getProject().getReleases()) {
                    releaseComboBox.addToComboBox(release);
                }

                toggleDone();
            }
        });*/

        teamComboBox.getComboBox().valueProperty().addListener(new ChangeListener<Team>() {
            @Override
            public void changed(ObservableValue<? extends Team> observable, Team oldValue, Team newValue) {
                toggleDone();
            }
        });

        releaseComboBox.getComboBox().valueProperty().addListener(new ChangeListener<Release>() {
            @Override
            public void changed(ObservableValue<? extends Release> observable,
                                Release oldValue, Release newValue) {
                sprintEndDatePicker.setDisable(true);
                sprintStartDatePicker.setDisable(true);
                teamComboBox.setValue(null);
                teamComboBox.setDisable(false);
                btnSave.setDisable(false);
                teamComboBox.clear();

                for (Team team : newValue.getProject().getCurrentTeams()) {
                    teamComboBox.addToComboBox(team);
                }

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
                }
                else if (newValue != null) {
                    sprintEndDatePicker.setDisable(false);
                }
                toggleDone();
            }
        });


        sprintEndDatePicker.getDatePicker().valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable,
                                LocalDate oldValue, LocalDate newValue) {
                toggleDone();
            }
        });

        btnSave.setOnAction((event) -> {
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
                if (goalUnchanged && longNameUnchanged && descriptionUnchanged
                        && teamUnchanged && releaseUnchanged && startDateUnchanged && endDateUnchanged) {
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
                            currentSprint.getStories() //This line just a placeholder for now
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

        btnSave.setDisable(!(correctGoal && correctLongName && teamSelected() && releaseSelected()
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
