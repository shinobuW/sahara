/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information;

import java.text.SimpleDateFormat;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.App;
import static seng302.group2.App.informationGrid;
import static seng302.group2.Global.selectedTreeItem;
import seng302.group2.project.team.person.Person;

/**
 * A class for displaying the Person Scene
 * @author crw73
 */
public class PersonScene
{
    
    /**
     * Gets the Person information display
     * @return The Person information display
     */
    public static GridPane getPersonScene()
    {
        
        Person currentPerson = (Person) selectedTreeItem.getValue();
        informationGrid = new GridPane();

        
        informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);
        informationGrid.setPadding(new Insets(25,25,25,25));
        Label title = new Label(currentPerson.getFirstName() + " " + currentPerson.getLastName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnEdit = new Button("Edit");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        informationGrid.add(title, 0, 0, 3, 1);
        informationGrid.add(new Label("Short Name: "), 0, 2);
        informationGrid.add(new Label("Email Address: "), 0, 3);
        informationGrid.add(new Label("Birth Date: "), 0, 4);
        informationGrid.add(new Label("Description: "), 0, 5);

        informationGrid.add(new Label(currentPerson.getShortName()), 1, 2);
        informationGrid.add(new Label(currentPerson.getEmail()), 1,3);
        informationGrid.add(new Label(dateFormat.format(currentPerson.getBirthDate())), 1, 4);
        informationGrid.add(new Label(currentPerson.getDescription()), 1, 5);
        informationGrid.add(btnEdit,1,6);

        btnEdit.setOnAction((event) ->
            {
                App.content.getChildren().remove(App.informationGrid);
                PersonEditScene.getPersonEditScene();
                App.content.getChildren().add(App.informationGrid);
                
            });

        return App.informationGrid;
    }

}
