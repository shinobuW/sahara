package seng302.group2.scenes.sceneswitch;

/**
 * Allows easy switching between scenes
 * Created by Jordane on 9/05/2015.
 */
@Deprecated
public class SceneSwitcher {
    /*public static void changeScene(TreeViewItem item) {
        item.switchToCategoryScene();
    }*/

    /*public static void changeScene(TreeViewItem item, boolean switchToEditScene) {
        item.switchToCategoryScene();
    }*/

    /**
     * Changes to the given category scene of the application
     *
     * @param scene The category scene to switch to
     */
    /*public static void changeScene(CategoryScene scene) {
        double[] contentDrags = App.content.getDividerPositions();

        switch (scene) {
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
    }*/

    /**
     * Changes to the scene of the application to the given scene for the given item
     *
     * @param scene The type of scene to switch to
     * @param item  The item to show as the context of the scene
     */
    /*public static void changeScene(ContentScene scene, TreeViewItem item) {
        double[] contentDrags = App.content.getDividerPositions();

        switch (scene) {
            case WORKSPACE:
                if (item.getClass() == Workspace.class) {
                    MainScene.contentPane.setContent(new WorkspaceScene((Workspace) item));
                }
                break;
            case WORKSPACE_EDIT:
                if (item.getClass() == Workspace.class) {
                    MainScene.contentPane.setContent(WorkspaceEditScene.getWorkspaceEditScene(
                            (Workspace) item));
                }
                break;
            case PERSON:
                if (item.getClass() == Person.class) {
                    MainScene.contentPane.setContent(new PersonScene((Person) item));
                }
                break;
            case PERSON_EDIT:
                if (item.getClass() == Person.class) {
                    MainScene.contentPane.setContent(PersonEditScene.getPersonEditScene(
                            (Person) item));
                }
                break;
            case PROJECT:
                if (item.getClass() == Project.class) {
                    MainScene.contentPane.setContent(new ProjectScene((Project) item));
                }
                break;
            case PROJECT_EDIT:
                if (item.getClass() == Project.class) {
                    MainScene.contentPane.setContent(ProjectEditScene.getProjectEditScene(
                            (Project) item));
                }
                break;
            case ROLE:
                if (item.getClass() == Role.class) {
                    MainScene.contentPane.setContent(new RoleScene((Role) item));
                }
                break;
            case ROLE_EDIT:
                break;
            case SKILL:
                if (item.getClass() == Skill.class) {
                    MainScene.contentPane.setContent(new SkillScene((Skill) item));
                }
                break;
            case SKILL_EDIT:
                if (item.getClass() == Skill.class) {
                    MainScene.contentPane.setContent(SkillEditScene.getSkillEditScene(
                            (Skill) item));
                }
                break;
            case RELEASE:
                if (item.getClass() == Release.class) {
                    MainScene.contentPane.setContent(new ReleaseScene((Release) item));
                }
                break;
            case RELEASE_EDIT:
                if (item.getClass() == Release.class) {
                    MainScene.contentPane.setContent(ReleaseEditScene.getReleaseEditScene(
                            (Release) item));
                }
                break;
            case RELEASE_CATEGORY:
                if (item.getClass() == ReleaseCategory.class) {
                    MainScene.contentPane.setContent(ReleaseCategoryScene.getReleaseCategoryScene(
                            (ReleaseCategory) item));
                }
                break;
            case STORY:
                if (item.getClass() == Story.class) {
                    MainScene.contentPane.setContent(new StoryScene((Story) item));
                }
                break;
            case STORY_EDIT:
                if (item.getClass() == Story.class) {
                    MainScene.contentPane.setContent(StoryEditScene.getStoryEditScene(
                            (Story) item));
                }
                break;
            case STORY_CATEGORY:
                if (item.getClass() == StoryCategory.class) {
                    MainScene.contentPane.setContent(StoryCategoryScene.getStoryCategoryScene(
                            (StoryCategory) item));
                }
                break;
            case TEAM:
                if (item.getClass() == Team.class) {
                    MainScene.contentPane.setContent(new TeamScene((Team) item));
                }
                break;
            case TEAM_EDIT:
                if (item.getClass() == Team.class) {
                    MainScene.contentPane.setContent(new TeamEditScene((Team) item));
                }
                break;
            case BACKLOG:
                if (item.getClass() == Backlog.class) {
                    MainScene.contentPane.setContent(new BacklogScene((Backlog) item));
                }
                break;
            case BACKLOG_EDIT:
                if (item.getClass() == Backlog.class) {
                    MainScene.contentPane.setContent(new BacklogEditScene(
                            (Backlog) item));
                }
                break;
            case BACKLOG_CATEGORY:
                if (item.getClass() == BacklogCategory.class) {
                    MainScene.contentPane.setContent(BacklogCategoryScene.getBacklogCategoryScene(
                            (BacklogCategory) item));
                }
                break;

            default:
                break;
        }

        App.content.setDividerPositions(contentDrags);
    }*/




    /**
     * An enumerations of category scenes in the project
     */
    /*public enum CategoryScene {
        PEOPLE,
        PROJECTS,
        ROLES,
        SKILLS,
        TEAMS
    }*/
}
