/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information.team;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.SceneSwitcher;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.util.validation.ShortNameValidator;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.team.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * A class for displaying the team edit scene.
 * @author crw73
 */
public class NewTeamEditScene extends ScrollPane
{
    Team baseTeam;
    RequiredField shortNameField;
    CustomTextArea descriptionField;
    Person allocatedProductOwner;
    Person allocatedScrumMaster;
    List<Person> allocatedDevelopers = new ArrayList<>();
    ObservableList<Role> roleList = observableArrayList();
    ComboBox<Role> roleComboBox;
    Role noneRole = new Role("(none)", Role.RoleType.NONE);

    public NewTeamEditScene(Team baseTeam)
    {
        this.baseTeam = baseTeam;

        VBox container = new VBox(10);
        container.setPadding(new Insets(25, 25, 25, 25));

        // Basic information fields
        shortNameField = new RequiredField("Short Name: ");
        descriptionField = new CustomTextArea("Team Description: ", 300);
        shortNameField.setText(baseTeam.getShortName());
        descriptionField.setText(baseTeam.getDescription());
        shortNameField.setMaxWidth(275);
        descriptionField.setMaxWidth(275);

        // Team assignment buttons
        Button btnAssign = new Button("<");
        Button btnUnassign = new Button(">");
        VBox assignmentButtons = new VBox();
        assignmentButtons.spacingProperty().setValue(10);
        assignmentButtons.getChildren().add(btnAssign);
        assignmentButtons.getChildren().add(btnUnassign);
        assignmentButtons.setAlignment(Pos.CENTER);

        // Buttons for the scene
        Button btnSave = new Button("Done");
        Button btnCancel = new Button("Cancel");
        HBox sceneButtons = new HBox();
        sceneButtons.spacingProperty().setValue(10);
        sceneButtons.alignmentProperty().set(Pos.TOP_LEFT);
        sceneButtons.getChildren().addAll(btnSave, btnCancel);

        // Role assignment
        Button btnRoleAssign = new Button("Assign");
        btnRoleAssign.setDisable(true);
        roleList = observableArrayList();
        roleList.add(noneRole);
        roleList.addAll(Global.currentWorkspace.getRoles());
        roleComboBox = new ComboBox<>(roleList);
        roleComboBox.setDisable(true);

        HBox roleAssignmentBox = new HBox(10);
        roleAssignmentBox.getChildren().addAll(
                new Label("Role:"), roleComboBox, btnRoleAssign);










        // Make lists of people in the team and available people
        ObservableList<Person> teamMembersList = observableArrayList();
        teamMembersList.addAll(baseTeam.getPeople());

        ObservableList<Person> availablePeopleList = observableArrayList();
        availablePeopleList.addAll(Global.currentWorkspace.getPeople());
        availablePeopleList.removeAll(teamMembersList);







        // Make list views for the people lists
        ListView<Person> teamMembersListView = new ListView<>(teamMembersList);
        teamMembersListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        teamMembersListView.getSelectionModel().select(0);

        ListView<Person> availablePeopleListView = new ListView<>(availablePeopleList);
        availablePeopleListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        teamMembersListView.getSelectionModel().select(0);

        // Add the views into styled gui containers
        VBox teamMembersBox = new VBox(10);
        teamMembersBox.getChildren().add(new Label("Team Members: "));
        teamMembersBox.getChildren().add(teamMembersListView);

        VBox availablePeopleBox = new VBox(10);
        availablePeopleBox.getChildren().add(new Label("Available People: "));
        availablePeopleBox.getChildren().add(availablePeopleListView);

        HBox memberListViews = new HBox(10);
        memberListViews.getChildren().addAll(teamMembersBox, assignmentButtons, availablePeopleBox);
        memberListViews.setPrefHeight(192);




















        // Adding of gui elements to the container (VBox)
        container.getChildren().addAll(
                shortNameField,
                descriptionField,
                memberListViews,
                roleAssignmentBox,
                sceneButtons
        );






        // Listeners
        // Update the roles combo when the selected person changes
        teamMembersListView.getSelectionModel().selectedItemProperty().addListener((event) ->
        {
            roleList.clear();
            if (teamMembersListView.getSelectionModel().getSelectedItems().size() == 1)
            {
                roleComboBox.setDisable(false);
                btnRoleAssign.setDisable(false);
                updateRoles(teamMembersListView.getSelectionModel().getSelectedItems().get(0));
            }
            else
            {
                // No person, or more than one person, selected
                roleComboBox.setDisable(true);
                btnRoleAssign.setDisable(true);
            }
        });





        // Button events
        btnAssign.setOnAction((event) ->
            {
                teamMembersList.addAll(
                        availablePeopleListView.getSelectionModel().getSelectedItems());
                availablePeopleList.removeAll(
                        availablePeopleListView.getSelectionModel().getSelectedItems());
            });


        btnUnassign.setOnAction((event) ->
            {
                availablePeopleList.addAll(
                        teamMembersListView.getSelectionModel().getSelectedItems());
                teamMembersList.removeAll(
                        teamMembersListView.getSelectionModel().getSelectedItems());
            });


        btnRoleAssign.setOnAction((event) ->
            {
                Person selectedPerson =
                        teamMembersListView.getSelectionModel().getSelectedItems().get(0);
                Role selectedRole = roleComboBox.getSelectionModel().getSelectedItem();
                if (selectedRole == null)
                {
                    System.out.println("No selected role");
                }
                switch (selectedRole.getType())
                {
                    case PRODUCT_OWNER:
                        allocatedProductOwner = selectedPerson;
                        break;
                    case SCRUM_MASTER:
                        allocatedScrumMaster = selectedPerson;
                        break;
                    case DEVELOPMENT_TEAM_MEMBER:
                        allocatedDevelopers.add(selectedPerson);
                        break;
                    case NONE:
                        if (allocatedProductOwner == selectedPerson)
                        {
                            allocatedProductOwner = null;
                        }
                        if (allocatedScrumMaster == selectedPerson)
                        {
                            allocatedScrumMaster = null;
                        }
                        allocatedDevelopers.remove(selectedPerson);
                        break;
                    default:
                        break;
                }
            });


        btnCancel.setOnAction((event) ->
            {
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.TEAM, baseTeam);
            });


