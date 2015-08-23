package seng302.group2.workspace.project.story.tasks;

import org.junit.Assert;
import org.junit.Test;

public class LogTest {

    @Test
    public void testGetDurationInHours() throws Exception {
        Log log = new Log();
        log.setDuration("2 hours 30 mins");
        Assert.assertTrue(log.getDurationInHours() == 2.5);
    }

    @Test
    public void testGetDurationInMinutes() throws Exception {
        Log log = new Log();
        log.setDuration("2 hours 30 mins");
        Assert.assertTrue(log.getDurationInMinutes() == 150);
    }


    @Test
    public void testGetDurationString() throws Exception {
        Log log = new Log();
        log.setDuration("2 hours 30 mins");
        Assert.assertEquals("2h 30min", log.getDurationString());
    }


    @Test
    public void testDeleteLog() throws Exception {

    }

    @Test
    public void testGenerateXML() throws Exception {

    }

    @Test
    public void testReadDurationToMinutes() throws Exception {
        String test = "1h30min";
        String test2 = "1h30m";
        String test3 = "30min";
        String test4 = "2h";
        String test6 = "2h5min";
        String test7 = "2hours 5min";
        String test8 = "2 hours 10 mins";
        String test9 = "     2 hour     5 m  ";
        String test10 = "5";
        String test11 = "1.1";
        String test12 = "0";
        String test13 = "1.25 hours";

        Assert.assertEquals((Double) 90.0, Log.readDurationToMinutes(test));
        Assert.assertEquals((Double) 90.0, Log.readDurationToMinutes(test2));
        Assert.assertEquals((Double) 30.0, Log.readDurationToMinutes(test3));
        Assert.assertEquals((Double) 120.0, Log.readDurationToMinutes(test4));
        Assert.assertEquals((Double) 125.0, Log.readDurationToMinutes(test6));
        Assert.assertEquals((Double) 125.0, Log.readDurationToMinutes(test7));
        Assert.assertEquals((Double) 130.0, Log.readDurationToMinutes(test8));
        Assert.assertEquals((Double) 125.0, Log.readDurationToMinutes(test9));
        Assert.assertEquals((Double) 300.0, Log.readDurationToMinutes(test10));
        Assert.assertEquals((Double) 66.0, Log.readDurationToMinutes(test11));
        Assert.assertEquals((Double) 0.0, Log.readDurationToMinutes(test12));
        Assert.assertEquals((Double) 75.0, Log.readDurationToMinutes(test13));
    }
}