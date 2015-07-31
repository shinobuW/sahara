package seng302.group2.scenes.information.project.sprint;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.project.sprint.Sprint;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * A class for displaying the sprint scene.
 *
 * Created by drm127 on 29/07/15.
 */
public class SprintScene extends TrackedTabPane {

    Collection<SearchableTab> searchableTabs = new HashSet<>();

    /**
     * Basic constructor for the sprint scene.
     * @param currentSprint the sprint for which information will be displayed.
     */
    public SprintScene(Sprint currentSprint) {
        super(ContentScene.SPRINT, currentSprint);

        //Define and add the tabs
        Tab informationTab = new SprintInfoTab(currentSprint);
        Tab burndownTab = new SprintBurndownTab(currentSprint);

        this.getTabs().addAll(informationTab, burndownTab);  // Add the tabs to the pane
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

    @Override
    public Collection<SearchableTab> getSearchableTabs() {
        return searchableTabs;
    }
}
