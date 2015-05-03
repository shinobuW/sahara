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
        
        ObservableList<Person> dialogPeopleRoles = observableArrayList();
        ObservableList<Role> dialogRoles = observableArrayList();
        
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
        
        
        btnAdd.setOnAction((event) ->
            {
                ObservableList<Person> selectedPeople = 
                        membersBox.getSelectionModel().getSelectedItems();
                for (Person item : selectedPeople)
                {
                    if (item.getTeam().isUnassignedTeam()
                            || item.getTeam() == null) 
                    {
                        tempTeam.add(item, false);
                    }
                    else 
                    {
                        personCheckDialog(item, tempTeam, currentTeam);
                    }
                }
                
                dialogPeople.clear();
                for (TreeViewItem projectPeople : Global.currentWorkspace.getPeople())
                {
                    if (!tempTeam.getPeople().contains(projectPeople))
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
                    if (!tempTeam.getPeople().contains(projectPeople))
                    {
                        dialogPeople.add((Person) projectPeople);
                    }
                }
            });

        mkeDev.setOnAction((event) ->
            {
                ObservableList<Person> selectedPeople =
                        teamsPeopleBox.getSelectionModel().getSelectedItems();

                for (Person selectedPerson : selectedPeople)
                {
                    dialogRoles.add(selectedPerson.getRole());
                    dialogPeopleRoles.add(selectedPerson);
                    selectedPerson.setRole(Global.currentWorkspace.getRoles().get(2));
                }

            });

        rmvDev.setOnAction((event) ->
            {
                ObservableList<Person> selectedPeople =
                        teamsPeopleBox.getSelectionModel().getSelectedItems();

                for (Person selectedPerson : selectedPeople)
                {
                    int personIndex = dialogPeopleRoles.indexOf(selectedPerson);
                    dialogPeopleRoles.remove(selectedPerson);
                    dialogRoles.remove(personIndex, personIndex);
                    selectedPerson.setRole(null);
                }
            });
        
        btnCancel.setOnAction((event) ->
            {
                App.content.getChildren().remove(informationGrid);
                TeamScene.getTeamScene((Team) Global.selectedTreeItem.getValue());
                App.content.getChildren().add(informationGrid);
                for (int i = 0; i < dialogRoles.size(); i++)
                {
                    dialogPeopleRoles.get(i).setRole(dialogRoles.get(i));
                }
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
                    
                    for (int i = 0; i < dialogRoles.size(); i++)
                    {
                        undoActions.add(new UndoableItem(
                                dialogPeopleRoles.get(i),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PERSON_ROLE,
                                        dialogRoles.get(i)),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PERSON_ROLE,
                                        dialogPeopleRoles.get(i).getRole())));
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

    /**
     * Asks if the user is sure about moving a person to a different team
     * @param person The person being moved
     * @param tempTeam The team to move to
     * @param currentTeam The persons current team
     */
    private static void personCheckDialog(Person person, Team tempTeam, Team currentTeam) 
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
                +  " from " + person.getTeamName() + " to " + currentTeam));
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
