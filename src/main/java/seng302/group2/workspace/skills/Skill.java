package seng302.group2.workspace.skills;

import javafx.collections.ObservableList;
import org.w3c.dom.Element;
import seng302.group2.Global;
import seng302.group2.scenes.control.search.SearchResultCellNode;
import seng302.group2.scenes.sceneswitch.switchStrategies.workspace.SkillInformationSwitchStrategy;
import seng302.group2.util.reporting.ReportGenerator;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.workspace.Workspace;

import java.io.Serializable;
import java.util.*;

/**
 * A basic class to represent skills a person may have
 *
 * @author crw73
 */
public class Skill extends SaharaItem implements Serializable, Comparable<Skill> {
    private String shortName;
    private String description;

    /**
     * Basic Skill constructor
     */
    public Skill() {
        super("Untitled Skill");
        this.shortName = "Untitled Skill";
        this.description = "no description";

        setInformationSwitchStrategy(new SkillInformationSwitchStrategy());
    }

    /**
     * Returns the items held by the Skill, blank as the skill has no child items.
     * @return a blank hash set
     */
    @Override
    public Set<SaharaItem> getItemsSet() {
        return new HashSet<>();
    }

    /**
     * Basic Skill constructor with all fields
     *
     * @param shortName   A unique short name to identify a Skill
     * @param description The Description of a skill
     */
    public Skill(String shortName, String description) {
        // Initialize as a SaharaItem
        super(shortName);

        this.shortName = shortName;
        this.description = description;
        setInformationSwitchStrategy(new SkillInformationSwitchStrategy());
    }

    // <editor-fold defaultstate="collapsed" desc="Getters"> 

    /**
     * Gets a skills short name
     *
     * @return The short name of the skill
     */
    public String getShortName() {
        return this.shortName;
    }

    /**
     * Sets a skills short name
     *
     * @param shortName the short name to set
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    //</editor-fold>


    // <editor-fold defaultstate="collapsed" desc="Setters">

    /**
     * Gets a skills description
     *
     * @return The description of the skill
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets a skills description
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    //</editor-fold>

    /**
     * Gets the children of the SaharaItem
     *
     * @return The items of the SaharaItem
     */
    @Override
    public ObservableList<SaharaItem> getChildren() {
        return null;
    }


    /**
     * Compares the skill to another skill by their short names
     *
     * @param compareSkill The skill to compare to
     * @return The result of the string comparison between the teams' short names
     */
    @Override
    public int compareTo(Skill compareSkill) {
        String skill1ShortName = this.getShortName();
        String skill2ShortName = compareSkill.getShortName();
        return skill1ShortName.compareTo(skill2ShortName);
    }


    /**
     * Deletes the skill and removes it from any people who have the skill.
     * Cannot delete Product Owner or Scrum Master skills.
     */
    public void deleteSkill() {
        if (this.shortName.equals("Scrum Master") || this.shortName.equals("Product Owner")) {
            System.out.println("Can't delete this skill");
            return;
        }
        Command deleteSkill = new DeleteSkillCommand(this, Global.currentWorkspace);
        Global.commandManager.executeCommand(deleteSkill);
    }


    /**
     * Gets a list of people in the current workspace that have this skill
     *
     * @return People with the skill
     */
    public List<Person> getPeopleWithSkill() {
        List<Person> people = new ArrayList<>();
        for (Person person : Global.currentWorkspace.getPeople()) {
            if (person.getSkills().contains(this)) {
                people.add(person);
            }
        }
        return people;
    }


    /**
     * Creates a skill edit command and executes it with the Global Command Manager, updating
     * the skill with the new parameter values.
     *
     * @param newShortName   The new short name
     * @param newDescription The new description
     */
    public void edit(String newShortName, String newDescription) {
        Command edit = new SkillEditCommand(this, newShortName, newDescription);
        Global.commandManager.executeCommand(edit);
    }

    /**
     * Method for creating an XML element for the Skill within report generation
     * @return element for XML generation
     */
    @Override
    public Element generateXML() {
        Element skillElement = ReportGenerator.doc.createElement("skill");

        //WorkSpace Elements
        Element skillID = ReportGenerator.doc.createElement("ID");
        skillID.appendChild(ReportGenerator.doc.createTextNode(String.valueOf(id)));
        skillElement.appendChild(skillID);

        Element skillShortName = ReportGenerator.doc.createElement("shortname");
        skillShortName.appendChild(ReportGenerator.doc.createTextNode(shortName));
        skillElement.appendChild(skillShortName);

        Element skillDescription = ReportGenerator.doc.createElement("description");
        skillDescription.appendChild(ReportGenerator.doc.createTextNode(description));
        skillElement.appendChild(skillDescription);
        return skillElement;
    }

    /**
     * An overridden version for the String representation of a Skill
     *
     * @return The short name of the Skill
     */
    @Override
    public String toString() {
        return this.shortName;
    }



    /**
     * A command class that allows the executing and undoing of skill edits
     */
    private class SkillEditCommand implements Command {
        private Skill skill;

        private String shortName;
        private String description;
        private String oldShortName;
        private String oldDescription;

        private SkillEditCommand(Skill skill, String newShortName, String newDescription) {
            this.skill = skill;
            this.shortName = newShortName;
            this.description = newDescription;
            this.oldShortName = skill.shortName;
            this.oldDescription = skill.description;
        }

        /**
         * Executes/Redoes the changes of the skill edit
         */
        public void execute() {
            skill.shortName = shortName;
            skill.description = description;
            Collections.sort(Global.currentWorkspace.getSkills());
        }

        /**
         * Undoes the changes of the skill edit
         */
        public void undo() {
            skill.shortName = oldShortName;
            skill.description = oldDescription;
            Collections.sort(Global.currentWorkspace.getSkills());
        }

        /**
         * Searches the stateObjects to find an equal model class to map to
         * @param stateObjects A set of objects to search through
         * @return If the item was successfully mapped
         */
        @Override
        public boolean map(Set<SaharaItem> stateObjects) {
            boolean mapped = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(skill)) {
                    this.skill = (Skill) item;
                    mapped = true;
                }
            }
            return mapped;
        }
    }

    /**
     * A command class for allowing the deletion of Skills from a Workspace.
     */
    private class DeleteSkillCommand implements Command {
        private Skill skill;
        private List<Person> people;

        /**
         * Constructor for the skill deletion command
         * @param skill The skill to be deleted
         * @param ws The workspace to which the skill belonged
         */
        DeleteSkillCommand(Skill skill, Workspace ws) {
            this.skill = skill;
            this.people = skill.getPeopleWithSkill();
        }

        /**
         * Executes the skill deletion command
         */
        public void execute() {
            for (Person person : this.people) {
                person.getSkills().remove(skill);
            }
            Global.currentWorkspace.getSkills().remove(skill);
        }

        /**
         * Undoes the skill deletion command
         */
        public void undo() {
            for (Person person : this.people) {
                person.getSkills().add(skill);
            }
            Global.currentWorkspace.getSkills().add(skill);
        }

        /**
         * Searches the stateObjects to find an equal model class to map to
         * @param stateObjects A set of objects to search through
         * @return If the item was successfully mapped
         */
        @Override
        public boolean map(Set<SaharaItem> stateObjects) {
            boolean mapped = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(skill)) {
                    this.skill = (Skill) item;
                    mapped = true;
                }
            }
            return mapped;
        }
    }
}
