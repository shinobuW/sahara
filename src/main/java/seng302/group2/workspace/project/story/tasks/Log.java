package seng302.group2.workspace.project.story.tasks;

import org.w3c.dom.Element;
import seng302.group2.Global;
import seng302.group2.util.conversion.DurationConverter;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.sprint.Sprint;
import seng302.group2.workspace.tag.Tag;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a log for recording the effort spent on a task
 * Created by swi67 on 31/07/15.
 */
public class Log extends SaharaItem implements Serializable {
    protected LocalDateTime startTime = LocalDateTime.now();
    protected double duration = 0;
    protected double effortLeftDifference = 0;
    protected Person logger = null;
    protected Task task = null;
    protected String description = "";
    protected boolean ghostLog = false;

    public Log() {
        super("Untitled Log");
    }


    /**
     *Basic constructor
     * @param task the task the los is for
     * @param description description of the work accomplished
     * @param logger the person logging the time
     * @param duration the duration the person worked for
     * @param startTime time the logger started working on the task
     * @param effortLeftDifference The effort left in minutes
     */
    public Log(Task task, String description, Person logger, double duration,
               LocalDateTime startTime, double effortLeftDifference) {
        super("Untitled Log");
        this.task = task;
        this.logger = logger;
        this.startTime = startTime;
        this.description = description;
        this.duration = duration;
        this.effortLeftDifference = effortLeftDifference;
    }



    /**
     * Gets the duration of the log
     * @return duration in hours
     */
    public double getDurationInHours() {
        return this.duration / 60;
    }


    /**
     * Gets the duration of the log
     * @return duration in minutes
     */
    public double getDurationInMinutes() {
        return this.duration;
    }

    /**
     * Gets the description of the log
     * @return description of the log
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets the startTime of the log
     * @return get the start dat ein LocalDateTime format.
     */
    public LocalDateTime getStartDate() {
        return this.startTime;
    }
    
    /**
     * Gets the startTime of the log
     * @return the startdate in LocalDate format.
     */
    public LocalDate getLocalStartDate() {
        return LocalDate.of(this.startTime.getYear(), this.startTime.getMonthValue(), this.startTime.getDayOfMonth());
    }

    /**
     * Gets the effort left set in this log.
     * @return effort left in hours
     */
    public double getEffortLeftDifferenceInHours() {
        return this.effortLeftDifference / 60;
    }

    /**
     * Gets the effort left set in this log.
     * @return effort left in minutes
     */
    public double getEffortLeftDifferenceInMinutes() {
        return this.effortLeftDifference;
    }

    /**
     * Sets the log to be a ghost log. Ghost logs are used when manually editing the effort left.
     */
    public void setGhostLog() {
        this.ghostLog = true;
    }

    /**
     * Returns if the current log is a ghost log or not.
     * @return if ghost log.
     */
    public boolean isGhostLog() {
        return this.ghostLog;
    }


    /**
     * Gets the user logging time on the task
     * @return the logger
     */
    public Person getLogger() {
        return this.logger;
    }

    /**
     * Gets the task the log is for
     * @return the task
     */
    public Task getTask() {
        return this.task;
    }

    /**
     * Gets the Duration string representation for Hours and minutes of Duration
     * @return the processed String value.
     */
    public String getDurationString() {
        return (int) Math.floor(duration / 60) + "h " + (int) Math.floor(duration % 60) + "min";
    }

    /**
     * Returns the sprint of the task the log belongs to
     * @return The Sprint the Log belongs to.
     */
    public Sprint getSprint() {
        return this.getTask().getStory().getSprint();
    }

    
    /**
     * Setting the Log's task to be the new task. Method only needed for edit commands.
     * @param task the new Task
     */
    public void setTask(Task task) {
        this.task = task;
    }
    
    /**
     * Deletes the log from the task it belongs to
     */
    public void deleteLog() {
        Command command = new DeleteLogCommand(this);
        Global.commandManager.executeCommand(command);
    }


