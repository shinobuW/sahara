/*
 * SENG302 Group 2
 */
package seng302.group2.workspace.team;

import javafx.collections.ObservableList;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.w3c.dom.Element;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.sceneswitch.switchStrategies.workspace.TeamInformationSwitchStrategy;
import seng302.group2.util.reporting.ReportGenerator;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.allocation.Allocation;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.role.Role;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * A basic class to represent a Team in the real world.
 *
 * @author crw73
 */
public class Team extends TreeViewItem implements Serializable, Comparable<Team> {
    private String shortName;
    private String description;
    private Person scrumMaster;
    private Person productOwner;
    private transient ObservableList<Person> people = observableArrayList();
    private List<Person> serializablePeople = new ArrayList<>();
    private transient ObservableList<Person> devs = observableArrayList();
    private List<Person> serializableDevs = new ArrayList<>();
    private transient ObservableList<Allocation> projectAllocations = observableArrayList();
    private List<Allocation> serializableProjectAllocations = new ArrayList<>();
    private boolean unassigned = false;
    private Project project;


    /**
     * Basic Team constructor
     */
    public Team() {
        super("unnamed");
        this.shortName = "unnamed";
        this.description = "";
        this.project = null;

        setInformationSwitchStrategy(new TeamInformationSwitchStrategy());
    }

    @Override
    public Set<TreeViewItem> getItemsSet() {
        Set<TreeViewItem> items = new HashSet<>();

        items.addAll(people);
        items.addAll(devs);
        items.addAll(projectAllocations);

        return items;
    }


    /**
     * Basic team constructor with all fields
     *
     * @param shortName   A unique short name to identify a Team
     * @param description of a Team
     */
    public Team(String shortName, String description) {
        // Initialize as a TreeViewItem
        super(shortName);
        this.shortName = shortName;
        this.description = description;
        this.project = null;

        setInformationSwitchStrategy(new TeamInformationSwitchStrategy());
    }


    // <editor-fold defaultstate="collapsed" desc="Getters"> 

    /**
     * Adds the 'Unassigned' team to the workspace.
     *
     * @return the unassigned team
     */
    public static Team createUnassignedTeam() {
        Team temp = new Team("Unassigned",
                "All the people unassigned to a team");
        temp.unassigned = true;
        return temp;
    }

    /**
     * Gets the Team's short name
     *
     * @return The short name of the team
     */
    public String getShortName() {
        return this.shortName;
    }

    /**
     * Sets the team's short name
     *
     * @param shortName the short name to set
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * Gets the team's description
     *
     * @return The description of the team
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets the team's description
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the project the Team is assigned to
     *
     * @return The description of the team
     */
    public Project getProject() {
        return this.project;
    }

    /**
     * Sets the team's project
     *
     * @param project the project the team has been added to
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * Gets the team's list of members
     *
     * @return The ObservableList of Persons
     */
    public ObservableList<Person> getPeople() {
        /*this.serializablePeople.clear();
        for (Object item : this.people)
        {
            this.serializablePeople.add((Person) item);
        }*/
        return this.people;
    }

    /**
     * Gets the team's list of roles
     *
     * @return The observable list of roles
     */
    public ObservableList<Person> getDevs() {
        /*this.serializableDevs.clear();
        for (Object item : this.devs)
        {
            this.serializableDevs.add((Person) item);
        }*/
        return this.devs;
    }

    /**
     * Get the team's Product Owner
     *
     * @return The Product Owner
     */
    public Person getProductOwner() {
        return this.productOwner;
    }

    /**
     * Sets the team's Product Owner
     *
     * @param person the person to set
     */
    public void setProductOwner(Person person) {
        this.productOwner = person;
        //person.setRole(new Role("Product Owner", RoleType.PRODUCT_OWNER));
    }

    //</editor-fold>


    // <editor-fold defaultstate="collapsed" desc="Setters">

    /**
     * Get the team's Scrum Master
     *
     * @return The Scrum Master
     */
    public Person getScrumMaster() {
        return this.scrumMaster;
    }

    /**
     * Sets the team's Scrum Master
     *
     * @param person the person to set
     */
    public void setScrumMaster(Person person) {
        this.scrumMaster = person;
        //person.setRole(new Role("Scrum Master", RoleType.SCRUM_MASTER));
    }

    /**
     * Gets the serializable people of a team
     *
     * @return the serializable people
     */
    public List<Person> getSerializablePeople() {
        return serializablePeople;
    }

