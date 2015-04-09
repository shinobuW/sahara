/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information;

import java.text.SimpleDateFormat;
import java.util.Date;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import seng302.group2.App;
import seng302.group2.Global;
import static seng302.group2.Global.selectedTreeItem;
import seng302.group2.project.team.person.Person;
import seng302.group2.scenes.MainScene;
import static seng302.group2.scenes.MainScene.informationGrid;
import static seng302.group2.scenes.MainScene.treeView;
import seng302.group2.scenes.control.CustomDateField;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.CustomTextField;
import seng302.group2.scenes.control.RequiredField;
import static seng302.group2.scenes.dialog.CreatePersonDialog.stringToDate;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.listdisplay.TreeViewWithItems;
import static seng302.group2.util.validation.DateValidator.validateBirthDate;
import static seng302.group2.util.validation.NameValidator.validateName;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

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
        informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);
        informationGrid.setPadding(new Insets(25,25,25,25));
    
        Button btnSave = new Button("Save");
        Button btnCancel = new Button("Cancel");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(btnSave, btnCancel);
        
        RequiredField shortNameCustomField = new RequiredField("Short Name");
        RequiredField firstNameCustomField = new RequiredField("First Name");
        RequiredField lastNameCustomField = new RequiredField("Last Name");
        CustomTextField emailTextField = new CustomTextField("Email");
        CustomDateField customBirthDate = new CustomDateField("Birth Date");
        CustomTextArea descriptionTextArea = new CustomTextArea("Description", 300);
        
        firstNameCustomField.setText(currentPerson.getFirstName());
        lastNameCustomField.setText(currentPerson.getLastName());
        shortNameCustomField.setText(currentPerson.getShortName());
        emailTextField.setText(currentPerson.getEmail());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");     
        customBirthDate.setText(dateFormat.format(currentPerson.getBirthDate()));
        descriptionTextArea.setText(currentPerson.getDescription());;
        
        informationGrid.add(shortNameCustomField, 0, 0);
        informationGrid.add(firstNameCustomField, 0, 1);
        informationGrid.add(lastNameCustomField, 0, 2);
        informationGrid.add(emailTextField, 0, 3);
        informationGrid.add(customBirthDate, 0, 4);
        informationGrid.add(descriptionTextArea, 0, 5);
        informationGrid.add(buttons, 0, 6);

        btnCancel.setOnAction((event) ->
            {
                App.content.getChildren().remove(informationGrid);
                PersonScene.getPersonScene();
                App.content.getChildren().add(informationGrid);
            });
        
        btnSave.setOnAction((event) ->
            {
                boolean correctDate = validateBirthDate(customBirthDate);
                boolean correctFirstName = validateName(firstNameCustomField);
                boolean correctLastName = validateName(lastNameCustomField);
                boolean correctShortName;
                
                if (shortNameCustomField.getText().equals(currentPerson.getShortName()))
                {
                    correctShortName = true;
                }
                else
                {
                    correctShortName = validateShortName(shortNameCustomField);
                }
                
                if (correctShortName && correctFirstName && correctLastName)
                {
                    //set Person proprties
                    final Date birthDate = stringToDate(customBirthDate.getText());

                    currentPerson.setFirstName(firstNameCustomField.getText());
                    currentPerson.setShortName(shortNameCustomField.getText());
                    currentPerson.setLastName(lastNameCustomField.getText());
                    currentPerson.setDescription(descriptionTextArea.getText());
                    currentPerson.setEmail(emailTextField.getText());
                    currentPerson.setBirthDate(birthDate);


                    //String birthdate = birthDateField.getText();
                    App.content.getChildren().remove(treeView);
                    App.content.getChildren().remove(informationGrid);
                    PersonScene.getPersonScene();
                    MainScene.treeView = new TreeViewWithItems(new TreeItem());
                    ObservableList<TreeViewItem> children = observableArrayList();
                    children.add(Global.currentProject);

                    MainScene.treeView.setItems(children);
                    MainScene.treeView.setShowRoot(false);

                    App.content.getChildren().add(treeView);
                    App.content.getChildren().add(informationGrid);
                    MainScene.treeView.getSelectionModel().select(selectedTreeItem);
                }
                else
                {
                    event.consume();
                }
            });
        
        return informationGrid;
    }
}
