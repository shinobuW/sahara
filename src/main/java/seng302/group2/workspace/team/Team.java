/*
 * SENG302 Group 2
 */
package seng302.group2.workspace.team;

import javafx.collections.ObservableList;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.util.undoredo.Command;
import seng302.group2.util.undoredo.UndoRedoAction;
import seng302.group2.util.undoredo.UndoRedoPerformer;
import seng302.group2.util.undoredo.UndoableItem;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * A basic class to represent a Team in the real world.
 *
 * @author crw73
 */
public class Team extends TreeViewItem implements Serializable, Comparable<Team>
{
    public static String birthDatePattern = "dd/MM/yyyy";

    private String shortName;
    private String description;
    private Person scrumMaster;
    private Person productOwner;
    private transient ObservableList<Person> people = observableArrayList();
    private List<Person> serializablePeople = new ArrayList<>();
    private transient ObservableList<Person> devs = observableArrayList();
    private List<Person> serializableDevs = new ArrayList<>();
    private transient ObservableList<Allocation> projectAllocations = observableArrayList();
    private List<Allocation> serializableProjectAllocation = new ArrayList<>();
    private boolean unassigned = false;
    private Project project;


    /**
     * Basic Team constructor
     */
    public Team()
    {
        super("unnamed");
        this.shortName = "unnamed";
        this.description = "";
        this.project = null;
    }


    /**
     * Basic team constructor with all fields
     *
     * @param shortName   A unique short name to identify a Team
     * @param description of a Team
     */
    public Team(String shortName, String description)
    {
        // Initialize as a TreeViewItem
        super(shortName);
        this.shortName = shortName;
        this.description = description;
        this.project = null;
    }


    // <editor-fold defaultstate="collapsed" desc="Getters"> 

    /**
     * Gets the Team's short name
     *
     * @return The short name of the team
     */
    public String getShortName()
    {
        return this.shortName;
    }

    /**
     * Gets the team's description
     *
     * @return The description of the team
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * Gets the project the Team is assigned to
     *
     * @return The description of the team
     */
    public Project getProject()
    {
        return this.project;
    }

    /**
     * Gets the team's list of members
     *
     * @return The ObservableList of Persons
     */
    public ObservableList<Person> getPeople()
    {
        this.serializablePeople.clear();
        for (Object item : this.people)
        {
            this.serializablePeople.add((Person) item);
        }
        return this.people;
    }

    /**
     * Gets the team's list of roles
     *
     * @return The observable list of roles
     */
    public ObservableList<Person> getDevs()
    {
        this.serializableDevs.clear();
        for (Object item : this.devs)
        {
            this.serializableDevs.add((Person) item);
        }
        return this.devs;
    }

    /**
     * Get the team's Product Owner
     *
     * @return The Product Owner
     */
    public Person getProductOwner()
    {
        return this.productOwner;
    }

    /**
     * Get the team's Scrum Master
     *
     * @return The Scrum Master
     */
    public Person getScrumMaster()
    {
        return this.scrumMaster;
    }

    /**
     * Gets the serializable people of a team
     *
     * @return the serializable people
     */
    public List<Person> getSerializablePeople()
    {
        return serializablePeople;
    }

    /**
     * Gets the serializable project allocation
     * @return list of project allocations
     */
    public List<Allocation> getSerializableProject()
    {
        return serializableProjectAllocation;
    }

    /**
     * Gets the team's list of project allocations
     * @return list of allocations
     */
    public ObservableList<Allocation> getProjectAllocations()
    {
        return projectAllocations;
    }

    /**
     * Gets the project the Team is assigned to
     *
     * @return The description of the team
     */
    public Allocation getCurrentAllocation()
    {
        Allocation currentAllocation = null;
        LocalDate now = LocalDate.now();
        for (Allocation allocation : this.getProjectAllocations())
        {
            if (allocation.getStartDate().isBefore(now)
                    && (allocation.getEndDate() == null
                            || allocation.getEndDate().isAfter(now)))
            {
                currentAllocation = allocation;
            }
        }
        return currentAllocation;
    }

    //</editor-fold>


    // <editor-fold defaultstate="collapsed" desc="Setters">

    /**
     * Sets the team's short name
     *
     * @param shortName the short name to set
     */
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

    /**
     * Gets the team's description
     *
     * @param description the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Sets the team's project
     *
     * @param project the project the team has been added to
     */
    public void setProject(Project project)
    {
        this.project = project;
    }

