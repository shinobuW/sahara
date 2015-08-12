package seng302.group2.scenes.information.person;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.search.*;
import seng302.group2.workspace.person.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * The person information tab
 * Created by jml168 on 11/05/15.
 */
public class PersonInfoTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();

    /**
     * A tab to display information on a selected tasks.
     * @param currentPerson The person to display the information about
     */
    public PersonInfoTab(Person currentPerson) {
        // Tab settings
        this.setText("Basic Information");
        Pane basicInfoPane = new VBox(10);
        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        // Create controls
        SearchableText title = new SearchableTitle(currentPerson.getFirstName() + " " + currentPerson.getLastName());
        SearchableListView personSkillsBox = new SearchableListView<>(currentPerson.getSkills());
        personSkillsBox.setPrefHeight(192);
        personSkillsBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        SearchableText shortName = new SearchableText("Short Name: " + currentPerson.getShortName());
        SearchableText emailAddress = new SearchableText("Email Address: " + currentPerson.getEmail());
        SearchableText birthDate = new SearchableText("Birth Date: " + currentPerson.getDateString());
        SearchableText desc = new SearchableText("Person Description: " + currentPerson.getDescription());
        SearchableText team = new SearchableText("Team: " + currentPerson.getTeamName());
        String roleString = currentPerson.getRole() == null ? "" : currentPerson.getRole().toString();
        SearchableText role = new SearchableText("Role: " + roleString);
        SearchableText skill  = new SearchableText("Skills: ");
        Button btnEdit = new Button("Edit");

        final Separator separator = new Separator();

        // Events
        btnEdit.setOnAction((event) -> {
                currentPerson.switchToInfoScene(true);
            });

        // Add items to pane & search collection
        basicInfoPane.getChildren().addAll(
                title,
                shortName,
                emailAddress,
                birthDate,
                desc,
                team,
                role,
                separator,
                skill,
                personSkillsBox,
                btnEdit);

        Collections.addAll(searchControls,
                title,
                shortName,
                emailAddress,
                birthDate,
                desc,
                team,
                role,
                skill,
                personSkillsBox
        );
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
