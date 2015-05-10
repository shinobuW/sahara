/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information.Team;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.controlsfx.dialog.Dialog;
import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.SceneSwitcher;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.util.undoredo.UndoRedoAction;
import seng302.group2.util.undoredo.UndoRedoPerformer;
import seng302.group2.util.undoredo.UndoableItem;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

import java.util.ArrayList;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.scenes.MainScene.informationPane;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * A class for displaying the team edit scene.
 * @author crw73
 */
public class TeamEditScene
{
    private static ObservableList<Person> tempTeam = observableArrayList();
    private static ListView teamsPeopleBox = new ListView();
    private static CustomComboBox productOwnerBox = new CustomComboBox("Product Owner: ", false);
    private static CustomComboBox scrumMasterBox = new CustomComboBox("Scrum Master: ", false);
    /**
     * Gets the Team Edit information scene.
     * @param currentTeam The team to show the information of
     * @return The Team Edit information scene
     */
    public static ScrollPane getTeamEditScene(Team currentTeam)
    {

        //Team currentTeam = (Team) selectedTreeItem.getValue();
        informationPane = new VBox(10);
        /*informationPane.setAlignment(Pos.TOP_LEFT);
        informationPane.setHgap(10);
        informationPane.setVgap(10);*/
        informationPane.setPadding(new Insets(25,25,25,25));

        Button btnCancel = new Button("Cancel");
        Button btnSave = new Button("Save");

        HBox devBtns = new HBox();
        devBtns.spacingProperty().setValue(10);
        devBtns.alignmentProperty().set(Pos.TOP_LEFT);
        Button mkeDev = new Button("Make Developer");
        Button rmvDev = new Button("Unmake Developer");
        devBtns.getChildren().add(mkeDev);
        devBtns.getChildren().add(rmvDev);

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.TOP_LEFT);
        buttons.getChildren().addAll(btnSave, btnCancel);

        RequiredField shortNameCustomField = new RequiredField("Short Name: ");
        CustomTextArea descriptionTextArea = new CustomTextArea("Team Description: ", 300);
        productOwnerBox = new CustomComboBox("Product Owner: ", false);
        scrumMasterBox = new CustomComboBox("Scrum Master: ", false);

        shortNameCustomField.setMaxWidth(275);
        descriptionTextArea.setMaxWidth(275);
        productOwnerBox.setMaxWidth(275);
        scrumMasterBox.setMaxWidth(275);

        productOwnerBox.addToComboBox("");
        scrumMasterBox.addToComboBox("");

        Person currentSM = currentTeam.getScrumMaster();
        Person currentPO = currentTeam.getProductOwner();

        
        shortNameCustomField.setText(currentTeam.getShortName());
        descriptionTextArea.setText(currentTeam.getDescription());
        
        Button btnAdd = new Button("<-");
        Button btnDelete = new Button("->");

        
        VBox peopleButtons = new VBox();
        peopleButtons.spacingProperty().setValue(10);
        peopleButtons.getChildren().add(btnAdd);
        peopleButtons.getChildren().add(btnDelete);
        peopleButtons.setAlignment(Pos.CENTER);

        // Creates a copy of the Teams people as of when this function is called.
        // CurrentTeam never changes until the save is called.
        ObservableList<String> tempTeamString = observableArrayList();
        tempTeam = observableArrayList();
        for (Person person : currentTeam.getPeople())
        {
            tempTeam.add(person);
        }
        
        // Creates the Teams box of People.
        teamsPeopleBox = new ListView(tempTeamString);
        teamsPeopleBox.setMaxWidth(275);
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
        
        for (Person teamMember : tempTeam)
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

        for (Person teamMember : tempTeam)
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
        
        // Creates the Box of people that aren't assigned to the current Team.
        ListView membersBox = new ListView(dialogPeople);
        membersBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        membersBox.setMaxWidth(275);

        informationPane.getChildren().add(shortNameCustomField);
        informationPane.getChildren().add(descriptionTextArea);

        informationPane.getChildren().add(productOwnerBox);
        informationPane.getChildren().add(scrumMasterBox);
        refreshListView(tempTeam);
        refreshComboBox(tempTeam);

        HBox h1 = new HBox(10);
        VBox v1 = new VBox(10);
        v1.getChildren().add(new Label("Team Members: "));
        v1.getChildren().add(membersBox);

        VBox v2 = new VBox(10);
        v2.getChildren().add(new Label("Available People: "));
        v2.getChildren().add(teamsPeopleBox);

        h1.getChildren().addAll(v1, peopleButtons, v2);

        informationPane.getChildren().add(h1);
        informationPane.getChildren().add(devBtns);
        informationPane.getChildren().add(buttons);



