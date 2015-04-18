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
import seng302.group2.workspace.Workspace;

import java.util.ArrayList;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.Global.selectedTreeItem;
import static seng302.group2.scenes.MainScene.informationGrid;
import static seng302.group2.scenes.MainScene.treeView;
import static seng302.group2.util.validation.NameValidator.validateName;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * A class for displaying the workspace edit scene.
 * Created by btm38 on 7/04/15.
 */
public class WorkspaceEditScene
{
    /**
     * Gets the workspace edit information scene.
     * @return The Workspace Edit information scene
     */
    public static GridPane getWorkspaceEditScene(Workspace currentWorkspace)
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
        
        shortNameCustomField.setText(currentWorkspace.getShortName());
        longNameCustomField.setText(currentWorkspace.getLongName());
        descriptionTextArea.setText(currentWorkspace.getDescription());

        informationGrid.add(shortNameCustomField, 0, 0);
        informationGrid.add(longNameCustomField, 0, 1);
        informationGrid.add(descriptionTextArea, 0, 2);
        informationGrid.add(buttons, 0, 3);


        btnCancel.setOnAction((event) ->
            {
                App.content.getChildren().remove(informationGrid);
                WorkspaceScene.getWorkspaceScene((Workspace) Global.selectedTreeItem.getValue());
                App.content.getChildren().add(informationGrid);

            });

        btnSave.setOnAction((event) ->
            {
                boolean correctShortName = validateShortName(shortNameCustomField);
                boolean correctLongName = validateName(longNameCustomField);


                if (correctShortName && correctLongName)
                {
                    // Build Undo/Redo edit array.
                    ArrayList<UndoableItem> undoActions = new ArrayList<>();                    
                    if (shortNameCustomField.getText() != currentWorkspace.getShortName())
                    {
                        undoActions.add(new UndoableItem(
                                currentWorkspace,
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.WORKSPACE_SHORTNAME,
                                        currentWorkspace.getShortName()),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.WORKSPACE_SHORTNAME,
                                        shortNameCustomField.getText())));
                    }
                    
                    if (longNameCustomField.getText() != currentWorkspace.getLongName())
                    {
                        undoActions.add(new UndoableItem(
                                currentWorkspace,
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.WORKSPACE_LONGNAME,
                                        currentWorkspace.getDescription()),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.WORKSPACE_LONGNAME,
                                        longNameCustomField.getText())));                        
                    }
                    if (descriptionTextArea.getText() != currentWorkspace.getDescription())
                    {
                        undoActions.add(new UndoableItem(
                                currentWorkspace,
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.WORKSPACE_DESCRIPTION,
                                        currentWorkspace.getDescription()),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.WORKSPACE_DESCRIPTION,
                                        descriptionTextArea.getText())));
                    }
                    
                    Global.undoRedoMan.add(new UndoableItem(
                            currentWorkspace,
                        new UndoRedoAction(
                                UndoRedoPerformer.UndoRedoProperty.WORKSPACE_EDIT,
                                undoActions), 
                        new UndoRedoAction(
                                UndoRedoPerformer.UndoRedoProperty.WORKSPACE_EDIT,
                                undoActions)
                        ));   
                    
                    // Save the edits.
                    currentWorkspace.setDescription(descriptionTextArea.getText());
                    currentWorkspace.setShortName(shortNameCustomField.getText());
                    currentWorkspace.setLongName(longNameCustomField.getText());
                    App.content.getChildren().remove(treeView);
                    App.content.getChildren().remove(informationGrid);
                    WorkspaceScene.getWorkspaceScene(currentWorkspace);
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
