package seng302.group2.scenes.information.project.story;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.CustomInfoLabel;
import seng302.group2.scenes.control.chart.StoryCompletenessBar;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.control.search.SearchableTitle;
import seng302.group2.workspace.project.story.Story;

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

        CustomInfoLabel description = new CustomInfoLabel("Story Description: ", currentStory.getDescription());
        CustomInfoLabel project = new CustomInfoLabel("Project: ", currentStory.getProject().toString());
        CustomInfoLabel priority = new CustomInfoLabel("Priority: ", currentStory.getPriority().toString());
        CustomInfoLabel estimate = new CustomInfoLabel("Estimate: ", currentStory.getEstimate());
        CustomInfoLabel state = new CustomInfoLabel("State: ", currentStory.getReadyState());
        CustomInfoLabel creator = new CustomInfoLabel("Story Creator: ", currentStory.getCreator());
        HBox progressBox = new HBox();
        CustomInfoLabel progressLabel = new CustomInfoLabel("Progress:  ", "");
        StoryCompletenessBar progressBar = new StoryCompletenessBar(currentStory);
        progressBox.getChildren().addAll(progressLabel, progressBar);

        btnEdit.setOnAction((event) -> {
                currentStory.switchToInfoScene(true);
            });

        basicInfoPane.getChildren().addAll(
                title,
                description,
                project,
                priority,
                estimate,
                state,
                creator,
                progressBox,
                btnEdit
        );

        Collections.addAll(searchControls,
                title,
                description,
                project,
                priority,
                estimate,
                state,
                creator
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
     * Gets the string representation of the current Tab
     * @return The String value
     */
    @Override
    public String toString() {
        return "Story Info Tab";
    }
}