    /**
     * Gets the serializable project allocations
     *
     * @return list of project allocations
     */
    public List<Allocation> getSerializableProject() {
        return serializableProjectAllocations;
    }

    /**
     * Gets the team's list of project allocations
     *
     * @return list of allocations
     */
    public ObservableList<Allocation> getProjectAllocations() {
        return projectAllocations;
    }

    //</editor-fold>

    /**
     * Gets the project the Team is assigned to
     *
     * @return The description of the team
     */
    public Allocation getCurrentAllocation() {
        Allocation currentAllocation = null;
        LocalDate now = LocalDate.now();
        for (Allocation allocation : this.getProjectAllocations()) {
            if (allocation.getStartDate().isBefore(now)
                    && (allocation.getEndDate() == null
                    || allocation.getEndDate().isAfter(now))) {
                currentAllocation = allocation;
            }
        }
        return currentAllocation;
    }

    /**
     * Adds a Person to the Teams list of Members
     *
     * @param person The person to add
     * @param undo   Whether to create an undo item for adding the person
     */
    public void add(Person person, Boolean undo) {
//        // Add the undo action to the stack
//        if (undo)
//        {
//            Global.undoRedoMan.add(new UndoableItem(
//                    person,
//                    new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_ADD_TEAM, this),
//                    new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_ADD_TEAM, this)
//            ));
//        }
        this.people.add(person);
    }

    /**
     * Adds a project allocation to the team's list of allocations
     *
     * @param allocation Allocation to add
     */
    @Deprecated
    public void add(Allocation allocation) {
        if (!this.equals(allocation.getTeam())) {
            System.out.println("Called on wrong team, not happening");
            return;
        }

        Command addAlloc = new AddAllocationCommand(allocation.getProject(), this, allocation);
        Global.commandManager.executeCommand(addAlloc);
    }

    /**
     * Returns if the team is the team of unassigned people
     *
     * @return if the team is the unassigned team
     */
    public boolean isUnassignedTeam() {
        if (this.unassigned) {
            return true;
        }
        return false;
    }


    /**
     * //this.getTeamAllocations().add(allocation);
     * Removes the given allocation from the team's list of allocations
     *
     * @param allocation allocation to be removed
     */
    public void remove(Allocation allocation) {
        this.projectAllocations.remove(allocation);
    }


    /**
     * Prepares a Team to be serialized.
     */
    public void prepSerialization() {
        serializablePeople.clear();
        for (Object item : people) {
            this.serializablePeople.add((Person) item);
        }

        serializableProjectAllocations.clear();
        for (Object item : projectAllocations) {
            this.serializableProjectAllocations.add((Allocation) item);
        }
    }


    /**
     * Deserialization post-processing.
     */
    public void postSerialization() {
        people.clear();
        for (Object item : serializablePeople) {
            this.people.add((Person) item);
        }

        projectAllocations.clear();
        for (Allocation alloc : serializableProjectAllocations) {
            this.projectAllocations.add(alloc);
        }
    }

    /**
     * Method for creating an XML element for the Team within report generation
     * @return element for XML generation
     */
    @Override
    public Element generateXML() {
        Element teamElement = ReportGenerator.doc.createElement("team");
        System.out.println(shortName);
        //WorkSpace Elements
        Element teamShortName = ReportGenerator.doc.createElement("identifier");
        teamShortName.appendChild(ReportGenerator.doc.createTextNode(shortName));
        teamElement.appendChild(teamShortName);

        Element teamDescription = ReportGenerator.doc.createElement("description");
        teamDescription.appendChild(ReportGenerator.doc.createTextNode(description));
        teamElement.appendChild(teamDescription);

        if (getCurrentAllocation() != null) {
            Element projectAllocatedTo = ReportGenerator.doc.createElement("assigned-project");
            projectAllocatedTo.appendChild(ReportGenerator.doc.createTextNode(getCurrentAllocation()
                    .getProject().toString()));
            teamElement.appendChild(projectAllocatedTo);

            Element teamStartDate = ReportGenerator.doc.createElement("allocation-start-date");
            teamStartDate.appendChild(ReportGenerator.doc.createTextNode(this.getCurrentAllocation()
                    .getStartDate().format(Global.dateFormatter)));
            teamElement.appendChild(teamStartDate);

            Element teamEndDate = ReportGenerator.doc.createElement("allocation-end-date");
            teamEndDate.appendChild(ReportGenerator.doc.createTextNode(this.getCurrentAllocation()
                    .getEndDate().format(Global.dateFormatter)));
            teamElement.appendChild(teamEndDate);
        }

        Element productOwnerElement = ReportGenerator.doc.createElement("product-owner");
        if (productOwner != null) {
            Element teamProductOwner = productOwner.generateXML();
            productOwnerElement.appendChild(teamProductOwner);
        }
        teamElement.appendChild(productOwnerElement);

        Element scrumMasterElement = ReportGenerator.doc.createElement("scrum-master");
        if (scrumMaster != null) {
            Element teamScrumMaster = scrumMaster.generateXML();
            scrumMasterElement.appendChild(teamScrumMaster);
        }
        teamElement.appendChild(scrumMasterElement);

        Element devElement = ReportGenerator.doc.createElement("devs");
        for (Person person : people) {
            if (person.getRole() != null) {
                if (person.getRole().getType() == Role.RoleType.DEVELOPMENT_TEAM_MEMBER) {
                    Element personElement = person.generateXML();
                    devElement.appendChild(personElement);
                }
            }
        }
        teamElement.appendChild(devElement);

        Element othersElement = ReportGenerator.doc.createElement("others");
        for (Person person : people) {
            if (person.getRole() != null) {
                if (person.getRole().getType() == Role.RoleType.NONE) {
                    Element personElement = person.generateXML();
                    othersElement.appendChild(personElement);
                }
            }
            if (person.getRole() == null) {
                Element personElement = person.generateXML();
                othersElement.appendChild(personElement);
            }
        }
        teamElement.appendChild(othersElement);

        return teamElement;
    }

