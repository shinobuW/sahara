package seng302.group2.scenes.information.project.story;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
