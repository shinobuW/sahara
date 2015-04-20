/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.workspace.release;

import java.util.Date;
import javafx.collections.ObservableList;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.project.Project;

/**
 *
 * @author Shinobu
 */
public class Release extends TreeViewItem
{
    private String shortName;
    private String description;
    private Date estimatedReleaseDate;
    private Project project;
    
    /** Basic constructor
     */
    public Release()
    {
        this.shortName = "Untitled Release";
        this.description = "Release without project assigned should not exist";
        this.estimatedReleaseDate = new Date("20/20/2020");
        this.project = new Project();
    }
    
    /** Constructor
     * @param shortName short name to be set
     * @param project project to be set
     */
    public Release(String shortName, Project project)
    {
        this.shortName = shortName;
        this.project = project;
    }
    
    /**Constructor
     * @param shortName short name to be set
     * @param description description to be set
     * @param releaseDate release date to be set
     * @param project project to be set
     */
    public Release(String shortName, String description, Date releaseDate, Project project)
    {
        this.shortName = shortName;
        this.description = description;
        this.estimatedReleaseDate = releaseDate;
        this.project = project;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Getters"> 
    
    /**Gets short name of the release
     * @return short name of the release
     */
    public String getShortName()
    {
        return this.shortName;
    }
    
    /**Gets the description of the release
     * @return the description of the release
     */
    public String getDescription()
    {
        return this.description;
    }
    
    /**
     * Gets the estimated release date for the release
     * @return the estimated release date of the release
     */
    public Date getEstimatedReleaseDate()
    {
        return this.estimatedReleaseDate;
    }
    
    /**
     * Gets the project the release belongs to
     * @return project 
     */
    public Project getProject()
    {
        return this.project;
    }
     //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Setters"> 
    
    /** 
     * Sets the short Name of the release
     * @param shortName short name to set 
     */
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }
    
    /**
     * Sets the Description of the release
     * @param description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /**
     * Sets the estimated Release Date of the release
     * @param releaseDate
     */
    public void setEstimatedReleaseDate(Date releaseDate)
    {
        this.estimatedReleaseDate = releaseDate;
    }
    
    /**
     * Sets the project the release belongs to
     * @param project project to set
     */
    public void setProject(Project project)
    {
        this.project = project;
    }
     //</editor-fold>
    
    @Override
    public ObservableList<TreeViewItem> getChildren()
    {
        return null;
    }
    
    @Override
    public String toString()
    {
        return this.shortName;
    }      
}
