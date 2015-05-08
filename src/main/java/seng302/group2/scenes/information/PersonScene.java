package seng302.group2.scenes.information;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
     * @param currentPerson the person to display the information of
     * @return The Person information scene
     */
    public static Pane getPersonScene(Person currentPerson)
    {
        informationGrid = new VBox();

        /*informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);*/
        informationGrid.setPadding(new Insets(25,25,25,25));
        Label title = new Label(currentPerson.getFirstName() + " " + currentPerson.getLastName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnEdit = new Button("Edit");
    
        ListView personSkillsBox = new ListView(currentPerson.getSkills());
        personSkillsBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        final Separator separator = new Separator();
        
        informationGrid.getChildren().add(title);
        informationGrid.getChildren().add(new Label("Short Name: "));
        informationGrid.getChildren().add(new Label("Email Address: "));
        informationGrid.getChildren().add(new Label("Birth Date: "));
        informationGrid.getChildren().add(new Label("Person Description: "));
        informationGrid.getChildren().add(new Label("Team: "));
        informationGrid.getChildren().add(new Label("Role: "));
        informationGrid.getChildren().add(separator);
        informationGrid.getChildren().add(new Label("Skills: "));
        informationGrid.getChildren().add(personSkillsBox);
        
        informationGrid.getChildren().add(new Label(currentPerson.getShortName()));
        informationGrid.getChildren().add(new Label(currentPerson.getEmail()));

        informationGrid.getChildren().add(new Label(currentPerson.getDateString()));

        informationGrid.getChildren().add(new Label(currentPerson.getDescription()));
        informationGrid.getChildren().add(new Label(currentPerson.getTeamName()));
        
        String roleString = currentPerson.getRole() == null ? "" : 
                currentPerson.getRole().toString();
        informationGrid.getChildren().add(new Label(roleString));
        informationGrid.getChildren().add(btnEdit);
        
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
