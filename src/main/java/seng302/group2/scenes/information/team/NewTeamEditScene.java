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
import javafx.scene.control.Dialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.dialog.*;
import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.SceneSwitcher;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.dialog.CustomDialog;
import seng302.group2.util.validation.ShortNameValidator;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.team.Team;

import java.util.*;

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
    Set<Person> allocatedDevelopers = new HashSet<>();
    ObservableList<Role> roleList = observableArrayList();
    ComboBox<Role> roleComboBox;
    Role noneRole = new Role("(none)", Role.RoleType.NONE);

    public NewTeamEditScene(Team baseTeam)
    {
        // Init
        this.baseTeam = baseTeam;
        allocatedDevelopers.addAll(baseTeam.getDevs());
        allocatedProductOwner = baseTeam.getProductOwner();
        allocatedScrumMaster = baseTeam.getScrumMaster();


        // Setup basic GUI
        VBox container = new VBox(10);
        container.setPadding(new Insets(25, 25, 25, 25));


        // Basic information fields
        shortNameField = new RequiredField("Short Name:");
        shortNameField.setText(baseTeam.getShortName());
        shortNameField.setMaxWidth(275);
        descriptionField = new CustomTextArea("Team Description:", 300);
        descriptionField.setText(baseTeam.getDescription());
        descriptionField.setMaxWidth(275);


        // Team assignment buttons
        Button btnAssign = new Button("<");
        Button btnUnassign = new Button(">");
        VBox assignmentButtons = new VBox();
        assignmentButtons.spacingProperty().setValue(10);
        assignmentButtons.getChildren().addAll(btnAssign, btnUnassign);
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
        roleComboBox = new ComboBox<>(roleList);

        HBox roleAssignmentBox = new HBox(10);
        roleAssignmentBox.getChildren().addAll(
                new Label("Role:"), roleComboBox, btnRoleAssign);



        // Draft member and available people lists
        ObservableList<Person> teamMembersList = observableArrayList();
        teamMembersList.addAll(baseTeam.getPeople());

        ObservableList<Person> availablePeopleList = observableArrayList();
        for (Person person : Global.currentWorkspace.getPeople())
        {
            if (person.getTeam().isUnassignedTeam())
            {
                availablePeopleList.add(person);
            }
        }
        availablePeopleList.removeAll(teamMembersList);


        // List views
        ListView<Person> teamMembersListView = new ListView<>(teamMembersList);
        teamMembersListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        teamMembersListView.getSelectionModel().select(0);

        ListView<Person> availablePeopleListView = new ListView<>(availablePeopleList);
        availablePeopleListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        availablePeopleListView.getSelectionModel().select(0);

        VBox teamMembersBox = new VBox(10);
        teamMembersBox.getChildren().add(new Label("Team Members: "));
        teamMembersBox.getChildren().add(teamMembersListView);

        VBox availablePeopleBox = new VBox(10);
        availablePeopleBox.getChildren().add(new Label("Available People: "));
        availablePeopleBox.getChildren().add(availablePeopleListView);

        HBox memberListViews = new HBox(10);
        memberListViews.getChildren().addAll(teamMembersBox, assignmentButtons, availablePeopleBox);
        memberListViews.setPrefHeight(192);


        // Load the initial role assignment options
        if (teamMembersListView.getSelectionModel().getSelectedItems().size() == 1)
        {
            roleComboBox.setDisable(false);
            btnRoleAssign.setDisable(false);
            updateRoles(teamMembersListView.getSelectionModel().getSelectedItems().get(0));
        }
        else
        {
            roleComboBox.setDisable(true);
            btnRoleAssign.setDisable(true);
        }


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
                    // Edit Command.

                    baseTeam.edit(shortNameField.getText(),
                        descriptionField.getText(),
                        teamMembersList,
                        allocatedProductOwner,
                        allocatedScrumMaster,
                        allocatedDevelopers
                    );

                    Collections.sort(Global.currentWorkspace.getTeams());
                    SceneSwitcher.changeScene(SceneSwitcher.ContentScene.TEAM, baseTeam);
                    MainScene.treeView.refresh();
                }
                else
                {
                    event.consume();
                }
            });


        // Finally
        this.setStyle("-fx-background-color:transparent;");
        this.setContent(container);
    }


    /**
     * Updates the roles in the role allocation combo box with those that the given person can fill
     * @param person The person whose roles to check
     */
    private void updateRoles(Person person)
    {
        roleList.clear();
        roleList.add(noneRole);
        for (Role role : Global.currentWorkspace.getRoles())
        {
            if (person != null && person.getSkills().containsAll(role.getRequiredSkills()))
            {
                roleList.add(role);
            }
        }
    }


    /**
     * Checks if the changes in the scene are valid
     * @return If the changes in the scene are valid
     */
    private boolean isValidState()
    {
        return (shortNameField.getText().equals(baseTeam.getShortName())  // Is the same,
                || ShortNameValidator.validateShortName(shortNameField, null))// new name validates
                && areRolesValid();
    }



    /**
     * Checks if the current role changes are valid, and displays a dialog with the first found
     * violation
     * @return If the role changes are valid
     */
    private boolean areRolesValid()
    {
        // Find and store errors in validation
        String errorMessage = null;
        if (allocatedScrumMaster == allocatedProductOwner && allocatedScrumMaster != null)
        {
            errorMessage = "Cannot have the same person assigned to Product Owner and Scrum Master";
        }
        else if (allocatedDevelopers.contains(allocatedScrumMaster) && allocatedScrumMaster != null)
        {
            errorMessage = "The Scrum Master cannot also be assigned as a developer";
        }
        else if (allocatedDevelopers.contains(allocatedProductOwner)
                && allocatedProductOwner != null)
        {
            errorMessage = "The Product Owner cannot also be assigned as a developer";
        }

        // Display the first found error, or pass if valid
        if (errorMessage == null)
        {
            return true;
        }
        else
        {
            CustomDialog.showDialog("Invalid Role Assignment", errorMessage,
                    Alert.AlertType.WARNING);
            return false;
        }
    }

}
