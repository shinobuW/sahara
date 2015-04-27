/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.control.CustomDateField;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.CustomTextField;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.listdisplay.TreeViewWithItems;
import seng302.group2.util.undoredo.UndoRedoAction;
import seng302.group2.util.undoredo.UndoRedoPerformer;
import seng302.group2.util.undoredo.UndoableItem;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.skills.Skill;

import java.util.ArrayList;
import java.util.Date;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.Global.currentWorkspace;
import static seng302.group2.Global.selectedTreeItem;
import static seng302.group2.scenes.MainScene.informationGrid;
import static seng302.group2.scenes.MainScene.treeView;
import static seng302.group2.scenes.dialog.CreatePersonDialog.stringToDate;
import static seng302.group2.util.validation.DateValidator.validateBirthDateField;
import static seng302.group2.util.validation.NameValidator.validateName;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * A class for displaying the person edit scene.
 * @author swi67
 */
public class PersonEditScene
{
    /**
     * Gets the Person Edit information scene.
     * @return The Person Edit information scene
     */
    public static GridPane getPersonEditScene(Person currentPerson)
    {
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
        
        Button btnAdd = new Button("<-");
        Button btnDelete = new Button("->");
        
        VBox skillsButtons = new VBox();
        skillsButtons.getChildren().add(btnAdd);
        skillsButtons.getChildren().add(btnDelete);
        skillsButtons.setAlignment(Pos.CENTER);
        
        
        ListView personSkillsBox = new ListView(currentPerson.getSkills());
        personSkillsBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        
        ObservableList<Skill> dialogSkills = observableArrayList();
        for (TreeViewItem projectSkill : currentWorkspace.getSkills())
        {
            if (!currentPerson.getSkills().contains(projectSkill))
            {
                dialogSkills.add((Skill)projectSkill);
            }
        }
                
        ListView skillsBox = new ListView(dialogSkills);
        skillsBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
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

        customBirthDate.setText(currentPerson.getDateString());

        descriptionTextArea.setText(currentPerson.getDescription());
        
        informationGrid.add(shortNameCustomField, 0, 0);
        informationGrid.add(firstNameCustomField, 0, 1);
        informationGrid.add(lastNameCustomField, 0, 2);
        informationGrid.add(emailTextField, 0, 3);
        informationGrid.add(customBirthDate, 0, 4);
        informationGrid.add(descriptionTextArea, 0, 5);
        informationGrid.add(new Label("Skills: "), 0, 6);
        informationGrid.add(personSkillsBox, 0, 7);
        
        informationGrid.add(skillsButtons,1,7);
        informationGrid.add(btnSave, 1, 8);
        
        informationGrid.add(skillsBox, 2, 7);
        informationGrid.add(btnCancel, 2, 8);
        
        btnAdd.setOnAction((event) ->
            {
                ObservableList<Skill> selectedSkills =
                        skillsBox.getSelectionModel().getSelectedItems();
                for (Skill item : selectedSkills)
                {
                    currentPerson.addSkill(item);
                }

                dialogSkills.clear();
                for (TreeViewItem projectSkill : currentWorkspace.getSkills())
                {
                    if (!currentPerson.getSkills().contains((Skill) projectSkill))
                    {
                        dialogSkills.add((Skill) projectSkill);
                    }
                }
            });
        
        btnDelete.setOnAction((event) ->
            {
                ObservableList<Skill> selectedSkills = 
                        personSkillsBox.getSelectionModel().getSelectedItems();
                for (int i = selectedSkills.size() - 1; i >= 0 ; i--)
                {
                    currentPerson.removeSkill(selectedSkills.get(i));
                }
                
                dialogSkills.clear();
                for (TreeViewItem projectSkill : currentWorkspace.getSkills())
                {
                    if (!currentPerson.getSkills().contains((Skill)projectSkill))
                    {
                        dialogSkills.add((Skill)projectSkill);
                    }
                }
            });        
        
        btnCancel.setOnAction((event) ->
            {
                App.content.getChildren().remove(informationGrid);
                PersonScene.getPersonScene(currentPerson);
                App.content.getChildren().add(informationGrid);
            });
        
        btnSave.setOnAction((event) ->
            {
                boolean correctDate = validateBirthDateField(customBirthDate);
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
                
                if (correctDate && correctShortName && correctFirstName && correctLastName)
                {
                    //set Person proprties
                    Date birthDate;
                    if (customBirthDate.getText().isEmpty())
                    {
                        birthDate = null;
                    }
                    else
                    {
                        birthDate = stringToDate(customBirthDate.getText());
                    }
                    
                    ArrayList<UndoableItem> undoActions = new ArrayList<>();
                    
                    if (firstNameCustomField.getText() != currentPerson.getFirstName())
                    {
                        undoActions.add(new UndoableItem(
                                currentPerson,
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PERSON_FIRSTNAME, 
                                        currentPerson.getFirstName()),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PERSON_FIRSTNAME, 
                                        firstNameCustomField.getText())));
                    }
                    
                    if (lastNameCustomField.getText() != currentPerson.getLastName())
                    {
                        undoActions.add(new UndoableItem(
                                currentPerson,
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PERSON_LASTNAME, 
                                        currentPerson.getLastName()),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PERSON_LASTNAME, 
                                        lastNameCustomField.getText())));
                    }
                    
