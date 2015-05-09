/*
 * SENG302 Group 2
 */
package seng302.group2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.information.*;
import seng302.group2.scenes.listdisplay.ReleaseCategory;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.util.config.ConfigLoader;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.Workspace.SaveLoadResult;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.release.Release;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

/**
 * The executable class for Sahara.
 */
@SuppressWarnings("deprecation")
public class App extends Application
{
    public static SplitPane content;
    
    public static Stage mainStage;
    public static Scene mainScene;
    
    /**
     * Refreshes the main scene GUI.
     */
    public static void refreshMainScene()
    {
        App.content = new SplitPane();
        App.mainScene = MainScene.getMainScene();
        mainStage.setScene(App.mainScene);
        App.refreshWindowTitle();
    }


    /**
     * An enumeration of scenes in the project
     */
    public enum ContentScene
    {
        PERSON,
        PERSON_EDIT,
        PROJECT,
        PROJECT_EDIT,
        WORKSPACE,
        WORKSPACE_EDIT,
        TEAM,
        TEAM_EDIT,
        ROLE,
        ROLE_EDIT,
        SKILL,
        SKILL_EDIT,
        RELEASE_CATEGORY,
        RELEASE,
        RELEASE_EDIT,
        STORY,
        STORY_EDIT,
        BACKLOG,
        BACKLOG_EDIT
    }

    /**
     * An enumerations of category scenes in the project
     */
    public enum CategoryScene
    {
        PEOPLE,
        PROJECTS,
        ROLES,
        SKILLS,
        TEAMS
    }


    /**
     * Changes to the given category scene of the application
     * @param scene The category scene to switch to
     */
    public static void changeScene(CategoryScene scene)
    {
        double[] contentDrags = content.getDividerPositions();

        switch (scene)
        {
            case PEOPLE:
                content.getItems().remove(MainScene.informationGrid);
                content.getItems().add(PersonCategoryScene.getPersonCategoryScene(
                        Global.currentWorkspace));
                break;
            case PROJECTS:
                content.getItems().remove(MainScene.informationGrid);
                content.getItems().add(ProjectCategoryScene.getProjectCategoryScene(
                        Global.currentWorkspace));
                break;
            case ROLES:
                content.getItems().remove(MainScene.informationGrid);
                content.getItems().add(RoleCategoryScene.getRoleCategoryScene(
                        Global.currentWorkspace));
                break;
            case SKILLS:
                content.getItems().remove(MainScene.informationGrid);
                content.getItems().add(SkillCategoryScene.getSkillCategoryScene(
                        Global.currentWorkspace));
                break;
            case TEAMS:
                content.getItems().remove(MainScene.informationGrid);
                content.getItems().add(TeamCategoryScene.getTeamCategoryScene(
                        Global.currentWorkspace));
                break;
        }

        content.setDividerPositions(contentDrags);
    }


