/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.dialog;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
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
@SuppressWarnings("deprecation")
public class CreatePersonDialog {
    /**
     * Displays the Dialog box for creating a person.
     */
    public static void show() {
        // Initialise Dialog and GridPane
        javafx.scene.control.Dialog<Map<String, String>> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle("New Person");
        dialog.getDialogPane().setStyle(" -fx-max-width:600px; -fx-max-height: 400px; -fx-pref-width: 600px; "
                + "-fx-pref-height: 350;");


        ButtonType btnCreate = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnCreate, ButtonType.CANCEL);


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
        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> shortNameCustomField.getTextField().requestFocus());


        dialog.setResultConverter(b -> {
                if (b == btnCreate) {
                    boolean correctDate = validateBirthDateField(customBirthDate);
                    boolean correctShortName = validateShortName(shortNameCustomField, null);
                    boolean correctFirstName = validateName(firstNameCustomField);
                    boolean correctLastName = validateName(lastNameCustomField);

                    if (correctDate && correctShortName && correctFirstName && correctLastName) {
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
                        dialog.close();
                    }

                }
                return null;
            });

        //Optional<Map<String, String>> result = dialog.showAndWait();


        // Create button event
//        btnCreate.setOnAction((event) -> {
//                boolean correctDate = validateBirthDateField(customBirthDate);
//                boolean correctShortName = validateShortName(shortNameCustomField, null);
//                boolean correctFirstName = validateName(firstNameCustomField);
//                boolean correctLastName = validateName(lastNameCustomField);
//
//                if (correctDate && correctShortName && correctFirstName && correctLastName) {
//                    //get user input
//                    String firstName = firstNameCustomField.getText();
//                    String lastName = lastNameCustomField.getText();
//                    String shortName = shortNameCustomField.getText();
//                    String email = emailTextField.getText();
//                    String description = descriptionTextArea.getText();
//
//                    String birthdateString = customBirthDate.getText();
//
//                    LocalDate birthDate;
//                    if (birthdateString.isEmpty()) {
//                        birthDate = null;
//                    }
//                    else {
//                        birthDate = stringToDate(birthdateString);
//                    }
//
//                    Person person = new Person(shortName, firstName, lastName, email, description,
//                            birthDate);
//                    Global.currentWorkspace.add(person);
//                    App.mainP dialog.close();
//                    dialog.hide();
//                }
//                else {
//                    btnCreate.disableProperty();
//                }
//            });
//
//        // Cancel button event
//        btnCancel.setOnAction((event) ->
//                dialog.hide());

        dialog.setResizable(false);
        dialog.show();
    }
}
