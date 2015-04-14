package seng302.group2.scenes.information;

import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.project.team.Team;
import seng302.group2.project.team.person.Person;
import static seng302.group2.scenes.MainScene.informationGrid;
import seng302.group2.scenes.listdisplay.TreeViewItem;

/**
 * A class for displaying the team scene.
 * @author crw73
 */
public class TeamScene
{
     /**
     * Gets the Team information scene.
     * @return The Team information scene
     */
    public static GridPane getTeamScene(Team currentTeam)
    {
        
        informationGrid = new GridPane();

        informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);
        informationGrid.setPadding(new Insets(25,25,25,25));
        Label title = new Label(currentTeam.getShortName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnEdit = new Button("Edit");
        Button btnAdd = new Button("<-");
        Button btnDelete = new Button("->");
        
        VBox peopleButtons = new VBox();
        peopleButtons.getChildren().add(btnAdd);
        peopleButtons.getChildren().add(btnDelete);
        
        
        ListView teamsPeopleBox = new ListView(currentTeam.getPeople());
        teamsPeopleBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        
        ObservableList<Person> dialogSkills = observableArrayList();
        for (TreeViewItem projectPerson : Global.currentProject.getPeople())
        {
            if (!currentTeam.getPeople().contains(projectPerson))
            {
                dialogSkills.add((Person)projectPerson);
            }
        }
                
        ListView membersBox = new ListView(dialogSkills);
        membersBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        informationGrid.add(title, 0, 0, 3, 1);
        informationGrid.add(new Label("Description: "), 0, 2);
        informationGrid.add(teamsPeopleBox, 0, 3);

        
        informationGrid.add(new Label(currentTeam.getDescription()), 1, 2);
        informationGrid.add(btnEdit, 1, 4);
        informationGrid.add(peopleButtons, 1, 3);
        
        informationGrid.add(membersBox, 2, 3);

        
        
        btnAdd.setOnAction((event) ->
            {
                ObservableList<Person> selectedPeople = 
                        membersBox.getSelectionModel().getSelectedItems();
                for (Person item : selectedPeople)
                {
                    currentTeam.addPerson(item);
                }
                
                dialogSkills.clear();
                for (TreeViewItem projectPeople : Global.currentProject.getPeople())
                {
                    if (!currentTeam.getPeople().contains((Person)projectPeople))
                    {
                        dialogSkills.add((Person)projectPeople);
                    }
                }
            });
        
        btnDelete.setOnAction((event) ->
            {
                ObservableList<Person> selectedPeople = 
                        teamsPeopleBox.getSelectionModel().getSelectedItems();
                System.out.println(selectedPeople.size());
                for (int i = selectedPeople.size() - 1; i >= 0 ; i--)
                {
                    currentTeam.removePerson(selectedPeople.get(i));
                }
                
                dialogSkills.clear();
                for (TreeViewItem projectPeople : Global.currentProject.getPeople())
                {
                    if (!currentTeam.getPeople().contains((Person)projectPeople))
                    {
                        dialogSkills.add((Person)projectPeople);
                    }
                }
            });        
        
        btnEdit.setOnAction((event) ->
            {
                App.content.getChildren().remove(informationGrid);
                TeamEditScene.getTeamEditScene();
                App.content.getChildren().add(informationGrid);
            });

        return informationGrid;
    }
 
}
