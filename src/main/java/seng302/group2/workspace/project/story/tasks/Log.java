package seng302.group2.workspace.project.story.tasks;

import org.w3c.dom.Element;
import seng302.group2.Global;
import seng302.group2.util.undoredo.Command;
import seng302.group2.util.validation.DateValidator;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.person.Person;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a log for recording the effort spent on a task
 * Created by swi67 on 31/07/15.
 */
public class Log extends SaharaItem implements Serializable {
    private LocalDate startTime = LocalDate.now();
    private Float duration = (float) 0;
    private Person logger = null;
    private Task task = null;
    private String description = "";

    public Log() {
    }


    /**
     *Basic constructor
     * @param task the task the los is for
     * @param description description of the work accomplished
     * @param logger the person logging the time
     * @param duration the duration the person worked for
     * @param startTime time the logger started working on the task
     */
    public Log(Task task, String description, Person logger, Float duration, LocalDate startTime) {
        this.task = task;
        this.logger = logger;
        this.startTime = startTime;
        this.description = description;
        this.startTime = startTime;
        this.duration = duration;
    }



    /**
     * Gets the duration of the log
     * @return duration in hours
     */
    public Float getDurationInHours() {
        return this.duration / 60;
    }


    /**
     * Gets the duration of the log
     * @return duration in minutes
     */
    public Float getDurationInMinutes() {
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
     * @return duration in hours
     */
    public LocalDate getStartDate() {
        return this.startTime;
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
//        return logElement;
        return null;
    }


    /**
     * A command class for allowing the deletion of logs
     */
    private class DeleteLogCommand implements Command {
        private Log log;

        /**
         * Constructor for the log deletion command.
         * @param log the log to be deleted.
         */
        DeleteLogCommand(Log log) {
            this.log = log;
        }


        /**
         * Executes the log deletion command.
         */
        public void execute() {
            this.log.task.getLogs().remove(this.log);
        }


        /**
         * Undoes the log deletion command.
         */
        public void undo() {
            this.log.task.getLogs().add(this.log);
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


    public boolean setDuration(String inputDuration) {
        this.duration = readDurationToMinutes(inputDuration);
        return duration != null;
    }


    public static Float readDurationToMinutes(String inputDuration) {
        if (!DateValidator.validDuration(inputDuration)) {
            return null;
        }

        String[] hourKeys = {"h", "hour", "hours", "hrs", "hr"};
        String[] minKeys = {"min", "mins", "m", "minutes", "minute"};

        int hourPos = -1;
        int minPos = -1;
        String hourWord = "";

        // Find the hour position
        for (String hour : hourKeys) {
            if (inputDuration.contains(hour)) {
                hourPos = inputDuration.indexOf(hour);
                hourWord = hour;
            }
        }

        // Find the minute position
        for (String min : minKeys) {
            if (inputDuration.contains(min)) {
                minPos = inputDuration.indexOf(min);
            }
        }

        if (hourPos > 0 && minPos > 0) {
            // Have both
            String hourString = inputDuration.substring(0, hourPos);
            String minString = inputDuration.substring(hourPos + hourWord.length(), minPos);
            return Float.valueOf(hourString.trim()) * 60 + Float.valueOf(minString.trim());

        }
        else if (hourPos > 0) {
            // Have just hours
            String hourString = inputDuration.substring(0, hourPos);
            return Float.valueOf(hourString.trim()) * 60;
        }
        else if (minPos > 0) {
            // Have just mins
            String minString = inputDuration.substring(0, minPos);
            return Float.valueOf(minString.trim());
        }
        else {
            // Have just a base number (hours default)
            return Float.valueOf(inputDuration.trim()) * 60;
        }
    }


    /**
     * A command class that allows the executing and undoing of project edits
     */
    private class LogEditCommand implements Command {
        private Log log;
        private Person logger;
        private LocalDate startTime;
        private Float duration;
        private String description;

        private Person oldLogger;
        private LocalDate oldStartTime;
        private Float oldDuration;
        private String oldDescription;

        protected LogEditCommand(Log log, Person newLogger, LocalDate newStartDate,
                                 Float newDuration, String newDescription) {
            this.log = log;
            this.logger = newLogger;
            this.startTime = newStartDate;
            this.duration = newDuration;
            this.description = newDescription;

            this.oldLogger = log.logger;
            this.oldStartTime = log.startTime;
            this.oldDuration = log.duration;
            this.oldDescription = log.description;
        }


        /**
         * Executes/Redoes the changes of the person edit
         */
        public void execute() {
            log.logger = logger;
            log.startTime = startTime;
            log.duration = duration;
            log.description = description;
            log.duration = duration;
        }


        /**
         * Undoes the changes of the person edit
         */
        public void undo() {
            log.logger = oldLogger;
            this.startTime = oldStartTime;
            this.duration = oldDuration;
            this.description = oldDescription;
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
