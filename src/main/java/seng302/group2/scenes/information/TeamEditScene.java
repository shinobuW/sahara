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
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.listdisplay.TreeViewWithItems;
import seng302.group2.util.undoredo.UndoRedoAction;
import seng302.group2.util.undoredo.UndoRedoPerformer;
import seng302.group2.util.undoredo.UndoableItem;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

import java.util.ArrayList;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.Global.selectedTreeItem;
import static seng302.group2.scenes.MainScene.informationGrid;
import static seng302.group2.scenes.MainScene.treeView;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;
import seng302.group2.workspace.role.Role;

/**
 * A class for displaying the team edit scene.
 * @author crw73
 */
public class TeamEditScene
{
    private static ListView teamsPeopleBox = new ListView();
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

        HBox devBtns = new HBox();
        Button mkeDev = new Button("Make Developer");
        Button rmvDev = new Button("Unmake Developer");
        devBtns.getChildren().add(mkeDev);
        devBtns.getChildren().add(rmvDev);

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(btnSave, btnCancel);

        RequiredField shortNameCustomField = new RequiredField("Short Name: ");
        CustomTextArea descriptionTextArea = new CustomTextArea("Team Description: ", 300);
        CustomComboBox productOwnerBox = new CustomComboBox("Product Owner: ", false);
        CustomComboBox scrumMasterBox = new CustomComboBox("Scrum Master: ", false);

        productOwnerBox.addToComboBox("");
        scrumMasterBox.addToComboBox("");

        Person currentSM = currentTeam.getScrumMaster();
        Person currentPO = currentTeam.getProductOwner();

        for (Person teamMember : currentTeam.getPeople())
        {
            for (Skill memSkill : teamMember.getSkills())
            {
                if (memSkill.getShortName().equals("Product Owner"))
                {
                    productOwnerBox.addToComboBox(teamMember.toString());
                    break;
                }
            }
        }
        if (currentPO == null)
        {
            productOwnerBox.setValue("");
        }
        else
        {
            productOwnerBox.setValue(currentPO.toString());
        }

        for (Person teamMember : currentTeam.getPeople())
        {
            for (Skill memSkill : teamMember.getSkills())
            {
                if (memSkill.getShortName().equals("Scrum Master"))
                {
                    scrumMasterBox.addToComboBox(teamMember.toString());
                    break;
                }
            }
        }
        if (currentSM == null)
        {
            scrumMasterBox.setValue("");
        }
        else
        {
            scrumMasterBox.setValue(currentSM.toString());
        }


        shortNameCustomField.setText(currentTeam.getShortName());
        descriptionTextArea.setText(currentTeam.getDescription());
        
        Button btnAdd = new Button("<-");
        Button btnDelete = new Button("->");

        
        VBox peopleButtons = new VBox();
        peopleButtons.spacingProperty().setValue(10);
        peopleButtons.getChildren().add(btnAdd);
        peopleButtons.getChildren().add(btnDelete);
        peopleButtons.setAlignment(Pos.CENTER);

        //Creates a copy of the Teams people as of when this function is called.
        //CurrentTeam never changes until the save is called.
        ObservableList<Person> tempTeam = observableArrayList();
        ObservableList<String> tempTeamString = observableArrayList();
        for (Person person : currentTeam.getPeople())
        {
            tempTeam.add(person);
            tempTeamString.add(person.getShortName() + " - (" + person.getRole() + ")");
        }
        
        //Creates the Teams box of People
        teamsPeopleBox = new ListView(tempTeamString);
        
        if (currentTeam.isUnassignedTeam())
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
        
        ObservableList<Person> teamPeopleRoles = observableArrayList();
        ObservableList<Role> teamRoles = observableArrayList();
        
        for (TreeViewItem projectPerson : Global.currentWorkspace.getPeople())
        {
            if (!tempTeam.contains(projectPerson))
            {
                dialogPeople.add((Person) projectPerson);
                dialogPeopleCopy.add((Person) projectPerson);
            }
        }
        
        if (currentTeam.isUnassignedTeam())
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
        
        //Creates the Box of people that aren't assigned to the current Team
        ListView membersBox = new ListView(dialogPeople);
        membersBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        informationGrid.add(shortNameCustomField, 0, 0);
        informationGrid.add(descriptionTextArea, 0, 1);
        informationGrid.add(productOwnerBox, 0, 2);
        informationGrid.add(scrumMasterBox, 0, 3);
        informationGrid.add(new Label("Team Members: "), 0, 4);
        informationGrid.add(new Label("Available People: "), 2, 4);

        informationGrid.add(teamsPeopleBox, 0, 5);
        informationGrid.add(peopleButtons, 1, 5);
        informationGrid.add(membersBox, 2, 5);
        informationGrid.add(devBtns, 0, 6);
        informationGrid.add(btnSave, 1, 6);
        informationGrid.add(btnCancel, 2, 6);
        refreshListView(tempTeam, currentTeam);

        btnAdd.setOnAction((event) ->
            {
                ObservableList<Person> selectedPeople = 
                        membersBox.getSelectionModel().getSelectedItems();
                for (Person item : selectedPeople)
                {
                    if (item.getTeam().isUnassignedTeam()
                            || item.getTeam() == null) 
                    {
                        tempTeam.add(item);
                    }
                    else 
                    {
                        personCheckDialog(item, tempTeam, currentTeam);
                    }
                }
                
                dialogPeople.clear();
                for (TreeViewItem projectPeople : Global.currentWorkspace.getPeople())
                {
                    if (!tempTeam.contains(projectPeople))
                    {
                        dialogPeople.add((Person) projectPeople);
                    }
                }
                refreshListView(tempTeam, currentTeam);
            });
        
