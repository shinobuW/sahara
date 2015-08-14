package seng302.group2.scenes.information.project.story.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.CustomTextField;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.util.validation.ShortNameValidator;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.story.tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * A class for displaying a tab used to edit people.
 * Created by btm38 on 30/07/15.
 */
public class TaskEditTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();

    Button btnDone;

    /**
     * Constructor for the PersonEditTab class. This constructor creates a JavaFX ScrollPane
     * which is populated with relevant controls then shown.
     *
     * @param currentTask The person being edited
     */
    public TaskEditTab(Task currentTask) {
        this.setText("Edit Task");
        Pane editPane = new VBox(10);
        editPane.setBorder(null);
        editPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(editPane);
        this.setContent(wrapper);


        Button btnCancel = new Button("Cancel");
        btnDone = new Button("Done");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.TOP_LEFT);
        buttons.getChildren().addAll(btnDone, btnCancel);

        RequiredField shortNameCustomField = new RequiredField("Task Name:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Task Description:", 300);
        CustomTextArea impedimentsTextArea = new CustomTextArea("Task Impediments:", 300);
        CustomTextField effortLeftField = new CustomTextField("Effort Left:");

        effortLeftField.setPrefWidth(300);

        HBox taskHbox = new HBox();
        SearchableText taskStateText = new SearchableText("Task State: ");
        ObservableList<Task.TASKSTATE> taskstateObservableList = observableArrayList();
        taskstateObservableList.addAll(Task.TASKSTATE.BLOCKED, Task.TASKSTATE.DEFERRED, Task.TASKSTATE.DONE,
                Task.TASKSTATE.IN_PROGRESS, Task.TASKSTATE.NOT_STARTED);

        ComboBox<Task.TASKSTATE> taskStateComboBox = new ComboBox<>(taskstateObservableList);
        taskStateComboBox.setValue(currentTask.getState());
        taskHbox.getChildren().addAll(taskStateText, taskStateComboBox);

        shortNameCustomField.setMaxWidth(275);
        descriptionTextArea.setMaxWidth(275);
        impedimentsTextArea.setMaxWidth(275);
        effortLeftField.setMaxWidth(275);

        shortNameCustomField.setText(currentTask.getShortName());
        descriptionTextArea.setText(currentTask.getDescription());
        impedimentsTextArea.setText(currentTask.getImpediments());
        effortLeftField.setText(currentTask.getEffortLeft().toString());


        //Assignees assign buttons
        Button btnAssign = new Button("<");
        Button btnUnassign = new Button(">");
        VBox assignmentButtons = new VBox();
        assignmentButtons.spacingProperty().setValue(10);
        assignmentButtons.getChildren().addAll(btnAssign, btnUnassign);
        assignmentButtons.setAlignment(Pos.CENTER);

        // Story list view setup
        ObservableList<Person> taskAssigneesList = FXCollections.observableArrayList();
        ObservableList<Person> availablePeopleList = FXCollections.observableArrayList();
        ListView<Person> taskAssigneesListView = new ListView<>();
        ListView<Person> availablePeopleListView = new ListView<>();
        taskAssigneesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        availablePeopleListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        VBox inTaskVBox = new VBox();
        VBox availableVBox = new VBox();
        inTaskVBox.getChildren().addAll(new SearchableText("Sprint Stories: ", searchControls), taskAssigneesListView);
        availableVBox.getChildren().addAll(new SearchableText("Available Stories: ", searchControls),
                availablePeopleListView);
        HBox peopleHBox = new HBox(10);
        peopleHBox.setPrefHeight(192);
        peopleHBox.getChildren().addAll(inTaskVBox, assignmentButtons, availableVBox);

        taskAssigneesListView.setItems(taskAssigneesList);
        availablePeopleListView.setItems(availablePeopleList);

        taskAssigneesList.addAll(currentTask.getResponsibilities());

        if (currentTask.getStory().getSprint() != null) {
            availablePeopleList.addAll(currentTask.getStory().getSprint().getTeam().getPeople());
        }

        availablePeopleList.removeAll(taskAssigneesList);


        //Adding to MainPane
        editPane.getChildren().addAll(shortNameCustomField,
                descriptionTextArea,
                impedimentsTextArea,
                effortLeftField,
                taskHbox,
                peopleHBox,
                buttons);

        // Button events
        btnAssign.setOnAction((event) -> {
                taskAssigneesList.addAll(
                        availablePeopleListView.getSelectionModel().getSelectedItems());
                availablePeopleList.removeAll(
                        availablePeopleListView.getSelectionModel().getSelectedItems());
                System.out.println(taskAssigneesList);
            });

        btnUnassign.setOnAction((event) -> {
                Collection<Person> selectedPeople = new ArrayList<>();
                selectedPeople.addAll(taskAssigneesListView.getSelectionModel().
                        getSelectedItems());
                availablePeopleList.addAll(selectedPeople);
                taskAssigneesList.removeAll(selectedPeople);
            });

        btnDone.setOnAction((event) -> {
                boolean shortNameUnchanged = shortNameCustomField.getText().equals(
                        currentTask.getShortName());

                boolean descriptionUnchanged = descriptionTextArea.getText().equals(
                        currentTask.getDescription());

                boolean impedimentsUnchanged = impedimentsTextArea.getText().equals(
                        currentTask.getImpediments());

                boolean taskstateUnchanged = taskStateComboBox.getValue().equals(
                        currentTask.getState());

                boolean effortLeftUnchanged = effortLeftField.getText().equals(
                        currentTask.getEffortLeft().toString());
                boolean assigneesUnchanged = taskAssigneesList.equals((currentTask.getResponsibilities()));

                if (shortNameUnchanged && descriptionUnchanged
                        && impedimentsUnchanged && taskstateUnchanged && effortLeftUnchanged
                        && assigneesUnchanged) {
                    // No changes
                    currentTask.switchToInfoScene();
                    return;
                }

                boolean correctShortName = ShortNameValidator.validateShortName(shortNameCustomField,
                        currentTask.getShortName());

                if (correctShortName) {
                    //                    Valid short name, make the edit

                    currentTask.edit(shortNameCustomField.getText(),
                            descriptionTextArea.getText(),
                            impedimentsTextArea.getText(),
                            taskStateComboBox.getValue(),
                            taskAssigneesList, currentTask.getLogs(),
                            new Integer(effortLeftField.getText()), currentTask.getEffortSpent());

                    currentTask.switchToInfoScene();
                    App.mainPane.refreshTree();
                    System.out.println(currentTask.getState());
                }
                else {
                    event.consume();
                }

            });

        btnCancel.setOnAction((event) -> {
                currentTask.switchToInfoScene();
            });
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