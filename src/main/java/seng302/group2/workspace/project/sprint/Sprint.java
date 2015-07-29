package seng302.group2.workspace.project.sprint;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.w3c.dom.Element;
import seng302.group2.scenes.sceneswitch.switchStrategies.workspace.project.SprintInformationSwitchStrategy;
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
    private Set<Story> stories = new HashSet<>();

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
     * Gets the stories to be worked on in this sprint.
     * @return The set of stories in this sprint.
     */
    public Set<Story> getStories() {
        return stories;
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
    public ObservableList getChildren() {
        ObservableList<SaharaItem> children = observableArrayList();
        children.addAll(tasksCategory);
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
     * Generate the XML report section for sprints and sprint information
     * @return The XML elements for reporting sprints
     */
    @Override
    public Element generateXML() {
        // TODO
        return null;
    }

    /**
     * Gets the string representation of a sprint
     * @return The sprint goal/label
     */
    @Override
    public String toString() {
        return goal;
    }
}
