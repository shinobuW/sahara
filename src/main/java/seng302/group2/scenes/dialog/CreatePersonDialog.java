/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.dialog;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomDateField;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.CustomTextField;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.workspace.person.Person;

import java.time.LocalDate;
import java.util.Map;

import static seng302.group2.util.validation.DateValidator.stringToDate;
import static seng302.group2.util.validation.DateValidator.validateBirthDateField;
import static seng302.group2.util.validation.NameValidator.validateName;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;


/**
 * Class to create a pop up dialog for creating a person.
 *
 * @author swi67
 */
public class CreatePersonDialog extends Dialog<Map<String, String>> {

    Boolean correctDate = Boolean.TRUE;
    Boolean correctShortName = Boolean.FALSE;
    Boolean correctFirstName = Boolean.FALSE;
    Boolean correctLastName = Boolean.FALSE;
    /**
     * Displays the Dialog box for creating a person.
     */
    public CreatePersonDialog() {
        correctDate = Boolean.TRUE;
        correctShortName = Boolean.FALSE;
        correctFirstName = Boolean.FALSE;
        correctLastName = Boolean.FALSE;

        this.setTitle("New Person");
        this.getDialogPane().setStyle(" -fx-max-width:600px; -fx-max-height: 400px; -fx-pref-width: 600px; "
                + "-fx-pref-height: 400px;");

        ButtonType btnTypeCreate = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(btnTypeCreate, ButtonType.CANCEL);

        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);

        // Add elements to grid
        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        RequiredField firstNameCustomField = new RequiredField("First Name:");
        RequiredField lastNameCustomField = new RequiredField("Last Name:");
        CustomTextField emailTextField = new CustomTextField("Email:");
        CustomDateField customBirthDate = new CustomDateField("Birth Date:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Description:");

        grid.getChildren().addAll(shortNameCustomField, firstNameCustomField, lastNameCustomField,
                emailTextField, customBirthDate, descriptionTextArea);

        //Add grid of controls to dialog
        this.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> shortNameCustomField.getTextField().requestFocus());

        Node createButton = this.getDialogPane().lookupButton(btnTypeCreate);
        createButton.setDisable(true);

        //Validation
        shortNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctShortName = validateShortName(shortNameCustomField, null);
                createButton.setDisable(!(correctShortName && correctFirstName && correctLastName && correctDate));
            });

        firstNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newvalue) -> {
                correctFirstName = validateName(firstNameCustomField);
                createButton.setDisable(!(correctShortName && correctFirstName && correctLastName && correctDate));
            });

        lastNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctLastName = validateName(lastNameCustomField);
                createButton.setDisable(!(correctShortName && correctFirstName && correctLastName && correctDate));
            });

        customBirthDate.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctDate = validateBirthDateField(customBirthDate);
                createButton.setDisable(!(correctShortName && correctFirstName && correctLastName && correctDate));
            });


        this.setResultConverter(b -> {
                if (b == btnTypeCreate) {
                    //get user input
                    String firstName = firstNameCustomField.getText();
                    String lastName = lastNameCustomField.getText();
                    String shortName = shortNameCustomField.getText();
                    String email = emailTextField.getText();
                    String description = descriptionTextArea.getText();

                    String birthdateString = customBirthDate.getText();

                    LocalDate birthDate;
                    if (birthdateString.isEmpty()) {
                        birthDate = null;
                    }
                    else {
                        birthDate = stringToDate(birthdateString);
                    }

                    Person person = new Person(shortName, firstName, lastName, email, description,
                            birthDate);
                    Global.currentWorkspace.add(person);
                    App.mainPane.selectItem(person);
                    this.close();

                }
                return null;
            });

        this.setResizable(false);
        this.show();
    }
}
