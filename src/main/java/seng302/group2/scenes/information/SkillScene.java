/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.App;
import static seng302.group2.Global.selectedTreeItem;
import seng302.group2.project.skills.Skill;
import static seng302.group2.scenes.MainScene.informationGrid;

/**
 *
 * @author drm127
 */
@SuppressWarnings("deprecation")
public class SkillScene
{
    /**
     * Gets the Skill information display
     * @return The Skill information display
     */
    public static GridPane getSkillScene()
    {
        
        Skill currentSkill = (Skill) selectedTreeItem.getValue();
        informationGrid = new GridPane();

        informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);
        informationGrid.setPadding(new Insets(25,25,25,25));
        Label title = new Label(currentSkill.getShortName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnEdit = new Button("Edit");

        informationGrid.add(title, 0, 0, 3, 1);
        informationGrid.add(new Label("Description: "), 0, 2);
        
        informationGrid.add(new Label(currentSkill.getDescription()), 1, 2);
        informationGrid.add(btnEdit, 1, 3);

        btnEdit.setOnAction((event) ->
            {
                App.content.getChildren().remove(informationGrid);
                SkillEditScene.getSkillEditScene();
                App.content.getChildren().add(informationGrid);
            });

        return informationGrid;
    }
 
}
