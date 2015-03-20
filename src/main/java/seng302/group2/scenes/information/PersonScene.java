/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.App;
import static seng302.group2.App.informationGrid;
import static seng302.group2.App.selectedTreeItem;
import seng302.group2.project.team.person.Person;

/**
 * A class for displaying the Person Scene
 * @author crw73
 */
public class PersonScene
{
    
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
        
        informationGrid.add(title, 0, 0);
        informationGrid.add(new Label("Short Name: " + currentPerson.getShortName()), 0, 1);
        informationGrid.add(new Label("Email Address: " + currentPerson.getEmail()), 0, 2);
        informationGrid.add(new Label("Birth Date: " + currentPerson.getBirthDate().toString()), 
                0, 3);
        informationGrid.add(new Label("Description: " + currentPerson.getDescription()), 0, 4);
        
        
        return App.informationGrid;
    }

}
