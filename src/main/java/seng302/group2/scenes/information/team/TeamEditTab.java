package seng302.group2.scenes.information.team;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableListView;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.dialog.CustomDialog;
import seng302.group2.util.validation.ShortNameValidator;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.tag.Tag;
import seng302.group2.workspace.team.Team;

import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * A class for displaying a tab used to edit teams.
 * Created by btm38 on 30/07/15.
 */
public class TeamEditTab extends SearchableTab {

    Set<SearchableControl> searchControls = new HashSet<>();

    private Team baseTeam;
    private RequiredField shortNameField;
    private CustomTextArea descriptionField;
    private Person allocatedProductOwner;
    private Person allocatedScrumMaster;
    private Set<Person> allocatedDevelopers = new HashSet<>();
    private ObservableList<Role> roleList = observableArrayList();
    private CustomComboBox<Role> roleComboBox;
    private Role noneRole = new Role("", Role.RoleType.NONE);
    String poPlaceholder = "No Product Owner Assigned";
    String smPlaceholder = "No Scrum Master Assigned";

    /**
     * Constructor for the Team Edit Tab class. This constructor creates a JavaFX ScrollPane
     * which is populated with relevant controls and then shown.
     *
     * @param baseTeam The team being edited
     */
    public TeamEditTab(Team baseTeam) {
        // Initialise Variables
        this.baseTeam = baseTeam;
        construct();
    }


    /**
     * Updates the roles in the role allocation combo box with those that the given person can fill
     *
     * @param person The person whose roles to check
     */
    private void updateRoles(Person person) {
        roleList.clear();
        roleList.add(noneRole);
        for (Role role : Global.currentWorkspace.getRoles()) {
            if (person != null && person.getSkills().containsAll(role.getRequiredSkills())) {
                roleList.add(role);
            }
        }
    }


    /**
     * Checks if the changes in the scene are valid
     *
     * @return If the changes in the scene are valid
     */
    private boolean isValidState() {
        return (shortNameField.getText().equals(baseTeam.getShortName())  // Is the same,
                || ShortNameValidator.validateShortName(shortNameField, null))// new name validates
                && areRolesValid();
    }


    /**
     * Checks if the current role changes are valid, and displays a dialog with the first found
     * violation
     *
     * @return If the role changes are valid
     */
    private boolean areRolesValid() {
        // Find and store errors in validation
        String errorMessage = null;
        if (allocatedScrumMaster == allocatedProductOwner && allocatedScrumMaster != null) {
            errorMessage = "Cannot have the same person assigned to Product Owner and Scrum Master";
        }
        else if (allocatedDevelopers.contains(allocatedScrumMaster) && allocatedScrumMaster != null) {
            errorMessage = "The Scrum Master cannot also be assigned as a developer";
        }
        else if (allocatedDevelopers.contains(allocatedProductOwner)
                && allocatedProductOwner != null) {
            errorMessage = "The Product Owner cannot also be assigned as a developer";
        }

        // Display the first found error, or pass if valid
        if (errorMessage == null) {
            return true;
        }
        else {
            CustomDialog.showDialog("Invalid Role Assignment", errorMessage,
                    Alert.AlertType.WARNING);
            return false;
        }
    }

    /**
     * Gets all the searchable controls on this tab.
     * @return a collection of all the searchable controls on this tab.
     */
    @Override
    public Collection<SearchableControl> getSearchableControls() {
        return searchControls;
    }

