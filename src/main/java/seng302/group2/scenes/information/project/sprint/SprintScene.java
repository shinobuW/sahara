package seng302.group2.scenes.information.project.sprint;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.information.project.story.*;
import seng302.group2.workspace.project.sprint.Sprint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A class for displaying the sprint scene.
 *
 * Created by drm127 on 29/07/15.
 */
public class SprintScene extends TrackedTabPane {

    Collection<SearchableTab> searchableTabs = new ArrayList<>();


    SearchableTab informationTab;
    SearchableTab burndownTab;
    SearchableTab scrumboardTab;
    SearchableTab taskStatusTab;
    SearchableTab editTab;


    Sprint currentSprint;
    boolean editScene = false;


    /**
     * Basic constructor for the sprint scene.
     * @param currentSprint the sprint for which information will be displayed.
     */
    public SprintScene(Sprint currentSprint) {
        super(ContentScene.SPRINT, currentSprint);

        this.currentSprint = currentSprint;

        //Define and add the tabs
        updateAllTabs();

        Collections.addAll(searchableTabs, informationTab, scrumboardTab, burndownTab, taskStatusTab);
        this.getTabs().addAll(searchableTabs);  // Add the tabs to the pane
    }


    /**
     * Constructor for the SprintScene. This should only be used to display an edit tab.
     * @param currentSprint the sprint for which information will be displayed.
     * @param editScene a boolean - if the scene is an edit scene
     */
    public SprintScene(Sprint currentSprint, boolean editScene) {
        super(ContentScene.SPRINT_EDIT, currentSprint);

        this.currentSprint = currentSprint;
        this.editScene = editScene;

        //Define and add the tabs
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
                editTab = new SprintEditTab(currentSprint);
            }
        }
        else {
            if (informationTab != selectedTab) {
                informationTab = new SprintInfoTab(currentSprint);
            }
            if (burndownTab != selectedTab) {
                burndownTab = new SprintBurndownTab(currentSprint);
            }
            if (scrumboardTab != selectedTab) {
                scrumboardTab = new ScrumboardTab(currentSprint);
            }
            if (taskStatusTab != selectedTab) {
                taskStatusTab = new SprintTaskStatusTab(currentSprint);
            }
        }
    }

    @Override
    public void updateAllTabs() {
        if (editScene) {
            editTab = new SprintEditTab(currentSprint);
        }
        else {
            informationTab = new SprintInfoTab(currentSprint);
            burndownTab = new SprintBurndownTab(currentSprint);
            scrumboardTab = new ScrumboardTab(currentSprint);
            taskStatusTab = new SprintTaskStatusTab(currentSprint);
        }
    }
}
