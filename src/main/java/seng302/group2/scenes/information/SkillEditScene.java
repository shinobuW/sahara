/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import seng302.group2.workspace.skills.Skill;

import java.util.ArrayList;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.Global.selectedTreeItem;
import static seng302.group2.scenes.MainScene.informationGrid;
import static seng302.group2.scenes.MainScene.treeView;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * A class for displaying the skill edit scene.
 * @author drm127
 */
public class SkillEditScene
{
    /**
     * Gets the Skill Edit information scene.
     * @param currentSkill The skill to show the information of
     * @return The Skill Edit information display
     */
    public static GridPane getSkillEditScene(Skill currentSkill)
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
        CustomTextArea descriptionTextArea = new CustomTextArea("Skill Description", 300);
        
        shortNameCustomField.setText(currentSkill.getShortName());
        descriptionTextArea.setText(currentSkill.getDescription());

        informationGrid.add(shortNameCustomField, 0, 0);
        informationGrid.add(descriptionTextArea, 0, 1);
        informationGrid.add(buttons, 0,2);


        btnCancel.setOnAction((event) ->
            {
                App.content.getChildren().remove(informationGrid);
                SkillScene.getSkillScene((Skill) Global.selectedTreeItem.getValue());
                App.content.getChildren().add(informationGrid);

            });

        btnSave.setOnAction((event) ->
            {
                boolean correctShortName;
                
                if (shortNameCustomField.getText().equals(currentSkill.getShortName()))
                {
                    correctShortName = true;
                }
                else
                {
                    correctShortName = validateShortName(shortNameCustomField);
                }


                if (correctShortName)
                {
                    // Build Undo/Redo edit array.
                    ArrayList<UndoableItem> undoActions = new ArrayList<>();          
                    if (shortNameCustomField.getText() != currentSkill.getShortName())
                    {
                        undoActions.add(new UndoableItem(
                                currentSkill,
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.SKILL_SHORTNAME,
                                        currentSkill.getShortName()),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.SKILL_SHORTNAME,
                                        shortNameCustomField.getText())));
                    }
                    
                    if (descriptionTextArea.getText() != currentSkill.getDescription())
                    {
                        undoActions.add(new UndoableItem(
                                currentSkill,
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.SKILL_DESCRIPTION,
                                        currentSkill.getDescription()),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.SKILL_DESCRIPTION,
                                        descriptionTextArea.getText())));
                    }
                           
                    Global.undoRedoMan.add(new UndoableItem(
                        currentSkill,
                        new UndoRedoAction(
                                UndoRedoPerformer.UndoRedoProperty.SKILL_EDIT,
                                undoActions), 
                        new UndoRedoAction(
                                UndoRedoPerformer.UndoRedoProperty.SKILL_EDIT, 
                                undoActions)
                        ));                      
                            
                    // Save the edits.        
                    currentSkill.setDescription(descriptionTextArea.getText());
                    currentSkill.setShortName(shortNameCustomField.getText());
                    
                    App.content.getChildren().remove(treeView);
                    App.content.getChildren().remove(informationGrid);
                    SkillScene.getSkillScene(currentSkill);
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
