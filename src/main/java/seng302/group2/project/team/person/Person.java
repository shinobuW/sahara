/*
 * SENG302 Group 2
 */
package seng302.group2.project.team.person;

import java.util.Date;

/**
 *
 * @author cvs20
 */
public class Person
{
    
    private String shortName;
    private String firstName;
    private String lastName;
    private String email;
    private String description;
    private Date birthDate = new Date();
    
    /**
     * Basic project constructor
     * @param shortName
     * @param firstName
     * @param lastName
     * @param email
     * @param birthDate
     * @param description 
     */
    public Person(String shortName, String firstName, String lastName, String email, 
            String description, Date birthDate)
    {
        this.shortName = shortName;
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
     * Creates and returns a new person
     * @return A new person
     */
    public static Person newPerson()
    {
        return new Person("Untitled", "John", "Doe", "null", "", new Date(01, 01, 1990));
    }
    
    @Override
    public String toString()
    {
        return this.shortName;
    }
    
}
