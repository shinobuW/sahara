/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information.role;

import javafx.scene.control.Tab;
import seng302.group2.scenes.SceneSwitcher;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.workspace.role.Role;

/**
 * A class for displaying the role scene
 * @author jml168
 */
public class RoleScene extends TrackedTabPane
{
    public RoleScene(Role currentRole)
    {
        super(SceneSwitcher.ContentScene.ROLE);

        // Define and add the tabs
        Tab informationTab = new RoleInfoTab(currentRole);

        this.getTabs().addAll(informationTab);  // Add the tabs to the pane
    }
}
