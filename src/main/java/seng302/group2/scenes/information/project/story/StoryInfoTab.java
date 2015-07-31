package seng302.group2.scenes.information.project.story;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;

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

        Label title = new TitleLabel(currentStory.getShortName());

        Button btnEdit = new Button("Edit");

        basicInfoPane.getChildren().add(title);
        basicInfoPane.getChildren().add(new Label("Story Description: " + currentStory.getDescription()));
        basicInfoPane.getChildren().add(new Label("Project: "
                + currentStory.getProject().toString()));
        basicInfoPane.getChildren().add(new Label("Priority: "
                + currentStory.getPriority()));
        basicInfoPane.getChildren().add(new Label("Estimate: "
                + currentStory.getEstimate()));
        basicInfoPane.getChildren().add(new Label("State: "
                + currentStory.getReadyState()));
        basicInfoPane.getChildren().add(new Label("Story Creator: "
                + currentStory.getCreator()));

        TableView<Story> taskTable = new TableView<>();
        taskTable.setEditable(false);
        taskTable.setPrefWidth(500);
        taskTable.setPrefHeight(200);
        taskTable.setPlaceholder(new Label("There are currently no tasks in this story."));
        taskTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Task> data = observableArrayList();
        data.addAll(currentStory.getTasks());

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

        basicInfoPane.getChildren().add(taskTable);
        basicInfoPane.getChildren().add(btnEdit);



        btnEdit.setOnAction((event) -> {
                currentStory.switchToInfoScene(true);
            });
    }

    @Override
    // TODO
    public Collection<SearchableControl> getSearchableControls() {
        return searchControls;
    }
}
