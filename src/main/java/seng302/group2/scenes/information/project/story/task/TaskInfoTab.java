package seng302.group2.scenes.information.project.story.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.search.*;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.story.tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The task information tab.
 * Created by cvs20 on 28/07/15.
 */
public class TaskInfoTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();
    //TODO Refactor into searchable controls and populate

    /**
     * Constructor for the Task Info Tab
     *
     * @param currentTask The currently selected Task
     */
    public TaskInfoTab(Task currentTask) {

        this.setText("Basic Information");
        Pane basicInfoPane = new VBox(10);

        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        SearchableText title = new SearchableTitle(currentTask.getShortName(), searchControls);

        Button btnEdit = new Button("Edit");

        basicInfoPane.getChildren().add(title);
        basicInfoPane.getChildren().add(new SearchableText("Task Description: "
                + currentTask.getDescription(), searchControls));
        basicInfoPane.getChildren().add(new SearchableText("Impediments: "
                + currentTask.getImpediments(), searchControls));
        basicInfoPane.getChildren().add(new SearchableText("Effort Left: "
                + currentTask.getEffortLeft(), searchControls));
        basicInfoPane.getChildren().add(new SearchableText("Effort Spent: "
                + currentTask.getEffortSpent(), searchControls));

        basicInfoPane.getChildren().add(new SearchableText("Task State: " + currentTask.getState(), searchControls));

        ObservableList<Person> responsibilitiesList = FXCollections.observableArrayList();
        responsibilitiesList.addAll(currentTask.getResponsibilities());

        SearchableListView<Person> responsibilitiesListView =
                new SearchableListView<>(responsibilitiesList, searchControls);
        responsibilitiesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        responsibilitiesListView.getSelectionModel().select(0);

        VBox responsibilitiesBox = new VBox(10);
        responsibilitiesBox.getChildren().add(new SearchableText("Responsibilities: "));
        responsibilitiesBox.getChildren().add(responsibilitiesListView);
        responsibilitiesBox.setPrefHeight(192);

        basicInfoPane.getChildren().add(responsibilitiesBox);
        basicInfoPane.getChildren().add(btnEdit);
        btnEdit.setOnAction((event) -> {
                currentTask.switchToInfoScene(true);
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
