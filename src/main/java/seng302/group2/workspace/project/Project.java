package seng302.group2.workspace.project;

import javafx.collections.ObservableList;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.ReleaseCategory;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.util.undoredo.Command;
import seng302.group2.util.undoredo.UndoRedoAction;
import seng302.group2.util.undoredo.UndoRedoPerformer;
import seng302.group2.util.undoredo.UndoableItem;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.release.Release;
import seng302.group2.workspace.story.Story;
import seng302.group2.workspace.team.Team;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private List<Team> serializableTeams = new ArrayList<>();
    private transient ObservableList<Release> releases = observableArrayList();
    private List<Release> serializableReleases = new ArrayList<>();
    private transient ObservableList<Team> pastTeams = observableArrayList();
    private List<Team> serializablePastTeams = new ArrayList<>();
    private transient ObservableList<Story> stories = observableArrayList();
    private List<Story> serializableStories = new ArrayList<>();


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
     * Gets the stories of the project
     * @return list of stories
     */
    public ObservableList<Story> getStories()
    {
        this.serializableStories.clear();
        for (Object item : this.stories)
        {
            this.serializableStories.add((Story)item);
        }
        return this.stories;
        
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
    public List<Team> getSerializableTeams()
    {
        return serializableTeams;
    }

    /**
     * Gets the serializable releases
     * @return the serializable releases
     */
    public List<Release> getSerializableReleases()
    {
        return serializableReleases;
    }
    
    /**
     * Gets the serializable stories
     * @return the serializable releases
     */
    public List<Story> getSerilizableStories()
    {
        return serializableStories;
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
    public List<Team> getSerializablePastTeams()
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
     */
    public void addWithoutUndo(Team team)
    {
        this.teams.add(team);
    }
    
    /**
     * Adds a Project to the Project list of Stories
     * @param story the story to add
     */
    public void add(Story story)
    {
        this.stories.add(story);
    }
    
    /**
     * Removes a Story from the Project's list of Stories
     * @param story story to remove
     */
    public void remove(Story story)
    {
        this.stories.remove(story);
        story.setProject(null);
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
     * Removes a Team from the Project's list of Teams without an undoable command
     * @param team The team to remove
     */
    public void removeWithoutUndo(Team team)
    {
        this.teams.remove(team);
    }


    /**
     * Removes the release from the project without creating an undo command
     * @param release release to be removed
     */
    public void removeWithoutUndo(Release release)
    {
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
     * Removes the team from the list of past teams allocated to the project
     * @param team team to rmeove from the list
     */
    public void removePastTeam(Team team)
    {
        this.pastTeams.remove(team);
    }


    /**
     * Add Release to Project without an undoable command
     * @param release release to be added
     */
    public void addWithoutUndo(Release release)
    {
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
     * Adds a team to the list od teams allocated to the project in the past
     * @param team team to add
     */
    public void addPastTeam(Team team)
    {
        this.pastTeams.add(team);
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
     * Deletes a project from the given workspace.
     * @param ws The workspace to remove the project from
     */
    public void deleteProject(Workspace ws)
    {
        DeleteProjectCommand command = new DeleteProjectCommand(this, ws);
        Global.commandManager.executeCommand(command);
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


    /**
     * Creates a Project edit command and executes it with the Global Command Manager, updating
     * the project with the new parameter values.
     * @param newShortName The new short name
     * @param newLongName The new long name
     * @param newDescription The new description
     */
    public void edit(String newShortName, String newLongName, String newDescription)
    {
        Command wsedit = new ProjectEditCommand(this, newShortName, newLongName, newDescription);
        Global.commandManager.executeCommand(wsedit);
    }


    /**
     * A command class that allows the executing and undoing of project edits
     */
    private class ProjectEditCommand implements Command
    {
        private Project proj;
        private String shortName;
        private String longName;
        private String description;
        private String oldShortName;
        private String oldLongName;
        private String oldDescription;

        private ProjectEditCommand(Project proj, String newShortName, String newLongName,
                                     String newDescription)
        {
            this.proj = proj;
            this.shortName = newShortName;
            this.longName = newLongName;
            this.description = newDescription;
            this.oldShortName = proj.shortName;
            this.oldLongName = proj.longName;
            this.oldDescription = proj.description;
        }

        /**
         * Executes/Redoes the changes of the workspace edit
         */
        public void execute()
        {
            proj.shortName = shortName;
            proj.longName = longName;
            proj.description = description;
        }

        /**
         * Undoes the changes of the workspace edit
         */
        public void undo()
        {
            proj.shortName = oldShortName;
            proj.longName = oldLongName;
            proj.description = oldDescription;
        }
    }


    private class DeleteProjectCommand implements Command
    {
        private Project proj;
        private Workspace ws;

        DeleteProjectCommand(Project proj, Workspace ws)
        {
            this.proj = proj;
            this.ws = ws;
        }

        public void execute()
        {
            ws.getProjects().remove(proj);
            // TODO Remove any associations
        }

        public void undo()
        {
            ws.getProjects().add(proj);
            // TODO Readd any associations
        }
    }
}
