/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information.skill;

import java.util.Collections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.SceneSwitcher;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.workspace.skills.Skill;

import static seng302.group2.scenes.MainScene.informationPane;
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
    public static ScrollPane getSkillEditScene(Skill currentSkill)
    {
        informationPane = new VBox(10);
        /*informationPane.setAlignment(Pos.TOP_LEFT);
        informationPane.setHgap(10);
        informationPane.setVgap(10);*/
        informationPane.setPadding(new Insets(25,25,25,25));

        Button btnCancel = new Button("Cancel");
        Button btnSave = new Button("Done");

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

        informationPane.getChildren().add(shortNameCustomField);
        informationPane.getChildren().add(descriptionTextArea);
        informationPane.getChildren().add(buttons);


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

                    Collections.sort(Global.currentWorkspace.getSkills());
                    SceneSwitcher.changeScene(SceneSwitcher.ContentScene.SKILL, currentSkill);
                    MainScene.treeView.refresh();
                }

                else
                {
                    event.consume();
                }

            });

        ScrollPane wrapper = new ScrollPane(informationPane);
        wrapper.setStyle("-fx-background-color:transparent;");
        return wrapper;
    }
}