    /**
     * Gets the children of the TreeViewItem
     *
     * @return The items of the TreeViewItem
     */
    @Override
    public ObservableList<TreeViewItem> getChildren() {
        return null;
    }


    /**
     * Compares the team to another team based on their short names
     *
     * @param compareTeam The team to compare to
     * @return The result of running the string comparator on the teams' short names
     */
    @Override
    public int compareTo(Team compareTeam) {
        String team1ShortName = this.getShortName();
        String team2ShortName = compareTeam.getShortName();
        return team1ShortName.compareTo(team2ShortName);
    }


    /**
     * Deletes the team without cascading deletion on the assigned people
     */
    public void deleteTeam() {
        Command teamDel = new DeleteTeamCommand(this);
        Global.commandManager.executeCommand(teamDel);
    }


    /**
     * Deletes the team and all the people currently assigned to it in a cascading manner.
     */
    public void deleteTeamCascading() {
        Command teamCasDelete = new DeleteTeamCascadingCommand(this, Global.currentWorkspace);
        Global.commandManager.executeCommand(teamCasDelete);
    }


    /**
     * An overridden version for the String representation of a Team
     *
     * @return The short name of the Team
     */
    @Override
    public String toString() {
        return this.shortName;
    }

    @Override
    public boolean equivalentTo(Object object) {
        if (!(object instanceof Team)) {
            return false;
        }
        if (object == this) {
            return true;
        }

        //return false;

        Team team = (Team)object;
        return new EqualsBuilder()
                .append(shortName, team.shortName)
                .append(description, team.description)
                .append(project, team.project)
                .isEquals();
    }


    /**
     * Creates a team edit command and executes it with the Global Command Manager, updating
     * the team with the new parameter values.
     *
     * @param newShortName   The new short name
     * @param newDescription The new description
     */
    public void edit(String newShortName, String newDescription) {
        Command teamEdit = new BasicTeamEditCommand(this, newShortName, newDescription);
        Global.commandManager.executeCommand(teamEdit);
    }


    /**
     * Creates a team edit command and executes it with the Global Command Manager, updating
     * the team with the new parameter values.
     *
     * @param newShortName   The new short name
     * @param newDescription The new description
     * @param newDevelopers  The new developers
     * @param newMembers     The new team members
     * @param newPO          The new product owner
     * @param newSM          The new scrum master
     */
    public void edit(String newShortName, String newDescription, Collection<Person> newMembers,
                     Person newPO, Person newSM, Collection<Person> newDevelopers) {
        Command teamEdit = new ExtendedTeamEditCommand(this, newShortName, newDescription,
                newMembers, newPO, newSM, newDevelopers);
        Global.commandManager.executeCommand(teamEdit);
    }


    /**
     * A command class that allows the executing and undoing of basic team edits
     */
    private class BasicTeamEditCommand implements Command {
        private Team team;
        private String shortName;
        private String description;
        private String oldShortName;
        private String oldDescription;

