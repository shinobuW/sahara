/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.undoredo;

import java.util.ArrayList;
import java.util.Date;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;
import seng302.group2.workspace.person.Person;
import static seng302.group2.scenes.MainScene.informationGrid;
import seng302.group2.scenes.information.PersonScene;
import seng302.group2.scenes.information.TeamScene;
import seng302.group2.scenes.information.SkillScene;
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
        PROJECT,
        PROJECT_SHORTNAME,
        PROJECT_LONGNAME,
        PROJECT_DESCRIPTION,
        PROJECT_EDIT,

        PERSON,
        PERSON_SHORTNAME,
        PERSON_FIRSTNAME,
        PERSON_LASTNAME,
        PERSON_EMAIL,
        PERSON_DESCRIPTION,
        PERSON_BIRTHDATE,
        PERSON_ADD_TEAM,
        PERSON_DEL_TEAM,
        PERSON_EDIT,
        
        SKILL,
        SKILL_SHORTNAME,
        SKILL_DESCRIPTION,
        SKILL_ADD_PERSON,
        SKILL_DEL_PERSON,
        SKILL_EDIT,
        
        TEAM,
        TEAM_SHORTNAME,
        TEAM_DESCRIPTION,
        TEAM_EDIT,
    }

    
    /**
     * Performs the undo on the underlying undoable item.
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
                case PROJECT_SHORTNAME:
                    proj.setShortName((String) item.getUndoAction().getValue());
                    break;
                case PROJECT_LONGNAME:
                    proj.setLongName((String) item.getUndoAction().getValue());
                    break;
                case PROJECT_DESCRIPTION:
                    proj.setDescription((String) item.getUndoAction().getValue());
                    break;
                case PROJECT_EDIT:
                    for (UndoableItem undoAction : (ArrayList<UndoableItem>)
                            item.getUndoAction().getValue()) 
                    {
                        UndoRedoPerformer.undo(undoAction);
                    }
                    //WorkspaceScene.refreshProjectScene(proj);
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
            switch (item.getUndoAction().getProperty())
            {
                case PERSON_SHORTNAME:
                    person.setShortName((String) item.getUndoAction().getValue());
                    break;
                case PERSON:
                    Global.currentWorkspace.getPeople().remove((Person) item.getHost());
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
                case PERSON_ADD_TEAM:
                    Team currentTeam = (Team) item.getUndoAction().getValue();
                    currentTeam.getPeople().remove(person);
                    App.content.getChildren().remove(informationGrid);
                    TeamScene.getTeamScene(currentTeam);
                    App.content.getChildren().add(informationGrid);
                    break;
                case PERSON_DEL_TEAM:
                    currentTeam = (Team) item.getUndoAction().getValue();
                    currentTeam.getPeople().add(person);
                    App.content.getChildren().remove(informationGrid);
                    TeamScene.getTeamScene(currentTeam);
                    App.content.getChildren().add(informationGrid);
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
                    System.out.println("Undo on person with this property not implemented (yet?)");
                    break;
            }
        }
        
        /* Skill Actions */
        else if (objClass == Skill.class)
        {
            Skill skill = (Skill) item.getHost();
            Person currentPerson = (Person) item.getUndoAction().getValue();
            switch (item.getUndoAction().getProperty())
            {
                case SKILL:
                    Global.currentWorkspace.getSkills().remove((Skill) item.getHost());
                    break;
                case SKILL_SHORTNAME:
                    skill.setShortName((String) item.getUndoAction().getValue());
                    break;
                case SKILL_DESCRIPTION:
                    skill.setDescription((String) item.getUndoAction().getValue());
                    break;
                case SKILL_ADD_PERSON:
                    currentPerson.getSkills().remove(skill);
                    App.content.getChildren().remove(informationGrid);
                    PersonScene.getPersonScene(currentPerson);
                    App.content.getChildren().add(informationGrid);
                    break;
                case SKILL_DEL_PERSON:
                    currentPerson.getSkills().add(skill);
                    App.content.getChildren().remove(informationGrid);
                    PersonScene.getPersonScene(currentPerson);
                    App.content.getChildren().add(informationGrid);
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
                case TEAM:
                    Global.currentWorkspace.getTeams().remove((Team) item.getHost());
                    break;
                case TEAM_SHORTNAME:
                    team.setShortName((String) item.getUndoAction().getValue());
                    break;
                case TEAM_DESCRIPTION:
                    team.setDescription((String) item.getUndoAction().getValue());
                    break;
                case TEAM_EDIT:
                    for (UndoableItem undoAction : (ArrayList<UndoableItem>)
                            item.getUndoAction().getValue()) 
                    {
                        UndoRedoPerformer.undo(undoAction);
                    }
                    //TeamScene.refreshTeamScene(team);
                    break;
                default:
                    System.out.println("Undo on skill with this property not implemented (yet?)");
                    break;                    
            }
        }
    }

    
     /**
     * Performs the redo on the underlying undoable item.
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
                    //WorkspaceScene.refreshProjectScene(proj);
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
                case PERSON:
                    Global.currentWorkspace.getPeople().add((Person) item.getHost());
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
                case PERSON_ADD_TEAM:
                    Team currentTeam = (Team) item.getUndoAction().getValue();
                    currentTeam.getPeople().add(person);
                    App.content.getChildren().remove(informationGrid);
                    TeamScene.getTeamScene(currentTeam);
                    App.content.getChildren().add(informationGrid);
                    break;
                case PERSON_DEL_TEAM:
                    currentTeam = (Team) item.getUndoAction().getValue();
                    currentTeam.getPeople().remove(person);
                    App.content.getChildren().remove(informationGrid);
                    TeamScene.getTeamScene(currentTeam);
                    App.content.getChildren().add(informationGrid);
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
            Person currentPerson = (Person) item.getUndoAction().getValue(); //Why is this here?
            switch (item.getRedoAction().getProperty())
            {
                case SKILL:
                    Global.currentWorkspace.getSkills().add((Skill) item.getHost());
                    break;
                case SKILL_SHORTNAME:
                    skill.setShortName((String) item.getRedoAction().getValue());
                    break;
                case SKILL_DESCRIPTION:
                    skill.setDescription((String) item.getRedoAction().getValue());
                    break;
                case SKILL_ADD_PERSON:
                    currentPerson.getSkills().add(skill);
                    App.content.getChildren().remove(informationGrid);
                    PersonScene.getPersonScene(currentPerson);
                    App.content.getChildren().add(informationGrid);
                    break;
                case SKILL_DEL_PERSON:
                    currentPerson.getSkills().remove(skill);
                    App.content.getChildren().remove(informationGrid);
                    PersonScene.getPersonScene(currentPerson);
                    App.content.getChildren().add(informationGrid);
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
                case TEAM:
                    Global.currentWorkspace.getTeams().add((Team) item.getHost());
                    break;
                case TEAM_SHORTNAME:
                    team.setShortName((String) item.getRedoAction().getValue());
                    break;
                case TEAM_DESCRIPTION:
                    team.setDescription((String) item.getRedoAction().getValue());
                    break;
                case TEAM_EDIT:
                    for (UndoableItem undoAction : (ArrayList<UndoableItem>)
                            item.getUndoAction().getValue()) 
                    {
                        UndoRedoPerformer.redo(undoAction);
                    }
                    //TeamScene.refreshTeamScene(team);    
                    break;
                default:
                    System.out.println("Redo on skill with this property not implemented (yet?)");
                    break;                   
            }
        }
    }
}