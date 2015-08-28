package seng302.group2.scenes.information.person;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.*;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableListView;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.util.validation.NameValidator;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.Global.currentWorkspace;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * A class for displaying a tab used to edit people.
 * Created by btm38 on 30/07/15.
 */
public class PersonEditTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();
    /**
     * Constructor for the PersonEditTab class. This constructor creates a JavaFX ScrollPane
     * which is populated with relevant controls then shown.
     *
     * @param currentPerson The person being edited
     */
    public PersonEditTab(Person currentPerson) {
        // Tab settings
        this.setText("Edit Person");
        Pane editPane = new VBox(10);
        editPane.setBorder(null);
        editPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(editPane);
        this.setContent(wrapper);

        // Create controls
        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        RequiredField firstNameCustomField = new RequiredField("First Name:");
        RequiredField lastNameCustomField = new RequiredField("Last Name:");
        CustomTextField emailTextField = new CustomTextField("Email:");
        CustomDatePicker birthDatePicker = new CustomDatePicker("Birth Date:", false);
        final Callback<DatePicker, DateCell> birthDateCellFactory =
            new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item.isAfter(LocalDate.now())) {
                                setDisable(true);
                                setStyle("-fx-background-color: #ffc0cb;");
                            }
                        }
                    };
                }
            };
        birthDatePicker.getDatePicker().setDayCellFactory(birthDateCellFactory);

        CustomTextArea descriptionTextArea = new CustomTextArea("Person Description:", 300);

        Person tempPerson = new Person();
        for (Skill skill : currentPerson.getSkills()) {
            tempPerson.getSkills().add(skill);
        }

        SearchableListView personSkillsBox = new SearchableListView<>(tempPerson.getSkills());
        personSkillsBox.setPrefHeight(192);
        personSkillsBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        personSkillsBox.setMaxWidth(275);

        ObservableList<Skill> dialogSkills = observableArrayList();
        ObservableList<Skill> dialogSkillsCopy = observableArrayList();

        SearchableListView skillsBox = new SearchableListView(dialogSkills);
        skillsBox.setPrefHeight(192);
        skillsBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        skillsBox.setMaxWidth(275);

        CustomComboBox<Team> teamBox = new CustomComboBox("Team: ");
        teamBox.getComboBox().setItems(Global.currentWorkspace.getTeams());
        teamBox.setValue(currentPerson.getTeam());


        Button btnDone = new Button("Done");
        Button btnCancel = new Button("Cancel");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.TOP_LEFT);
        buttons.getChildren().addAll(btnDone, btnCancel);

        Button btnAdd = new Button("<-");
        Button btnDelete = new Button("->");

        VBox skillsButtons = new VBox();
        skillsButtons.spacingProperty().setValue(10);
        skillsButtons.setAlignment(Pos.CENTER);
        skillsButtons.getChildren().addAll(btnAdd, btnDelete);

        // Set values
        for (SaharaItem projectSkill : currentWorkspace.getSkills()) {
            if (!currentPerson.getSkills().contains(projectSkill)) {
                dialogSkills.add((Skill) projectSkill);
                dialogSkillsCopy.add((Skill) projectSkill);
            }
        }

        shortNameCustomField.setText(currentPerson.getShortName());
        firstNameCustomField.setText(currentPerson.getFirstName());
        lastNameCustomField.setText(currentPerson.getLastName());
        emailTextField.setText(currentPerson.getEmail());
        birthDatePicker.setValue(currentPerson.getBirthDate());
        descriptionTextArea.setText(currentPerson.getDescription());

        shortNameCustomField.setPrefWidth(275);
        firstNameCustomField.setPrefWidth(275);
        lastNameCustomField.setPrefWidth(275);
        emailTextField.setPrefWidth(275);
        birthDatePicker.setPrefWidth(275);
        descriptionTextArea.setPrefWidth(275);
        teamBox.setPrefWidth(275);

        VBox v1 = new VBox(10);
        SearchableText v1Label = new SearchableText("Skills: ");
        v1Label.setStyle("-fx-font-weight: bold");
        v1.getChildren().addAll(v1Label, personSkillsBox);

        VBox v2 = new VBox(10);
        SearchableText v2Label = new SearchableText("Available Skills: ");
        v2Label.setStyle("-fx-font-weight: bold");
        v2.getChildren().addAll(v2Label, skillsBox);

        HBox h1 = new HBox(10);
        h1.getChildren().addAll(v1, skillsButtons, v2);

        // Events
        btnAdd.setOnAction((event) -> {
                ObservableList<Skill> selectedSkills =
                        skillsBox.getSelectionModel().getSelectedItems();
                for (Skill item : selectedSkills) {
                    tempPerson.getSkills().add(item);
                }

                dialogSkills.clear();
                for (SaharaItem projectSkill : currentWorkspace.getSkills()) {
                    if (!tempPerson.getSkills().contains(projectSkill)) {
                        dialogSkills.add((Skill) projectSkill);
                    }
                }
            });

        btnDelete.setOnAction((event) -> {
                ObservableList<Skill> selectedSkills =
                        personSkillsBox.getSelectionModel().getSelectedItems();
                for (int i = selectedSkills.size() - 1; i >= 0; i--) {
                    tempPerson.getSkills().remove(selectedSkills.get(i));
                }

                dialogSkills.clear();
                for (SaharaItem projectSkill : currentWorkspace.getSkills()) {
                    if (!tempPerson.getSkills().contains(projectSkill)) {
                        dialogSkills.add((Skill) projectSkill);
                    }
                }
            });

        btnDone.setOnAction((event) -> {
                Team selectedTeam = teamBox.getValue();

                boolean shortNameUnchanged = shortNameCustomField.getText().equals(
                        currentPerson.getShortName());
                boolean firstNameUnchanged = firstNameCustomField.getText().equals(
                        currentPerson.getFirstName());
                boolean lastNameUnchanged = lastNameCustomField.getText().equals(
                        currentPerson.getLastName());
                boolean descriptionUnchanged = descriptionTextArea.getText().equals(
                        currentPerson.getDescription());
                boolean birthdayUnchanged = birthDatePicker.getValue() == currentPerson.getBirthDate();
                boolean emailUnchanged = emailTextField.getText().equals(
                        currentPerson.getEmail());
                boolean teamUnchanged = selectedTeam.getShortName().equals(
                        currentPerson.getTeamName());
                boolean skillsUnchanged = true;
                for (Object skill : personSkillsBox.getItems()) {
                    if (!currentPerson.getSkills().contains(skill)) {
                        skillsUnchanged = false;
                        break;
                    }
                }

                if (shortNameUnchanged && firstNameUnchanged && lastNameUnchanged
                        && descriptionUnchanged && birthdayUnchanged && emailUnchanged
                        && teamUnchanged && skillsUnchanged) {
                    // No fields have been changed
                    currentPerson.switchToInfoScene();
                    return;
                }

                boolean correctShortName = validateShortName(shortNameCustomField,
                        currentPerson.getShortName());
                boolean firstNameValidated = NameValidator.validateName(firstNameCustomField);
                boolean lastNameValidated = NameValidator.validateName(lastNameCustomField);

                // The short name is the same or valid
                if (correctShortName && firstNameValidated && lastNameValidated) {
                    LocalDate birthDate = birthDatePicker.getValue();


                    currentPerson.edit(shortNameCustomField.getText(),
                            firstNameCustomField.getText(),
                            lastNameCustomField.getText(),
                            emailTextField.getText(),
                            birthDate,
                            descriptionTextArea.getText(),
                            selectedTeam,
                            personSkillsBox.getItems()
                    );

                    Collections.sort(Global.currentWorkspace.getPeople());
                    currentPerson.switchToInfoScene();
                    App.mainPane.refreshTree();
                }
                else {
                    event.consume();
                }
            });

        btnCancel.setOnAction((event) -> {
                currentPerson.switchToInfoScene();
            });

        // Add items to pane & search collection
        editPane.getChildren().addAll(
                shortNameCustomField,
                firstNameCustomField,
                lastNameCustomField,
                emailTextField,
                birthDatePicker,
                descriptionTextArea,
                teamBox,
                h1,
                buttons
        );

        Collections.addAll(searchControls,
                shortNameCustomField,
                firstNameCustomField,
                lastNameCustomField,
                emailTextField,
                birthDatePicker,
                descriptionTextArea,
                teamBox,
                v1Label,
                personSkillsBox,
                v2Label,
                skillsBox
        );

    }

    /**
     * Gets all the searchable controls on this tab.
     * @return a collection of all the searchable controls on this tab.
     */
    @Override
    public Collection<SearchableControl> getSearchableControls() {
        return searchControls;
    }
}
