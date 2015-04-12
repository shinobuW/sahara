/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information;

import java.text.SimpleDateFormat;
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
import static seng302.group2.Global.currentProject;
import static seng302.group2.Global.selectedTreeItem;
import seng302.group2.project.skills.Skill;
import seng302.group2.project.team.person.Person;
import seng302.group2.scenes.MainScene;
import static seng302.group2.scenes.MainScene.informationGrid;
import seng302.group2.scenes.listdisplay.TreeViewItem;

/**
 * A class for displaying the Person Scene
 * @author crw73
 */
public class PersonScene
{
    
    /**
     * Gets the Person information display
     * @return The Person information display
     */
    public static GridPane getPersonScene()
    {
        Person currentPerson = (Person) selectedTreeItem.getValue();

        informationGrid = new GridPane();
        
        informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);
        informationGrid.setPadding(new Insets(25,25,25,25));
        Label title = new Label(currentPerson.getFirstName() + " " + currentPerson.getLastName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnEdit = new Button("Edit");
        Button btnAdd = new Button("<-");
        Button btnDelete = new Button("->");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        VBox skillsButtons = new VBox();
        skillsButtons.getChildren().add(btnAdd);
        skillsButtons.getChildren().add(btnDelete);
        
        
        ListView personSkillsBox = new ListView(currentPerson.getSkills());
        personSkillsBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        
        ObservableList<Skill> dialogSkills = observableArrayList();
        for (TreeViewItem projectSkill : currentProject.getSkills())
        {
            if (!currentPerson.getSkills().contains(projectSkill))
            {
                dialogSkills.add((Skill)projectSkill);
            }
        }
                
        ListView skillsBox = new ListView(dialogSkills);
        skillsBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        
        informationGrid.add(title, 0, 0, 3, 1);
        informationGrid.add(new Label("Short Name: "), 0, 2);
        informationGrid.add(new Label("Email Address: "), 0, 3);
        informationGrid.add(new Label("Birth Date: "), 0, 4);
        informationGrid.add(new Label("Description: "), 0, 5);
        informationGrid.add(personSkillsBox, 0, 6);
        
        informationGrid.add(new Label(currentPerson.getShortName()), 1, 2);
        informationGrid.add(new Label(currentPerson.getEmail()), 1,3);
        informationGrid.add(new Label(dateFormat.format(currentPerson.getBirthDate())), 1, 4);
        informationGrid.add(new Label(currentPerson.getDescription()), 1, 5);
        informationGrid.add(skillsButtons,1,6);
        informationGrid.add(btnEdit,1,8);

        informationGrid.add(skillsBox, 2, 6);
        
        btnAdd.setOnAction((event) ->
            {
                ObservableList<Skill> selectedSkills = 
                        skillsBox.getSelectionModel().getSelectedItems();
                
                for (Skill item : selectedSkills)
                {
                    currentPerson.addSkill(item);
                }
                
                dialogSkills.clear();
                for (TreeViewItem projectSkill : currentProject.getSkills())
                {
                    if (!currentPerson.getSkills().contains((Skill)projectSkill))
                    {
                        dialogSkills.add((Skill)projectSkill);
                    }
                }
            });
        
        btnDelete.setOnAction((event) ->
            {
                ObservableList<Skill> selectedSkills = 
                        personSkillsBox.getSelectionModel().getSelectedItems();

                for (int i = selectedSkills.size() - 1; i >= 0 ; i--)
                {
                    currentPerson.removeSkill(selectedSkills.get(i));
                }
                
                dialogSkills.clear();
                for (TreeViewItem projectSkill : currentProject.getSkills())
                {
                    if (!currentPerson.getSkills().contains((Skill)projectSkill))
                    {
                        dialogSkills.add((Skill)projectSkill);
                    }
                }
            });
        
        btnEdit.setOnAction((event) ->
            {
                App.content.getChildren().remove(MainScene.informationGrid);
                PersonEditScene.getPersonEditScene();
                App.content.getChildren().add(MainScene.informationGrid);
                
            });

        return MainScene.informationGrid;
    }

}
