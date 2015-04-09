/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.undoredo;

import java.util.Date;
import seng302.group2.Global;
import seng302.group2.project.Project;
import seng302.group2.project.skills.Skill;
import seng302.group2.project.team.person.Person;

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

        PERSON,
        PERSON_SHORTNAME,
        PERSON_FIRSTNAME,
        PERSON_LASTNAME,
        PERSON_EMAIL,
        PERSON_DESCRIPTION,
        PERSON_BIRTHDATE,
        
        SKILL,
        SKILL_SHORTNAME,
        SKILL_DESCRIPTION,
    }

    
    /**
     * Performs the undo on the underlying undoable item.
     * @param item The UndoableItem to undo
     */
    public static void undo(UndoableItem item)
    {
        Class objClass = item.getHost().getClass();
        
        /* Project actions */
        if (objClass == Project.class)
        {
            Project proj = (Project) item.getHost();
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
                default:
                    System.out.println("Undo on project with this property not implemented (yet?)");
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
                    Global.currentProject.getPeople().remove((Person) item.getHost());
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
                default:
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
                case SKILL:
                    Global.currentProject.getSkills().remove((Skill) item.getHost());
                    break;
                case SKILL_SHORTNAME:
                    skill.setShortName((String) item.getUndoAction().getValue());
                    break;
                case SKILL_DESCRIPTION:
                    skill.setDescription((String) item.getUndoAction().getValue());
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
        
        /* Project actions */
        if (objClass == Project.class)
        {
            Project proj = (Project) item.getHost();
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
                default:
                    System.out.println("Redo on project with this property not implemented (yet?)");
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
                    Global.currentProject.getPeople().add((Person) item.getHost());
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
                case SKILL:
                    Global.currentProject.getSkills().add((Skill) item.getHost());
                    break;
                case SKILL_SHORTNAME:
                    skill.setShortName((String) item.getRedoAction().getValue());
                    break;
                case SKILL_DESCRIPTION:
                    skill.setDescription((String) item.getRedoAction().getValue());
                    break;
                default:
                    System.out.println("Redo on skill with this property not implemented (yet?)");
                    break;                   
            }
        }
    }
}