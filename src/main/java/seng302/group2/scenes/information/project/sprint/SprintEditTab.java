package seng302.group2.scenes.information.project.sprint;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomDatePicker;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.release.Release;
import seng302.group2.workspace.project.sprint.Sprint;
import seng302.group2.workspace.team.Team;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A class for displaying a tab used to edit people.
 * Created by btm38 on 30/07/15.
 */
public class SprintEditTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();

    static Boolean correctShortName = Boolean.FALSE;
    static Boolean correctLongName = Boolean.FALSE;

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
        Button btnSave = new Button("Done");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.TOP_LEFT);
        buttons.getChildren().addAll(btnSave, btnCancel);

        RequiredField goalCustomField = new RequiredField("Goal:");
        RequiredField longNameCustomField = new RequiredField("Long Name; ");
        CustomTextArea descriptionTextArea = new CustomTextArea("Sprint Description:", 300);

        CustomComboBox<Backlog> backlogComboBox = new CustomComboBox<>("Backlog: ", true);
        CustomComboBox<Team> teamComboBox = new CustomComboBox<>("Team: ", true);
        CustomComboBox<Release> releaseComboBox = new CustomComboBox<>("Release: ", true);

        CustomDatePicker sprintStartDatePicker = new CustomDatePicker("Start Date:", false);
        CustomDatePicker sprintEndDatePicker  = new CustomDatePicker("End Date: ", false);


        goalCustomField.setMaxWidth(275);
        longNameCustomField.setMaxWidth(275);
        descriptionTextArea.setMaxWidth(275);
        backlogComboBox.setMaxWidth(275);
        teamComboBox.setMaxWidth(275);
        releaseComboBox.setMaxWidth(275);
        sprintStartDatePicker.setMaxWidth(275);
        sprintEndDatePicker.setMaxWidth(275);

        goalCustomField.setText(currentSprint.getGoal());
        longNameCustomField.setText(currentSprint.getLongName());
        descriptionTextArea.setText(currentSprint.getDescription());

        for (Backlog backlog : currentSprint.getProject().getBacklogs()) {
            backlogComboBox.addToComboBox(backlog);
        }
        backlogComboBox.setValue(currentSprint.getBacklog());

        for (Team team : currentSprint.getProject().getCurrentTeams()) {
            teamComboBox.addToComboBox(team);
        }
        teamComboBox.setValue(currentSprint.getTeam());

        for (Release release : currentSprint.getProject().getReleases()) {
            releaseComboBox.addToComboBox(release);
        }
        releaseComboBox.setValue(currentSprint.getRelease());

        editPane.getChildren().addAll(goalCustomField, longNameCustomField, descriptionTextArea, backlogComboBox,
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
                    };
                }
            };
        sprintEndDatePicker.getDatePicker().setDayCellFactory(endDateCellFactory);


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
