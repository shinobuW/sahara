/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.project.scenes.menu;

import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import seng302.group2.App;
import seng302.group2.project.Project;

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
        newProjectItem.setOnAction((event) ->
            {
                App.currentProject = Project.newProject();
                App.refreshMainScene();
            });
        newProjectBranch.getItems().add(newProjectItem);

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
                    System.out.println("TODO: File doesn't exist");
                }
                catch (IOException e)
                {
                    System.out.println("TODO: Error reading from file");
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
                    System.out.println("TODO: Error writing to file");
                }
            });
        
        // Create 'Quit' MenuItem
        MenuItem quitProgramItem = new MenuItem("Quit");
        quitProgramItem.setOnAction((event) ->
            {
                System.exit(0);
            });
        
        // Add MenuItems to Menu
        fileMenu.getItems().addAll(newProjectBranch, openItem,
                saveItem, new SeparatorMenuItem(), quitProgramItem);
        
        return menuBar;
    }
}
