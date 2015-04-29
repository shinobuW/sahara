package seng302.group2.scenes.information;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.listdisplay.TreeViewWithItems;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.team.Team;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.Global.selectedTreeItem;
import static seng302.group2.scenes.MainScene.informationGrid;

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
        ListView teamsPeopleBox = new ListView(currentTeam.getPeople());
        
        teamsPeopleBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        ObservableList<Person> dialogPeople = observableArrayList();
        for (TreeViewItem projectPerson : Global.currentWorkspace.getPeople())
        {
            if (!currentTeam.getPeople().contains(projectPerson))
            {
                dialogPeople.add((Person)projectPerson);
            }
        }

        Separator separator = new Separator();

        informationGrid.add(title, 0, 0, 3, 1);
        informationGrid.add(new Label("Description: "), 0, 2);
        informationGrid.add(separator, 0, 3, 4, 1);
        informationGrid.add(new Label("Development Team Members: "), 0, 4);
        informationGrid.add(new Label("Team Members (No Role): "), 1, 4);
        informationGrid.add(teamsPeopleBox, 0, 5, 1, 1);
        
        informationGrid.add(new Label(currentTeam.getDescription()), 1, 2, 5, 1);
        informationGrid.add(btnEdit, 3, 6);

        btnEdit.setOnAction((event) ->
            {
                App.content.getChildren().remove(informationGrid);
                TeamEditScene.getTeamEditScene(currentTeam);
                App.content.getChildren().add(informationGrid);
            });
        
        return informationGrid;
    }
    
    public static void refreshTeamScene(Team team)
    {
	App.content.getChildren().remove(MainScene.informationGrid);
	App.content.getChildren().remove(MainScene.treeView);
	TeamScene.getTeamScene(team);
	MainScene.treeView = new TreeViewWithItems(new TreeItem());
	ObservableList<TreeViewItem> children = observableArrayList();
	children.add(Global.currentWorkspace);

	MainScene.treeView.setItems(children);
	MainScene.treeView.setShowRoot(false);
	App.content.getChildren().add(MainScene.treeView);
	App.content.getChildren().add(MainScene.informationGrid);
	MainScene.treeView.getSelectionModel().select(selectedTreeItem);
    }
 
}
