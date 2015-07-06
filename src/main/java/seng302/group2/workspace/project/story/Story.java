package seng302.group2.workspace.project.story;

import javafx.collections.ObservableList;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.w3c.dom.Element;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.sceneswitch.switchStrategies.workspace.project.StoryInformationSwitchStrategy;
import seng302.group2.util.reporting.ReportGenerator;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.story.acceptanceCriteria.AcceptanceCriteria;

import java.io.Serializable;
import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * An instance of a user story that is used to describe high-level requirements of a project
 * Created by swi67 on 6/05/15.
 */
public class Story extends TreeViewItem implements Serializable {
    /**
     * A comparator that returns the comparison of two story's priorities
     */
    public static Comparator<Story> StoryPriorityComparator = (story1, story2) -> {
        return story2.getPriority().compareTo(story1.getPriority());
    };
    /**
     * A comparator that returns the comparison of two story's short names
     */
    public static Comparator<Story> StoryNameComparator = (story1, story2) -> {
        return story1.getShortName().compareTo(story2.getShortName());
    };

    private String shortName;
    private String longName;
    private String description;
    private String creator;
    private Project project;
    private Integer priority;
    private Backlog backlog;
    private String estimate;
    private transient ObservableList<AcceptanceCriteria> acceptanceCriteria = observableArrayList();
    private List<AcceptanceCriteria> serializableAcceptanceCriteria = new ArrayList<>();

    /**
     * Basic Story constructor
     */
    public Story() {
        this.shortName = "Untitled Story";
        this.longName = "Untitled Story";
        this.description = "";
        this.creator = null;
        this.project = null;
        this.priority = 0;
        this.estimate = "-";

        setInformationSwitchStrategy(new StoryInformationSwitchStrategy());
    }

    @Override
    public Set<TreeViewItem> getItemsSet() {
        Set<TreeViewItem> items = new HashSet<>();
        items.addAll(acceptanceCriteria);
        return items;
    }

    /**
     * Story Constructor with all fields
     *
     * @param shortName   short name to identify the story
     * @param longName    long name
     * @param description description
     * @param creator     creator of the story
     * @param project     project the story belongs to
     * @param priority    the projects priority
     */
    public Story(String shortName, String longName, String description, String creator,
                 Project project, Integer priority) {
        this.shortName = shortName;
        this.longName = longName;
        this.description = description;
        this.creator = creator;
        this.project = project;
        this.priority = priority;
        this.estimate = "-";

        setInformationSwitchStrategy(new StoryInformationSwitchStrategy());
    }

    /**
     * Gets the short name of the story
     *
     * @return the short name
     */
    public String getShortName() {
        return this.shortName;
    }

    /**
     * Sets the short name of the story
     *
     * @param shortName short name to be set
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * Gets the long name of the story
     *
     * @return the long name
     */
    public String getLongName() {
        return this.longName;
    }

    /**
     * Sets the long name of the story
     *
     * @param longName long name to be set
     */
    public void setLongName(String longName) {
        this.longName = longName;
    }

    /**
     * Gets the description of the story
     *
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description of the story
     *
     * @param description description to be set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the person who created the story
     *
     * @return name of the person
     */
    public String getCreator() {
        return this.creator;
    }

    /**
     * Sets the person who created the story
     *
     * @param creator name of the person to be set.
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * Gets the priority of the project
     *
     * @return an integer representing the priority
     */
    public Integer getPriority() {
        return this.priority;
    }

    /**
     * Sets the priority of the story
     *
     * @param priority the priority
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * Gets the project this story belongs to.
     *
     * @return the project
     */
    public Project getProject() {
        return this.project;
    }

    /**
     * Sets the project the story belongs to
     *
     * @param project project to set to
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * Gets the backlog this story belongs to.
     *
     * @return the backlog
     */
    public Backlog getBacklog() {
        return this.backlog;
    }

    /**
     * Sets the backlog the story belongs to
     *
     * @param backlog the backlog to set to
     */
    public void setBacklog(Backlog backlog) {
        this.backlog = backlog;
    }

    /**
     * Gets the estimate value of this story
     */
    public String getEstimate() {
        return this.estimate;
    }

    /**
     * Sets the estimate value of this story
     *
     * @param estimate The new estimate value
     */
    public void setEstimate(String estimate) {
        this.estimate = estimate;
    }

    /**
     * Gets the acceptance criteria of this story
     */
    public ObservableList<AcceptanceCriteria> getAcceptanceCriteria() {
        return this.acceptanceCriteria;
    }

    /**
     * Gets the serializable AC's
     *
     * @return the serializable AC's
     */
    public List<AcceptanceCriteria> getSerializableAc() {
        return serializableAcceptanceCriteria;
    }

    /**
     * Adds an Acceptance Criteria to the story
     *
     * @param ac ac to be added
     */
    public void add(AcceptanceCriteria ac) {
        Command command = new AddAcceptanceCriteriaCommand(this, ac);
        Global.commandManager.executeCommand(command);
    }

