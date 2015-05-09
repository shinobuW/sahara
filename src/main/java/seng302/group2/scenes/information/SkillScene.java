package seng302.group2.scenes.information;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.App;
import seng302.group2.workspace.skills.Skill;

import static seng302.group2.scenes.MainScene.informationGrid;

/**
 * A class for displaying the skill scene
 * @author drm127
 */
@SuppressWarnings("deprecation")
public class SkillScene
{
    /**
     * Gets the Skill information scene
     * @param currentSkill the skill to show the information of
     * @return The Skill information scene
     */
    public static Pane getSkillScene(Skill currentSkill)
    {
        informationGrid = new VBox(10);

        /*informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);*/
        informationGrid.setPadding(new Insets(25,25,25,25));
        Label title = new Label(currentSkill.getShortName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnEdit = new Button("Edit");

        informationGrid.getChildren().add(title);
        informationGrid.getChildren().add(new Label("Skill Description: "));
        
        informationGrid.getChildren().add(new Label(currentSkill.getDescription()));
        informationGrid.getChildren().add(btnEdit);

        btnEdit.setOnAction((event) ->
            {
                App.changeScene(App.ContentScene.SKILL_EDIT, currentSkill);
            });

        return informationGrid;
    }
 
    public static void refreshSkillScene(Skill skill)
    {
        App.changeScene(App.ContentScene.SKILL, skill);
    }
}
    
