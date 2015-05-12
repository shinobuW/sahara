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
import seng302.group2.scenes.control.CustomDateField;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.util.validation.DateValidator;
import seng302.group2.util.validation.ShortNameValidator;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.release.Release;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static seng302.group2.scenes.MainScene.informationPane;
import static seng302.group2.util.validation.DateValidator.isCorrectDateFormat;
import static seng302.group2.util.validation.DateValidator.stringToDate;

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

        RequiredField shortNameCustomField = new RequiredField("Short Name: ");
        CustomTextArea descriptionTextArea = new CustomTextArea("Release Description: ", 300);
        CustomDateField releaseDateField = new CustomDateField("Estimated Release Date: ");
        
        CustomComboBox projectComboBox = new CustomComboBox("Project: ", true);

        shortNameCustomField.setMaxWidth(275);
        descriptionTextArea.setMaxWidth(275);
        releaseDateField.setMaxWidth(275);
        projectComboBox.setMaxWidth(275);

        for (TreeViewItem project : Global.currentWorkspace.getProjects())
        {
            projectComboBox.addToComboBox(project.toString());
        }
        
        shortNameCustomField.setText(currentRelease.getShortName());
        descriptionTextArea.setText(currentRelease.getDescription());
        releaseDateField.setText(currentRelease.getDateString());

        informationPane.getChildren().add(shortNameCustomField);
        informationPane.getChildren().add(descriptionTextArea);
        informationPane.getChildren().add(releaseDateField);
        informationPane.getChildren().add(projectComboBox);
        informationPane.getChildren().add(buttons);

        String defaultProject = currentRelease.getProject().getShortName();
        projectComboBox.setValue(defaultProject);

        btnCancel.setOnAction((event) ->
            {
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.RELEASE, currentRelease);
            });

