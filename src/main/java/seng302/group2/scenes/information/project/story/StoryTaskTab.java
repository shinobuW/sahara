package seng302.group2.scenes.information.project.story;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import seng302.group2.App;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.control.search.*;
import seng302.group2.scenes.information.project.story.task.TaskScene;
import seng302.group2.util.validation.DateValidator;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.acceptanceCriteria.AcceptanceCriteria;
import seng302.group2.workspace.project.story.tasks.Log;
import seng302.group2.workspace.project.story.tasks.Task;
import seng302.group2.workspace.team.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;
import static javafx.collections.FXCollections.observableList;
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

        TableView<Task> taskTable = new TableView<>();
        taskTable.setEditable(true);
        taskTable.setPrefWidth(700);
        taskTable.setPrefHeight(200);
        taskTable.setPlaceholder(new SearchableText("There are currently no tasks in this story.", searchControls));
        taskTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        VBox ACLabels = new VBox();
        SearchableTitle acTitle = new SearchableTitle("Acceptance Criteria: ");

        ACLabels.getChildren().add(acTitle);
        ACLabels.getChildren().add(new Text(""));

        for (AcceptanceCriteria ac : currentStory.getAcceptanceCriteria()) {
            SearchableText acText = new SearchableText("\t\u2022" + ac.toString());
            ACLabels.getChildren().addAll(acText);
            Collections.addAll(searchControls, acText);
        }
        ACLabels.getChildren().add(new Text(""));
        SearchableTitle tasksTitle = new SearchableTitle("Tasks Table: ");
        ACLabels.getChildren().add(tasksTitle);

        ObservableList<Task> data = currentStory.getTasks();

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Task, String>("shortName"));
        nameCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));
