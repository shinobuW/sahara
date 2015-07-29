package seng302.group2.workspace.project.sprint;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.w3c.dom.Element;
import seng302.group2.Global;
import seng302.group2.scenes.sceneswitch.switchStrategies.workspace.project.SprintInformationSwitchStrategy;
import seng302.group2.util.reporting.ReportGenerator;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.categories.subCategory.project.task.TaskCategory;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.release.Release;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.tasks.Task;
import seng302.group2.workspace.team.Team;

import java.time.LocalDate;
import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * A class to hold information around scrum sprints
 */
public class Sprint extends SaharaItem {

    private Backlog backlog = null;
    private Team team = null;
    private Release release = null;
    private transient ObservableList<Story> stories = observableArrayList();
    private List<Story> serializableStories = new ArrayList<>();

    private String goal = "Untitled Sprint/Goal";
    private String longName = "Untitled Sprint/Goal";
    private String description = "";
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = LocalDate.now().plusWeeks(2);
    private transient ObservableList<Task> unallocatedTasks = observableArrayList();
    private List<Task> serializableTasks = new ArrayList<>();

    private transient TaskCategory tasksCategory = new TaskCategory(this);


    /**
     * Basic constructor
     */
    public Sprint() {
        super("Untitled Sprint");

        setInformationSwitchStrategy(new SprintInformationSwitchStrategy());
    }

    /**
     * Complete Constructor
     *
     * @param longName The name of the sprint
     * @param goal The goal of the sprint
     * @param description The description of the sprint
     * @param startDate The start date of the sprint
     * @param endDate The end date of the sprint
     * @param backlog The backlog of the sprint
     * @param team The team working on the sprint
     * @param release The release to which the sprint is dedicated
     */
    public Sprint(String longName, String goal, String description, LocalDate startDate, LocalDate endDate,
                  Backlog backlog, Team team, Release release) {

        this.longName = longName;
        this.description = description;
        this.goal = goal;
        this.startDate = startDate;
        this.endDate = endDate;
        this.backlog = backlog;
        this.team = team;
        this.release = release;

        setInformationSwitchStrategy(new SprintInformationSwitchStrategy());
    }


    /**
     * Gets the team allocated to the sprint
     * @return The team allocated to the sprint
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Gets the backlog allocated to the sprint
     * @return The backlog allocated to the sprint
     */
    public Backlog getBacklog() {
        return backlog;
    }

    /**
     * Gets the project allocated to the sprint (implicitly via the backlog)
     * @return The project allocated to the sprint
     */
    public Project getProject() {
        return backlog.getProject();
    }

    /**
     * Gets the Release with which the sprint is associated.
     * @return The release the sprint is assigned
     */
    public Release getRelease() {
        return release;
    }

    /**
     * Gets the sprints list of stories
     *
     * @return The ObservableList of stories
     */
    public ObservableList<Story> getStories() {
        this.serializableStories.clear();
        for (Object item : this.stories) {
            this.serializableStories.add((Story) item);
        }
        return this.stories;
    }

    /**
     * Gets the short name/label of the sprint
     * @return The sprint goal
     */
    public String getShortName() {
        return goal;
    }

    /**
     * Gets the full name of the sprint
     * @return The long name of the sprint
     */
    public String getLongName() {
        return longName;
    }

    /**
     * Gets the description of the sprint
     * @return The description of the sprint
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the start date of the sprint
     * @return The sprint's start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Gets the end date of the sprint
     * @return The sprint's end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Gets the unallocatedTasks of the Sprint
     *
     * @return list of unallocatedTasks
     */
    public ObservableList<Task> getUnallocatedTasks() {
        return this.unallocatedTasks;
    }

    /**
     * Gets all of the tasks within the sprint (unassigned and assigned)
     *
     * @return all of the tasks within the sprint (unassigned and assigned)
     */
    public Set<Task> getAllTasks() {
        Set<Task> tasks = new HashSet<>();
        tasks.addAll(unallocatedTasks);
        for (Story story : stories) {
            tasks.addAll(story.getTasks());
        }
        return tasks;
    }

    /**
     * Gets the children of the SaharaItem
     *
     * @return The items of the SaharaItem
     */
    @Override
    public ObservableList<SaharaItem> getChildren() {
        ObservableList<SaharaItem> children = observableArrayList();

        children.addAll(tasksCategory);
        children.addAll(stories);

        return children;
    }

