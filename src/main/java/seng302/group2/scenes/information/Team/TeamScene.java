package seng302.group2.scenes.information.Team;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.Global;
import seng302.group2.scenes.SceneSwitcher;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.team.Team;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.scenes.MainScene.informationPane;

/**
 * A class for displaying the team scene.
 * @author crw73
 */
public class TeamScene
{
     /**
     * Gets the Team information scene.
      * @param currentTeam The team to show the information of
     * @return The Team information scene
     */
    public static ScrollPane getTeamScene(Team currentTeam)
    {
        
        informationPane = new VBox(10);

        /*informationPane.setAlignment(Pos.TOP_LEFT);
        informationPane.setHgap(10);
        informationPane.setVgap(10);*/
        informationPane.setPadding(new Insets(25,25,25,25));
        Label title = new Label(currentTeam.getShortName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnEdit = new Button("Edit");
        if (currentTeam.isUnassignedTeam())
        {
            btnEdit.setVisible(false);
        }
        else
        {
            btnEdit.setVisible(true);
        }
        

        if (currentTeam.isUnassignedTeam())
        {
            for (TreeViewItem person : Global.currentWorkspace.getPeople())
            {
                Person castedPerson = (Person) person;
                if (castedPerson.getTeam() == null)
                {
                    currentTeam.add(castedPerson, false);
                }
            }
        }

        ObservableList<String> tempTeamString = TeamScene.convertToString(currentTeam.getPeople());
        
        ListView teamsPeopleBox = new ListView(tempTeamString);
        teamsPeopleBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        teamsPeopleBox.setMaxWidth(275);

        Separator separator = new Separator();

        informationPane.getChildren().add(title);
        informationPane.getChildren().add(new Label("Team Description: "
                + currentTeam.getDescription()));
        informationPane.getChildren().add(separator);
        if (currentTeam.getProductOwner() != null)
        {
            informationPane.getChildren().add(new Label("Product Owner: "
                    + currentTeam.getProductOwner().toString()));
        }
        else
        {
            informationPane.getChildren().add(new Label("Product Owner: "));
        }


        if (currentTeam.getScrumMaster() != null)
        {
            informationPane.getChildren().add(new Label("Scrum Master: "
                    + currentTeam.getScrumMaster().toString()));
        }
        else
        {
            informationPane.getChildren().add(new Label("Scrum Master: "));
        }

        informationPane.getChildren().add(new Label("Team Members: "));
        informationPane.getChildren().add(teamsPeopleBox);

        informationPane.getChildren().add(btnEdit);

        btnEdit.setOnAction((event) ->
            {
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.TEAM_EDIT, currentTeam);
            });
        
        return new ScrollPane(informationPane);
    }
    
    /*
    * Refreshes the information grid for the TeamScene
    * @param team Refreshes based on this Team
    */
    public static void refreshTeamScene(Team team)
    {
        SceneSwitcher.changeScene(SceneSwitcher.ContentScene.TEAM, team);
    }
    
    /*
    * Sorts a list of people based on their roles on a team, PO first, SM second, Dev third
    * then lastly goes people without a role.
    * @param currentTeam The team to sort by.
    * @return Returns sorted list.
    */
    public static ObservableList<Person> sortListView(ObservableList<Person> currentTeam)
    {
        ObservableList<Person> teamList = observableArrayList();
        
        for (Person person : currentTeam)
        {
            if (person.getRole() != null)
            {
                if (person.getRole().toString().equals("Product Owner"))
                {
                    teamList.add(person);
                }
            }
        }
        
        for (Person person : currentTeam)
        {
            if (person.getRole() != null)
            {
                if (person.getRole().toString().equals("Scrum Master"))
                {
                    teamList.add(person);
                }
            }
        }
        
        for (Person person : currentTeam)
        {
            if (person.getRole() != null)
            {
                if (person.getRole().toString().equals("Development Team Member"))
                {
                    teamList.add(person);
                }
            }
        }
        
        for (Person person : currentTeam)
        {
            if (person.getRole() == null)
            {
                teamList.add(person);
            }
        }
        return teamList;
    }
 
    /*
    * @param currentTeam It is the team to be converted.
    * @return returns a List of the People converted to a List of their Shortnames
    */
    public static ObservableList<String> convertToString(ObservableList<Person> currentTeam)
    {
        ObservableList<String> currentStringTeam = observableArrayList();
        
        
        for (Person person : currentTeam)
        {
            if (person.getRole() != null)
            {
                if (person.getRole().toString().equals("Product Owner"))
                {
                    currentStringTeam.add(person.toString() + " (" + person.getRole() + ")");
                }
            }
        }

        for (Person person : currentTeam)
        {
            if (person.getRole() != null)
            {
                if (person.getRole().toString().equals("Scrum Master"))
                {
                    currentStringTeam.add(person.toString() + " (" + person.getRole() + ")");
                }
            }
        }

        for (Person person : currentTeam)
        {
            if (person.getRole() != null)
            {
                if (person.getRole().toString().equals("Development Team Member"))
                {
                    currentStringTeam.add(person.toString() + " (" + person.getRole() + ")");
                }
            }
        }

        for (Person person : currentTeam)
        {
            if (person.getRole() == null)
            {
                currentStringTeam.add(person.toString());
            }
        }

	return currentStringTeam;
    }
}
