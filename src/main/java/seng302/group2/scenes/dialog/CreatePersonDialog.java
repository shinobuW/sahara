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
import seng302.group2.util.validation.DateValidator;
import seng302.group2.util.validation.NameValidator;
import seng302.group2.util.validation.ShortNameValidator;

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
                boolean correctDate = validateDate(customBirthDate);
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
            {
                dialog.hide();
            });
                
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
     * Checks whether the birth date format is correct
     * Shows error message and red borders if incorrect
     * @param birthDateString the birth date string to try parse
     * @param dateField the date GUI field
     * @param birthdateError the birth date error GUI label
     * @return true if correct format
    **/
    public static boolean validateDate(CustomDateField customBirthDate)
    {
        // It is okay for the field to be blank, otherwise validate
        if(customBirthDate == null) 
        {
            return true;
        }
        System.out.print(customBirthDate.getText());
        
        switch (DateValidator.isValidDateString(customBirthDate.getText()))
        {
            case VALID:
                customBirthDate.hideErrorField();
                return true;
            case OUT_OF_RANGE:
                customBirthDate.showErrorField("* This is not a valid birth date");
                return false;
            case PATTERN_MISMATCH:
                customBirthDate.showErrorField("* Format must be dd/MM/yyyy e.g 12/03/1990");
                return false;
            default:
                return true;
        }
    }

    
    /**
     * Checks whether the name is valid
     * @param nameField the text field
     * @return If the name is valid
     */
    public static boolean validateName(RequiredField nameField) 
    {
        switch (NameValidator.validateName(nameField.getText()))
        {
            case VALID:
                nameField.hideErrorField();
                return true;
            case INVALID:
                nameField.showErrorField("* Please insert a name");
                return false;
            default:
                nameField.showErrorField("* Not a valid name");
                return false;
        }
    }
    
    
    /**
     * Checks whether a given short name is valid (unique and not null/empty)
     * @param shortNameField is a short name field
     * @return If the short name is valid
     */   
    
    public static boolean validateShortName(RequiredField shortNameField) 
    {
        switch (ShortNameValidator.validateShortName(shortNameField.getText()))
        {
            case VALID:
                //shortNameError.setText(null);
                //shortNameField.setStyle(null); 
                shortNameField.hideErrorField();
                return true;
            case NON_UNIQUE:
                shortNameField.showErrorField("* Short name has already been taken");
                return false;
            case INVALID:
                shortNameField.showErrorField("* Not a valid short name");
                return false;
            case OUT_OF_RANGE:
                shortNameField.showErrorField("* Short names must be less than 20 characters long");
                return false;
            default:
                shortNameField.showErrorField("* Not a valid short name");
                return false;
        }
    }
   
    
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