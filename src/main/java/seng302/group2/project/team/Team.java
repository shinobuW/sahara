/*
 * SENG302 Group 2
 */
package seng302.group2.project.team;

import java.io.Serializable;
import java.util.ArrayList;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import seng302.group2.Global;
import seng302.group2.project.team.person.Person;
import seng302.group2.project.team.role.Role;
import seng302.group2.project.team.role.RoleType;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.util.undoredo.UndoRedoAction;
import seng302.group2.util.undoredo.UndoRedoPerformer;
import seng302.group2.util.undoredo.UndoableItem;

/**
 * A basic class to represent a Team in the real world.
 * @author crw73
 */
public class Team extends TreeViewItem implements Serializable
{
    public static String birthDatePattern = "dd/MM/yyyy";
    
    private String shortName;
    private String description;
    private Person scrumMaster;
    private Person productOwner;
    private transient ObservableList<Person> people = observableArrayList();
    private ArrayList<Person> serializablePeople = new ArrayList<>();
    
    
    /**
     * Basic Team constructor
     */
    public Team()
    {
        super("unnamed");
        this.shortName = "unnamed";
        this.description = "";
    }
    
    
    /**
     * Basic team constructor with all fields
     * @param shortName A unique short name to identify a Team
     * @param description of a Team
     */
    public Team(String shortName, String description)
    {
        // Initialize as a TreeViewItem
        super(shortName);
        
        this.shortName = shortName;
        this.description = description;
    }
       
    // <editor-fold defaultstate="collapsed" desc="Getters"> 
    /**
     * Gets the Team's short name
     * @return The short name of the team
     */
    public String getShortName()
    {
        return this.shortName;
    }
    
    /**
     * Gets the team's description
     * @return The description of the team
     */
    public String getDescription()
    {
        return this.description;
    }
    
    /**
     * Gets the team's list of members
     * @return The ObservableList of Persons
     */
    public ObservableList<Person> getPeople()
    {
        this.serializablePeople.clear();
        for (Object item : this.people)
        {
            this.serializablePeople.add((Person)item);
        }
        return this.people;
    }
    
    /**Get the team's Product Owner
     * @return The Product Owner
     */
    public Person getProductOwner()
    {
        return this.productOwner;
    }
    
    /**Get the team's Scrum Master
    * @return The Scrum Master
    */
    public Person getScrumMaster()
    {
        return this.scrumMaster;
    }
    
    
    //</editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="Setters">
    /**
     * Sets the team's short name
     * @param shortName the short name to set
     */
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }
    
    /**
     * Gets the team's description
     * @param description the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /**
    * Gets the team's Scrum Master
    * @param person the person to set
    */
    public void setScrumMaster(Person person)
    {
        this.scrumMaster = person;
        person.setRole(new Role("Scrum Master", RoleType.ScrumMaster));
    }
    
    /**
    * Gets the team's Product Owner
    * @param person the person to set
    */
    public void setProductOwner(Person person)
    {
        this.productOwner = person;
        person.setRole(new Role("Product Owner", RoleType.ProductOwner));
    }
    
        //</editor-fold>
    
    /**
     * Adds a Person to the Teams list of Members
     * @param person The person to add
     */
    public void addPerson(Person person)
    {
        // Add the undo action to the stack
        Global.undoRedoMan.add(new UndoableItem(
                person,
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_ADD_TEAM, this), 
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_ADD_TEAM, this)
                ));
        
        this.people.add(person);
        person.setTeam(this);
    }
    
    /**
     * Removes a Person to the Project's list of Members
     * @param person The person to remove
     */
    public void removePerson(Person person)
    {
        // Add the undo action to the stack
        Global.undoRedoMan.add(new UndoableItem(
                person,
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_DEL_TEAM, this), 
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_DEL_TEAM, this)
                ));
        
        this.people.remove(person);
        person.setTeam(null);
    }
    
    /**
     * Prepares a Team to be serialized.
     */
    public void prepSerialization()
    {
        serializablePeople.clear();
        for (Object item : people)
        {
            this.serializablePeople.add((Person) item);
        }
    }


    /**
     * Deserialization post-processing.
     */
    public void postSerialization()
    {
        people.clear();
        for (Object item : serializablePeople)
        {
            this.people.add((Person) item);
        }
    }
    
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
     * An overridden version for the String representation of a Team
     * @return The short name of the Team
     */
    @Override
    public String toString()
    {
        return this.shortName;
    }
}
