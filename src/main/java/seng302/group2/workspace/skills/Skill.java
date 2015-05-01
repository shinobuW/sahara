package seng302.group2.workspace.skills;

import javafx.collections.ObservableList;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.util.undoredo.UndoRedoAction;
import seng302.group2.util.undoredo.UndoRedoPerformer;
import seng302.group2.util.undoredo.UndoableItem;
import seng302.group2.workspace.person.Person;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A basic class to represent skills a person may have
 * @author crw73
 */
public class Skill extends TreeViewItem implements Serializable
{
    
    private String shortName;
    private String description;
    
    /**
    * Basic Skill constructor
    */
    public Skill()
    {
        super("unnamed");
        this.shortName = "unnamed";
        this.description = "no description";
    }
    
     /**
     * Basic Skill constructor with all fields
     * @param shortName A unique short name to identify a Skill
     * @param description The Description of a skill
     */
    public Skill(String shortName, String description)
    {
        // Initialize as a TreeViewItem
        super(shortName);
        
        this.shortName = shortName;
        this.description = description;
    }
        
    // <editor-fold defaultstate="collapsed" desc="Getters"> 
    /**
     * Gets a skills short name
     * @return The short name of the skill
     */
    public String getShortName()
    {
        return this.shortName;
    }
    
    /**
     * Gets a skills description
     * @return The description of the skill
     */
    public String getDescription()
    {
        return this.description;
    }
  
    //</editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="Setters">
     /**
     * Sets a skills short name
     * @param shortName the short name to set
     */
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }
  
    /**
     * Gets a skills description
     * @param description the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
  
    //</editor-fold>
        
    /**
     * Gets the children of the TreeViewItem
     * @return The items of the TreeViewItem
     */
    @Override
    public ObservableList<TreeViewItem> getChildren()
    {
        return null;
    }
    
    
    /**
     * Deletes a skill and removes it from any people who have the skill.
     * Cannot delete Product Owner or Scrum Master skills.
     * @param deletedSkill The skill to delete
     */
    public static void deleteSkill(Skill deletedSkill)
    {
        ArrayList<UndoableItem> undoActions = new ArrayList<>();
        if (!deletedSkill.getShortName().equals("Product Owner")
                && !deletedSkill.getShortName().equals("Scrum Master"))
        {
            for (Person personRemoveSkill : Global.currentWorkspace.getPeople())
            {
                if (personRemoveSkill.getSkills().contains(deletedSkill))
                {
                    undoActions.add(new UndoableItem(
                            deletedSkill,
                            new UndoRedoAction(
                                    UndoRedoPerformer.UndoRedoProperty.SKILL_DEL_PERSON,
                                    personRemoveSkill),
                            new UndoRedoAction(
                                    UndoRedoPerformer.UndoRedoProperty.SKILL_DEL_PERSON,
                                    personRemoveSkill)));
                    personRemoveSkill.removeSkill(deletedSkill, false);
                }
            }

            undoActions.add(new UndoableItem(
                    deletedSkill,
                    new UndoRedoAction(
                            UndoRedoPerformer.UndoRedoProperty.SKILL_DEL,
                            deletedSkill),
                    new UndoRedoAction(
                            UndoRedoPerformer.UndoRedoProperty.SKILL_DEL,
                            deletedSkill)));
            Global.currentWorkspace.remove(deletedSkill, false);


            if (undoActions.size() > 0)
            {
                Global.undoRedoMan.add(new UndoableItem(
                        deletedSkill,
                        new UndoRedoAction(
                                UndoRedoPerformer.UndoRedoProperty.SKILL_DEL_RECURSIVE,
                                undoActions),
                        new UndoRedoAction(
                                UndoRedoPerformer.UndoRedoProperty.SKILL_DEL_RECURSIVE,
                                undoActions)
                ));
            }
        }
    }    
    
    /**
     * An overridden version for the String representation of a Skill
     * @return The short name of the Skill
     */
    @Override
    public String toString()
    {
        return this.shortName;
    }
    
}
