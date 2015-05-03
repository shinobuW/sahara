package seng302.group2.scenes.contextmenu;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.information.*;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.release.Release;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;
import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;
import seng302.group2.scenes.listdisplay.TreeViewItem;


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

        if (selectedCategory == Categories.PROJECT)
        {
            deleteItem.setDisable(true);
        }

        if (selectedCategory == Categories.RELEASE)
        {
            deleteItem.setDisable(true);
        }

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
        App.content.getChildren().remove(MainScene.informationGrid);
        
        switch (category)
        {
            case PERSON:
                PersonEditScene.getPersonEditScene((Person) Global.selectedTreeItem.getValue());
                break;
            case SKILL:
                SkillEditScene.getSkillEditScene((Skill) Global.selectedTreeItem.getValue());
                break;
            case TEAM:
                TeamEditScene.getTeamEditScene((Team) Global.selectedTreeItem.getValue());
                break;
            case PROJECT:
                ProjectEditScene.getProjectEditScene((Project) Global.selectedTreeItem.getValue());
                break;
            case RELEASE:
                ReleaseEditScene.getReleaseEditScene((Release) Global.selectedTreeItem.getValue());
                break;
            case OTHER:
                System.out.println("The category was not correctly recognized");
                break;
            default:
                System.out.println("The category was not set");
                break;
        }
        App.content.getChildren().add(MainScene.informationGrid);
    }
}