    /**
     * Changes to the scene of the application to the given scene for the given item
     * @param scene The type of scene to switch to
     * @param item The item to show as the context of the scene
     */
    public static void changeScene(ContentScene scene, TreeViewItem item)
    {
        double[] contentDrags = content.getDividerPositions();

        switch (scene)
        {
            case WORKSPACE:
                if (item.getClass() == Workspace.class)
                {
                    content.getItems().remove(MainScene.informationGrid);
                    content.getItems().add(WorkspaceScene.getWorkspaceScene((Workspace) item));
                }
                break;
            case WORKSPACE_EDIT:
                if (item.getClass() == Workspace.class)
                {
                    content.getItems().remove(MainScene.informationGrid);
                    content.getItems().add(
                            WorkspaceEditScene.getWorkspaceEditScene((Workspace) item));
                }
                break;
            case PERSON:
                if (item.getClass() == Person.class)
                {
                    content.getItems().remove(MainScene.informationGrid);
                    content.getItems().add(PersonScene.getPersonScene((Person) item));
                }
                break;
            case PERSON_EDIT:
                if (item.getClass() == Person.class)
                {
                    content.getItems().remove(MainScene.informationGrid);
                    content.getItems().add(PersonEditScene.getPersonEditScene((Person) item));
                }
                break;
            case PROJECT:
                if (item.getClass() == Project.class)
                {
                    content.getItems().remove(MainScene.informationGrid);
                    content.getItems().add(ProjectScene.getProjectScene((Project) item));
                }
                break;
            case PROJECT_EDIT:
                if (item.getClass() == Project.class)
                {
                    content.getItems().remove(MainScene.informationGrid);
                    content.getItems().add(ProjectEditScene.getProjectEditScene((Project) item));
                }
                break;
            case ROLE:
                if (item.getClass() == Role.class)
                {
                    content.getItems().remove(MainScene.informationGrid);
                    content.getItems().add(RoleScene.getRoleScene((Role) item));
                }
                break;
            case ROLE_EDIT:
                //TODO: Roles edit scenes
                break;
            case SKILL:
                if (item.getClass() == Skill.class)
                {
                    content.getItems().remove(MainScene.informationGrid);
                    content.getItems().add(SkillScene.getSkillScene((Skill) item));
                }
                break;
            case SKILL_EDIT:
                if (item.getClass() == Skill.class)
                {
                    content.getItems().remove(MainScene.informationGrid);
                    content.getItems().add(SkillEditScene.getSkillEditScene((Skill) item));
                }
                break;
            case RELEASE:
                if (item.getClass() == Release.class)
                {
                    content.getItems().remove(MainScene.informationGrid);
                    content.getItems().add(ReleaseScene.getReleaseScene((Release) item));
                }
                break;
            case RELEASE_EDIT:
                if (item.getClass() == Release.class)
                {
                    content.getItems().remove(MainScene.informationGrid);
                    content.getItems().add(ReleaseEditScene.getReleaseEditScene((Release) item));
                }
                break;
            case RELEASE_CATEGORY:
                if (item.getClass() == ReleaseCategory.class)
                {
                    content.getItems().remove(MainScene.informationGrid);
                    content.getItems().add(ReleaseCategoryScene.getReleaseCategoryScene(
                            (ReleaseCategory) item));
                }
                break;
            case TEAM:
                if (item.getClass() == Team.class)
                {
                    content.getItems().remove(MainScene.informationGrid);
                    content.getItems().add(TeamScene.getTeamScene((Team) item));
                }
                break;
            case TEAM_EDIT:
                if (item.getClass() == Team.class)
                {
                    content.getItems().remove(MainScene.informationGrid);
                    content.getItems().add(TeamEditScene.getTeamEditScene((Team) item));
                }
                break;
            //TODO: Other scenes when implemented
            default:
                break;
        }

        content.setDividerPositions(contentDrags);
    }
    
    
    /**
     * Refreshes the title of the window to show the name of the current workspace, if any.
     */
    public static void refreshWindowTitle()
    {
        if (App.mainStage == null)
        {
            return;
        }
        if (Global.currentWorkspace == null)
        {
            App.mainStage.titleProperty().set("Sahara");
        }
        else
        {
            if (Global.currentWorkspace.getHasUnsavedChanges())
            {
                App.mainStage.titleProperty().set("Sahara: " + Global.currentWorkspace.getLongName()
                        + "*");
            }
            else
            {
                App.mainStage.titleProperty().set("Sahara: "
                        + Global.currentWorkspace.getLongName());
            }
        }
    }
    
    
    /**
     * The GUI setup and launch of the workspace.
     * @param primaryStage primary stage
     */
    @Override
    public void start(Stage primaryStage)
    {
        Global.currentWorkspace = new Workspace();

        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        // The title of the window
        primaryStage.setTitle("Sahara");
      
        primaryStage.setWidth(960);
        primaryStage.setHeight(540);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(500);
	
	//primaryStage.setWidth(0.75 * screenSize.getWidth());
        //primaryStage.setHeight(0.75 * screenSize.getHeight());
        //primaryStage.setMinHeight(0.25 * screenSize.getWidth());
        //primaryStage.setMinWidth(0.25 * screenSize.getWidth());
	
        primaryStage.getIcons().add(new Image("file:images/icon.png"));
        
        // Set the scene of the stage to the initial scene
        content = new SplitPane();
        App.mainScene = MainScene.getMainScene();
        primaryStage.setScene(App.mainScene);
        mainStage = primaryStage;

        // Load the config
        ConfigLoader.loadConfig();
        
        // Exit button handling
        Platform.setImplicitExit(false);
        primaryStage.setOnCloseRequest(event ->
            {
                exitApp();
                event.consume();
            });
        
        // Show the stage/window
        App.refreshWindowTitle();
        mainStage.show();
    }


    /**
     * The closure and application tear-down method that should be executed on closure of the
     * application from any branch of the project code
     */
    public static void exitApp()
    {
        ConfigLoader.saveConfig();
        if (!Global.currentWorkspace.getHasUnsavedChanges())
        {
            System.exit(0);
            return;  // Clean from a method POV
        }

        Action response = Dialogs.create()
                .title("Save Workspace?")
                .message("Would you like to save your changes to the current workspace?")
                .showConfirm();

        if (response == Dialog.ACTION_YES)
        {
            SaveLoadResult saved = Workspace.saveWorkspace(Global.currentWorkspace, false);
            if (saved == SaveLoadResult.SUCCESS)
            {
                // Save configuration again as settings may change on save of workspace
                ConfigLoader.saveConfig();
                System.exit(0);
            }
        }
        else if (response == Dialog.ACTION_NO)
        {
            System.exit(0);
        }
    }

    
    /**
     * The main entry of the application.
     * @param args arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}
