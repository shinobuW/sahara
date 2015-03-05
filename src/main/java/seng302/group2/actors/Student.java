/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.actors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * A crude representation of a student with basic members
 * @author Jordane Lew (jml168)
 */
public class Student implements Serializable
{
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String address;
    private List<String> courses;
    
    public Student(String fname, String lname, Date dob, String addr, List<String> courses)
    {
	this.firstName = fname;
	this.lastName = lname;
	this.birthDate = dob;
	this.address = addr;
	this.courses = courses;
    }
}
