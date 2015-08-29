package seng302.group2.scenes.information.project.story;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
import seng302.group2.scenes.information.project.story.task.LoggingEffortPane;
import seng302.group2.scenes.validation.ValidationStyle;
import seng302.group2.util.conversion.DurationConverter;
import seng302.group2.util.validation.DateValidator;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.acceptanceCriteria.AcceptanceCriteria;
import seng302.group2.workspace.project.story.tasks.Task;
import seng302.group2.workspace.team.Team;

import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * Created by cvs20 on 5/08/15.
 */
public class StoryTaskTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();
    static Boolean correctShortName = Boolean.FALSE;
    static Boolean correctEffortLeft = Boolean.FALSE;

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
        taskTable.setPrefWidth(700);
        taskTable.setPrefHeight(200);
        taskTable.setPlaceholder(new SearchableText("There are currently no tasks in this story.", searchControls));
        taskTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        PopOver acPopover = new PopOver();
        acPopover.setDetachedTitle(currentStory.getShortName() + " - Acceptance Criteria");
        VBox acContent = new VBox();
        acContent.setPadding(new Insets(8, 8, 8, 8));
        if (currentStory.getAcceptanceCriteria().size() == 0) {
            SearchableText noAcLabel = new SearchableText("This story has no Acceptance Criteria.", searchControls);
            acContent.getChildren().add(noAcLabel);
        }
        else {
            SearchableListView<AcceptanceCriteria> acListView =
                    new SearchableListView<>(currentStory.getAcceptanceCriteria());
            ScrollPane acWrapper = new ScrollPane();
            acListView.setPrefSize(750, 250);
            acWrapper.setContent(acListView);
            acContent.getChildren().add(acWrapper);
        }

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

        TableColumn stateCol = new TableColumn("State");
        stateCol.setCellValueFactory(new PropertyValueFactory<Task, String>("state"));
        stateCol.setEditable(true);
        stateCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        ObservableList<Task.TASKSTATE> states = observableArrayList();
        states.addAll(Task.TASKSTATE.values());
        Callback<TableColumn, TableCell> stateCellFactory = col -> new ComboBoxEditingCell(states);
        stateCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Task, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Task,
                            String> task) {
                        SimpleStringProperty property = new SimpleStringProperty();
                        property.setValue(task.getValue().getState().toString());
                        return property;
                    }
                });

        stateCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Task, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Task, String> event) {
                        if (!event.getNewValue().isEmpty()) {
                            Task currentTask = event.getTableView().getItems()
                                    .get(event.getTablePosition().getRow());
                            Task.TASKSTATE newState = null;

                            for (Object state : states) {
                                if (state.toString() == event.getNewValue()) {
                                    newState = (Task.TASKSTATE) state;
                                }
                            }
                            if (newState.toString() != event.getOldValue()) {
                                currentTask.edit(currentTask.getShortName(), currentTask.getDescription(),
                                    currentTask.getDescription(), newState, currentTask.getAssignee(),
                                    currentTask.getLogs(), currentTask.getEffortLeft(),
                                    currentTask.getEffortSpent());
                            }
                        }
                    }
                });

        stateCol.setCellFactory(stateCellFactory);

        TableColumn assigneesCol = new TableColumn("Assignee");