        private BasicTeamEditCommand(Team team, String newShortName, String newDescription) {
            this.team = team;
            this.shortName = newShortName;
            this.description = newDescription;
            this.oldShortName = team.shortName;
            this.oldDescription = team.description;
        }

        /**
         * Executes/Redoes the changes of the team edit
         */
        public void execute() {
            team.shortName = shortName;
            team.description = description;
        }

        /**
         * Undoes the changes of the team edit
         */
        public void undo() {
            team.shortName = oldShortName;
            team.description = oldDescription;
        }

        @Override
        public boolean map(Set<TreeViewItem> stateObjects) {
            boolean mapped = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(team)) {
                    this.team = (Team) item;
                    mapped = true;
                }
            }
            return mapped;
        }
    }


    /**
     * A command class that allows the executing and undoing of team edits
     */
    private class ExtendedTeamEditCommand implements Command {
        private Team team;

        private String shortName;
        private String description;
        private Collection<Person> members = new HashSet<>();
        private Person productOwner;
        private Person scrumMaster;
        private Collection<Person> developers = new HashSet<>();

        private String oldShortName;
        private String oldDescription;
        private Collection<Person> oldMembers = new HashSet<>();
        private Person oldProductOwner;
        private Person oldScrumMaster;
        private Collection<Person> oldDevelopers = new HashSet<>();

        private ExtendedTeamEditCommand(Team team, String newShortName, String newDescription,
                                        Collection<Person> newMembers, Person newPO, Person newSM,
                                        Collection<Person> newDevelopers) {
            this.team = team;

            this.shortName = newShortName;
            this.description = newDescription;
            this.members.addAll(newMembers);
            this.productOwner = newPO;
            this.scrumMaster = newSM;
            this.developers.addAll(newDevelopers);

            this.oldShortName = team.shortName;
            this.oldDescription = team.description;
            this.oldMembers.addAll(team.people);
            this.oldProductOwner = team.productOwner;
            this.oldScrumMaster = team.scrumMaster;
            this.oldDevelopers.addAll(team.devs);
        }

        /**
         * Executes/Redoes the changes of the team edit
         */
        public void execute() {
            Global.getUnassignedTeam().getPeople().removeAll(oldMembers);
            for (Person member : oldMembers) {
                member.setTeam(Global.getUnassignedTeam());
            }

            Global.getUnassignedTeam().getPeople().removeAll(members);
            for (Person member : members) {
                member.setTeam(team);
            }

            team.shortName = shortName;
            team.description = description;
            team.people.clear();
            team.people.addAll(members);
            team.productOwner = productOwner;
            team.scrumMaster = scrumMaster;
            team.devs.clear();
            team.devs.addAll(developers);
        }

        /**
         * Undoes the changes of the team edit
         */
        public void undo() {
            team.shortName = oldShortName;
            team.description = oldDescription;
            team.people.clear();
            team.people.addAll(oldMembers);
            team.productOwner = oldProductOwner;
            team.scrumMaster = oldScrumMaster;
            team.devs.clear();
            team.devs.addAll(oldDevelopers);

            Global.getUnassignedTeam().getPeople().addAll(members);
            for (Person member : members) {
                member.setTeam(Global.getUnassignedTeam());
            }
            for (Person member : oldMembers) {
                member.setTeam(team);
            }
        }

        @Override
        public boolean map(Set<TreeViewItem> stateObjects) {
            boolean mapped_team = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(team)) {
                    this.team = (Team) item;
                    mapped_team = true;
                }
            }

            boolean mapped_po = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(productOwner)) {
                    this.productOwner = (Person) item;
                    mapped_po = true;
                }
            }
            boolean mapped_old_po = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(oldProductOwner)) {
                    this.oldProductOwner = (Person) item;
                    mapped_old_po = true;
                }
            }

            boolean mapped_sm = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(scrumMaster)) {
                    this.scrumMaster = (Person) item;
                    mapped_sm = true;
                }
            }
            boolean mapped_old_sm = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(oldScrumMaster)) {
                    this.oldScrumMaster = (Person) item;
                    mapped_old_sm = true;
                }
            }

            // Members collections
            for (Person member : members) {
                for (TreeViewItem item : stateObjects) {
                    if (item.equivalentTo(member)) {
                        members.remove(member);
                        members.add((Person)item);
                        break;
                    }
                }
            }
            for (Person member : oldMembers) {
                for (TreeViewItem item : stateObjects) {
                    if (item.equivalentTo(member)) {
                        oldMembers.remove(member);
                        oldMembers.add((Person)item);
                        break;
                    }
                }
            }

            // Developers collections
            for (Person dev : developers) {
                for (TreeViewItem item : stateObjects) {
                    if (item.equivalentTo(dev)) {
                        developers.remove(dev);
                        developers.add((Person)item);
                        break;
                    }
                }
            }
            for (Person dev : oldDevelopers) {
                for (TreeViewItem item : stateObjects) {
                    if (item.equivalentTo(dev)) {
                        oldDevelopers.remove(dev);
                        oldDevelopers.add((Person)item);
                        break;
                    }
                }
            }

            return mapped_sm && mapped_po && mapped_team && mapped_old_po && mapped_old_sm;
        }
    }


    private class DeleteTeamCommand implements Command {
        private Team team;
        private List<Person> members;

        DeleteTeamCommand(Team team) {
            this.team = team;
            this.members = team.getPeople();
        }

        public void execute() {
            Global.currentWorkspace.getTeams().remove(team);
            for (Person member : members) {
                member.setTeam(null);
            }
        }

        public void undo() {
            for (Person member : members) {
                member.setTeam(team);
            }
            Global.currentWorkspace.getTeams().add(team);
        }

        @Override
        public boolean map(Set<TreeViewItem> stateObjects) {
            boolean mapped = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(team)) {
                    this.team = (Team) item;
                    mapped = true;
                }
            }

            // Member collection
            for (Person member : members) {
                for (TreeViewItem item : stateObjects) {
                    if (item.equivalentTo(member)) {
                        members.remove(member);
                        members.add((Person)item);
                        break;
                    }
                }
            }

            return mapped;
        }
    }

    private class DeleteTeamCascadingCommand implements Command {
        private Team team;
        private List<Person> members = new ArrayList<>();

        DeleteTeamCascadingCommand(Team team, Workspace ws) {
            this.team = team;
            for (Person p : team.getPeople()) {
                members.add(p);
            }
        }

        public void execute() {
            for (Person member : members) {
                Global.currentWorkspace.getPeople().remove(member);
            }
            Global.currentWorkspace.getTeams().remove(team);
        }

        public void undo() {
            System.out.println("Undone Team Casc Delete");
            for (Person member : members) {
                Global.currentWorkspace.getPeople().add(member);
            }
            Global.currentWorkspace.getTeams().add(team);
        }

        @Override
        public boolean map(Set<TreeViewItem> stateObjects) {
            boolean mapped = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(team)) {
                    this.team = (Team) item;
                    mapped = true;
                }
            }

            // Member collection
            for (Person member : members) {
                for (TreeViewItem item : stateObjects) {
                    if (item.equivalentTo(member)) {
                        members.remove(member);
                        members.add((Person)item);
                        break;
                    }
                }
            }

            return mapped;
        }
    }

    private class AddPersonCommand implements Command {
        private Person person;
        private Team team;

        AddPersonCommand(Team team, Person person) {
            this.person = person;
            this.team = team;
        }

        public void execute() {
            team.getPeople().add(person);
            person.setTeam(team);
        }

        public void undo() {
            team.getPeople().remove(person);
            person.setTeam(team);
        }

        @Override
        public boolean map(Set<TreeViewItem> stateObjects) {
            boolean mapped_team = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(team)) {
                    this.team = (Team) item;
                    mapped_team = true;
                }
            }
            boolean mapped_person = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(person)) {
                    this.person = (Person) item;
                    mapped_person = true;
                }
            }
            return mapped_team && mapped_person;
        }
    }

    private class AddAllocationCommand implements Command {
        private Project proj;
        private Team team;
        private Allocation allocation;

        AddAllocationCommand(Project proj, Team team, Allocation allocation) {
            this.proj = proj;
            this.team = team;
            this.allocation = allocation;
        }

        public void execute() {
            proj.getTeamAllocations().add(allocation);
            team.getProjectAllocations().add(allocation);
        }

        public void undo() {
            proj.getTeamAllocations().remove(allocation);
            team.getProjectAllocations().remove(allocation);
        }

        @Override
        public boolean map(Set<TreeViewItem> stateObjects) {
            boolean mapped_team = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(team)) {
                    this.team = (Team) item;
                    mapped_team = true;
                }
            }
            boolean mapped_project = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(proj)) {
                    this.proj = (Project) item;
                    mapped_project = true;
                }
            }
            boolean mapped_alloc = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(allocation)) {
                    this.allocation = (Allocation) item;
                    mapped_alloc = true;
                }
            }
            return mapped_team && mapped_project && mapped_alloc;
        }
    }
}
