package seng302.group2.scenes.information.project.story;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.project.story.Story;

import java.util.*;

/**
 * A class for displaying the story scene
 * Created by drm127 on 17/05/15.
 */
public class StoryScene extends TrackedTabPane {

    Collection<SearchableTab> searchableTabs = new ArrayList<>();

    SearchableTab informationTab;
    SearchableTab acceptanceCriteriaTab;
    SearchableTab dependantTab;
    SearchableTab taskTab;

    SearchableTab editTab;

    Story currentStory;
    boolean editScene = false;

    /**
     * Constructor for the Story Scene. Creates instances of the StoryInfoTab, StoryAcTab and StoryDependenciedTab
     * and displays them.
     * 
     * @param currentStory The currently selected story.
     */
    public StoryScene(Story currentStory) {
        super(ContentScene.STORY, currentStory);

        this.currentStory = currentStory;


        // Define and add the tabs
        informationTab = new StoryInfoTab(currentStory);
        acceptanceCriteriaTab = new StoryAcTab(currentStory);
        dependantTab = new StoryDependenciesTab(currentStory);
        taskTab = new StoryTaskTab(currentStory);


        if (!currentStory.tasksWithoutStory) {
            Collections.addAll(searchableTabs, informationTab, acceptanceCriteriaTab, dependantTab);
        }
        searchableTabs.add(taskTab);

        this.getTabs().addAll(searchableTabs);  // Add the tabs to the pane
    }

    /**
     * Constructor for the Story Scene. This creates an instance of the StoryEditTab tab and displays it.
     *
     * @param currentStory The story to be edited
     * @param editScene a boolean - if the scene is an edit scene
     */
    public StoryScene(Story currentStory, boolean editScene) {
        super(ContentScene.STORY_EDIT, currentStory);

        this.currentStory = currentStory;
        this.editScene = editScene;

        // Define and add the tabs
        informationTab = new StoryInfoTab(currentStory);
        editTab = new StoryEditTab(currentStory);

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