package seng302.group2.actors;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class StudentTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public StudentTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( StudentTest.class );
    }

    /**
     * Tests that a student's full name is returned correctly by getFullName()
     */
    public void testGetFullName()
    {
        Student student = new Student("Foo", "Bar");
        assertEquals("Foo Bar", student.getFullName());
    }
}
