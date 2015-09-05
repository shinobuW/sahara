package seng302.group2.workspace.project.story.tasks;

import org.w3c.dom.Element;
import seng302.group2.Global;
import seng302.group2.util.conversion.DurationConverter;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.person.Person;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a log for recording the effort spent on a task
 * Created by swi67 on 31/07/15.
 */
public class Log extends SaharaItem implements Serializable {
    private LocalDateTime startTime = LocalDateTime.now();
    private double duration = 0;
    private double effortLeftDifference = 0;
    private Person logger = null;
    private Task task = null;
    private String description = "";

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
            this.log.task.getLogs().remove(this.log);
            this.log.task.setEffortSpent((this.oldEffortSpent - this.log.duration));
        }


        /**
         * Undoes the log deletion command.
         */
        public void undo() {
            this.log.task.getLogs().add(this.log);
            this.log.task.setEffortSpent(this.oldEffortSpent);
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
     */
    public void edit(Person newLogger, LocalDateTime newStartDate,
                     double newDuration, String newDescription, double newEffortLeft) {
        LogEditCommand logEditCommand = new LogEditCommand(this, newLogger, newStartDate, newDuration, newDescription,
                newEffortLeft);
        Global.commandManager.executeCommand(logEditCommand);
    }


    /**
     * A command class that allows the executing and undoing of project edits
     */
    private class LogEditCommand implements Command {
        private Log log;
        private Person logger;
        private LocalDateTime startTime;
        private double duration;
        private String description;
        private double effortLeftDifference;
        private Task task;

        private Person oldLogger;
        private LocalDateTime oldStartTime;
        private double oldDuration;
        private String oldDescription;
        private double oldEffortLeftDifference;

        protected LogEditCommand(Log log, Person newLogger, LocalDateTime newStartDate,
                                 double newDuration, String newDescription, double newEffortLeftDifference) {
            this.log = log;
            this.logger = newLogger;
            this.startTime = newStartDate;
            this.duration = newDuration;
            this.description = newDescription;
            this.effortLeftDifference = newEffortLeftDifference;

            this.oldLogger = log.logger;
            this.oldStartTime = log.startTime;
            this.oldDuration = log.duration;
            this.oldDescription = log.description;
            this.oldEffortLeftDifference = log.effortLeftDifference;

            this.task = log.task;
        }


        /**
         * Executes/Redoes the changes of the person edit
         */
        public void execute() {
            task.setEffortSpent(task.getEffortSpent() - log.getDurationInMinutes() + duration);
            log.logger = logger;
            log.startTime = startTime;
            log.duration = duration;
            log.description = description;
            log.duration = duration;
            log.effortLeftDifference = effortLeftDifference;


        }


        /**
         * Undoes the changes of the person edit
         */
        public void undo() {
            log.logger = oldLogger;
            log.startTime = oldStartTime;
            log.duration = oldDuration;
            log.description = oldDescription;
            log.effortLeftDifference = oldEffortLeftDifference;
            task.setEffortSpent(task.getEffortSpent() + log.getDurationInMinutes() - duration);

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
}
