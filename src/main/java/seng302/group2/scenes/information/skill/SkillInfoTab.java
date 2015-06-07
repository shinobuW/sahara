package seng302.group2.scenes.information.skill;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.sceneswitch.SceneSwitcher;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.workspace.skills.Skill;


/**
 * The workspace information tab
 * Created by jml168 on 11/05/15.
 */
public class SkillInfoTab extends Tab
{
    public SkillInfoTab(Skill currentSkill)
    {
        this.setText("Basic Information");

        Pane basicInfoPane = new VBox(10);  // The pane that holds the basic info
        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        Label title = new TitleLabel(currentSkill.getShortName());

        Button btnEdit = new Button("Edit");

        basicInfoPane.getChildren().add(title);
        basicInfoPane.getChildren().add(new Label("Skill Description: "
                + currentSkill.getDescription()));

        basicInfoPane.getChildren().add(btnEdit);
        
        if (currentSkill.getShortName().equals("Product Owner") 
            || currentSkill.getShortName().equals("Scrum Master"))
        {
            btnEdit.setDisable(true);
        }

        btnEdit.setOnAction((event) ->
            {
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.SKILL_EDIT, currentSkill);
            });
    }
}
