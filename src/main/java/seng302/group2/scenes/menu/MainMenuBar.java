package seng302.group2.scenes.menu;

import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.dialog.*;
import seng302.group2.util.revert.RevertManager;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.categories.Category;
import seng302.group2.workspace.categories.subCategory.project.ReleaseCategory;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;
import seng302.group2.workspace.workspace.Workspace;
import seng302.group2.workspace.workspace.Workspace.SaveLoadResult;

import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;

/**
 * The main menu bar of the workspace window(s).
 *
 * @author Jordane Lew (jml168)
 */
@SuppressWarnings("deprecation")
public class MainMenuBar extends MenuBar {
    /**
     * Creates a menu item "Workspace" and sets the on action event if "Workspace" is clicked.
     *
     * @return MenuItem Workspace
     */
    private static MenuItem createWorkspaceItem() {
        MenuItem newWorkspaceItem = new MenuItem("Workspace");
        newWorkspaceItem.setOnAction((ActionEvent event) -> {
                newWorkspaceAction();
            });

        newWorkspaceItem.setAccelerator(new KeyCodeCombination(KeyCode.N,
                KeyCombination.CONTROL_DOWN,
                KeyCombination.SHORTCUT_DOWN));
        return newWorkspaceItem;
    }

    static void newWorkspaceAction() {
        if (Global.currentWorkspace == null
                || !Global.currentWorkspace.getHasUnsavedChanges()) {
            CreateWorkspaceDialog.show();
            App.refreshMainScene();
            Global.commandManager.clear();
            return;
        }

        Action response = Dialogs.create()
                .title("Save Workspace?")
                .message("Would you like to save your changes to the current workspace?")
                .showConfirm();

        if (response == Dialog.ACTION_YES) {
            SaveLoadResult saved = Workspace.saveWorkspace(Global.currentWorkspace, false);
            if (saved == SaveLoadResult.SUCCESS) {
                CreateWorkspaceDialog.show();
                App.refreshMainScene();
            }
            else {
                System.out.println("There was an error saving the file. Cancelling");
            }
        }
        else if (response == Dialog.ACTION_NO) {
            CreateWorkspaceDialog.show();
            App.refreshMainScene();
        }
        if (response != Dialog.ACTION_CANCEL) {
            Global.commandManager.clear();
            App.mainPane.expandTree();
        }
    }


    /**
     * Creates a menu item "Workspace" and sets the on action event if "Workspace" is clicked.
     *
     * @return MenuItem Workspace
     */
    private static MenuItem createProjectItem() {
        MenuItem newProjectItem = new MenuItem("Project");
        newProjectItem.setOnAction((ActionEvent event) ->
                CreateProjectDialog.show());

        newProjectItem.setAccelerator(new KeyCodeCombination(KeyCode.P,
                KeyCombination.CONTROL_DOWN,
                KeyCombination.SHIFT_DOWN,
                KeyCombination.SHORTCUT_DOWN));

        return newProjectItem;
    }


    /**
     * Creates a menu item "Person" and sets the on action event if "Person" is clicked.
     *
     * @return MenuItem Person
     */
    private static MenuItem createPersonItem() {
        MenuItem newPersonItem = new MenuItem("Person");
        newPersonItem.setOnAction((event) ->
                CreatePersonDialog.show());
        newPersonItem.setAccelerator(new KeyCodeCombination(KeyCode.P,
                KeyCombination.CONTROL_DOWN,
                KeyCombination.SHORTCUT_DOWN));
        return newPersonItem;
    }

    /**
     * Creates a menu item "Skill" and sets the on action event if "Skill" is clicked.
     *
     * @return MenuItem Skill
     */
    private static MenuItem createSkillItem() {
        MenuItem newSkillItem = new MenuItem("Skill");
        newSkillItem.setOnAction((event) ->
                CreateSkillDialog.show());
        newSkillItem.setAccelerator(new KeyCodeCombination(KeyCode.K,
                KeyCombination.CONTROL_DOWN,
                KeyCombination.SHORTCUT_DOWN));
        return newSkillItem;
    }

    /**
     * Create a menu item "Release" and sets the action event if "Release" is clicked.
     *
     * @return MenuItem Release
     */
    private static MenuItem createReleaseItem() {

        MenuItem newReleaseItem = new MenuItem("Release");
        newReleaseItem.setOnAction((event) -> {
                CreateReleaseDialog.show(null);
            });
        newReleaseItem.setAccelerator(new KeyCodeCombination(KeyCode.R,
                KeyCombination.CONTROL_DOWN,
                KeyCombination.SHORTCUT_DOWN));
        return newReleaseItem;
    }

