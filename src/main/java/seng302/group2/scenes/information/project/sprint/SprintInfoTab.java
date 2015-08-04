package seng302.group2.scenes.information.project.sprint;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.control.search.SearchableTitle;
import seng302.group2.workspace.project.sprint.Sprint;
import seng302.group2.workspace.project.story.Story;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

        SearchableText title = new SearchableTitle(currentSprint.getLongName());

        Button btnEdit = new Button("Edit");

        //SUBJECT TO CHANGE BASED ON FUTURE STORIES
        ObservableList<Story> data = observableArrayList();
        data.addAll(currentSprint.getStories());
        ListView sprintStoryBox = new ListView(data);
        sprintStoryBox.setPrefHeight(192);
        sprintStoryBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        final Separator separator = new Separator();

        basicInfoPane.getChildren().add(title);
        basicInfoPane.getChildren().add(new SearchableText("Sprint Name: " + currentSprint.getLongName(),
                searchControls));
        basicInfoPane.getChildren().add(new SearchableText("Sprint Goal: " + currentSprint.getGoal(),
                searchControls));
        basicInfoPane.getChildren().add(new SearchableText("Start Date: " + currentSprint.getStartDate(),
                searchControls));
        basicInfoPane.getChildren().add(new SearchableText("End Date: " + currentSprint.getEndDate(),
                searchControls));
        basicInfoPane.getChildren().add(new SearchableText("Description: " + currentSprint.getDescription(),
                searchControls));

        basicInfoPane.getChildren().add(separator);

        basicInfoPane.getChildren().add(new SearchableText("Team: " + currentSprint.getTeam(), searchControls));
        basicInfoPane.getChildren().add(new SearchableText("Project: " + currentSprint.getProject(), searchControls));
        basicInfoPane.getChildren().add(new SearchableText("Backlog: " + currentSprint.getBacklog(), searchControls));
        basicInfoPane.getChildren().add(new SearchableText("Release: " + currentSprint.getRelease(), searchControls));

        basicInfoPane.getChildren().add(new SearchableText("Stories: ", searchControls));
        basicInfoPane.getChildren().add(sprintStoryBox);

        basicInfoPane.getChildren().add(btnEdit);

        btnEdit.setOnAction((event) -> {
                currentSprint.switchToInfoScene(true);
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
