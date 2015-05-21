/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information.release;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.SceneSwitcher;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomDatePicker;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.util.validation.DateValidator;
import seng302.group2.util.validation.ShortNameValidator;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.release.Release;

import java.time.LocalDate;
import java.util.Collections;

import static seng302.group2.scenes.MainScene.informationPane;

/**
 *
 * @author Shinobu
 */
public class ReleaseEditScene
{
    /**
     * Gets the editable information scene for a release
     * @param currentRelease the release to display the information of
     * @return the editable information scene for a release
     */
    public static ScrollPane getReleaseEditScene(Release currentRelease)
    {
        informationPane = new VBox(10);
        /*informationPane.setAlignment(Pos.TOP_LEFT);
        informationPane.setHgap(10);
        informationPane.setVgap(10);*/
        informationPane.setPadding(new Insets(25,25,25,25));

        Button btnCancel = new Button("Cancel");
        Button btnSave = new Button("Save");

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

        for (TreeViewItem project : Global.currentWorkspace.getProjects())
        {
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

        btnCancel.setOnAction((event) ->
            {
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.RELEASE, currentRelease);
            });


        btnSave.setOnAction((event) ->
            {
                if (shortNameCustomField.getText().equals(currentRelease.getShortName())
                        && descriptionTextArea.getText().equals(currentRelease.getDescription())
                        && projectComboBox.getValue().equals(currentRelease.getProject()))
                {
                    // No fields have been changed
                    event.consume();
                    SceneSwitcher.changeScene(SceneSwitcher.ContentScene.RELEASE, currentRelease);
                }

                LocalDate releaseDate = releaseDatePicker.getValue();



                if (releaseDatePicker.getValue() == null)
                {
                    releaseDate = null;
                }
                else
                {
                    if (!DateValidator.isFutureDate(releaseDate))
                    {
                        releaseDatePicker.showErrorField("Date must be a future date");
                    }
                }

                // The short name is the same or valid
                if (((shortNameCustomField.getText().equals(currentRelease.getShortName())
                        || ShortNameValidator.validateShortName(shortNameCustomField))))
                {
                    System.out.print("HELLO?");
                    Project project = new Project();

                    for (Project proj : Global.currentWorkspace.getProjects())
                    {
                        if (proj.getShortName() == projectComboBox.getValue())
                        {
                            project = proj;
                            break;
                        }
                    }

                    currentRelease.edit(shortNameCustomField.getText(),
                        descriptionTextArea.getText(), releaseDate, project);
                    
                    Collections.sort(currentRelease.getProject().getReleases());
                    SceneSwitcher.changeScene(SceneSwitcher.ContentScene.RELEASE, currentRelease);
                    MainScene.treeView.refresh();
                }
                else
                {
                    // One or more fields incorrectly validated, stay on the edit scene
                    event.consume();
                }
            });

        ScrollPane wrapper = new ScrollPane(informationPane);
        wrapper.setStyle("-fx-background-color:transparent;");
        return wrapper;

    }
    
}
