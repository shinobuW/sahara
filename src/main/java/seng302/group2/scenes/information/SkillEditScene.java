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
import seng302.group2.scenes.SceneSwitcher;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.workspace.skills.Skill;

import static seng302.group2.scenes.MainScene.informationGrid;
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
    public static Pane getSkillEditScene(Skill currentSkill)
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
        CustomTextArea descriptionTextArea = new CustomTextArea("Skill Description: ", 300);

        shortNameCustomField.setMaxWidth(275);
        descriptionTextArea.setMaxWidth(275);

        shortNameCustomField.setText(currentSkill.getShortName());
        descriptionTextArea.setText(currentSkill.getDescription());

        informationGrid.getChildren().add(shortNameCustomField);
        informationGrid.getChildren().add(descriptionTextArea);
        informationGrid.getChildren().add(buttons);


        btnCancel.setOnAction((event) ->
            {
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.SKILL, currentSkill);
            });

        btnSave.setOnAction((event) ->
            {
                if (shortNameCustomField.getText().equals(currentSkill.getShortName())
                        && descriptionTextArea.getText().equals(currentSkill.getDescription()))
                {
                    // No changes
                    SceneSwitcher.changeScene(SceneSwitcher.ContentScene.SKILL, currentSkill);
                }

                else if (shortNameCustomField.getText().equals(currentSkill.getShortName())
                        || validateShortName(shortNameCustomField))
                {
                    // Valid short name, make the edit
                    currentSkill.edit(shortNameCustomField.getText(),
                            descriptionTextArea.getText());

                    SceneSwitcher.changeScene(SceneSwitcher.ContentScene.SKILL, currentSkill);
                }


/*              if (correctShortName)
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
                    currentSkill.setShortName(shortNameCustomField.getText());*/

                    /*
                    App.content.getItems().remove(treeView);
                    App.content.getItems().remove(informationGrid);
                    SkillScene.getSkillScene(currentSkill);
                    MainScene.treeView = new TreeViewWithItems(new TreeItem());
                    ObservableList<TreeViewItem> children = observableArrayList();
                    children.add(Global.currentWorkspace);

                    MainScene.treeView.setItems(children);
                    MainScene.treeView.setShowRoot(false);

                    App.content.getItems().add(treeView);
                    App.content.getItems().add(informationGrid);
                    MainScene.treeView.getSelectionModel().select(selectedTreeItem);
                    */



                else
                {
                    event.consume();
                }

            });

        return informationGrid;
    }
}
