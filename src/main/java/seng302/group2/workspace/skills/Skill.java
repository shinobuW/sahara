package seng302.group2.workspace.skills;

import javafx.collections.ObservableList;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.sceneswitch.switchStrategies.workspace.SkillInformationSwitchStrategy;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.allocation.Allocation;
import seng302.group2.workspace.person.Person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * A basic class to represent skills a person may have
 *
 * @author crw73
 */
public class Skill extends TreeViewItem implements Serializable, Comparable<Skill> {
    private String shortName;
    private String description;

    /**
     * Basic Skill constructor
     */
    public Skill() {
        super("unnamed");
        this.shortName = "unnamed";
        this.description = "no description";

        setInformationSwitchStrategy(new SkillInformationSwitchStrategy());
    }

    @Override
    public Set<TreeViewItem> getItemsSet() {
        return null;
    }

    /**
     * Basic Skill constructor with all fields
     *
     * @param shortName   A unique short name to identify a Skill
     * @param description The Description of a skill
     */
    public Skill(String shortName, String description) {
        // Initialize as a TreeViewItem
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
     * Gets the children of the TreeViewItem
     *
     * @return The items of the TreeViewItem
     */
    @Override
    public ObservableList<TreeViewItem> getChildren() {
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

        /*ArrayList<UndoableItem> undoActions = new ArrayList<>();
        if (!deletedSkill.getShortName().equals("Product Owner")
                && !deletedSkill.getShortName().equals("Scrum Master"))
        {
            for (Person personRemoveSkill : Global.currentWorkspace.getPeople())
            {
                if (personRemoveSkill.getSkills().contains(deletedSkill))
                {
                    undoActions.add(new UndoableItem(
                            deletedSkill,
                            new UndoRedoAction(
                                    UndoRedoPerformer.UndoRedoProperty.SKILL_DEL_PERSON,
                                    personRemoveSkill),
                            new UndoRedoAction(
                                    UndoRedoPerformer.UndoRedoProperty.SKILL_DEL_PERSON,
                                    personRemoveSkill)));
                    personRemoveSkill.removeSkillFromPerson(deletedSkill, false);
                }
            }

            undoActions.add(new UndoableItem(
                    deletedSkill,
                    new UndoRedoAction(
                            UndoRedoPerformer.UndoRedoProperty.SKILL_DEL,
                            deletedSkill),
                    new UndoRedoAction(
                            UndoRedoPerformer.UndoRedoProperty.SKILL_DEL,
                            deletedSkill)));
            Global.currentWorkspace.removeWithoutUndo(deletedSkill);


            if (undoActions.size() > 0)
            {
                Global.undoRedoMan.add(new UndoableItem(
                        deletedSkill,
                        new UndoRedoAction(
                                UndoRedoPerformer.UndoRedoProperty.SKILL_DEL_RECURSIVE,
                                undoActions),
                        new UndoRedoAction(
                                UndoRedoPerformer.UndoRedoProperty.SKILL_DEL_RECURSIVE,
                                undoActions)
                ));
            }
        }*/
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

        @Override
        public boolean map(Set<TreeViewItem> stateObjects) {
            boolean mapped = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equals(skill)) {
                    this.skill = (Skill) item;
                    mapped = true;
                }
            }
            return mapped;
        }
    }

    private class DeleteSkillCommand implements Command {
        private Skill skill;
        private List<Person> people;

        DeleteSkillCommand(Skill skill, Workspace ws) {
            this.skill = skill;
            this.people = skill.getPeopleWithSkill();
        }

        public void execute() {
            for (Person person : this.people) {
                person.getSkills().remove(skill);
            }
            Global.currentWorkspace.getSkills().remove(skill);
        }

        public void undo() {
            for (Person person : this.people) {
                person.getSkills().add(skill);
            }
            Global.currentWorkspace.getSkills().add(skill);
        }

        @Override
        public boolean map(Set<TreeViewItem> stateObjects) {
            boolean mapped = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equals(skill)) {
                    this.skill = (Skill) item;
                    mapped = true;
                }
            }
            return mapped;
        }
    }
}
