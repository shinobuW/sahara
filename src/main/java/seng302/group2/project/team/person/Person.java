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
     * @param fullName
     * @param description 
     */
    public Person(String shortName, String firstName, String description)
    {
        this.shortName = shortName;
        //this.longName = fullName;
        this.description = description;
    }
    
    
}
