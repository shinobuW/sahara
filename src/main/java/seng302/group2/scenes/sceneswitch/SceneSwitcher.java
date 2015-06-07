package seng302.group2.scenes.sceneswitch;

import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.information.backlog.BacklogCategoryScene;
import seng302.group2.scenes.information.backlog.BacklogEditScene;
import seng302.group2.scenes.information.backlog.BacklogScene;
import seng302.group2.scenes.information.person.PersonCategoryScene;
import seng302.group2.scenes.information.person.PersonEditScene;
import seng302.group2.scenes.information.person.PersonScene;
import seng302.group2.scenes.information.project.ProjectCategoryScene;
import seng302.group2.scenes.information.project.ProjectEditScene;
import seng302.group2.scenes.information.project.ProjectScene;
import seng302.group2.scenes.information.release.ReleaseCategoryScene;
import seng302.group2.scenes.information.release.ReleaseEditScene;
import seng302.group2.scenes.information.release.ReleaseScene;
import seng302.group2.scenes.information.role.RoleCategoryScene;
import seng302.group2.scenes.information.role.RoleScene;
import seng302.group2.scenes.information.skill.SkillCategoryScene;
import seng302.group2.scenes.information.skill.SkillEditScene;
import seng302.group2.scenes.information.skill.SkillScene;
import seng302.group2.scenes.information.story.StoryCategoryScene;
import seng302.group2.scenes.information.story.StoryEditScene;
import seng302.group2.scenes.information.story.StoryScene;
import seng302.group2.scenes.information.team.NewTeamEditScene;
import seng302.group2.scenes.information.team.TeamCategoryScene;
import seng302.group2.scenes.information.team.TeamScene;
import seng302.group2.scenes.information.workspace.WorkspaceEditScene;
import seng302.group2.scenes.information.workspace.WorkspaceScene;
import seng302.group2.scenes.listdisplay.BacklogCategory;
import seng302.group2.scenes.listdisplay.ReleaseCategory;
import seng302.group2.scenes.listdisplay.StoryCategory;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.backlog.Backlog;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.release.Release;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.story.Story;
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
        STORY_CATEGORY,
        BACKLOG,
        BACKLOG_EDIT,
        BACKLOG_CATEGORY
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
                MainScene.contentPane.setContent(PersonCategoryScene.getPersonCategoryScene(
                        Global.currentWorkspace));
                break;
            case PROJECTS:
                MainScene.contentPane.setContent(ProjectCategoryScene.getProjectCategoryScene(
                        Global.currentWorkspace));
                break;
            case ROLES:
                MainScene.contentPane.setContent(RoleCategoryScene.getRoleCategoryScene(
                        Global.currentWorkspace));
                break;
            case SKILLS:
                MainScene.contentPane.setContent(SkillCategoryScene.getSkillCategoryScene(
                        Global.currentWorkspace));
                break;
            case TEAMS:
                MainScene.contentPane.setContent(TeamCategoryScene.getTeamCategoryScene(
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
                    MainScene.contentPane.setContent(new WorkspaceScene((Workspace) item));
                }
                break;
            case WORKSPACE_EDIT:
                if (item.getClass() == Workspace.class)
                {
                    MainScene.contentPane.setContent(WorkspaceEditScene.getWorkspaceEditScene(
                            (Workspace) item));
                }
                break;
            case PERSON:
                if (item.getClass() == Person.class)
                {
                    MainScene.contentPane.setContent(new PersonScene((Person) item));
                }
                break;
            case PERSON_EDIT:
                if (item.getClass() == Person.class)
                {
                    MainScene.contentPane.setContent(PersonEditScene.getPersonEditScene(
                            (Person) item));
                }
                break;
            case PROJECT:
                if (item.getClass() == Project.class)
                {
                    MainScene.contentPane.setContent(new ProjectScene((Project) item));
                }
                break;
            case PROJECT_EDIT:
                if (item.getClass() == Project.class)
                {
                    MainScene.contentPane.setContent(ProjectEditScene.getProjectEditScene(
                            (Project) item));
                }
                break;
            case ROLE:
                if (item.getClass() == Role.class)
                {
                    MainScene.contentPane.setContent(new RoleScene((Role) item));
                }
                break;
            case ROLE_EDIT:
                //TODO: Roles edit scene when necc.
                break;
            case SKILL:
                if (item.getClass() == Skill.class)
                {
                    MainScene.contentPane.setContent(new SkillScene((Skill) item));
                }
                break;
            case SKILL_EDIT:
                if (item.getClass() == Skill.class)
                {
                    MainScene.contentPane.setContent(SkillEditScene.getSkillEditScene(
                            (Skill) item));
                }
                break;
            case RELEASE:
                if (item.getClass() == Release.class)
                {
                    MainScene.contentPane.setContent(new ReleaseScene((Release) item));
                }
                break;
            case RELEASE_EDIT:
                if (item.getClass() == Release.class)
                {
                    MainScene.contentPane.setContent(ReleaseEditScene.getReleaseEditScene(
                            (Release) item));
                }
                break;
            case RELEASE_CATEGORY:
                if (item.getClass() == ReleaseCategory.class)
                {
                    MainScene.contentPane.setContent(ReleaseCategoryScene.getReleaseCategoryScene(
                            (ReleaseCategory) item));
                }
                break;
            case STORY:
                if (item.getClass() == Story.class)
                {
                    MainScene.contentPane.setContent(new StoryScene((Story) item));
                }
                break;
            case STORY_EDIT:
                if (item.getClass() == Story.class)
                {
                    MainScene.contentPane.setContent(StoryEditScene.getStoryEditScene(
                            (Story) item));
                }
                break;
            case STORY_CATEGORY:
                if (item.getClass() == StoryCategory.class)
                {
                    MainScene.contentPane.setContent(StoryCategoryScene.getStoryCategoryScene(
                            (StoryCategory) item));
                }
                break;
            case TEAM:
                if (item.getClass() == Team.class)
                {
                    MainScene.contentPane.setContent(new TeamScene((Team) item));
                }
                break;
            case TEAM_EDIT:
                if (item.getClass() == Team.class)
                {
                    MainScene.contentPane.setContent(new NewTeamEditScene((Team) item));
                }
                break;
            case BACKLOG:
                if (item.getClass() == Backlog.class)
                {
                    MainScene.contentPane.setContent(new BacklogScene((Backlog) item));
                }
                break;
            case BACKLOG_EDIT:
                if (item.getClass() == Backlog.class)
                {
                    MainScene.contentPane.setContent(new BacklogEditScene(
                            (Backlog) item));
                }
                break;
            case BACKLOG_CATEGORY:
                if (item.getClass() == BacklogCategory.class)
                {
                    MainScene.contentPane.setContent(BacklogCategoryScene.getBacklogCategoryScene(
                            (BacklogCategory) item));
                }
                break;

            default:
                break;
        }

        App.content.setDividerPositions(contentDrags);
    }
}
