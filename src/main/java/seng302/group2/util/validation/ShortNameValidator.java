/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.validation;

import seng302.group2.Global;
import seng302.group2.project.team.person.Person;


/**
 *
 * @author Jordane
 */
public class ShortNameValidator
{
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
        
        for (Object object : Global.currentProject.getPeople())
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
        return ValidationStatus.VALID;
    }
}