        btnSave.setOnAction((event) ->
            {
                if (isValidState())// validation
                {
                    // Edit Command
                    baseTeam.edit(shortNameField.getText(), descriptionField.getText(),
                            teamMembersList, allocatedProductOwner, allocatedScrumMaster,
                            allocatedDevelopers);

                    Collections.sort(Global.currentWorkspace.getTeams());
                    SceneSwitcher.changeScene(SceneSwitcher.ContentScene.TEAM, baseTeam);
                    MainScene.treeView.refresh();
                }
                else
                {
                    event.consume();
                }
            });




        this.setStyle("-fx-background-color:transparent;");
        this.setContent(container);
    }


    private void updateRoles(Person person)
    {
        roleList.clear();
        roleList.add(noneRole);
        for (Role role : Global.currentWorkspace.getRoles())
        {
            if (person.getSkills().containsAll(role.getRequiredSkills()))
            {
                roleList.add(role);
            }
        }
    }


    private boolean isValidState()
    {
        // Validation methods
        return (shortNameField.getText().equals(baseTeam.getShortName())  // Is the same,
                || ShortNameValidator.validateShortName(shortNameField))  // or new name validates
                && areRolesValid();
    }


    private boolean areRolesValid()
    {
        return !(allocatedScrumMaster == allocatedProductOwner)
                && !allocatedDevelopers.contains(allocatedScrumMaster)
                && !allocatedDevelopers.contains(allocatedProductOwner);
    }

}
