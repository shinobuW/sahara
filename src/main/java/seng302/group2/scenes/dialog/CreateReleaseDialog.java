/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.dialog;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
 * @author Shinobu
 */
public class CreateReleaseDialog {
    /**
     * Shows the release creation dialog
     */

    static boolean correctShortName;
    static boolean correctDate;
    public static void show(Project defaultProject) {
        correctShortName = Boolean.FALSE;
        correctDate = Boolean.TRUE;

        if (Global.currentWorkspace.getProjects().isEmpty()) {
            // There are no projects to create releases for
            return;
        }

        javafx.scene.control.Dialog<Map<String, String>> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle("New Release");
        dialog.getDialogPane().setStyle(" -fx-max-width:600px; -fx-max-height: 500px; -fx-pref-width: 600px; "
                + "-fx-pref-height: 500px;");

        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);

        ButtonType btnTypeCreate = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnTypeCreate, ButtonType.CANCEL);

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);

        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Description:");
        CustomDatePicker releaseDateField = new CustomDatePicker("Estimated Release Date:", false);
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

        grid.getChildren().addAll(shortNameCustomField, descriptionTextArea, releaseDateField, projectComboBox);

        //Add grid of controls to dialog
        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> shortNameCustomField.getTextField().requestFocus());

        Node createButton = dialog.getDialogPane().lookupButton(btnTypeCreate);
        createButton.setDisable(true);

        shortNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctShortName = validateShortName(shortNameCustomField, null);

                createButton.setDisable(!(correctShortName && correctDate));
            });

        releaseDateField.getDatePicker().setOnAction(event -> {
                correctDate = false;
                LocalDate releaseDate = releaseDateField.getValue();
                if ((DateValidator.isFutureDate(releaseDate) || releaseDate == null) && correctShortName) {
                    releaseDateField.hideErrorField();
                    correctDate = true;
                    createButton.setDisable(!(correctShortName && correctDate));
                }
                else {
                    releaseDateField.showErrorField("Date must be a future date");
                    createButton.setDisable(true);
                }
            });

        dialog.setResultConverter(b -> {
                if (b == btnTypeCreate) {
                    String shortName = shortNameCustomField.getText();
                    String description = descriptionTextArea.getText();
                    LocalDate releaseDate = releaseDateField.getValue();

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
                        dialog.close();
                    }
                    else {
                        if (!DateValidator.isFutureDate(releaseDate)) {
                            releaseDateField.showErrorField("Date must be a future date");
                        }
                        else {
                            Release release = new Release(shortName, description, releaseDate,
                                    project);
                            project.add(release);
                            App.mainPane.selectItem(release);
                            dialog.close();
                        }
                    }
                }
                for (SaharaItem project : Global.currentWorkspace.getProjects()) {
                    projectComboBox.addToComboBox(project.toString());
                }
                return null;
            });
        dialog.setResizable(false);
        dialog.show();
    }
}