    /**
     * SaharaItems 'belonging' to the Log
     * @return Log empty set as logs do not have any items belonging to
     */
    @Override
    public Set<SaharaItem> getItemsSet() {
        return new HashSet<>();
    }

    /**
     * Log's toString method
     * @return Log information
     */
    @Override
    public String toString() {
        return "Log: " + this.startTime.toString() + ", " + this.duration + ", Task:" + this.task.toString();
    }


    /**
     * Method for creating an XML element for the Log within report generation
     * @return element for XML generation
     */
    @Override
    public Element generateXML() {
        //Logs not needed in XML reports, commenting out so it can be used for future generation of log reports

//        Element logElement = ReportGenerator.doc.createElement("log");
//
//        //WorkSpace Elements
//        Element logID = ReportGenerator.doc.createElement("ID");
//        logID.appendChild(ReportGenerator.doc.createTextNode(String.valueOf(id)));
//        logElement.appendChild(logID);
//
//        Element loggerElement = ReportGenerator.doc.createElement("logger");
//        loggerElement.appendChild(ReportGenerator.doc.createTextNode(logger.toString()));
//        logElement.appendChild(loggerElement);
//
//        Element descriptionElement = ReportGenerator.doc.createElement("description");
//        descriptionElement.appendChild(ReportGenerator.doc.createTextNode(description));
//        logElement.appendChild(descriptionElement);
//
//        Element startTimeElement = ReportGenerator.doc.createElement("start-time");
//        startTimeElement.appendChild(ReportGenerator.doc.createTextNode(startTime.toString()));
//        logElement.appendChild(startTimeElement);
//
//        Element durationElement = ReportGenerator.doc.createElement("duration");
//        durationElement.appendChild(ReportGenerator.doc.createTextNode(duration.toString()));
//        logElement.appendChild(durationElement);
//
//        Element effortLeftElement = ReportGenerator.doc.createElement("effort-left");
//        effortLeftElement.appendChild(ReportGenerator.doc.createTextNode(effortLeft.toString()));
//        logElement.appendChild(effortLeftElement);
//
//        Element logTagElement = ReportGenerator.doc.createElement("tags");
//        for (Tag tag : this.getTags()) {
//            Element tagElement = tag.generateXML();
//            logTagElement.appendChild(tagElement);
//        }
//        logElement.appendChild(logTagElement);
//
//        return logElement;
        return null;
    }


    /**
     * A command class for allowing the deletion of logs
     */
    private class DeleteLogCommand implements Command {
        private Log log;
        
        private double oldEffortSpent;

        /**
         * Constructor for the log deletion command.
         * @param log the log to be deleted.
         */
        DeleteLogCommand(Log log) {
            this.log = log;
            this.oldEffortSpent = log.getTask().getEffortSpent();
        }


        /**
         * Executes the log deletion command.
         */
        public void execute() {
            this.log.getTask().getStory().getProject().getLogs().remove(this.log);
            this.log.task.setEffortSpent((this.oldEffortSpent - this.log.duration));
        }


        /**
         * Undoes the log deletion command.
         */
        public void undo() {
            this.log.getTask().getStory().getProject().getLogs().add(this.log);
            this.log.task.setEffortSpent(this.oldEffortSpent);
        }

