/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information.project.release;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomDatePicker;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.util.validation.DateValidator;
import seng302.group2.util.validation.ShortNameValidator;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.release.Release;

import java.time.LocalDate;
import java.util.Collections;

/**
 * @author Shinobu
 */
public class ReleaseEditScene {
    /**
     * Gets the editable information scene for a release
     *
     * @param currentRelease the release to display the information of
     * @return the editable information scene for a release
     */
    public static ScrollPane getReleaseEditScene(Release currentRelease) {
        Pane informationPane = new VBox(10);
        /*informationPane.setAlignment(Pos.TOP_LEFT);
        informationPane.setHgap(10);
        informationPane.setVgap(10);*/
        informationPane.setPadding(new Insets(25, 25, 25, 25));

        Button btnCancel = new Button("Cancel");
        Button btnSave = new Button("Done");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.TOP_LEFT);
        buttons.getChildren().addAll(btnSave, btnCancel);

        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Release Description:", 300);
        CustomDatePicker releaseDatePicker = new CustomDatePicker("Estimated Release Date:",
                false);

        CustomComboBox projectComboBox = new CustomComboBox("Project: ", true);

        shortNameCustomField.setMaxWidth(275);
        descriptionTextArea.setMaxWidth(275);
        releaseDatePicker.setMaxWidth(275);
        projectComboBox.setMaxWidth(275);

        for (SaharaItem project : Global.currentWorkspace.getProjects()) {
            projectComboBox.addToComboBox(project.toString());
        }

        shortNameCustomField.setText(currentRelease.getShortName());
        descriptionTextArea.setText(currentRelease.getDescription());
        releaseDatePicker.setValue(currentRelease.getEstimatedDate());

        informationPane.getChildren().addAll(shortNameCustomField, descriptionTextArea,
                releaseDatePicker, buttons);
        //informationPane.getChildren().add(projectComboBox);

        String defaultProject = currentRelease.getProject().getShortName();
        projectComboBox.setValue(defaultProject);


        btnSave.setOnAction((event) -> {
                boolean shortNameUnchanged = shortNameCustomField.getText().equals(
                        currentRelease.getShortName());
                boolean descriptionUnchanged = descriptionTextArea.getText().equals(
                        currentRelease.getDescription());
                boolean projectUnchanged = projectComboBox.getValue().equals(
                        currentRelease.getProject());
                if (shortNameUnchanged && descriptionUnchanged && projectUnchanged) {
                    // No fields have been changed
                    currentRelease.switchToInfoScene();
                    return;
                }

                LocalDate releaseDate = releaseDatePicker.getValue();

                if (releaseDatePicker.getValue() == null) {
                    releaseDate = null;
                }
                else {
                    if (!DateValidator.isFutureDate(releaseDate)) {
                        releaseDatePicker.showErrorField("Date must be a future date");
                    }
                }

                boolean correctShortName = ShortNameValidator.validateShortName(shortNameCustomField,
                        currentRelease.getShortName());
                // The short name is the same or valid
                if (correctShortName) {
                    Project project = new Project();

                    for (Project proj : Global.currentWorkspace.getProjects()) {
                        if (proj.getShortName().equals(projectComboBox.getValue())) {
                            project = proj;
                            break;
                        }
                    }

                    currentRelease.edit(shortNameCustomField.getText(),
                            descriptionTextArea.getText(),
                            releaseDate,
                            project
                    );

                    Collections.sort(currentRelease.getProject().getReleases());
                    currentRelease.switchToInfoScene();
                    App.mainPane.refreshTree();
                }
                else {
                    // One or more fields incorrectly validated, stay on the edit scene
                    event.consume();
                }
            });

        btnCancel.setOnAction((event) -> {
                currentRelease.switchToInfoScene();
            });

        ScrollPane wrapper = new ScrollPane(informationPane);
        wrapper.setStyle("-fx-background-color:transparent;");
        return wrapper;

    }

}
