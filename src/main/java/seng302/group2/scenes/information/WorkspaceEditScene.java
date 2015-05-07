package seng302.group2.scenes.information;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.util.validation.NameValidator;
import seng302.group2.util.validation.ShortNameValidator;
import seng302.group2.workspace.Workspace;

import static seng302.group2.scenes.MainScene.informationGrid;

/**
 * A class for displaying the workspace edit scene.
 * Created by btm38 on 7/04/15.
 */
public class WorkspaceEditScene
{
    /**
     * Gets the workspace edit information scene.
     * @param currentWorkspace The workspace to get the editable information scene for
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

        RequiredField shortNameCustomField = new RequiredField("Short Name: ");
        RequiredField longNameCustomField = new RequiredField("Long Name: ");
        CustomTextArea descriptionTextArea = new CustomTextArea("Workspace Description: ", 300);
        
        shortNameCustomField.setText(currentWorkspace.getShortName());
        longNameCustomField.setText(currentWorkspace.getLongName());
        descriptionTextArea.setText(currentWorkspace.getDescription());

        informationGrid.add(shortNameCustomField, 0, 0);
        informationGrid.add(longNameCustomField, 0, 1);
        informationGrid.add(descriptionTextArea, 0, 2);
        informationGrid.add(buttons, 0, 3);


        btnCancel.setOnAction((event) ->
            {
                returnFromEdit();
            });

        btnSave.setOnAction((event) ->
            {

                /*boolean correctShortName;
                boolean correctLongName;   
                
                if (shortNameCustomField.getText().equals(currentWorkspace.getShortName()))
                {
                    correctShortName = true;
                }
                else
                {
                    correctShortName = validateShortName(shortNameCustomField);
                }
                
                if (longNameCustomField.getText().equals(currentWorkspace.getLongName()))
                {
                    correctLongName = true;
                }
                else
                {
                    correctLongName = validateShortName(longNameCustomField);
                }
                if (correctShortName && correctLongName)*/

                    // Build Undo/Redo edit array.
                    /*ArrayList<UndoableItem> undoActions = new ArrayList<>();
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
                                        currentWorkspace.getLongName()),
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
                    
                    if (undoActions.size() > 0)
                    {
                        Global.undoRedoMan.add(new UndoableItem(
                                currentWorkspace,
                            new UndoRedoAction(
                                    UndoRedoPerformer.UndoRedoProperty.WORKSPACE_EDIT,
                                    undoActions), 
                            new UndoRedoAction(
                                    UndoRedoPerformer.UndoRedoProperty.WORKSPACE_EDIT,
                                    undoActions)
                            ));   
                    }
                    
                    // Save the edits.
                    currentWorkspace.setDescription(descriptionTextArea.getText());
                    currentWorkspace.setShortName(shortNameCustomField.getText());
                    currentWorkspace.setLongName(longNameCustomField.getText());*/




                    /*
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
                    MainScene.treeView.getSelectionModel().select(selectedTreeItem);*/




                if (shortNameCustomField.getText().equals(currentWorkspace.getShortName())
                        && longNameCustomField.getText().equals(currentWorkspace.getLongName())
                        && descriptionTextArea.getText().equals(currentWorkspace.getDescription()))
                {
                    // No fields have been changed
                    returnFromEdit();
                }
                    // The short name is the same or valid
                if ((shortNameCustomField.getText().equals(currentWorkspace.getShortName())
                            || ShortNameValidator.validateShortName(shortNameCustomField))
                            && // and the long name is the same or valid
                            (longNameCustomField.getText().equals(currentWorkspace.getLongName())
                                    || NameValidator.validateName(longNameCustomField)))
                {
                    currentWorkspace.edit(shortNameCustomField.getText(),
                            longNameCustomField.getText(), descriptionTextArea.getText());

                    returnFromEdit();
                    MainScene.treeView.refresh();
                }
                else
                {
                    // One or more fields incorrectly validated, stay on the edit scene
                    event.consume();
                }

            });

        return informationGrid;
    }

    private static void returnFromEdit()
    {
        App.content.getChildren().remove(informationGrid);
        WorkspaceScene.getWorkspaceScene((Workspace) Global.selectedTreeItem.getValue());
        App.content.getChildren().add(informationGrid);
    }
}