    @Override
    public void construct() {
        allocatedDevelopers.addAll(baseTeam.getDevs());
        SearchableText poText;
        SearchableText smText;

        if (baseTeam.getProductOwner() != null) {
            allocatedProductOwner = baseTeam.getProductOwner();
            poText = new SearchableText("Product Owner: " + allocatedProductOwner);
        }
        else {
            poText = new SearchableText("Product Owner: " + poPlaceholder);
        }

        if (baseTeam.getScrumMaster() != null) {
            allocatedScrumMaster = baseTeam.getScrumMaster();
            smText = new SearchableText("Scrum Master: " + allocatedScrumMaster);
        }
        else {
            smText = new SearchableText("Scrum Master: " + smPlaceholder);
        }


        // Tab settings
        this.setText("Edit Team");
        Pane editPane = new VBox(10);
        editPane.setBorder(null);
        editPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(editPane);
        this.setContent(wrapper);


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
        Button btnDone = new Button("Done");
        Button btnCancel = new Button("Cancel");
        HBox sceneButtons = new HBox();
        sceneButtons.spacingProperty().setValue(10);
        sceneButtons.alignmentProperty().set(Pos.TOP_LEFT);
        sceneButtons.getChildren().addAll(btnDone, btnCancel);


        // Role assignment
        Button btnRoleAssign = new Button("Assign");
        btnRoleAssign.setDisable(true);
        roleList = observableArrayList();
        roleComboBox = new CustomComboBox<>("Role:");
        roleComboBox.getComboBox().setItems(roleList);

        HBox roleAssignmentBox = new HBox(10);

        roleAssignmentBox.getChildren().addAll(roleComboBox, btnRoleAssign);
        for (Role role : Global.currentWorkspace.getRoles()) {
            roleComboBox.addToComboBox(role);
        }



        // Draft member and available people lists
        ObservableList<Person> teamMembersList = observableArrayList();
        teamMembersList.addAll(baseTeam.getPeople());

        ObservableList<Person> availablePeopleList = observableArrayList();
        for (Person person : Global.currentWorkspace.getPeople()) {
            if (person.getTeam().isUnassignedTeam()) {
                availablePeopleList.add(person);
            }
        }
        availablePeopleList.removeAll(teamMembersList);


        // List views
        SearchableListView<Person> teamMembersListView = new SearchableListView<>(teamMembersList);
        teamMembersListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        teamMembersListView.getSelectionModel().select(0);

        SearchableListView<Person> availablePeopleListView = new SearchableListView<>(availablePeopleList);
        availablePeopleListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        availablePeopleListView.getSelectionModel().select(0);

        VBox teamMembersBox = new VBox(10);
        SearchableText teamMemberLabel = new SearchableText("Team Members: ");
        teamMemberLabel.setStyle("-fx-font-weight: bold");
        teamMembersBox.getChildren().addAll(teamMemberLabel, teamMembersListView);


        VBox availablePeopleBox = new VBox(10);
        SearchableText availablePeopleLabel = new SearchableText("Available People: ");
        availablePeopleLabel.setStyle("-fx-font-weight: bold");
        availablePeopleBox.getChildren().addAll(availablePeopleLabel, availablePeopleListView);

        HBox memberListViews = new HBox(10);
        memberListViews.getChildren().addAll(teamMembersBox, assignmentButtons, availablePeopleBox);
        memberListViews.setPrefHeight(192);


        // Load the initial role assignment options
        if (teamMembersListView.getSelectionModel().getSelectedItems().size() == 1) {
            roleComboBox.setDisable(false);
            btnRoleAssign.setDisable(false);
            updateRoles(teamMembersListView.getSelectionModel().getSelectedItems().get(0));
        }
        else {
            roleComboBox.setDisable(true);
            btnRoleAssign.setDisable(true);
        }

        // Listeners
        // Update the roles combo when the selected person changes
        teamMembersListView.getSelectionModel().selectedItemProperty().addListener((event) -> {
            roleList.clear();
            if (teamMembersListView.getSelectionModel().getSelectedItems().size() == 1) {
                roleComboBox.setDisable(false);
                btnRoleAssign.setDisable(false);
                updateRoles(teamMembersListView.getSelectionModel().getSelectedItems().get(0));
            }
            else {
                // No person, or more than one person, selected
                roleComboBox.setDisable(true);
                btnRoleAssign.setDisable(true);
            }
        });


        // Events
        btnAssign.setOnAction((event) -> {
            Collection<Person> selectedPeople = new ArrayList<>();
            selectedPeople.addAll(availablePeopleListView.getSelectionModel().
                    getSelectedItems());
            for (Person person : selectedPeople) {
                if (person.getRole() == Role.getRoleFromType(Role.RoleType.PRODUCT_OWNER)) {
                    person.setRole(Role.getRoleFromType(Role.RoleType.NONE));
                }
                if (person.getRole() == Role.getRoleFromType(Role.RoleType.SCRUM_MASTER)) {
                    person.setRole(Role.getRoleFromType(Role.RoleType.NONE));
                }
            }

            teamMembersList.addAll(
                    availablePeopleListView.getSelectionModel().getSelectedItems());
            availablePeopleList.removeAll(
                    availablePeopleListView.getSelectionModel().getSelectedItems());
        });

        btnUnassign.setOnAction((event) -> {
            Collection<Person> selectedPeople = new ArrayList<>();
            selectedPeople.addAll(teamMembersListView.getSelectionModel().
                    getSelectedItems());
            availablePeopleList.addAll(selectedPeople);
            teamMembersList.removeAll(selectedPeople);

            for (Person person : selectedPeople) {
                if (allocatedProductOwner == person) {
                    allocatedProductOwner = null;
                }
                if (allocatedScrumMaster == person) {
                    allocatedProductOwner = null;
                }
                if (allocatedDevelopers.contains(person)) {
                    allocatedDevelopers.remove(person);
                }
            }
        });

        btnRoleAssign.setOnAction((event) -> {
            Person selectedPerson =
                    teamMembersListView.getSelectionModel().getSelectedItems().get(0);
            Role selectedRole = roleComboBox.getComboBox().getSelectionModel().getSelectedItem();
            if (selectedRole == null) {
                return;
            }
            switch (selectedRole.getType()) {
                case PRODUCT_OWNER:
                    if (allocatedProductOwner != null) {
                        allocatedProductOwner.setRole(Role.getRoleFromType(Role.RoleType.NONE));
                    }
                    allocatedProductOwner = selectedPerson;
                    poText.setText("Product Owner: " + allocatedProductOwner);
                    selectedPerson.setRole(Role.getRoleFromType(Role.RoleType.PRODUCT_OWNER));
                    break;
                case SCRUM_MASTER:
                    if (allocatedScrumMaster != null) {
                        allocatedScrumMaster.setRole(Role.getRoleFromType(Role.RoleType.NONE));
                    }
                    allocatedScrumMaster = selectedPerson;
                    smText.setText("Scrum Master: " + allocatedScrumMaster);
                    selectedPerson.setRole(Role.getRoleFromType(Role.RoleType.SCRUM_MASTER));
                    break;
                case DEVELOPMENT_TEAM_MEMBER:
                    allocatedDevelopers.add(selectedPerson);
                    selectedPerson.setRole(Role.getRoleFromType(Role.RoleType.DEVELOPMENT_TEAM_MEMBER));
                    break;
                case NONE:
                    if (allocatedProductOwner == selectedPerson) {
                        allocatedProductOwner = null;
                    }
                    if (allocatedScrumMaster == selectedPerson) {
                        allocatedScrumMaster = null;
                    }
                    allocatedDevelopers.remove(selectedPerson);
                    selectedPerson.setRole(Role.getRoleFromType(Role.RoleType.NONE));
                    break;
                default:
                    break;
            }
        });

        btnCancel.setOnAction((event) -> baseTeam.switchToInfoScene());

        btnDone.setOnAction((event) -> {
            if (isValidState()) { // validation
                ArrayList<Tag> tags = new ArrayList<>();
                baseTeam.edit(shortNameField.getText(),
                        descriptionField.getText(),
                        teamMembersList,
                        allocatedProductOwner,
                        allocatedScrumMaster,
                        allocatedDevelopers,
                        tags
                );

                Collections.sort(Global.currentWorkspace.getTeams());
                baseTeam.switchToInfoScene();
                App.mainPane.refreshTree();
            }
            else {
                event.consume();
            }
        });

        // Add items to pane & search collection
        editPane.getChildren().addAll(
                shortNameField,
                descriptionField,
                memberListViews,
                roleAssignmentBox,
                poText,
                smText,
                sceneButtons
        );

        Collections.addAll(searchControls,
                shortNameField,
                descriptionField,
                roleComboBox,
                poText,
                smText,
                teamMembersListView,
                availablePeopleListView,
                teamMemberLabel,
                availablePeopleLabel
        );
    }
}
