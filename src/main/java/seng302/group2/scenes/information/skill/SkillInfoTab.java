package seng302.group2.scenes.information.skill;

import com.sun.javafx.font.freetype.HBGlyphLayout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomInfoLabel;
import seng302.group2.scenes.control.search.*;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.skills.Skill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * The skill information tab
 * Created by jml168 on 11/05/15.
 */
public class SkillInfoTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();
    Button btnEdit = new Button("Edit");

    /**
     * Constructor for the SkillInfoTab class.
     * @param currentSkill the current skill for which information will be displayed
     */
    public SkillInfoTab(Skill currentSkill) {
        // Tab settings
        this.setText("Basic Information");
        Pane basicInfoPane = new VBox(10);
        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);


        CustomInfoLabel desc = new CustomInfoLabel("Description: ", currentSkill.getDescription());
        // Create controls
        SearchableText title = new SearchableTitle(currentSkill.getShortName());
        //SearchableText desc = new SearchableText("Description: " + currentSkill.getDescription());
        CustomInfoLabel listViewLabel = new CustomInfoLabel("People who have this skill:", "");
        ObservableList<Person> peopleWithSkill = FXCollections.observableArrayList();
        for (Person p : Global.currentWorkspace.getPeople()) {
            if (p.getSkills().contains(currentSkill)) {
                peopleWithSkill.add(p);
            }
        }
        SearchableListView<Person> personListView = new SearchableListView<>(peopleWithSkill);

        boolean skillIsProductOwner = currentSkill.getShortName().equals("Product Owner");
        boolean skillIsScrumMaster = currentSkill.getShortName().equals("Scrum Master");
        if (skillIsProductOwner || skillIsScrumMaster) {
            btnEdit.setDisable(true);
        }

        // Events
        btnEdit.setOnAction((event) -> currentSkill.switchToInfoScene(true));

        // Add items to pane & search collection
        basicInfoPane.getChildren().addAll(title, desc, listViewLabel, personListView, btnEdit);
        Collections.addAll(searchControls, title, desc, listViewLabel, personListView);


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
