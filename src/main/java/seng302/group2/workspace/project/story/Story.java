package seng302.group2.workspace.project.story;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import org.w3c.dom.Element;
import seng302.group2.Global;
import seng302.group2.scenes.sceneswitch.switchStrategies.workspace.project.StoryInformationSwitchStrategy;
import seng302.group2.util.reporting.ReportGenerator;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.sprint.Sprint;
import seng302.group2.workspace.project.story.acceptanceCriteria.AcceptanceCriteria;
import seng302.group2.workspace.project.story.estimation.EstimationScalesDictionary;
import seng302.group2.workspace.project.story.tasks.Task;

import java.io.Serializable;
import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * An instance of a user story that is used to describe high-level requirements of a project
 * Created by swi67 on 6/05/15.
 */
public class Story extends SaharaItem implements Serializable {
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
    private STORYCOLOUR colour;
    private Integer priority;
    private Backlog backlog;
    private Project project;
    private Sprint sprint;
    private String estimate = EstimationScalesDictionary.getScaleValue(EstimationScalesDictionary.DefaultValues.NONE);
    private boolean ready = false;
    private transient ObservableList<AcceptanceCriteria> acceptanceCriteria = observableArrayList();
    private List<AcceptanceCriteria> serializableAcceptanceCriteria = new ArrayList<>();
    //private Set<Story> dependentOnThis = new HashSet<>();
    private Set<Story> dependentOn = new HashSet<>();
    private transient ObservableList<Task> tasks = observableArrayList();
    private List<Task> serializableTasks = new ArrayList<>();


    // Observable lists for use on the scrumboard
    public ObservableList<Task> todoTasks = FXCollections.observableArrayList();
    public ObservableList<Task> inProgTasks = FXCollections.observableArrayList();
    public ObservableList<Task> verifyTasks = FXCollections.observableArrayList();
    public ObservableList<Task> completedTasks = FXCollections.observableArrayList();


    public static String stateReady = "Ready";
    public static String stateNotReady = "Not Ready";

    public static Color greenHighlight = Color.color(0.4, 1, 0, 0.4);
    public static Color orangeHighlight = Color.color(1, 0.6, 0, 0.4);
    public static Color redHighlight = Color.color(1, 0, 0, 0.4);

    /**
     * Basic Story constructor
     */
    public Story() {
        super("Untitled Story");
        this.shortName = "Untitled Story";
        this.longName = "Untitled Story";
        this.description = "";
        this.creator = null;
        this.priority = 0;
        this.project = null;
        this.colour = STORYCOLOUR.DEFAULT;

        setInformationSwitchStrategy(new StoryInformationSwitchStrategy());
    }

    /**
     * Gets the set of SaharaItems 'belonging' to the Story (It's Acceptance Criteria).
     * @return A set of SaharaItems belonging to the story
     */
    @Override
    public Set<SaharaItem> getItemsSet() {
        Set<SaharaItem> items = new HashSet<>();
        items.addAll(acceptanceCriteria);
        for (AcceptanceCriteria ac : acceptanceCriteria) {
            items.addAll(ac.getItemsSet());
        }
        items.addAll(tasks);
        for (Task task : tasks) {
            items.addAll(task.getItemsSet());
        }
        return items;
    }

