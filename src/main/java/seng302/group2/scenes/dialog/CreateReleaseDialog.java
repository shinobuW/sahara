/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.dialog;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomDatePicker;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.util.validation.DateValidator;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.release.Release;

import java.time.LocalDate;
import java.util.Map;

import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * Class to create a popup dialog for creating a release.
 * @author Shinobu
 */
public class CreateReleaseDialog extends Dialog<Map<String, String>> {

    Boolean correctShortName = Boolean.FALSE;
    Boolean correctDate = Boolean.FALSE;

    /**
     * Shows a dialog used for creating a new release.
     */
    public CreateReleaseDialog() {
        correctShortName = Boolean.FALSE;
        correctDate = Boolean.TRUE;

        if (Global.currentWorkspace.getProjects().isEmpty()) {
            // There are no projects to create releases for
            return;
        }

        this.setTitle("New Release");
        this.getDialogPane().setStyle(" -fx-max-width:600px; -fx-max-height: 330px; -fx-pref-width: 600px; "
                + "-fx-pref-height: 330px;");

        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);

        ButtonType btnTypeCreate = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(btnTypeCreate, ButtonType.CANCEL);

        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Description:");
        CustomDatePicker releaseDatePicker = new CustomDatePicker("Estimated Release Date:", false);
        CustomComboBox projectComboBox = new CustomComboBox("Project:", true);

        for (SaharaItem project : Global.currentWorkspace.getProjects()) {
            projectComboBox.addToComboBox(project.toString());
        }

