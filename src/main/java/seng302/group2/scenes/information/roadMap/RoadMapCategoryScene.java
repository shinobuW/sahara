package seng302.group2.scenes.information.roadMap;

import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.workspace.Workspace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Category Scene for RoadMaps
 * Created by cvs20 on 11/09/15.
 */
public class RoadMapCategoryScene extends TrackedTabPane {
    Collection<SearchableTab> searchableTabs = new ArrayList<>();

    /**
     * Constructor for the RoadMapCategoryScene class. Creates a tab
     * of RoadMapCategoryTab and displays it.
     * @param currentWorkspace the current workspace
     */
    public RoadMapCategoryScene(Workspace currentWorkspace) {
        super(TrackedTabPane.ContentScene.ROADMAP_CATEGORY, currentWorkspace);

        // Define and add the tabs
        SearchableTab categoryTab = new RoadMapCategoryTab(currentWorkspace);
        Collections.addAll(searchableTabs, categoryTab);

        this.getTabs().addAll(searchableTabs);
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