//        assigneesCol.setCellValueFactory(new PropertyValueFactory<Task,
//                Person>("assignee"));
        assigneesCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        assigneesCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Task, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Task, String> task) {
                SimpleStringProperty prop = new SimpleStringProperty();
                prop.set(task.getValue().getShortName());
                return prop;
            }
        });

        Callback<TableColumn, TableCell> assigneeCellFactory = col -> new AssigneeCell(currentStory);
        assigneesCol.setCellFactory(assigneeCellFactory);

        TableColumn leftCol = new TableColumn("Effort Left");
        leftCol.setCellValueFactory(new PropertyValueFactory<Task, String>("effortLeftString"));
        leftCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

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
                    VBox taskInfo = new VBox();

                    taskInfo.setBorder(null);
                    taskInfo.setPadding(new Insets(25, 25, 25, 25));
                    this.setContent(wrapper);

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

                    taskInfo.getChildren().addAll(
                            title,
                            description,
                            impediments,
                            effortLeft,
                            effortSpent,
                            taskState,
                            assignedPerson
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

        Button btnAdd = new Button("Add");
        btnAdd.setDisable(true);
        addTaskBox.getChildren().addAll(task, shortNameCustomField, effortLeftField, btnAdd);

        shortNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctShortName = validateShortName(shortNameCustomField, null);
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
     * *A subclass of TableCell to bind combo box to the cell
     * to allow for editing
     */
    class ComboBoxEditingCell extends TableCell<Object, String> {
        private ComboBox<Object> comboBox;
        private ObservableList items;

        /**
         * Constructor
         * @param itemList items to populate the combo box with
         */
        private ComboBoxEditingCell(ObservableList itemList) {
            this.items = itemList;
        }

        /**
         * Sets the cell to a combo box when focused on.
         */
        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createCombo();
                setGraphic(comboBox);

                if (!getText().isEmpty()) {
                    comboBox.setValue(getType());
                }
                else {
                    comboBox.setValue(null);
                }
                Platform.runLater(() -> {
                        comboBox.requestFocus();
                    });
            }
        }

        /**
         * Resets the cell to a label on cancel
         */
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setGraphic(null);
        }

        /**
         * Updates the item
         * @param item the item to update to
         * @param empty
         */
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            }
            else {
                if (isEditing()) {
                    if (comboBox != null) {
                        comboBox.setValue(getType());
                    }
                    setText(getItem());
                    setGraphic(comboBox);
                }
                else {
                    setText(getItem());
                    setGraphic(null);
                }
            }
        }

        /**
         * Gets the selected item
         * @return the selected item as a class instance
         */
        private Object getType() {
            Object selected = null;
            for (Object saharaItem : items) {
                if (saharaItem.toString() == getItem()) {
                    selected = saharaItem;
                }
            }
            return selected;
        }

        /**
         * Creates the combo box and populates it with the itemList. Updates the value in the cell.
         */
        private void createCombo() {
            comboBox = new ComboBox<Object>(this.items);
            comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            comboBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0,
                                    Boolean arg1, Boolean arg2) {
                    if (!arg2) {
                        if (comboBox.getValue() != null) {
                            commitEdit(comboBox.getValue().toString());
                        }
                        else {
                            commitEdit("");
                        }
                    }
                    else {
                        updateItem(getItem(), false);
                    }
                }
            });
        }
    }

    class AssigneeCell extends TableCell<Object, String> {
        public Node popUp;
        public Story story;
        /**
         * Constructor
         */
        private AssigneeCell(Story story) {
            this.story = story;
        }

        /**
         * Updates the item
         *
         * @param item  the item to update to
         * @param empty
         */
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            }
            else {
                this.popUp = createAssigneeNode(getTask());
                setGraphic(popUp);
            }
        }

        public Task getTask() {
            Task result = null;
            for (Task task : this.story.getTasks()) {
                if (task.getShortName() == getItem()) {
                    result = task;
                }
            }
            return result;
        }

    }

    class ImpedimentsCell extends TableCell<Object, String> {
        public Node popUp;
        public Story story;

        /**
         * Constructor
         */
        private ImpedimentsCell(Story story) {
            this.story = story;
        }

        /**
         * Updates the item
         *
         * @param item  the item to update to
         * @param empty
         */
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            }
            else {
                this.popUp = createImpedimentsNode(getTask());
                setGraphic(popUp);
            }
        }

        public Task getTask() {
            Task result = null;
            for (Task task : this.story.getTasks()) {
                if (task.getShortName() == getItem()) {
                    result = task;
                }
            }
            return result;
        }
    }


    private Node createImpedimentsNode(Task task) {
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

//        warningImage.setOnMouseEntered(me -> {
//            this.getScene().setCursor(Cursor.HAND); //Change cursor to hand
//        });
//        warningImage.setOnMouseExited(me -> {
//            this.getScene().setCursor(Cursor.DEFAULT); //Change cursor to hand
//        });


        PopOver impedimentPopOver = new PopOver();
        impedimentPopOver.setDetachedTitle(task.getShortName() + "'s Impediments");
        SortedSet<Task.TASKSTATE> availableStatuses = new TreeSet<>();
        try {
            for (Task.TASKSTATE state : Task.getImpedingStates()) {
                availableStatuses.add(state);
            }
        }
        catch (NullPointerException ex) {
        }

        // Leave the combobox without a type so we can add a blank '(none)' without having to create a new task state
        ComboBox impedimentCombo = new ComboBox<>();
        impedimentCombo.getItems().add("(none)");
        impedimentCombo.getItems().addAll(availableStatuses);
        // Make default selection
        if (impedimentCombo.getItems().contains(task.getState())) {
            impedimentCombo.getSelectionModel().select(task.getState());
        }
        else {
            impedimentCombo.getSelectionModel().select("(none)");
        }
        warningImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (impedimentPopOver.isShowing()) {
                    impedimentPopOver.hide();
                }
                else {
                    impedimentPopOver.show(warningImage);
                }
                event.consume();
            });

        VBox impedimentsVBox = new VBox(4);
        SearchableText impedimentsLabel = new SearchableText("Impediments: ");
        TextArea impedimentsTextArea = new TextArea(task.getImpediments());
        impedimentsTextArea.setPrefSize(240, 80);
        impedimentsVBox.getChildren().addAll(impedimentsLabel, impedimentsTextArea);

        Button impedimentSaveButton = new Button("Save Impediments");
        impedimentSaveButton.setAlignment(Pos.CENTER_RIGHT);
        impedimentSaveButton.setOnAction(event -> {
                Task.TASKSTATE selectedState = null;
                Object selectedObject = impedimentCombo.getSelectionModel().getSelectedItem();
                if (selectedObject == null || !selectedObject.toString().equals("(none)")) {
                    selectedState = (Task.TASKSTATE) selectedObject;
                }
                task.editImpedimentState(selectedState, impedimentsTextArea.getText());
                impedimentPopOver.hide();

        //            this.getChildren().clear();
        //            this.getChildren().add(construct());
            });

        SearchableText statusLabel = new SearchableText("Status: ");
        statusLabel.setTextAlignment(TextAlignment.LEFT);
        HBox impedimentComboLabel = new HBox(8);
        impedimentComboLabel.setAlignment(Pos.CENTER_RIGHT);
        impedimentComboLabel.getChildren().addAll(
                statusLabel,
                impedimentCombo
        );


        VBox impedimentChangeNode = new VBox(8);
        impedimentChangeNode.setAlignment(Pos.CENTER_RIGHT);
        impedimentChangeNode.setPadding(new Insets(8, 8, 8, 8));
        impedimentChangeNode.getChildren().addAll(
                impedimentComboLabel,
                impedimentsVBox,
                impedimentSaveButton
        );
        impedimentPopOver.setContentNode(impedimentChangeNode);

        return warningImage;
    }

    private Node createAssigneeNode(Task task) {
        // Assignee icon
        ImageView assigneeImage;
        System.out.println("in");
        if (task.getAssignee() != null) {
            assigneeImage = new ImageView("icons/person.png");
            seng302.group2.scenes.control.Tooltip.create(task.getAssignee().getFullName(), assigneeImage, 50);
        }
        else {
            assigneeImage = new ImageView("icons/person_empty.png");
            seng302.group2.scenes.control.Tooltip.create("This task is unassigned", assigneeImage, 50);
        }

//        assigneeImage.setOnMouseEntered(me -> {
//            this.getScene().setCursor(Cursor.HAND); //Change cursor to hand
//        });
//        assigneeImage.setOnMouseExited(me -> {
//            this.getScene().setCursor(Cursor.DEFAULT); //Change cursor to hand
//        });

        PopOver assignPopOver = new PopOver();
        assignPopOver.setDetachedTitle(task.getShortName() + " Assignment");
        SortedSet<Person> availableAssignees = new TreeSet<>();
        try {
            for (Team team : task.getStory().getProject().getCurrentTeams()) {
                availableAssignees.addAll(team.getPeople());
            }
        }
        catch (NullPointerException ex) {
        }
        Person nonePerson = new Person();
        nonePerson.setShortName("(none)");
        availableAssignees.add(nonePerson);

        ComboBox<Person> assigneeCombo = new ComboBox<>();
        assigneeCombo.getItems().addAll(availableAssignees);
        assigneeCombo.getSelectionModel().select(task.getAssignee());
        assigneeImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (assignPopOver.isShowing()) {
                    assignPopOver.hide();
                }
                else {
                    assignPopOver.show(assigneeImage);
                }
                event.consume();
            });

        Button assigneeSaveButton = new Button("Save Assignee");
        assigneeSaveButton.setAlignment(Pos.CENTER_RIGHT);
        assigneeSaveButton.setOnAction(event -> {
                Person selectedPerson = assigneeCombo.getSelectionModel().getSelectedItem();
                if (selectedPerson.equals(nonePerson)) {
                    selectedPerson = null;
                }
                task.editAssignee(selectedPerson);
                assignPopOver.hide();

                //            this.getChildren().clear();
                //            this.getChildren().add(construct());
            });

        SearchableText assigneeLabel = new SearchableText("Assignee: ");
        assigneeLabel.setTextAlignment(TextAlignment.LEFT);
        HBox assigneeComboLabel = new HBox(8);
        assigneeComboLabel.setAlignment(Pos.CENTER_RIGHT);
        assigneeComboLabel.getChildren().addAll(
                assigneeLabel,
                assigneeCombo
        );
        VBox assigneeChangeNode = new VBox(8);
        assigneeChangeNode.setAlignment(Pos.CENTER_RIGHT);
        assigneeChangeNode.setPadding(new Insets(8, 8, 8, 8));
        assigneeChangeNode.getChildren().addAll(
                assigneeComboLabel,
                assigneeSaveButton
        );
        assignPopOver.setContentNode(assigneeChangeNode);

        return assigneeImage;
    }

}
