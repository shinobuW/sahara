/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes;

import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import static seng302.group2.App.content;
import static seng302.group2.App.informationGrid;
import seng302.group2.project.Project;
import seng302.group2.project.team.person.Person;
import seng302.group2.scenes.information.PersonScene;
import seng302.group2.scenes.information.ProjectScene;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.listdisplay.TreeViewWithItems;
import seng302.group2.scenes.menu.MainMenuBar;


/**
 * A class for holding JavaFX scenes used in the project
 * @author Jordane Lew (jml168)
 */
public class MainScene
{
    public static Scene getMainScene()
    {
        // The root window box
        VBox root = new VBox();
        
        MenuBar menuBar = MainMenuBar.getMainMenuBar();
        root.getChildren().add(new StackPane(menuBar));
        
        if (App.selectedTreeItem.getValue() instanceof Project)
        {
            ProjectScene.getProjectScene();
        }
        else if (App.selectedTreeItem.getValue() instanceof Person)
        {
            PersonScene.getPersonScene();
        }
        

        // Old: TreeView display = ListDisplay.getProjectTree();  // (Manual)
        // Create the display menu from the project tree
        TreeViewWithItems treeView = new TreeViewWithItems(new TreeItem());
        ObservableList<TreeViewItem> children = observableArrayList();

        
        children.add(App.currentProject);
        
        treeView.setItems(children);
        treeView.setShowRoot(false);
        
        content.getChildren().add(treeView);
        content.getChildren().add(informationGrid);
        root.getChildren().add(content);

        return new Scene(root);
    }
}