    /**
     * Create a menu item "Release" and sets the action event if "Release" is clicked.
     *
     * @return MenuItem Release
     */
    private static MenuItem createStoryItem() {

        MenuItem newStoryItem = new MenuItem("Story");
        newStoryItem.setOnAction((event) -> CreateStoryDialog.show());
        return newStoryItem;
    }

    /**
     * Create a menu item "Backlog" and sets the action event if "Backlog" is clicked.
     *
     * @return MenuItem Backlog
     */
    private static MenuItem createBacklogItem() {

        MenuItem newBacklogItem = new MenuItem("Backlog");
        newBacklogItem.setOnAction((event) -> {
                javafx.scene.control.Dialog creationDialog = new CreateBacklogDialog();
                creationDialog.show();
            });
        return newBacklogItem;
    }

    /**
     * Creates a menu item "Team" and sets the on action event if "Team" is clicked.
     *
     * @return MenuItem Team
     */
    private static MenuItem createTeamItem() {
        MenuItem newTeamItem = new MenuItem("Team");
        newTeamItem.setOnAction((event) ->
                CreateTeamDialog.show());
        newTeamItem.setAccelerator(new KeyCodeCombination(KeyCode.T,
                KeyCombination.CONTROL_DOWN,
                KeyCombination.SHORTCUT_DOWN));
        return newTeamItem;
    }

