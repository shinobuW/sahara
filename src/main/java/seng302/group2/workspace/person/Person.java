/*
 * SENG302 Group 2
 */
package seng302.group2.workspace.person;

import javafx.collections.ObservableList;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.sceneswitch.switchStrategies.workspace.PersonInformationSwitchStrategy;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;
import sun.reflect.generics.tree.Tree;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * A basic class to represent a Person in the real world.
 *
 * @author crw73
 */
public class Person extends TreeViewItem implements Serializable, Comparable<Person> {
    private String shortName;
    private String firstName;
    private String lastName;
    private String email;
    private String description;
    private LocalDate birthDate = null;
    private transient ObservableList<Skill> skills = observableArrayList();
    private List<Skill> serializableSkills = new ArrayList<>();
    private Team team;
    private Role role;


    /**
     * Basic Person constructor
     */
    public Person() {
        super("unnamed");
        this.shortName = "unnamed";
        this.firstName = "firstName";
        this.lastName = "lastName";
        this.email = "";
        this.description = "";

        for (TreeViewItem team : Global.currentWorkspace.getTeams()) {
            Team castedTeam = (Team) team;
            if (castedTeam.isUnassignedTeam()) {
                this.team = castedTeam;
                break; // Only one unassigned team
            }
        }

        this.birthDate = null;

        setInformationSwitchStrategy(new PersonInformationSwitchStrategy());
    }


    /**
     * Basic person constructor with all fields
     *
     * @param shortName   A unique short name to identify a Person
     * @param firstName   The first name of the Person
     * @param lastName    The last name of the Person
     * @param email       The email of the Person
     * @param birthDate   The date of birth of the Person
     * @param description A description of the Person
     */
    public Person(String shortName, String firstName, String lastName, String email,
                  String description, LocalDate birthDate) {
        // Initialize as a TreeViewItem
        super(shortName);

        this.setShortName(shortName);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setDescription(description);
        this.setBirthDate(birthDate);
        for (TreeViewItem team : Global.currentWorkspace.getTeams()) {
            Team castedTeam = (Team) team;
            if (castedTeam.isUnassignedTeam()) {
                this.team = castedTeam;
                break; // Only one unassigned team
            }
        }

        setInformationSwitchStrategy(new PersonInformationSwitchStrategy());
    }


    /**
     * Returns a set of all children items inside a person
     * @return A set of all children items inside this person
     */
    public Set<TreeViewItem> getItemsSet() {
        Set<TreeViewItem> items = new HashSet<>();

        for (Skill skill : skills) {
            items.addAll(skill.getItemsSet());
        }

        return items;
    }


    // <editor-fold defaultstate="collapsed" desc="Getters"> 

    /**
     * Gets the person's short name
     *
     * @return The short name of the person
     */
    public String getShortName() {
        return this.shortName;
    }

    /**
     * Sets the person's short name
     *
     * @param shortName the short name to set
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * Gets the person's first name
     *
     * @return The first name of the person
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Sets the person's first name
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the person's last name
     *
     * @return The last name of the person
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Sets the person's last name
     *
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the person's email
     *
     * @return The email of the person
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the person's email
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the person's description
     *
     * @return The description of the person
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the person's description
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the person's birth date.
     *
     * @return The birth date of the person
     */
    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    //</editor-fold>


    // <editor-fold defaultstate="collapsed" desc="Setters">

    /**
     * Sets the person's birth date
     *
     * @param birthDate the birth date to set
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Gets the person's current team's name.
     *
     * @return The name of the team the person is currently in
     */
    public String getTeamName() {
        if (this.team == null) {
            return "Unassigned";
        }
        else {
            return this.team.getShortName();
        }
    }

    /**
     * Gets the ArrayList of serializable skills
     *
     * @return the serializable skills
     */
    public List<Skill> getSerializableSkills() {
        return serializableSkills;
    }

    /**
     * Gets the person's team
     *
     * @return The person's team
     */
    public Team getTeam() {
        return this.team;
    }

