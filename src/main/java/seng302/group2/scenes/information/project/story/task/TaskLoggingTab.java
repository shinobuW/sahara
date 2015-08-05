/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information.project.story.task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
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

/**
 *
 * @author Darzolak
 */
public class TaskLoggingTab extends SearchableTab {
    List<SearchableControl> searchControls = new ArrayList<>();
    //TODO Refactor into searchable controls and populate

    /**
     * Constructor for the Task Info Tab
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

        taskTable.setItems(data);
        TableColumn[] columns = {loggerCol};
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
        SearchableText task = new SearchableText("Add Quick Tasks:", "-fx-font-weight: bold;");


        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        Button btnAdd = new Button("Add");
        btnAdd.setDisable(true);

        addTaskBox.getChildren().addAll(task, shortNameCustomField, btnAdd);

        basicInfoPane.getChildren().add(taskTable);
        basicInfoPane.getChildren().add(btnView);
        basicInfoPane.getChildren().add(addTaskBox);

       
        btnAdd.setOnAction((event) -> {
                
                String shortName = shortNameCustomField.getText();
                Log newLog = new Log(currentTask, new Person(), 90, null);
                currentTask.add(newLog);
                App.refreshMainScene();
                
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
