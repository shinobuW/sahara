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
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.control.search.SearchableTitle;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * The story information tab.
 * Created by drm127 on 17/05/15.
 */
public class StoryInfoTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();


    /**
     * Constructor for the Story Info Tab
     * 
     * @param currentStory The currently selected Story 
     */
    public StoryInfoTab(Story currentStory) {

        this.setText("Basic Information");

        Pane basicInfoPane = new VBox(10);

        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        SearchableText title = new SearchableTitle(currentStory.getShortName());

        Button btnEdit = new Button("Edit");

        basicInfoPane.getChildren().add(title);
        basicInfoPane.getChildren().add(new SearchableText("Story Description: " + currentStory.getDescription(),
                searchControls));
        basicInfoPane.getChildren().add(new SearchableText("Project: "
                + currentStory.getProject().toString(), searchControls));
        basicInfoPane.getChildren().add(new SearchableText("Priority: "
                + currentStory.getPriority(), searchControls));
        basicInfoPane.getChildren().add(new SearchableText("Estimate: "
                + currentStory.getEstimate(), searchControls));
        basicInfoPane.getChildren().add(new SearchableText("State: "
                + currentStory.getReadyState(), searchControls));
        basicInfoPane.getChildren().add(new SearchableText("Story Creator: "
                + currentStory.getCreator(), searchControls));

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
        stateCol.setCellValueFactory(new PropertyValueFactory<Task, Task.TASKSTATE>("state"));
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
        
        basicInfoPane.getChildren().add(taskTable);
        basicInfoPane.getChildren().add(btnEdit);


        
        btnEdit.setOnAction((event) -> {
                currentStory.switchToInfoScene(true);
            });

        Collections.addAll(searchControls, title);
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
