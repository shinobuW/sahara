/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.dialog;

import java.text.MessageFormat;
import java.util.ArrayList;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import seng302.group2.Global;
import seng302.group2.scenes.contextmenu.ElementTreeContextMenu;
import seng302.group2.scenes.contextmenu.ElementTreeContextMenu.Categories;
import static seng302.group2.scenes.contextmenu.ElementTreeContextMenu.Categories.OTHER;
import static seng302.group2.scenes.contextmenu.ElementTreeContextMenu.Categories.PERSON;
import static seng302.group2.scenes.contextmenu.ElementTreeContextMenu.Categories.PROJECT;
import static seng302.group2.scenes.contextmenu.ElementTreeContextMenu.Categories.SKILL;
import static seng302.group2.scenes.contextmenu.ElementTreeContextMenu.Categories.TEAM;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

/**
 * TODO
 * @author btm38
 */
public class DeleteDialog
{
/**
     * Shows a confirm dialog box for element deletion
     * @param category Type of Category
     */
    public static boolean showDeleteDialog(TreeViewItem element)
    {
        ArrayList<String> dialogText = new ArrayList<String>(2);
        dialogText = getDeleteDialogText(element);

        
        Action response = Dialogs.create()
            .title(dialogText.get(0))
            .message(dialogText.get(1))
            .actions(Dialog.ACTION_YES, Dialog.ACTION_CANCEL) 
            .showConfirm();

        if (response == Dialog.ACTION_YES)
        {
            if (element.getClass() == Person.class)
            {
                Person deletedPerson = (Person)element;
                Person.deletePerson(deletedPerson);
            }
            else if (element.getClass() == Project.class)
            {
                Project deletedProject = (Project)element;
                Project.deleteProject(deletedProject);
            }
            else if (element.getClass() == Team.class)
            {
                Team deletedTeam = (Team)element;
                Team.deleteTeam(deletedTeam);
            }
            else if (element.getClass() == Skill.class)
            {
                Skill deletedSkill = (Skill)element;
                Skill.deleteSkill(deletedSkill);            
            }
            else
            {
                System.out.printf("Deletion dialog for that element has not been deleted");
                return false;
            }
        }
        return false;
    }
    
    
    /**
     * Returns different titles and deletion messages depending on the category passed into the 
     * function. Returns an arraylist of strings of size two where index 0 is the title of the
     * dialog and index 1 is the message of the dialog box.
     * @param element The selected element
     * @return The text of the delete dialog
     */
    public static ArrayList<String> getDeleteDialogText(TreeViewItem element)
    {
        ArrayList<String> dialogText = new ArrayList<String>(2);
        String title = "";
        String message = "";
        if (element.getClass() == Person.class)
        {
            title = "Delete Person?";
            Person deletedPerson = (Person)element;  
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
        }
        else if (element.getClass() == Skill.class)
        {
            title = "Delete Skill?";
            Skill deletedSkill = (Skill)element;
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
                    deletedSkill.toString() + "?");
            }
        }
        else if (element.getClass() == Team.class)
        {
            title = "Delete Team";
            Team deletedTeam = (Team)element;
            message = MessageFormat.format("Are you sure you want to delete team {0}", 
                deletedTeam.toString() + "? \nWARNING: All people "
                + "currently part of the team will be deleted as well as the team.");
        }
        else if (element.getClass() == Project.class)
        {
            title = "Delete Project";
            Project deletedProject = (Project)element;
            message = MessageFormat.format("Are you sure you want to delete {0}", 
                    deletedProject.toString() + "?");
        }              
        else
        {
            title = "Delete Item";
            message = MessageFormat.format("Are you sure you want to delete {0}", 
                    Global.selectedTreeItem.getValue().toString() + "?");
        }
        dialogText.add(title);
        dialogText.add(message);
        return dialogText;   
    }  
}    
