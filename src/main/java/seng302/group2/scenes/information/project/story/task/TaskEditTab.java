package seng302.group2.scenes.information.project.story.task;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.control.SearchableListView;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.util.validation.ShortNameValidator;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.sprint.Sprint;
import seng302.group2.workspace.project.story.tasks.Log;
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
        Button btnSave = new Button("Done");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.TOP_LEFT);
        buttons.getChildren().addAll(btnSave, btnCancel);

        RequiredField shortNameCustomField = new RequiredField("Task Name:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Task Description:", 300);
        CustomTextArea impedimentsTextArea = new CustomTextArea("Task Impediments:", 300);
        CustomTextArea effortLeftField = new CustomTextArea("Effort Left:", 300);


        HBox taskHbox = new HBox();
        SearchableText taskStateText = new SearchableText("Task State: ");
        ObservableList<Task.TASKSTATE> taskstateObservableList = observableArrayList();
        taskstateObservableList.addAll(Task.TASKSTATE.BLOCKED, Task.TASKSTATE.DEFERRED, Task.TASKSTATE.DONE,
                Task.TASKSTATE.IN_PROGRESS, Task.TASKSTATE.NOT_STARTED, Task.TASKSTATE.PENDING, Task.TASKSTATE.READY);

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


        ObservableList<Person> taskAssigneesList = observableArrayList();
        taskAssigneesList.addAll(currentTask.getResponsibilities());

        ObservableList<Person> availablePeopleList = observableArrayList();
        if (currentTask.getStory().getSprint() != null) {
            availablePeopleList.addAll(currentTask.getStory().getSprint().getTeam().getPeople());
        }

        availablePeopleList.removeAll(taskAssigneesList);

        //Assignees assign buttons
        Button btnAssign = new Button("<");
        Button btnUnassign = new Button(">");
        VBox assignmentButtons = new VBox();
        assignmentButtons.spacingProperty().setValue(10);
        assignmentButtons.getChildren().addAll(btnAssign, btnUnassign);
        assignmentButtons.setAlignment(Pos.CENTER);

        // List views
        SearchableListView<Person> taskAssigneesListView = new SearchableListView<>(taskAssigneesList, searchControls);
        taskAssigneesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        taskAssigneesListView.getSelectionModel().select(0);

        SearchableListView<Person> availablePeopleListView = new SearchableListView<>(availablePeopleList,
                searchControls);
        availablePeopleListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        availablePeopleListView.getSelectionModel().select(0);

        VBox teamMembersBox = new VBox(10);
        teamMembersBox.getChildren().add(new SearchableText("Team Members: ", searchControls));
        teamMembersBox.getChildren().add(taskAssigneesListView);

        VBox availablePeopleBox = new VBox(10);
        availablePeopleBox.getChildren().add(new SearchableText("Available People: ", searchControls));
        availablePeopleBox.getChildren().add(availablePeopleListView);

        HBox memberListViews = new HBox(10);
        memberListViews.getChildren().addAll(teamMembersBox, assignmentButtons, availablePeopleBox);
        memberListViews.setPrefHeight(192);

        //Adding to MainPane
        editPane.getChildren().addAll(shortNameCustomField,
                descriptionTextArea,
                impedimentsTextArea,
                effortLeftField,
                taskHbox,
                memberListViews,
                buttons);

        btnSave.setOnAction((event) -> {
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


                if (shortNameUnchanged &&  descriptionUnchanged
                        && impedimentsUnchanged && taskstateUnchanged && effortLeftUnchanged) {
                    // No changes
                    currentTask.switchToInfoScene();
                    return;
                }

                boolean correctShortName = ShortNameValidator.validateShortName(shortNameCustomField,
                        currentTask.getShortName());
                System.out.println(taskStateComboBox.getValue());

                if (correctShortName) {
    //                    Valid short name, make the edit
                    currentTask.edit(shortNameCustomField.getText(),
                            descriptionTextArea.getText(),
                            impedimentsTextArea.getText(),
                            taskStateComboBox.getValue(),
                            new ArrayList<Person>(), new ArrayList<Log>(),
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