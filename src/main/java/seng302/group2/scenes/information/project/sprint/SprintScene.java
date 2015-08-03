package seng302.group2.scenes.information.project.sprint;

import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
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

    /**
     * Basic constructor for the sprint scene.
     * @param currentSprint the sprint for which information will be displayed.
     */
    public SprintScene(Sprint currentSprint) {
        super(ContentScene.SPRINT, currentSprint);

        //Define and add the tabs
        SearchableTab informationTab = new SprintInfoTab(currentSprint);
        SearchableTab burndownTab = new SprintBurndownTab(currentSprint);
        SearchableTab scrumboardTab = new TestingCellTestTab(currentSprint);

        Collections.addAll(searchableTabs, informationTab, burndownTab);
        this.getTabs().addAll(searchableTabs);  // Add the tabs to the pane
    }

    /**
     * Constructor for the SprintScene. This sohuld only be used to display an edit tab.
     * @param currentSprint the sprint for which information will be displayed.
     */
    public SprintScene(Sprint currentSprint, boolean editScene) {
        super(ContentScene.SPRINT_EDIT, currentSprint);

        //Define and add the tabs
        SearchableTab editTab = new SprintEditTab(currentSprint);
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
