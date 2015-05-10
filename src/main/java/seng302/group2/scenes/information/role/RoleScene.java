/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information.role;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.workspace.role.Role;

import static seng302.group2.scenes.MainScene.informationPane;

/**
 * @author Shinobu
 */
public class RoleScene
{  
    /**
     * Gets the Role information scene
     * @param currentRole the selected role 
     * @return The Role information scene
     */
    public static ScrollPane getRoleScene(Role currentRole)
    {
        informationPane = new VBox(10);
        
        /*informationPane.setAlignment(Pos.TOP_LEFT);
        informationPane.setHgap(10);
        informationPane.setVgap(10);*/
        informationPane.setPadding(new Insets(25,25,25,25));
        Label title = new Label(currentRole.getShortName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        ListView skillsBox = new ListView(currentRole.getRequiredSkills());
        skillsBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        skillsBox.setMaxWidth(275);

        informationPane.getChildren().add(title);
        informationPane.getChildren().add(new Label("Role Description: "));
        informationPane.getChildren().add(new Label("Required Skills: "));

        informationPane.getChildren().add(new Label(currentRole.getDescription()));
        informationPane.getChildren().add(skillsBox);

        ScrollPane wrapper = new ScrollPane(informationPane);
        wrapper.setStyle("-fx-background-color:transparent;");
        return wrapper;
    }
}
