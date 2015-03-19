/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.menu;

import java.util.Date;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.controlsfx.dialog.Dialog;
import seng302.group2.App;
import seng302.group2.project.team.person.Person;

/**
 *
 * @author swi67
 */
public class CreatePersonDialog
{
    public static void show()
    {
        Dialog dialog = new Dialog(null, "Create New Person");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        
        TextField shortNameField = new TextField();
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField emailField = new TextField();
        TextField descriptionField = new TextField();
        TextField birthdateField = new TextField();
        
        Button btnCreate = new Button("Create");
        Button btnCancel = new Button("Cancel");
        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(btnCreate, btnCancel);
        
        grid.add(new Label("Short Name"), 0, 0);
        grid.add(shortNameField, 1, 0);
        grid.add(new Label("First Name"), 0, 1);
        grid.add(firstNameField, 1, 1);
        grid.add(new Label("Last Name"), 0, 2);
        grid.add(lastNameField, 1, 2);
        grid.add(new Label("Email"), 0, 3);
        grid.add(emailField, 1, 3);
        grid.add(new Label("Birth Date"), 0, 4);
        grid.add(birthdateField, 1, 4);
        grid.add(new Label("Description"), 0, 5);
        grid.add(descriptionField, 1, 5);
        grid.add(buttons, 1, 6);
        
        
        String birthDatePattern = "dd/MM/yyyy";
        
        Date birthDate = new Date();
                       
        btnCreate.setOnAction((event) ->
            {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String shortName = shortNameField.getText();
                String email = emailField.getText();
                String description = descriptionField.getText();
                String birthdateString = birthdateField.getText();
                
                Person person = new Person(shortName, firstName, lastName, email, description,
                        birthDate);
                System.out.println(firstName + "Testing");
                App.currentProject.addPerson(person);
                dialog.hide();
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
    
    private String[] validation()
    {
        // TODO Shinobu: What even? See to use ControlsFX validation as well.
        String[] errorList = new String[10];      
        return errorList;
    }
}