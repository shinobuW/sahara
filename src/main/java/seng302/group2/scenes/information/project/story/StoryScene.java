package seng302.group2.scenes.information.project.story;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.information.project.ProjectEditTab;
import seng302.group2.scenes.information.project.ProjectHistoryTab;
import seng302.group2.scenes.information.project.ProjectInfoTab;
import seng302.group2.scenes.information.project.ProjectLoggingTab;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.story.Story;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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
        updateAllTabs();

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
        updateAllTabs();

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


    @Override
    public void updateTabs() {
        Tab selectedTab = this.getSelectionModel().getSelectedItem();

        if (editScene) {
            if (editTab != selectedTab) {
                editTab = new StoryEditTab(currentStory);
            }
        }
        else {
            if (informationTab != selectedTab) {
                informationTab = new StoryInfoTab(currentStory);
            }
            if (acceptanceCriteriaTab != selectedTab) {
                acceptanceCriteriaTab = new StoryAcTab(currentStory);
            }
            if (dependantTab != selectedTab) {
                dependantTab = new StoryDependenciesTab(currentStory);
            }
            if (taskTab != selectedTab) {
                taskTab = new StoryTaskTab(currentStory);
            }
        }
    }

    @Override
    public void updateAllTabs() {
        if (editScene) {
            editTab = new StoryEditTab(currentStory);
        }
        else {
            if (!currentStory.tasksWithoutStory) {
                informationTab = new StoryInfoTab(currentStory);
                acceptanceCriteriaTab = new StoryAcTab(currentStory);
                dependantTab = new StoryDependenciesTab(currentStory);
            }

            taskTab = new StoryTaskTab(currentStory);
        }
    }
}