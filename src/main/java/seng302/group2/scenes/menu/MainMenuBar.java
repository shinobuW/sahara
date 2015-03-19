/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.menu;

import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import seng302.group2.App;
import seng302.group2.project.Project;
import seng302.group2.project.Project.SaveLoadResult;

/**
 *
 * @author Jordane Lew (jml168)
 */
public class MainMenuBar
{
    public static MenuBar getMainMenuBar()
    {
    // The menus and menu bar creation
        MenuBar menuBar = new MenuBar();
        
        Menu fileMenu = new Menu("File");
        menuBar.getMenus().add(fileMenu);
        
        // Create 'New >' sub-menu
        Menu newProjectBranch = new Menu("New");

        MenuItem newProjectItem = new MenuItem("Project");
        newProjectItem.setOnAction((ActionEvent event) ->
            {
                if (App.currentProject == null)
                {
                    App.currentProject = new Project();
                    App.refreshMainScene();
                    return;
                }
                
                Action response = Dialogs.create()
                    .title("Save Project?")
                    .message("Would you like to save your changes to the current project?")
                    .showConfirm();
                
                if (response == Dialog.ACTION_YES)
                {
                    SaveLoadResult saved = Project.saveProject(App.currentProject);
                    if (saved == SaveLoadResult.SUCCESS)
                    {
                        CreateProjectDialog.show();
                        App.refreshMainScene();
                    }
                }
                else if (response == Dialog.ACTION_NO)
                {
                    CreateProjectDialog.show();
                    App.refreshMainScene();
                }
            });
        newProjectBranch.getItems().add(newProjectItem);
        
        MenuItem newPersonItem = new MenuItem("Person");
        newPersonItem.setOnAction((event) -> 
            {
                CreatePersonDialog.show();
            });
        
       
        newProjectBranch.getItems().add(newPersonItem);
        
        

        // Create 'Open' MenuItem
        MenuItem openItem = new MenuItem("Open");
        openItem.setOnAction((event) ->
            {
                Project.loadProject();
            });
        
        // Create 'Open' MenuItem
        MenuItem saveItem = new MenuItem("Save");
        saveItem.setOnAction((event) ->
            {
                Project.saveProject(App.currentProject);
            });
        
        // Create 'Quit' MenuItem
        MenuItem quitProgramItem = new MenuItem("Quit");
        quitProgramItem.setOnAction((event) ->
            {
                Action response = Dialogs.create()
                    .title("Save Project?")
                    .message("Would you like to save your changes to the current project?")
                    .showConfirm();
                
                if (response == Dialog.ACTION_YES)
                {
                    SaveLoadResult saved = Project.saveProject(App.currentProject);
                    if (saved == SaveLoadResult.SUCCESS)
                    {
                        System.exit(0);
                    }
                }
                else if (response == Dialog.ACTION_NO)
                {
                    System.exit(0);
                }
            });
        
        // Add MenuItems to Menu
        fileMenu.getItems().addAll(newProjectBranch, openItem,
                saveItem, new SeparatorMenuItem(), quitProgramItem);
        
        return menuBar;
    }
}
