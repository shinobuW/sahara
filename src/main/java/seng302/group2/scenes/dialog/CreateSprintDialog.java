package seng302.group2.scenes.dialog;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.*;
import seng302.group2.scenes.control.Tooltip;
import seng302.group2.scenes.validation.ValidationStyle;
import seng302.group2.workspace.allocation.Allocation;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.release.Release;
import seng302.group2.workspace.project.sprint.Sprint;
import seng302.group2.workspace.team.Team;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.util.validation.NameValidator.validateName;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * Class to create a pop up dialog for creating a sprint
 * Created by drm127 on 29/07/15.
 */
public class CreateSprintDialog extends Dialog<Map<String, String>> {
    private static Boolean correctShortName = Boolean.FALSE;
    private static Boolean correctLongName = Boolean.FALSE;

    private CustomComboBox<Project> projectComboBox;
    private CustomComboBox<Team> teamComboBox;
    private CustomComboBox<Release> releaseComboBox;
    private CustomDatePicker sprintStartDatePicker;
    private CustomDatePicker sprintEndDatePicker;
    private Node createButton;

    /**
     * Constructor for the CreateSprintDialog. The dialog generated by this constructor
     * is shown after construction. A default project is selected in the project combo box.
     *
     * @param defaultProject The default selected project. use null for no default option.
     */
    public CreateSprintDialog(Project defaultProject) {
        correctShortName = Boolean.FALSE;
        correctLongName = Boolean.FALSE;

        this.setTitle("New Sprint");
        this.getDialogPane().setStyle(" -fx-max-width:600px; -fx-max-height: 500px; -fx-pref-width: 600px; "
                + "-fx-pref-height: 500px;");

        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);

        ButtonType btnTypeCreate = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(btnTypeCreate, ButtonType.CANCEL);

        RequiredField shortNameCustomField = new RequiredField("Goal:");
        RequiredField longNameCustomField = new RequiredField("Long Name:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Description:");

        sprintStartDatePicker = new CustomDatePicker("Start Date:", true);
        sprintEndDatePicker = new CustomDatePicker("End Date:", true);


