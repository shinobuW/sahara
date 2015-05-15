package seng302.group2.scenes.contextmenu;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.SceneSwitcher;
import seng302.group2.scenes.information.person.PersonEditScene;
import seng302.group2.scenes.information.project.ProjectEditScene;
import seng302.group2.scenes.information.release.ReleaseEditScene;
import seng302.group2.scenes.information.skill.SkillEditScene;
import seng302.group2.scenes.information.team.TeamEditScene;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.release.Release;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;


/**
 *ContextMenu class for instances of Person, Skill and Team
 * @author swi67, jml168, btm38
 */
@SuppressWarnings("deprecation")
public class ElementTreeContextMenu extends ContextMenu
{
    /**
     * An enumeration of the categories selected.
     * Each category my have different menu items associated.
     */
    public enum Categories
    {
        PERSON,
        SKILL,
        TEAM,
        PROJECT,
        WORKSPACE,
        ROLE,
        RELEASE,
        OTHER  // For anything else, or unresolved.
    }


    /**
     * Constructor. Sets the event for contextMenu buttons
     */
    public ElementTreeContextMenu()
    {
        MenuItem deleteItem = new MenuItem("Delete");
        MenuItem editItem = new MenuItem("Edit");
        
        Categories selectedCategory = getSelectedCategory();

        final Categories finalSelectedCategory = selectedCategory;  // for use in lamda expr.

        //"Edit" button event
        editItem.setOnAction(e ->
            {
                showEditScene(finalSelectedCategory);
            });
        
        //"Delete" button event
        deleteItem.setOnAction(e -> 
            {
                showDeleteDialog((TreeViewItem)Global.selectedTreeItem.getValue());
            });

        this.getItems().addAll(editItem, deleteItem);

        if (selectedCategory == Categories.TEAM)
        {
            Team selectedTeam = (Team) Global.selectedTreeItem.getValue();
            if (selectedTeam.isUnassignedTeam())
            {
                editItem.setDisable(true);
                deleteItem.setDisable(true);
            }
            else
            {
                editItem.setDisable(false);
                deleteItem.setDisable(false);
            }
        }
        
        if (selectedCategory == Categories.ROLE)
        {
            Role selectedRole = (Role) Global.selectedTreeItem.getValue();
            if (!selectedRole.isDefault())
            {
                editItem.setDisable(true);
                deleteItem.setDisable(true);
            }
            else
            {
                editItem.setDisable(false);
                deleteItem.setDisable(false);
            }
        }
        
        if (selectedCategory == Categories.SKILL)
        {
            Skill selectedSkill = (Skill) Global.selectedTreeItem.getValue();
            if (selectedSkill.getShortName().equals("Scrum Master")
                    || selectedSkill.getShortName().equals("Product Owner"))
            {
                editItem.setDisable(true);
                deleteItem.setDisable(true);
            }
            else
            {
                editItem.setDisable(false);
                deleteItem.setDisable(false);
            }
        }
        
        if (selectedCategory == Categories.WORKSPACE)
        {
            deleteItem.setDisable(true);
        }
    }


    /**
     * Gets the category of the selected tree item.
     * @return An enum value of the selected tree item's category
     */
    public static Categories getSelectedCategory()
    {
        Categories selectedCategory = Categories.OTHER;
        if (Global.selectedTreeItem.getValue().getClass() == Person.class)
        {
            selectedCategory = Categories.PERSON;
        }
        else if (Global.selectedTreeItem.getValue().getClass() == Skill.class)
        {
            selectedCategory = Categories.SKILL;
        }
        else if (Global.selectedTreeItem.getValue().getClass() == Team.class)
        {
            selectedCategory = Categories.TEAM;
        }
        else if (Global.selectedTreeItem.getValue().getClass() == Workspace.class)
        {
            selectedCategory = Categories.WORKSPACE;
        }
        else if (Global.selectedTreeItem.getValue().getClass() == Project.class)
        {
            selectedCategory = Categories.PROJECT;
        }
        else if (Global.selectedTreeItem.getValue().getClass() == Role.class)
        {
            selectedCategory = Categories.ROLE;
        }
        else if (Global.selectedTreeItem.getValue().getClass() == Release.class)
        {
            selectedCategory = Categories.RELEASE;
        }        
        return selectedCategory;
    }


    /**
     * Displays the edit scene for given element
     * @param category Type of Category
     */
    public static void showEditScene(Categories category)
    {
        switch (category)
        {
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
    }
}
