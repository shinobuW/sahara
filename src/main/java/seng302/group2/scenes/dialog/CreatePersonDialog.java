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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.controlsfx.dialog.Dialog;
import seng302.group2.Global;
import seng302.group2.project.team.person.Person;
import seng302.group2.util.validation.DateValidator;
import seng302.group2.util.validation.EmailValidator;
import seng302.group2.util.validation.NameValidator;
import seng302.group2.util.validation.ShortNameValidator;
import static seng302.group2.util.validation.ValidationStatus.OUT_OF_RANGE;
import static seng302.group2.util.validation.ValidationStatus.PATTERN_MISMATCH;
import static seng302.group2.util.validation.ValidationStatus.VALID;


/**
 *Class to create a pop up dialog for creating a person
 * @author swi67
 */
public class CreatePersonDialog
{
    public static void show()
    {
        // Initialise Dialog and GridPane
        Dialog dialog = new Dialog(null, "Create New Person");
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);
        
        // Initialise Input fields
        TextField shortNameField = new TextField();
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField emailField = new TextField();
        TextArea descriptionField = new TextArea();
        TextField birthdateField = new TextField();
        birthdateField.setPromptText("dd/mm/yyyy");
        
        Button btnCreate = new Button("Create");
        Button btnCancel = new Button("Cancel");
        
        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(btnCreate, btnCancel);
        
        Label shortNameError = new Label();
        Label firstNameError = new Label();
        Label lastNameError = new Label();
        Label dateError = new Label();
        Label emailError = new Label();
        
        // Add elements to grid
        grid.add(new Label("Short Name"), 0, 0);
        grid.add(shortNameField, 1, 0);
        grid.add(shortNameError, 2, 0, 10, 1);
        
        grid.add(new Label("First Name"), 0, 1);
        grid.add(firstNameField, 1, 1);
        grid.add(firstNameError, 2, 1, 10, 1);
        
        grid.add(new Label("Last Name"), 0, 2);
        grid.add(lastNameField, 1, 2);
        grid.add(lastNameError, 2, 2, 10, 1);
        
        grid.add(new Label("Email"), 0, 3);
        grid.add(emailField, 1, 3);
        grid.add(emailError, 2, 3, 10, 1);
        
        grid.add(new Label("Birth Date"), 0, 4);
        grid.add(birthdateField, 1, 4);
        grid.add(dateError, 2, 4, 18, 1);
        
        grid.add(new Label("Description"), 0, 5);
        grid.add(descriptionField, 1, 5);
        grid.add(buttons, 1, 6);
           
        // "Create" button event
        btnCreate.setOnAction((event) ->
            {
                // Get user input
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String shortName = shortNameField.getText();
                String email = emailField.getText();
                String description = descriptionField.getText();
                String birthdateString = birthdateField.getText();
                
                // Validation & Error messages
                boolean dateCorrect = validateDate(birthdateString, birthdateField,
                        dateError);
                boolean emailCorrect = validateEmail(email, emailField,  emailError);
                boolean isShortNameUnique = validateShortName(shortName, shortNameError,
                        shortNameField);
                boolean shortNameCorrect = validateShortName(shortName, 
                        shortNameError, shortNameField); 
                boolean firstNameCorrect = validateName(firstName, firstNameField, firstNameError,
                        "first name");
                boolean lastNameCorrect = validateName(lastName, lastNameField, lastNameError,
                        "last name");

                // Create new person if all fields are correct
                if (dateCorrect && emailCorrect && shortNameCorrect && firstNameCorrect 
                        && lastNameCorrect && isShortNameUnique) 
                {
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
     * @param emailError the email error field
     * @return whether or not the email is valid
     */
    public static boolean validateEmail(String email, TextField emailField, Label emailError)
    {
        switch (EmailValidator.validEmail(email))
        {
            case VALID:
                emailError.setText(null);
                emailField.setStyle(null);
                return true;
            case INVALID:
                emailField.setStyle("-fx-border-color: red;");
                emailError.setText("*Not a valid email address");
                return false;
            default:
                emailError.setText("*Not a valid email address");
                emailField.setStyle("-fx-border-color: red;");
                return false;
        }
    }
    
    
    /**
     * Checks whether the birth date format is correct
     * Shows error message and red borders if incorrect
     * @return true if correct format
    **/
    public static boolean validateDate(String birthDateString, TextField dateField,
            Label birthdateError)
    {
        switch (DateValidator.isValidDateString(birthDateString))
        {
            case VALID:
                dateField.setStyle(null);
                birthdateError.setText(null);
                return true;
            case OUT_OF_RANGE:
                dateField.setStyle("-fx-border-color: red;");
                birthdateError.setText("*This is not a valid birth date");
                return false;
            case PATTERN_MISMATCH:
                dateField.setStyle("-fx-border-color: red;");
                birthdateError.setText("*Format must be dd/MM/yyyy e.g 12/03/1990");
                return false;
            default:
                birthdateError.setText("*Not a valid birth date");
                dateField.setStyle("-fx-border-color: red;");
                return false;
        }
    }

    
    /**
     * Checks whether the name is valid
     * @param name the name
     * @param nameTextField the text field
     * @param error the error label
     * @param nameType the name type
     * @return If the name is valid
     */
    public static boolean validateName(String name, TextField nameTextField,
            Label error, String nameType) 
    {
        switch (NameValidator.validateName(name))
        {
            case VALID:
                nameTextField.setStyle(null);
                error.setText(null);
                return true;
            case INVALID:
                error.setText("*Enter a " + nameType);
                nameTextField.setStyle("-fx-border-color: red;");
                return false;
            default:
                error.setText("*Not a valid " + nameType);
                nameTextField.setStyle("-fx-border-color: red;");
                return false;
        }
    }
    
    
    /**
     * Checks whether a given short name is valid (unique and not null/empty)
     * @param shortName the short name
     * @param shortNameError the error label
     * @param shortNameField the text field
     * @return If the short name is valid
     */
    public static boolean validateShortName(String shortName, Label shortNameError,
            TextField shortNameField) 
    {
        switch (ShortNameValidator.validateShortName(shortName))
        {
            case VALID:
                shortNameError.setText(null);
                shortNameField.setStyle(null);   
                return true;
            case NON_UNIQUE:
                shortNameError.setText("*Short Name taken");
                shortNameField.setStyle("-fx-border-color: red;");
                return false;
            case INVALID:
                shortNameError.setText("*Enter a short name");
                shortNameField.setStyle("-fx-border-color: red;");
                return false;
            default:
                shortNameError.setText("*Not a valid short name");
                shortNameField.setStyle("-fx-border-color: red;");
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