        final Callback<DatePicker, DateCell> startDateCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (releaseSelected() && releaseComboBox.getValue().getEstimatedDate() != null
                                        && item.isAfter(releaseComboBox.getValue().getEstimatedDate())) {
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
                                if (startDateSelected() && (item.isBefore(sprintStartDatePicker.getValue()))) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                                if (releaseSelected() && releaseComboBox.getValue().getEstimatedDate() != null
                                        && item.isAfter(releaseComboBox.getValue().getEstimatedDate())) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                                if (startDateSelected()) {
                                    long p = ChronoUnit.DAYS.between(sprintStartDatePicker.getValue(), item);
                                    setTooltip(new Tooltip(getTooltipStr(p)));
                                }

                            }
                        };
                    }
                };
        sprintEndDatePicker.getDatePicker().setDayCellFactory(endDateCellFactory);


        // Create project combo box.
        ObservableList<Project> projectOptions = observableArrayList();
        projectComboBox = new CustomComboBox<>("Project:", true);
        projectComboBox.getComboBox().setItems(projectOptions);

        // Create release combo box.
        ObservableList<Release> releaseOptions = observableArrayList();
        releaseComboBox = new CustomComboBox<>("Release:", true);
        releaseComboBox.getComboBox().setItems(releaseOptions);

        // Create team combo box.
        //ObservableList<Team> teamOptions = observableArrayList();
        teamComboBox = new CustomComboBox<>("Team:", true);
        //teamComboBox.getComboBox().setItems(teamOptions);

        // Initially disabled as no team selected
        //teamComboBox.disable(true);
        //releaseComboBox.disable(true);
        //sprintStartDatePicker.disable(true);
        //sprintEndDatePicker.disable(true);

        for (Project project : Global.currentWorkspace.getProjects()) {
            projectOptions.add(project);
        }

        grid.getChildren().addAll(shortNameCustomField, longNameCustomField, projectComboBox,
                releaseComboBox, sprintStartDatePicker, sprintEndDatePicker, teamComboBox, descriptionTextArea);

        //Add grid of controls to dialog
        this.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> shortNameCustomField.getTextField().requestFocus());

        createButton = this.getDialogPane().lookupButton(btnTypeCreate);
        createButton.setDisable(true);

        shortNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
            correctShortName = validateShortName(shortNameCustomField, null);
            toggleCreate();
        });

        longNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newvalue) -> {
            correctLongName = validateName(longNameCustomField);
            toggleCreate();
        });

        projectComboBox.getComboBox().valueProperty().addListener(new ChangeListener<Project>() {
            @Override
            public void changed(ObservableValue<? extends Project> observable,
                                Project oldValue, Project newValue) {

                releaseComboBox.disable(false);
                releaseComboBox.setValue(null);
                teamComboBox.disable(false);
                teamComboBox.setValue(null);
                //sprintStartDatePicker.disable(true);
                sprintStartDatePicker.setValue(null);
                //sprintEndDatePicker.disable(true);
                sprintEndDatePicker.setValue(null);


                releaseOptions.clear();

                for (Release release : newValue.getReleases()) {
                    releaseOptions.add(release);
                }

                if (releaseOptions.size() == 0) {
                    ValidationStyle.borderGlowRed(releaseComboBox.getComboBox());
                    ValidationStyle.showMessage("No releases currently exist for this project - you will need"
                                    + " to create one in order to create a sprint for this project",
                            releaseComboBox.getComboBox());
                    releaseComboBox.setTooltip(new Tooltip("No releases currently exist for this project - "
                            + "you will need to create one in order to create a sprint for this project"));
                }
                else {
                    ValidationStyle.borderGlowNone(releaseComboBox.getComboBox());
                    releaseComboBox.removeTooltip();
                }

                teamComboBox.clear();

                for (Team team : newValue.getAllTeams()) {
                    teamComboBox.addToComboBox(team);
                }

                toggleCreate();
            }
        });

        releaseComboBox.getComboBox().valueProperty().addListener(new ChangeListener<Release>() {
            @Override
            public void changed(ObservableValue<? extends Release> observable,
                                Release oldValue, Release newValue) {

                if (newValue != null) {
                    if (sprintStartDatePicker.getValue() != null && newValue.getEstimatedDate() != null
                            && newValue.getEstimatedDate().isBefore(sprintStartDatePicker.getValue())) {
                        ValidationStyle.borderGlowRed(sprintStartDatePicker.getDatePicker());
                        ValidationStyle.showMessage("The start date of the Sprint must be before the estimated"
                                        + " release date of the Release: " + newValue.getEstimatedDate().toString(),
                                sprintStartDatePicker.getDatePicker());
                        sprintStartDatePicker.setTooltip(new seng302.group2.scenes.control.Tooltip("The start date"
                                + " of the Sprint must be before the estimated release date of the Release: "
                                + newValue.getEstimatedDate().toString()));
                        sprintStartDatePicker.setDisable(false);
                        teamComboBox.setDisable(true);
                    }
                    else {
                        ValidationStyle.borderGlowNone(sprintStartDatePicker.getDatePicker());
                        sprintStartDatePicker.removeTooltip();
                        sprintStartDatePicker.setDisable(false);
                        teamComboBox.setDisable(false);
                    }
                    if (sprintEndDatePicker.getValue() != null && newValue.getEstimatedDate() != null
                            && newValue.getEstimatedDate().isBefore(sprintEndDatePicker.getValue())) {
                        ValidationStyle.borderGlowRed(sprintEndDatePicker.getDatePicker());
                        ValidationStyle.showMessage("The end date of the Sprint must be before the estimated"
                                        + " release date of the Release: " + newValue.getEstimatedDate().toString(),
                                sprintEndDatePicker.getDatePicker());
                        sprintEndDatePicker.setTooltip(new seng302.group2.scenes.control.Tooltip("The end date of"
                                + " the Sprint must be before the estimated release date of the Release: "
                                + newValue.getEstimatedDate().toString()));
                        sprintEndDatePicker.setDisable(false);
                        teamComboBox.setDisable(true);
                    }
                    else {
                        ValidationStyle.borderGlowNone(sprintEndDatePicker.getDatePicker());
                        sprintEndDatePicker.removeTooltip();
                        sprintEndDatePicker.setDisable(false);
                        teamComboBox.setDisable(false);
                    }
                    toggleCreate();
                }
            }
        });

        sprintStartDatePicker.getDatePicker().valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable,
                                LocalDate oldValue, LocalDate newValue) {
                Team prevTeam = teamComboBox.getValue();

                ValidationStyle.borderGlowNone(sprintStartDatePicker.getDatePicker());
                sprintStartDatePicker.removeTooltip();


                if (newValue != null && releaseComboBox.getValue() != null
                        && releaseComboBox.getValue().getEstimatedDate() != null
                        && releaseComboBox.getValue().getEstimatedDate().isBefore(newValue)) {
                    ValidationStyle.borderGlowRed(sprintStartDatePicker.getDatePicker());
                    ValidationStyle.showMessage("The start date of the Sprint must be before the estimated"
                                    + " release date of the Release: "
                                    + releaseComboBox.getValue().getEstimatedDate().toString(),
                            sprintStartDatePicker.getDatePicker());
                    sprintStartDatePicker.setTooltip(new seng302.group2.scenes.control.Tooltip("The start date of"
                            + " the Sprint must be before the estimated release date of the Release: "
                            + releaseComboBox.getValue().getEstimatedDate().toString()));
                    sprintStartDatePicker.setDisable(false);
                    teamComboBox.setDisable(true);
                }
                if ((sprintEndDatePicker.getValue() != null) && (newValue != null)
                        && newValue.isAfter(sprintEndDatePicker.getValue())) {
                    sprintEndDatePicker.setDisable(false);
                    ValidationStyle.borderGlowRed(sprintEndDatePicker.getDatePicker());
                    ValidationStyle.showMessage("The end date of the Sprint must be after the start date",
                            sprintEndDatePicker.getDatePicker());
                    sprintEndDatePicker.setTooltip(new seng302.group2.scenes.control.Tooltip("The end date of the"
                            + " Sprint must be after the start date"));
                    //sprintEndDatePicker.setValue(null);
                    teamComboBox.setValue(null);
                    teamComboBox.setDisable(true);
                }
                else if (newValue != null) {

                    if (releaseComboBox.getValue() != null && sprintEndDatePicker.getValue() != null
                            && releaseComboBox.getValue().getEstimatedDate() != null
                            && releaseComboBox.getValue().getEstimatedDate().
                            isBefore(sprintEndDatePicker.getValue())) {
                        ValidationStyle.borderGlowRed(sprintEndDatePicker.getDatePicker());
                        ValidationStyle.showMessage("The end date of the Sprint must be before the estimated"
                                        + " release date of the Release: "
                                        + releaseComboBox.getValue().getEstimatedDate().toString(),
                                sprintEndDatePicker.getDatePicker());
                        sprintEndDatePicker.setTooltip(new seng302.group2.scenes.control.Tooltip("The end date of"
                                + " the Sprint must be before the estimated release date of the Release: "
                                + releaseComboBox.getValue().getEstimatedDate().toString()));
                        sprintEndDatePicker.setDisable(false);
                        teamComboBox.setDisable(true);
                    }
                    else {
                        ValidationStyle.borderGlowNone(sprintEndDatePicker.getDatePicker());
                        sprintEndDatePicker.removeTooltip();
                        sprintEndDatePicker.setDisable(false);
                        teamComboBox.clear();
                        if (projectComboBox.getValue() != null && sprintStartDatePicker.getValue() != null) {
                            outer:
                            for (Team team : projectComboBox.getValue().getAllTeams()) {
                                for (Allocation alloc : team.getProjectAllocations()) {

                                    if (alloc.getStartDate().
                                            isBefore((sprintStartDatePicker.getValue().plusDays(1)))
                                            && alloc.getEndDate().isAfter(sprintStartDatePicker.getValue())) {
                                        teamComboBox.addToComboBox(team);
                                        continue outer;

                                    }
                                }
                            }
                        }
                        teamComboBox.setDisable(false);
                        teamComboBox.setValue(null);
                    }
                }
                if (prevTeam != null && teamComboBox.getComboBox().getItems().contains(prevTeam)) {
                    ValidationStyle.borderGlowNone(teamComboBox.getComboBox());
                    teamComboBox.removeTooltip();
                    teamComboBox.setValue(prevTeam);
                }
                else if (sprintStartDatePicker.getValue() != null
                        && teamComboBox.getComboBox().getItems().size() == 0) {
                    ValidationStyle.borderGlowRed(teamComboBox.getComboBox());
                    ValidationStyle.showMessage("There are no Teams allocated to this project at the start date"
                            + " specified", teamComboBox.getComboBox());
                    teamComboBox.setTooltip(new seng302.group2.scenes.control.Tooltip("There are no Teams allocated"
                            + " to this project at the start date specified"));
                }
                else {
                    ValidationStyle.borderGlowNone(teamComboBox.getComboBox());
                    teamComboBox.removeTooltip();
                }
                toggleCreate();
            }
        });


        sprintEndDatePicker.getDatePicker().valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable,
                                LocalDate oldValue, LocalDate newValue) {


                Team prevTeam = teamComboBox.getValue();

                ValidationStyle.borderGlowNone(sprintEndDatePicker.getDatePicker());
                sprintEndDatePicker.getDatePicker().setTooltip(null);
                if (newValue != null) {
                    sprintEndDatePicker.setStyle(null);
                    sprintEndDatePicker.getDatePicker().setTooltip(null);

                    if (releaseComboBox.getValue() != null && releaseComboBox.getValue().getEstimatedDate() != null
                            && releaseComboBox.getValue().getEstimatedDate().
                            isBefore(sprintEndDatePicker.getValue())) {
                        ValidationStyle.borderGlowRed(sprintEndDatePicker.getDatePicker());
                        ValidationStyle.showMessage("The end date of the Sprint must be before the estimated"
                                        + " release date of the Release: "
                                        + releaseComboBox.getValue().getEstimatedDate().toString(),
                                sprintEndDatePicker.getDatePicker());
                        sprintEndDatePicker.setTooltip(new seng302.group2.scenes.control.Tooltip("The end date of"
                                + " the Sprint must be before the estimated release date of the Release: "
                                + releaseComboBox.getValue().getEstimatedDate().toString()));
                        sprintEndDatePicker.setDisable(false);
                        teamComboBox.setDisable(true);
                    }
                    else if (sprintStartDatePicker.getValue() != null
                            && newValue.isBefore(sprintStartDatePicker.getValue())) {
                        ValidationStyle.borderGlowRed(sprintEndDatePicker.getDatePicker());
                        ValidationStyle.showMessage("The end date of the Sprint must be after the start date",
                                sprintEndDatePicker.getDatePicker());
                        sprintEndDatePicker.setTooltip(new seng302.group2.scenes.control.Tooltip("The end date of"
                                + " the Sprint must be after the start date"));
                        sprintEndDatePicker.setDisable(false);
                        teamComboBox.setDisable(true);
                    }
                    else {
                        teamComboBox.clear();

                        if (projectComboBox.getValue() != null && sprintStartDatePicker.getValue() != null) {
                            outer:
                            for (Team team : projectComboBox.getValue().getAllTeams()) {
                                for (Allocation alloc : team.getProjectAllocations()) {

                                    if (alloc.getStartDate().
                                            isBefore((sprintStartDatePicker.getValue().plusDays(1)))
                                            && alloc.getEndDate().isAfter(sprintStartDatePicker.getValue())) {
                                        teamComboBox.addToComboBox(team);
                                        continue outer;

                                    }
                                }
                            }
                        }
                        teamComboBox.setDisable(false);
                        teamComboBox.setValue(null);

                        if (prevTeam != null && teamComboBox.getComboBox().getItems().contains(prevTeam)) {
                            ValidationStyle.borderGlowNone(teamComboBox.getComboBox());
                            teamComboBox.removeTooltip();
                            teamComboBox.setValue(prevTeam);
                        }
                        else if (sprintStartDatePicker.getValue() != null
                                && teamComboBox.getComboBox().getItems().size() == 0) {
                            ValidationStyle.borderGlowRed(teamComboBox.getComboBox());
                            ValidationStyle.showMessage("There are no Teams allocated to this project at the start"
                                    + " date specified", teamComboBox.getComboBox());
                            teamComboBox.setTooltip(new seng302.group2.scenes.control.Tooltip("There are no Teams "
                                    + "allocated to this project at the start date specified"));
                        }
                        else {
                            ValidationStyle.borderGlowNone(teamComboBox.getComboBox());
                            teamComboBox.removeTooltip();
                        }
                    }
                    toggleCreate();
                }
            }
        });

        teamComboBox.getComboBox().valueProperty().addListener(new ChangeListener<Team>() {
            @Override
            public void changed(ObservableValue<? extends Team> observable, Team oldValue, Team newValue) {
                toggleCreate();
            }
        });

        this.setResultConverter(b -> {
            if (b == btnTypeCreate) {
                String goal = shortNameCustomField.getText();
                String longName = longNameCustomField.getText();
                String description = descriptionTextArea.getText();
                LocalDate sprintStartDate = sprintStartDatePicker.getValue();
                LocalDate sprintEndDate = sprintEndDatePicker.getValue();
                Project sprintProject = projectComboBox.getValue();
                Team sprintTeam = teamComboBox.getValue();
                Release sprintRelease = releaseComboBox.getValue();

                Sprint sprint = new Sprint(goal, longName, description, sprintStartDate,
                        sprintEndDate, sprintProject, sprintTeam, sprintRelease);

                sprintProject.add(sprint);
                App.mainPane.selectItem(sprint);

                this.close();

            }
            return null;
        });

        this.setResizable(false);
        this.show();

        if (defaultProject == null) {
            projectComboBox.setValue(Global.currentWorkspace.getProjects().get(0));
        }
        else {
            projectComboBox.setValue(defaultProject);
        }

    }

    private String getTooltipStr(long dur) {
        String returnStr;
        if (Math.floorDiv(dur, 7) == 0) {
            if (dur == 0) {
                returnStr = "Sprint duration: less than a day.";
            }
            else if (dur % 7 == 1) {
                returnStr = "Sprint duration: 1 day.";
            }
            else {
                returnStr = "Sprint duration: " + (dur % 7) + " days.";
            }
        }
        else if (dur % 7 == 0) {
            if (Math.floorDiv(dur, 7) == 1 && (dur % 7) == 0) {
                returnStr = "Sprint duration: 1 week.";
            }
            else {
                returnStr = "Sprint duration: " + Math.floorDiv(dur, 7) + " weeks.";
            }
        }
        else {
            if (Math.floorDiv(dur, 7) == 1 && (dur % 7) == 1) {
                returnStr = "Sprint duration: 1 week and 1 day.";
            }
            else if (Math.floorDiv(dur, 7) == 1) {
                returnStr = "Sprint duration: 1 week and " + (dur % 7) + " days.";
            }
            else if (dur % 7 == 1) {
                returnStr = "Sprint duration: " + Math.floorDiv(dur, 7) + " weeks and 1 day.";
            }
            else {
                returnStr = "Sprint duration: " + Math.floorDiv(dur, 7) + " weeks and " + (dur % 7) + " days.";
            }
        }
        return returnStr;
    }

    private Boolean projectSelected() {
        return (projectComboBox.getValue() != null);
    }

    private Boolean teamSelected() {
        return (teamComboBox.getValue() != null);
    }

    private Boolean releaseSelected() {
        return (releaseComboBox.getValue() != null);
    }

    private Boolean startDateSelected() {
        return (sprintStartDatePicker.getValue() != null);
    }

    private Boolean endDateSelected() {
        return (sprintEndDatePicker.getValue() != null);
    }

    private void toggleCreate() {
        createButton.setDisable(!(correctShortName && correctLongName
                && projectSelected() && teamSelected() && releaseSelected()
                && startDateSelected() && endDateSelected()));
    }


}
