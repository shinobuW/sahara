package seng302.group2.scenes.contextmenu;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.listdisplay.categories.Category;
import seng302.group2.scenes.listdisplay.categories.subCategory.SubCategory;
import seng302.group2.scenes.sceneswitch.SceneSwitcher;
import seng302.group2.scenes.sceneswitch.switchStrategies.workspace.project.ReleaseInformationSwitchStrategy;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.release.Release;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;


/**
 * ContextMenu class for instances of Person, Skill and Team
 *
 * @author swi67, jml168, btm38
 */
@SuppressWarnings("deprecation")
public class ElementTreeContextMenu extends ContextMenu {
    Logger logger = LoggerFactory.getLogger(ElementTreeContextMenu.class);

    /**
     * Constructor. Sets the event for contextMenu buttons
     */
    public ElementTreeContextMenu() {
        MenuItem deleteItem = new MenuItem("Delete");
        MenuItem editItem = new MenuItem("Edit");

        /*Categories selectedCategory = getSelectedCategory();

        final Categories finalSelectedCategory = selectedCategory;  // for use in lamda expr.*/

        TreeViewItem selected = (TreeViewItem) Global.selectedTreeItem.getValue();

        //"Edit" button event
        editItem.setOnAction(e -> {
                //showEditScene(finalSelectedCategory);
                selected.switchToInfoScene(true);
            });

        //"Delete" button event
        deleteItem.setOnAction(e -> {
                showDeleteDialog(selected);
            });

        this.getItems().addAll(editItem, deleteItem);


        /* Specific enable/disables based on items */
        if (selected instanceof Team) {
            Team selectedTeam = (Team) Global.selectedTreeItem.getValue();
            if (selectedTeam.isUnassignedTeam()) {
                editItem.setDisable(true);
                deleteItem.setDisable(true);
            }
            else {
                editItem.setDisable(false);
                deleteItem.setDisable(false);
            }
        }

        if (selected instanceof Role) {
            Role selectedRole = (Role) Global.selectedTreeItem.getValue();
            if (!selectedRole.isDefault()) {
                editItem.setDisable(true);
                deleteItem.setDisable(true);
            }
            else {
                editItem.setDisable(false);
                deleteItem.setDisable(false);
            }
        }

        if (selected instanceof Skill) {

            editItem.setDisable(false);
            deleteItem.setDisable(false);

            for (Role role : Global.currentWorkspace.getRoles()) {
                if (role.getType() == Role.RoleType.PRODUCT_OWNER
                        || role.getType() == Role.RoleType.SCRUM_MASTER) {
                    if (role.getRequiredSkills().contains(selected)) {
                        editItem.setDisable(true);
                        deleteItem.setDisable(true);
                    }
                }
            }

            /*Skill selectedSkill = (Skill) Global.selectedTreeItem.getValue();
            if (selectedSkill.getShortName().equals("Scrum Master")
                    || selectedSkill.getShortName().equals("Product Owner")) {
                editItem.setDisable(true);
                deleteItem.setDisable(true);
            }
            else {
                editItem.setDisable(false);
                deleteItem.setDisable(false);
            }*/
        }

        if (selected instanceof Workspace) {
            deleteItem.setDisable(true);
        }
    }

    /**
     * Gets the category of the selected tree item.
     *
     * @return An enum value of the selected tree item's category
     */
    /*private static Categories getSelectedCategory() {
        Categories selectedCategory = Categories.OTHER;
        if (Global.selectedTreeItem.getValue().getClass() == Person.class) {
            selectedCategory = Categories.PERSON;
        }
        else if (Global.selectedTreeItem.getValue().getClass() == Skill.class) {
            selectedCategory = Categories.SKILL;
        }
        else if (Global.selectedTreeItem.getValue().getClass() == Team.class) {
            selectedCategory = Categories.TEAM;
        }
        else if (Global.selectedTreeItem.getValue().getClass() == Workspace.class) {
            selectedCategory = Categories.WORKSPACE;
        }
        else if (Global.selectedTreeItem.getValue().getClass() == Project.class) {
            selectedCategory = Categories.PROJECT;
        }
        else if (Global.selectedTreeItem.getValue().getClass() == Role.class) {
            selectedCategory = Categories.ROLE;
        }
        else if (Global.selectedTreeItem.getValue().getClass() == Release.class) {
            selectedCategory = Categories.RELEASE;
        }
        else if (Global.selectedTreeItem.getValue().getClass() == Story.class) {
            selectedCategory = Categories.STORY;
        }
        else if (Global.selectedTreeItem.getValue().getClass() == Backlog.class) {
            selectedCategory = Categories.BACKLOG;
        }
        return selectedCategory;
    }*/

    /**
     * Displays the edit scene for given element
     *
     * @param category Type of Category
     */
    /*private static void showEditScene(Categories category) {
        switch (category) {
            case PERSON:
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.PERSON_EDIT,
                        (Person) Global.selectedTreeItem.getValue());
                break;
            case SKILL:
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.SKILL_EDIT,
                        (Skill) Global.selectedTreeItem.getValue());
                break;
            case TEAM:
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.TEAM_EDIT,
                        (Team) Global.selectedTreeItem.getValue());
                break;
            case PROJECT:
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.PROJECT_EDIT,
                        (Project) Global.selectedTreeItem.getValue());
                break;
            case RELEASE:
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.RELEASE_EDIT,
                        (Release) Global.selectedTreeItem.getValue());
                break;
            case STORY:
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.STORY_EDIT,
                        (Story) Global.selectedTreeItem.getValue());
                break;
            case BACKLOG:
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.BACKLOG_EDIT,
                        (Backlog) Global.selectedTreeItem.getValue());
                break;
            case WORKSPACE:
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.WORKSPACE_EDIT,
                        (Workspace) Global.selectedTreeItem.getValue());
                break;
            case OTHER:
                System.out.println("The category was not correctly recognized");
                break;
            default:
                System.out.println("The category was not set");
                break;
        }
    }*/


    /**
     * An enumeration of the categories selected.
     * Each category my have different menu items associated.
     */
    /*public enum Categories {
        PERSON,
        SKILL,
        TEAM,
        PROJECT,
        WORKSPACE,
        ROLE,
        RELEASE,
        STORY,
        BACKLOG,
        OTHER  // For anything else, or unresolved.
    }*/
}
