package seng302.group2.scenes;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.information.person.PersonScene;
import seng302.group2.scenes.information.project.ProjectScene;
import seng302.group2.scenes.information.role.RoleScene;
import seng302.group2.scenes.information.skill.SkillScene;
import seng302.group2.scenes.information.team.TeamScene;
import seng302.group2.scenes.information.workspace.WorkspaceScene;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.listdisplay.TreeViewWithItems;
import seng302.group2.scenes.menu.MainMenuBar;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

import java.util.UUID;

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
    public static ScrollPane contentPane = new ScrollPane();
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

        if (Global.selectedTreeItem == null || Global.selectedTreeItem.getValue() == null)
        {
            treeView.selectItem(Global.currentWorkspace);
            contentPane.setContent(new WorkspaceScene(
                    Global.currentWorkspace));
        }
        else if (Global.selectedTreeItem.getValue() instanceof Workspace)
        {
            contentPane.setContent(new WorkspaceScene(
                    (Workspace) Global.selectedTreeItem.getValue()));
        }
        else if (Global.selectedTreeItem.getValue() instanceof Project)
        {
            contentPane.setContent(new ProjectScene((Project) Global.selectedTreeItem.getValue()));
        }
        else if (Global.selectedTreeItem.getValue() instanceof Person)
        {
            contentPane.setContent(new PersonScene((Person) Global.selectedTreeItem.getValue()));
        }
        else if (Global.selectedTreeItem.getValue() instanceof Skill)
        {
            contentPane.setContent(new SkillScene((Skill) Global.selectedTreeItem.getValue()));
        }
        else if (Global.selectedTreeItem.getValue() instanceof Team)
        {
            contentPane.setContent(new TeamScene((Team) Global.selectedTreeItem.getValue()));
        }
        else if (Global.selectedTreeItem.getValue() instanceof Role)
        {
            contentPane.setContent(new RoleScene((Role) Global.selectedTreeItem.getValue()));
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

        contentPane = new ScrollPane(informationPane);
        contentPane.setFitToHeight(true);
        contentPane.setFitToWidth(true);
        contentPane.setStyle("-fx-background-color:transparent;");

        content.boundsInParentProperty();
        informationPane.boundsInParentProperty();

        content.getItems().removeAll(treeView, informationPane);
        //content.getChildren().removeAll(treeView, informationPane);

        if (!menuHidden)
        {
            content.getItems().add(treeView);
            //content.getChildren().add(treeView);
        }
        content.getItems().add(contentPane);


        //root.getChildren().remove(content);
        root.setCenter(content);

        content.setDividerPositions(0.225);
        Platform.runLater(()->content.setDividerPositions(0.225));

        return new Scene(root);
    }
}
