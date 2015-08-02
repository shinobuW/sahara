package seng302.group2.workspace.project.story.tasks;

import javafx.collections.ObservableList;
import org.w3c.dom.Element;
import seng302.group2.scenes.sceneswitch.switchStrategies.workspace.project.StoryInformationSwitchStrategy;
import seng302.group2.util.reporting.ReportGenerator;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.person.Person;

import java.io.Serializable;
import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;
import seng302.group2.scenes.sceneswitch.switchStrategies.workspace.project.story.TaskInformationSwitchStrategy;

/**
 * Created by cvs20 on 27/07/15.
 */
public class Task extends SaharaItem implements Serializable {

    /**
     * A comparator that returns the comparison of two story's short names
     */
    public static Comparator<Task> TaskNameComparator = (task1, task2) -> {
        return task1.getShortName().compareTo(task2.getShortName());
    };

    private String shortName;
    private String description;
    private String impediments;
    private TASKSTATE state;
    private transient ObservableList<Person> responsibilities = observableArrayList();
    private List<Person> serializableResponsibilities = new ArrayList<>();
    private transient ObservableList<Log> logs = observableArrayList();
    private List<Log> serializableLogs = new ArrayList<>();
    // TODO Effort left and Spent for logging


    /**
     * Returns the items held by the Task
     * @return set of logs associated with the task
     */
    @Override
    public Set<SaharaItem> getItemsSet() {
        Set<SaharaItem> items = new HashSet<>();
        for (Log log : this.logs) {
            items.add(log);
        }
        return items;
    }


    /**
     * Basic Task constructor
     */
    public Task() {
        super("Untitled Task");
        this.shortName = "Untitled Task";
        this.description = "";
        this.impediments = "";
        this.state = TASKSTATE.NOT_STARTED;
        setInformationSwitchStrategy(new TaskInformationSwitchStrategy());
    }


    /**
     * Basic Task constructor
     * @param shortName The shortname of the Task
     * @param description The description of the task
     */
    public Task(String shortName, String description) {
        super(shortName);
        this.shortName = shortName;
        this.description = description;
        this.impediments = "";
        this.state = TASKSTATE.NOT_STARTED;

        setInformationSwitchStrategy(new TaskInformationSwitchStrategy());
    }


    /**
     * Gets the short name of the task
     *
     * @return the short name
     */
    public String getShortName() {
        return this.shortName;
    }

    /**
     * Sets the short name of the task
     *
     * @param shortName short name to be set
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * Gets the description of the task
     *
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description of the task
     *
     * @param description description to be set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the state of the current task
     *
     * @return the state as one of the Enum values
     */
    public TASKSTATE getState() {
        return this.state;
    }

    /**
     * Sets the state of the current task
     *
     * @param state state to set the task to
     */
    public void setState(TASKSTATE state) {
        this.state = state;
    }

    /**
     * Gets the impediments of the current task
     *
     * @return the impediments of the current tasks=
     */
    public String getImpediments() {
        return this.impediments;
    }

    /**
     * Sets the impediments of the current task
     *
     * @param impediments impediments to add to the task
     */
    public void setImpediments(String impediments) {
        this.impediments = impediments;
    }

    /**
     * Gets the responsibilities of a Task
     *
     * @return The ObservableList of responsibilities as a list of People
     */
    public ObservableList<Person> getResponsibilities() {
        return this.responsibilities;
    }

    /**
     * Gets the logs associated to this task
     * @return list of logs
     */
    public ObservableList<Log> getLogs() {
        return this.logs;
    }

    /**
     * Adds a Person to the Tasks responsibility list
     *  @param person The person to add
     */
    public void add(Person person) {
        this.responsibilities.add(person);
    }

    /**
     * Prepares a Task to be serialized.
     */
    public void prepSerialization() {
        serializableResponsibilities.clear();
        for (Object item : responsibilities) {
            this.serializableResponsibilities.add((Person) item);
        }

        serializableLogs.clear();
        for (Object item : logs) {
            this.serializableLogs.add((Log) item);
        }
    }


    /**
     * Deserialization post-processing.
     */
    public void postSerialization() {
        responsibilities.clear();
        for (Object item : serializableResponsibilities) {
            this.responsibilities.add((Person) item);
        }
        logs.clear();
        for (Object item : serializableLogs) {
            this.logs.add((Log) item);
        }
    }

    /**
     * Method for creating an XML element for the Task within report generation
     * @return element for XML generation
     */
    @Override
    public Element generateXML() {
        Element taskElement = ReportGenerator.doc.createElement("skill");

        //WorkSpace Elements
        Element taskID = ReportGenerator.doc.createElement("ID");
        taskID.appendChild(ReportGenerator.doc.createTextNode(String.valueOf(id)));
        taskElement.appendChild(taskID);

        Element taskShortName = ReportGenerator.doc.createElement("shortname");
        taskShortName.appendChild(ReportGenerator.doc.createTextNode(shortName));
        taskElement.appendChild(taskShortName);

        Element taskDescription = ReportGenerator.doc.createElement("description");
        taskDescription.appendChild(ReportGenerator.doc.createTextNode(description));
        taskElement.appendChild(taskDescription);

        Element taskState = ReportGenerator.doc.createElement("description");
        taskState.appendChild(ReportGenerator.doc.createTextNode(state.toString()));
        taskElement.appendChild(taskState);

        Element taskResponsibilities = ReportGenerator.doc.createElement("task-responsibilities");
        for (Person person : this.responsibilities) {
            taskResponsibilities.appendChild(ReportGenerator.doc.createTextNode(person.getShortName()));
        }
        taskElement.appendChild(taskResponsibilities);

        return taskElement;
    }

    public enum TASKSTATE {
        NOT_STARTED,
        IN_PROGRESS,
        BLOCKED,
        DONE,
        READY,
        PENDING,
        DEFERRED
    }



}
