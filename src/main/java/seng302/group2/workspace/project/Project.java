package seng302.group2.workspace.project;

import javafx.collections.ObservableList;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.ReleaseCategory;
import seng302.group2.scenes.listdisplay.StoryCategory;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.util.undoredo.Command;
import seng302.group2.util.undoredo.UndoRedoAction;
import seng302.group2.util.undoredo.UndoRedoPerformer;
import seng302.group2.util.undoredo.UndoableItem;
import seng302.group2.util.validation.DateValidator;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.release.Release;
import seng302.group2.workspace.story.Story;
import seng302.group2.workspace.team.Allocation;
import seng302.group2.workspace.team.Team;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ListChangeListener;
import seng302.group2.workspace.person.Person;

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
    private transient ObservableList<Story> stories = observableArrayList();
    private List<Story> serializableStories = new ArrayList<>();

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
    
    // TODO
    private void addListeners()
    {
        releases.addListener((ListChangeListener<Release>) change ->
            {
                if (change.next() && !change.wasPermutated())
                {
                    Collections.sort(releases);
                }
            });
        
        stories.addListener((ListChangeListener<Story>) change ->
            {
                if (change.next() && !change.wasPermutated())
                {
                    Collections.sort(stories);
                }
            });
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
    @Deprecated
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
                    && !teams.contains(projectTeam))
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
    public Set<Team> getPastTeams()
    {
        Set<Team> teams = new HashSet<>();
        LocalDate now = LocalDate.now();
        for (Allocation alloc : teamAllocations)
        {
            Team projectTeam = alloc.getTeam();

            if (!projectTeam.isUnassignedTeam()
                    && alloc.getStartDate().isBefore(now)
                    && alloc.getEndDate().isBefore(now)
                    && !teams.contains(projectTeam))
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
        this.serializableReleases.clear();
        for (Object item : this.releases)
        {
            this.serializableReleases.add((Release)item);
        }
        //TODO sort()
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
        Command command = new AddStoryCommand(this, story);
        Global.commandManager.executeCommand(command);

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
     * Removes the team allocation from the list of project's team allocation
     * @param allocation allocation to remove from the list
     */
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



//    /** 
//     * Add Release to Project
//     * @param release release to be added
//     */
//    public void add(Release release)
//    {
//        Global.undoRedoMan.add(new UndoableItem(
//                release,
//                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.RELEASE_ADD, this),
//                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.RELEASE_ADD, this)
//        ));
//        this.releases.add(release);
//    }
    
    
    
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
        if (DateValidator.validateAllocation(allocation, this))
        {
            Command addAlloc = new AddAllocationCommand(this, allocation.getTeam(), allocation);
            Global.commandManager.executeCommand(addAlloc);
        }
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

        System.out.println("allocation serial prep");
        serializableTeamAllocations.clear();
        for (Allocation item : teamAllocations)
        {
            System.out.println("there is an allocation");
            this.serializableTeamAllocations.add(item);
        }

        serializableStories.clear();
        for (Story item : stories)
        {
            this.serializableStories.add(item);
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

        teamAllocations.clear();
        for (Allocation alloc : serializableTeamAllocations)
        {
            this.teamAllocations.add(alloc);
        }

        stories.clear();
        for (Story story : serializableStories)
        {
            this.stories.add(story);
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

    
    // TODO write javadoc.
    @Override
    public int compareTo(Project compareProject)
    {
        String proj1ShortName = this.getShortName().toUpperCase();
        String proj2ShortName = compareProject.getShortName().toUpperCase();
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
        StoryCategory storiesCategory = new StoryCategory("Stories", this);
        children.add(storiesCategory);
 

        return children;
    }


    /**
     * Creates a Project edit command and executes it with the Global Command Manager, updating
     * the project with the new parameter values.
     * @param newShortName The new short name
     * @param newLongName The new long name
     * @param newDescription The new description
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
            // TODO Remove any associations
        }

        public void undo()
        {
            ws.getProjects().add(proj);
            // TODO Readd any associations
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
            // TODO Readd any associations, eg. allocation history
        }

        public void undo()
        {
            proj.getReleases().remove(release);
            // TODO Remove any associations, eg. allocation history
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
            proj.getStories().add(story);
        }

        public void undo()
        {
            proj.getStories().remove(story);
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
