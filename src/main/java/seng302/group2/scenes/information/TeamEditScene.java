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
import org.controlsfx.dialog.Dialog;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.listdisplay.TreeViewWithItems;
import seng302.group2.util.undoredo.UndoRedoAction;
import seng302.group2.util.undoredo.UndoRedoPerformer;
import seng302.group2.util.undoredo.UndoableItem;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.team.Team;

import java.util.ArrayList;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.Global.selectedTreeItem;
import static seng302.group2.scenes.MainScene.informationGrid;
import static seng302.group2.scenes.MainScene.treeView;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * A class for displaying the team edit scene.
 * @author crw73
 */
public class TeamEditScene
{

    /**
     * Gets the Team Edit information scene.
     * @param currentTeam The team to show the information of
     * @return The Team Edit information scene
     */
    public static GridPane getTeamEditScene(Team currentTeam)
    {

        //Team currentTeam = (Team) selectedTreeItem.getValue();
        informationGrid = new GridPane();
        informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);
        informationGrid.setPadding(new Insets(25,25,25,25));

        Button btnCancel = new Button("Cancel");
        Button btnSave = new Button("Save");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(btnSave, btnCancel);

        RequiredField shortNameCustomField = new RequiredField("Short Name");
        CustomTextArea descriptionTextArea = new CustomTextArea("Team Description", 300);
        
        shortNameCustomField.setText(currentTeam.getShortName());
        descriptionTextArea.setText(currentTeam.getDescription());
        
        Button btnAdd = new Button("<-");
        Button btnDelete = new Button("->");
        
        VBox peopleButtons = new VBox();
        peopleButtons.getChildren().add(btnAdd);
        peopleButtons.getChildren().add(btnDelete);
        peopleButtons.setAlignment(Pos.CENTER);

        
        Team tempTeam = new Team();
        for (Person person : currentTeam.getPeople()) 
        {
            System.out.println(person);
            tempTeam.add(person, false);
        }
        
        ListView teamsPeopleBox = new ListView(tempTeam.getPeople());
        if (tempTeam.isUnassignedTeam())
        {
            for (TreeViewItem person : Global.currentWorkspace.getPeople())
            {
                Person castedPerson = (Person) person;
                if (castedPerson.getTeam() == null)
                {
                    teamsPeopleBox.getItems().add((Person) person);
                }
            }
        }
        
        teamsPeopleBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        
        ObservableList<Person> dialogPeople = observableArrayList();
        ObservableList<Person> dialogPeopleCopy = observableArrayList();
        
        for (TreeViewItem projectPerson : Global.currentWorkspace.getPeople())
        {
            if (!tempTeam.getPeople().contains(projectPerson))
            {
                dialogPeople.add((Person) projectPerson);
                dialogPeopleCopy.add((Person) projectPerson);
            }
        }
        
        if (tempTeam.isUnassignedTeam())
        {
            for (TreeViewItem person : Global.currentWorkspace.getPeople())
            {
                Person castedPerson = (Person) person;
                if (castedPerson.getTeamName() == null)
                {
                    teamsPeopleBox.getItems().remove((Person) person);
                }
            }
        }
                
        ListView membersBox = new ListView(dialogPeople);
        membersBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        informationGrid.add(shortNameCustomField, 0, 0);
        informationGrid.add(descriptionTextArea, 0, 1);
        informationGrid.add(new Label("Team Members: "), 0, 2);
        informationGrid.add(teamsPeopleBox, 0, 3);

        informationGrid.add(peopleButtons, 1, 3);
        informationGrid.add(btnSave, 1, 4);

        informationGrid.add(membersBox, 2, 3);
        informationGrid.add(btnCancel, 2, 4);


        
        btnAdd.setOnAction((event) ->
            {
                ObservableList<Person> selectedPeople = 
                        membersBox.getSelectionModel().getSelectedItems();
                for (Person item : selectedPeople)
                {
                    if (item.getTeam() == (Team) Global.currentWorkspace.getTeams().get(0) 
                            || item.getTeam() == null) 
                    {
                        tempTeam.add(item, false);
                    }
                    else 
                    {
                        personCheckDialog(item, tempTeam);
                    }
                }
                
                dialogPeople.clear();
                for (TreeViewItem projectPeople : Global.currentWorkspace.getPeople())
                {
                    if (!tempTeam.getPeople().contains((Person)projectPeople))
                    {
                        dialogPeople.add((Person) projectPeople);
                    }
                }
            });
        
