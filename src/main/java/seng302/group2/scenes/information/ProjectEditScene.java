package seng302.group2.scenes.information;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.listdisplay.TreeViewWithItems;
import seng302.group2.util.undoredo.UndoRedoAction;
import seng302.group2.util.undoredo.UndoRedoPerformer;
import seng302.group2.util.undoredo.UndoableItem;
import seng302.group2.workspace.project.Project;

import java.util.ArrayList;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.Global.selectedTreeItem;
import static seng302.group2.scenes.MainScene.informationGrid;
import static seng302.group2.scenes.MainScene.treeView;
import static seng302.group2.util.validation.NameValidator.validateName;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * A class for displaying the project edit scene.
 * Created by jml168 on 7/04/15.
 */
public class ProjectEditScene
{
    /**
     * Gets the workspace edit information scene.
     * @return The Workspace Edit information scene
     */
    public static GridPane getProjectEditScene(Project currentProject)
    {
        informationGrid = new GridPane();
        informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);
        informationGrid.setPadding(new Insets(25,25,25,25));

        Button btnCancel = new Button("Cancel");
        Button btnSave = new Button("Save");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(btnSave, btnCancel);

        RequiredField shortNameCustomField = new RequiredField("Short Name");
        RequiredField longNameCustomField = new RequiredField("Long Name");
        CustomTextArea descriptionTextArea = new CustomTextArea("Workspace Description", 300);

        shortNameCustomField.setText(currentProject.getShortName());
        longNameCustomField.setText(currentProject.getLongName());
        descriptionTextArea.setText(currentProject.getDescription());

        informationGrid.add(shortNameCustomField, 0, 0);
        informationGrid.add(longNameCustomField, 0, 1);
        informationGrid.add(descriptionTextArea, 0, 2);
        informationGrid.add(buttons, 0, 3);


        btnCancel.setOnAction((event) ->
            {
                App.content.getChildren().remove(informationGrid);
                ProjectScene.getProjectScene((Project) Global.selectedTreeItem.getValue());
                App.content.getChildren().add(informationGrid);

            });

        btnSave.setOnAction((event) ->
            {
              
                boolean correctShortName;
                boolean correctLongName;   
                
                if (shortNameCustomField.getText().equals(currentProject.getShortName()))
                {
                    correctShortName = true;
                }
                else
                {
                    correctShortName = validateShortName(shortNameCustomField);
                }
                
                if (longNameCustomField.getText().equals(currentProject.getLongName()))
                {
                    correctLongName = true;
                }
                else
                {
                    correctLongName = validateShortName(longNameCustomField);
                }

                if (correctShortName && correctLongName)
                {
                    // Build Undo/Redo edit array.
                    ArrayList<UndoableItem> undoActions = new ArrayList<>();
                    if (shortNameCustomField.getText() != currentProject.getShortName())
                    {
                        undoActions.add(new UndoableItem(
                                currentProject,
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PROJECT_SHORTNAME,
                                        currentProject.getShortName()),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PROJECT_SHORTNAME,
                                        shortNameCustomField.getText())));
                    }

                    if (longNameCustomField.getText() != currentProject.getLongName())
                    {
                        undoActions.add(new UndoableItem(
                                currentProject,
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PROJECT_LONGNAME,
                                        currentProject.getDescription()),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PROJECT_LONGNAME,
                                        longNameCustomField.getText())));
                    }

                    if (descriptionTextArea.getText() != currentProject.getDescription())
                    {
                        undoActions.add(new UndoableItem(
                                currentProject,
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PROJECT_DESCRIPTION,
                                        currentProject.getDescription()),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PROJECT_DESCRIPTION,
                                        descriptionTextArea.getText())));
                    }

                    Global.undoRedoMan.add(new UndoableItem(
                            currentProject,
                            new UndoRedoAction(
                                    UndoRedoPerformer.UndoRedoProperty.PROJECT_EDIT,
                                    undoActions),
                            new UndoRedoAction(
                                    UndoRedoPerformer.UndoRedoProperty.PROJECT_EDIT,
                                    undoActions)
                    ));

                    // Save the edits.
                    currentProject.setDescription(descriptionTextArea.getText());
                    currentProject.setShortName(shortNameCustomField.getText());
                    currentProject.setLongName(longNameCustomField.getText());
                    App.content.getChildren().remove(treeView);
                    App.content.getChildren().remove(informationGrid);
                    ProjectScene.getProjectScene(currentProject);
                    MainScene.treeView = new TreeViewWithItems(new TreeItem());
                    ObservableList<TreeViewItem> children = observableArrayList();
                    children.add(Global.currentWorkspace);

                    MainScene.treeView.setItems(children);
                    MainScene.treeView.setShowRoot(false);

                    App.content.getChildren().add(treeView);
                    App.content.getChildren().add(informationGrid);
                    MainScene.treeView.getSelectionModel().select(selectedTreeItem);
                }
                else
                {
                    event.consume();
                }
            });

        return informationGrid;
    }
}
