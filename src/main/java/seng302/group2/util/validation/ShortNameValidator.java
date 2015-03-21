/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.validation;

import seng302.group2.Global;
import seng302.group2.project.team.person.Person;
import static seng302.group2.util.validation.ValidationStatus.INVALID;
import static seng302.group2.util.validation.ValidationStatus.NON_UNIQUE;
import static seng302.group2.util.validation.ValidationStatus.VALID;

/**
 *
 * @author Jordane
 */
public class ShortNameValidator
{
    /**
     * Checks whether a person's short name is valid (unique and not null/empty).
     * @param shortName
     * @return 
     */
    public static ValidationStatus validateShortName(String shortName)
    {
        if (NameValidator.validateName(shortName) == INVALID)
        {
            return INVALID;
        }
        
        for (Object object : Global.currentProject.getPeople())
        {
            try
            {
                Person person = (Person) object;
                if (person.getShortName().equals(shortName))
                {
                    return NON_UNIQUE;
                }
            }
            catch (Exception ex)
            {
                continue;
            }
        }
        return VALID;
    }
}
