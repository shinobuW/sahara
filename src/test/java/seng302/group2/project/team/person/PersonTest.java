/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.project.team.person;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * A series of tests relating to Persons
 * @author Jordane Lew (jml168)
 */
public class PersonTest extends TestCase
{
    /**
     * Create the test case
     * @param testName name of the test case
     */
    public PersonTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PersonTest.class );
    }

    /**
     * A simple test for the Project constructors
     */
    public void testPersonConstructors()
    {
        Person pers = new Person();
        // TODO implement Person constructor test(s)
    }
}
