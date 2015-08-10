package seng302.group2.workspace.project.story.tasks;

import org.w3c.dom.Element;
import seng302.group2.Global;
import seng302.group2.util.undoredo.Command;
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
    private LocalDate startTime;
    private long duration;
    private Person logger;
    private Task task;
    private String description;


    /**
     *Basic constructor
     * @param task the task the los is for
     * @param description description of the work accomplished
     * @param logger the person logging the time
     * @param duration the duration the person worked for
     * @param startTime time the logger started working on the task
     */
    public Log(Task task, String description, Person logger, long duration, LocalDate startTime) {
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
    public long getDuration() {
        return this.duration;
    }


    /**
     * Gets the user logging time on the task
     * @return the logger
     */
    public Person getLogger() {
        return this.logger;
    }


    /**
     * Deletes the log from the task it belongs to
     */
    public void deleteProject() {
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
        //TODO: implement
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

    /**
     * A command class that allows the executing and undoing of project edits
     */
    private class LogEditCommand implements Command {
        private Log log;
        private Person logger;
        private LocalDate startTime;
        private Long duration;
        private String description;

        private Person oldLogger;
        private LocalDate oldStartTime;
        private Long oldDuration;
        private String oldDescription;

        protected LogEditCommand(Log log, Person newLogger, LocalDate newStartDate,
                                 Long newDuration, String newDescription) {
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
