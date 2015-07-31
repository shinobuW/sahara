package seng302.group2.scenes.information.person;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.person.Person;

import java.util.ArrayList;
import java.util.Collection;
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


        Label title = new TitleLabel(currentPerson.getFirstName() + " "
                + currentPerson.getLastName());

        Button btnEdit = new Button("Edit");

        ListView personSkillsBox = new ListView(currentPerson.getSkills());
        personSkillsBox.setPrefHeight(192);

        personSkillsBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        final Separator separator = new Separator();

        basicInfoPane.getChildren().add(title);
        basicInfoPane.getChildren().add(new Label("Short Name: " + currentPerson.getShortName()));
        basicInfoPane.getChildren().add(new Label("Email Address: " + currentPerson.getEmail()));
        basicInfoPane.getChildren().add(new Label("Birth Date: "
                + currentPerson.getDateString()));
        basicInfoPane.getChildren().add(new Label("Person Description: "
                + currentPerson.getDescription()));
        basicInfoPane.getChildren().add(new Label("Team: " + currentPerson.getTeamName()));

        String roleString = currentPerson.getRole() == null ? "" :
                currentPerson.getRole().toString();
        basicInfoPane.getChildren().add(new Label("Role: " + roleString));

        basicInfoPane.getChildren().add(separator);
        basicInfoPane.getChildren().add(new Label("Skills: "));
        basicInfoPane.getChildren().add(personSkillsBox);

        basicInfoPane.getChildren().add(btnEdit);

        btnEdit.setOnAction((event) -> {
                currentPerson.switchToInfoScene(true);
            });

    }

    @Override
    // TODO
    public Collection<SearchableControl> getSearchableControls() {
        return searchControls;
    }
}
