/*
 * SENG302 Group 2
 */
package seng302.group2.project.team.person;

import java.util.Date;
import seng302.group2.App;
import seng302.group2.scenes.listdisplay.TreeViewItem;

/**
 * A basic class to represent a Person in the real world.
 * @author crw73
 */
public class Person extends TreeViewItem
{
    private String shortName;
    private String firstName;
    private String lastName;
    private String email;
    private String description;
    private Date birthDate = new Date();
    
    /**
     * Basic person constructor with all fields
     * @param shortName A unique short name to identify a Person
     * @param firstName The first name of the Person
     * @param lastName The last name of the Person
     * @param email The email of the Person
     * @param birthDate A description of the Person
     * @param description The date of birth of a Person
     */
    public Person(String shortName, String firstName, String lastName, String email, 
            String description, Date birthDate)
    {
        // Initialize as a TreeViewItem
        super(shortName);
        
        // Check for duplicates and change if neccessary to keep short names unique
        String newShortName = shortName;
        int i = 0;
        for (TreeViewItem person : App.currentProject.getPeople())
        {
            if (person.toString().equals(newShortName))
            {
                i++;
                newShortName = shortName + "~" + String.valueOf(i);
            }
        }
        
        // Continue constructing
        this.shortName = newShortName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.description = description;
        this.birthDate = birthDate;
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="Getters">

    
    /**
     * Gets a person's short name
     * @return The short name of the person
     */
    public String getShortName()
    {
        return this.shortName;
    }
    
    /**
     * Gets a person's first name
     * @return The first name of the person
     */
    public String getFirstname()
    {
        return this.firstName;
    }
    
    /**
     * Gets a person's last name
     * @return The last name of the person
     */
    public String getLastName()
    {
        return this.lastName;
    }
    
    /**
     * Gets a person's email
     * @return The email of the person
     */
    public String getEmail()
    {
        return this.email;
    }
    
    /**
     * Gets a person's description
     * @return The description of the person
     */
    public String getDescription()
    {
        return this.description;
    }
    
    /**
     * Gets a person's birth date
     * @return The birth date of the person
     */
    public Date getBirthDate()
    {
        return this.birthDate;
    }
    
    //</editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="Setters">
    
     /**
     * Sets a person's short name
     * @param shortName
     */
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }
    
    /**
     * Sets a person's first name
     * @param firstName
     */
    public void setFirstname(String firstName)
    {
        this.firstName = firstName;
    }
    
    /**
     * Gets a person's last name
     * @param lastName
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    
    /**
     * Gets a person's email
     * @param email
     */
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    /**
     * Gets a person's description
     * @param description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /**
     * Gets a person's birth date
     * @param birthDate
     */
    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }
    
        //</editor-fold>
    
    
    /**
     * An overridden version for the String representation of a Person
     * @return The short name of the Person
     */
    @Override
    public String toString()
    {
        return this.shortName;
    }
}
