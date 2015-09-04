package seng302.group2.scenes.information.project.story;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import org.controlsfx.control.PopOver;
import seng302.group2.App;
import seng302.group2.scenes.control.*;
import seng302.group2.scenes.control.Tooltip;
import seng302.group2.scenes.control.search.*;
import seng302.group2.scenes.information.project.story.task.AssigneeTableCell;
import seng302.group2.scenes.information.project.story.task.LoggingEffortPane;
import seng302.group2.scenes.validation.ValidationStyle;
import seng302.group2.util.conversion.DurationConverter;
import seng302.group2.util.validation.DateValidator;
import seng302.group2.util.validation.ShortNameValidator;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.acceptanceCriteria.AcceptanceCriteria;
import seng302.group2.workspace.project.story.tasks.Task;
import seng302.group2.workspace.team.Team;

import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * A tab on the story scene which shows the all the stories tasks and their status.
 * Created by cvs20 on 5/08/15.
 */
public class StoryTaskTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();
    static Boolean correctShortName = Boolean.FALSE;
    static Boolean correctEffortLeft = Boolean.FALSE;
    static Boolean assigneeSelected = Boolean.FALSE;
    private VBox taskInfo;

    /**
     * Constructor for the Story Task Tab
     *
     * @param currentStory The currently selected Story
     */
    public StoryTaskTab(Story currentStory) {

        this.setText("Tasks");
        correctShortName = false;
        correctEffortLeft = false;

        Pane basicInfoPane = new VBox(10);

        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        PopOverTable<Task> taskTable = new PopOverTable<>();
        taskTable.setEditable(true);
        taskTable.setPrefWidth(1200);
        taskTable.setPrefHeight(200);
        taskTable.setPlaceholder(new SearchableText("There are currently no tasks in this story.", searchControls));
        taskTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        PopOver acPopover = new PopOver();
        acPopover.setDetachedTitle(currentStory.getShortName() + " - Acceptance Criteria");
        VBox acContent = new VBox();
        acContent.setAlignment(Pos.CENTER);
        acContent.setPadding(new Insets(8, 8, 8, 8));

        SearchableListView<AcceptanceCriteria> acListView = new SearchableListView<>(
                currentStory.getAcceptanceCriteria());
        ScrollPane acWrapper = new ScrollPane();
        acListView.setPrefSize(640, 250);
        acListView.setPlaceholder(new SearchableText("No Acceptance Criteria", searchControls));
        acWrapper.setContent(acListView);
        acContent.getChildren().add(acWrapper);

        acPopover.setContentNode(acContent);


        Button acButton = new Button("View Acceptance Criteria");

        acButton.setOnAction((event) -> {
                acPopover.show(acButton);
            });


        SearchableTitle tasksTitle = new SearchableTitle("Tasks Table: ");

        ObservableList<Task> data = currentStory.getTasks();

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Task, String>("shortName"));
        nameCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Task, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Task, String> event) {
                        Task selectedTask = event.getTableView().getItems().get(
                                event.getTablePosition().getRow());
                        if (!event.getNewValue().isEmpty() && event.getNewValue() != null) {
                            selectedTask.edit(event.getNewValue(), selectedTask.getDescription(),
                                    selectedTask.getImpediments(), selectedTask.getState(), selectedTask.getAssignee(),
                                    selectedTask.getLogs(),
                                    selectedTask.getEffortLeft(),
                                    selectedTask.getEffortSpent());
                        }
                    }
                }
        );

        TableColumn stateCol = new TableColumn("State");
        stateCol.setCellValueFactory(new PropertyValueFactory<Task, String>("state"));
        stateCol.setEditable(true);
        stateCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        ObservableList<Task.TASKSTATE> states = observableArrayList();
        states.addAll(Task.TASKSTATE.values());
        stateCol.setCellFactory(ComboBoxTableCell.forTableColumn(
                states
        ));

        stateCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Task, Task.TASKSTATE>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Task,
                            Task.TASKSTATE> event) {
                        Task currentTask = event.getTableView().getItems().get(
                                event.getTablePosition().getRow());
                        currentTask.editImpedimentState(event.getNewValue(), currentTask.getImpediments());
                    }
                }
        );


        TableColumn assigneesCol = new TableColumn("Assignee");
        assigneesCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        ObservableList<Person> availablePeople = FXCollections.observableArrayList();
        Set<Team> availableTeam = currentStory.getProject().getCurrentTeams();
        for (Team team : availableTeam) {
            availablePeople.addAll(team.getPeople());
        }

        assigneesCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Task, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Task, String> task) {
                SimpleStringProperty prop = new SimpleStringProperty();
                prop.set(task.getValue().getShortName());
                return prop;
            }
        });

        assigneesCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Task, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Task, String> event) {
                        Task selectedTask = event.getTableView().getItems().get(
                             event.getTablePosition().getRow());
                        Person selectedPerson = null;

                        for (Person person : availablePeople) {
                            if (event.getNewValue() == person.getShortName()) {
                                selectedPerson = person;
                            }
                        }
                        if (event.getNewValue() != null && event.getNewValue() != null) {
                            selectedTask.editAssignee(selectedPerson);
                        }
                    }
                });



        Callback<TableColumn, TableCell> assigneeCellFactory = col -> new AssigneeTableCell(currentStory,
                availablePeople);
        assigneesCol.setCellFactory(assigneeCellFactory);

        TableColumn leftCol = new TableColumn("Effort Left");
        leftCol.setCellValueFactory(new PropertyValueFactory<Task, String>("effortLeftString"));
        leftCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));
        leftCol.setCellFactory(TextFieldTableCell.forTableColumn());
        leftCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Task, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Task, String> event) {
                        Task selectedTask = event.getTableView().getItems().get(
                                event.getTablePosition().getRow());
                        if (!event.getNewValue().isEmpty() && event.getNewValue() != null) {
                            selectedTask.edit(selectedTask.getShortName(), selectedTask.getDescription(),
                                    selectedTask.getImpediments(), selectedTask.getState(), selectedTask.getAssignee(),
                                    selectedTask.getLogs(),
                                    DurationConverter.readDurationToMinutes(event.getNewValue()),
                                    selectedTask.getEffortSpent());
                        }
                    }
                }
        );

        TableColumn spentCol = new TableColumn("Effort Spent");
        spentCol.setCellValueFactory(new PropertyValueFactory<Task, String>("effortSpentString"));
        spentCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        TableColumn impedimentsCol = new TableColumn("Impediments");
        impedimentsCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        impedimentsCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Task, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Task, String> task) {
                SimpleStringProperty prop = new SimpleStringProperty();
                prop.set(task.getValue().getShortName());
                return prop;
            }
        });
        Callback<TableColumn, TableCell> impedimentsCellFactory = col -> new ImpedimentsCell(currentStory);
        impedimentsCol.setCellFactory(impedimentsCellFactory);

        TableColumn descriptionCol = new TableColumn("Description");
        descriptionCol.setEditable(true);
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
        descriptionCol.setPrefWidth(250);

        descriptionCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Task, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Task, String> event) {
                        (event.getTableView().getItems().get(
                                event.getTablePosition().getRow())
                        ).editDescription(event.getNewValue());
                    }
                }
        );

        taskTable.setItems(data);
        TableColumn[] columns = {nameCol, stateCol, assigneesCol, leftCol, spentCol, impedimentsCol, descriptionCol};
        taskTable.getColumns().setAll(columns);


        // Listener to disable columns being movable
        taskTable.getColumns().addListener(new ListChangeListener() {
            public boolean suspended;

            @Override
            public void onChanged(ListChangeListener.Change change) {
                change.next();
                if (change.wasReplaced() && !suspended) {
                    this.suspended = true;
                    taskTable.getColumns().setAll(columns);
                    this.suspended = false;
                }
            }
        });

        Button btnView = new Button("Task View");

        btnView.setOnAction((event) -> {
                PopOver taskPopover = new PopOver();
                VBox taskContent = new VBox();
                taskContent.setPadding(new Insets(8, 8, 8, 8));
                if (taskTable.getSelectionModel().getSelectedItem() == null) {
                    SearchableText noTaskLabel = new SearchableText("No tasks selected.", searchControls);
                    taskContent.getChildren().add(noTaskLabel);
                }
                else {
                    Task currentTask = taskTable.getSelectionModel().getSelectedItem();
                    taskPopover.setDetachedTitle(currentTask.toString());
                    taskInfoPane(currentTask);
                    
                    ScrollPane taskWrapper = new ScrollPane();
                    taskWrapper.setContent(new LoggingEffortPane(taskTable.getSelectionModel().getSelectedItem(),
                            taskPopover));
                    
                    TitledPane collapsableInfoPane = new TitledPane("Task Info", taskInfo);
                    collapsableInfoPane.setPrefHeight(30);
                    collapsableInfoPane.setExpanded(true);
                    collapsableInfoPane.setAnimated(true);
                    
                    TitledPane collapsableLoggingPane = new TitledPane("Task Logging", taskWrapper);
                    collapsableLoggingPane.setExpanded(true);
                    collapsableLoggingPane.setAnimated(true);

                    taskContent.getChildren().addAll(collapsableInfoPane, collapsableLoggingPane);
                }

                taskPopover.setContentNode(taskContent);
                taskPopover.show(btnView);
            });
      

        VBox addTaskBox = new VBox(10);
        SearchableText task = new SearchableText("Add Quick Tasks:", "-fx-font-weight: bold;");

        RequiredField shortNameCustomField = new RequiredField("Task Name:");
        RequiredField effortLeftField = new RequiredField("Effort left: ");
        CustomComboBox<Person> assigneeComboBox = new CustomComboBox<Person>("Assignee", false);

        assigneeComboBox.setDisable(true);

        if (currentStory.getProject().getCurrentTeams() != null) {
//            Set<Team> currentTeams = currentStory.getProject().getCurrentTeams();
//            ObservableList<Person> availablePeople = FXCollections.observableArrayList();
//
//            for (Team team : currentTeams) {
//                availablePeople.addAll(team.getPeople());
//            }

            if (availablePeople.size() != 0) {
                assigneeComboBox.getComboBox().getItems().addAll(availablePeople);
                assigneeComboBox.setDisable(false);
            }
            else {
                System.out.println("size is zero");
                ValidationStyle.showMessage("There are no team members in the currently allocated teams",
                        assigneeComboBox.getComboBox());
                assigneeComboBox.setDisable(true);
            }

        }
        else {
            System.out.println("current teams null");
            assigneeComboBox.setDisable(true);
            ValidationStyle.showMessage("There are currently no allocated teams on this project",
                    assigneeComboBox.getComboBox());
        }

        Button btnAdd = new Button("Add");
        btnAdd.setDisable(true);
        addTaskBox.getChildren().addAll(task, shortNameCustomField, effortLeftField, assigneeComboBox, btnAdd);

        shortNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                ValidationStyle.borderGlowNone(shortNameCustomField.getTextField());
                correctShortName = !shortNameCustomField.getText().isEmpty();
                if (!correctShortName) {
                    ValidationStyle.borderGlowRed(shortNameCustomField.getTextField());
                    ValidationStyle.showMessage("Short Name required",
                            shortNameCustomField.getTextField());
                }
                btnAdd.setDisable(!(correctShortName && correctEffortLeft));
            });

        effortLeftField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctEffortLeft = DateValidator.validDuration(newValue) && !newValue.isEmpty();
                if (correctEffortLeft) {
                    ValidationStyle.borderGlowNone(effortLeftField.getTextField());
                }
                else {
                    if (newValue.isEmpty()) {
                        ValidationStyle.borderGlowRed(effortLeftField.getTextField());
                        ValidationStyle.showMessage("This field must be filled", effortLeftField.getTextField());
                    }
                    else {
                        ValidationStyle.borderGlowRed(effortLeftField.getTextField());
                        ValidationStyle.showMessage("Please input in valid format", effortLeftField.getTextField());
                    }
                }
                btnAdd.setDisable(!(correctShortName && correctEffortLeft));
            });

        btnAdd.setOnAction((event) -> {
                //get user input
                String shortName = shortNameCustomField.getText();
                Task newTask;
                if (effortLeftField.getText().isEmpty()) {
                    newTask = new Task(shortName, " ", currentStory, null, 0);
                }
                else {
                    newTask = new Task(shortName, " ", currentStory, null,
                            DurationConverter.readDurationToMinutes(effortLeftField.getText()));
                }
                currentStory.add(newTask);
                App.refreshMainScene();
            });

        basicInfoPane.getChildren().addAll(
                tasksTitle,
                taskTable,
                btnView,
                acButton,
                addTaskBox
        );

        Collections.addAll(searchControls,
                task,
                tasksTitle,
                shortNameCustomField,
                effortLeftField,
                assigneeComboBox,
                taskTable
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
     * A cell used to show the Impediments status.
     */
    class ImpedimentsCell extends TableCell<Object, String> {
        public Node popUp;
        public Story story;

        /**
         * Constructor
         * @param story The currently selected story
         */
        private ImpedimentsCell(Story story) {
            this.story = story;
        }

        /**
         * Updates the item
         *
         * @param item  the item to update to
         * @param empty if the cell is empty
         */
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null || getTask() == null) {
                setText(null);
                setGraphic(null);
            }
            else {
                this.popUp = createImpedimentsNode(getTask(), this);
                setGraphic(popUp);
            }
        }

        public Task getTask() {
            Task result = null;
            for (Task task : this.story.getTasks()) {
                if (task.getShortName().equals(getItem())) {
                    result = task;
                }
            }
            return result;
        }
    }


    private Node createImpedimentsNode(Task task, TableCell tableCell) {
        // Impediments icon
        ImageView warningImage;
        if (Task.getImpedingStates().contains(task.getState()) || !task.getImpediments().isEmpty()) {
            warningImage = new ImageView("icons/dialog-cancel.png");
            if (task.getState() == Task.TASKSTATE.BLOCKED) {
                if (!task.getImpediments().isEmpty()) {
                    Tooltip.create("This task is currently blocked, with the following impediments:\n"
                            + task.getImpediments(), warningImage, 50);
                }
                else {
                    Tooltip.create("This task is currently blocked", warningImage, 50);
                }
            }
            else if (task.getState() == Task.TASKSTATE.DEFERRED) {
                if (!task.getImpediments().isEmpty()) {
                    //System.out.println(task.getImpediments());
                    Tooltip.create("This task has been deferred, and has the following impediments:\n"
                            + task.getImpediments(), warningImage, 50);
                }
                else {
                    Tooltip.create("This task has been deferred", warningImage, 50);
                }
            }
            else {
                Tooltip.create("This task has the following impediments:\n" + task.getImpediments(),
                        warningImage, 50);
            }
        }
        else {
            warningImage = new ImageView("icons/dialog-cancel-empty.png");
            Tooltip.create("This task has no impediments or blockages", warningImage, 50);
        }

        tableCell.setOnMouseEntered(me -> {
                tableCell.setCursor(Cursor.HAND); //Change cursor to hand
            });
        tableCell.setOnMouseExited(me -> {
                tableCell.setCursor(Cursor.DEFAULT); //Change cursor to hand
            });


        PopOver impedimentPopOver = new PopOver();
        impedimentPopOver.setDetachedTitle(task.getShortName() + "'s Impediments");
        tableCell.setOnMouseClicked(me -> {
                impedimentPopOver.show(tableCell);
            });

        VBox impedimentsVBox = new VBox(4);
        impedimentsVBox.setPadding(new Insets(8, 8, 8, 8));
        SearchableText impedimentsLabel = new SearchableText("Impediments: ");
        Label impediments = new Label(task.getImpediments());
//        impedimentsTextArea.setPrefSize(240, 80);
        impedimentsVBox.getChildren().addAll(impedimentsLabel, impediments);

        SearchableText statusLabel = new SearchableText("Status: ");
        statusLabel.setTextAlignment(TextAlignment.LEFT);
        HBox impedimentComboLabel = new HBox(8);
        impedimentComboLabel.setAlignment(Pos.CENTER_RIGHT);
        impedimentComboLabel.getChildren().addAll(
                statusLabel

        );


//        VBox impedimentsVBox = new VBox(8);
//        impedimentsVBox.setAlignment(Pos.CENTER_RIGHT);
//        impedimentsVBox.setPadding(new Insets(8, 8, 8, 8));
//        impedimentsVBox.getChildren().addAll(
//                impedimentComboLabel
//
//
//        );
        impedimentPopOver.setContentNode(impedimentsVBox);

        return warningImage;
    }

    private void taskInfoPane(Task currentTask) {
        taskInfo = new VBox();

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

        Collections.addAll(searchControls,
                title,
                description,
                impediments,
                effortLeft,
                effortSpent,
                taskState,
                assignedPerson
        );

        btnEdit.setOnAction((event) -> {
                taskEditPane(currentTask);
            });

    }

    private void taskEditPane(Task currentTask) {
        taskInfo = new VBox(10);
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


        CustomComboBox<Person> taskAssigneesList = new CustomComboBox<>("Assignee: ");
        if (currentTask.getStory() != null && currentTask.getStory().getSprint() != null) {
            taskAssigneesList.getComboBox().setItems(currentTask.getStory().getSprint().getTeam().getPeople());
        }

        //Adding to MainPane
        taskInfo.getChildren().addAll(shortNameCustomField,
                descriptionTextArea,
                impedimentsTextArea,
                effortLeftField,
                taskHbox,
                taskAssigneesList,
                buttons);



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
                boolean assigneesUnchanged = taskAssigneesList.equals((currentTask.getAssignee()));

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
                            taskAssigneesList.getValue(), currentTask.getLogs(),
                            Double.parseDouble(effortLeftField.getText()), currentTask.getEffortSpent());

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

}
