/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.listdisplay.TreeViewWithItems;
import seng302.group2.workspace.role.Role;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.Global.selectedTreeItem;
import static seng302.group2.scenes.MainScene.informationGrid;

/**
 * @author Shinobu
 */
public class RoleScene
{  
    /**
     * Gets the Role information scene
     * @param currentRole the selected role 
     * @return The Role information scene
     */
    public static Pane getRoleScene(Role currentRole)
    {
        informationGrid = new VBox(10);
        
        /*informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);*/
        informationGrid.setPadding(new Insets(25,25,25,25));
        Label title = new Label(currentRole.getShortName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        ListView skillsBox = new ListView(currentRole.getRequiredSkills());
        skillsBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        skillsBox.setMaxWidth(275);

        informationGrid.getChildren().add(title);
        informationGrid.getChildren().add(new Label("Role Description: "));
        informationGrid.getChildren().add(new Label("Required Skills: "));

        informationGrid.getChildren().add(new Label(currentRole.getDescription()));
        informationGrid.getChildren().add(skillsBox);
            
        return MainScene.informationGrid;
    }
    
    public static void refreshRoleScene(Role role)
    {
	App.content.getChildren().remove(MainScene.informationGrid);
	App.content.getChildren().remove(MainScene.treeView);
	RoleScene.getRoleScene(role);
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
