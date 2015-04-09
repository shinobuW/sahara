/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information;

import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import seng302.group2.App;
import static seng302.group2.App.informationGrid;
import seng302.group2.Global;
import static seng302.group2.Global.selectedTreeItem;
import seng302.group2.project.skills.Skill;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import static seng302.group2.scenes.dialog.CreatePersonDialog.validateShortName;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.listdisplay.TreeViewWithItems;

/**
 *
 * @author drm127
 */
public class SkillEditScene
{
    /**
     * Gets the Skill Edit information display
     * @return The Skill Edit information display
     */
    public static GridPane getSkillEditScene()
    {

        Skill currentSkill = (Skill) selectedTreeItem.getValue();
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
                App.content.getChildren().remove(App.informationGrid);
                SkillScene.getSkillScene();
                App.content.getChildren().add(App.informationGrid);

            });

        btnSave.setOnAction((event) ->
            {
                boolean correctShortName = validateShortName(shortNameCustomField);


                if (correctShortName)
                {
                    currentSkill.setDescription(descriptionTextArea.getText());
                    currentSkill.setShortName(shortNameCustomField.getText());
                    App.content.getChildren().remove(App.treeView);
                    App.content.getChildren().remove(App.informationGrid);
                    SkillScene.getSkillScene();
                    App.treeView = new TreeViewWithItems(new TreeItem());
                    ObservableList<TreeViewItem> children = observableArrayList();
                    children.add(Global.currentProject);

                    App.treeView.setItems(children);
                    App.treeView.setShowRoot(false);

                    App.content.getChildren().add(App.treeView);
                    App.content.getChildren().add(App.informationGrid);
                    App.treeView.getSelectionModel().select(selectedTreeItem);

                }
                else
                {
                    event.consume();
                }

            });

        return App.informationGrid;
    }
}
