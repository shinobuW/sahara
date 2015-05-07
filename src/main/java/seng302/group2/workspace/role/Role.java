/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.workspace.role;

import javafx.collections.ObservableList;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.skills.Skill;

import java.util.ArrayList;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;


/**
 * @author swi67
 */
public class Role extends TreeViewItem
{
    public enum RoleType
    {
        ScrumMaster, ProductOwner, DevelopmentTeamMember, Others
    }


    private String shortName;
    private String description;
    private RoleType type;
    private transient ObservableList<Skill> requiredSkills = observableArrayList();
    private List<Skill> serializableRequiredSkills = new ArrayList<>();
    private boolean defaultRole;

    /**
     * Basic Role constructor
     */
    public Role()
    {
        // Initialize as a TreeViewItem
        super("unnamed");
        this.shortName = "role Name";
        this.description = "";
        this.type = RoleType.Others;
    }

    /**
     * Role Constructor
     *
     * @param shortName short name to be set
     * @param type      type of role to be set
     */
    public Role(String shortName, RoleType type)
    {
        // Initialize as a TreeViewItem
        super(shortName);
        this.shortName = shortName;
        this.type = type;
    }

    /**
     * Role Constructor
     *
     * @param shortName   short name to be set
     * @param type        type of role to be set
     * @param description brief description of role
     */
    public Role(String shortName, RoleType type, String description)
    {
        // Initialize as a TreeViewItem
        super(shortName);
        this.shortName = shortName;
        this.description = description;
        this.type = type;
    }

    /**
     * Role Constructor
     *
     * @param shortName short name to be set
     * @param type      type of role to be set
     * @param skills    skills required for role
     */
    public Role(String shortName, RoleType type, ObservableList<Skill> skills)
    {
        // Initialize as a TreeViewItem
        super(shortName);
        this.shortName = shortName;
        this.description = "";
        this.requiredSkills = skills;
        this.type = type;
    }

    /**
     * Role Constructor
     *
     * @param shortName   short name to be set
     * @param type        type of role to be set
     * @param description brief description of role
     * @param skills      skills required for role
     */
    public Role(String shortName, RoleType type, String description, ObservableList<Skill> skills)
    {
        // Initialize as a TreeViewItem
        super(shortName);
        this.shortName = shortName;
        this.description = description;
        this.requiredSkills = skills;
        this.type = type;
    }


    // <editor-fold defaultstate="collapsed" desc="Getters">

    /**
     * Gets the Role's short name
     *
     * @return The short name of the role
     */
    public String getShortName()
    {
        return this.shortName;
    }

    /**
     * Gets the Role's description
     *Role
     * @return The description name of the description
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * Gets the type of role
     *
     * @return Type of role
     */
    public RoleType getType()
    {
        return this.type;
    }

    /**
     * Gets the required skills for a role
     *
     * @return list of skills
     */
    public ObservableList<Skill> getRequiredSkills()
    {
        return this.requiredSkills;
    }

    /**
     * Gets the default state of the role
     *
     * @return whether the role is a default role or not
     */
    public boolean isDefault()
    {
        return this.defaultRole;
    }
    
    
    /**
     * Searches all roles in the workspace to find the role for the given type
     * @param type The type of role to return
     * @return The role in the workspace of the given type
     */
    public static Role getRoleType(RoleType type)
    {
        Role role = null;
        for (Role wsrole : Global.currentWorkspace.getRoles())
        {
            if (wsrole.getType().equals(type))
            {
                role = wsrole;
                break;
            }
        }
        return role;
    }
    

    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Setters">

    /**
     * Sets the role's short name
     *
     * @param shortName the short name to set
     */
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

    /**
     * Sets the role's short name
     *
     * @param description the short name to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Sets the type of role
     *
     * @param type RoleType to set
     */
    public void setType(RoleType type)
    {
        this.type = type;
    }

    /**
     * Set the default state of the role
     *
     * @param isDefault Boolean type of default to set
     */
    public void setDefault(boolean isDefault)
    {
        this.defaultRole = isDefault;
    }

    //</editor-fold>  

    /**
     * Prepares a role to be serialized.
     */
    public void prepSerialization()
    {
        serializableRequiredSkills.clear();
        for (Object item : requiredSkills)
        {
            this.serializableRequiredSkills.add((Skill) item);
        }
    }


    /**
     * Deserialization post-processing.
     */
    public void postSerialization()
    {
        requiredSkills.clear();
        for (Object item : serializableRequiredSkills)
        {
            this.requiredSkills.add((Skill) item);
        }
    }

    /**
     * Gets the children of the TreeViewItem
     *
     * @return The items of the TreeViewItem
     */
    @Override
    public ObservableList<TreeViewItem> getChildren()
    {
        return null;
    }

    @Override
    public String toString()
    {
        if (this == null)
        {
            return "null";
        }
        return this.shortName;
    }
}
