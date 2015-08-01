package seng302.group2.scenes.information.role;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
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

    /**
     * Constructor for the Role Info Tab
     * 
     * @param currentRole The currently selected Role. 
     */
    public RoleInfoTab(Role currentRole) {
        this.setText("Basic Information");

        Pane basicInfoPane = new VBox(10);  // The pane that holds the basic info
        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);


        SearchableText title = new SearchableText(currentRole.getShortName());

        ListView skillsBox = new ListView(currentRole.getRequiredSkills());
        skillsBox.setPrefHeight(192);
        skillsBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        skillsBox.setMaxWidth(450);

        SearchableText desc = new SearchableText("Role Description: "
                + currentRole.getDescription());
        SearchableText required = new SearchableText("Required Skills: ");
        basicInfoPane.getChildren().addAll(title, desc, required, skillsBox);
        Collections.addAll(searchControls, title, desc, required);
    }

    /**
     * Gets all the searchable controls on this tab.
     * @return a collection of all the searchable controls on this tab.
     */
    @Override
    public Collection<SearchableControl> getSearchableControls() {
        return searchControls;
    }
}