    /**
     * Adds listeners to the Sprint tasks for sorting
     */
    public void addListeners() {
        unallocatedTasks.addListener((ListChangeListener<Task>) change ->
            {
                if (change.next() && !change.wasPermutated()) {
                    Collections.sort(unallocatedTasks, Task.TaskNameComparator);
                }
            });
    }

    /**
     * Prepares the backlog to be serialized.
     */
    public void prepSerialization() {
        serializableStories.clear();
        for (Story story : stories) {
            story.prepSerialization();
            this.serializableStories.add(story);
        }

        serializableTasks.clear();
        for (Task item : unallocatedTasks) {
            item.prepSerialization();
            this.serializableTasks.add(item);
        }
    }


    /**
     * Deserialization post-processing.
     */
    public void postDeserialization() {
        unallocatedTasks.clear();
        for (Task task : serializableTasks) {
            task.postSerialization();
            this.unallocatedTasks.add(task);
        }

        stories.clear();
        for (Story story : serializableStories) {
            story.postSerialization();
            this.stories.add(story);
        }

        Collections.sort(this.stories, Story.StoryPriorityComparator);
    }


    /**
     * Gets the underlying item set in the hierarchy of sprints
     * @return A blank set (as no <b>new</b> items appear under the sprint hierarchy)
     */
    @Override
    public Set<SaharaItem> getItemsSet() {
        Set<SaharaItem> items = new HashSet<>();

        for (Task task : unallocatedTasks) {
            items.addAll(task.getItemsSet());
        }
        items.addAll(unallocatedTasks);

        return items;
    }

    /**
     * Adds a story to the sprints list of stories
     *
     * @param story Story to add
     */
    public void add(Story story) {
        Command addStory = new AddStoryCommand(this, story);
        Global.commandManager.executeCommand(addStory);
    }


    /**
     * Method for creating an XML element for the Sprint within report generation
     * @return element for XML generation
     */
    @Override
    public Element generateXML() {
        Element sprintElement = ReportGenerator.doc.createElement("sprint");

        //WorkSpace Elements
        Element sprintID = ReportGenerator.doc.createElement("ID");
        sprintID.appendChild(ReportGenerator.doc.createTextNode(String.valueOf(id)));
        sprintElement.appendChild(sprintID);

        Element sprintGoal = ReportGenerator.doc.createElement("goal");
        sprintGoal.appendChild(ReportGenerator.doc.createTextNode(goal));
        sprintElement.appendChild(sprintGoal);

        Element sprintLongName = ReportGenerator.doc.createElement("long-name");
        sprintLongName.appendChild(ReportGenerator.doc.createTextNode(longName));
        sprintElement.appendChild(sprintLongName);

        Element sprintStartDate = ReportGenerator.doc.createElement("start-date");
        sprintStartDate.appendChild(ReportGenerator.doc.createTextNode(startDate.toString()));
        sprintElement.appendChild(sprintStartDate);

        Element sprintEndDate = ReportGenerator.doc.createElement("end-date");
        sprintEndDate.appendChild(ReportGenerator.doc.createTextNode(endDate.toString()));
        sprintElement.appendChild(sprintEndDate);

        Element sprintDescription = ReportGenerator.doc.createElement("description");
        sprintDescription.appendChild(ReportGenerator.doc.createTextNode(description));
        sprintElement.appendChild(sprintDescription);

        Element sprintTeam = ReportGenerator.doc.createElement("team");
        sprintTeam.appendChild(ReportGenerator.doc.createTextNode(team.toString()));
        sprintElement.appendChild(sprintTeam);

        Element sprintBacklog = ReportGenerator.doc.createElement("backlog");
        sprintBacklog.appendChild(ReportGenerator.doc.createTextNode(backlog.toString()));
        sprintElement.appendChild(sprintBacklog);

        Element sprintRelease = ReportGenerator.doc.createElement("release");
        sprintRelease.appendChild(ReportGenerator.doc.createTextNode(release.toString()));
        sprintElement.appendChild(sprintRelease);

        Element sprintStories = ReportGenerator.doc.createElement("stories");
        for (Story story : getStories()) {
            Element storyElement = story.generateXML();
            sprintStories.appendChild(storyElement);
        }
        sprintElement.appendChild(sprintStories);

        return sprintElement;
    }