//
//        TextFieldEditingCell nameEditingCell = new TextFieldEditingCell();
//        Callback<TableColumn, TableCell> nameCellFactory = col -> nameEditingCell;
//        nameCol.setCellValueFactory(
//                new Callback<TableColumn.CellDataFeatures<Task, String>,
//                        ObservableValue<String>>() {
//                    @Override
//                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Task,
//                            String> task) {
//                        SimpleStringProperty property = new SimpleStringProperty();
//                        property.setValue(task.getValue().getShortName());
//                        return property;
//                    }
//                });
//
//        nameCol.setOnEditCommit(
//                new EventHandler<TableColumn.CellEditEvent<Task, String>>() {
//                    @Override
//                    public void handle(TableColumn.CellEditEvent<Task, String> event) {
//                        if (!event.getNewValue().isEmpty()) {
//                            Task currentTask = event.getTableView().getItems()
//                                    .get(event.getTablePosition().getRow());
//                           String newName = null;
//                            if (newName != event.getOldValue()) {
//                                currentTask.edit(newName, currentTask.getDescription(), currentTask.getImpediments(),
//                                        currentTask.getState(), currentTask.getAssignee(), currentTask.getLogs(),
//                                        currentTask.getEffortLeft(), currentTask.getEffortSpent());
//                            }
//                        }
//                    }
//                });
//
//        nameCol.setCellFactory(nameCellFactory);

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
        assigneesCol.setCellValueFactory(new PropertyValueFactory<Task,
                Person>("assignee"));
        assigneesCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        ObservableList availablePeople = observableArrayList();
        for (Team team : currentStory.getProject().getCurrentTeams()) {
            availablePeople.addAll(team.getPeople());
        }
        assigneesCol.setEditable(true);

        Callback<TableColumn, TableCell> assigneeCellFactory = col -> new ComboBoxEditingCell(availablePeople);
        assigneesCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Task, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Task,
                            String> task) {
                        SimpleStringProperty property = new SimpleStringProperty();
                        property.setValue(task.getValue().getAssignee().toString());
                        return property;
                    }
                });

        assigneesCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Task, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Task, String> event) {

                        if (!event.getNewValue().isEmpty()) {
                            Task currentTask = event.getTableView().getItems()
                                    .get(event.getTablePosition().getRow());
                            Person newPerson = null;

                            for (Object person: availablePeople) {
                                if (person.toString() == event.getNewValue()) {
                                    newPerson = (Person)person;
                                }
                            }
                            if (newPerson.toString() != event.getOldValue()) {
                                currentTask.editAssignee(newPerson);
                            }
                        }
                    }
                });

        assigneesCol.setCellFactory(assigneeCellFactory);


        TableColumn leftCol = new TableColumn("Effort Left");
        leftCol.setCellValueFactory(new PropertyValueFactory<Task, String>("effortLeftString"));
        leftCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        TableColumn spentCol = new TableColumn("Effort Spent");
        spentCol.setCellValueFactory(new PropertyValueFactory<Task, String>("effortSpentString"));
        spentCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        taskTable.setItems(data);
        TableColumn[] columns = {nameCol, stateCol, assigneesCol, leftCol, spentCol};
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

        Button btnView = new Button("View");

        btnView.setOnAction((event) -> {
                App.mainPane.setContent(new TaskScene(taskTable.getSelectionModel().getSelectedItem()));
            });

        VBox addTaskBox = new VBox(10);
        SearchableText task = new SearchableText("Add Quick Tasks:", "-fx-font-weight: bold;");



        RequiredField shortNameCustomField = new RequiredField("Task Name:");
        RequiredField effortLeftField = new RequiredField("Effort left: ");
        CustomComboBox<Person> assigneeField = new CustomComboBox<Person>("Assignee: ");
        CustomTextArea descriptionField = new CustomTextArea("Task Description: ");

        ObservableList<Person> personList = observableArrayList();
        Person blankPerson = new Person("", "", "", null, null, null);
        personList.add(blankPerson);
        for (Team team : currentStory.getProject().getAllTeams()) {
            personList.addAll(team.getPeople());
        }
        Collections.sort(personList);
        assigneeField.getComboBox().setItems(personList);

        Button btnAdd = new Button("Add");
        btnAdd.setDisable(true);
        addTaskBox.getChildren().addAll(task, shortNameCustomField, effortLeftField, assigneeField,
                descriptionField, btnAdd);


        shortNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctShortName = validateShortName(shortNameCustomField, null);
                btnAdd.setDisable(!(correctShortName && correctEffortLeft));
            });

        effortLeftField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctEffortLeft = DateValidator.validDuration(newValue) && !newValue.isEmpty();
                if (correctEffortLeft) {
                    effortLeftField.hideErrorField();
                }
                else {
                    if (newValue.isEmpty()) {
                        effortLeftField.showErrorField("* This field must be filled");
                    }
                    else {
                        effortLeftField.showErrorField("* Please input valid format");
                    }
                }
                btnAdd.setDisable(!(correctShortName && correctEffortLeft));
            });

        btnAdd.setOnAction((event) -> {
                //get user input
                String shortName = shortNameCustomField.getText();
                Task newTask = new Task(shortName, descriptionField.getText(), currentStory, null);
                Person assignee = null;
                if (!assigneeField.getValue().equals(blankPerson)) {
                    assignee = assigneeField.getValue();
                }

                newTask.setAssignee(assignee);
                if (effortLeftField.getText().isEmpty()) {
                    newTask.setEffortLeft((double) 0);
                }
                else {
                    newTask.setEffortLeft(Log.readDurationToMinutes(effortLeftField.getText()));
                }
                currentStory.add(newTask);
                App.refreshMainScene();
            });

        basicInfoPane.getChildren().addAll(
                ACLabels,
                taskTable,
                btnView,
                addTaskBox
        );

        Collections.addAll(searchControls,
                task,
                acTitle,
                tasksTitle,
                shortNameCustomField,
                effortLeftField,
                descriptionField,
                assigneeField
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

    class TextFieldEditingCell extends TableCell<Object, String> {
        public SearchableTextField textField;

        /**
         * Constructor
         */
        private TextFieldEditingCell() {
        }

        /**
         * Sets the cell to a combo box when focused on.
         */
        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setGraphic(textField);

                if (!getText().isEmpty()) {
                    textField.setText(getItem());
                }
                else {
                    textField.setText(null);
                }
                Platform.runLater(() -> {
                        textField.requestFocus();
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
         *
         * @param item  the item to update to
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
                    if (textField != null) {
                        textField.setText(getItem());
                    }
                    setText(getItem());
                    setGraphic(textField);
                }
                else {
                    setText(getItem());
                    setGraphic(null);
                }
            }
        }

        /**
         * Creates the combo box and populates it with the itemList. Updates the value in the cell.
         */
        private void createTextField() {
            textField = new SearchableTextField();
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0,
                                    Boolean arg1, Boolean arg2) {
                    if (!arg2) {
                        if (textField.getText() != null) {
                            commitEdit(textField.getText());
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

        public SearchableTextField getTextField() {
            return this.textField;
        }

    }
}
