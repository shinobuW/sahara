package seng302.group2.workspace.project.story.tasks;

import javafx.collections.ObservableList;
import org.w3c.dom.Element;
import seng302.group2.Global;
import seng302.group2.scenes.sceneswitch.switchStrategies.workspace.project.story.TaskInformationSwitchStrategy;
import seng302.group2.util.conversion.GeneralEnumStringConverter;
import seng302.group2.util.reporting.ReportGenerator;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.story.Story;

import java.io.Serializable;
import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;

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
    private Story story;
    private transient ObservableList<Person> responsibilities = observableArrayList();
    private List<Person> serializableResponsibilities = new ArrayList<>();
    private transient ObservableList<Log> logs = observableArrayList();
    private List<Log> serializableLogs = new ArrayList<>();
    private Integer effortLeft;
    private Integer effortSpent;

    GeneralEnumStringConverter converter = new GeneralEnumStringConverter();

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
        this.story = null;
        this.state = TASKSTATE.NOT_STARTED;
        this.effortLeft = 0;
        this.effortSpent = 0;


        setInformationSwitchStrategy(new TaskInformationSwitchStrategy());
    }


    /**
     * Basic Task constructor
     * @param shortName The shortname of the Task
     * @param description The description of the task
     */
    public Task(String shortName, String description, Story story, ObservableList<Person> responsibles) {
        super(shortName);
        this.shortName = shortName;
        this.description = description;
        this.impediments = "";
        this.state = TASKSTATE.NOT_STARTED;
        this.story = story;
        this.effortLeft = 0;
        this.effortSpent = 0;
        if (responsibles == null) {
            this.responsibilities = observableArrayList();
        }
        else {
            this.responsibilities = responsibles;
        }
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
     * Gets the story of the current task
     *
     * @return the story the Task is on
     */
    public Story getStory() {
        return this.story;
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
     * @return the impediments of the current tasks
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
     * Gets the effortLeft of the current task
     *
     * @return the effortLeft of the current task
     */
    public Integer getEffortLeft() {
        return this.effortLeft;
    }

    /**
     * Sets the impediments of the current task
     *
     * @param effortLeft effortLeft of the current task
     */
    public void setEffortLeft(Integer effortLeft) {
        this.effortLeft = effortLeft;
    }

    /**
     * Gets the effortLeft of the current task
     *
     * @return the effortLeft of the current task
     */
    public Integer getEffortSpent() {
        return this.effortSpent;
    }

    /**
     * Sets the impediments of the current task
     *
     * @param effortSpent effortLeft of the current task
     */
    public void setEffortSpent(Integer effortSpent) {
        this.effortSpent = effortSpent;
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
     * Adds a log to the Tasks Logs list
     *  @param log The log to add
     */
    public void add(Log log) {
        this.logs.add(log);
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
        Element taskElement = ReportGenerator.doc.createElement("task");

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

        Element taskState = ReportGenerator.doc.createElement("task-state");
        taskState.appendChild(ReportGenerator.doc.createTextNode(state.toString()));
        taskElement.appendChild(taskState);

        Element taskResponsibilities = ReportGenerator.doc.createElement("task-responsibilities");
        for (Person person : this.responsibilities) {
            taskResponsibilities.appendChild(ReportGenerator.doc.createTextNode(person.getShortName()));
        }
        taskElement.appendChild(taskResponsibilities);
        
        Element taskLogsElement = ReportGenerator.doc.createElement("task-responsibilities");
        for (Log log : this.logs) {
            Element logElement = log.generateXML();
            taskLogsElement.appendChild(logElement);
        }
        taskElement.appendChild(taskLogsElement);

        return taskElement;
    }

    /**
     * An enum for the states of the Task. Also includes a toString method for GUI application of TaskStates
     */
    public enum TASKSTATE {
        NOT_STARTED("Not Started"),
        IN_PROGRESS("In Progress"),
        BLOCKED("Blocked"),
        DONE("Done"),
        READY("Ready"),
        PENDING("Pending"),
        DEFERRED("Deferred");

        private String value;

        TASKSTATE(String value) {
            this.value = value;
        }

        /**
         * Gets the String value of the Enum.
         * @return The String equivalent of the Enum
         */
        public String getValue() {
            return value;
        }

        /**
         * Overriding toString method
         * @return The String equivalent of the Enum
         */
        @Override
        public String toString() {
            return this.getValue();
        }
    }

    public void deleteTask() {
        Command command = new DeleteTaskCommand(this);
        Global.commandManager.executeCommand(command);
    }

    /**
     * Creates a Task edit command and executes it with the Global Command Manager, updating
     * the task with the new parameter values.
     *
     * @param newShortName   The new short name
     * @param newDescription The new description
     * @param newImpediments The new Impediments
     * @param newState    The new state
     * @param newResponsibilities The new Responsibilities
     * @param newLogs The new Logs
     */
    public void edit(String newShortName, String newDescription, String newImpediments, TASKSTATE newState
                     , List<Person> newResponsibilities,  List<Log> newLogs, Integer newEffortLeft
            , Integer newEffortSpent) {
        Command relEdit = new TaskEditCommand(this, newShortName, newDescription, newImpediments,
                newState, newResponsibilities, newLogs, newEffortLeft, newEffortSpent);

        Global.commandManager.executeCommand(relEdit);
    }
    
    /**
     * A command class that allows the executing and undoing of task edits
     */
    private class TaskEditCommand implements Command {
        private Task task;

        private String shortName;
        private String description;
        private String impediments;
        private Collection<Person> responsibilities;
        private Collection<Log> logs;
        private TASKSTATE state;
        private Integer effortLeft;
        private Integer effortSpent;


        private String oldShortName;
        private String oldDescription;
        private String oldImpediments;
        private Collection<Person> oldResponsibilities;
        private Collection<Log> oldLogs;
        private TASKSTATE oldState;
        private Integer oldEffortLeft;
        private Integer oldEffortSpent;
        
        /**
         * Constructor for the Task Edit command.
         * @param task The story to be edited
         * @param newShortName   The new short name
         * @param newDescription The new description
         * @param newImpediments The new Impediments
         * @param newState    The new state
         * @param newResponsibilities The new Responsibilities
         * @param newLogs The new Logs
         */
        private TaskEditCommand(Task task, String newShortName, String newDescription, 
                String newImpediments, TASKSTATE newState,
                List<Person> newResponsibilities,  List<Log> newLogs, Integer effortLeft, Integer effortSpent) {
            this.task = task;

            this.shortName = newShortName;
            this.description = newDescription;
            this.impediments = newImpediments;
            this.responsibilities = new HashSet<>();
            this.responsibilities.addAll(newResponsibilities);
            this.logs = new HashSet<>();
            this.logs.addAll(newLogs);
            this.state = newState;
            this.effortLeft = effortLeft;
            this.effortSpent = effortSpent;

            this.oldShortName = task.shortName;
            this.oldDescription = task.description;
            this.oldImpediments = task.impediments;
            this.oldResponsibilities = new HashSet<>();
            this.oldResponsibilities.addAll(task.responsibilities);
            this.oldLogs = new HashSet<>();
            this.oldLogs.addAll(task.logs);
            this.oldState = task.state;
            this.oldEffortLeft = task.effortLeft;
            this.oldEffortSpent = task.effortSpent;

            
        }

        /**
         * Executes/Redoes the changes of the task edit
         */
        public void execute() {
            task.shortName = shortName;
            task.description = description;
            task.impediments = impediments;
            task.state = state;
            task.effortLeft = effortLeft;
            task.effortSpent = effortSpent;
            
            task.responsibilities.clear();
            task.responsibilities.addAll(responsibilities);
            
            task.logs.clear();
            task.logs.addAll(logs);
        }

        /**
         * Undoes the changes of the task edit
         */
        public void undo() {
            task.shortName = oldShortName;
            task.description = oldDescription;
            task.description = oldImpediments;
            task.state = oldState;
            task.effortLeft = oldEffortLeft;
            task.effortSpent = oldEffortSpent;

            task.responsibilities.clear();
            task.responsibilities.addAll(oldResponsibilities);
            
            task.logs.clear();
            task.logs.addAll(oldLogs);
        }

        /**
         * Searches the stateObjects to find an equal model class to map to
         * @param stateObjects A set of objects to search through
         * @return If the item was successfully mapped
         */
        @Override
        public boolean map(Set<SaharaItem> stateObjects) {
            boolean mapped_task = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(task)) {
                    this.task = (Task) item;
                    mapped_task = true;
                }
            }

            return  mapped_task;
        }
    }
    
    
    /**
     * A class for implementing task deletion in the Command undo/redo structure.
     */
    private class DeleteTaskCommand implements Command {
        private Task task;
        private Story story;
        // TODO maybe needs sprint added in here as well for tasks without a story.
        
        /**
         * Contructor for a task deletion command.
         *
         * @param task The task to delete
         */
        DeleteTaskCommand(Task task) {
            this.task = task;
            this.story = task.getStory();
        }

        /**
         * Executes the deletion of a task.
         */
        public void execute() {
            if (story != null) {
                story.getTasks().remove(task);
            }
        }

        /**
         * Undoes the deletion of a task.
         */
        public void undo() {
            if (story != null) {
                story.getTasks().add(task);
            }
            
        }

        /**
         * Searches the stateObjects to find an equal model class to map to
         * @param stateObjects A set of objects to search through
         * @return If the item was successfully mapped
         */
        @Override
        public boolean map(Set<SaharaItem> stateObjects) {
            boolean mapped_task = false;
            for (SaharaItem item : stateObjects) {
                if (item.equals(task)) {
                    this.task = (Task) item;
                    mapped_task = true;
                }
            }
            
            boolean mapped_story = false;
            for (SaharaItem item : stateObjects) {
                if (item.equals(story)) {
                    this.story = (Story) item;
                    mapped_story = true;
                }
            }
            
            return mapped_task && mapped_story;
        }
    }


}