    /**
     * Gets the string representation of a sprint
     * @return The sprint goal/label
     */
    @Override
    public String toString() {
        return goal;
    }

    /**
     * Creates a Sprint edit command and executes it with the Global Command Manager, updating
     * the Backlog with the new parameter values.
     *
     * @param newGoal         The new goal
     * @param newLongName     The new long name
     * @param newDescription  The new description
     * @param newStartDate    The new start date
     * @param newEndDate      The new end date
     * @param newBacklog      The new backlog
     * @param newTeam         The new team
     * @param newRelease      The new release
     * @param newStories      The new stories in the backlog
     */
    public void edit(String newGoal, String newLongName, String newDescription, LocalDate newStartDate,
                     LocalDate newEndDate, Backlog newBacklog, Team newTeam, Release newRelease,
                     Collection<Story> newStories) {
        Command relEdit = new SprintEditCommand(this, newGoal, newLongName, newDescription, newStartDate, newEndDate,
                newBacklog, newTeam, newRelease, newStories);
        Global.commandManager.executeCommand(relEdit);
    }

    /**
     * Deletes a sprint from the given project.
     */
    public void deleteSprint() {
        Command command = new DeleteSprintCommand(this);
        Global.commandManager.executeCommand(command);
    }

    /**
     * A command class that allows the executing and undoing of backlog edits
     */
    private class SprintEditCommand implements Command {
        private Sprint sprint;

        private String goal;
        private String longName;
        private String description;
        private LocalDate startDate;
        private LocalDate endDate;
        private Backlog backlog;
        private Team team;
        private Release release;
        private Collection<Story> stories = new HashSet<>();

        private String oldGoal;
        private String oldLongName;
        private String oldDescription;
        private LocalDate oldStartDate;
        private LocalDate oldEndDate;
        private Backlog oldBacklog;
        private Team oldTeam;
        private Release oldRelease;
        private Collection<Story> oldStories = new HashSet<>();

        private Map<Story, String> oldEstimateDict = new HashMap<>();
        private Map<Story, Boolean> oldReadyStateDict = new HashMap<>();

        private SprintEditCommand(Sprint sprint, String newGoal, String newLongName,
                                   String newDescription, LocalDate newStartDate, LocalDate newEndDate,
                                   Backlog newBacklog, Team newTeam, Release newRelease, Collection<Story> newStories) {
            this.sprint = sprint;

            this.goal = newGoal;
            this.longName = newLongName;
            this.description = newDescription;
            this.startDate = newStartDate;
            this.endDate = newEndDate;
            this.backlog = newBacklog;
            this.team = newTeam;
            this.release = newRelease;
            this.stories.addAll(newStories);

            this.oldGoal = sprint.goal;
            this.oldLongName = sprint.longName;
            this.oldDescription = sprint.description;
            this.oldStartDate = sprint.startDate;
            this.oldEndDate = sprint.endDate;
            this.oldBacklog = sprint.backlog;
            this.oldTeam = sprint.team;
            this.oldRelease = sprint.release;
            this.oldStories.addAll(sprint.stories);

            for (Story story : oldStories) {
                oldEstimateDict.put(story, story.getEstimate());
                oldReadyStateDict.put(story, story.getReady());
            }
        }

        /**
         * Executes/Redoes the changes of the backlog edit
         */
        public void execute() {
            sprint.goal = goal;
            sprint.longName = longName;
            sprint.description = description;
            sprint.startDate = startDate;
            sprint.endDate = endDate;
            sprint.backlog = backlog;
            sprint.team = team;
            sprint.release = release;

            sprint.stories.removeAll(oldStories);
            sprint.stories.addAll(stories);


            //Are stories sorted in sprint?
            //Collections.sort(sprint.stories, Story.StoryPriorityComparator);
        }

        /**
         * Undoes the changes of the backlog edit
         */
        public void undo() {
            sprint.goal = oldGoal;
            sprint.longName = oldLongName;
            sprint.description = oldDescription;
            sprint.startDate = oldStartDate;
            sprint.endDate = oldEndDate;
            sprint.backlog = oldBacklog;
            sprint.team = oldTeam;
            sprint.release = oldRelease;

            sprint.stories.removeAll(stories);
            sprint.stories.addAll(oldStories);

            //Are stories sorted in sprint?
            //Collections.sort(sprint.stories, Story.StoryPriorityComparator);
        }

