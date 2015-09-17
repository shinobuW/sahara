package seng302.group2.scenes.information.roadMap;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.information.project.ProjectCategoryTab;
import seng302.group2.workspace.workspace.Workspace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Category Scene for RoadMaps
 * Created by cvs20 on 11/09/15.
 */
public class RoadMapCategoryScene extends TrackedTabPane {

    Workspace currentWorkspace;
    SearchableTab categoryTab;

    /**
     * Constructor for the RoadMapCategoryScene class. Creates a tab
     * of RoadMapCategoryTab and displays it.
     * @param currentWorkspace the current workspace
     */
    public RoadMapCategoryScene(Workspace currentWorkspace) {
        super(TrackedTabPane.ContentScene.ROADMAP_CATEGORY, currentWorkspace);

        this.currentWorkspace = currentWorkspace;

        // Define and add the tabs
        categoryTab = new RoadMapCategoryTab(currentWorkspace);
        updateAllTabs();

        Collections.addAll(getSearchableTabs(), categoryTab);
        this.getTabs().addAll(getSearchableTabs());
    }

}