        /**
         * Gets the String value of the Command for deletion of logs.
         */
        public String getString() {
            return null;
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
                if (item.equivalentTo(log)) {
                    this.log = (Log) item;
                    mapped = true;
                }
            }
            return mapped;
        }
    }

    /**
     * Sets the duration
     * @param inputDuration the new duration the log
     * @return true if duration is not null
     */
    public boolean setDuration(String inputDuration) {
        Double newDuration = DurationConverter.readDurationToMinutes(inputDuration);
        if (newDuration != null) {
            this.duration = newDuration;
            return true;
        }
        return false;
    }

    /**
     * Edits the log using the commands to allow for undo-redo
     * @param newLogger the logger to edit to 
     * @param newStartDate the new start date to set
     * @param newDuration the new duration to set
     * @param newDescription the new description to set
     * @param newEffortLeft the new effort left to set
     * @param newTags        The new tags
     */
    public void edit(Person newLogger, LocalDateTime newStartDate,
                     double newDuration, String newDescription, double newEffortLeft, ArrayList<Tag> newTags) {
        LogEditCommand logEditCommand = new LogEditCommand(this, newLogger, newStartDate, newDuration, newDescription,
                newEffortLeft, newTags );
        Global.commandManager.executeCommand(logEditCommand);
    }

    /**
     * Edits the Log's description
     * @param description the description to edit to
     */
    public void editDescription(String description) {
        DescriptionEditCommand editCommand = new DescriptionEditCommand(this, description);
        Global.commandManager.executeCommand(editCommand);
    }

    /**
     * Edits the Log's logger
     * @param logger the logger to edit to
     */
    public void editLogger(Person logger) {
        LoggerEditCommand command = new LoggerEditCommand(this, logger);
        Global.commandManager.executeCommand(command);
    }


    /**
     * A command class that allows the executing and undoing of project edits
     */
    private class LogEditCommand implements Command {
        private String commandString;
        private Log log;
        private Person logger;
        private LocalDateTime startTime;
        private double duration;
        private String description;
        private double effortLeftDifference;
        private Task task;
        private Set<Tag> logTags = new HashSet<>();
        private Set<Tag> globalTags = new HashSet<>();

        private Person oldLogger;
        private LocalDateTime oldStartTime;
        private double oldDuration;
        private String oldDescription;
        private double oldEffortLeftDifference;
        private Set<Tag> oldLogTags = new HashSet<>();
        private Set<Tag> oldGlobalTags = new HashSet<>();

        protected LogEditCommand(Log log, Person newLogger, LocalDateTime newStartDate, double newDuration,
                                 String newDescription, double newEffortLeftDifference, ArrayList<Tag> newTags) {
            this.log = log;

            if (newTags == null) {
                newTags = new ArrayList<>();
            }

            this.logger = newLogger;
            this.startTime = newStartDate;
            this.duration = newDuration;
            this.description = newDescription;
            this.effortLeftDifference = newEffortLeftDifference;
            this.logTags.addAll(newTags);
            this.globalTags.addAll(newTags);
            this.globalTags.addAll(Global.currentWorkspace.getAllTags());

            this.oldLogger = log.logger;
            this.oldStartTime = log.startTime;
            this.oldDuration = log.duration;
            this.oldDescription = log.description;
            this.oldEffortLeftDifference = log.effortLeftDifference;
            this.oldLogTags.addAll(log.getTags());
            this.oldGlobalTags.addAll(Global.currentWorkspace.getAllTags());

            this.task = log.task;
        }


        /**
         * Executes/Redoes the changes of the log edit
         */
        public void execute() {
            task.setEffortSpent(task.getEffortSpent() - log.getDurationInMinutes() + duration);
            log.logger = logger;
            log.startTime = startTime;
            log.duration = duration;
            log.description = description;
            log.duration = duration;
            log.effortLeftDifference = effortLeftDifference;

            //Add any created tags to the global collection
            Global.currentWorkspace.getAllTags().clear();
            Global.currentWorkspace.getAllTags().addAll(globalTags);
            //Add the tags a log has to their list of tags
            log.getTags().clear();
            log.getTags().addAll(logTags);

            commandString = "Redoing the edit of Log \"" + log.toString() + "\".";


        }


        /**
         * Undoes the changes of the log edit
         */
        public void undo() {
            log.logger = oldLogger;
            log.startTime = oldStartTime;
            log.duration = oldDuration;
            log.description = oldDescription;
            log.effortLeftDifference = oldEffortLeftDifference;
            task.setEffortSpent(task.getEffortSpent() + log.getDurationInMinutes() - duration);

            //Adds the old global tags to the overall collection
            Global.currentWorkspace.getAllTags().clear();
            Global.currentWorkspace.getAllTags().addAll(oldGlobalTags);

            //Changes the logs list of tags to what they used to be
            log.getTags().clear();
            log.getTags().addAll(oldLogTags);
            commandString = "Undoing the edit of Log \"" + log.toString() + "\".";

        }

        /**
         * Gets the String value of the Command for Editting of Logs.
         */
        public String getString() {
            return commandString;
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
                if (item.equivalentTo(log)) {
                    this.log = (Log) item;
                    mapped = true;
                }
            }

            //Tag collections
            for (Tag tag : logTags) {
                for (SaharaItem item : stateObjects) {
                    if (item.equivalentTo(tag)) {
                        logTags.remove(tag);
                        logTags.add((Tag)item);
                        break;
                    }
                }
            }

            for (Tag tag : oldLogTags) {
                for (SaharaItem item : stateObjects) {
                    if (item.equivalentTo(tag)) {
                        oldLogTags.remove(tag);
                        oldLogTags.add((Tag) item);
                        break;
                    }
                }
            }

            for (Tag tag : globalTags) {
                for (SaharaItem item : stateObjects) {
                    if (item.equivalentTo(tag)) {
                        globalTags.remove(tag);
                        globalTags.add((Tag)item);
                        break;
                    }
                }
            }

            for (Tag tag : oldGlobalTags) {
                for (SaharaItem item : stateObjects) {
                    if (item.equivalentTo(tag)) {
                        oldGlobalTags.remove(tag);
                        oldGlobalTags.add((Tag)item);
                        break;
                    }
                }
            }
            return mapped;
        }
    }


    /**
     * Edit command for editing description
     */
    private class DescriptionEditCommand implements Command {
        private String commandString;
        private Log log;
        private String newDescription;
        private String oldDescription;

        /**
         * Constructor
         * @param log log to edit
         * @param description new description to edit to
         */
        private DescriptionEditCommand(Log log, String description) {
            this.log = log;
            this.newDescription = description;
            this.oldDescription = log.description;
        }


        /**
         * Executes the edit
         */
        @Override
        public void execute() {
            log.description = this.newDescription;
            commandString = "Redoing the edit of Description on Log \"" + log.toString() + "\".";
        }


        /**
         * Un-does the edit
         */
        @Override
        public void undo() {
            log.description = this.oldDescription;
            commandString = "Undoing the edit of Description on Log \"" + log.toString() + "\".";
        }

        /**
         * Gets the String value of the Command for editing the description of Logs.
         */
        public String getString() {
            return commandString;
        }


        @Override
        public boolean map(Set<SaharaItem> stateObjects) {
            boolean mapped = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(log)) {
                    this.log = (Log) item;
                    mapped = true;
                }
            }
            return mapped;
        }
    }

    /**
     * Edit command for editing the logger
     */
    private class LoggerEditCommand implements Command {
        private Log log;
        private Person oldLogger;
        private Person newLogger;
        private String commandString;

        /**
         * Constructor
         * @param log log to edit
         * @param logger new logger to edit to
         */
        private LoggerEditCommand(Log log, Person logger) {
            this.log = log;
            this.newLogger = logger;
            this.oldLogger = log.getLogger();
        }

        /**
         * Executes the logger edit
         */
        @Override
        public void execute() {
            log.logger = this.newLogger;
            commandString = "Redoing the edit of Logger on Log \"" + log.toString() + "\".";
        }


        /**
         * Un-does the logger edit
         */
        @Override
        public void undo() {
            log.logger = this.oldLogger;
            commandString = "Undoing the edit of Logger on Log \"" + log.toString() + "\".";
        }


        /**
         * Gets the String value of the Command for editing the description of Logs.
         */
        @Override
        public String getString() {
            return null;
        }


        @Override
        public boolean map(Set<SaharaItem> stateObjects) {
            boolean mapped = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(log)) {
                    this.log = (Log) item;
                    mapped = true;
                }
            }
            return mapped;
        }
    }

}
