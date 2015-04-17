package seng302.group2.scenes.menu;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import seng302.group2.scenes.MainScene;
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
import seng302.group2.scenes.dialog.CreateSkillDialog;
import seng302.group2.scenes.dialog.CreateTeamDialog;
import seng302.group2.util.config.ConfigLoader;

/**
 * The main menu bar of the project window(s).
 * @author Jordane Lew (jml168)
 */
@SuppressWarnings("deprecation")
public class MainMenuBar
{
    /**
     * Creates a menu item "Project" and sets the on action event if "Project" is clicked.
     * @return MenuItem Project
     */
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

        newProjectItem.setAccelerator(new KeyCodeCombination(KeyCode.N,
                KeyCombination.CONTROL_DOWN,
                KeyCombination.SHORTCUT_DOWN));
        return newProjectItem;
    }
    
    /**
     * Creates a menu item "Person" and sets the on action event if "Person" is clicked.
     * @return MenuItem Person
     */
    private static MenuItem createPersonItem() 
    {
        MenuItem newPersonItem = new MenuItem("Person");
        newPersonItem.setOnAction((event) -> 
            {
                CreatePersonDialog.show();
            });
        newPersonItem.setAccelerator(new KeyCodeCombination(KeyCode.P,
                KeyCombination.CONTROL_DOWN,
                KeyCombination.SHORTCUT_DOWN));
        return newPersonItem;
    }
    
    /**
     * Creates a menu item "Skill" and sets the on action event if "Skill" is clicked.
     * @return MenuItem Skill
     */
    private static MenuItem createSkillItem()
    {
        MenuItem newSkillItem = new MenuItem("Skill");
        newSkillItem.setOnAction((event) ->
            {
                CreateSkillDialog.show();
            });
        newSkillItem.setAccelerator(new KeyCodeCombination(KeyCode.K,
                KeyCombination.CONTROL_DOWN,
                KeyCombination.SHORTCUT_DOWN));
        return newSkillItem;
    }
    
    /**
     * Creates a menu item "Team" and sets the on action event if "Team" is clicked.
     * @return MenuItem Team
     */
    private static MenuItem createTeamItem()
    {
        MenuItem newTeamItem = new MenuItem("Team");
        newTeamItem.setOnAction((event) ->
            {
                CreateTeamDialog.show();
            });
        newTeamItem.setAccelerator(new KeyCodeCombination(KeyCode.T,
                KeyCombination.CONTROL_DOWN,
                KeyCombination.SHORTCUT_DOWN));
        return newTeamItem;
    }
    
    /**
     * Creates a menu item "Open" and sets the on action event if "Open" is clicked.
     * @return MenuItem Open
     */
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
        openItem.setAccelerator(new KeyCodeCombination(KeyCode.O,
                KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
        return openItem;
    }
    
    /**
     * Creates a menu item "Save" and sets the on action event if "Save" is clicked.
     * @return MenuItem Save
     */
    private static MenuItem createSaveItem() 
    {
        // Create 'Save' MenuItem
        MenuItem saveItem = new MenuItem("Save");
        saveItem.setOnAction((event) ->
            {
                Project.saveProject(Global.currentProject, false);
            });

        saveItem.setAccelerator(new KeyCodeCombination(KeyCode.S,
                KeyCombination.CONTROL_DOWN,
                KeyCombination.SHORTCUT_DOWN));
        return saveItem;
    }
    
    /**
     * Creates a menu item "SaveAs" and sets the on action event if "SaveAs" is clicked.
     * @return MenuItem SaveAs
     */
    private static MenuItem createSaveAsItem()
    {
        // Create 'Save As' MenuItem
        MenuItem saveAsItem = new MenuItem("Save As...");
        saveAsItem.setOnAction((event) ->
            {
                Project.saveProject(Global.currentProject, true);
            });

        saveAsItem.setAccelerator(new KeyCodeCombination(KeyCode.S,
                KeyCombination.CONTROL_DOWN,
                KeyCombination.SHIFT_DOWN,
                KeyCombination.SHORTCUT_DOWN));
        return saveAsItem;
    }
    
    /**
     * Creates a menu item "Quit" and sets the on action event if "Quit" is clicked.
     * @return MenuItem Quit
     */
    private static MenuItem createQuitItem()
    {
        MenuItem quitProgramItem = new MenuItem("Quit");
        quitProgramItem.setOnAction((event) ->
            {
                App.exitApp();
            });      
        
        return quitProgramItem;
    }
    
    /**
     * Creates a menu item "Undo" and sets the on action event if "Undo" is clicked.
     * @return MenuItem Undo
     */
    private static MenuItem createUndoItem()
    {
        // Create 'Undo' MenuItem
        MenuItem undoItem = new MenuItem("Undo");
        undoItem.setOnAction((event) ->
            {
                Global.undoRedoMan.undo();
            });

        undoItem.setAccelerator(new KeyCodeCombination(KeyCode.Z,
                KeyCombination.CONTROL_DOWN,
                KeyCombination.SHORTCUT_DOWN));
        return undoItem;
    }
    
    /**
     * Creates a menu item "Redo" and sets the on action event if "Redo" is clicked.
     * @return MenuItem Redo
     */
    private static MenuItem createRedoItem()
    {
        // Create 'Redo' MenuItem
        MenuItem redoItem = new MenuItem("Redo");
        redoItem.setOnAction((event) ->
            {
                Global.undoRedoMan.redo();
            });

        redoItem.setAccelerator(new KeyCodeCombination(KeyCode.Y,
                KeyCombination.CONTROL_DOWN,
                KeyCombination.SHORTCUT_DOWN));
        return redoItem;
    }

    /**
     * Creates a menu item "Toggle Tree" and sets the on action event if "Toggle Tree" is clicked.
     * @return MenuItem ToggleTree
     */
    private static MenuItem createToggleTreeItem()
    {
        MenuItem toggleItem = new MenuItem("Toggle Menu");
        toggleItem.setOnAction((event) ->
            {
                if (Global.appRunning())
                {
                    if (MainScene.menuHidden)
                    {
                        MainScene.menuHidden = false;
                    }
                    else
                    {
                        MainScene.menuHidden = true;
                    }
                    App.refreshMainScene();
                }
            });

        toggleItem.setAccelerator(new KeyCodeCombination(KeyCode.M,
                KeyCombination.CONTROL_DOWN,
                KeyCombination.SHORTCUT_DOWN));
        return toggleItem;
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
        MenuItem newSkillItem = createSkillItem();
        MenuItem newTeamItem = createTeamItem();
        MenuItem openItem = createOpenItem();
        MenuItem saveItem = createSaveItem();
        MenuItem saveAsItem = createSaveAsItem();
        MenuItem quitProgramItem = createQuitItem();
        
        newProjectBranch.getItems().add(newProjectItem);
        newProjectBranch.getItems().add(newPersonItem);
        newProjectBranch.getItems().add(newSkillItem);
        newProjectBranch.getItems().add(newTeamItem);


        // Create 'Edit >' sub-menu
        Menu editMenu = new Menu("Edit");
        menuBar.getMenus().add(editMenu);

        //Create MenuItems for Edit submenu
        MenuItem undoItem = createUndoItem();
        MenuItem redoItem = createRedoItem();
              

        // Create 'Display >' sub-menu
        Menu displayMenu = new Menu("Display");
        menuBar.getMenus().add(displayMenu);

        // Create MenuItems for Display submenu
        MenuItem toggleTree = createToggleTreeItem();
        displayMenu.getItems().addAll(toggleTree);


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
