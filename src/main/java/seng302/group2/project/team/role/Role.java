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
    private RoleType type;
    
    
    public Role(String shortName)
    {
        this.shortName = shortName;
    }
    
    public Role(String shortName, RoleType type)
    {
        this.shortName = shortName;
        this.type = type;
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
    
    /**
     * Gets the type of role
     * @return Type of role
     */
    public RoleType getType()
    {
        return this.type;
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
    * Sets the type of role
    * @param type RoleType to set
    */
    public void setType(RoleType type)
    {
        this.type = type;
    }
    
    //</editor-fold>  
}