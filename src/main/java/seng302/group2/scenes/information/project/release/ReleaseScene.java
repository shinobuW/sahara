/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information.project.release;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.information.roadMap.RoadMapEditTab;
import seng302.group2.scenes.information.roadMap.RoadMapInfoTab;
import seng302.group2.workspace.project.release.Release;
import seng302.group2.workspace.roadMap.RoadMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A class for displaying the release scene
 *
 * @author jml168
 */
public class ReleaseScene extends TrackedTabPane {

    Collection<SearchableTab> searchableTabs = new ArrayList<>();

    Release currentRelease;
    boolean editScene = false;

    SearchableTab informationTab;
    SearchableTab editTab;
    
    /**
     * Constructor for the Release Scene. Creates an instance of the ReleaseInfoTab class and displays it.
     * 
     * @param currentRelease The currently selected Release
     */
    public ReleaseScene(Release currentRelease) {
        super(ContentScene.RELEASE, currentRelease.getProject());

        this.currentRelease = currentRelease;

        // Define and add the tabs
        informationTab = new ReleaseInfoTab(currentRelease);


        Collections.addAll(searchableTabs, informationTab);
        this.getTabs().addAll(searchableTabs);  // Add the tabs to the pane
    }

    /**
     * Constructor for the Release Scene. This creates an instance of the ReleaseEditTab tab and displays it.
     *
     * @param currentRelease the release to be edited
     * @param editScene a boolean - if the scene is an edit scene
     */
    public ReleaseScene(Release currentRelease, boolean editScene) {
        super(ContentScene.RELEASE_EDIT, currentRelease.getProject());

        this.currentRelease = currentRelease;
        this.editScene = editScene;

        // Define and add the tabs
        editTab = new ReleaseEditTab(currentRelease);

        Collections.addAll(searchableTabs, editTab);

        this.getTabs().addAll(searchableTabs);  // Add the tabs to the pane
    }

    /**
     * Gets all the SearchableTabs on this scene
     * @return collection of tabs
     */
    @Override
    public Collection<SearchableTab> getSearchableTabs() {
        return searchableTabs;
    }

}
