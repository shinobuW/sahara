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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.controlsfx.dialog.Dialog;
import seng302.group2.App;
import seng302.group2.project.team.person.Person;
import seng302.group2.scenes.listdisplay.TreeViewItem;

/**
 *
 * @author swi67
 */
public class CreatePersonDialog
{
    public static void show()
    {
        //Initialise Dialog and GridPane
        Dialog dialog = new Dialog(null, "Create New Person");
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);
        
        //Initialise Input fields
        TextField shortNameField = new TextField();
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField emailField = new TextField();
        TextArea descriptionField = new TextArea();
        TextField birthdateField = new TextField();
        birthdateField.setPromptText("dd/mm/yyyy");
        Button btnCreate = new Button("Create");
        
        Label shortNameError = new Label();
        Label firstNameError = new Label();
        Label lastNameError = new Label();
        Label dateError = new Label();
        Label emailError = new Label();
        
        //Add elements to grid
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
        
        grid.add(new Label("BirthDate"), 0, 4);
        grid.add(birthdateField, 1, 4);
        grid.add(dateError, 2, 4, 18, 1);
        
        grid.add(new Label("Description"), 0, 5);
        grid.add(descriptionField, 1, 5);
        grid.add(btnCreate, 1, 6);
        
        Date birthDate = new Date();
        
        //"Create" button event
        btnCreate.setOnAction((event) ->
            {
                //Get user input
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String shortName = shortNameField.getText();
                String email = emailField.getText();
                String description = descriptionField.getText();
                String birthdateString = birthdateField.getText();
                
                //Validation & Error messages
                boolean dateCorrect = validateDate(birthdateString, birthDate, birthdateField,
                        dateError);
                boolean emailCorrect = validateEmail(email, emailField,  emailError);
                boolean shortNameCorrect = validateName(shortName, shortNameField,shortNameError,
                        "Short Name"); 
                boolean firstNameCorrect = validateName(firstName, firstNameField, firstNameError,
                        "first name");
                boolean lastNameCorrect = validateName(lastName, lastNameField, lastNameError,
                        "last name");
                boolean isShortNameUnique = validateUniqueShortName(shortName, shortNameError,
                        shortNameField);

                //Create new person if all fields are correct
                if (dateCorrect && emailCorrect && shortNameCorrect && firstNameCorrect 
                        && lastNameCorrect && isShortNameUnique) 
                {
                    Person person = new Person(shortName, firstName, lastName, email, description,
                        birthDate);
                    System.out.println(firstName + "Testing");
                    App.currentProject.addPerson(person);
                    dialog.hide();
                }
                else 
                {
                    btnCreate.disableProperty();
                }
            });
        
        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(grid);
        dialog.show();        
    }
    
    private static boolean validateEmail(String email, TextField emailField, Label emailError)
    {
        //Checks whether the email is correct format
        //Error message shown and TextField border changed to red if incorrect
        //Returns true if correct format
        String emailPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\."
                + "[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(emailPattern);
        java.util.regex.Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) 
        {
            emailField.setStyle("-fx-border-color: red;");
            emailError.setText("*Incorrect Format");
        }
        else 
        {
            emailError.setText(null);
            emailField.setStyle(null);
        }
        return matcher.matches();
    }
    
    private static boolean validateDate(String birthDateString, Date birthdate, TextField dateField,
            Label birthdateError)
    {
        //Checks whether the birth date format is correct
        //Error message shown and TextField border changed to red if incorrect
        //Returns true if correct format
        boolean correctFormat = false;

        try
        {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            df.setLenient(false);
            df.parse(birthDateString);
            birthdate = df.parse(birthDateString);
            correctFormat = true;
        }
        catch (ParseException e)
        {
            System.out.println("Error parsing date");
        }


        if (!correctFormat) 
        {
            dateField.setStyle("-fx-border-color: red;");
            birthdateError.setText("*Format must be dd/MM/yyyy e.g 12/03/1990");
        }
        else 
        {
            System.out.println(birthdate.getMonth());
            dateField.setStyle(null);
            birthdateError.setText(null);
        }
        return correctFormat;
    }

    
    public static boolean validateName(String name, TextField nameTextField,
            Label error, String nameType) 
    {
        //Checks whether the specified field is empty
        //Shows error message and changes the field border colour to red
        boolean correctName;
        if (name.isEmpty()) 
        {
            error.setText("*Enter a " + nameType);
            nameTextField.setStyle("-fx-border-color: red;");
            name.getClass().getResource("text-field-red-border.css");
            correctName = false;
        }
        else 
        {
            nameTextField.setStyle(null);
            error.setText(null);
            correctName = true;
        }
        return correctName;
    }
    
    public static boolean validateUniqueShortName(String shortName, Label shortNameError,
            TextField shortNameField) 
    {
        boolean isUnique = true;
 
        String newShortName = shortName;
        int i = 0;
        for (TreeViewItem person : App.currentProject.getPeople())
        {
            if (person.toString().equals(newShortName))
            {
                shortNameError.setText("*Short Name taken");
                shortNameField.setStyle("-fx-border-color: red;");
                isUnique = false;
            }
            else 
            {
                shortNameError.setText(null);
                shortNameField.setStyle(null);
                isUnique = true;
            }
        }
        return isUnique;
    }

        

}