    /**
     * Story Constructor with all fields. Ready state set to false
     *
     * @param shortName   short name to identify the story
     * @param longName    long name
     * @param description description
     * @param creator     creator of the story
     * @param project     project the story belongs to
     * @param priority    the projects priority
     *
     */
    public Story(String shortName, String longName, String description, String creator,
                 Project project, Integer priority) {
        super(shortName);
        this.shortName = shortName;
        this.longName = longName;
        this.description = description;
        this.creator = creator;
        this.priority = priority;
        this.project = project;
        this.ready = false;
        this.colour = STORYCOLOUR.DEFAULT;

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
     * Sets the project of the story
     *
     * @param project the project
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
     * Gets String representation of the stories ready state.
     *
     * @return the readyString
     */
    public String getReadyString() {
        if (ready) {
            return "Ready";
        }
        return "Not Ready";
    }

    /**
     * Gets the sprint this story belongs to.
     *
     * @return the project
     */
    public Sprint getSprint() {
        return this.sprint;
    }

    /**
     * Sets the sprint the story belongs to
     *
     * @param sprint sprint to set to
     */
    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    /**
     * Gets the stories the current story is dependant of
     *
     * @return the stories, the current story is dependant of
     */
    public Set<Story> getDependentOn() {
        return this.dependentOn;
    }

    /**
     * Sets the highlight colour of the story to its appropriate colour according to the criteria
     */
    public void setHighlightColour() {
        Boolean red = false;
        for (Story story : this.dependentOn) {
            if (story.priority < this.priority) {
                red = true;
            }
        }
        if (red) {
            this.colour = STORYCOLOUR.RED;
        }
        else if (this.ready) {
            this.colour = STORYCOLOUR.GREEN;
        }
        else if (this.estimate.equals(EstimationScalesDictionary.getScaleValue(EstimationScalesDictionary
                .DefaultValues.NONE)) && this.acceptanceCriteria.size() > 0) {
            this.colour = STORYCOLOUR.ORANGE;
        }
        else {
            this.colour = STORYCOLOUR.DEFAULT;
        }
    }

    /**
     * Converts a JavaFX colour into an html/web colour string (including opacity)
     * @param color The colour to convert
     * @return The web equivalent of the colour
     */
    public static String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X%02X",
                (int)(color.getRed() * 255),
                (int)(color.getGreen() * 255),
                (int)(color.getBlue() * 255),
                (int)(color.getOpacity() * 255));
    }

    /**
     * Gets the string form of the story colour t
     * @return the colour of the story in highlight mode
     */
    public String getColour() {
        this.setHighlightColour();
        switch (colour) {
            case GREEN:
                return toRGBCode(greenHighlight);//"#aaffaa";
            case ORANGE:
                return toRGBCode(orangeHighlight);
            case RED:
                return toRGBCode(redHighlight);//"#fd4949";
            case DEFAULT:
                return "transparent";
            default:
                return "transparent";
        }
    }

    /**
     * Gets the dependentOnThis this story has.
     *
     * @return the set of dependentOnThis
     */
    public Set<Story> getDependentOnThis() {
        Set<Story> stories = new HashSet<>();
        for (SaharaItem item : Global.currentWorkspace.getItemsSet()) {
            if (item instanceof Story && ((Story) item).dependentOn.contains(this)) {
                stories.add((Story)item);
            }
        }
        return stories;
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
     *
     * @return The estimate value of the story.
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
     * Gets the ready state of the story
     * @return the ready state
     */
    public boolean getReady() {
        return ready;
    }


    /**
     * Traverses the dependencies of the story (DFS) and returns if there are any cyclic dependencies
     * @return Whether or not the story has cyclic dependencies
     */
    public boolean hasDependencyCycle() {
        // http://www.geeksforgeeks.org/detect-cycle-in-a-graph/
        Set<Story> visited = new HashSet<>();
        Set<Story> backTrack = new HashSet<>();

        // Call the recursive helper function to detect cycle in different DFS trees
        for (Story story : dependentOn) {
            if (recursiveCycleCheck(story, visited, backTrack)) {
                return true;
            }
        }

        return false;
    }


    private boolean recursiveCycleCheck(Story story, Set<Story> visited, Set<Story> backTrack) {
        if (!visited.contains(story)) {
            // Mark the current node as visited and part of recursion (backTrack) stack
            visited.add(story);
            backTrack.add(story);

            // Recursively traverse for all the vertices adjacent to this vertex
            for (Story adjStory : story.dependentOn) {
                if (!visited.contains(adjStory) && recursiveCycleCheck(adjStory, visited, backTrack)) {
                    return true;
                }
                else if (backTrack.contains(adjStory)) {
                    return true;
                }
            }
        }

        backTrack.remove(story);  // remove the vertex from recursion (backTrack) stack
        return false;
    }


    /**
     * Checks if a cycle will be added if a dependency is to be created with the given story added to the stories'
     * dependencies
     * @param story The story to check the addition of. Presumed to have no cycles to start with.
     * @return If the added story adds a cycle to the dependencies of this story.
     */
    public boolean checkAddCycle(Story story) {
        if (this.dependentOn.contains(story)) {
            return true; // Assumed that the story has no cycles to start
        }
        this.dependentOn.add(story);
        boolean cycle = hasDependencyCycle();
        this.dependentOn.remove(story);
        return cycle;
    }

    private boolean cycleCheckTraverse(Set<Story> traversedItems) {
        if (!traversedItems.add(this)) {
            // Item wasn't added to set (duplicate)
            System.out.println("Can't add " + this + " to " + traversedItems);
            return true;  // There was a cycle
        }
        for (Story dependent : getDependentOn()) {
            dependent.cycleCheckTraverse(traversedItems);
        }
        return false;
    }


    /**
     * A method to return the ready state of a story as a string
     * @return The ready state of a story as a string
     */
    public String getReadyState() {
        if (ready) {
            return stateReady;
        }
        return stateNotReady;
    }

    /**
     * Gets the tasks of this story
     *
     * @return the tasks of the story
     */
    public ObservableList<Task> getTasks() {
        this.serializableTasks.clear();
        for (Object item : this.tasks) {
            this.serializableTasks.add((Task) item);
        }
        return this.tasks;
    }

    /**
     * Gets the serializable tasks
     *
     * @return the serializable tasks
     */
    public List<Task> getSerializableTasks() {
        return serializableTasks;
    }

    /**
     * Adds a task to the story
     *
     * @param task task to be added
     */
    public void add(Task task) {
        Command command = new AddTaskCommand(this, task);
        Global.commandManager.executeCommand(command);
    }
    /**
     * Sets the story's ready state to the given boolean
     * @param ready The boolean state to set
     */
    public void setReady(boolean ready) {
        this.ready = ready;
    }

    /**
     * Gets the acceptance criteria of this story
     *
     * @return the acceptance criteria of the story
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
        serializableTasks.clear();
        for (Task task : tasks) {
            task.prepSerialization();
            this.serializableTasks.add(task);
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
        tasks.clear();
        for (Task task : serializableTasks) {
            task.postSerialization();
            this.tasks.add(task);
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
        Element storyID = ReportGenerator.doc.createElement("ID");
        storyID.appendChild(ReportGenerator.doc.createTextNode(String.valueOf(id)));
        storyElement.appendChild(storyID);

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

        Element storyEstimate = ReportGenerator.doc.createElement("estimate");
        storyEstimate.appendChild(ReportGenerator.doc.createTextNode(estimate));
        storyElement.appendChild(storyEstimate);

        Element storyReady = ReportGenerator.doc.createElement("ready_state");
        if (ready) {
            storyReady.appendChild(ReportGenerator.doc.createTextNode("Ready"));
        }
        else {
            storyReady.appendChild(ReportGenerator.doc.createTextNode("Not Ready"));
        }
        storyElement.appendChild(storyReady);

        Element acceptanceCriteriaElement = ReportGenerator.doc.createElement("story-acceptance-criteria");
        for (AcceptanceCriteria acceptanceCriteria : this.acceptanceCriteria) {
            Element acElement = acceptanceCriteria.generateXML();
            acceptanceCriteriaElement.appendChild(acElement);
        }
        storyElement.appendChild(acceptanceCriteriaElement);

        Element dependenciesElement = ReportGenerator.doc.createElement("story-dependencies");
        for (Story dependency : this.dependentOn) {
            Element dependencyElement = ReportGenerator.doc.createElement("dependency");
            dependencyElement.appendChild(ReportGenerator.doc.createTextNode(String
                    .valueOf(dependency.getShortName())));
            dependenciesElement.appendChild(dependencyElement);
        }
        storyElement.appendChild(dependenciesElement);

        Element tasksElement = ReportGenerator.doc.createElement("tasks");
        for (Task task : this.tasks) {
            Element taskElement = task.generateXML();
            tasksElement.appendChild(taskElement);
        }
        storyElement.appendChild(tasksElement);

        return storyElement;
    }

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
     * An overridden version for the String representation of a Story
     *
     * @return The short name of the Story
     */
    @Override
    public String toString() {
        return this.shortName;
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
     * @param newLongName    The new long name
     * @param newDescription The new description
     * @param newProject     The new project
     * @param newPriority    The new priority
     * @param newBacklog     The new backlog
     * @param newEstimate    The new estimate
     * @param newReady       The new ready state
     * @param newDependentOn The new dependent ons
     */
    public void edit(String newShortName, String newLongName, String newDescription,
                     Project newProject, Integer newPriority, Backlog newBacklog, String newEstimate,
                     boolean newReady, List<Story> newDependentOn) {
        Command relEdit = new StoryEditCommand(this, newShortName, newLongName,
                newDescription, newProject, newPriority, newBacklog, newEstimate, newReady, newDependentOn);

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
        private boolean ready;
        private Collection<Story> dependentOn;

        private String oldShortName;
        private String oldLongName;
        private String oldDescription;
        private Project oldProject;
        private Integer oldPriority;
        private Backlog oldBacklog;
        private String oldEstimate;
        private boolean oldReady;
        private Collection<Story> oldDependentOn;
        
        /**
         * Constructor for the Story Edit command.
         * @param story The story to be edited
         * @param newShortName The new short name for the story
         * @param newLongName The new long name for the story
         * @param newDescription The new description of the story
         * @param newProject The new project to which the story belongs
         * @param newPriority The new priority value for the story
         * @param newBacklog The new backlog to which the story belongs
         * @param newEstimate The new estimate value for the story
         * @param newReady The story's new readiness state
         * @param newDependentOn The new list of stories the story is dependant on
         */
        private StoryEditCommand(Story story, String newShortName, String newLongName,
                                 String newDescription, Project newProject, Integer newPriority,
                                 Backlog newBacklog, String newEstimate, boolean newReady, List<Story> newDependentOn) {
            this.story = story;

            this.shortName = newShortName;
            this.longName = newLongName;
            this.description = newDescription;
            this.project = newProject;
            this.priority = newPriority;
            this.backlog = newBacklog;
            this.estimate = newEstimate;
            this.ready = newReady;
            this.dependentOn = new HashSet<>();
            this.dependentOn.addAll(newDependentOn);

            this.oldShortName = story.shortName;
            this.oldLongName = story.longName;
            this.oldDescription = story.description;
            this.oldPriority = story.priority;
            this.oldProject = story.project;
            this.oldBacklog = story.backlog;
            this.oldEstimate = story.estimate;
            this.oldReady = story.ready;
            this.oldDependentOn = new HashSet<>();
            this.oldDependentOn.addAll(story.dependentOn);

            if (backlog == null) {
                estimate = EstimationScalesDictionary.getScaleValue(EstimationScalesDictionary.DefaultValues.NONE);
                ready = false;
            }
            if (estimate.equals(EstimationScalesDictionary.getScaleValue(
                    EstimationScalesDictionary.DefaultValues.NONE))) {
                ready = false;
            }
        }

        /**
         * Executes/Redoes the changes of the story edit
         */
        public void execute() {
            story.shortName = shortName;
            story.longName = longName;
            story.description = description;
            story.priority = priority;
            story.backlog = backlog;
            story.project = project;
            story.estimate = estimate;
            story.ready = ready;


            story.dependentOn.clear();
            story.dependentOn.addAll(dependentOn);

            Collections.sort(project.getUnallocatedStories(), Story.StoryNameComparator);
            if (backlog != null) {
                Collections.sort(backlog.getStories(), Story.StoryPriorityComparator);
            }

            /* If the story if being added to a backlog in the project, remove it from the unassigned stories.*/
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
            story.priority = oldPriority;
            story.backlog = oldBacklog;
            story.project = oldProject;
            story.estimate = oldEstimate;
            story.ready = oldReady;


            story.dependentOn.clear();
            story.dependentOn.addAll(oldDependentOn);

            Collections.sort(project.getUnallocatedStories(), Story.StoryNameComparator);
            Collections.sort(backlog.getStories(), Story.StoryPriorityComparator);

            /* If the story if being added back into a backlog in the project, remove it from the unassigned stories.*/
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
        public boolean map(Set<SaharaItem> stateObjects) {
            boolean mapped_story = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(story)) {
                    this.story = (Story) item;
                    mapped_story = true;
                }
            }

            boolean mapped_project = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(project)) {
                    this.project = (Project) item;
                    mapped_project = true;
                }
            }
            boolean mapped_old_project = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(oldProject)) {
                    this.project = (Project) item;
                    mapped_old_project = true;
                }
            }

            boolean mapped_backlog = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(backlog)) {
                    this.backlog = (Backlog) item;
                    mapped_backlog = true;
                }
            }
            boolean mapped_old_backlog = false;
            for (SaharaItem item : stateObjects) {
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
        public boolean map(Set<SaharaItem> stateObjects) {
            boolean mapped_story = false;
            for (SaharaItem item : stateObjects) {
                if (item.equals(story)) {
                    this.story = (Story) item;
                    mapped_story = true;
                }
            }
            boolean mapped_project = false;
            for (SaharaItem item : stateObjects) {
                if (item.equals(proj)) {
                    this.proj = (Project) item;
                    mapped_project = true;
                }
            }
            boolean mapped_backlog = false;
            for (SaharaItem item : stateObjects) {
                if (item.equals(backlog)) {
                    this.backlog = (Backlog) item;
                    mapped_backlog = true;
                }
            }
            return mapped_backlog && mapped_project && mapped_story;
        }
    }

    /**
     * Command to add and remove acceptance criteria
     */
    private class AddAcceptanceCriteriaCommand implements Command {
        private Story story;
        private AcceptanceCriteria ac;

        /**
         * Constructor for the acceptance criteria addition command
         * @param story The story to which the acceptance criteria is to be added
         * @param ac The acceptance criteria to be added
         */
        AddAcceptanceCriteriaCommand(Story story, AcceptanceCriteria ac) {
            this.story = story;
            this.ac = ac;
        }

        /**
         * Executes the acceptance criteria addition command
         */
        public void execute() {
            story.getAcceptanceCriteria().add(ac);
            ac.setStory(story);
        }

        /**
         * Undoes the acceptance criteria addition command
         */
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
        public boolean map(Set<SaharaItem> stateObjects) {
            boolean mapped_story = false;
            for (SaharaItem item : stateObjects) {
                if (item.equals(story)) {
                    this.story = (Story) item;
                    mapped_story = true;
                }
            }
            boolean mapped_ac = false;
            for (SaharaItem item : stateObjects) {
                if (item.equals(ac)) {
                    this.ac = (AcceptanceCriteria) item;
                    mapped_ac = true;
                }
            }
            return mapped_ac && mapped_story;
        }
    }

    /**
     * Command to add and remove acceptance criteria
     */
    private class AddTaskCommand implements Command {
        private Story story;
        private Task task;

        /**
         * Constructor for the acceptance criteria addition command
         * @param story The story to which the acceptance criteria is to be added
         * @param task The acceptance criteria to be added
         */
        AddTaskCommand(Story story, Task task) {
            this.story = story;
            this.task = task;
        }

        /**
         * Executes the acceptance criteria addition command
         */
        public void execute() {
            story.getTasks().add(task);
        }

        /**
         * Undoes the acceptance criteria addition command
         */
        public void undo() {
            story.getTasks().remove(task);
        }

        /**
         * Searches the stateObjects to find an equal model class to map to
         * @param stateObjects A set of objects to search through
         * @return If the item was successfully mapped
         */
        @Override
        public boolean map(Set<SaharaItem> stateObjects) {
            boolean mapped_story = false;
            for (SaharaItem item : stateObjects) {
                if (item.equals(story)) {
                    this.story = (Story) item;
                    mapped_story = true;
                }
            }
            boolean mapped_ac = false;
            for (SaharaItem item : stateObjects) {
                if (item.equals(task)) {
                    this.task = (Task) item;
                    mapped_ac = true;
                }
            }
            return mapped_ac && mapped_story;
        }
    }

    /**
     * enum used for colour setter
     */
    public enum STORYCOLOUR {
        GREEN,
        ORANGE,
        RED,
        DEFAULT
    }
}
