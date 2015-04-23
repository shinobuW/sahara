package seng302.group2.workspace.project;

import javafx.collections.ObservableList;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.util.undoredo.UndoRedoAction;
import seng302.group2.util.undoredo.UndoRedoPerformer;
import seng302.group2.util.undoredo.UndoableItem;
import seng302.group2.workspace.release.Release;
import seng302.group2.workspace.team.Team;

import java.io.Serializable;
import java.util.ArrayList;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * A class representing real-world projects
 * Created by Jordane on 18/04/2015.
 */
public class Project extends TreeViewItem implements Serializable
{
    private String shortName;
    private String longName;
    private String description;
    private transient ObservableList<Team> teams = observableArrayList();
    private ArrayList<Team> serializableTeams = new ArrayList<>();
    private transient ObservableList<Release> releases = observableArrayList();
    private ArrayList<Release> serializableReleases = new ArrayList<>();


    /**
     * Basic Workspace constructor.
     */
    public Project()
    {
        super("Untitled Project");
        this.shortName = "Untitled Project";
        this.longName = "Untitled Project";
        this.description = "A blank project.";
    }


    /**
     * Basic project constructor with input.
     * @param shortName A unique short name to identify the Project
     * @param fullName The full Project name
     * @param description A description of the Project
     */
    public Project(String shortName, String fullName, String description)
    {
        super(shortName);
        this.shortName = shortName;
        this.longName = fullName;
        this.description = description;
    }

     // <editor-fold defaultstate="collapsed" desc="Getters">
    /**
     * Gets the short name of the project
     * @return Short name of the project
     */
    public String getShortName()
    {
        return shortName;
    }

    /**
     * Gets the long name of the project
     * @return The long name of the project
     */
    public String getLongName()
    {
        return longName;
    }

    /**
     * Gets the teams of the project
     * @return list of teams
     */
    public ObservableList<Team> getTeams()
    {
        this.serializableTeams.clear();
        for (Object item : this.teams)
        {
            this.serializableTeams.add((Team) item);
        }
        return this.teams;
    }

    /**
     * Gets the releases of the project
     * @return list of releases
     */
    public ObservableList<Release> getReleases()
    {
        this.serializableReleases.clear();
        for (Object item : this.releases)
        {
            this.serializableReleases.add((Release)item);
        }
        return this.releases;
    }
    
    /**
     * Gets the description of the project
     * @return The description of the project
     */
    public String getDescription()
    {
        return description;
    }
    
    //</editor-fold>

    /**
     * Sets the short name of the project
     * @param shortName The short name to set to the project
     */
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Setters"> 
    /**
     * Sets the long name of the project
     * @param longName The long name to set to the project
     */
    public void setLongName(String longName)
    {
        this.longName = longName;
    }

    /**
     * Sets the description of the project
     * @param description The description to set to the project
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    //</editor-fold>

    /**
     * Adds a Team to the Project list of Teams
     * @param team The team to add
     */
    public void addTeam(Team team, Boolean undo)
    {
        // Add the undo action to the stack
        if (undo)
        {
            Global.undoRedoMan.add(new UndoableItem(
                    team,
                    new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.TEAM_ADD_PROJECT, this),
                    new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.TEAM_ADD_PROJECT, this)
            ));
        }
        this.teams.add(team);
    }

    /**
     * Removes a Team from the Project's list of Teams
     * @param team The team to remove
     */
    public void removeTeam(Team team, Boolean redo)
    {
        // Add the undo action to the stack
        if (redo)
        {
            Global.undoRedoMan.add(new UndoableItem(
                    team,
                    new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.TEAM_DEL_PROJECT, this),
                    new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.TEAM_DEL_PROJECT, this)
            ));
        }
        this.teams.remove(team);
    }

    /** 
     * Add Release to Project
     * @param release release to be added
     */
    public void addRelease(Release release)
    {
        //Add the undo action to the stack
        //TODO UNDO REDO
        this.releases.add(release);
    }
    
     /**
     * Prepares a project to be serialized.
     */
    public void prepSerialization()
    {
        serializableTeams.clear();
        for (Object item : teams)
        {
            this.serializableTeams.add((Team) item);
        }

        serializableReleases.clear();
        for (Object item : releases)
        {
            this.serializableReleases.add((Release) item);
        }
    }


    /**
     * Deserialization post-processing.
     */
    public void postSerialization()
    {
        teams.clear();
        for (Object item : serializableTeams)
        {
            this.teams.add((Team) item);
        }

        releases.clear();
        for (Object item : serializableReleases)
        {
            this.releases.add((Release) item);
        }
    }

    /**
     * An overridden version for the String representation of a Workspace.
     * @return The short name of the Workspace
     */
    @Override
    public String toString()
    {
        return this.shortName;
    }
}