                    if (shortNameCustomField.getText() != currentPerson.getShortName())
                    {
                        undoActions.add(new UndoableItem(
                                currentPerson,
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PERSON_SHORTNAME, 
                                        currentPerson.getShortName()),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PERSON_SHORTNAME, 
                                        shortNameCustomField.getText())));
                    }
                    
                    if (descriptionTextArea.getText() != currentPerson.getDescription())
                    {
                        undoActions.add(new UndoableItem(
                                currentPerson,
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PERSON_DESCRIPTION, 
                                        currentPerson.getDescription()),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PERSON_DESCRIPTION, 
                                        descriptionTextArea.getText())));
                    }
                    
                    if (emailTextField.getText() != currentPerson.getEmail())
                    {
                        undoActions.add(new UndoableItem(
                                currentPerson,
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PERSON_EMAIL, 
                                        currentPerson.getEmail()),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PERSON_EMAIL, 
                                        emailTextField.getText())));
                    }                    

                    if (stringToDate(customBirthDate.getText()) != currentPerson.getBirthDate())
                    {
                        undoActions.add(new UndoableItem(
                                currentPerson,
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PERSON_BIRTHDATE, 
                                        currentPerson.getBirthDate()),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PERSON_BIRTHDATE, 
                                        stringToDate(customBirthDate.getText()))));
                    }  
                    
                    Global.undoRedoMan.add(new UndoableItem(
                        currentPerson,
                        new UndoRedoAction(
                                UndoRedoPerformer.UndoRedoProperty.PERSON_EDIT,
                                undoActions), 
                        new UndoRedoAction(
                                UndoRedoPerformer.UndoRedoProperty.PERSON_EDIT, 
                                undoActions)
                        ));
                    
                    currentPerson.setFirstName(firstNameCustomField.getText());
                    currentPerson.setShortName(shortNameCustomField.getText());
                    currentPerson.setLastName(lastNameCustomField.getText());
                    currentPerson.setDescription(descriptionTextArea.getText());
                    currentPerson.setEmail(emailTextField.getText());
                    currentPerson.setBirthDate(birthDate);


                    //String birthdate = birthDateField.getText();
                    App.content.getChildren().remove(treeView);
                    App.content.getChildren().remove(informationGrid);
                    PersonScene.getPersonScene(currentPerson);
                    MainScene.treeView = new TreeViewWithItems(new TreeItem());
                    ObservableList<TreeViewItem> children = observableArrayList();
                    children.add(Global.currentWorkspace);

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