        btnDelete.setOnAction((event) ->
            {
                ObservableList<Person> selectedPeople = 
                        teamsPeopleBox.getSelectionModel().getSelectedItems();
                for (int i = selectedPeople.size() - 1; i >= 0 ; i--)
                {
                    tempTeam.remove(selectedPeople.get(i), false);
                }
                
                dialogPeople.clear();
                for (TreeViewItem projectPeople : Global.currentWorkspace.getPeople())
                {
                    if (!tempTeam.getPeople().contains((Person)projectPeople))
                    {
                        dialogPeople.add((Person) projectPeople);
                    }
                }
            });        
        
        btnCancel.setOnAction((event) ->
            {
                App.content.getChildren().remove(informationGrid);
                TeamScene.getTeamScene((Team) Global.selectedTreeItem.getValue());
                App.content.getChildren().add(informationGrid);

            });

        btnSave.setOnAction((event) ->
            {
                boolean correctShortName;
                
                if (shortNameCustomField.getText().equals(currentTeam.getShortName()))
                {
                    correctShortName = true;
                }
                else
                {
                    correctShortName = validateShortName(shortNameCustomField);
                }
                
                if (correctShortName)
                {
                    
                    ArrayList<UndoableItem> undoActions = new ArrayList<>();
                    
                    if (shortNameCustomField.getText() != currentTeam.getShortName())
                    {
                        undoActions.add(new UndoableItem(
                                currentTeam,
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.TEAM_SHORTNAME, 
                                        currentTeam.getShortName()),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.TEAM_SHORTNAME, 
                                        shortNameCustomField.getText())));
                    }
                    
                    currentTeam.setShortName(shortNameCustomField.getText());
                    
                    if (descriptionTextArea.getText() != currentTeam.getDescription())
                    {
                        undoActions.add(new UndoableItem(
                                currentTeam,
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.TEAM_DESCRIPTION, 
                                        currentTeam.getDescription()),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.TEAM_DESCRIPTION, 
                                        descriptionTextArea.getText())));
                    }
                    
                    currentTeam.setDescription(descriptionTextArea.getText());
                    
                    for (Person person : tempTeam.getPeople())
                    {
                        System.out.println("Adding " + person);
                        if (!currentTeam.getPeople().contains(person))
                        {
                            undoActions.add(new UndoableItem(
                                    person,
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.PERSON_ADD_TEAM, 
                                            person.getTeam()),
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.PERSON_ADD_TEAM, 
                                            currentTeam)));

                            undoActions.add(new UndoableItem(
                                    person,
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.PERSON_TEAM, 
                                            person.getTeam()),
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.PERSON_TEAM, 
                                            currentTeam)));



                            person.getTeam().remove(person, false);
                            person.setTeam(currentTeam);
                            currentTeam.add(person, false);
                        }
                    }
                    
                    for (Person person : dialogPeople)
                    {
                        System.out.println("Deleting " + person.getTeam());
                        if (!dialogPeopleCopy.contains(person))
                        {
                            undoActions.add(new UndoableItem(
                                    person,
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.PERSON_DEL_TEAM, 
                                            person.getTeam()),
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.PERSON_DEL_TEAM, 
                                            person.getTeam())));

                            undoActions.add(new UndoableItem(
                                    person,
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.PERSON_TEAM, 
                                            person.getTeam()),
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.PERSON_TEAM, 
                                            (Team)Global.currentWorkspace.getTeams().get(0))));
                                         
                            person.getTeam().remove(person, false);
                            person.setTeam((Team)Global.currentWorkspace.getTeams().get(0));
                            ((Team)Global.currentWorkspace.getTeams().get(0))
                                    .add(person, false);
                        }
                    }
                    
                    Global.undoRedoMan.add(new UndoableItem(
                        currentTeam,
                        new UndoRedoAction(
                                UndoRedoPerformer.UndoRedoProperty.TEAM_EDIT,
                                undoActions), 
                        new UndoRedoAction(
                                UndoRedoPerformer.UndoRedoProperty.TEAM_EDIT, 
                                undoActions)
                        ));
                    
                    App.content.getChildren().remove(treeView);
                    App.content.getChildren().remove(informationGrid);
                    TeamScene.getTeamScene((Team) Global.selectedTreeItem.getValue());
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
    
    private static void personCheckDialog(Person person, Team tempTeam) 
    {
        
        Dialog dialog = new Dialog(null, "Already Assigned to a Team");
        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);
               
        Button btnYes = new Button("Yes");
        Button btnNo = new Button("No");
        
        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(btnYes, btnNo);
        
        grid.getChildren().add(new Label("Are you sure you want to change teams?"));
        grid.getChildren().add(buttons);
        
        btnYes.setOnAction((event) ->
            {
                tempTeam.add(person, false);
                dialog.hide();
            });
        
        btnNo.setOnAction((event) ->
            {
                dialog.hide();
            });
        
        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(grid);
        dialog.show();
    }
}
