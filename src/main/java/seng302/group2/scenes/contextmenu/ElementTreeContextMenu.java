package seng302.group2.scenes.contextmenu;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.information.PersonEditScene;
import seng302.group2.scenes.information.SkillEditScene;
import seng302.group2.scenes.information.TeamEditScene;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

import java.text.MessageFormat;

/**
 *ContextMenu class for instances of Person, Skill and Team
 * @author swi67, jml168
 */
@SuppressWarnings("deprecation")
public class ElementTreeContextMenu extends ContextMenu
{
    /**
     * An enumeration of the categories selected.
     * Each category my have different menu items associated.
     */
    private enum Categories
    {
        PERSON,
        SKILL,
        TEAM,
        PROJECT,
        WORKSPACE,
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
                showDeleteDialog(finalSelectedCategory);
            });

        this.getItems().addAll(editItem, deleteItem);
        if (selectedCategory == Categories.TEAM)
        {
            Team selectedTeam = (Team) Global.selectedTreeItem.getValue();
            if (selectedTeam.isUnassignedTeam())
            {
                editItem.setDisable(true);
            }
            else
            {
                editItem.setDisable(false);
            }
        }
    }


    /**
     * Gets the category of the selected tree item.
     * @return An enum value of the selected tree item's category
     */
    private Categories getSelectedCategory()
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
                // TODO: Project, not Workspace
                /*
                WorkspaceEditScene.getWorkspaceEditScene(
                        (Project) Global.selectedTreeItem.getValue());
                        */
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


    /**
     * Shows a confirm dialog box for element deletion
     * @param category Type of Category
     */
    public static void showDeleteDialog(Categories category)
    {
        Action response = Dialogs.create()
            .title("Delete item?")
            .message(MessageFormat.format("Are you sure you want to delete {0}",
                    Global.selectedTreeItem.getValue().toString() + "?"))
            .showConfirm();

        if (response == Dialog.ACTION_YES || response == Dialog.ACTION_OK)
        {
            switch (category)
            {
                case PERSON:
                    Global.currentWorkspace.remove((Person) Global.selectedTreeItem.getValue());
                    break;
                case PROJECT:
                    Global.currentWorkspace.remove((Project) Global.selectedTreeItem.getValue());
                    break;
                case TEAM:
                    Global.currentWorkspace.remove((Team) Global.selectedTreeItem.getValue());
                    break;
                case SKILL:
                    Global.currentWorkspace.remove((Skill) Global.selectedTreeItem.getValue());
                    break;
                case OTHER:
                    System.out.println("Can't delete unknown selected class");
                    break;
                default:
                    System.out.println("Did not identify the class of object to delete");
                    break;
            }
        }
    }
}