        btnDelete.setOnAction((event) ->
            {
                ObservableList<Integer> selectedPeople =
                        teamsPeopleBox.getSelectionModel().getSelectedIndices();
                for (int i = selectedPeople.size()-1; i >= 0; i--)
                {
                    tempTeam.remove(i);
                    
                }
                refreshListView(tempTeam, currentTeam);
                dialogPeople.clear();
                for (TreeViewItem projectPeople : Global.currentWorkspace.getPeople())
                {
                    if (!tempTeam.contains(projectPeople))
                    {
                        dialogPeople.add((Person) projectPeople);
                    }
                }
                refreshListView(tempTeam, currentTeam);
            });

        mkeDev.setOnAction((event) ->
            {
                ObservableList<Integer> selectedPeople =
                        teamsPeopleBox.getSelectionModel().getSelectedIndices();
                System.out.println(selectedPeople);
                for (int i : selectedPeople)
                {
                    teamRoles.add(tempTeam.get(i).getRole());
                    teamPeopleRoles.add(tempTeam.get(i));
                    tempTeam.get(i).setRole(Global.currentWorkspace.getRoles().get(2));
                    refreshListView(tempTeam, currentTeam);
                }

            });

        rmvDev.setOnAction((event) ->
            {
                ObservableList<Integer> selectedPeople =
                        teamsPeopleBox.getSelectionModel().getSelectedIndices();

                for (int i : selectedPeople)
                {
                    teamPeopleRoles.remove(tempTeam.get(i));
                    teamRoles.remove(i, i);
                    tempTeam.get(i).setRole(null);
                    refreshListView(tempTeam, currentTeam);

                }
            });
        
        btnCancel.setOnAction((event) ->
            {
                App.content.getChildren().remove(informationGrid);
                for (int i = 0; i < teamPeopleRoles.size(); i++)
                {
                    teamPeopleRoles.get(i).setRole(teamRoles.get(i));
                }
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
                    
                    if (!shortNameCustomField.getText().equals(currentTeam.getShortName()))
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
                    
                    if (!descriptionTextArea.getText().equals(currentTeam.getDescription()))
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
                    
                    for (int i = 0; i < teamRoles.size(); i++)
                    {
                        undoActions.add(new UndoableItem(
                                teamPeopleRoles.get(i),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PERSON_ROLE,
                                        teamRoles.get(i)),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PERSON_ROLE,
                                        teamPeopleRoles.get(i).getRole())));
                    }

                    String stringSM = "";
                    if (currentSM != null)
                    {
                        stringSM = currentSM.toString();
                    }
                    if (!scrumMasterBox.getValue().equals(stringSM))
                    {
                        for (Person selectedPerson : currentTeam.getPeople())
                        {
                            if (selectedPerson.toString().equals(scrumMasterBox.getValue()))
                            {
                                undoActions.add(new UndoableItem(
                                    selectedPerson,
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.PERSON_ROLE,
                                            selectedPerson.getRole()),
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.PERSON_ROLE,
                                            Global.currentWorkspace.getRoles().get(0))));
                                currentTeam.setScrumMaster(selectedPerson);
                                selectedPerson.setRole(Global.currentWorkspace.getRoles().get(0));
                            }
                        }
                    }

                    String stringPO = "";
                    if (currentPO != null)
                    {
                        stringPO = currentPO.toString();
                    }
                    if (!productOwnerBox.getValue().equals(stringPO))
                    {
                        for (Person selectedPerson : currentTeam.getPeople())
                        {
                            if (selectedPerson.toString().equals(productOwnerBox.getValue()))
                            {
                                undoActions.add(new UndoableItem(
                                    selectedPerson,
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.PERSON_ROLE,
                                            selectedPerson.getRole()),
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.PERSON_ROLE,
                                            Global.currentWorkspace.getRoles().get(1))));
                                currentTeam.setProductOwner(selectedPerson);
                                selectedPerson.setRole(Global.currentWorkspace.getRoles().get(1));
                            }
                        }
                    }
                    
                    for (Person person : tempTeam)
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

                    if (undoActions.size() > 0)
                    {
                        Global.undoRedoMan.add(new UndoableItem(
                                currentTeam,
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.TEAM_EDIT,
                                        undoActions),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.TEAM_EDIT,
                                        undoActions)
                        ));
                    }
                    
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
    
    private static void personCheckDialog(Person person, ObservableList<Person> tempTeam, Team currentTeam) 
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
        
        grid.getChildren().add(new Label("Are you sure you want to move " + person.getShortName() 
                +  " from " + person.getTeamName() + " to " + currentTeam + "?"));
        grid.getChildren().add(buttons);
        
        btnYes.setOnAction((event) ->
            {
                tempTeam.add(person);
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
    
    private static void refreshListView(ObservableList<Person> currentTeam, Team team)
    {
            ObservableList<String> tempTeamString = TeamScene.sortListView(currentTeam, team);
            
            informationGrid.getChildren().remove(teamsPeopleBox);

            teamsPeopleBox = new ListView(tempTeamString);
            teamsPeopleBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            informationGrid.add(teamsPeopleBox, 0, 5);
    }
}
