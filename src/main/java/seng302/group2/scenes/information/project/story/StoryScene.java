package seng302.group2.scenes.information.project.story;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.project.story.Story;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * A class for displaying the story scene
 * Created by drm127 on 17/05/15.
 */
public class StoryScene extends TrackedTabPane {

    Collection<SearchableTab> searchableTabs = new HashSet<>();

    /**
     * Constructor for the Story Scene. Creates instances of the StoryInfoTab, StoryAcTab and StoryDependenciedTab
     * and displays them.
     * 
     * @param currentStory The currently selected story.
     */
    public StoryScene(Story currentStory) {
        super(ContentScene.STORY, currentStory);



        // Define and add the tabs
        Tab informationTab = new StoryInfoTab(currentStory);
        Tab acceptanceCriteriaTab = new StoryAcTab(currentStory);
        Tab dependantTab = new StoryDependenciesTab(currentStory);

        this.getTabs().addAll(informationTab, acceptanceCriteriaTab, dependantTab);  // Add the tabs to the pane
    }

    /**
     * Constructor for the Stor Scene. This creates an instance of the StoryEditTab tab and displays it.
     *
     * @param currentStory The story to be edited
     * @param editScene a boolean - if the scene is an edit scene
     */
    public StoryScene(Story currentStory, boolean editScene) {
        super(ContentScene.STORY_EDIT, currentStory);

        // Define and add the tabs
        SearchableTab editTab = new StoryEditTab(currentStory);
        Collections.addAll(searchableTabs, editTab);

        this.getTabs().addAll(searchableTabs);  // Add the tabs to the pane
    }

    /**
     * Gets all the SearchableTabs on this scene
     * @return collection of SearchableTabs
     */
    @Override
    public Collection<SearchableTab> getSearchableTabs() {
        return searchableTabs;
    }
}