        final Callback<DatePicker, DateCell> dayCellFactory =
            new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item.isBefore(LocalDate.now())) {
                                setDisable(true);
                                setStyle("-fx-background-color: #ffc0cb;");
                            }

                        }
                    };
                }
            };

        releaseDatePicker.getDatePicker().setDayCellFactory(dayCellFactory);

        grid.getChildren().addAll(shortNameCustomField, releaseDatePicker, projectComboBox, descriptionTextArea);

        //Add grid of controls to dialog
        this.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> shortNameCustomField.getTextField().requestFocus());

        Node createButton = this.getDialogPane().lookupButton(btnTypeCreate);
        createButton.setDisable(true);

        shortNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctShortName = validateShortName(shortNameCustomField, null);
                createButton.setDisable(!(correctShortName && correctDate));
            });

        releaseDatePicker.getDatePicker().setOnAction(event -> {
                LocalDate releaseDate = releaseDatePicker.getValue();
                correctDate = DateValidator.isFutureDate(releaseDate);

                if (correctDate) {
                    releaseDatePicker.hideErrorField();
                }
                else {
                    releaseDatePicker.showErrorField("Date must be a future date");
                }

                if ((DateValidator.isFutureDate(releaseDate) || releaseDate == null) && correctShortName) {
                    correctDate = true;
                    createButton.setDisable(!(correctShortName && correctDate));
                }
                else {
                    createButton.setDisable(true);
                }
            });

        this.setResultConverter(b -> {
                if (b == btnTypeCreate) {
                    String shortName = shortNameCustomField.getText();
                    String description = descriptionTextArea.getText();
                    LocalDate releaseDate = releaseDatePicker.getValue();

                    Project project = new Project();
                    for (SaharaItem item : Global.currentWorkspace.getProjects()) {
                        if (item.toString().equals(projectComboBox.getValue())) {
                            project = (Project) item;
                        }
                    }

                    if (releaseDate == null) {
                        releaseDate = null;
                        Release release = new Release(shortName, description, releaseDate, project);
                        project.add(release);
                        App.mainPane.selectItem(release);
                        this.close();
                    }
                    else {
                        if (!DateValidator.isFutureDate(releaseDate)) {
                            releaseDatePicker.showErrorField("Date must be a future date");
                        }
                        else {
                            Release release = new Release(shortName, description, releaseDate,
                                    project);
                            project.add(release);
                            App.mainPane.selectItem(release);
                            this.close();
                        }
                    }
                }
                for (SaharaItem project : Global.currentWorkspace.getProjects()) {
                    projectComboBox.addToComboBox(project.toString());
                }
                return null;
            });
        this.setResizable(false);
        this.show();
    }

    /**
     * Shows a dialog used for creating a new release. A default project is selected in the project combo box.
     * @param defaultProject The default project selection.
     */
    public CreateReleaseDialog(Project defaultProject) {
        correctShortName = Boolean.FALSE;
        correctDate = Boolean.TRUE;

        if (Global.currentWorkspace.getProjects().isEmpty()) {
            // There are no projects to create releases for
            return;
        }

        this.setTitle("New Release");
        this.getDialogPane().setStyle(" -fx-max-width:600px; -fx-max-height: 330px; -fx-pref-width: 600px; "
                + "-fx-pref-height: 330px;");

        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);

        ButtonType btnTypeCreate = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(btnTypeCreate, ButtonType.CANCEL);

        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Description:");
        CustomDatePicker releaseDatePicker = new CustomDatePicker("Estimated Release Date:", false);
        CustomComboBox projectComboBox = new CustomComboBox("Project:", true);

        for (SaharaItem project : Global.currentWorkspace.getProjects()) {
            projectComboBox.addToComboBox(project.toString());
        }

        if (defaultProject == null) {
            String firstItem = Global.currentWorkspace.getProjects().get(0).toString();
            projectComboBox.setValue(firstItem);
        }
        else {
            projectComboBox.setValue(defaultProject.toString());
        }

        final Callback<DatePicker, DateCell> dayCellFactory =
            new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item.isBefore(LocalDate.now())) {
                                setDisable(true);
                                setStyle("-fx-background-color: #ffc0cb;");
                            }

                        }
                    };
                }
            };
        releaseDatePicker.getDatePicker().setDayCellFactory(dayCellFactory);

        grid.getChildren().addAll(shortNameCustomField, releaseDatePicker, projectComboBox, descriptionTextArea);

        //Add grid of controls to dialog
        this.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> shortNameCustomField.getTextField().requestFocus());

        Node createButton = this.getDialogPane().lookupButton(btnTypeCreate);
        createButton.setDisable(true);

        shortNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctShortName = validateShortName(shortNameCustomField, null);

                createButton.setDisable(!(correctShortName && correctDate));
            });

        releaseDatePicker.getDatePicker().setOnAction(event -> {
                LocalDate releaseDate = releaseDatePicker.getValue();
                correctDate = DateValidator.isFutureDate(releaseDate);

                if (correctDate) {
                    releaseDatePicker.hideErrorField();
                }
                else {
                    releaseDatePicker.showErrorField("Date must be a future date");
                }

                if ((DateValidator.isFutureDate(releaseDate) || releaseDate == null) && correctShortName) {
                    correctDate = true;
                    createButton.setDisable(!(correctShortName && correctDate));
                }
                else {
                    createButton.setDisable(true);
                }
            });

        this.setResultConverter(b -> {
                if (b == btnTypeCreate) {
                    String shortName = shortNameCustomField.getText();
                    String description = descriptionTextArea.getText();
                    LocalDate releaseDate = releaseDatePicker.getValue();

                    Project project = new Project();
                    for (SaharaItem item : Global.currentWorkspace.getProjects()) {
                        if (item.toString().equals(projectComboBox.getValue())) {
                            project = (Project) item;
                        }
                    }

                    if (releaseDate == null) {
                        releaseDate = null;
                        Release release = new Release(shortName, description, releaseDate, project);
                        project.add(release);
                        App.mainPane.selectItem(release);
                        this.close();
                    }
                    else {
                        if (!DateValidator.isFutureDate(releaseDate)) {
                            releaseDatePicker.showErrorField("Date must be a future date");
                        }
                        else {
                            Release release = new Release(shortName, description, releaseDate,
                                    project);
                            project.add(release);
                            App.mainPane.selectItem(release);
                            this.close();
                        }
                    }
                }
                for (SaharaItem project : Global.currentWorkspace.getProjects()) {
                    projectComboBox.addToComboBox(project.toString());
                }
                return null;
            });
        this.setResizable(false);
        this.show();
    }
}
