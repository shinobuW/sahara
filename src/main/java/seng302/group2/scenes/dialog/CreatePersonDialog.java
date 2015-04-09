/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.dialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.dialog.Dialog;
import seng302.group2.Global;
import seng302.group2.project.team.person.Person;
import seng302.group2.scenes.control.CustomDateField;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.CustomTextField;
import seng302.group2.scenes.control.RequiredField;
import static seng302.group2.util.validation.DateValidator.validateBirthDate;
import static seng302.group2.util.validation.NameValidator.validateName;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 *Class to create a pop up dialog for creating a person
 * @author swi67
 */
@SuppressWarnings("deprecation")
public class CreatePersonDialog
{
    public static void show()
    {
        // Initialise Dialog and GridPane
        Dialog dialog = new Dialog(null, "Create New Person");
        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);
        
        // Initialise Input fields
        Button btnCreate = new Button("Create");
        Button btnCancel = new Button("Cancel");
        
        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(btnCreate, btnCancel);
        
        // Add elements to grid
        RequiredField shortNameCustomField = new RequiredField("Short Name");
        RequiredField firstNameCustomField = new RequiredField("First Name");
        RequiredField lastNameCustomField = new RequiredField("Last Name");
        CustomTextField emailTextField = new CustomTextField("Email");
        CustomDateField customBirthDate = new CustomDateField("Birth Date");
        CustomTextArea descriptionTextArea = new CustomTextArea("Description");
        
        grid.getChildren().add(shortNameCustomField);
        grid.getChildren().add(firstNameCustomField);  
        grid.getChildren().add(lastNameCustomField);  
        grid.getChildren().add(emailTextField);  
        grid.getChildren().add(customBirthDate);  
        grid.getChildren().add(descriptionTextArea);  
        grid.getChildren().add(buttons);
           
        // "Create" button event
        btnCreate.setOnAction((event) ->
            {
                boolean correctDate = validateBirthDate(customBirthDate);
                boolean correctShortName = validateShortName(shortNameCustomField);
                boolean correctFirstName = validateName(firstNameCustomField);
                boolean correctLastName = validateName(lastNameCustomField);
                
                if (correctShortName && correctFirstName && correctLastName)
                {
                    //get user input
                    String firstName = firstNameCustomField.getText();
                    String lastName = lastNameCustomField.getText();
                    String shortName = shortNameCustomField.getText();
                    String email = emailTextField.getText();
                    String description = descriptionTextArea.getText();
                    String birthdateString = customBirthDate.getText();
                    
                    final Date birthDate = stringToDate(birthdateString);
                    Person person = new Person(shortName, firstName, lastName, email, description,
                        birthDate);
                    Global.currentProject.addPerson(person);
                    dialog.hide();
                }
                else 
                {
                    btnCreate.disableProperty();
                } 
            });
        
        btnCancel.setOnAction((event) ->
                dialog.hide());
                
        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(grid);
        dialog.show();        
    }
    
    
    /**
     * Checks whether the email is correct format
     * Error message shown and TextField border changed to red if incorrect
     * @param email the email address
     * @param emailField the email text field
     * @return whether or not the email is valid
     */
//    public static boolean validateEmail(String email, CustomTextField emailField)
//    {
//        switch (EmailValidator.validEmail(email))
//        {
//            case VALID:
//                emailField.setStyle(null);
//                return true;
//            case INVALID:
//                emailField.setStyle("-fx-border-color: red;");
//                emailField.showErrorField("Not a valid email address");
//                emailError.setText("*Not a valid email address");
//                return false;
//            default:
//                emailError.setText("*Not a valid email address");
//                emailField.setStyle("-fx-border-color: red;");
//                return false;
//        }
//    }
//    
    


    /**
     * Converts strings to dates
     * @param birthDateString the birth date string
     * @return the parsed date
     */
    public static Date stringToDate(String birthDateString)
    {
        Date birthDate = new Date();
        try
        {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            df.setLenient(false);
            df.parse(birthDateString);
            birthDate = df.parse(birthDateString);
        }
        catch (ParseException e)
        {
            System.out.println("Error parsing date");
        }
        return birthDate;
    }
}