    /**
     * Prepares a story to be serialized.
     */
    public void prepSerialization() {
        serializableAcceptanceCriteria.clear();
        for (AcceptanceCriteria ac : acceptanceCriteria) {
            this.serializableAcceptanceCriteria.add(ac);
        }
    }

    /**
     * Deserialization post-processing.
     */
    public void postSerialization() {
        acceptanceCriteria.clear();
        for (Object item : serializableAcceptanceCriteria) {
            this.acceptanceCriteria.add((AcceptanceCriteria) item);
        }
    }

    /**
     * Method for creating an XML element for the Story within report generation
     * @return element for XML generation
     */
    @Override
    public Element generateXML() {
        Element storyElement = ReportGenerator.doc.createElement("story");

        //WorkSpace Elements
        Element storyShortName = ReportGenerator.doc.createElement("identifier");
        storyShortName.appendChild(ReportGenerator.doc.createTextNode(shortName));
        storyElement.appendChild(storyShortName);

        Element storyLongName = ReportGenerator.doc.createElement("long-name");
        storyLongName.appendChild(ReportGenerator.doc.createTextNode(longName));
        storyElement.appendChild(storyLongName);

        Element storyDescription = ReportGenerator.doc.createElement("description");
        storyDescription.appendChild(ReportGenerator.doc.createTextNode(description));
        storyElement.appendChild(storyDescription);

        Element storyCreator = ReportGenerator.doc.createElement("creator");
        storyCreator.appendChild(ReportGenerator.doc.createTextNode(creator));
        storyElement.appendChild(storyCreator);

        Element storyPriority = ReportGenerator.doc.createElement("priority");
        storyPriority.appendChild(ReportGenerator.doc.createTextNode(priority.toString()));
        storyElement.appendChild(storyPriority);

        return storyElement;
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
     * An overridden version for the String representation of a Story
     *
     * @return The short name of the Story
     */
    @Override
    public String toString() {
        return this.shortName;
    }

    @Override
    public boolean equivalentTo(Object object) {
        if (!(object instanceof Story)) {
            return false;
        }
        if (object == this) {
            return true;
        }

        Story story = (Story)object;
        return new EqualsBuilder()
                .append(shortName, story.shortName)
                .append(longName, story.longName)
                .append(description, story.description)
                .append(project, story.project)
                .append(priority, story.priority)
                .append(backlog, story.backlog)
                .append(estimate, story.estimate)
                .append(creator, story.creator)
                .isEquals();
    }


    /**
     * Deletes the given acceptance criteria from the story
     * @param ac The acceptance criteria to delete
     */
    public void delete(AcceptanceCriteria ac) {
        ac.delete();
    }


    /**
     * Creates a Story edit command and executes it with the Global Command Manager, updating
     * the story with the new parameter values.
     *
     * @param newShortName   The new short name
     * @param newDescription The new description
     * @param newProject     The new project
     * @param newPriority    The new priority
     */
    public void edit(String newShortName, String newLongName, String newDescription,
                     Project newProject, Integer newPriority, Backlog newBacklog, String newEstimate) {
        Command relEdit = new StoryEditCommand(this, newShortName, newLongName,
                newDescription, newProject, newPriority, newBacklog, newEstimate);
        Global.commandManager.executeCommand(relEdit);
    }

    /**
     * Deletes a story from the given project.
     */
    public void deleteStory() {
        Command command = new DeleteStoryCommand(this);
        Global.commandManager.executeCommand(command);
    }


    /**
     * A command class that allows the executing and undoing of story edits
     */
    private class StoryEditCommand implements Command {
        private Story story;

        private String shortName;
        private String longName;
        private String description;
        private Project project;
        private Integer priority;
        private Backlog backlog;
        private String estimate;

        private String oldShortName;
        private String oldLongName;
        private String oldDescription;
        private Project oldProject;
        private Integer oldPriority;
        private Backlog oldBacklog;
        private String oldEstimate;

        private StoryEditCommand(Story story, String newShortName, String newLongName,
                                 String newDescription, Project newProject, Integer newPriority,
                                 Backlog newBacklog, String newEstimate) {
            this.story = story;

            this.shortName = newShortName;
            this.longName = newLongName;
            this.description = newDescription;
            this.project = newProject;
            this.priority = newPriority;
            this.backlog = newBacklog;
            this.estimate = newEstimate;

            this.oldShortName = story.shortName;
            this.oldLongName = story.longName;
            this.oldDescription = story.description;
            this.oldProject = story.project;
            this.oldPriority = story.priority;
            this.oldBacklog = story.backlog;
            this.oldEstimate = story.estimate;
        }

        /**
         * Executes/Redoes the changes of the story edit
         */
        public void execute() {
            story.shortName = shortName;
            story.longName = longName;
            story.description = description;
            story.project = project;
            story.priority = priority;
            story.backlog = backlog;
            story.estimate = estimate;
            Collections.sort(project.getUnallocatedStories(), Story.StoryNameComparator);
            if (backlog != null) {
                Collections.sort(backlog.getStories(), Story.StoryPriorityComparator);
            }

            /* If the story if being added to a backlog in the project, remove it from the
            unassigned stories.*/
            if (backlog != null && project != null) {
                project.getUnallocatedStories().remove(story);
            }
        }

        /**
         * Undoes the changes of the story edit
         */
        public void undo() {
            story.shortName = oldShortName;
            story.longName = oldLongName;
            story.description = oldDescription;
            story.project = oldProject;
            story.priority = oldPriority;
            story.backlog = oldBacklog;
            story.estimate = oldEstimate;
            Collections.sort(project.getUnallocatedStories(), Story.StoryNameComparator);
            Collections.sort(backlog.getStories(), Story.StoryPriorityComparator);

            /* If the story if being added back into a backlog in the project, remove it from the
            unassigned stories.*/
            if (oldBacklog != null && oldProject != null) {
                oldProject.getUnallocatedStories().remove(story);
            }
        }

        /**
         * Searches the stateObjects to find an equal model class to map to
         * @param stateObjects A set of objects to search through
         * @return If the item was successfully mapped
         */
        @Override
        public boolean map(Set<TreeViewItem> stateObjects) {
            boolean mapped_story = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(story)) {
                    this.story = (Story) item;
                    mapped_story = true;
                }
            }

            boolean mapped_project = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(project)) {
                    this.project = (Project) item;
                    mapped_project = true;
                }
            }
            boolean mapped_old_project = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(oldProject)) {
                    this.project = (Project) item;
                    mapped_old_project = true;
                }
            }

            boolean mapped_backlog = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(backlog)) {
                    this.backlog = (Backlog) item;
                    mapped_backlog = true;
                }
            }
            boolean mapped_old_backlog = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(oldBacklog)) {
                    this.backlog = (Backlog) item;
                    mapped_old_backlog = true;
                }
            }

            return mapped_backlog && mapped_project && mapped_story && mapped_old_backlog
                    && mapped_old_project;
        }
    }

    /**
     * A class for implementing story deletion in the Command undo/redo structure.
     */
    private class DeleteStoryCommand implements Command {
        private Story story;
        private Project proj;
        private Backlog backlog;

        /**
         * Contructor for a story deletion command.
         *
         * @param story The story to delete
         */
        DeleteStoryCommand(Story story) {
            this.story = story;
            this.proj = story.getProject();
            this.backlog = story.getBacklog();
        }

        /**
         * Executes the deletion of a story.
         */
        public void execute() {
            if (backlog != null) {
                backlog.getStories().remove(story);
            }
            else if (proj != null) {
                proj.getUnallocatedStories().remove(story);
            }
        }

        /**
         * Undoes the deletion of a story.
         */
        public void undo() {
            if (backlog != null) {
                backlog.getStories().add(story);
            }
            else if (proj != null) {
                proj.getUnallocatedStories().add(story);
            }
        }

        /**
         * Searches the stateObjects to find an equal model class to map to
         * @param stateObjects A set of objects to search through
         * @return If the item was successfully mapped
         */
        @Override
        public boolean map(Set<TreeViewItem> stateObjects) {
            boolean mapped_story = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equals(story)) {
                    this.story = (Story) item;
                    mapped_story = true;
                }
            }
            boolean mapped_project = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equals(proj)) {
                    this.proj = (Project) item;
                    mapped_project = true;
                }
            }
            boolean mapped_backlog = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equals(backlog)) {
                    this.backlog = (Backlog) item;
                    mapped_backlog = true;
                }
            }
            return mapped_backlog && mapped_project && mapped_story;
        }
    }

    private class AddAcceptanceCriteriaCommand implements Command {
        private Story story;
        private AcceptanceCriteria ac;

        AddAcceptanceCriteriaCommand(Story story, AcceptanceCriteria ac) {
            this.story = story;
            this.ac = ac;
        }

        public void execute() {
            story.getAcceptanceCriteria().add(ac);
            ac.setStory(story);
        }

        public void undo() {
            story.getAcceptanceCriteria().remove(ac);
            ac.setStory(null);
        }

        /**
         * Searches the stateObjects to find an equal model class to map to
         * @param stateObjects A set of objects to search through
         * @return If the item was successfully mapped
         */
        @Override
        public boolean map(Set<TreeViewItem> stateObjects) {
            boolean mapped_story = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equals(story)) {
                    this.story = (Story) item;
                    mapped_story = true;
                }
            }
            boolean mapped_ac = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equals(ac)) {
                    this.ac = (AcceptanceCriteria) item;
                    mapped_ac = true;
                }
            }
            return mapped_ac && mapped_story;
        }
    }
}