        /**
         * Searches the stateObjects to find an equal model class to map to
         * @param stateObjects A set of objects to search through
         * @return If the item was successfully mapped
         */
        @Override //
        public boolean map(Set<SaharaItem> stateObjects) {
            boolean mapped_sp = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(sprint)) {
                    this.sprint = (Sprint) item;
                    mapped_sp = true;
                }
            }

            boolean mapped_bl = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(backlog)) {
                    this.backlog = (Backlog) item;
                    mapped_bl = true;
                }
            }

            boolean mapped_old_bl = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(oldBacklog)) {
                    this.oldBacklog = (Backlog) item;
                    mapped_old_bl = true;
                }
            }

            boolean mapped_tm = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(team)) {
                    this.team = (Team) item;
                    mapped_tm = true;
                }
            }

            boolean mapped_old_tm = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(oldTeam)) {
                    this.oldTeam = (Team) item;
                    mapped_old_tm = true;
                }
            }

            boolean mapped_rl = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(release)) {
                    this.release = (Release) item;
                    mapped_rl = true;
                }
            }

            boolean mapped_old_rl = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(oldRelease)) {
                    this.oldRelease = (Release) item;
                    mapped_old_rl = true;
                }
            }

            // Story collections
            for (Story story : stories) {
                for (SaharaItem item : stateObjects) {
                    if (item.equivalentTo(story)) {
                        stories.remove(story);
                        stories.add((Story)item);
                        break;
                    }
                }
            }

            for (Story story : oldStories) {
                for (SaharaItem item : stateObjects) {
                    if (item.equivalentTo(story)) {
                        oldStories.remove(story);
                        oldStories.add((Story)item);
                        break;
                    }
                }
            }

            return mapped_sp && mapped_bl && mapped_tm && mapped_rl & mapped_old_bl && mapped_old_tm && mapped_old_rl;
        }
    }

    /**
     * A command class for allowing the deletion of Sprints.
     */
    private class DeleteSprintCommand implements Command {
        private Sprint sprint;
        private Project proj;

        /**
         * Constructor for the sprint deletion command.
         * @param sprint The sprint to be deleted.
         */
        DeleteSprintCommand(Sprint sprint) {
            this.sprint = sprint;
            this.proj = sprint.getBacklog().getProject();
        }

        /**
         * Executes the backlog deletion command.
         */
        public void execute() {
            //System.out.println("Exec Sprint Delete");
            proj.getSprints().remove(sprint);
            //release.setProject(null);
        }

        /**
         * Undoes the backlog deletion command.
         */
        public void undo() {
            //System.out.println("Undone Sprint Delete");
            proj.getSprints().add(sprint);
            //release.setProject(proj);
        }

        /**
         * Searches the stateObjects to find an equal model class to map to
         * @param stateObjects A set of objects to search through
         * @return If the item was successfully mapped
         */
        @Override //
        public boolean map(Set<SaharaItem> stateObjects) {
            boolean mapped_sp = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(sprint)) {
                    this.sprint = (Sprint) item;
                    mapped_sp = true;
                }
            }
            return mapped_sp;
        }
    }

    /**
     * A command class for allowing the addition of Stories to Sprints.
     */
    private class AddStoryCommand implements Command {
        private Sprint sprint;
        private Story story;

        /**
         * Constructor for the story addition command.
         * @param sprint The sprint to which the story is to be added.
         * @param story The story to be added.
         */
        AddStoryCommand(Sprint sprint, Story story) {
            //this.proj = proj;
            this.sprint = sprint;
            this.story = story;
        }

        /**
         * Executes the Story addition command
         */
        public void execute() {
            sprint.stories.add(story);
        }

        /**
         * Undoes the story addition command.
         */
        public void undo() {
            sprint.stories.remove(story);
        }

        /**
         * Searches the stateObjects to find an equal model class to map to
         * @param stateObjects A set of objects to search through
         * @return If the item was successfully mapped
         */
        @Override //
        public boolean map(Set<SaharaItem> stateObjects) {
            boolean mapped_sp = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(backlog)) {
                    this.sprint = (Sprint) item;
                    mapped_sp = true;
                }
            }
            boolean mapped_story = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(story)) {
                    this.story = (Story) item;
                    mapped_story = true;
                }
            }
            return mapped_sp && mapped_story;
        }
    }
}
