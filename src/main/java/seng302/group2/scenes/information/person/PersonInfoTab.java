package seng302.group2.scenes.information.person;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomInfoLabel;
import seng302.group2.scenes.control.search.*;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;


/**
 * The person information tab
 * Created by jml168 on 11/05/15.
 */
public class PersonInfoTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();

    /**
     * A tab to display information on a selected tasks.
     * @param currentPerson The person to display the information about
     */
    public PersonInfoTab(Person currentPerson) {
        // Tab settings
        this.setText("Basic Information");
        Pane basicInfoPane = new VBox(10);
        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        // Create controls
        HBox listViewHBox = new HBox(10);


        SearchableText title = new SearchableTitle(currentPerson.getFirstName() + " " + currentPerson.getLastName());

        VBox skillVBox = new VBox(10);
        CustomInfoLabel skill  = new CustomInfoLabel("Skills: ", "");
        SearchableListView personSkillsBox = new SearchableListView<>(currentPerson.getSkills());
        personSkillsBox.setPrefHeight(192);
        personSkillsBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        skillVBox.getChildren().addAll(skill, personSkillsBox);

        VBox taskVBox = new VBox(10);
        HBox titleAndCombo = new HBox(10);
        CustomInfoLabel taskLabel = new CustomInfoLabel("Tasks: ", "");
        ObservableList<Task.TASKSTATE> taskStates = observableArrayList(Task.TASKSTATE.values());

        CustomComboBox<Object> filterComboBox = new CustomComboBox<>("");
        filterComboBox.addToComboBox("All");

        for (Task.TASKSTATE state : taskStates) {
            filterComboBox.addToComboBox(state);
        }


        ArrayList<Story> storyList = new ArrayList<>();
        if (currentPerson.getTeam() != Global.getUnassignedTeam() && currentPerson.getTeam().getProject() != null) {
            storyList.addAll(currentPerson.getTeam().getProject().getAllStories());
        }

        ArrayList<Task> taskList = new ArrayList<>();
        ObservableList<Task> filteredList = observableArrayList();
        for (Story story : storyList) {
            for (Task task : story.getTasks()) {
                if (task.getAssignee() == currentPerson) {
                    taskList.add(task);
                }
            }
        }
        SearchableListView taskBox = new SearchableListView<>(filteredList);
        taskBox.setPrefHeight(192);
        taskBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        titleAndCombo.getChildren().addAll(taskLabel, filterComboBox);
        taskVBox.getChildren().addAll(titleAndCombo, taskBox);
        listViewHBox.getChildren().addAll(skillVBox, taskVBox);

        CustomInfoLabel shortName = new CustomInfoLabel("Short Name: ", currentPerson.getShortName());
        CustomInfoLabel emailAddress = new CustomInfoLabel("Email Address: ", currentPerson.getEmail());
        CustomInfoLabel birthDate = new CustomInfoLabel("Birth Date: ", currentPerson.getDateString());
        CustomInfoLabel desc = new CustomInfoLabel("Person Description: ", currentPerson.getDescription());
        CustomInfoLabel team = new CustomInfoLabel("Team: ", currentPerson.getTeamName());
        String roleString = currentPerson.getRole() == null ? "" : currentPerson.getRole().toString();
        CustomInfoLabel role = new CustomInfoLabel("Role: ", roleString);

        Button btnEdit = new Button("Edit");

        final Separator separator = new Separator();

        // Events
        btnEdit.setOnAction((event) -> {
                currentPerson.switchToInfoScene(true);
            });

        filterComboBox.getComboBox().valueProperty().addListener(new ChangeListener<Object>() {
                @Override
                public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                    filteredList.clear();
                    if (newValue == "All") {
                        for (Task task : taskList) {
                            filteredList.add(task);
                        }
                    }
                    else {
                        for (Task task : taskList) {
                            if (task.getState() == newValue) {
                                filteredList.add(task);
                            }
                        }
                    }
                }
            });

        filterComboBox.getComboBox().setValue(filterComboBox.getComboBox().getItems().get(0));


        // Add items to pane & search collection
        basicInfoPane.getChildren().addAll(
                title,
                shortName,
                emailAddress,
                birthDate,
                desc,
                team,
                role,
                separator,
                listViewHBox,
                btnEdit);

        Collections.addAll(searchControls,
                title,
                shortName,
                emailAddress,
                birthDate,
                desc,
                team,
                role,
                skill,
                personSkillsBox,
                taskBox,
                taskLabel
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

    /**
     * Gets the string representation of the current Tab
     * @return The String value
     */
    @Override
    public String toString() {
        return "Person Info Tab";
    }
}
