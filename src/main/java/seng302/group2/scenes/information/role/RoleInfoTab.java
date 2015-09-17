package seng302.group2.scenes.information.role;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.CustomInfoLabel;
import seng302.group2.scenes.control.search.*;
import seng302.group2.workspace.role.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * The workspace information tab
 * Created by jml168 on 11/05/15.
 */
public class RoleInfoTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();
    Role currentRole;

    /**
     * Constructor for the Role Info Tab
     * 
     * @param currentRole The currently selected Role. 
     */
    public RoleInfoTab(Role currentRole) {
        this.currentRole = currentRole;
        this.construct();
    }

    /**
     * Gets all the searchable controls on this tab.
     * @return a collection of all the searchable controls on this tab.
     */
    @Override
    public Collection<SearchableControl> getSearchableControls() {
        return searchControls;
    }

    @Override
    public void construct() {
        // Tab settings
        this.setText("Basic Information");
        Pane basicInfoPane = new VBox(10);  // The pane that holds the basic info
        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        // Create controls
        SearchableText title = new SearchableTitle(currentRole.getShortName());
        CustomInfoLabel desc = new CustomInfoLabel("Role Description: ", currentRole.getDescription());
        CustomInfoLabel required = new CustomInfoLabel("Required Skills: ", "");
        SearchableListView skillsBox = new SearchableListView<>(currentRole.getRequiredSkills());
        skillsBox.setPrefHeight(192);
        skillsBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        skillsBox.setMaxWidth(450);



        // Add items to pane & search collection
        basicInfoPane.getChildren().addAll(
                title,
                desc,
                required,
                skillsBox
        );

        Collections.addAll(searchControls,
                title,
                desc,
                required,
                skillsBox
        );
    }

    /**
     * Gets the string representation of the current Tab
     * @return The String value
     */
    @Override
    public String toString() {
        return "Role Info Tab";
    }
}
