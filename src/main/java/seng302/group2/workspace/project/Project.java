package seng302.group2.workspace.project;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.BacklogCategory;
import seng302.group2.scenes.listdisplay.ReleaseCategory;
import seng302.group2.scenes.listdisplay.StoryCategory;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.backlog.Backlog;
import seng302.group2.workspace.release.Release;
import seng302.group2.workspace.story.Story;
import seng302.group2.workspace.allocation.Allocation;
import seng302.group2.workspace.team.Team;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * A class representing real-world projects
 * Created by Jordane on 18/04/2015.
 */
public class Project extends TreeViewItem implements Serializable, Comparable<Project>
{
    private String shortName;
    private String longName;
    private String description;

    private transient ObservableList<Release> releases = observableArrayList();
    private List<Release> serializableReleases = new ArrayList<>();
    private transient ObservableList<Allocation> teamAllocations = observableArrayList();
    private List<Allocation> serializableTeamAllocations = new ArrayList<>();
    private transient ObservableList<Story> unallocatedStories = observableArrayList();
    private List<Story> serializableStories = new ArrayList<>();
    private transient ObservableList<Backlog> backlogs = observableArrayList();
    private List<Backlog> serializableBacklogs = new ArrayList<>();

    @Deprecated
    private transient ObservableList<Team> teams = observableArrayList();
    @Deprecated
    private List<Team> serializableTeams = new ArrayList<>();


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
        addListeners();
    }


    /**
     * Adds listeners to the project properties (lists), primarily for sorting
     */
    public void addListeners()
    {
        System.out.println("Listeners added");
        releases.addListener((ListChangeListener<Release>) change ->
            {
                if (change.next() && !change.wasPermutated())
                {
                    Collections.sort(releases);
                }
            });
        
        unallocatedStories.addListener((ListChangeListener<Story>) change ->
            {
                if (change.next() && !change.wasPermutated())
                {
                    Collections.sort(unallocatedStories, Story.StoryNameComparator);
                }
            });

        backlogs.addListener((ListChangeListener<Backlog>) change ->
            {
                if (change.next() && !change.wasPermutated())
                {
                    Collections.sort(backlogs);
                }
            });
        for (Backlog bl : backlogs)
        {
            bl.addListeners();
        }
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
     * Gets a set of the teams that have been assigned to the project through the team allocations
     * @return a set of the teams that have been assigned to the project
     */
    public Set<Team> getAllTeams()
    {
        Set<Team> teams = new HashSet<>();
        for (Allocation alloc : teamAllocations)
        {
            Team projectTeam = alloc.getTeam();
            if (!projectTeam.isUnassignedTeam())
            {
                teams.add(projectTeam);
            }
        }
        return teams;
    }


    /**
     * Gets a set of the teams currently assigned to the project through the team allocations
     * @return a set of the teams currently assigned to the project
     */
    public Set<Team> getCurrentTeams()
    {
        Set<Team> teams = new HashSet<>();
        LocalDate now = LocalDate.now();
        for (Allocation alloc : teamAllocations)
        {
            Team projectTeam = alloc.getTeam();

            if (!projectTeam.isUnassignedTeam()
                    && alloc.getStartDate().isBefore(now)
                    && (alloc.getEndDate() == null || alloc.getEndDate().isAfter(now))
                    && !teams.contains(projectTeam)
                    && Global.currentWorkspace.getTeams().contains(alloc.getTeam()))
            {
                teams.add(projectTeam);
            }
        }
        return teams;
    }


    /**
     * Gets a set of the teams that have been assigned to the project through the team allocations
     * @return a set of the teams that have been assigned to the project
     */
    public Set<Allocation> getPastAllocations()
    {
        Set<Allocation> allocations = new HashSet<>();
        LocalDate now = LocalDate.now();
        for (Allocation alloc : teamAllocations)
        {
            Team projectTeam = alloc.getTeam();

            if (!projectTeam.isUnassignedTeam()
                    && alloc.getStartDate().isBefore(now)
                    && alloc.getEndDate().isBefore(now))
            {
                allocations.add(alloc);
            }
        }
        return allocations;
    }


    /**
     * Gets the releases of the project
     * @return list of releases
     */
    public ObservableList<Release> getReleases()
    {
        return this.releases;
    }

    /**
     * Gets the unallocatedStories of the project
     * @return list of unallocatedStories
     */
    public ObservableList<Story> getUnallocatedStories()
    {
        return this.unallocatedStories;
    }

    /**
     * Gets the backlogs of the project
     * @return list of backlogs
     */
    public ObservableList<Backlog> getBacklogs()
    {
        return this.backlogs;
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
    @Deprecated
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
     * Gets the serializable unallocatedStories
     * @return the serializable releases
     */
    public List<Story> getSerilizableStories()
    {
        return serializableStories;
    }

    /**
     * Gets the serializable Backlogs
     * @return the serializable Backlogs
     */
    public List<Backlog> getSerilizableBacklogs()
    {
        return serializableBacklogs;
    }


    /**
     * Gets the list of teams allocated to the project in the past
     * @return list of teams
     */
    public ObservableList<Allocation> getTeamAllocations()
    {
        return teamAllocations;
    }

    /**
     * Gets the serializable teams allocated to the project in the past
     * @return serializable teams
     */
    public List<Allocation> getSerializableTeamAllocations()
    {
        return serializableTeamAllocations;
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
    /*
    @Deprecated
    public void add(Team team)
    {
        // Add the undo action to the stack
        Global.undoRedoMan.add(new UndoableItem(
                team,
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.TEAM_ADD_PROJECT, this),
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.TEAM_ADD_PROJECT, this)
        ));

        this.teams.add(team);
    }*/

    /**
     * Adds a Team to the Project list of Teams
     * @param team The team to add
     */
    /*
    @Deprecated
    public void addWithoutUndo(Team team)
    {
        this.teams.add(team);
    }*/
    
    /**
     * Adds a Project to the Project list of Stories
     * @param story the story to add
     */
    public void add(Story story)
    {
        Command command = new AddStoryCommand(this, story);
        Global.commandManager.executeCommand(command);

    }


    /**
     * Adds a Backlog to the Backlog list of Backlogs
     * @param backlog the Backlog to add
     */
    public void add(Backlog backlog)
    {
        Command command = new AddBacklogCommand(this, backlog);
        Global.commandManager.executeCommand(command);
    }


    /**
     * Removes the team allocation from the list of project's team allocation
     * @param allocation allocation to remove from the list
     */
    @Deprecated
    public void remove(Allocation allocation)
    {
        this.teamAllocations.remove(allocation);
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
     * Adds a Release to the Project's list of Releases.
     * @param release The release to add
     */
    public void add(Release release)
    {
        Command command = new AddReleaseCommand(this, release);
        Global.commandManager.executeCommand(command);
    }


    /**
     * Adds a team allocation to the list of project's team allocations
     * @param allocation Allocation to add
     */
    public void add(Allocation allocation)
    {
        if (!this.equals(allocation.getProject()))
        {
            System.out.println("Called on wrong project, not happening");
            return;
        }

        Command addAlloc = new AddAllocationCommand(this, allocation.getTeam(), allocation);
        Global.commandManager.executeCommand(addAlloc);
    }


    /**
     * Prepares a project to be serialized.
     */
    public void prepSerialization()
    {
        serializableReleases.clear();
        for (Object item : releases)
        {
            this.serializableReleases.add((Release) item);
        }

        System.out.println("allocation serial prep");
        serializableTeamAllocations.clear();
        for (Allocation item : teamAllocations)
        {
            System.out.println("there is an allocation");
            this.serializableTeamAllocations.add(item);
        }

        serializableStories.clear();
        for (Story item : unallocatedStories)
        {
            this.serializableStories.add(item);
        }

        serializableBacklogs.clear();
        for (Backlog backlog : backlogs)
        {
            backlog.prepSerialization();
            this.serializableBacklogs.add(backlog);
        }
    }


    /**
     * Deserialization post-processing.
     */
    public void postSerialization()
    {
        /*teams.clear();
        for (Object item : serializableTeams)
        {
            this.teams.add((Team) item);
        }*/

        releases.clear();
        for (Object item : serializableReleases)
        {
            this.releases.add((Release) item);
        }

        teamAllocations.clear();
        for (Allocation alloc : serializableTeamAllocations)
        {
            this.teamAllocations.add(alloc);
        }

        unallocatedStories.clear();
        for (Story story : serializableStories)
        {
            this.unallocatedStories.add(story);
        }

        backlogs.clear();
        for (Backlog backlog : serializableBacklogs)
        {
            backlog.postDeserialization();
            this.backlogs.add(backlog);
        }
    }


    /**
     * Gets all of the stories within the project (unassigned and assigned)
     * @return all of the stories within the project (unassigned and assigned)
     */
    public Set<Story> getAllStories()
    {
        Set<Story> stories = new HashSet<>();
        stories.addAll(unallocatedStories);
        for (Backlog backlog : backlogs)
        {
            stories.addAll(backlog.getStories());
        }
        return stories;
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
     * Compares to another project based on their short names
     * @param compareProject The project to compare to
     * @return The result of compareTo on the short names of the projects
     */
    @Override
    public int compareTo(Project compareProject)
    {
        String proj1ShortName = this.getShortName();
        String proj2ShortName = compareProject.getShortName();
        return proj1ShortName.compareTo(proj2ShortName);
    }

    
    /**
     * Deletes a project from the given workspace.
     * @param ws The workspace to remove the project from
     */
    public void deleteProject(Workspace ws)
    {
        Command command = new DeleteProjectCommand(this, ws);
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
        BacklogCategory backlogCategory = new BacklogCategory("Backlog", this);
        children.add(backlogCategory);
        StoryCategory storiesCategory = new StoryCategory("Unassigned Stories", this);
        children.add(storiesCategory);
        return children;
    }


    /**
     * Creates a Project edit command and executes it with the Global Command Manager, updating
     * the project with the new parameter values.
     * @param newShortName The new short name
     * @param newLongName The new long name
     * @param newDescription The new description
     * @param teams The new project teams
     */
    public void edit(String newShortName, String newLongName, String newDescription,
                     ObservableList<Team> teams)
    {
        Command projEdit = new ProjectEditCommand(this, newShortName, newLongName, newDescription,
                teams);
        Global.commandManager.executeCommand(projEdit);
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
        private ObservableList<Team> teams;
        private String oldShortName;
        private String oldLongName;
        private String oldDescription;
        private ObservableList<Team> oldTeams;

        private ProjectEditCommand(Project proj, String newShortName, String newLongName,
                                     String newDescription, ObservableList<Team> newTeams)
        {
            this.proj = proj;
            this.shortName = newShortName;
            this.longName = newLongName;
            this.description = newDescription;
            this.teams = newTeams;
            this.oldShortName = proj.shortName;
            this.oldLongName = proj.longName;
            this.oldDescription = proj.description;
            this.oldTeams = proj.teams;
        }

        /**
         * Executes/Redoes the changes of the project edit
         */
        public void execute()
        {
            proj.shortName = shortName;
            proj.longName = longName;
            proj.description = description;
            proj.teams = teams;
            Collections.sort(Global.currentWorkspace.getProjects());
        }

        /**
         * Undoes the changes of the project edit
         */
        public void undo()
        {
            proj.shortName = oldShortName;
            proj.longName = oldLongName;
            proj.description = oldDescription;
            proj.teams = oldTeams;
            Collections.sort(Global.currentWorkspace.getProjects());
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
        }

        public void undo()
        {
            ws.getProjects().add(proj);
        }
    }
    
    
    private class AddReleaseCommand implements Command
    {
        private Release release;
        private Project proj;

        AddReleaseCommand(Project proj, Release release)
        {
            this.proj = proj;
            this.release = release;
        }

        public void execute()
        {
            proj.getReleases().add(release);
            release.setProject(proj);
        }

        public void undo()
        {
            proj.getReleases().remove(release);
            release.setProject(null);
        }
    }


    private class AddStoryCommand implements Command
    {
        private Story story;
        private Project proj;

        AddStoryCommand(Project proj, Story story)
        {
            this.proj = proj;
            this.story = story;
        }

        public void execute()
        {
            proj.getUnallocatedStories().add(story);
        }

        public void undo()
        {
            proj.getUnallocatedStories().remove(story);
        }
    }


    private class AddBacklogCommand implements Command
    {
        private Backlog backlog;
        private Project proj;

        AddBacklogCommand(Project proj, Backlog backlog)
        {
            this.proj = proj;
            this.backlog = backlog;
        }

        public void execute()
        {
            proj.getBacklogs().add(backlog);
            backlog.setProject(proj);
        }

        public void undo()
        {
            proj.getBacklogs().remove(backlog);
            backlog.setProject(null);
        }
    }

    private class AddAllocationCommand implements Command
    {
        private Project proj;
        private Team team;
        private Allocation allocation;

        AddAllocationCommand(Project proj, Team team, Allocation allocation)
        {
            this.proj = proj;
            this.team = team;
            this.allocation = allocation;
        }

        public void execute()
        {
            proj.getTeamAllocations().add(allocation);
            team.getProjectAllocations().add(allocation);
        }

        public void undo()
        {
            proj.getTeamAllocations().remove(allocation);
            team.getProjectAllocations().remove(allocation);
        }
    }
}
