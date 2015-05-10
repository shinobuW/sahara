package seng302.group2.scenes.information.Skill;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.scenes.SceneSwitcher;
import seng302.group2.workspace.skills.Skill;

import static seng302.group2.scenes.MainScene.informationPane;

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
    public static ScrollPane getSkillScene(Skill currentSkill)
    {
        informationPane = new VBox(10);

        /*informationPane.setAlignment(Pos.TOP_LEFT);
        informationPane.setHgap(10);
        informationPane.setVgap(10);*/
        informationPane.setPadding(new Insets(25,25,25,25));
        Label title = new Label(currentSkill.getShortName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnEdit = new Button("Edit");

        informationPane.getChildren().add(title);
        informationPane.getChildren().add(new Label("Skill Description: "));
        
        informationPane.getChildren().add(new Label(currentSkill.getDescription()));
        informationPane.getChildren().add(btnEdit);

        btnEdit.setOnAction((event) ->
            {
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.SKILL_EDIT, currentSkill);
            });

        return new ScrollPane(informationPane);
    }
 
    public static void refreshSkillScene(Skill skill)
    {
        SceneSwitcher.changeScene(SceneSwitcher.ContentScene.SKILL, skill);
    }
}
    
