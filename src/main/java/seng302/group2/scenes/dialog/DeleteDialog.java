/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.release.Release;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Optional;

/**
 * The deletion dialog for deleting workspace elements
 *
 * @author btm38
 */
public class DeleteDialog {

    /**
     * Shows a confirm dialog box for element deletion
     *
     * @param element The element to be deleted
     * @return if the user confirms for the element to be deleted
     */
    public static boolean showDeleteDialog(SaharaItem element) {
        ArrayList<String> dialogText;
        dialogText = getDeleteDialogText(element);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(dialogText.get(0));
        alert.setHeaderText("Delete " + "'" + element.toString() + "'?");
        alert.setContentText(dialogText.get(1));
        alert.getDialogPane().setStyle(" -fx-max-width:600px; -fx-max-height: 200px; -fx-pref-width: 600px; "
                + "-fx-pref-height: 200px;");
        Optional<ButtonType> result  = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            if (element.getClass() == Person.class) {
                Person deletedPerson = (Person) element;
                deletedPerson.deletePerson();
            }
            else if (element.getClass() == Project.class) {
                Project deletedProject = (Project) element;
                deletedProject.deleteProject();
                //Project.deleteProject(deletedProject);
                App.refreshMainScene();
            }
            else if (element.getClass() == Team.class) {
                Team deletedTeam = (Team) element;
                deletedTeam.deleteTeamCascading();
            }
            else if (element.getClass() == Skill.class) {
                Skill deletedSkill = (Skill) element;
                deletedSkill.deleteSkill();
            }
            else if (element.getClass() == Release.class) {
                Release deletedRelease = (Release) element;
                deletedRelease.deleteRelease();
            }
            else if (element.getClass() == Story.class) {
                Story deletedStory = (Story) element;
                deletedStory.deleteStory();
            }
            else if (element.getClass() == Backlog.class) {
                Backlog deletedBacklog = (Backlog) element;
                deletedBacklog.deleteBacklog();
            }
            else {
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
     *
     * @param element The selected element
     * @return The text of the delete dialog
     */
    private static ArrayList<String> getDeleteDialogText(SaharaItem element) {
        ArrayList<String> dialogText = new ArrayList<>(2);
        String title;
        String message;
        if (element.getClass() == Person.class) {
            title = "Delete Person?";
            Person deletedPerson = (Person) element;
            if (deletedPerson.getTeamName().equals("Unassigned")) {
                message = MessageFormat.format(
                        "Are you sure you want to delete {0}", deletedPerson.toString() + "?");
            }
            else {
                message = MessageFormat.format(
                        "Are you sure you want to delete {0}, currently in the team \"{1}",
                        deletedPerson.toString(), deletedPerson.getTeamName() + "\"?");
            }
        }
        else if (element.getClass() == Skill.class) {
            title = "Delete Skill?";
            String customSkillMessage;
            String namesOfPeopleWithSkill = "";
            Skill deletedSkill = (Skill) element;
            ArrayList<Person> peopleWithSkill = new ArrayList<>();
            for (Person person : Global.currentWorkspace.getPeople()) {
                if (person.getSkills().contains(deletedSkill)) {
                    peopleWithSkill.add(person);
                }
            }

            // Build a String list of people with the skill.
            if (peopleWithSkill.size() > 1 && peopleWithSkill.size() < 7) {
                int i = 0;
                while (i < peopleWithSkill.size()) {
                    if (i == peopleWithSkill.size() - 1) {
                        namesOfPeopleWithSkill = namesOfPeopleWithSkill.substring(
                                0, namesOfPeopleWithSkill.length() - 2);
                        namesOfPeopleWithSkill += (" and " + peopleWithSkill.get(i))
                                + " ";
                    }
                    else {
                        namesOfPeopleWithSkill += (peopleWithSkill.get(i)).toString() + ", ";
                    }
                    i += 1;
                }
            }
            else if (peopleWithSkill.size() >= 7) {
                int i = 0;
                while (i < 6) {
                    if (i == 5) {
                        namesOfPeopleWithSkill += (peopleWithSkill.get(i)).toString() + " ";
                    }
                    else {
                        namesOfPeopleWithSkill += (peopleWithSkill.get(i)).toString() + ", ";
                    }
                    i += 1;
                }
            }

            // Create the appropriate message following common grammar conventions
            if (peopleWithSkill.size() == 0) {
                customSkillMessage = "";
            }
            else if (peopleWithSkill.size() == 1) {
                customSkillMessage = peopleWithSkill.get(0).toString()
                        + " currently has this skill.";
            }
            else if (peopleWithSkill.size() < 7) {
                customSkillMessage = namesOfPeopleWithSkill + "currently have this skill.";
            }
            else if (peopleWithSkill.size() == 7) {
                customSkillMessage = namesOfPeopleWithSkill
                        + "and 1 other person currently have this skill.";
            }
            else {
                customSkillMessage = MessageFormat.format(namesOfPeopleWithSkill
                                + "and {0} other people currently have this skill.",
                        peopleWithSkill.size() - 6);
            }

            message = MessageFormat.format("Are you sure you want to delete the skill \"{0}",
                    deletedSkill.toString() + "\"?\n" + customSkillMessage);
        }
        else if (element.getClass() == Team.class) {
            title = "Delete Team";
            Team deletedTeam = (Team) element;
            message = MessageFormat.format("Are you sure you want to delete the team \"{0}",
                    deletedTeam.toString() + "\"? \nWARNING: All people "
                            + "currently part of the team will also be deleted.");
        }
        else if (element.getClass() == Project.class) {
            title = "Delete Project";
            Project deletedProject = (Project) element;
            message = MessageFormat.format("Are you sure you want to delete the project \"{0}",
                    deletedProject.toString() + "\"?");
        }
        else if (element.getClass() == Release.class) {
            title = "Delete Release";
            Release deletedRelease = (Release) element;
            message = MessageFormat.format("Are you sure you want to delete the release \"{0}",
                    deletedRelease.toString() + "\"?");
        }
        else if (element.getClass() == Story.class) {
            title = "Delete Story";
            Story deletedStory = (Story) element;
            message = MessageFormat.format("Are you sure you want to delete the story \"{0}",
                    deletedStory.toString() + "\"?");
        }
        else if (element.getClass() == Backlog.class) {
            title = "Delete Backlog";
            Backlog deletedBacklog = (Backlog) element;
            message = MessageFormat.format("Are you sure you want to delete the backlog \"{0}",
                    deletedBacklog.toString() + "\"?");
        }
        else {
            title = "Delete Item";
            message = MessageFormat.format("Are you sure you want to delete {0}",
                    Global.selectedTreeItem.getValue().toString() + "?");
        }
        dialogText.add(title);
        dialogText.add(message);
        return dialogText;
    }
}    