        btnAdd.setOnAction((event) ->
            {
                ObservableList<Person> selectedPeople = 
                        membersBox.getSelectionModel().getSelectedItems();
                for (Person item : selectedPeople)
                {
                    if (item.getTeam().isUnassignedTeam()
                            || item.getTeam() == null || (item.getTeam() == currentTeam)) 
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
                refreshListView(tempTeam);
                refreshComboBox(tempTeam);
            });
        
        btnDelete.setOnAction((event) ->
            {
                ObservableList<Integer> selectedPeople =
                        teamsPeopleBox.getSelectionModel().getSelectedIndices();
                for (int i = selectedPeople.size() - 1; i >= 0; i--)
                {
                    tempTeam.remove(tempTeam.get(selectedPeople.get(i)));
                }
                
                refreshListView(tempTeam);
                dialogPeople.clear();
                for (TreeViewItem projectPeople : Global.currentWorkspace.getPeople())
                {
                    if (!tempTeam.contains(projectPeople))
                    {
                        dialogPeople.add((Person) projectPeople);
                    }
                }
                refreshListView(tempTeam);
                refreshComboBox(tempTeam);

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
                    refreshListView(tempTeam);
                    refreshComboBox(tempTeam);
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
                    refreshListView(tempTeam);

                }
            });
        