    /**
     * Sets the person's current team
     *
     * @param team the team the person has been added too
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * Gets the person's current role.
     *
     * @return The current role
     */
    public Role getRole() {
        return this.role;
    }

    /**
     * Sets the person's current team
     *
     * @param role the role to set
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets the person's list of Skills.
     *
     * @return The skills associated with a person
     */
    public ObservableList<Skill> getSkills() {
        /*this.serializableSkills.clear();
        for (Object item : this.skills)
        {
            this.serializableSkills.add((Skill)item);
        }*/
        return this.skills;
    }

    //</editor-fold>

    /**
     * Prepares a person to be serialized.
     */
    public void prepSerialization() {
        serializableSkills.clear();
        for (Object item : skills) {
            this.serializableSkills.add((Skill) item);
        }
    }


    /**
     * Deserialization post-processing.
     */
    public void postSerialization() {
        skills.clear();
        for (Object item : serializableSkills) {
            this.skills.add((Skill) item);
        }
    }

    /**
     * Gets the persons birth date as a string
     *
     * @return The persons birth date as a string
     */
    public String getDateString() {
        if (birthDate == null) {
            return "";
        }
        else {
            try {
                return this.getBirthDate().format(Global.dateFormatter);
                //return Global.datePattern.format(this.getBirthDate());
            }
            catch (Exception e) {
                System.out.println("Error parsing date");
                return "";
            }
        }
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
     * Compares the person to another person based on their short names
     *
     * @param comparePerson The person to compare to
     * @return The string comparison result of the peoples' short names
     */
    @Override
    public int compareTo(Person comparePerson) {
        String pers1ShortName = this.getShortName();
        String pers2ShortName = comparePerson.getShortName();
        return pers1ShortName.compareTo(pers2ShortName);
    }


    /**
     * Deletes the person and removes them from team if they are in one.
     */
    public void deletePerson() {
        Command deletePers = new DeletePersonCommand(this);
        Global.commandManager.executeCommand(deletePers);
    }


    /**
     * Creates a Person edit command and executes it with the Global Command Manager, updating
     * the person with the new parameter values.
     *
     * @param newShortName   The persons new shortName
     * @param newFirstName   The persons new First name
     * @param newLastName    The perosns new Last name
     * @param newEmail       The persons new email address
     * @param newBirthDate   The persons new birth date
     * @param newDescription The persons new description
     * @param newSkills      the persons new list of skills
     * @param newTeam        the persons new team
     */
    public void edit(String newShortName, String newFirstName, String newLastName,
                     String newEmail, LocalDate newBirthDate, String newDescription,
                     Team newTeam, ObservableList newSkills) {
        Command persEdit = new PersonEditCommand(this, newShortName, newFirstName, newLastName,
                newEmail, newBirthDate, newDescription, newTeam, newSkills);
        Global.commandManager.executeCommand(persEdit);
    }

    /**
     * An overridden version for the String representation of a Person
     *
     * @return The short name of the Person
     */
    @Override
    public String toString() {
        return this.shortName;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Person)) {
            return false;
        }
        if (object == this) {
            return true;
        }
        Person person = (Person)object;
        return new EqualsBuilder()
                .append(shortName, person.shortName)
                .append(firstName, person.firstName)
                .append(lastName, person.lastName)
                .append(email, person.email)
                .append(birthDate, person.birthDate)
                .append(description, person.description)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                append(shortName)
                .append(firstName)
                .append(lastName)
                .append(email)
                .append(birthDate)
                .append(description)
                .toHashCode();
                //Ignoring team and skills
    }

    /**
     * A command class that allows the executing and undoing of project edits
     */
    private class PersonEditCommand implements Command {
        private Person person;
        private String shortName;
        private String firstName;
        private String lastName;
        private String email;
        private LocalDate birthDate;
        private String description;
        private Team team;
        private ObservableList<Skill> skills;

        private String oldShortName;
        private String oldFirstName;
        private String oldLastName;
        private String oldNewEmail;
        private LocalDate oldBirthDate;
        private String oldDescription;
        private Team oldTeam;
        private ObservableList<Skill> oldSkills;

        protected PersonEditCommand(Person person, String newShortName, String newFirstName,
                                    String newLastName, String newEmail, LocalDate newBirthDate,
                                    String newDescription, Team newTeam, ObservableList<Skill> newSkills) {
            this.person = person;

            this.shortName = newShortName;
            this.firstName = newFirstName;
            this.lastName = newLastName;
            this.email = newEmail;
            this.birthDate = newBirthDate;
            this.description = newDescription;
            this.team = newTeam;
            this.skills = newSkills;

            this.oldShortName = person.shortName;
            this.oldFirstName = person.firstName;
            this.oldLastName = person.lastName;
            this.oldNewEmail = person.email;
            this.oldBirthDate = person.birthDate;
            this.oldDescription = person.description;
            this.oldTeam = person.team;
            this.oldSkills = person.skills;
        }

        /**
         * Executes/Redoes the changes of the person edit
         */
        public void execute() {
            person.shortName = shortName;
            person.firstName = firstName;
            person.lastName = lastName;
            person.email = email;
            person.birthDate = birthDate;
            person.description = description;
            person.team = team;
            person.skills = skills;
            Collections.sort(Global.currentWorkspace.getPeople());
        }

        /**
         * Undoes the changes of the person edit
         */
        public void undo() {
            person.shortName = oldShortName;
            person.firstName = oldFirstName;
            person.lastName = oldLastName;
            person.email = oldNewEmail;
            person.birthDate = oldBirthDate;
            person.description = oldDescription;
            person.team = oldTeam;
            person.skills = oldSkills;
            Collections.sort(Global.currentWorkspace.getPeople());
        }

        /**
         * Searches the stateObjects to find an equal model class to map to
         * @param stateObjects A set of objects to search through
         * @return If the item was successfully mapped
         */
        @Override
        public boolean map(Set<TreeViewItem> stateObjects) {
            boolean mapped = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equals(person)) {
                    this.person = (Person) item;
                    mapped = true;
                }
            }
            boolean mapped_team = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equals(team)) {
                    this.team = (Team) item;
                    mapped_team = true;
                }
            }
            boolean mapped_old_team = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equals(oldTeam)) {
                    this.oldTeam = (Team) item;
                    mapped_old_team = true;
                }
            }

            for (Skill skill : skills) {
                for (TreeViewItem item : stateObjects) {
                    if (item.equals(skill)) {
                        skill = (Skill) item;
                    }
                }
            }

            for (Skill skill : oldSkills) {
                for (TreeViewItem item : stateObjects) {
                    if (item.equals(skill)) {
                        skill = (Skill) item;
                    }
                }
            }

            return mapped && mapped_old_team && mapped_team;
        }
    }

    private class DeletePersonCommand implements Command {
        private Person person;
        private Team team;

        DeletePersonCommand(Person person) {
            this.person = person;
            this.team = person.getTeam();
        }

        public void execute() {
            team.getPeople().remove(person);
            person.setTeam(null);
            Global.currentWorkspace.getPeople().remove(person);
        }

        public void undo() {
            team.getPeople().add(person);
            person.setTeam(team);
            Global.currentWorkspace.getPeople().add(person);
        }

        /**
         * Searches the stateObjects to find an equal model class to map to
         * @param stateObjects A set of objects to search through
         * @return If the item was successfully mapped
         */
        @Override
        public boolean map(Set<TreeViewItem> stateObjects) {
            boolean mapped_person = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equals(person)) {
                    this.person = (Person) item;
                    mapped_person = true;
                }
            }
            boolean mapped_team = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equals(team)) {
                    this.team = (Team) item;
                    mapped_team = true;
                }
            }
            return mapped_person && mapped_team;
        }
    }
}
