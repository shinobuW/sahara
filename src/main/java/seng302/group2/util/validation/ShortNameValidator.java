/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.validation;

import seng302.group2.Global;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;
import seng302.group2.workspace.person.Person;
import seng302.group2.scenes.control.RequiredField;


/**
 *
 * @author Jordane
 */
public class ShortNameValidator
{
        /**
     * Checks whether a given short name is valid (unique and not null/empty)
     * @param shortNameField is a short name field
     * @return If the short name is valid
     */
    public static boolean validateShortName(RequiredField shortNameField)
    {
        switch (ShortNameValidator.validateShortName(shortNameField.getText()))
        {
            case VALID:
                //shortNameError.setText(null);
                //shortNameField.setStyle(null); 
                shortNameField.hideErrorField();
                return true;
            case NON_UNIQUE:
                shortNameField.showErrorField("* Short name has already been taken");
                return false;
            case INVALID:
                shortNameField.showErrorField("* Not a valid short name");
                return false;
            case OUT_OF_RANGE:
                shortNameField.showErrorField("* Short names must be less than 20 characters long");
                return false;
            default:
                shortNameField.showErrorField("* Not a valid short name");
                return false;
        }
    }
    
    /**
     * Checks whether a person's short name is valid (unique and not null/empty).
     * @param shortName The short name to validate
     * @return Validation status representing if the short name is valid
     */
    public static ValidationStatus validateShortName(String shortName)
    {
        if (NameValidator.validateName(shortName) == ValidationStatus.INVALID)
        {
            return ValidationStatus.INVALID;
        }
        if (shortName.length() > 20)
        {
            return ValidationStatus.OUT_OF_RANGE;
        }
        
        for (Object object : Global.currentWorkspace.getPeople())
        {
            try
            {
                Person person = (Person) object;
                if (person.getShortName().equals(shortName))
                {
                    return ValidationStatus.NON_UNIQUE;
                }
            }
            catch (Exception ex)
            {
                continue;
            }
        }
        
        for (Object object : Global.currentWorkspace.getSkills())
        {
            try
            {
                Skill skill = (Skill) object;
                if (skill.getShortName().equals(shortName))
                {
                    return ValidationStatus.NON_UNIQUE;
                }
            }
            catch (Exception ex)    
            {
                continue;
            }    
        }
        
        for (Object object : Global.currentWorkspace.getTeams())
        {
            try
            {
                Team team = (Team) object;
                if (team.getShortName().equals(shortName))
                {
                    return ValidationStatus.NON_UNIQUE;
                }
            }
            catch (Exception ex)    
            {
                continue;
            }    
        }        
        return ValidationStatus.VALID;
    }
}
