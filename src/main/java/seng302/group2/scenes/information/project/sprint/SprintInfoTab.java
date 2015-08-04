package seng302.group2.scenes.information.project.sprint;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.project.sprint.Sprint;
import seng302.group2.workspace.project.story.Story;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * The sprint information tab.
 * Created by drm127 on 29/07/15.
 */
public class SprintInfoTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();

    /**
     * Constructor for the sprint information tab.
     */
    public SprintInfoTab(Sprint currentSprint) {
        this.setText("Basic Information");

        Pane basicInfoPane = new VBox(10);  // The pane that holds the basic info
        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        Label title = new TitleLabel(currentSprint.getLongName());

        Button btnEdit = new Button("Edit");

        //SUBJECT TO CHANGE BASED ON FUTURE STORIES
        ObservableList<Story> data = observableArrayList();
        data.addAll(currentSprint.getStories());
        ListView sprintStoryBox = new ListView(data);
        sprintStoryBox.setPrefHeight(192);
        sprintStoryBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        final Separator separator = new Separator();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String startDateString = currentSprint.getStartDate().format(formatter);
        String endDateString = currentSprint.getEndDate().format(formatter);


        basicInfoPane.getChildren().add(title);
        basicInfoPane.getChildren().add(new Label("Sprint Name: " + currentSprint.getLongName()));
        basicInfoPane.getChildren().add(new Label("Sprint Goal: " + currentSprint.getGoal()));
        basicInfoPane.getChildren().add(new Label("Start Date: " + startDateString));
        basicInfoPane.getChildren().add(new Label("End Date: " + endDateString));
        basicInfoPane.getChildren().add(new Label("Description: " + currentSprint.getDescription()));

        basicInfoPane.getChildren().add(separator);

        basicInfoPane.getChildren().add(new Label("Team: " + currentSprint.getTeam()));
        basicInfoPane.getChildren().add(new Label("Project: " + currentSprint.getProject()));
        basicInfoPane.getChildren().add(new Label("Backlog: " + currentSprint.getBacklog()));
        basicInfoPane.getChildren().add(new Label("Release: " + currentSprint.getRelease()));

        basicInfoPane.getChildren().add(new Label("Stories: "));
        basicInfoPane.getChildren().add(sprintStoryBox);

        basicInfoPane.getChildren().add(btnEdit);

        btnEdit.setOnAction((event) -> {
                currentSprint.switchToInfoScene(true);
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
