package seng302.group2.workspace.project;

import javafx.collections.ObservableList;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.ReleaseCategory;
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
    private transient ObservableList<Team> pastTeams = observableArrayList();
    private ArrayList<Team> serializablePastTeams = new ArrayList<>();


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
     * Returns the releases of a project casted as TreeViewItems
     * @return list of releases casted as TreeViewItems
     */
    public ObservableList<TreeViewItem> getTreeViewReleases()
    {
        ObservableList<TreeViewItem> treeViewReleases = observableArrayList();
        for (Object item : this.releases)
        {
            treeViewReleases.add((TreeViewItem)item);
        }
        return treeViewReleases;
    }
   
    
    /**
     * Gets the description of the project
     * @return The description of the project
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Gets the serializable teams
     * @return the serializable teams
     */
    public ArrayList<Team> getSerializableTeams()
    {
        return serializableTeams;
    }

    /**
     * Gets the serializable releases
     * @return the serializable releases
     */
    public ArrayList<Release> getSerializableReleases()
    {
        return serializableReleases;
    }

    /**
     * Gets the list of teams allocated to the project in the past
     * @return list of teams
     */
    public ObservableList<Team> getPastTeams()
    {
        return pastTeams;
    }

    /**
     * Gets the serializable teams allocated to the project in the past
     * @return serializable teams
     */
    public ArrayList<Team> getSerializablePastTeams()
    {
        return serializablePastTeams;
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
     * Adds an undo item by default
     * @param team The team to add
     */
    public void add(Team team)
    {
        // Add the undo action to the stack
        Global.undoRedoMan.add(new UndoableItem(
                team,
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.TEAM_ADD_PROJECT, this),
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.TEAM_ADD_PROJECT, this)
        ));

        this.teams.add(team);
    }

    /**
     * Adds a Team to the Project list of Teams
     * @param team The team to add
     * @param undo If adding the team should be undoable
     */
    public void add(Team team, boolean undo)
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
     * Adds a redo item by default
     * @param team The team to remove
     */
    public void remove(Team team)
    {
        // Add the undo action to the stack

        Global.undoRedoMan.add(new UndoableItem(
                team,
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.TEAM_DEL_PROJECT, this),
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.TEAM_DEL_PROJECT, this)
        ));

        this.teams.remove(team);
    }


    /**
     * Removes a Team from the Project's list of Teams
     * @param team The team to remove
     * @param redo If adding the team should be redoable
     */
    public void remove(Team team, Boolean redo)
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
     * Removes the release from the project
     * @param release release to be removed
     * @param redo Whether or not to add a redo item to the undoredo stack
     */
    public void remove(Release release, Boolean redo)
    {
        if (redo)
        {
            Global.undoRedoMan.add(new UndoableItem(
                    release,
                    new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.RELEASE_DEL, this),
                    new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.RELEASE_DEL, this)
            ));
        }

        this.releases.remove(release);
    }



    
        /**
     * Removes the release from the project
     * @param release release to be removed
     */
    public void remove(Release release)
    {
        Global.undoRedoMan.add(new UndoableItem(
                release,
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.RELEASE_DEL, this),
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.RELEASE_DEL, this)
        ));

        this.releases.remove(release);
    }



    /**
     * Add Release to Project
     * @param release release to be added
     * @param undo whether or not to create an undo item and add it to the undoredo stack
     */
    public void add(Release release, boolean undo)
    {
        if (undo)
        {
            Global.undoRedoMan.add(new UndoableItem(
                    release,
                    new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.RELEASE_ADD, this),
                    new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.RELEASE_ADD, this)
            ));
        }
        this.releases.add(release);
    }



    /** 
     * Add Release to Project
     * @param release release to be added
     */
    public void add(Release release)
    {
        Global.undoRedoMan.add(new UndoableItem(
                release,
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.RELEASE_ADD, this),
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.RELEASE_ADD, this)
        ));
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

    
    /**
     * Deletes a project from the current workspace.
     * @param deletedProject The project to delete
     */
    public static void deleteProject(Project deletedProject)
    {
        Global.currentWorkspace.remove(deletedProject);
    }

    /**
     * Gets the children of the category
     * @return the children of the category
     */
    @Override
    public ObservableList<TreeViewItem> getChildren()
    {
        ObservableList<TreeViewItem> children = observableArrayList();
        ReleaseCategory releasesCategory = new ReleaseCategory("Releases", this);
        children.add(releasesCategory);

        return children;
    }
}
