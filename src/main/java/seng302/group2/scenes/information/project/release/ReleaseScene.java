/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information.project.release;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.workspace.project.release.Release;

/**
 * A class for displaying the release scene
 *
 * @author jml168
 */
public class ReleaseScene extends TrackedTabPane {
    
    /**
     * Constructor for the Release Scene. Creates an instance of the ReleaseInfoTab class and displays it.
     * 
     * @param currentRelease The currently selected Release
     */
    public ReleaseScene(Release currentRelease) {
        super(ContentScene.RELEASE, currentRelease.getProject());

        // Define and add the tabs
        Tab informationTab = new ReleaseInfoTab(currentRelease);

        this.getTabs().addAll(informationTab);  // Add the tabs to the pane
    }

    /**
     * Constructor for the Release Scene. This creates an instance of the ReleaseEditTab tab and displays it.
     *
     * @param currentRelease the release to be edited
     * @param editScene a boolean - if the scene is an edit scene
     */
    public ReleaseScene(Release currentRelease, boolean editScene) {
        super(ContentScene.RELEASE_EDIT, currentRelease.getProject());

        // Define and add the tabs
        Tab editTab = new ReleaseEditTab(currentRelease);

        this.getTabs().addAll(editTab);  // Add the tabs to the pane
    }
}
