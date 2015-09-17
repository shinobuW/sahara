package seng302.group2.scenes.information.roadMap;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.information.role.RoleInfoTab;
import seng302.group2.workspace.roadMap.RoadMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by cvs20 on 11/09/15.
 */
public class RoadMapScene extends TrackedTabPane {

    Collection<SearchableTab> searchableTabs = new ArrayList<>();


    RoadMap currentRoadMap;
    boolean editScene = false;

    SearchableTab informationTab;
    SearchableTab editTab;

    /**
     * Constructor for the RoadMapScene class. Displays an instance of RoadMapInfoTab.
     * @param currentRoadMap the current RoadMap for which information will be displayed
     */
    public RoadMapScene(RoadMap currentRoadMap) {
        super(ContentScene.ROADMAP, currentRoadMap);

        this.currentRoadMap = currentRoadMap;

        //Define and add the tabs
        updateAllTabs();

        Collections.addAll(searchableTabs, informationTab);
        this.getTabs().addAll(searchableTabs);

    }

    /**
     * Constructor for the RoadMapScene class. This creates an instance of the RoadMapEditTab tab and displays it.
     * @param currentRoadMap the RoadMap which will be edited
     * @param editScene a boolean - if the scene is an edit scene
     */
    public RoadMapScene(RoadMap currentRoadMap, boolean editScene) {
        super(ContentScene.ROADMAP_EDIT, currentRoadMap);

        this.currentRoadMap = currentRoadMap;
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
            if (informationTab != selectedTab) {
                informationTab = new RoadMapInfoTab(currentRoadMap);
            }
            if (editTab != selectedTab) {
                editTab = new RoadMapEditTab(currentRoadMap);
            }
        }

    }

    @Override
    public void updateAllTabs() {
        if (editScene) {
            editTab = new RoadMapEditTab(currentRoadMap);
        }
        else {
            informationTab = new RoadMapInfoTab(currentRoadMap);
        }
    }
}

