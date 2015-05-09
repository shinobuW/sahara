package seng302.group2.scenes;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.information.*;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.listdisplay.TreeViewWithItems;
import seng302.group2.scenes.menu.MainMenuBar;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.App.content;


/**
 * A class for holding JavaFX scenes used in the workspace
 * @author Jordane Lew (jml168)
 */
public class MainScene
{
    public static TreeViewWithItems treeView = new TreeViewWithItems(new TreeItem());
    public static Pane informationPane = new Pane();
    public static boolean menuHidden = false;
    
    /**
     * Gets the Main Scene for display in the GUI
     * @return The Main Scene.
     */
    public static Scene getMainScene()
    {
        // The root window box
        BorderPane root = new BorderPane();
        MenuBar menuBar = MainMenuBar.getMainMenuBar();
        root.setTop(menuBar);
        //root.getChildren().add(new StackPane(menuBar));

        if (Global.selectedTreeItem == null)
        {
            treeView.selectItem(Global.currentWorkspace);
        }
        
        if (Global.selectedTreeItem.getValue() instanceof Workspace)
        {
            WorkspaceScene.getWorkspaceScene((Workspace) Global.selectedTreeItem.getValue());
        }
        else if (Global.selectedTreeItem.getValue() instanceof Person)
        {
            PersonScene.getPersonScene((Person) Global.selectedTreeItem.getValue());
        }
        else if (Global.selectedTreeItem.getValue() instanceof Skill)
        {
            SkillScene.getSkillScene((Skill) Global.selectedTreeItem.getValue());
        }
        else if (Global.selectedTreeItem.getValue() instanceof Team)
        {
            TeamScene.getTeamScene((Team) Global.selectedTreeItem.getValue());
        }
        else if (Global.selectedTreeItem.getValue() instanceof Role)
        {
            RoleScene.getRoleScene((Role) Global.selectedTreeItem.getValue());
        }
        

        // Create the display menu from the workspace tree
        
        ObservableList<TreeViewItem> children = observableArrayList();
        children.add(Global.currentWorkspace);

        
        treeView.setItems(children);
        treeView.setShowRoot(false);

        
        root.heightProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> arg0,
                                Number arg1, Number arg2)
            {
                App.content.setPrefHeight(arg2.doubleValue());
            }
        });

        content.boundsInParentProperty();
        informationPane.boundsInParentProperty();

        content.getItems().removeAll(treeView, informationPane);
        //content.getChildren().removeAll(treeView, informationPane);

        if (!menuHidden)
        {
            content.getItems().add(treeView);
            //content.getChildren().add(treeView);
        }
        content.getItems().add(informationPane);

        //content.getChildren().add(informationPane);

        //root.getChildren().remove(content);
        root.setCenter(content);

        Platform.runLater(()->content.setDividerPositions(0.225));

        return new Scene(root);
    }
}
