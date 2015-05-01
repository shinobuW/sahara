package seng302.group2.scenes.contextmenu;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
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

import java.text.MessageFormat;
import java.util.ArrayList;

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
                showDeleteDialog(finalSelectedCategory);
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


    /**
     * Shows a confirm dialog box for element deletion
     * @param category Type of Category
     */
    public static void showDeleteDialog(Categories category)
    {
        ArrayList<String> dialogText = new ArrayList<String>(2);
        dialogText = getDeleteDialogText(category);

        
        Action response = Dialogs.create()
            .title(dialogText.get(0))
            .message(dialogText.get(1))
            .actions(Dialog.ACTION_YES, Dialog.ACTION_CANCEL) 
            .showConfirm();

        if (response == Dialog.ACTION_YES)
        {
            switch (category)
            {
                case PERSON:
                    Person.deletePerson((Person)Global.selectedTreeItem.getValue());
                    break;
                case PROJECT:
                    Project.deleteProject((Project)Global.selectedTreeItem.getValue());
                    break;
                case TEAM:
                    Team.deleteTeam((Team)Global.selectedTreeItem.getValue());
                    break;
                case SKILL:
                    Skill.deleteSkill((Skill)Global.selectedTreeItem.getValue());               
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
    
    
    /**
     * Returns different titles and deletion messages depending on the category passed into the 
     * function. Returns an arraylist of strings of size two where index 0 is the title of the
     * dialog and index 1 is the message of the dialog box.
     * @param category The selected category
     * @return The text of the delete dialog
     */
    public static ArrayList<String> getDeleteDialogText(Categories category)
    {
        ArrayList<String> dialogText = new ArrayList<String>(2);
        String title = "";
        String message = "";
        switch (category)
        {
            case PERSON:
                title = "Delete Person?";
                Person deletedPerson = (Person)Global.selectedTreeItem.getValue();  
                if  (deletedPerson.getTeamName().equals("Unassigned"))
                {
                    message = MessageFormat.format(
                        "Are you sure you want to delete {0}", deletedPerson.toString() + "?");
                }
                else
                {
                    message = MessageFormat.format(
                        "Are you sure you want to delete {0}, currently in Team {1}", 
                        deletedPerson.toString(), deletedPerson.getTeamName() + "?");
                }

                break;
            case SKILL:
                title = "Delete Skill?";
                Skill deletedSkill = (Skill)Global.selectedTreeItem.getValue();
                ArrayList<Person> peopleWithSkill = new ArrayList<>();
                for (Person person : Global.currentWorkspace.getPeople())
                {
                    if (person.getSkills().contains(deletedSkill))
                    {
                        peopleWithSkill.add(person);
                    }
                }
                
                if (peopleWithSkill.size() > 0)
                {
                    int i = 0;
                    String namesOfPeopleWithSkill = "";
                    String customMessage = "";
                    while (i < 6 && i < peopleWithSkill.size())
                    {
                        namesOfPeopleWithSkill += (peopleWithSkill.get(i)).toString() + ", ";
                        i += 1;
                    }
                    if (peopleWithSkill.size() < 7)
                    {
                        customMessage = namesOfPeopleWithSkill + "currently have this skill.";
                    }
                    else if (peopleWithSkill.size() == 7)
                    {
                        customMessage = namesOfPeopleWithSkill 
                            + "and 1 other currently have this skill.";
                    }
                    else
                    {
                        customMessage = MessageFormat.format(namesOfPeopleWithSkill 
                            + "and {0} others currently have this skill.",
                            peopleWithSkill.size() - 6);
                    }

                    message = MessageFormat.format("Are you sure you want to delete the skill {0}",
                        deletedSkill.toString() + "?\n" + customMessage);
                }
                else
                {
                    message = MessageFormat.format("Are you sure you want to delete {0}", 
                        Global.selectedTreeItem.getValue().toString() + "?");
                }
                break;
            case TEAM:
                title = "Delete Team";
                message = MessageFormat.format("Are you sure you want to delete team {0}", 
                    Global.selectedTreeItem.getValue().toString() + "? \nWARNING: All people "
                    + "currently part of the team will be deleted as well as the team.");
                break;
            case PROJECT:
                title = "Delete Project";
                message = MessageFormat.format("Are you sure you want to delete {0}", 
                        Global.selectedTreeItem.getValue().toString() + "?");
                break;                
            default:
                title = "Delete Item";
                message = MessageFormat.format("Are you sure you want to delete {0}", 
                        Global.selectedTreeItem.getValue().toString() + "?");
                break;
        }
        dialogText.add(title);
        dialogText.add(message);
        return dialogText;   
    }  
}
