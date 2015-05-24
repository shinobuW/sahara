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
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.control.CustomDateField;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.CustomTextField;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.team.Team;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static seng302.group2.util.validation.DateValidator.stringToDate;
import static seng302.group2.util.validation.DateValidator.validateBirthDateField;
import static seng302.group2.util.validation.NameValidator.validateName;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * Class to create a pop up dialog for creating a person.
 *
 * @author swi67
 */
@SuppressWarnings("deprecation")
public class CreatePersonDialog
{
    /**
     * Displays the Dialog box for creating a person.
     */
    public static void show()
    {
        // Initialise Dialog and GridPane
        Dialog dialog = new Dialog(null, "New Person");
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
        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        RequiredField firstNameCustomField = new RequiredField("First Name:");
        RequiredField lastNameCustomField = new RequiredField("Last Name:");
        CustomTextField emailTextField = new CustomTextField("Email:");
        CustomDateField customBirthDate = new CustomDateField("Birth Date:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Description:");

        grid.getChildren().addAll(shortNameCustomField, firstNameCustomField, lastNameCustomField,
                emailTextField, customBirthDate, descriptionTextArea, buttons);

        // Create button event
        btnCreate.setOnAction((event) ->
            {
                boolean correctDate = validateBirthDateField(customBirthDate);
                boolean correctShortName = validateShortName(shortNameCustomField, null);
                boolean correctFirstName = validateName(firstNameCustomField);
                boolean correctLastName = validateName(lastNameCustomField);

                if (correctDate && correctShortName && correctFirstName && correctLastName)
                {
                    //get user input
                    String firstName = firstNameCustomField.getText();
                    String lastName = lastNameCustomField.getText();
                    String shortName = shortNameCustomField.getText();
                    String email = emailTextField.getText();
                    String description = descriptionTextArea.getText();

                    String birthdateString = customBirthDate.getText();

                    LocalDate birthDate;
                    if (birthdateString.isEmpty())
                    {
                        birthDate = null;
                    }
                    else
                    {
                        birthDate = stringToDate(birthdateString);
                    }

                    Person person = new Person(shortName, firstName, lastName, email, description,
                            birthDate);
                    Global.currentWorkspace.add(person);
                    MainScene.treeView.selectItem(person);
                    dialog.hide();
                }
                else
                {
                    btnCreate.disableProperty();
                }
            });

        // Cancel button event
        btnCancel.setOnAction((event) ->
                dialog.hide());

        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(grid);
        dialog.show();
    }
}
