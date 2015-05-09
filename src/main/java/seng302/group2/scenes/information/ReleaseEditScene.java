/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomDateField;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.util.undoredo.UndoRedoAction;
import seng302.group2.util.undoredo.UndoRedoPerformer;
import seng302.group2.util.undoredo.UndoableItem;
import seng302.group2.util.validation.DateValidator;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.release.Release;

import java.util.ArrayList;
import java.util.Date;

import static seng302.group2.scenes.MainScene.informationGrid;
import static seng302.group2.util.validation.DateValidator.isCorrectDateFormat;
import static seng302.group2.util.validation.DateValidator.stringToDate;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

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
    public static Pane getReleaseEditScene(Release currentRelease)
    {
        informationGrid = new VBox(10);
        /*informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);*/
        informationGrid.setPadding(new Insets(25,25,25,25));

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

        informationGrid.getChildren().add(shortNameCustomField);
        informationGrid.getChildren().add(descriptionTextArea);
        informationGrid.getChildren().add(releaseDateField);
        informationGrid.getChildren().add(projectComboBox);
        informationGrid.getChildren().add(buttons);

        String defaultProject = currentRelease.getProject().getShortName();
        projectComboBox.setValue(defaultProject);

        btnCancel.setOnAction((event) ->
            {
                App.content.getItems().remove(informationGrid);
                ReleaseScene.getReleaseScene((Release) Global.selectedTreeItem.getValue());
                App.content.getItems().add(informationGrid);
            });

        btnSave.setOnAction((event) ->
            {
                boolean correctShortName;
                boolean correctDateFormat = false;
                Date releaseDate = null;
                
                Project selectedProject = null;
                for (TreeViewItem item : Global.currentWorkspace.getProjects())
                {
                    if (item.toString().equals(projectComboBox.getValue()))
                    {
                        selectedProject = (Project)item;
                    }
                }
                
                if (shortNameCustomField.getText().equals(currentRelease.getShortName()))
                {
                    correctShortName = true;
                }
                else
                {
                    correctShortName = validateShortName(shortNameCustomField);
                }

                if (stringToDate(releaseDateField.getText())
                        .equals(currentRelease.getEstimatedDate()))
                {
                    releaseDate = currentRelease.getEstimatedDate();
                    correctDateFormat = true;
                }
                else if (releaseDateField.getText().isEmpty())
                {
                    releaseDate = null;
                    correctDateFormat = true;
                }
                else if (isCorrectDateFormat(releaseDateField))
                {
                    releaseDate = stringToDate(releaseDateField.getText());
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

                if (correctShortName && correctDateFormat)
                {
                    // Build Undo/Redo edit array.
                    ArrayList<UndoableItem> undoActions = new ArrayList<>();
                    if (!shortNameCustomField.getText().equals(currentRelease.getShortName()))
                    {
                        undoActions.add(new UndoableItem(
                                currentRelease,
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.RELEASE_SHORTNAME,
                                        currentRelease.getShortName()),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.RELEASE_SHORTNAME,
                                        shortNameCustomField.getText())));
                    }

                    if (currentRelease.getEstimatedDate() != releaseDate)
                    {
                        undoActions.add(new UndoableItem(
                                currentRelease,
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.RELEASE_RELEASEDATE,
                                        currentRelease.getEstimatedDate()),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.RELEASE_RELEASEDATE,
                                        releaseDate)
                        ));
                    }

                    if (!currentRelease.getDescription().equals(descriptionTextArea.getText()))
                    {
                        undoActions.add(new UndoableItem(
                                currentRelease,
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.RELEASE_DESCRIPTION,
                                        currentRelease.getDescription()),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.RELEASE_DESCRIPTION,
                                        descriptionTextArea.getText())
                        ));
                    }

                    if (!currentRelease.getProject().equals(selectedProject))
                    {
                        undoActions.add(new UndoableItem(
                                currentRelease,
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.RELEASE_PROJECT,
                                        currentRelease.getProject()),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.RELEASE_PROJECT,
                                        selectedProject)));
                    }

                    if (undoActions.size() > 0)
                    {
                        System.out.println("Release edit added to undoredo stack");
                        Global.undoRedoMan.add(new UndoableItem(
                                currentRelease,
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.RELEASE_EDIT,
                                        undoActions),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.RELEASE_EDIT,
                                        undoActions)
                        ));
                    }


                    // Save the edits.        
                    currentRelease.setDescription(descriptionTextArea.getText());
                    currentRelease.setShortName(shortNameCustomField.getText());
                    currentRelease.setEstimatedDate(releaseDate);
                    
                    Project previous = currentRelease.getProject();
                    currentRelease.getProject().removeWithoutUndo(currentRelease);
                    currentRelease.setProjectWithoutUndo(selectedProject);


                    /*
                    App.content.getItems().remove(treeView);
                    App.content.getItems().remove(informationGrid);
                    ReleaseScene.getReleaseScene(currentRelease);
                    MainScene.treeView = new TreeViewWithItems( new TreeItem());
                    ObservableList<TreeViewItem> children = observableArrayList();
                    children.add(Global.currentWorkspace);
                    
                    MainScene.treeView.setItems(children);
                    MainScene.treeView.setShowRoot(false);

                    App.content.getItems().add(treeView);
                    App.content.getItems().add(informationGrid);
                    MainScene.treeView.getSelectionModel().select(selectedTreeItem);
                    */

                    App.changeScene(App.ContentScene.RELEASE, currentRelease);

                }
                else
                {
                    event.consume();
                }
            });

        return informationGrid;
    }
    
}
