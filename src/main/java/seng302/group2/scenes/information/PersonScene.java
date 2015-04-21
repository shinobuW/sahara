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


import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.Global.selectedTreeItem;
import static seng302.group2.scenes.MainScene.informationGrid;

/**
 * A class for displaying the Person Scene
 * @author crw73
 */
public class PersonScene
{
    
    /**
     * Gets the Person information scene
     * @return The Person information scene
     */
    public static GridPane getPersonScene(Person currentPerson)
    {
        informationGrid = new GridPane();
        
        informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);
        informationGrid.setPadding(new Insets(25,25,25,25));
        Label title = new Label(currentPerson.getFirstName() + " " + currentPerson.getLastName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnEdit = new Button("Edit");
    
        ListView personSkillsBox = new ListView(currentPerson.getSkills());
        personSkillsBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        final Separator separator = new Separator();
        
        informationGrid.add(title, 0, 0, 3, 1);
        informationGrid.add(new Label("Short Name: "), 0, 2);
        informationGrid.add(new Label("Email Address: "), 0, 3);
        informationGrid.add(new Label("Birth Date: "), 0, 4);
        informationGrid.add(new Label("Description: "), 0, 5);
        informationGrid.add(new Label("Team: "), 0, 6);
        informationGrid.add(new Label("Role: "), 0, 7);
        informationGrid.add(separator, 0, 8, 4, 1);
        informationGrid.add(new Label("Skills: "), 0, 9);
        informationGrid.add(personSkillsBox, 0, 10, 2, 1);
        
        informationGrid.add(new Label(currentPerson.getShortName()), 1, 2, 5, 1);
        informationGrid.add(new Label(currentPerson.getEmail()), 1, 3, 5, 1);

        informationGrid.add(new Label(currentPerson.getDateString()), 1, 4, 5, 1);

        informationGrid.add(new Label(currentPerson.getDescription()), 1, 5, 5, 1);
        informationGrid.add(new Label(currentPerson.getTeamName()), 1, 6, 5, 1);
        
        String roleString = currentPerson.getRole() == null ? "" : 
                currentPerson.getRole().toString();
        informationGrid.add(new Label(roleString), 1, 7, 5, 1);
        informationGrid.add(btnEdit, 3, 11);
        
        btnEdit.setOnAction((event) ->
            {
                App.content.getChildren().remove(MainScene.informationGrid);
                PersonEditScene.getPersonEditScene(currentPerson);
                App.content.getChildren().add(MainScene.informationGrid);
                
            });

        return MainScene.informationGrid;
    }

    
    public static void refreshPersonScene(Person person)
    {
	App.content.getChildren().remove(MainScene.informationGrid);
	App.content.getChildren().remove(MainScene.treeView);
	PersonScene.getPersonScene(person);
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
