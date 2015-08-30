package seng302.group2.scenes.control;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;
import seng302.group2.App;
import seng302.group2.scenes.control.search.*;
import seng302.group2.scenes.information.project.story.task.LoggingEffortPane;
import seng302.group2.util.validation.ShortNameValidator;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.story.tasks.Task;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by crw73 on 26/08/15.
 */
public class PopOverTable<T> extends SearchableTable<T> {


    /**
     * Basic constructor.
     */
    public PopOverTable() {
        super();
        updateRows();
    }

    /**
     * Highlights a row if a matching query is found within that row. If there is no matching query,
     * the row's style is set to null (default).
     */
    private void updateRows() {
        setRowFactory(tv -> new TableRow<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {

                super.updateItem(item, empty);
                HBox content = new HBox();
                PopOver taskPopover = new PopOver();

                VBox taskContent = new VBox();
                taskContent.setPadding(new Insets(8, 8, 8, 8));
                if (item != null) {

                    if (item == null) {
                        SearchableText noTaskLabel = new SearchableText("No tasks selected.");
                        taskContent.getChildren().add(noTaskLabel);
                    }
                    else {
                        Task currentTask = (Task) item;

                        taskPopover.setDetachedTitle(currentTask.toString());
                        taskInfoPane(currentTask, taskContent);

                        ScrollPane taskWrapper = new ScrollPane();
                        LoggingEffortPane loggingPane = new LoggingEffortPane((Task) item,
                                taskPopover);
                        loggingPane.setStyle(null);

                        taskWrapper.setContent(loggingPane);

                        TitledPane collapsableLoggingPane = new TitledPane("Task Logging", taskWrapper);
                        collapsableLoggingPane.setExpanded(true);
                        collapsableLoggingPane.setAnimated(true);

                        taskContent.getChildren().add(collapsableLoggingPane);
                        taskWrapper.setStyle(" -fx-background: -fx-control-inner-background ;\n"
                                + "  -fx-background-color: -fx-table-cell-border-color, -fx-background ;\n" );
                    }

                    taskPopover.setContentNode(taskContent);
                    if (matchingItems.contains(item)) {
                        setStyle("-fx-background-color: " + SearchableControl.highlightColourString + "; ");
                    }
                    else {
                        setStyle(null);
                        //setStyle("-fx-background-color: " + Color.TRANSPARENT + ";");
                    }
                    this.setOnMouseClicked(event -> {

                            if (taskPopover.isShowing()) {
                                taskPopover.hide();
                            }
                            else {
                                if (event.getClickCount() == 2) {
                                    taskPopover.show(this);
                                }
                            }
                            event.consume();
                        });
                }

                this.getChildren().add(content);
            }
        });
    }

    private void taskInfoPane(Task currentTask, VBox taskContent) {
        if (!taskContent.getChildren().isEmpty()) {
            taskContent.getChildren().remove(0);
        }
        VBox taskInfo = new VBox();

        taskInfo.setBorder(null);
        taskInfo.setPadding(new Insets(25, 25, 25, 25));

        SearchableText title = new SearchableTitle(currentTask.getShortName());
        SearchableText description = new SearchableText("Task Description: "
                + currentTask.getDescription());
        SearchableText impediments = new SearchableText("Impediments: " + currentTask.getImpediments());
        SearchableText effortLeft = new SearchableText("Effort Left: " + currentTask.getEffortLeftString());
        SearchableText effortSpent = new SearchableText("Effort Spent: "
                + currentTask.getEffortSpentString());
        SearchableText taskState = new SearchableText("Task State: " + currentTask.getState());
        SearchableText assignedPerson;
        if (currentTask.getAssignee() == null) {
            assignedPerson = new SearchableText("Assigned Person: ");
        }
        else {
            assignedPerson = new SearchableText("Assigned Person: " + currentTask.getAssignee());
        }

        Button btnEdit = new Button("Edit");



        taskInfo.getChildren().addAll(
            title,
            description,
            impediments,
            effortLeft,
            effortSpent,
            taskState,
            assignedPerson,
            btnEdit
        );

//        Collections.addAll(searchControls,
//                title,
//                description,
//                impediments,
//                effortLeft,
//                effortSpent,
//                taskState,
//                assignedPerson
//        );

        taskInfo.setStyle(" -fx-background: -fx-control-inner-background ;\n"
                + "  -fx-background-color: -fx-table-cell-border-color, -fx-background ;\n");



        TitledPane collapsableInfoPane = new TitledPane("Task Info", taskInfo);
        collapsableInfoPane.setPrefHeight(30);
        collapsableInfoPane.setExpanded(true);
        collapsableInfoPane.setAnimated(true);


        taskContent.getChildren().add(0, collapsableInfoPane);
        btnEdit.setOnAction((event) -> {
                taskEditPane(currentTask, taskContent);
            });
    }

    private void taskEditPane(Task currentTask, VBox taskContent) {
        taskContent.getChildren().remove(0);

        VBox taskInfo = new VBox();
        taskInfo.setBorder(null);
        taskInfo.setPadding(new Insets(25, 25, 25, 25));


        Button btnCancel = new Button("Cancel");
        Button btnDone = new Button("Done");

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
        taskstateObservableList.addAll(Task.TASKSTATE.values());


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
        effortLeftField.setText(Double.toString(currentTask.getEffortLeft()));


        CustomComboBox<Person> taskAssigneesList = new CustomComboBox<Person>("Assignee: ");
        taskAssigneesList.addToComboBox(null);
        if (currentTask.getStory() != null && currentTask.getStory().getSprint() != null) {
            taskAssigneesList.getComboBox().setItems(currentTask.getStory().getSprint().getTeam().getPeople());
        }
        taskAssigneesList.setValue(currentTask.getAssignee());

        //Adding to MainPane
        taskInfo.getChildren().addAll(shortNameCustomField,
            descriptionTextArea,
            impedimentsTextArea,
            effortLeftField,
            taskHbox,
            taskAssigneesList,
            buttons
        );



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
                        Double.toString(currentTask.getEffortLeft()));
                //TODO deal with null assignment in the combobox
//                boolean assigneesUnchanged = taskAssigneesList.getValue().equals((currentTask.getAssignee()));


                if (shortNameUnchanged && descriptionUnchanged
                        && impedimentsUnchanged && taskstateUnchanged && effortLeftUnchanged) {
                    // No changes
                    taskInfoPane(currentTask, taskContent);
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
                            taskAssigneesList.getValue(), currentTask.getLogs(),
                            Double.parseDouble(effortLeftField.getText()), currentTask.getEffortSpent());


                }
                else {
                    event.consume();
                }

            });

        taskInfo.setStyle(" -fx-background: -fx-control-inner-background ;\n"
                + "  -fx-background-color: -fx-table-cell-border-color, -fx-background ;\n");

        btnCancel.setOnAction((event) -> {
                taskInfoPane(currentTask, taskContent);
            });

        TitledPane collapsableInfoPane = new TitledPane("Task Info", taskInfo);
        collapsableInfoPane.setPrefHeight(30);
        collapsableInfoPane.setExpanded(true);
        collapsableInfoPane.setAnimated(true);

        taskContent.getChildren().add(0, collapsableInfoPane);
    }
}