    /**
     * Sets the team's Scrum Master
     *
     * @param person the person to set
     */
    public void setScrumMaster(Person person)
    {
        this.scrumMaster = person;
        //person.setRole(new Role("Scrum Master", RoleType.ScrumMaster));
    }

    /**
     * Sets the team's Product Owner
     *
     * @param person the person to set
     */
    public void setProductOwner(Person person)
    {
        this.productOwner = person;
        //person.setRole(new Role("Product Owner", RoleType.ProductOwner));
    }

    //</editor-fold>


    /**
     * Adds a Person to the Teams list of Members
     * Adds an undo item by default
     *
     * @param person The person to add
     */
    public void add(Person person)
    {
        // Add the undo action to the stack
        Global.undoRedoMan.add(new UndoableItem(
                person,
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_ADD_TEAM, this),
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_ADD_TEAM, this)
        ));

        this.people.add(person);
    }


    /**
     * Adds a Person to the Teams list of Members
     *
     * @param person The person to add
     * @param undo   Whether to create an undo item for adding the person
     */
    public void add(Person person, Boolean undo)
    {
        // Add the undo action to the stack
        if (undo)
        {
            Global.undoRedoMan.add(new UndoableItem(
                    person,
                    new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_ADD_TEAM, this),
                    new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_ADD_TEAM, this)
            ));
        }
        this.people.add(person);
    }

    /**
     * Adds a project allocation to the team's list of allocations
     * @param allocation Allocation to add
     */
    public void add(Allocation allocation)
    {
        if (!this.equals(allocation.getTeam()))
        {
            System.out.println("Called on wrong team, not happening");
            return;
        }
        Command addAlloc = new AddAllocationCommand(allocation.getProject(), this, allocation);
        Global.commandManager.executeCommand(addAlloc);
    }


    /**
     * Adds the 'Unassigned' team to the workspace.
     *
     * @return the unassigned team
     */
    public static Team createUnassignedTeam()
    {
        Team temp = new Team("Unassigned",
                "All the people unassigned to a team");
        temp.unassigned = true;
        return temp;
    }


    /**
     * Returns if the team is the team of unassigned people
     *
     * @return if the team is the unassigned team
     */
    public boolean isUnassignedTeam()
    {
        if (this.unassigned)
        {
            return true;
        }
        return false;
    }


    /**
     * Removes a Person from the Team's of Members
     * Adds a redo item by default
     *
     * @param person The person to remove
     */
    public void remove(Person person)
    {
        // Add the undo action to the stack
        Global.undoRedoMan.add(new UndoableItem(
                person,
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_DEL_TEAM, this),
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_DEL_TEAM, this)
        ));

        this.people.remove(person);
    }


    /**
     * Removes a Person from the Team's of Members
     *
     * @param person The person to remove
     * @param redo   Whether to create an redo item for removing the person
     */
    public void remove(Person person, Boolean redo)
    {
        // Add the undo action to the stack
        if (redo)
        {
            Global.undoRedoMan.add(new UndoableItem(
                    person,
                    new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_DEL_TEAM, this),
                    new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_DEL_TEAM, this)
            ));
        }
        this.people.remove(person);
    }

    /**
     * Removes the given allocation from the team's list of allocations
     * @param allocation allocation to be removed
     */
    public void remove(Allocation allocation)
    {
        this.projectAllocations.remove(allocation);
    }

    /**
     * Adds the role to the team's list of Roles
     *
     * @param person Role to add
     */
    public void addRole(Person person)
    {
        // Add the undo action to the stack
        // TODO
        this.devs.add(person);
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
     *
     * @return The items of the TreeViewItem
     */
    @Override
    public ObservableList<TreeViewItem> getChildren()
    {
        return null;
    }

    
    // TODO write javadoc.
    @Override
    public int compareTo(Team compareTeam)
    {
        String team1ShortName = this.getShortName().toUpperCase();
        String team2ShortName = compareTeam.getShortName().toUpperCase();
        return team1ShortName.compareTo(team2ShortName);
    }
    

    /**
     * Deletes the team without cascading deletion on the assigned people
     */
    public void deleteTeam()
    {
        Command teamDel = new DeleteTeamCommand(this, Global.currentWorkspace);
        Global.commandManager.executeCommand(teamDel);
    }


    /**
     * Deletes the team and all the people currently assigned to it in a cascading manner.
     */
    public void deleteTeamCascading()
    {
        Command teamCasDelete = new DeleteTeamCascadingCommand(this, Global.currentWorkspace);
        Global.commandManager.executeCommand(teamCasDelete);

        /*
        ArrayList<Person> peopleToBeDeleted = new ArrayList<>();
        ArrayList<UndoableItem> undoActions = new ArrayList<>();
        for (Person personRemoveTeam : Global.currentWorkspace.getPeople())
        {
            if (personRemoveTeam.getTeam() == this)
            {
                //Deletes the team and all corresponding people.
                peopleToBeDeleted.add(personRemoveTeam);

                //Only deletes team from person. Does not delete the person.
                //personRemoveTeam.setTeam(
                //(Team)Global.currentWorkspace.getTeams().get(0));
                //((Team)Global.currentWorkspace.getTeams().get(0)).add(
                //personRemoveTeam, false);
            }
        }

        for (Person personToDelete : peopleToBeDeleted)
        {
            undoActions.add(new UndoableItem(
                    personToDelete,
                    new UndoRedoAction(
                            UndoRedoPerformer.UndoRedoProperty.PERSON_DEL,
                            this),
                    new UndoRedoAction(
                            UndoRedoPerformer.UndoRedoProperty.PERSON_DEL,
                            this)));
            Global.currentWorkspace.removeWithoutUndo(personToDelete);

        }

        undoActions.add(new UndoableItem(
                this,
                new UndoRedoAction(
                        UndoRedoPerformer.UndoRedoProperty.TEAM_DEL,
                        this),
                new UndoRedoAction(
                        UndoRedoPerformer.UndoRedoProperty.TEAM_DEL,
                        this)));
        Global.currentWorkspace.removeWithoutUndo(this);

        if (undoActions.size() > 0)
        {
            Global.undoRedoMan.add(new UndoableItem(
                    this,
                    new UndoRedoAction(
                            UndoRedoPerformer.UndoRedoProperty.TEAM_DEL_RECURSIVE,
                            undoActions),
                    new UndoRedoAction(
                            UndoRedoPerformer.UndoRedoProperty.TEAM_DEL_RECURSIVE,
                            undoActions)
            ));
        }*/
    }


    /**
     * An overridden version for the String representation of a Team
     *
     * @return The short name of the Team
     */
    @Override
    public String toString()
    {
        return this.shortName;
    }


    /**
     * Creates a team edit command and executes it with the Global Command Manager, updating
     * the team with the new parameter values.
     * @param newShortName The new short name
     * @param newDescription The new description
     */
    public void edit(String newShortName, String newDescription)
    {
        Command teamEdit = new TeamEditCommand(this, newShortName, newDescription);
        Global.commandManager.executeCommand(teamEdit);
    }


    /**
     * A command class that allows the executing and undoing of team edits
     */
    private class TeamEditCommand implements Command
    {
        private Team team;
        private String shortName;
        private String description;
        private String oldShortName;
        private String oldDescription;

        private TeamEditCommand(Team team, String newShortName, String newDescription)
        {
            this.team = team;
            this.shortName = newShortName;
            this.description = newDescription;
            this.oldShortName = team.shortName;
            this.oldDescription = team.description;
        }

        /**
         * Executes/Redoes the changes of the team edit
         */
        public void execute()
        {
            team.shortName = shortName;
            team.description = description;
        }

        /**
         * Undoes the changes of the team edit
         */
        public void undo()
        {
            team.shortName = oldShortName;
            team.description = oldDescription;
        }
    }


    private class DeleteTeamCommand implements Command
    {
        private Team team;
        private Workspace ws;
        private List<Person> members;

        DeleteTeamCommand(Team team, Workspace ws)
        {
            this.team = team;
            this.ws = ws;
            this.members = team.getPeople();
        }

        public void execute()
        {
            ws.getTeams().remove(team);
            for (Person member : members)
            {
                member.setTeam(null);
            }
            // TODO Remove any associations
        }

        public void undo()
        {
            for (Person member : members)
            {
                member.setTeam(team);
            }
            ws.getTeams().add(team);
            // TODO Readd any associations
        }
    }

    private class DeleteTeamCascadingCommand implements Command
    {
        private Team team;
        private Workspace ws;
        private List<Person> members = new ArrayList<>();

        DeleteTeamCascadingCommand(Team team, Workspace ws)
        {
            this.team = team;
            this.ws = ws;
            for (Person p : team.getPeople())
            {
                members.add(p);
            }
        }

        public void execute()
        {
            for (Person member : members)
            {
                ws.getPeople().remove(member);
            }
            ws.getTeams().remove(team);
        }

        public void undo()
        {
            System.out.println("Undone Team Casc Delete");
            for (Person member : members)
            {
                ws.getPeople().add(member);
            }
            ws.getTeams().add(team);
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
