/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import static seng302.group2.App.content;
import seng302.group2.Global;
import seng302.group2.project.Project;
import seng302.group2.project.skills.Skill;
import seng302.group2.project.team.person.Person;
import seng302.group2.scenes.information.PersonScene;
import seng302.group2.scenes.information.ProjectScene;
import seng302.group2.scenes.information.SkillScene;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.listdisplay.TreeViewWithItems;
import seng302.group2.scenes.menu.MainMenuBar;


/**
 * A class for holding JavaFX scenes used in the project
 * @author Jordane Lew (jml168)
 */
public class MainScene
{
    public static TreeViewWithItems treeView = new TreeViewWithItems(new TreeItem());
    public static GridPane informationGrid = new GridPane();
    
    public static Scene getMainScene()
    {
        // The root window box
        VBox root = new VBox();
        MenuBar menuBar = MainMenuBar.getMainMenuBar();
        root.getChildren().add(new StackPane(menuBar));
        
        if (Global.selectedTreeItem.getValue() instanceof Project)
        {
            ProjectScene.getProjectScene();
        }
        else if (Global.selectedTreeItem.getValue() instanceof Person)
        {
            PersonScene.getPersonScene();
        }
        else if (Global.selectedTreeItem.getValue() instanceof Skill)
        {
            SkillScene.getSkillScene();
        }
        
        // Old: TreeView display = ListDisplay.getProjectTree();  // (Manual)
        // Create the display menu from the project tree
        
        ObservableList<TreeViewItem> children = observableArrayList();

        
        children.add(Global.currentProject);
        
        treeView.setItems(children);
        treeView.setShowRoot(false);
        
        root.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0,
                    Number arg1, Number arg2) 
            {
                App.content.setPrefHeight(arg2.doubleValue());
            }
        });
        
        content.boundsInParentProperty();
        informationGrid.boundsInParentProperty();

        content.getChildren().removeAll(treeView, informationGrid);
        content.getChildren().addAll(treeView, informationGrid);

        root.getChildren().remove(content);
        root.getChildren().add(content);

        return new Scene(root);
    }
}
