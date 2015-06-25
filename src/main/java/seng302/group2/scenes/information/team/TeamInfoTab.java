package seng302.group2.scenes.information.team;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.Global;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.team.Team;

import static javafx.collections.FXCollections.observableArrayList;


/**
 * The workspace information tab
 * Created by jml168 on 11/05/15.
 */
public class TeamInfoTab extends Tab {
    /**
     * Constructor for team basic information tab
     *
     * @param currentTeam currently selected team
     */
    public TeamInfoTab(Team currentTeam) {
        this.setText("Basic Information");

        Pane basicInfoPane = new VBox(10);  // The pane that holds the basic info
        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);


        Label title = new TitleLabel(currentTeam.getShortName());

        Button btnEdit = new Button("Edit");
        if (currentTeam.isUnassignedTeam()) {
            btnEdit.setVisible(false);
        }
        else {
            btnEdit.setVisible(true);
        }


        if (currentTeam.isUnassignedTeam()) {
            for (TreeViewItem person : Global.currentWorkspace.getPeople()) {
                Person castedPerson = (Person) person;
                if (castedPerson.getTeam() == null) {
                    currentTeam.add(castedPerson, false);
                }
            }
        }

        ObservableList<String> tempTeamString = personRoleToString(currentTeam.getPeople());

        ListView teamsPeopleBox = new ListView(tempTeamString);
        teamsPeopleBox.setPrefHeight(192);
        teamsPeopleBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        teamsPeopleBox.setMaxWidth(275);

        Separator separator = new Separator();

        basicInfoPane.getChildren().add(title);
        basicInfoPane.getChildren().add(new Label("Team Description: "
                + currentTeam.getDescription()));
        basicInfoPane.getChildren().add(separator);

        if (!currentTeam.isUnassignedTeam()) {
            if (currentTeam.getProductOwner() != null) {
                basicInfoPane.getChildren().add(new Label("Product Owner: "
                        + currentTeam.getProductOwner().toString()));
            }
            else {
                basicInfoPane.getChildren().add(new Label("Product Owner: "));
            }

            if (currentTeam.getScrumMaster() != null) {
                basicInfoPane.getChildren().add(new Label("Scrum Master: "
                        + currentTeam.getScrumMaster().toString()));
            }
            else {
                basicInfoPane.getChildren().add(new Label("Scrum Master: "));
            }

            basicInfoPane.getChildren().add(new Label("Team Members: "));
            basicInfoPane.getChildren().add(teamsPeopleBox);

            basicInfoPane.getChildren().add(btnEdit);

            btnEdit.setOnAction((event) -> {
                    currentTeam.switchToInfoScene(true);
                });
        }
        else {
            // Just add the members list
            basicInfoPane.getChildren().add(new Label("Unassigned People: "));
            basicInfoPane.getChildren().add(teamsPeopleBox);
        }
    }


    /*
    * @param currentTeam It is the team to be converted.
    * @return returns a List of the People converted to a List of their Shortnames
    */
    public static ObservableList<String> personRoleToString(ObservableList<Person> currentTeam) {
        ObservableList<String> currentStringTeam = observableArrayList();

        for (Person person : currentTeam) {
            if (person.getRole() != null) {
                if (person.getRole().toString().equals("Product Owner")) {
                    currentStringTeam.add(person.toString() + " (" + person.getRole() + ")");
                }
                else if (person.getRole().toString().equals("Scrum Master")) {
                    currentStringTeam.add(person.toString() + " (" + person.getRole() + ")");
                }
                else if (person.getRole().toString().equals("Development Team Member")) {
                    currentStringTeam.add(person.toString() + " (" + person.getRole() + ")");
                }
            }
            else {
                currentStringTeam.add(person.toString());
            }
        }

        return currentStringTeam;
    }


    /*
    * Sorts a list of people based on their roles on a team, PO first, SM second, Dev third
    * then lastly goes people without a role.
    * @param currentTeam The team to sort by.
    * @return Returns sorted list.
    */
    public static ObservableList<Person> sortListView(ObservableList<Person> currentTeam) {
        ObservableList<Person> teamList = observableArrayList();

        for (Person person : currentTeam) {
            if (person.getRole() != null) {
                if (person.getRole().toString().equals("Product Owner")) {
                    teamList.add(person);
                }
                else if (person.getRole().toString().equals("Scrum Master")) {
                    teamList.add(person);
                }
                else if (person.getRole().toString().equals("Development Team Member")) {
                    teamList.add(person);
                }
            }
            else {
                teamList.add(person);
            }
        }

        return teamList;
    }
}
