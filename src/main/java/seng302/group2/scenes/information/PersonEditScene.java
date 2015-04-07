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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import seng302.group2.App;
import static seng302.group2.App.informationGrid;
import static seng302.group2.Global.selectedTreeItem;
import seng302.group2.project.team.person.Person;

/**
 *
 * @author swi67
 */
public class PersonEditScene
{
    /**
     * Gets the Person Edit information display
     * @return The Person Edit information display
     */
    public static GridPane getPersonEditScene()
    {
        Person currentPerson = (Person) selectedTreeItem.getValue();

        informationGrid = new GridPane();
        TextField shortNameField = new TextField();
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField emailField = new TextField();
        TextField birthDateField = new TextField();
        TextField descriptionField = new TextField();

        Button btnSave = new Button("Save");
        Button btnCancel = new Button("Cancel");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(btnSave, btnCancel);


        informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);
        informationGrid.setPadding(new Insets(25,25,25,25));
        
        informationGrid.add(new Label("First Name: "), 0, 0);
        informationGrid.add(firstNameField, 1, 0);
        firstNameField.setText(currentPerson.getFirstName());
        
        informationGrid.add(new Label("Last Name: "), 0, 1);
        informationGrid.add(lastNameField, 1, 1);
        lastNameField.setText(currentPerson.getLastName());
        
        informationGrid.add(new Label("Short Name: "), 0, 2);
        informationGrid.add(shortNameField, 1, 2);
        shortNameField.setText(currentPerson.getShortName());
        
        informationGrid.add(new Label("Email Address: "), 0, 3);
        informationGrid.add(emailField, 1, 3);
        emailField.setText(currentPerson.getEmail());
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        informationGrid.add(new Label("Birth Date: "), 0, 4);
        informationGrid.add(birthDateField, 1, 4);
        birthDateField.setText(dateFormat.format(currentPerson.getBirthDate()));
        
        informationGrid.add(new Label("Description: "), 0, 5);
        informationGrid.add(descriptionField, 1, 5);
        descriptionField.setText(currentPerson.getDescription());
        
        informationGrid.add(buttons, 1, 6);

        btnCancel.setOnAction((event) ->
            {
                App.content.getChildren().remove(App.informationGrid);
                PersonScene.getPersonScene();
                App.content.getChildren().add(App.informationGrid);
            });
        
        btnSave.setOnAction((event) ->
            {
                currentPerson.setFirstName(firstNameField.getText());
                currentPerson.setShortName(shortNameField.getText());
                currentPerson.setLastName(lastNameField.getText());
                currentPerson.setDescription(descriptionField.getText());
                currentPerson.setEmail(emailField.getText());
           
                //String birthdate = birthDateField.getText();
                
                App.content.getChildren().remove(App.informationGrid);
                PersonScene.getPersonScene();
                App.content.getChildren().add(App.informationGrid);

            });
        

        
        return App.informationGrid;
    }
}
