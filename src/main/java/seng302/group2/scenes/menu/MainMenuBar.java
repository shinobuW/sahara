/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.menu;

import java.io.FileNotFoundException;
import java.io.IOException;
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
import seng302.group2.project.team.person.Person;

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
                Action response = Dialogs.create()
                    .title("Save Project?")
                    .message("Would you like to save your changes to the current project?")
                    .showConfirm();
                
                if (response == Dialog.ACTION_YES)
                {
                    try
                    {
                        Project.saveCurrentProject();
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                    App.currentProject = new Project();
                    App.refreshMainScene();
                }
                else if (response == Dialog.ACTION_NO)
                {
                    App.currentProject = new Project();
                    App.refreshMainScene();
                }
            });
        newProjectBranch.getItems().add(newProjectItem);
        
        MenuItem newPersonItem = new MenuItem("Person");
        newPersonItem.setOnAction((event) -> 
            {
                App.currentProject.addPerson(new Person());
            });
        
       
        newProjectBranch.getItems().add(newPersonItem);
        
        

        // Create 'Open' MenuItem
        MenuItem openItem = new MenuItem("Open");
        openItem.setOnAction((event) ->
            {
                try
                {
                    Project.loadProject();
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            });
        
        // Create 'Open' MenuItem
        MenuItem saveItem = new MenuItem("Save");
        saveItem.setOnAction((event) ->
            {
                try
                {
                    Project.saveCurrentProject();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
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
                    try
                    {
                        Project.saveCurrentProject();
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                    System.exit(0);
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
