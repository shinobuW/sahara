package seng302.group2.scenes.information.project.story;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.workspace.project.story.Story;

/**
 * The story information tab.
 * Created by drm127 on 17/05/15.
 */
public class StoryInfoTab extends Tab {
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
        basicInfoPane.getChildren().add(new Label("Story Description: "
                + currentStory.getDescription()));
        basicInfoPane.getChildren().add(new Label("Project: "
                + currentStory.getProject().toString()));
        basicInfoPane.getChildren().add(new Label("Priority: "
                + currentStory.getPriority()));
        basicInfoPane.getChildren().add(new Label("Estimate: "
                + currentStory.getEstimate()));
        basicInfoPane.getChildren().add(new Label("Story Creator: "
                + currentStory.getCreator()));
        basicInfoPane.getChildren().add(btnEdit);

        btnEdit.setOnAction((event) -> {
                currentStory.switchToInfoScene(true);
            });
    }

}
