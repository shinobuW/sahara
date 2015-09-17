/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information.role;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.information.skill.SkillEditTab;
import seng302.group2.scenes.information.skill.SkillInfoTab;
import seng302.group2.workspace.role.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A class for displaying the role scene
 *
 * @author jml168
 */
public class RoleScene extends TrackedTabPane {

    Collection<SearchableTab> searchableTabs = new
            ArrayList<>();

    Role currentRole;
    SearchableTab informationTab;
    
    /**
     * Constructor for the Role Scene. Creates an instance of the Role Info Tab and displays it.
     * 
     * @param currentRole The currently selected Role. 
     */
    public RoleScene(Role currentRole) {
        super(ContentScene.ROLE, currentRole);

        this.currentRole = currentRole;

        // Define and add the tabs
        updateAllTabs();

        Collections.addAll(searchableTabs, informationTab);
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


        if (informationTab != selectedTab) {
            informationTab = new RoleInfoTab(currentRole);
        }

    }

    @Override
    public void updateAllTabs() {
        informationTab = new RoleInfoTab(currentRole);
    }
}
