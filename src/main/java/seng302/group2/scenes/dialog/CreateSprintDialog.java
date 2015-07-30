package seng302.group2.scenes.dialog;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomDatePicker;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.release.Release;
import seng302.group2.workspace.project.sprint.Sprint;
import seng302.group2.workspace.team.Team;

import java.time.LocalDate;
import java.util.Map;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.util.validation.NameValidator.validateName;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * Class to create a pop up dialog for creating a sprint
 * Created by drm127 on 29/07/15.
 */
public class CreateSprintDialog extends Dialog<Map<String, String>> {
    static Boolean correctShortName = Boolean.FALSE;
    static Boolean correctLongName = Boolean.FALSE;

    ComboBox<Backlog> backlogComboBox;
    ComboBox<Team> teamComboBox;
    ComboBox<Release> releaseComboBox;

    public CreateSprintDialog() {
        //TODO add date validation
        //TODO fix up sizing

        this.setTitle("New Sprint");
        this.getDialogPane().setStyle(" -fx-max-width:600px; -fx-max-height: 400px; -fx-pref-width: 600px; "
                + "-fx-pref-height: 400px;");

        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);

        ButtonType btnTypeCreate = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(btnTypeCreate, ButtonType.CANCEL);

        RequiredField shortNameCustomField = new RequiredField("Goal:");
        RequiredField longNameCustomField = new RequiredField("Long Name:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Description:");
        CustomDatePicker sprintStartDatePicker = new CustomDatePicker("Start Date:", true);
        CustomDatePicker sprintEndDatePicker = new CustomDatePicker("End Date:", true);

        // Create backlog combo box.
        ObservableList<Backlog> backlogOptions = observableArrayList();
        backlogComboBox = new ComboBox<>(backlogOptions);
        backlogComboBox.setStyle("-fx-pref-width: 175;");
        Label backlogComboLabel = new Label("Backlog:");
        HBox backlogComboHBox = new HBox(backlogComboLabel);

        Label aster1 = new Label(" * ");
        aster1.setTextFill(Color.web("#ff0000"));
        backlogComboHBox.getChildren().add(aster1);

        VBox backlogVBox = new VBox();
        HBox backlogCombo = new HBox();
        backlogCombo.getChildren().addAll(backlogComboHBox, backlogComboBox);
        HBox.setHgrow(backlogComboHBox, Priority.ALWAYS);
        backlogVBox.getChildren().add(backlogCombo);

        // Create team combo box.
        ObservableList<Team> teamOptions = observableArrayList();
        teamComboBox = new ComboBox<>(teamOptions);
        teamComboBox.setStyle("-fx-pref-width: 175;");
        Label teamComboLabel = new Label("Team:");
        HBox teamComboHBox = new HBox(teamComboLabel);

        Label aster2 = new Label(" * ");
        aster2.setTextFill(Color.web("#ff0000"));
        teamComboHBox.getChildren().add(aster2);

        VBox teamVBox = new VBox();
        HBox teamCombo = new HBox();
        teamCombo.getChildren().addAll(teamComboHBox, teamComboBox);
        HBox.setHgrow(teamComboHBox, Priority.ALWAYS);
        teamVBox.getChildren().add(teamCombo);

        // Create release combo box.
        ObservableList<Release> releaseOptions = observableArrayList();
        releaseComboBox = new ComboBox<>(releaseOptions);
        releaseComboBox.setStyle("-fx-pref-width: 175;");
        Label releaseComboLabel = new Label("Release:");
        HBox releaseComboHBox = new HBox(releaseComboLabel);

        Label aster3 = new Label(" * ");
        aster3.setTextFill(Color.web("#ff0000"));
        releaseComboHBox.getChildren().add(aster3);

        VBox releaseVBox = new VBox();
        HBox releaseCombo = new HBox();
        releaseCombo.getChildren().addAll(releaseComboHBox, releaseComboBox);
        HBox.setHgrow(releaseComboHBox, Priority.ALWAYS);
        releaseVBox.getChildren().add(releaseCombo);

