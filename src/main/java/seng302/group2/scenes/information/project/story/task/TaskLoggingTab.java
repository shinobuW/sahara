/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information.project.story.task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomDatePicker;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.control.search.SearchableTitle;
import seng302.group2.util.conversion.GeneralEnumStringConverter;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.story.tasks.Log;
import seng302.group2.workspace.project.story.tasks.Task;
import seng302.group2.workspace.team.Team;

/**
 *
 * @author Darzolak
 */
public class TaskLoggingTab extends SearchableTab {
    List<SearchableControl> searchControls = new ArrayList<>();

    /**
     * Constructor for the Task Logging Tab
     *
     * @param currentTask The currently selected Task
     */
    public TaskLoggingTab(Task currentTask) {

        this.setText("Logging Effort");
        Pane basicInfoPane = new VBox(10);

        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        TableView<Log> taskTable = new TableView<>();
        taskTable.setEditable(false);
        taskTable.setPrefWidth(500);
        taskTable.setPrefHeight(200);
        taskTable.setPlaceholder(new SearchableText("There are currently no tasks in this story.", searchControls));
        taskTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Log> data = currentTask.getLogs();

        TableColumn loggerCol = new TableColumn("Name");
        loggerCol.setCellValueFactory(new PropertyValueFactory<Task, Person>("logger"));
        loggerCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));
        
        TableColumn startTimeCol = new TableColumn("Start Time");
        startTimeCol.setCellValueFactory(new PropertyValueFactory<Task, LocalDate>("startTime"));
        startTimeCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));
        
        TableColumn endTimeCol = new TableColumn("End Time");
        endTimeCol.setCellValueFactory(new PropertyValueFactory<Task, LocalDate>("endTime"));
        endTimeCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));
        
        TableColumn durationCol = new TableColumn("Duration");
        durationCol.setCellValueFactory(new PropertyValueFactory<Task, Long>("duration"));
        durationCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        taskTable.setItems(data);
        TableColumn[] columns = {loggerCol, startTimeCol, endTimeCol, durationCol};
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
                App.mainPane.selectItem(taskTable.getSelectionModel().getSelectedItem());
            });

        VBox addTaskBox = new VBox(10);
        
        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.BOTTOM_RIGHT);
        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        buttons.getChildren().addAll(addButton, deleteButton);
        
        
        
        HBox newLogFields = new HBox(35);
        final ComboBox<Person> personComboBox = new ComboBox<>(observableArrayList());
        CustomDatePicker startDatePicker = new CustomDatePicker("Start Date", true);
        CustomDatePicker endDatePicker = new CustomDatePicker("End Date", false);

        startDatePicker.getDatePicker().setStyle("-fx-pref-width: 200;");
        endDatePicker.getDatePicker().setStyle("-fx-pref-width: 200;");
        personComboBox.setStyle("-fx-pref-width: 250;");

        personComboBox.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(3).divide(100).multiply(30));
        startDatePicker.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(3).divide(100).multiply(30));
        endDatePicker.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(3).divide(100).multiply(30));
        newLogFields.getChildren().addAll(personComboBox,
                startDatePicker, endDatePicker);

        personComboBox.setOnMouseClicked(event -> {
                personComboBox.getItems().clear();
                //TODO not going to work on tasks without a story
                for (Team team : currentTask.getStory().getBacklog().getProject().getCurrentTeams()) {
                    for (Person person : team.getPeople()) {
                        personComboBox.getItems().add(person);
                    }
                }
            });

        addButton.setOnAction((event) -> {
                startDatePicker.hideErrorField();
                if (personComboBox.getValue() != null && startDatePicker.getValue() != null) {
                    LocalDate endDate = endDatePicker.getValue();
                    LocalDate startDate = startDatePicker.getValue();
                    Person selectedPerson = null;

                    selectedPerson = personComboBox.getValue();

                    if (true) {
                        Log newLog = new Log(currentTask, selectedPerson, 90, startDate);
                        currentTask.add(newLog);
                        App.refreshMainScene(); 
                    }
                    else {
                        
                        event.consume();
                    }
                }
                else {
                    if (personComboBox.getValue() == null) {
                        //personComboBox.showErrorField("Please select a team");
                        event.consume();
                    }
                    if (startDatePicker.getValue() == null) {
                        startDatePicker.showErrorField("Please select a date");
                        event.consume();
                    }
                }
            });

        deleteButton.setOnAction((event) -> {
                Log selectedLog = taskTable.getSelectionModel().getSelectedItem();
                if (selectedLog != null) {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete");
                    alert.setHeaderText("Delete Allocation?");
                    alert.setContentText("Do you really want to delete this allocation?");
                    alert.getDialogPane().setStyle(" -fx-max-width:450; -fx-max-height: 100px; -fx-pref-width: 450px; "
                            + "-fx-pref-height: 100px;");

                    ButtonType buttonTypeYes = new ButtonType("Yes");
                    ButtonType buttonTypeNo = new ButtonType("No");

                    alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                    Optional<ButtonType> result  = alert.showAndWait();

                    if (result.get() == buttonTypeYes) {
                        //selectedLog.delete();
                    }
                    else if (result.get() == buttonTypeNo) {
                        event.consume();
                    }
                }
            });

        
        basicInfoPane.getChildren().add(taskTable);
        basicInfoPane.getChildren().add(btnView);
        basicInfoPane.getChildren().add(newLogFields);
        basicInfoPane.getChildren().add(buttons);

       
        
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
