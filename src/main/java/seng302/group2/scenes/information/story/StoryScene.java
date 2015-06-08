package seng302.group2.scenes.information.story;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.sceneswitch.SceneSwitcher;
import seng302.group2.workspace.story.Story;

/**
 * A class for displaying the story scene
 * Created by drm127 on 17/05/15.
 */
public class StoryScene extends TrackedTabPane {
    public StoryScene(Story currentStory) {
        super(SceneSwitcher.ContentScene.STORY);

        // Define and add the tabs
        Tab informationTab = new StoryInfoTab(currentStory);
        Tab accepetanceCriteriaTab = new StoryAcTab(currentStory);

        this.getTabs().addAll(informationTab, accepetanceCriteriaTab);  // Add the tabs to the pane
    }
}