    /**
     * Creates a menu item "Open" and sets the on action event if "Open" is clicked.
     *
     * @return MenuItem Open
     */
    private static MenuItem createOpenItem() {
        // Create 'Open' MenuItem
        MenuItem openItem = new MenuItem("Open");
        openItem.setOnAction((event) -> loadAction());
        openItem.setAccelerator(new KeyCodeCombination(KeyCode.O,
                KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
        return openItem;
    }

    static void loadAction() {
        if (!Global.currentWorkspace.getHasUnsavedChanges()) {
            Workspace.loadWorkspace();
            return;
        }
        Action response = Dialogs.create()
                .title("Save Workspace?")
                .message("Would you like to save your changes to the current workspace?")
                .showConfirm();

        if (response == Dialog.ACTION_YES) {
            SaveLoadResult saved = Workspace.saveWorkspace(Global.currentWorkspace, false);
            if (saved == SaveLoadResult.SUCCESS) {
                Workspace.loadWorkspace();
                //App.refreshMainScene();
            }
        }
        else if (response == Dialog.ACTION_NO) {
            Workspace.loadWorkspace();
            //App.refreshMainScene();
        }
    }

    /**
     * Creates a menu item "Save" and sets the on action event if "Save" is clicked.
     *
     * @return MenuItem Save
     */
    private static MenuItem createSaveItem() {
        // Create 'Save' MenuItem
        MenuItem saveItem = new MenuItem("Save");
        saveItem.setOnAction((event) -> saveAction());

        saveItem.setAccelerator(new KeyCodeCombination(KeyCode.S,
                KeyCombination.CONTROL_DOWN,
                KeyCombination.SHORTCUT_DOWN));
        return saveItem;
    }

    static void saveAction() {
        Workspace.saveWorkspace(Global.currentWorkspace, false);
    }

    /**
     * Creates a menu item "SaveAs" and sets the on action event if "SaveAs" is clicked.
     *
     * @return MenuItem SaveAs
     */
    private static MenuItem createSaveAsItem() {
        // Create 'Save As' MenuItem
        MenuItem saveAsItem = new MenuItem("Save As...");
        saveAsItem.setOnAction((event) ->
                Workspace.saveWorkspace(Global.currentWorkspace, true));

        saveAsItem.setAccelerator(new KeyCodeCombination(KeyCode.S,
                KeyCombination.CONTROL_DOWN,
                KeyCombination.SHIFT_DOWN,
                KeyCombination.SHORTCUT_DOWN));
        return saveAsItem;
    }

    /**
     * Creates a menu item "Export" and sets the on action event to export the file as an XML
     * document.
     *
     * @return MenuItem Export
     */
    private static MenuItem createReportItem() {
        // Create 'Save' MenuItem
        MenuItem reportItem = new MenuItem("Report");
        reportItem.setOnAction((event) ->
                //ReportGenerator.generateReport());
                reportAction());

        //ShortcutItem not implemented yet
//        exportItem.setAccelerator(new KeyCodeCombination(KeyCode.S,
//                KeyCombination.CONTROL_DOWN,
//                KeyCombination.SHORTCUT_DOWN));
        return reportItem;
    }

    static void reportAction() {
        GenerateReportDialog.show();
    }

    /**
     * Creates a menu item "Revert" and sets the on action event to revert the current workspace to
     * the Revert state from when the program is last saved or last opened
     *
     * @return MenuItem Revert
     */
    private static MenuItem createRevertItem() {
        // Create 'Save' MenuItem
        MenuItem revertItem = new MenuItem("Revert");
        revertItem.setOnAction((event) -> {
                if (!Global.currentWorkspace.getHasUnsavedChanges()) {
                    RevertManager.revertWorkspace();
                    return;
                }
                Action response = Dialogs.create()
                        .title("Save Workspace?")
                        .message("Would you like to save your changes to the current workspace?")
                        .showConfirm();

                if (response == Dialog.ACTION_YES) {
                    SaveLoadResult saved = Workspace.saveWorkspace(Global.currentWorkspace, false);
                    if (saved == SaveLoadResult.SUCCESS) {
                        RevertManager.revertWorkspace();
                    }
                }
                else if (response == Dialog.ACTION_NO) {
                    RevertManager.revertWorkspace();
                }
            });


        //ShortcutItem not implemented yet
//        exportItem.setAccelerator(new KeyCodeCombination(KeyCode.S,
//                KeyCombination.CONTROL_DOWN,
//                KeyCombination.SHORTCUT_DOWN));
        return revertItem;
    }

    /**
     * Creates a menu item "Quit" and sets the on action event if "Quit" is clicked.
     *
     * @return MenuItem Quit
     */
    private static MenuItem createQuitItem() {
        MenuItem quitProgramItem = new MenuItem("Quit");
        quitProgramItem.setOnAction((event) ->
                App.exitApp());

        return quitProgramItem;
    }

    /**
     * Creates a menu item "Undo" and sets the on action event if "Undo" is clicked.
     *
     * @return MenuItem Undo
     */
    private static MenuItem createUndoItem() {
        // Create 'Undo' MenuItem
        MenuItem undoItem = new MenuItem("Undo");
        undoItem.setOnAction((event) -> undoAction());

        undoItem.setAccelerator(new KeyCodeCombination(KeyCode.Z,
                KeyCombination.CONTROL_DOWN,
                KeyCombination.SHORTCUT_DOWN));
        return undoItem;
    }

    static void undoAction() {
        Global.commandManager.undo();
    }

    /**
     * Creates a menu item "Redo" and sets the on action event if "Redo" is clicked.
     *
     * @return MenuItem Redo
     */
    private static MenuItem createRedoItem() {
        // Create 'Redo' MenuItem
        MenuItem redoItem = new MenuItem("Redo");
        redoItem.setOnAction((event) ->
                redoAction());

        redoItem.setAccelerator(new KeyCodeCombination(KeyCode.Y,
                KeyCombination.CONTROL_DOWN,
                KeyCombination.SHORTCUT_DOWN));
        return redoItem;
    }

    static void redoAction() {
        Global.commandManager.redo();
    }

    /**
     * Creates a menu item "Delete" item and sets the on action event if "Delete" is clicked.
     * Piggy-backing off the context menu for now. Better they are synced than duplicate methods.
     *
     * @return MenuItem Delete
     */
    private static MenuItem createDeleteTreeItem() {
        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction((event) -> {
                showDeleteDialog((SaharaItem) Global.selectedTreeItem.getValue());
            });

        deleteItem.setAccelerator(new KeyCodeCombination(KeyCode.DELETE,
                KeyCombination.SHIFT_DOWN));
        return deleteItem;
    }

    /**
     * Creates a menu item "Toggle Tree" and sets the on action event if "Toggle Tree" is clicked.
     *
     * @return MenuItem ToggleTree
     */
    private static MenuItem createToggleTreeItem() {
        MenuItem toggleItem = new MenuItem("Toggle Menu");
        toggleItem.setOnAction((event) -> {
                App.mainPane.toggleTree();
                //MainScene.menuHidden = !MainScene.menuHidden;
                //App.refreshMainScene();
            });

        toggleItem.setAccelerator(new KeyCodeCombination(KeyCode.M,
                KeyCombination.CONTROL_DOWN,
                KeyCombination.SHORTCUT_DOWN));
        return toggleItem;
    }

    /**
     * Constructor for the Menu Bar
     */
    public MainMenuBar() {
        // The menus and menu bar creation
        //MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        this.getMenus().add(fileMenu);

        // Create 'New >' sub-menu
        Menu newBranch = new Menu("New");

        // Create MenuItems for New submenu
        MenuItem newWorkspaceItem = createWorkspaceItem();
        MenuItem newProjectItem = createProjectItem();
        MenuItem newPersonItem = createPersonItem();
        MenuItem newSkillItem = createSkillItem();
        MenuItem newTeamItem = createTeamItem();
        MenuItem newReleaseItem = createReleaseItem();
        MenuItem newStoryItem = createStoryItem();
        MenuItem newBacklogItem = createBacklogItem();

        // Create other items for file menu
        MenuItem openItem = createOpenItem();
        MenuItem saveItem = createSaveItem();
        MenuItem saveAsItem = createSaveAsItem();

        MenuItem quitProgramItem = createQuitItem();

        newBranch.getItems().add(newWorkspaceItem);
        newBranch.getItems().add(newProjectItem);
        newBranch.getItems().add(newPersonItem);
        newBranch.getItems().add(newSkillItem);
        newBranch.getItems().add(newTeamItem);
        newBranch.getItems().add(newReleaseItem);
        newBranch.getItems().add(newStoryItem);
        newBranch.getItems().add(newBacklogItem);


        // Create 'Edit >' sub-menu
        Menu editMenu = new Menu("Edit");
        this.getMenus().add(editMenu);


        //Create MenuItems for Edit submenu
        MenuItem undoItem = createUndoItem();
        MenuItem redoItem = createRedoItem();
        MenuItem deleteTreeItem = createDeleteTreeItem();
        MenuItem revertItem = createRevertItem();

        // Create 'Display >' sub-menu
        Menu displayMenu = new Menu("Display");
        this.getMenus().add(displayMenu);
        // Create MenuItems for Display submenu
        MenuItem toggleTree = createToggleTreeItem();
        displayMenu.getItems().addAll(toggleTree);


        // Create 'Generate >' menu
        Menu generateMenu = new Menu("Generate");
        this.getMenus().add(generateMenu);
        MenuItem reportItem = createReportItem();
        generateMenu.getItems().add(reportItem);


        // Add MenuItems to Menu
        fileMenu.getItems().addAll(newBranch, openItem,
                saveItem, saveAsItem, revertItem, new SeparatorMenuItem(),
                quitProgramItem);

        editMenu.getItems().addAll(undoItem, redoItem, deleteTreeItem);

        fileMenu.setOnShowing((event) -> {
                if (Global.currentWorkspace.getHasUnsavedChanges()) {
                    revertItem.setDisable(false);
                }
                else {
                    revertItem.setDisable(true);
                }
            });

        newBranch.setOnShowing((event) -> {
                newBacklogItem.setDisable(true);
                boolean PoExists = false;
                for (Person person : Global.currentWorkspace.getPeople()) {
                    if (person.getSkills().containsAll(Role.getRoleFromType(Role.RoleType.PRODUCT_OWNER)
                            .getRequiredSkills())) {
                        PoExists = true;
                    }
                }
                if (PoExists && !Global.currentWorkspace.getProjects().isEmpty()) {
                    newBacklogItem.setDisable(false);  // Enable
                }

                if (Global.currentWorkspace.getProjects().isEmpty()) {
                    newReleaseItem.setDisable(true);
                    newStoryItem.setDisable(true);
                }
                else {
                    newReleaseItem.setDisable(false);
                    newStoryItem.setDisable(false);
                }
            });

        editMenu.setOnShowing((event) -> {
                redoItem.setDisable(!Global.commandManager.isRedoAvailable());
                undoItem.setDisable(!Global.commandManager.isUndoAvailable());

                if (Global.selectedTreeItem == null || Global.selectedTreeItem.getValue() == null) {
                    deleteTreeItem.setDisable(true);
                }
                else if (Global.selectedTreeItem.getValue().getClass() == Category.class
                        || Global.selectedTreeItem.getValue().getClass() == ReleaseCategory.class
                        || Global.selectedTreeItem.getValue().getClass() == Category.class
                        || Global.selectedTreeItem.getValue().getClass() == Workspace.class) {
                    // Non-deleteable classes (for now?)
                    deleteTreeItem.setDisable(true);
                }
                else if (Global.selectedTreeItem.getValue().getClass() == Skill.class) {
                    Skill selectedSkill = (Skill) Global.selectedTreeItem.getValue();
                    if (selectedSkill.getShortName().equals("Product Owner")
                            || selectedSkill.getShortName().equals("Scrum Master")) {
                        deleteTreeItem.setDisable(true);
                    }
                    else {
                        deleteTreeItem.setDisable(false);
                    }
                }
                else if (Global.selectedTreeItem.getValue().getClass() == Team.class) {
                    Team selectedTeam = (Team) Global.selectedTreeItem.getValue();
                    deleteTreeItem.setDisable(selectedTeam.isUnassignedTeam());
                }
                else {
                    deleteTreeItem.setDisable(false);
                }
            });

    }
}