        btnCancel.setOnAction((event) ->
            {
                //App.content.getItems().remove(informationPane);
                for (int i = 0; i < teamPeopleRoles.size(); i++)
                {
                    teamPeopleRoles.get(i).setRole(teamRoles.get(i));
                }
                /*TeamScene.getTeamScene((Team) Global.selectedTreeItem.getValue());
                App.content.getItems().add(informationPane);*/
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.TEAM, currentTeam);
            });

        btnSave.setOnAction((event) ->
            {
                boolean correctShortName;
                if (productOwnerBox.getValue().equals(scrumMasterBox.getValue()) 
                        && !productOwnerBox.getValue().equals(""))
                {
                    roleCheckDialog(currentSM, currentPO);
                }
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
                                        UndoRedoPerformer.UndoRedoProperty.PERSON_ADD_ROLE,
                                        teamRoles.get(i)),
                                new UndoRedoAction(
                                        UndoRedoPerformer.UndoRedoProperty.PERSON_ADD_ROLE,
                                        teamPeopleRoles.get(i).getRole())));
                    }

                    String stringSM = "";
                    if (currentSM != null)
                    {
                        stringSM = currentSM.toString();
                    }
                    //Check for UndoRedo
                    if (!scrumMasterBox.getValue().equals(stringSM))
                    {
                        System.out.println(scrumMasterBox.getValue() + "SM box");
                        System.out.println(stringSM + " owner");
                        //For looping through the changed list
                        
                        
                        for (Person selectedPerson : tempTeam)
                        {
                            if (scrumMasterBox.getValue().equals(""))
                            {
                                currentTeam.setScrumMaster(null);
                            }
                            
                            //checking to see if the selected Person is a ScrumMaster
                            if (selectedPerson.toString().equals(scrumMasterBox.getValue()))
                            {
                                undoActions.add(new UndoableItem(
                                    selectedPerson,
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.PERSON_ADD_ROLE,
                                            selectedPerson.getRole()),
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.PERSON_ADD_ROLE,
                                            Global.currentWorkspace.getRoles().get(0))));
                                currentTeam.setScrumMaster(selectedPerson);
                                selectedPerson.setRole(Global.currentWorkspace.getRoles().
                                        get(0));
                            }
                            else if (selectedPerson.getRole() != null) 
                            {
                                if (selectedPerson.getRole().getType() 
                                        == Role.RoleType.ScrumMaster)
                                {
                                    selectedPerson.setRole(Role.getRoleType(
                                            Role.RoleType.Others));
                                }
                            }

                        }
                        
                    }

                    String stringPO = "";
                    if (currentPO != null)
                    {
                        stringPO = currentPO.toString();
                    }
                    
                    if (!(productOwnerBox.getValue().equals(stringPO)))
                    {
                        for (Person selectedPerson : tempTeam)
                        {
                            if (selectedPerson.toString().equals(productOwnerBox.getValue()))
                            {
                                undoActions.add(new UndoableItem(
                                    selectedPerson,
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.PERSON_ADD_ROLE,
                                            selectedPerson.getRole()),
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.PERSON_ADD_ROLE,
                                            Global.currentWorkspace.getRoles().get(1))));
                                currentTeam.setProductOwner(selectedPerson);
                                selectedPerson.setRole(Global.currentWorkspace.getRoles().get(1));
                            }
                            else if (selectedPerson.getRole() != null) 
                            {
                                if (selectedPerson.getRole().getType() 
                                            == Role.RoleType.ProductOwner)
                                {
                                    selectedPerson.setRole(Role.getRoleType(Role.RoleType.Others));
                                }
                            }
                        }
                    }
                    
                    for (Person person : tempTeam)
                    {
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
                                     
                            undoActions.add(new UndoableItem(
                                    person,
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.PERSON_TEAM, 
                                            person.getRole()),
                                    new UndoRedoAction(
                                            UndoRedoPerformer.UndoRedoProperty.PERSON_TEAM, 
                                            null)));
                            
                            if (person.getRole() == Role.getRoleType(Role.RoleType.ScrumMaster))
                            {
                                currentTeam.setScrumMaster(null);
                            }
                            else if (person.getRole() == Role.getRoleType(
                                    Role.RoleType.ScrumMaster))
                            {
                                currentTeam.setScrumMaster(null);
                            }
                            person.setRole(Role.getRoleType(Role.RoleType.Others));
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

                    SceneSwitcher.changeScene(SceneSwitcher.ContentScene.TEAM, currentTeam);
                    MainScene.treeView.refresh();

                }
                else
                {
                    event.consume();
                }

            });
        ScrollPane wrapper = new ScrollPane(informationPane);
        wrapper.setStyle("-fx-background-color:transparent;");
        return wrapper;
    }
    
    /*
    * Dialog shown for checking if a Person is already on a team.
    * @param person The person that is being checked.
    * @param tempTeam The temporary team for adding to see if any changes before and after clicking
    * save.
    * @param currentTeam The currentTeam that the person is being moved to.
    */
    private static void personCheckDialog(Person person, ObservableList<Person> tempTeam, 
            Team currentTeam) 
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
    
    /*
    * Dialog shown for checking what role to give someone if they have been assigned to in both 
    * Comboboxes.
    * @param currentSM The current Scrum Master.
    * @param currentPo The current Product Owner.
    */
    private static void roleCheckDialog(Person currentSM, Person currentPO) 
    {
        Dialog dialog = new Dialog(null, "PO and SM set to same Person");
        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);
               
        Button btnPO = new Button("Make Product Owner");
        Button btnSM = new Button("Make Scrum Master");
        
        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(btnPO, btnSM);
        
        grid.getChildren().add(new Label("Can't make " + scrumMasterBox.getValue() 
                + " set to both Scrum Master and Product Owner."));
        grid.getChildren().add(buttons);
        
        btnPO.setOnAction((event) ->
            {
                scrumMasterBox.setValue(currentSM.toString());
                System.out.println(currentSM.toString() + scrumMasterBox.getValue());
                if (scrumMasterBox.getValue().equals(currentSM.toString()))
                {
                    scrumMasterBox.setValue("");
                }
                dialog.hide();
            });
        
        btnSM.setOnAction((event) ->
            {
                productOwnerBox.setValue(currentPO.toString());
                if (scrumMasterBox.getValue().equals(currentPO.toString()))
                {
                    scrumMasterBox.setValue("");
                }
                dialog.hide();
                
            });
        
        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(grid);
        dialog.show();
    }
    
    /*
    * Dynamically updating the Listview when someone is being added or a role is being changed on a 
    * person.
    * @param currentTeam the Team to input into the ListView
    */
    private static void refreshListView(ObservableList<Person> currentTeam)
    {
        System.out.println(tempTeam + " before");
        tempTeam = TeamScene.sortListView(currentTeam);
        System.out.println(tempTeam + " after");
        ObservableList<String> tempTeamString = TeamScene.convertToString(tempTeam);

        informationPane.getChildren().remove(teamsPeopleBox);

        teamsPeopleBox = new ListView(tempTeamString);
        teamsPeopleBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        informationPane.getChildren().add(teamsPeopleBox);
    }
    
    /*
    * Dynamically updating the Combobox when someone is being added or a role is being changed on a 
    * person.
    * @param currentTeam the Team to input into the Combobox based on skills
    */
    private static void refreshComboBox(ObservableList<Person> currentTeam)
    {
        
        informationPane.getChildren().remove(productOwnerBox);
        informationPane.getChildren().remove(scrumMasterBox);
        
        productOwnerBox = new CustomComboBox("Product Owner: ", false);
        scrumMasterBox = new CustomComboBox("Scrum Master: ", false);

        productOwnerBox.addToComboBox("");
        scrumMasterBox.addToComboBox("");

        Person currentSM = null;
        Person currentPO = null;
        
        for (Person teamMember : currentTeam)
        {
            for (Skill memSkill : teamMember.getSkills())
            {
                if (memSkill.getShortName().equals("Product Owner"))
                {
                    productOwnerBox.addToComboBox(teamMember.toString());
                    if (teamMember.getRole() != null)
                    {
                        if (teamMember.getRole().toString().equals("Product Owner"))
                        {
                            currentPO = teamMember;
                        }
                    }
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

        for (Person teamMember : currentTeam)
        {
            for (Skill memSkill : teamMember.getSkills())
            {
                if (memSkill.getShortName().equals("Scrum Master"))
                {
                    scrumMasterBox.addToComboBox(teamMember.toString());
                    if (teamMember.getRole() != null)
                    {
                        if (teamMember.getRole().toString().equals("Scrum Master"))
                        {
                            currentSM = teamMember;
                        }
                    }
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
        
        informationPane.getChildren().add(productOwnerBox);
        informationPane.getChildren().add(scrumMasterBox);
    }
}
