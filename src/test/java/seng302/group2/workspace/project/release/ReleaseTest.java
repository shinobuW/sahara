/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.workspace.project.release;


import org.junit.Assert;
import org.junit.Test;
import seng302.group2.Global;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.project.Project;

import java.time.LocalDate;

/**
 * @author Shinobu
 */
public class ReleaseTest {
    /**
     * Test constructors
     */
    @Test
    public void testReleaseConstructor() {
        Release release1 = new Release();
        Assert.assertEquals("Untitled Release", release1.getShortName());
        Assert.assertEquals("Release without project assigned should not exist",
                release1.getDescription());
        Assert.assertEquals(LocalDate.now(), release1.getEstimatedDate());
        Assert.assertEquals("Untitled Project", release1.getProject().getShortName());

        Project testProject = new Project();
        Release release2 = new Release("Test2", testProject);
        Assert.assertEquals("Test2", release2.getShortName());
        Assert.assertEquals("Untitled Project", release2.getProject().getShortName());

        Release release3 = new Release("Test", "description", LocalDate.parse("12/12/2020",
                Global.dateFormatter), testProject);
        Assert.assertEquals("Test", release3.getShortName());
        Assert.assertEquals("description", release3.getDescription());
        Assert.assertEquals(LocalDate.parse("12/12/2020", Global.dateFormatter),
                release3.getEstimatedDate());
        Assert.assertEquals("Untitled Project", release3.getProject().getShortName());
    }

    @Test
    public void testReleaseSetters() {
        Release testRelease = new Release();
        Project testProject = new Project("Test Project", "Long name", "Description");
        testRelease.setShortName("Release 2.0");
        testRelease.setDescription("Second Release");
        testRelease.setEstimatedDate(LocalDate.parse("26/03/2016", Global.dateFormatter));
        testProject.add(testRelease);

        Assert.assertEquals("Release 2.0", testRelease.getShortName());
        Assert.assertEquals("Second Release", testRelease.getDescription());
        Assert.assertEquals(LocalDate.parse("26/03/2016", Global.dateFormatter),
                testRelease.getEstimatedDate());
        Assert.assertEquals("Test Project", testRelease.getProject().getShortName());
        Assert.assertTrue(testProject.getReleases().contains(testRelease));
    }

    /**
     * Tests the compareTo method of Release to ensure it correctly returns an int representing if a
     * shortName is larger or not.
     */
    @Test
    public void testCompareTo() {
        Release release1 = new Release();
        Release release2 = new Release();
        release1.setShortName("A");
        release2.setShortName("Z");

        Assert.assertTrue(release1.compareTo(release2) <= 0);
        Assert.assertTrue(release2.compareTo(release1) >= 0);
        Assert.assertTrue(release1.compareTo(release1) == 0);
    }


    @Test
    public void testEdit() {
        Release release = new Release();
        Project testProject = new Project();
        release.setProject(testProject);

        Project testProject2 = new Project();
        release.edit("newShortName", "newDescription", LocalDate.now(), testProject2);

        Assert.assertEquals("newShortName", release.getShortName());
        Assert.assertEquals("newDescription", release.getDescription());
        Assert.assertEquals(LocalDate.now(), release.getEstimatedDate());
        Assert.assertEquals(testProject2, release.getProject());

        Global.commandManager.undo();

        Assert.assertEquals("Untitled Release", release.getShortName());
        Assert.assertEquals("Release without project assigned should not exist",
                release.getDescription());
        Assert.assertEquals(LocalDate.now(), release.getEstimatedDate());
        Assert.assertEquals("Untitled Project", release.getProject().getShortName());
    }

    @Test
    public void testDeleteRelease() {
        Release release = new Release();
        Project project = new Project();
        Workspace ws = new Workspace();
        ws.add(project);
        project.add(release);

        release.deleteRelease();

        Assert.assertFalse(project.getReleases().contains(release));
        Global.commandManager.undo();
        Assert.assertTrue(project.getReleases().contains(release));
    }
}