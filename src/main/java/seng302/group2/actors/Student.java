/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.actors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * A crude representation of a student with basic properties and functions
 * @author Jordane Lew (jml168)
 */
public class Student implements Serializable
{
    // <editor-fold defaultstate="collapsed" desc="Private Members">
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String address;
    private ArrayList<String> courses;
    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Blank constructor for a student
     */
    public Student()
    {
        this.firstName = null;
	this.lastName = null;
        this.birthDate = null;
	this.address = null;
	this.courses = new ArrayList();
    }
    
    
    /**
     * Basic constructor for a student
     * @param fname
     * @param lname 
     */
    public Student(String fname, String lname)
    {
        this.firstName = fname;
	this.lastName = lname;
        this.birthDate = null;
	this.address = null;
	this.courses = new ArrayList();
    }
    
    
    /**
     * Extensive constructor for a student
     * @param fname
     * @param lname
     * @param dob
     * @param addr
     * @param courses 
     */
    public Student(String fname, String lname, Date dob, String addr, ArrayList<String> courses)
    {
	this.firstName = fname;
	this.lastName = lname;
	this.birthDate = dob;
	this.address = addr;
	this.courses = courses;
    }
    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="Simple Getters">
    /**
     * Gets the first name of the student
     * @return The first name of the student
     */
    public String getFirstName()
    {
        return this.firstName;
    }
    
    
    /**
     * Gets the last name of the student
     * @return The last name of the student
     */
    public String getLastName()
    {
        return this.lastName;
    }
    
    
    /**
     * Gets the birth date of the student
     * @return The birth date of the student
     */
    public Date getBirthDate()
    {
        return this.birthDate;
    }
    
    
    /**
     * Gets the address of the student
     * @return The address of the student
     */
    public String getAddress()
    {
        return this.address;
    }
    
    
    /**
     * Gets the courses of the student
     * @return The courses of the student
     */
    public ArrayList<String> getCourses()
    {
        return this.courses;
    }
    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="Simple Setters">
    /**
     * Sets the first name of a student
     * @param newFirstName 
     */
    public void setFirstName(String newFirstName)
    {
        this.firstName = newFirstName;
    }
    
    
    /**
     * Sets the last name of a student
     * @param newLastName 
     */
    public void setLastName(String newLastName)
    {
        this.lastName = newLastName;
    }
    
    
    /**
     * Sets the birth date of a student
     * @param newBirthDate 
     */
    public void setBirthDate(Date newBirthDate)
    {
        this.birthDate = newBirthDate;
    }
    
    
    /**
     * Sets the address of a student
     * @param newAddress 
     */
    public void setAddress(String newAddress)
    {
        this.address = newAddress;
    }
    
    
    /**
     * Sets the courses of a student
     * @param newCourses 
     */
    public void setCourses(ArrayList<String> newCourses)
    {
        this.courses = newCourses;
    }
    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="Functions">
    /**
     * Gets the full name of a student
     * @return The student's full name
     */
    public String getFullName()
    {
        return firstName + " " + lastName;
    }
    // </editor-fold>
}
