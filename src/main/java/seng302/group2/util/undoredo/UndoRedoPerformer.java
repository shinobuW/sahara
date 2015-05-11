/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.undoredo;

import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.information.person.PersonScene;
import seng302.group2.scenes.information.release.ReleaseScene;
import seng302.group2.scenes.information.skill.SkillScene;
import seng302.group2.scenes.information.team.TeamScene;
import seng302.group2.scenes.information.workspace.WorkspaceScene;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.release.Release;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

import java.util.ArrayList;
import java.util.Date;

import static seng302.group2.scenes.MainScene.informationPane;
/**
 * A class that handles the ugly undo/redo work behind the scenes on undoable items.
 * @author jml168
 */
public class UndoRedoPerformer
{
    /**
     * An enumerable action identifier for undo/redo items.
     * @author jml168
     */
    public static enum UndoRedoProperty
    {
        WORKSPACE,
        WORKSPACE_SHORTNAME,
        WORKSPACE_LONGNAME,
        WORKSPACE_DESCRIPTION,
        WORKSPACE_EDIT,

        PROJECT_ADD,
        PROJECT_DEL,
        PROJECT_SHORTNAME,
        PROJECT_LONGNAME,
        PROJECT_DESCRIPTION,
        PROJECT_EDIT,

        PERSON_ADD,
        PERSON_DEL,
        PERSON_DEL_RECURSIVE,
        PERSON_SHORTNAME,
        PERSON_FIRSTNAME,
        PERSON_LASTNAME,
        PERSON_EMAIL,
        PERSON_DESCRIPTION,
        PERSON_BIRTHDATE,
        PERSON_TEAM,
        PERSON_ADD_TEAM,
        PERSON_DEL_TEAM,
        PERSON_EDIT,
        PERSON_ADD_ROLE,
        PERSON_DEL_ROLE,
        
        SKILL_ADD,
        SKILL_DEL,
        SKILL_DEL_RECURSIVE,
        SKILL_SHORTNAME,
        SKILL_DESCRIPTION,
        SKILL_ADD_PERSON,
        SKILL_DEL_PERSON,
        SKILL_EDIT,
        
        TEAM_ADD,
        TEAM_DEL,
        TEAM_DEL_RECURSIVE,
        TEAM_SHORTNAME,
        TEAM_DESCRIPTION,
        TEAM_PROJECT,
        TEAM_ADD_PROJECT,
        TEAM_DEL_PROJECT,
        TEAM_EDIT,

