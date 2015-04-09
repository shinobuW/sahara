/*
 * SENG302 Group 2
 */
package seng302.group2.project.team.person;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.collections.ObservableList;
import seng302.group2.scenes.listdisplay.TreeViewItem;

/**
 * A basic class to represent a Person in the real world.
 * @author crw73
 */
public class Person extends TreeViewItem implements Serializable
{
    public static String birthDatePattern = "dd/MM/yyyy";
    
    private String shortName;
    private String firstName;
    private String lastName;
    private String email;
    private String description;
    private Date birthDate = new Date();
    
    
    /**
     * Basic Person constructor
     */
    public Person()
    {
        super("unnamed");
        this.shortName = "unnamed";
        this.firstName = "firstName";
        this.lastName = "lastName";
        this.email = "";
        this.description = "";
        try
        {
            this.birthDate = new SimpleDateFormat(birthDatePattern).parse("29/05/1985");
        }
        catch (ParseException e)
        {
            System.out.println("Error parsing date");
        }
    }
    
    
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
        
        this.shortName = shortName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.description = description;
        this.birthDate = birthDate;
    }
       
    // <editor-fold defaultstate="collapsed" desc="Getters"> 
    /**
     * Gets the person's short name
     * @return The short name of the person
     */
    public String getShortName()
    {
        return this.shortName;
    }
    
    /**
     * Gets the person's first name
     * @return The first name of the person
     */
    public String getFirstName()
    {
        return this.firstName;
    }
    
    /**
     * Gets the person's last name
     * @return The last name of the person
     */
    public String getLastName()
    {
        return this.lastName;
    }
    
    /**
     * Gets the person's email
     * @return The email of the person
     */
    public String getEmail()
    {
        return this.email;
    }
    
    /**
     * Gets the person's description
     * @return The description of the person
     */
    public String getDescription()
    {
        return this.description;
    }
    
    /**
     * Gets the person's birth date
     * @return The birth date of the person
     */
    public Date getBirthDate()
    {
        return this.birthDate;
    }
    
    //</editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="Setters">
    /**
     * Sets the person's short name
     * @param shortName the short name to set
     */
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }
    
    /**
     * Sets the person's first name
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    
    /**
     * Gets the person's last name
     * @param lastName the last name to set
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    
    /**
     * Gets the person's email
     * @param email the email to set
     */
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    /**
     * Gets the person's description
     * @param description the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /**
     * Gets the person's birth date
     * @param birthDate the birth date to set
     */
    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }
    
        //</editor-fold>
    
    
    /**
     * Gets the children of the TreeViewItem
     * @return The items of the TreeViewItem
     */
    @Override
    public ObservableList<TreeViewItem> getChildren()
    {
        return null;
    }
    
    
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
