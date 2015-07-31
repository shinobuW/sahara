/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information.role;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.role.Role;

import java.util.Collection;
import java.util.HashSet;

/**
 * A class for displaying the role scene
 *
 * @author jml168
 */
public class RoleScene extends TrackedTabPane {

    Collection<SearchableTab> searchableTabs = new HashSet<>();
    
    /**
     * Constructor for the Role Scene. Creates an instance of the Role Info Tab and displays it.
     * 
     * @param currentRole The currently selected Role. 
     */
    public RoleScene(Role currentRole) {
        super(ContentScene.ROLE, currentRole);

        // Define and add the tabs
        SearchableTab informationTab = new RoleInfoTab(currentRole);

        this.getTabs().addAll(informationTab);  // Add the tabs to the pane
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
