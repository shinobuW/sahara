/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.menu;

import seng302.group2.scenes.dialog.CreateProjectDialog;
import seng302.group2.scenes.dialog.CreatePersonDialog;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.project.Project;
import seng302.group2.project.Project.SaveLoadResult;

/**
 * The main menu bar of the project window(s).
 * @author Jordane Lew (jml168)
 */
public class MainMenuBar
{
    
    private static MenuItem createProjectItem() 
    {
        MenuItem newProjectItem = new MenuItem("Project");
        newProjectItem.setOnAction((ActionEvent event) ->
            {
                if (Global.currentProject == null || !Global.currentProject.getHasUnsavedChanges())
                {
                    CreateProjectDialog.show();
                    App.refreshMainScene();
                    Global.undoRedoMan.emptyAll();
                    return;
                }
               
                Action response = Dialogs.create()
                    .title("Save Project?")
                    .message("Would you like to save your changes to the current project?")
                    .showConfirm();
                
                if (response == Dialog.ACTION_YES)
                {
                    SaveLoadResult saved = Project.saveProject(Global.currentProject, false);
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
        return newProjectItem;
    }
    
    
    private static MenuItem createPersonItem() 
    {
        MenuItem newPersonItem = new MenuItem("Person");
        newPersonItem.setOnAction((event) -> 
            {
                CreatePersonDialog.show();
            });
        return newPersonItem;
    }
    
    
    private static MenuItem createOpenItem()
    {
        // Create 'Open' MenuItem
        MenuItem openItem = new MenuItem("Open");
        openItem.setOnAction((event) ->
            {
                if (!Global.currentProject.getHasUnsavedChanges())
                {
                    Project.loadProject();
                    return;
                }
                Action response = Dialogs.create()
                    .title("Save Project?")
                    .message("Would you like to save your changes to the current project?")
                    .showConfirm();
                
                if (response == Dialog.ACTION_YES)
                {
                    SaveLoadResult saved = Project.saveProject(Global.currentProject, false);
                    if (saved == SaveLoadResult.SUCCESS)
                    {
                        Project.loadProject();
                        //App.refreshMainScene();
                    }
                }
                else if (response == Dialog.ACTION_NO)
                {
                    Project.loadProject();
                    //App.refreshMainScene();
                }   
            });
        return openItem;
    }
    
    
    private static MenuItem createSaveItem() 
    {
        // Create 'Save' MenuItem
        MenuItem saveItem = new MenuItem("Save");
        saveItem.setOnAction((event) ->
            {
                Project.saveProject(Global.currentProject, false);
            });
        
        return saveItem;
    }
    
    
    private static MenuItem createSaveAsItem()
    {
        // Create 'Save As' MenuItem
        MenuItem saveAsItem = new MenuItem("Save As...");
        saveAsItem.setOnAction((event) ->
            {
                Project.saveProject(Global.currentProject, true);
            });
        
        return saveAsItem;
    }
    
    
    private static MenuItem createQuitItem()
    {
        MenuItem quitProgramItem = new MenuItem("Quit");
        quitProgramItem.setOnAction((event) ->
            {
                if (!Global.currentProject.getHasUnsavedChanges())
                {
                    System.exit(0);
                }   
                Action response = Dialogs.create()
                    .title("Save Project?")
                    .message("Would you like to save your changes to the current project?")
                    .showConfirm();

                if (response == Dialog.ACTION_YES)
                {
                    SaveLoadResult saved = Project.saveProject(Global.currentProject, false);
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
        
        return quitProgramItem;
    }
    
    
    private static MenuItem createUndoItem()
    {
        // Create 'Undo' MenuItem
        MenuItem undoItem = new MenuItem("Undo");
        undoItem.setOnAction((event) ->
            {
                Global.undoRedoMan.undo();
            });
        
        return undoItem;
    }
    
    
    private static MenuItem createRedoItem()
    {
        // Create 'Redo' MenuItem
        MenuItem redoItem = new MenuItem("Redo");
        redoItem.setOnAction((event) ->
            {
                Global.undoRedoMan.redo();
            });
        
        return redoItem;
    }
    
    /**
     * Creates the Menu Bar for the Scene
     * @return The MenuBar with all required items.
     */
    public static MenuBar getMainMenuBar()
    {
    // The menus and menu bar creation
        MenuBar menuBar = new MenuBar();
        
        Menu fileMenu = new Menu("File");
        menuBar.getMenus().add(fileMenu);

        // Create 'New >' sub-menu
        Menu newProjectBranch = new Menu("New");

        //Create MenuItems for New submenu
        MenuItem newProjectItem = createProjectItem();
        MenuItem newPersonItem = createPersonItem();
        MenuItem openItem = createOpenItem();
        MenuItem saveItem = createSaveItem();
        MenuItem saveAsItem = createSaveAsItem();
        MenuItem quitProgramItem = createQuitItem();
        
        newProjectBranch.getItems().add(newProjectItem);
        newProjectBranch.getItems().add(newPersonItem);

        // Create 'Edit >' sub-menu
        Menu editMenu = new Menu("Edit");
        menuBar.getMenus().add(editMenu);

        //Create MenuItems for Edit submenu
        MenuItem undoItem = createUndoItem();
        MenuItem redoItem = createRedoItem();
              
        // Add MenuItems to Menu
        fileMenu.getItems().addAll(newProjectBranch, openItem,
                saveItem, saveAsItem, new SeparatorMenuItem(), quitProgramItem);
        
        editMenu.getItems().addAll(undoItem, redoItem);
                
        editMenu.setOnShowing((event) ->
            {
                if (!Global.undoRedoMan.canRedo())
                {
                    redoItem.setDisable(true);
                }
                else
                {
                    redoItem.setDisable(false);
                }
                
                if (!Global.undoRedoMan.canUndo())
                {
                    undoItem.setDisable(true);
                }
                else
                {
                    undoItem.setDisable(false);
                }
            });
               
        return menuBar;
    }
}