        // Initially disabled as no team selected
        teamComboBox.setDisable(true);
        releaseComboBox.setDisable(true);

        for (Project project : Global.currentWorkspace.getProjects()) {
            for (Backlog backlog : project.getBacklogs()) {
                backlogOptions.add(backlog);
            }
        }

        grid.getChildren().addAll(shortNameCustomField, longNameCustomField, backlogVBox, teamVBox,
                releaseVBox, sprintStartDatePicker, sprintEndDatePicker, descriptionTextArea);

        //Add grid of controls to dialog
        this.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> shortNameCustomField.getTextField().requestFocus());

        Node createButton = this.getDialogPane().lookupButton(btnTypeCreate);
        createButton.setDisable(true);

        shortNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctShortName = validateShortName(shortNameCustomField, null);
                createButton.setDisable(!(correctShortName && correctLongName
                        && backlogSelected() & teamSelected() & releaseSelected()));
            });

        longNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newvalue) -> {
                correctLongName = validateName(longNameCustomField);
                createButton.setDisable(!(correctShortName && correctLongName
                        && backlogSelected() & teamSelected() & releaseSelected()));
            });

        backlogComboBox.valueProperty().addListener(new ChangeListener<Backlog>() {
                @Override
                public void changed(ObservableValue<? extends Backlog> observable, Backlog oldValue, Backlog newValue) {
                    teamComboBox.setValue(null);
                    teamComboBox.setDisable(false);
                    releaseComboBox.setValue(null);
                    releaseComboBox.setDisable(false);
                    createButton.setDisable(false);
                    teamOptions.clear();

                    for (Team team : newValue.getProject().getCurrentTeams()) {
                        teamOptions.add(team);
                    }
                    releaseOptions.clear();
                    for (Release release : newValue.getProject().getReleases()) {
                        releaseOptions.add(release);
                    }
                    createButton.setDisable(!(correctShortName && correctLongName
                            && backlogSelected() & teamSelected() & releaseSelected()));
                }
            });

        teamComboBox.valueProperty().addListener(new ChangeListener<Team>() {
                @Override
                public void changed(ObservableValue<? extends Team> observable, Team oldValue, Team newValue) {
                    createButton.setDisable(!(correctShortName && correctLongName
                            && backlogSelected() & teamSelected() & releaseSelected()));
                }
            });

        releaseComboBox.valueProperty().addListener(new ChangeListener<Release>() {
                @Override
                public void changed(ObservableValue<? extends Release> observable, Release oldValue, Release newValue) {
                    createButton.setDisable(!(correctShortName && correctLongName
                            && backlogSelected() & teamSelected() & releaseSelected()));
                }
            });

        this.setResultConverter(b -> {
                if (b == btnTypeCreate) {
                    String goal = shortNameCustomField.getText();
                    String longName = longNameCustomField.getText();
                    String description = descriptionTextArea.getText();
                    LocalDate sprintStartDate = sprintStartDatePicker.getValue();
                    LocalDate sprintEndDate = sprintEndDatePicker.getValue();
                    Backlog sprintBacklog = backlogComboBox.getValue();
                    Team sprintTeam = teamComboBox.getValue();
                    Release sprintRelease = releaseComboBox.getValue();

                    Sprint sprint = new Sprint(goal, longName, description, sprintStartDate,
                            sprintEndDate, sprintBacklog, sprintTeam, sprintRelease);

                    sprintBacklog.getProject().add(sprint);
                    App.mainPane.selectItem(sprint);
                    this.close();

                }
                return null;
            });

        this.setResizable(false);
        this.show();


    }

    private Boolean backlogSelected() {
        return !(backlogComboBox.getValue() == null);
    }

    private Boolean teamSelected() {
        return !(teamComboBox.getValue() == null);
    }

    private Boolean releaseSelected() {
        return !(releaseComboBox.getValue() == null);
    }

}
