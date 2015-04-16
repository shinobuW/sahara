/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information;

import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.Global;
import static seng302.group2.Global.selectedTreeItem;
import seng302.group2.project.team.Team;
import seng302.group2.project.team.person.Person;
import seng302.group2.scenes.MainScene;
import static seng302.group2.scenes.MainScene.informationGrid;
import static seng302.group2.scenes.MainScene.treeView;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.listdisplay.TreeViewWithItems;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * A class for displaying the team edit scene.
 * @author crw73
 */
public class TeamEditScene
{
    /**
     * Gets the Team Edit information scene.
     * @return The Team Edit information scene
     */
    public static GridPane getTeamEditScene(Team currentTeam)
    {

        //Team currentTeam = (Team) selectedTreeItem.getValue();
        informationGrid = new GridPane();
        informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);
        informationGrid.setPadding(new Insets(25,25,25,25));

        Button btnCancel = new Button("Cancel");
        Button btnSave = new Button("Save");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(btnSave, btnCancel);

        RequiredField shortNameCustomField = new RequiredField("Short Name");
        CustomTextArea descriptionTextArea = new CustomTextArea("Skill Description", 300);
        
        shortNameCustomField.setText(currentTeam.getShortName());
        descriptionTextArea.setText(currentTeam.getDescription());
        
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

        informationGrid.add(shortNameCustomField, 0, 0);
        informationGrid.add(descriptionTextArea, 0, 1);
        informationGrid.add(buttons, 0,3);
        informationGrid.add(teamsPeopleBox, 0, 2);


        informationGrid.add(peopleButtons, 1, 2);
        
        informationGrid.add(membersBox, 2, 2);
        
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
        
        btnCancel.setOnAction((event) ->
            {
                App.content.getChildren().remove(informationGrid);
                TeamScene.getTeamScene((Team) Global.selectedTreeItem.getValue());
                App.content.getChildren().add(informationGrid);

            });

        btnSave.setOnAction((event) ->
            {
                boolean correctShortName = validateShortName(shortNameCustomField);


                if (correctShortName)
                {
                    currentTeam.setDescription(descriptionTextArea.getText());
                    currentTeam.setShortName(shortNameCustomField.getText());
                    App.content.getChildren().remove(treeView);
                    App.content.getChildren().remove(informationGrid);
                    TeamScene.getTeamScene((Team) Global.selectedTreeItem.getValue());
                    MainScene.treeView = new TreeViewWithItems(new TreeItem());
                    ObservableList<TreeViewItem> children = observableArrayList();
                    children.add(Global.currentProject);

                    MainScene.treeView.setItems(children);
                    MainScene.treeView.setShowRoot(false);

                    App.content.getChildren().add(treeView);
                    App.content.getChildren().add(informationGrid);
                    MainScene.treeView.getSelectionModel().select(selectedTreeItem);

                }
                else
                {
                    event.consume();
                }

            });

        return informationGrid;
    }
}
