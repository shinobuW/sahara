/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.dialog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.dialog.Dialog;
import seng302.group2.Global;
import seng302.group2.project.team.Team;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * Class to create a pop up dialog for creating a team.
 * @author crw73
 */
@SuppressWarnings("deprecation")
public class CreateTeamDialog 
{
    /**
     * Displays the Dialog box for creating a Team.
     */
    public static void show()
    {
        Dialog dialog = new Dialog(null, "New Team");
        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);
               
        Button btnCreate = new Button("Create");
        Button btnCancel = new Button("Cancel");
        
        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(btnCreate, btnCancel);
        
        RequiredField shortNameCustomField = new RequiredField("Short Name");
        CustomTextArea descriptionTextArea = new CustomTextArea("Skill Description");
        
        grid.getChildren().add(shortNameCustomField);
        grid.getChildren().add(descriptionTextArea);
        grid.getChildren().add(buttons);
        
        btnCreate.setOnAction((event) ->
            {
                boolean correctShortName = validateShortName(shortNameCustomField);
                
                String shortName = shortNameCustomField.getText();
                String description = descriptionTextArea.getText();
                
                if (correctShortName)
                {
                    Team team = new Team(shortName, description);
                    Global.currentProject.add(team);
                    dialog.hide();
                }
                else
                {
                    event.consume();
                }
            });
        
        btnCancel.setOnAction((event) ->
            {
                dialog.hide();
            });
        
        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(grid);
        dialog.show();
    }
}
