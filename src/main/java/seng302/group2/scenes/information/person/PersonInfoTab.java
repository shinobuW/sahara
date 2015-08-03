package seng302.group2.scenes.information.person;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
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
    public PersonInfoTab(Person currentPerson) {
        this.setText("Basic Information");

        Pane basicInfoPane = new VBox(10);  // The pane that holds the basic info
        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);


        SearchableText title = new SearchableText(currentPerson.getFirstName() + " "
                + currentPerson.getLastName());

        Button btnEdit = new Button("Edit");

        ListView personSkillsBox = new ListView(currentPerson.getSkills());
        personSkillsBox.setPrefHeight(192);

        personSkillsBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        final Separator separator = new Separator();

        SearchableText shortName = new SearchableText("Short Name: " + currentPerson.getShortName());
        SearchableText emailAddress = new SearchableText("Email Address: " + currentPerson.getEmail());
        SearchableText birthDate = new SearchableText("Birth Date: "
                + currentPerson.getDateString());
        SearchableText desc = new SearchableText("Person Description: "
                + currentPerson.getDescription());
        SearchableText team = new SearchableText("Team: " + currentPerson.getTeamName());

        String roleString = currentPerson.getRole() == null ? "" :
                currentPerson.getRole().toString();

        SearchableText role = new SearchableText("Role: " + roleString);
        SearchableText skill  = new SearchableText("Skills: ");
        basicInfoPane.getChildren().addAll(title, shortName, emailAddress, birthDate, desc, team, role, separator,
                skill, personSkillsBox, btnEdit);
        Collections.addAll(searchControls, title, shortName, emailAddress, birthDate, desc, team, role, skill);

        btnEdit.setOnAction((event) -> {
                currentPerson.switchToInfoScene(true);
            });

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