        RELEASE_ADD,
        RELEASE_DEL,
        RELEASE_EDIT,
        RELEASE_SHORTNAME,
        RELEASE_RELEASEDATE,
        RELEASE_DESCRIPTION,
        RELEASE_PROJECT
    }

    
    /**
     * Performs the relevant undo on the underlying undoable item.
     * @param item The UndoableItem to undo
     */
    public static void undo(UndoableItem item)
    {
        Class objClass = item.getHost().getClass();
        
        /* Workspace actions */
        if (objClass == Workspace.class)
        {
            Workspace proj = (Workspace) item.getHost();
            switch (item.getUndoAction().getProperty())
            {
                case WORKSPACE_SHORTNAME:
                    proj.setShortName((String) item.getUndoAction().getValue());
                    System.out.println("Workspace short name change undone ("
                            + item.getUndoAction().getValue() + ")");
                    break;
                case WORKSPACE_LONGNAME:
                    proj.setLongName((String) item.getUndoAction().getValue());
                    System.out.println("Workspace name change undone ("
                            + item.getUndoAction().getValue() + ")");
                    break;
                case WORKSPACE_DESCRIPTION:
                    proj.setDescription((String) item.getUndoAction().getValue());
                    System.out.println("Workspace description change undone ("
                            + item.getUndoAction().getValue() + ")");
                    break;
                case WORKSPACE_EDIT:
                    for (UndoableItem undoAction : (ArrayList<UndoableItem>)
                            item.getUndoAction().getValue())
                    {
                        UndoRedoPerformer.undo(undoAction);
                    }
                    //WorkspaceScene.refreshWorkspaceScene(proj); //Commeneted for unit testing
                    break;
                default:
                    System.out.println("Undo with this property not implemented (yet?)");
                    break;
            }
        }

        /* Project actions */
        if (objClass == Project.class)
        {
            Project proj = (Project) item.getHost();
            switch (item.getUndoAction().getProperty())
            {
                case PROJECT_ADD:
                    Global.currentWorkspace.getProjects().remove((Project) item.getHost());
                    System.out.println("Project creation undone ("
                            + item.getUndoAction().getValue() + ")");
                    break;
                case PROJECT_DEL:
                    Global.currentWorkspace.getProjects().add((Project) item.getHost());
                    System.out.println("Project deletion undone ("
                            + item.getUndoAction().getValue() + ")");
                    break;
                case PROJECT_SHORTNAME:
                    proj.setShortName((String) item.getUndoAction().getValue());
                    System.out.println("Project short name change undone ("
                            + item.getUndoAction().getValue() + ")");
                    break;
                case PROJECT_LONGNAME:
                    proj.setLongName((String) item.getUndoAction().getValue());
                    System.out.println("Project long name change undone ("
                            + item.getUndoAction().getValue() + ")");
                    break;
                case PROJECT_DESCRIPTION:
                    proj.setDescription((String) item.getUndoAction().getValue());
                    System.out.println("Project description change undone ("
                            + item.getUndoAction().getValue() + ")");
                    break;
                case PROJECT_EDIT:
                    for (UndoableItem undoAction : (ArrayList<UndoableItem>)
                            item.getUndoAction().getValue())
                    {
                        UndoRedoPerformer.undo(undoAction);
                    }
                    System.out.println("Project edit undone");
                    //ProjectScene.refreshProjectScene(proj);
                    break;
                default:
                    System.out.println("Undo with this property not implemented (yet?)");
                    break;
            }
        }

        /* Person actions */
        else if (objClass == Person.class)
        {
            Person person = (Person) item.getHost();
            System.out.println(person + " " + item.getUndoAction().getValue() 
                    + item.getUndoAction().getProperty());
            switch (item.getUndoAction().getProperty())
            {
                case PERSON_ADD:
                    Global.currentWorkspace.getPeople().remove((Person) item.getHost());
                    break;
                case PERSON_DEL:
                    Global.currentWorkspace.getPeople().add((Person) item.getHost());
                    break;
                case PERSON_DEL_RECURSIVE:
                    for (UndoableItem undoAction : (ArrayList<UndoableItem>)
                            item.getUndoAction().getValue())
                    {
                        UndoRedoPerformer.undo(undoAction);
                    }
                    PersonScene.refreshPersonScene(person);
                    break;
                case PERSON_SHORTNAME:
                    person.setShortName((String) item.getUndoAction().getValue());
                    break;    
                case PERSON_FIRSTNAME:
                    person.setFirstName((String) item.getUndoAction().getValue());
                    break;
                case PERSON_LASTNAME:
                    person.setLastName((String) item.getUndoAction().getValue());
                    break;
                case PERSON_EMAIL:
                    person.setEmail((String) item.getUndoAction().getValue());
                    break;
                case PERSON_DESCRIPTION:
                    person.setDescription((String) item.getUndoAction().getValue());
                    break;
                case PERSON_BIRTHDATE:
                    person.setBirthDate((Date) item.getUndoAction().getValue());
                    break;
                case PERSON_TEAM:
                    person.setTeam((Team) item.getUndoAction().getValue());
                    break;
                case PERSON_ADD_ROLE:
                    Role role = (Role) item.getUndoAction().getValue();
                    if (person.getRole().toString().equals("Scrum Master"))
                    {
                        person.getTeam().setScrumMaster(null);
                    }
                    else if (person.getRole().toString().equals("Product Owner"))
                    {
                        person.getTeam().setProductOwner(null);
                    }
                    if (role != null) 
                    {
                        if (role.toString().equals("Scrum Master")) 
                        {
                            person.getTeam().setScrumMaster(person);
                        }
                        else if (role.toString().equals("Product Owner"))
                        {
                            person.getTeam().setProductOwner(person);
                        }
                    }
                    person.setRole((Role) item.getUndoAction().getValue());
                    break;
                case PERSON_DEL_ROLE:
                    role = (Role) item.getUndoAction().getValue();
                    if (person.getRole().toString().equals("Scrum Master"))
                    {
                        person.getTeam().setScrumMaster(null);
                    }
                    else if (person.getRole().toString().equals("Product Owner"))
                    {
                        person.getTeam().setProductOwner(null);
                    }
                    if (role.toString().equals("Scrum Master")) 
                    {
                        person.getTeam().setScrumMaster(person);
                    }
                    else if (role.toString().equals("Product Owner"))
                    {
                        person.getTeam().setProductOwner(person);
                    }
                    person.setRole(role);
                    break;
                case PERSON_ADD_TEAM:
                    Team currentTeam = (Team) item.getUndoAction().getValue();
                    person.getTeam().getPeople().remove(person);
                    currentTeam.getPeople().add(person);
                    App.content.getItems().remove(informationPane);
                    TeamScene.getTeamScene(currentTeam);
                    App.content.getItems().add(informationPane);
                    break;
                case PERSON_DEL_TEAM:
                    currentTeam = (Team) item.getUndoAction().getValue();
                    currentTeam.getPeople().add(person);
                    if (!(currentTeam == Global.getUnassignedTeam()))
                    {
                        Global.getUnassignedTeam().remove(person);
                    }
                    person.setTeam(currentTeam);
                    App.content.getItems().remove(informationPane);
                    TeamScene.getTeamScene(currentTeam);
                    App.content.getItems().add(informationPane);
                    break;
                case PERSON_EDIT:
                    for (UndoableItem undoAction : (ArrayList<UndoableItem>)
                            item.getUndoAction().getValue()) 
                    {
                        UndoRedoPerformer.undo(undoAction);
                    }
                    PersonScene.refreshPersonScene(person);
                    break;
                default:
                    System.out.println(item.getUndoAction().getProperty());
                    System.out.println("Undo on person with this property not implemented (yet?)");
                    break;
            }
        }
        
        /* Skill Actions */
        else if (objClass == Skill.class)
        {
            Skill skill = (Skill) item.getHost();
            
            switch (item.getUndoAction().getProperty())
            {
                case SKILL_ADD:
                    Global.currentWorkspace.getSkills().remove((Skill) item.getHost());
                    break;
                case SKILL_DEL:
                    Global.currentWorkspace.getSkills().add((Skill) item.getHost());
                    break;
                case SKILL_DEL_RECURSIVE:
                    for (UndoableItem undoAction : (ArrayList<UndoableItem>)
                            item.getUndoAction().getValue())
                    {
                        UndoRedoPerformer.undo(undoAction);
                    }
                    SkillScene.refreshSkillScene(skill);
                    break;
                case SKILL_SHORTNAME:
                    skill.setShortName((String) item.getUndoAction().getValue());
                    break;
                case SKILL_DESCRIPTION:
                    skill.setDescription((String) item.getUndoAction().getValue());
                    break;
                case SKILL_ADD_PERSON:
                    Person currentPerson = (Person) item.getUndoAction().getValue();
                    currentPerson.getSkills().remove(skill);
                    App.content.getItems().remove(informationPane);
                    PersonScene.getPersonScene(currentPerson);
                    App.content.getItems().add(informationPane);
                    break;
                case SKILL_DEL_PERSON:
                    currentPerson = (Person) item.getUndoAction().getValue();
                    currentPerson.getSkills().add(skill);
                    App.content.getItems().remove(informationPane);
                    PersonScene.getPersonScene(currentPerson);
                    App.content.getItems().add(informationPane);
                    break;
                case SKILL_EDIT:
                    for (UndoableItem undoAction : (ArrayList<UndoableItem>)
                            item.getUndoAction().getValue()) 
                    {
                        UndoRedoPerformer.undo(undoAction);
                    }
                    SkillScene.refreshSkillScene(skill);
                    break;
                default:
                    System.out.println("Undo on skill with this property not implemented (yet?)");
                    break;                    
            }
        }
        
        /* Team Actions */
        else if (objClass == Team.class)
        {
            Team team = (Team) item.getHost();
            switch (item.getUndoAction().getProperty())
            {
                case TEAM_ADD:
                    Global.currentWorkspace.getTeams().remove((Team) item.getHost());
                    break;
                case TEAM_DEL:
                    Global.currentWorkspace.getTeams().add((Team) item.getHost());
                    break;
                case TEAM_DEL_RECURSIVE:
                    for (UndoableItem undoAction : (ArrayList<UndoableItem>)
                            item.getUndoAction().getValue())
                    {
                        UndoRedoPerformer.undo(undoAction);
                    }
                    TeamScene.refreshTeamScene(team);
                    break;
                case TEAM_SHORTNAME:
                    System.out.println("TEAM_SHORTNAME" + "  " + item.getUndoAction().getValue());
                    team.setShortName((String) item.getUndoAction().getValue());
                    break;
                case TEAM_DESCRIPTION:
                    System.out.println("TEAM DESC" + "  " + item.getUndoAction().getValue());
                    team.setDescription((String) item.getUndoAction().getValue());
                    break;
                case TEAM_PROJECT:
                    team.setProject((Project) item.getUndoAction().getValue());
                    break;
                case TEAM_ADD_PROJECT:
                    Project currentProject = (Project) item.getUndoAction().getValue();
                    team.getProject().getTeams().remove(team);
                    if (currentProject != null)
                    {
                        currentProject.getTeams().add(team);
                    }
                    App.content.getItems().remove(informationPane);
                    //ProjectScene.getProjectScene(team.getProject());
                    App.content.getItems().add(informationPane);
                    break;
                case TEAM_DEL_PROJECT:
                    currentProject = (Project) item.getUndoAction().getValue();
                    currentProject.getTeams().add(team);
                    App.content.getItems().remove(informationPane);
                    //ProjectScene.getProjectScene(currentProject);
                    App.content.getItems().add(informationPane);
                    break;
                case TEAM_EDIT:
                    for (UndoableItem undoAction : (ArrayList<UndoableItem>)
                            item.getUndoAction().getValue()) 
                    {
                        UndoRedoPerformer.undo(undoAction);
                    }
                    TeamScene.refreshTeamScene(team);
                    break;
                default:
                    System.out.println("Undo on skill with this property not implemented (yet?)");
                    break;                    
            }
        }
        else if (objClass == Release.class)
        {
            Release release = (Release)item.getHost();
            Project project = release.getProject();
            switch (item.getUndoAction().getProperty())
            {
                case RELEASE_ADD:
                    release.getProject().getReleases().remove(release);
                    break;
                case RELEASE_DEL:
                    project.getReleases().add(release);
                    break;
                case RELEASE_SHORTNAME:
                    System.out.println("RELEASE_SHORTNAME" + "  "
                            + item.getUndoAction().getValue());
                    release.setShortName((String)item.getUndoAction().getValue());
                    break;
                case RELEASE_RELEASEDATE:
                    System.out.println("RELEASE_RELEASEDATE" + "  "
                            + item.getUndoAction().getValue());
                    release.setEstimatedDate((Date)item.getUndoAction().getValue());
                    break;
                case RELEASE_DESCRIPTION:
                    System.out.println("RELEASE_DESCRIPTION" + "  "
                            + item.getUndoAction().getValue());
                    release.setDescription((String)item.getUndoAction().getValue());
                    break;
                case RELEASE_PROJECT:
                    System.out.println("RELEASE_PROJECT" + "  "
                            + item.getUndoAction().getValue());
                    release.setProject((Project)item.getUndoAction().getValue());
                    break;
                case RELEASE_EDIT:
                    for (UndoableItem undoAction : (ArrayList<UndoableItem>)
                            item.getUndoAction().getValue())
                    {
                        UndoRedoPerformer.undo(undoAction);
                    }
                    ReleaseScene.refreshReleaseScene(release);
                    break;
                default:
                    System.out.println("Undo on release with this property not implemented (yet?)");
                    break;
            }
        }
    }

    
     /**
     * Performs the relevant redo on the underlying undoable item.
     * @param item The UndoableItem to redo
     */
    public static void redo(UndoableItem item)
    {
        Class objClass = item.getHost().getClass();
        
        /* Workspace actions */
        if (objClass == Workspace.class)
        {
            Workspace proj = (Workspace) item.getHost();
            switch (item.getRedoAction().getProperty())
            {
                case WORKSPACE_SHORTNAME:
                    proj.setShortName((String) item.getRedoAction().getValue());
                    break;
                case WORKSPACE_LONGNAME:
                    proj.setLongName((String) item.getRedoAction().getValue());
                    break;
                case WORKSPACE_DESCRIPTION:
                    proj.setDescription((String) item.getRedoAction().getValue());
                    break;
                case WORKSPACE_EDIT:
                    for (UndoableItem undoAction : (ArrayList<UndoableItem>)
                            item.getUndoAction().getValue()) 
                    {
                        UndoRedoPerformer.redo(undoAction);
                    }
                    //WorkspaceScene.refreshWorkspaceScene(proj);
                    break;     
                default:
                    System.out.println("Redo with this property not implemented (yet?)");
                    break;
            }
        }
        
        else if (objClass == Project.class)
        {
            Project proj = (Project) item.getHost();
            switch (item.getRedoAction().getProperty())
            {
                case PROJECT_ADD:
                    Global.currentWorkspace.getProjects().add((Project) item.getHost());
                    break;
                case PROJECT_DEL:
                    Global.currentWorkspace.getProjects().remove((Project) item.getHost());
                    break;
                case PROJECT_SHORTNAME:
                    proj.setShortName((String) item.getRedoAction().getValue());
                    break;
                case PROJECT_LONGNAME:
                    proj.setLongName((String) item.getRedoAction().getValue());
                    break;
                case PROJECT_DESCRIPTION:
                    proj.setDescription((String) item.getRedoAction().getValue());
                    break;
                case PROJECT_EDIT:
                    for (UndoableItem undoAction : (ArrayList<UndoableItem>)
                            item.getUndoAction().getValue()) 
                    {
                        UndoRedoPerformer.redo(undoAction);
                    }
                    //ProjectScene.refreshProjectScene(proj);
                    break;     
                default:
                    System.out.println("Redo with this property not implemented (yet?)");
                    break;
            }
        }
        
        /* Person actions */
        else if (objClass == Person.class)
        {
            Person person = (Person) item.getHost();
            switch (item.getRedoAction().getProperty())
            {
                case PERSON_ADD:
                    Global.currentWorkspace.getPeople().add((Person) item.getHost());
                    break;
                case PERSON_DEL:
                    Global.currentWorkspace.getPeople().remove((Person) item.getHost());
                    break;
                case PERSON_DEL_RECURSIVE:
                    for (UndoableItem undoAction : (ArrayList<UndoableItem>)
                            item.getUndoAction().getValue())
                    {
                        UndoRedoPerformer.redo(undoAction);
                    }
                    PersonScene.refreshPersonScene(person);
                    break;
                case PERSON_SHORTNAME:
                    person.setShortName((String) item.getRedoAction().getValue());
                    break;
                case PERSON_FIRSTNAME:
                    person.setFirstName((String) item.getRedoAction().getValue());
                    break;
                case PERSON_LASTNAME:
                    person.setLastName((String) item.getRedoAction().getValue());
                    break;
                case PERSON_EMAIL:
                    person.setEmail((String) item.getRedoAction().getValue());
                    break;
                case PERSON_DESCRIPTION:
                    person.setDescription((String) item.getRedoAction().getValue());
                    break;
                case PERSON_BIRTHDATE:
                    person.setBirthDate((Date) item.getRedoAction().getValue());
                    break;
                case PERSON_TEAM:
                    person.setTeam((Team) item.getRedoAction().getValue());
                    break;
                case PERSON_ADD_ROLE:
                    Role role = (Role) item.getUndoAction().getValue();
                    person.setRole((Role) item.getRedoAction().getValue());
                    if (person.getRole().toString().equals("Scrum Master"))
                    {
                        person.getTeam().setScrumMaster(person);
                    }
                    else if (person.getRole().toString().equals("Product Owner"))
                    {
                        person.getTeam().setProductOwner(person);
                    }
                    if (role != null)
                    {
                        if (role.toString().equals("Scrum Master")) 
                        {
                            person.getTeam().setScrumMaster(null);
                        }
                        else if (role.toString().equals("Product Owner"))
                        {
                            person.getTeam().setProductOwner(null);
                        }
                    }
                    break;
                case PERSON_DEL_ROLE:
                    person.setRole((Role) item.getRedoAction().getValue());
                    if (person.getRole().toString().equals("Scrum Master"))
                    {
                        person.getTeam().setScrumMaster(person);
                    }
                    else if (person.getRole().toString().equals("Product Owner"))
                    {
                        person.getTeam().setProductOwner(person);
                    }
                    break;
                case PERSON_ADD_TEAM:
                    Team currentTeam = (Team) item.getRedoAction().getValue();
                    person.getTeam().getPeople().remove(person);
                    currentTeam.getPeople().add(person);
                    App.content.getItems().remove(informationPane);
                    TeamScene.getTeamScene(currentTeam);
                    App.content.getItems().add(informationPane);
                    break;
                case PERSON_DEL_TEAM:
                    currentTeam = (Team) item.getRedoAction().getValue();
                    currentTeam.getPeople().remove(person);
                    if (currentTeam == Global.getUnassignedTeam())
                    {
                        Global.getUnassignedTeam().remove(person);
                    }
                    App.content.getItems().remove(informationPane);
                    TeamScene.getTeamScene(currentTeam);
                    App.content.getItems().add(informationPane);
                    break;
                case PERSON_EDIT:
                    for (UndoableItem undoAction : (ArrayList<UndoableItem>)
                            item.getUndoAction().getValue())
                    {
                        UndoRedoPerformer.redo(undoAction);
                    }
                    PersonScene.refreshPersonScene(person);
                    break;
                default:
                    System.out.println("Redo on person with this property not implemented (yet?)");
                    break;
            }
        }

        /* Skill Actions */
        else if (objClass == Skill.class)
        {
            Skill skill = (Skill) item.getHost();
            switch (item.getRedoAction().getProperty())
            {
                case SKILL_ADD:
                    Global.currentWorkspace.getSkills().add((Skill) item.getHost());
                    break;
                case SKILL_DEL:
                    Global.currentWorkspace.getSkills().remove((Skill) item.getHost());
                    break;
                case SKILL_DEL_RECURSIVE:
                    for (UndoableItem undoAction : (ArrayList<UndoableItem>)
                            item.getUndoAction().getValue())
                    {
                        UndoRedoPerformer.redo(undoAction);
                    }
                    SkillScene.refreshSkillScene(skill);
                    break;
                case SKILL_SHORTNAME:
                    skill.setShortName((String) item.getRedoAction().getValue());
                    break;
                case SKILL_DESCRIPTION:
                    skill.setDescription((String) item.getRedoAction().getValue());
                    break;
                case SKILL_ADD_PERSON:
                    Person currentPerson = (Person) item.getUndoAction().getValue();
                    currentPerson.getSkills().add(skill);
                    App.content.getItems().remove(informationPane);
                    PersonScene.getPersonScene(currentPerson);
                    App.content.getItems().add(informationPane);
                    break;
                case SKILL_DEL_PERSON:
                    currentPerson = (Person) item.getUndoAction().getValue();
                    currentPerson.getSkills().remove(skill);
                    App.content.getItems().remove(informationPane);
                    PersonScene.getPersonScene(currentPerson);
                    App.content.getItems().add(informationPane);
                    break;
                case SKILL_EDIT:
                    for (UndoableItem undoAction : (ArrayList<UndoableItem>)
                            item.getUndoAction().getValue())
                    {
                        UndoRedoPerformer.redo(undoAction);
                    }
                    SkillScene.refreshSkillScene(skill);
                    break;
                default:
                    System.out.println("Redo on skill with this property not implemented (yet?)");
                    break;
            }
        }

        /* Team Actions */
        else if (objClass == Team.class)
        {
            Team team = (Team) item.getHost();
            switch (item.getRedoAction().getProperty())
            {
                case TEAM_ADD:
                    Global.currentWorkspace.getTeams().add((Team) item.getHost());
                    break;
                case TEAM_DEL:
                    Global.currentWorkspace.getTeams().remove((Team) item.getHost());
                    break;
                case TEAM_DEL_RECURSIVE:
                    for (UndoableItem undoAction : (ArrayList<UndoableItem>)
                            item.getUndoAction().getValue())
                    {
                        UndoRedoPerformer.redo(undoAction);
                    }
                    TeamScene.refreshTeamScene(team);
                    break;
                case TEAM_SHORTNAME:
                    team.setShortName((String) item.getRedoAction().getValue());
                    break;
                case TEAM_DESCRIPTION:
                    team.setDescription((String) item.getRedoAction().getValue());
                    break;
                case TEAM_PROJECT:
                    team.setProject((Project) item.getRedoAction().getValue());
                    break;
                case TEAM_ADD_PROJECT:
                    Project currentProject = (Project) item.getRedoAction().getValue();
                    if (team.getProject() != null)
                    {
                        team.getProject().getTeams().remove(team);
                    }
                    currentProject.getTeams().add(team);
                    App.content.getItems().remove(informationPane);
                    //ProjectScene.getProjectScene(currentProject);
                    App.content.getItems().add(informationPane);
                    break;
                case TEAM_DEL_PROJECT:
                    currentProject = (Project) item.getRedoAction().getValue();
                    currentProject.getTeams().remove(team);
                    App.content.getItems().remove(informationPane);
                    //ProjectScene.getProjectScene(currentProject);
                    App.content.getItems().add(informationPane);
                    break;
                case TEAM_EDIT:
                    for (UndoableItem undoAction : (ArrayList<UndoableItem>)
                            item.getUndoAction().getValue())
                    {
                        UndoRedoPerformer.redo(undoAction);
                    }
                    TeamScene.refreshTeamScene(team);
                    break;
                default:
                    System.out.println("Redo on Team with this property not implemented (yet?)");
                    break;
            }
        }
        else if (objClass == Release.class)
        {
            Release release = (Release)item.getHost();
            Project project = release.getProject();
            switch (item.getRedoAction().getProperty())
            {
                case RELEASE_ADD:
                    for (Project worksSpaceProject : Global.currentWorkspace.getProjects())
                    {
                        if (worksSpaceProject == release.getProject())
                        {
                            project.add(release);
                        }
                    }
                    break;
                case RELEASE_DEL:
                    project.remove(release);
                    break;
                case RELEASE_SHORTNAME:
                    System.out.println("RELEASE_SHORTNAME" + "  "
                            + item.getRedoAction().getValue());
                    release.setShortName((String)item.getRedoAction().getValue());
                    break;
                case RELEASE_RELEASEDATE:
                    System.out.println("RELEASE_RELEASEDATE" + "  "
                            + item.getRedoAction().getValue());
                    release.setEstimatedDate((Date)item.getRedoAction().getValue());
                    break;
                case RELEASE_DESCRIPTION:
                    System.out.println("RELEASE_DESCRIPTION" + "  "
                            + item.getRedoAction().getValue());
                    release.setDescription((String)item.getRedoAction().getValue());
                    break;
                case RELEASE_PROJECT:
                    System.out.println("RELEASE_PROJECT" + " " + item.getRedoAction().getValue());
                    release.setProject((Project)item.getRedoAction().getValue());
                    break;
                case RELEASE_EDIT:
                    for (UndoableItem undoAction : (ArrayList<UndoableItem>)
                            item.getRedoAction().getValue())
                    {
                        UndoRedoPerformer.redo(undoAction);
                    }
                    ReleaseScene.refreshReleaseScene(release);
                    break;
                default:
                    System.out.println("Redo on Release with this property not implemented (yet?)");
                    break;
            }
        }
    }
}