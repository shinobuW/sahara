/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.project.team.role;

/**
 *
 * @author swi67
 */
public class Role
{
    private String shortName;
    private String description;
    private Boolean isScrumMaster;
    private Boolean isProductOwner;
    
    
    public Role(String shortName)
    {
        this.shortName = shortName;
    }
    
    public Role(String shortName, Boolean scrumMaster, Boolean productOwner)
    {
        this.shortName = shortName;
        this.isScrumMaster = scrumMaster;
        this.isProductOwner = productOwner;
    }
    // <editor-fold defaultstate="collapsed" desc="Getters">
     /**
     * Gets the Role's short name
     * @return The short name of the role
     */
    public String getShortName()
    {
        return this.shortName;
    }
    
     /**
     * Gets the Role's description
     * @return The description name of the description
     */
    public String getDescription()
    {
        return this.description;
    }
    
    public Boolean getIsScrumMaster()
    {
        return this.isScrumMaster;
    }
    
    public Boolean getIsProductOwner()
    {
        return this.isProductOwner;
    }
    
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Setters">
    /**
    * Sets the role's short name
    * @param shortName the short name to set
    */
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }
    
    /**
    * Sets the role's short name
    * @param description the short name to set
    */
    public void setDesciption(String description)
    {
        this.description = description;
    }
    
    /**
    * Sets/un-sets the role as a Scrum Master
    * @param scrumMaster boolean to set
    */
    public void setIsScrumMaster(Boolean scrumMaster)
    {
        this.isScrumMaster = scrumMaster;
    }
    
    /**
    * Sets/un-sets the role as a Product Owner
    * @param productOwner boolean to set
    */
    public void setIsProductOwner(Boolean productOwner)
    {
        this.isProductOwner = productOwner;
    }
    
    //</editor-fold>  
}
