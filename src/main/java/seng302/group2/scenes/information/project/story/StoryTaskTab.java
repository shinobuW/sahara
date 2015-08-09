package seng302.group2.scenes.information.project.story;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
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
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * Created by cvs20 on 5/08/15.
 */
public class StoryTaskTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();
    static Boolean correctShortName = Boolean.FALSE;

    /**
     * Constructor for the Story Task Tab
     *
     * @param currentStory The currently selected Story
     */
    public StoryTaskTab(Story currentStory) {

        this.setText("Tasks");
        Pane basicInfoPane = new VBox(10);

        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        TableView<Task> taskTable = new TableView<>();
        taskTable.setEditable(false);
        taskTable.setPrefWidth(500);
        taskTable.setPrefHeight(200);
        taskTable.setPlaceholder(new SearchableText("There are currently no tasks in this story.", searchControls));
        taskTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Task> data = currentStory.getTasks();

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Task, String>("shortName"));
        nameCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        TableColumn stateCol = new TableColumn("State");
        stateCol.setCellValueFactory(new PropertyValueFactory<Task, String>("stringState"));
        stateCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        TableColumn responsibilitiesCol = new TableColumn("Responsibilities");
        responsibilitiesCol.setCellValueFactory(new PropertyValueFactory<Task,
                ObservableList<Person>>("responsibilities"));
        responsibilitiesCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        TableColumn leftCol = new TableColumn("Effort Left");
        // TODO
        // leftCol.setCellValueFactory(new PropertyValueFactory<Task, String>("shortName"));
        leftCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        TableColumn spentCol = new TableColumn("Effort Spent");
        //TODO
        // spentCol.setCellValueFactory(new PropertyValueFactory<Task, String>("shortName"));
        spentCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        taskTable.setItems(data);
        TableColumn[] columns = {nameCol, stateCol, responsibilitiesCol, leftCol, spentCol};
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

        shortNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctShortName = validateShortName(shortNameCustomField, null);
                btnAdd.setDisable(!(correctShortName));

            });
        btnAdd.setOnAction((event) -> {
                if (correctShortName) {
                    //get user input
                    String shortName = shortNameCustomField.getText();
                    Task newTask = new Task(shortName, "", currentStory);
                    currentStory.add(newTask);
                    App.refreshMainScene();
                }
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
