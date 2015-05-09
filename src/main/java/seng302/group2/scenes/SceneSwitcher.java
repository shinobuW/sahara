package seng302.group2.scenes;

import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.information.*;
import seng302.group2.scenes.listdisplay.ReleaseCategory;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.release.Release;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

/**
 * Allows easy switching between scenes
 * Created by Jordane on 9/05/2015.
 */
public class SceneSwitcher
{
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
        double[] contentDrags = App.content.getDividerPositions();

        switch (scene)
        {
            case PEOPLE:
                App.content.getItems().remove(MainScene.informationPane);
                App.content.getItems().add(PersonCategoryScene.getPersonCategoryScene(
                        Global.currentWorkspace));
                break;
            case PROJECTS:
                App.content.getItems().remove(MainScene.informationPane);
                App.content.getItems().add(ProjectCategoryScene.getProjectCategoryScene(
                        Global.currentWorkspace));
                break;
            case ROLES:
                App.content.getItems().remove(MainScene.informationPane);
                App.content.getItems().add(RoleCategoryScene.getRoleCategoryScene(
                        Global.currentWorkspace));
                break;
            case SKILLS:
                App.content.getItems().remove(MainScene.informationPane);
                App.content.getItems().add(SkillCategoryScene.getSkillCategoryScene(
                        Global.currentWorkspace));
                break;
            case TEAMS:
                App.content.getItems().remove(MainScene.informationPane);
                App.content.getItems().add(TeamCategoryScene.getTeamCategoryScene(
                        Global.currentWorkspace));
                break;
            default:
                break;
        }

        App.content.setDividerPositions(contentDrags);
    }

    /**
     * Changes to the scene of the application to the given scene for the given item
     * @param scene The type of scene to switch to
     * @param item The item to show as the context of the scene
     */
    public static void changeScene(ContentScene scene, TreeViewItem item)
    {
        double[] contentDrags = App.content.getDividerPositions();

        switch (scene)
        {
            case WORKSPACE:
                if (item.getClass() == Workspace.class)
                {
                    App.content.getItems().remove(MainScene.informationPane);
                    App.content.getItems().add(WorkspaceScene.getWorkspaceScene((Workspace) item));
                }
                break;
            case WORKSPACE_EDIT:
                if (item.getClass() == Workspace.class)
                {
                    App.content.getItems().remove(MainScene.informationPane);
                    App.content.getItems().add(
                            WorkspaceEditScene.getWorkspaceEditScene((Workspace) item));
                }
                break;
            case PERSON:
                if (item.getClass() == Person.class)
                {
                    App.content.getItems().remove(MainScene.informationPane);
                    App.content.getItems().add(PersonScene.getPersonScene((Person) item));
                }
                break;
            case PERSON_EDIT:
                if (item.getClass() == Person.class)
                {
                    App.content.getItems().remove(MainScene.informationPane);
                    App.content.getItems().add(PersonEditScene.getPersonEditScene((Person) item));
                }
                break;
            case PROJECT:
                if (item.getClass() == Project.class)
                {
                    App.content.getItems().remove(MainScene.informationPane);
                    App.content.getItems().add(ProjectScene.getProjectScene((Project) item));
                }
                break;
            case PROJECT_EDIT:
                if (item.getClass() == Project.class)
                {
                    App.content.getItems().remove(MainScene.informationPane);
                    App.content.getItems().add(ProjectEditScene.getProjectEditScene(
                            (Project) item));
                }
                break;
            case ROLE:
                if (item.getClass() == Role.class)
                {
                    App.content.getItems().remove(MainScene.informationPane);
                    App.content.getItems().add(RoleScene.getRoleScene((Role) item));
                }
                break;
            case ROLE_EDIT:
                //TODO: Roles edit scenes
                break;
            case SKILL:
                if (item.getClass() == Skill.class)
                {
                    App.content.getItems().remove(MainScene.informationPane);
                    App.content.getItems().add(SkillScene.getSkillScene((Skill) item));
                }
                break;
            case SKILL_EDIT:
                if (item.getClass() == Skill.class)
                {
                    App.content.getItems().remove(MainScene.informationPane);
                    App.content.getItems().add(SkillEditScene.getSkillEditScene((Skill) item));
                }
                break;
            case RELEASE:
                if (item.getClass() == Release.class)
                {
                    App.content.getItems().remove(MainScene.informationPane);
                    App.content.getItems().add(ReleaseScene.getReleaseScene((Release) item));
                }
                break;
            case RELEASE_EDIT:
                if (item.getClass() == Release.class)
                {
                    App.content.getItems().remove(MainScene.informationPane);
                    App.content.getItems().add(ReleaseEditScene.getReleaseEditScene(
                            (Release) item));
                }
                break;
            case RELEASE_CATEGORY:
                if (item.getClass() == ReleaseCategory.class)
                {
                    App.content.getItems().remove(MainScene.informationPane);
                    App.content.getItems().add(ReleaseCategoryScene.getReleaseCategoryScene(
                            (ReleaseCategory) item));
                }
                break;
            case TEAM:
                if (item.getClass() == Team.class)
                {
                    App.content.getItems().remove(MainScene.informationPane);
                    App.content.getItems().add(TeamScene.getTeamScene((Team) item));
                }
                break;
            case TEAM_EDIT:
                if (item.getClass() == Team.class)
                {
                    App.content.getItems().remove(MainScene.informationPane);
                    App.content.getItems().add(TeamEditScene.getTeamEditScene((Team) item));
                }
                break;
            //TODO: Other scenes when implemented
            default:
                break;
        }

        App.content.setDividerPositions(contentDrags);
    }
}