//        btnSave.setOnAction((event) ->
//            {
//                boolean correctShortName;
//                boolean correctDateFormat = false;
//                Date releaseDate = null;
//
//                Project selectedProject = null;
//                for (TreeViewItem item : Global.currentWorkspace.getProjects())
//                {
//                    if (item.toString().equals(projectComboBox.getValue()))
//                    {
//                        selectedProject = (Project)item;
//                    }
//                }
//
//                if (shortNameCustomField.getText().equals(currentRelease.getShortName()))
//                {
//                    correctShortName = true;
//                }
//                else
//                {
//                    correctShortName = validateShortName(shortNameCustomField);
//                }
//
//                if (stringToDate(releaseDateField.getText())
//                        .equals(currentRelease.getEstimatedDate()))
//                {
//                    releaseDate = currentRelease.getEstimatedDate();
//                    correctDateFormat = true;
//                }
//                else if (releaseDateField.getText().isEmpty())
//                {
//                    releaseDate = null;
//                    correctDateFormat = true;
//                }
//                else if (isCorrectDateFormat(releaseDateField))
//                {
//                    releaseDate = stringToDate(releaseDateField.getText());
//                    if (!DateValidator.isFutureDate(releaseDate))
//                    {
//                        releaseDateField.showErrorField("Date must be a future date");
//                        correctDateFormat = false;
//                    }
//                    else
//                    {
//                        correctDateFormat = true;
//                    }
//                }
//
//                if (correctShortName && correctDateFormat)
//                {
//                    // Build Undo/Redo edit array.
//                    ArrayList<UndoableItem> undoActions = new ArrayList<>();
//                    if (!shortNameCustomField.getText().equals(currentRelease.getShortName()))
//                    {
//                        undoActions.add(new UndoableItem(
//                                currentRelease,
//                                new UndoRedoAction(
//                                        UndoRedoPerformer.UndoRedoProperty.RELEASE_SHORTNAME,
//                                        currentRelease.getShortName()),
//                                new UndoRedoAction(
//                                        UndoRedoPerformer.UndoRedoProperty.RELEASE_SHORTNAME,
//                                        shortNameCustomField.getText())));
//                    }
//
//                    if (currentRelease.getEstimatedDate() != releaseDate)
//                    {
//                        undoActions.add(new UndoableItem(
//                                currentRelease,
//                                new UndoRedoAction(
//                                        UndoRedoPerformer.UndoRedoProperty.RELEASE_RELEASEDATE,
//                                        currentRelease.getEstimatedDate()),
//                                new UndoRedoAction(
//                                        UndoRedoPerformer.UndoRedoProperty.RELEASE_RELEASEDATE,
//                                        releaseDate)
//                        ));
//                    }
//
//                    if (!currentRelease.getDescription().equals(descriptionTextArea.getText()))
//                    {
//                        undoActions.add(new UndoableItem(
//                                currentRelease,
//                                new UndoRedoAction(
//                                        UndoRedoPerformer.UndoRedoProperty.RELEASE_DESCRIPTION,
//                                        currentRelease.getDescription()),
//                                new UndoRedoAction(
//                                        UndoRedoPerformer.UndoRedoProperty.RELEASE_DESCRIPTION,
//                                        descriptionTextArea.getText())
//                        ));
//                    }
//
//                    if (!currentRelease.getProject().equals(selectedProject))
//                    {
//                        undoActions.add(new UndoableItem(
//                                currentRelease,
//                                new UndoRedoAction(
//                                        UndoRedoPerformer.UndoRedoProperty.RELEASE_PROJECT,
//                                        currentRelease.getProject()),
//                                new UndoRedoAction(
//                                        UndoRedoPerformer.UndoRedoProperty.RELEASE_PROJECT,
//                                        selectedProject)));
//                    }
//
//                    if (undoActions.size() > 0)
//                    {
//                        System.out.println("Release edit added to undoredo stack");
//                        Global.undoRedoMan.add(new UndoableItem(
//                                currentRelease,
//                                new UndoRedoAction(
//                                        UndoRedoPerformer.UndoRedoProperty.RELEASE_EDIT,
//                                        undoActions),
//                                new UndoRedoAction(
//                                        UndoRedoPerformer.UndoRedoProperty.RELEASE_EDIT,
//                                        undoActions)
//                        ));
//                    }
//
//
//                    // Save the edits.
//                    currentRelease.setDescription(descriptionTextArea.getText());
//                    currentRelease.setShortName(shortNameCustomField.getText());
//                    currentRelease.setEstimatedDate(releaseDate);
//
//                    Project previous = currentRelease.getProject();
//                    currentRelease.getProject().removeWithoutUndo(currentRelease);
//                    currentRelease.setProjectWithoutUndo(selectedProject);
//
//
//                    SceneSwitcher.changeScene(SceneSwitcher.ContentScene.RELEASE, currentRelease);
//                    MainScene.treeView.refresh();
//
//                }
//                else
//                {
//                    event.consume();
//                }
//            });

        btnSave.setOnAction((event) ->
            {
                if (shortNameCustomField.getText().equals(currentRelease.getShortName())
                        && descriptionTextArea.getText().equals(currentRelease.getDescription())
                        && releaseDateField.getText().equals(currentRelease.getDateString())
                        && projectComboBox.getValue().equals(currentRelease.getProject()))
                {
                    // No fields have been changed
                    System.out.print("test");
                    SceneSwitcher.changeScene(SceneSwitcher.ContentScene.RELEASE, currentRelease);
                }

                boolean correctDateFormat = false;
                LocalDate releaseDate = null;

                //TODO Date validation.
    //            Date estimatedDate = new Date();
    //            if (releaseDateField.getText().equals(""))
    //            {
    //                estimatedDate = null;
    //            }
    //            else
    //            {
    //                estimatedDate = stringToDate(releaseDateField.getText());
    //            }


                if (releaseDateField.getText().isEmpty())
                {
                    releaseDate = null;
                    correctDateFormat = true;
                }
                else if (stringToDate(releaseDateField.getText())
                        .equals(currentRelease.getEstimatedDate()))
                {
                    releaseDate = currentRelease.getEstimatedDate();
                    correctDateFormat = true;
                }
                else if (isCorrectDateFormat(releaseDateField))
                {
                    releaseDate = LocalDate.parse(releaseDateField.getText(),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    if (!DateValidator.isFutureDate(releaseDate))
                    {
                        releaseDateField.showErrorField("Date must be a future date");
                        correctDateFormat = false;
                    }
                    else
                    {
                        correctDateFormat = true;
                    }
                }

                // The short name is the same or valid
                if (((shortNameCustomField.getText().equals(currentRelease.getShortName())
                        || ShortNameValidator.validateShortName(shortNameCustomField)))
                        && (correctDateFormat = true))
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
