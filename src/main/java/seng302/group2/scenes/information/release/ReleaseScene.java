/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information.release;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.sceneswitch.SceneSwitcher;
import seng302.group2.workspace.project.release.Release;

/**
 * A class for displaying the release scene
 *
 * @author jml168
 */
public class ReleaseScene extends TrackedTabPane {
    public ReleaseScene(Release currentRelease) {
        super(SceneSwitcher.ContentScene.RELEASE);

        // Define and add the tabs
        Tab informationTab = new ReleaseInfoTab(currentRelease);

        this.getTabs().addAll(informationTab);  // Add the tabs to the pane
    }
}
