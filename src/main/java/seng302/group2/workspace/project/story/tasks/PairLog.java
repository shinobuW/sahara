package seng302.group2.workspace.project.story.tasks;

import seng302.group2.Global;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.person.Person;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * A log class extended from Log used for logging pair programming
 * Created by swi67 on 10/09/15.
 */
public class PairLog extends Log {
    private Person partner;

    /**
     * @param duration duration of the log
     * @param startTime the start time
     * @param task the task the los is for
     * @param description description of the work accomplished
     * @param logger the person logging the time
     * @param partner the other logger
     * @param duration the duration the person worked for
     * @param startTime time the logger started working on the task
     * @param effortLeftDifference The effort left in minutes
     */
    public PairLog(Task task, String description, Person logger, Person partner, double duration,
               LocalDateTime startTime, double effortLeftDifference) {
        super(task, description, logger, duration, startTime, effortLeftDifference);
        this.partner = partner;
    }

    /**
     * Returns the person who worked with the main logger
     * @return the other logger
     */
    public Person getPartner() {
        return this.partner;
    }


    /**
     * Returns half of the duration
     * @return half the duration in minutes.
     */
    public double getIndividualDuration() {
        return getDurationInMinutes() / 2;
    }

    /**
     * Deletes the log from the task it belongs to
     */
    @Override
    public void deleteLog() {
        DeletePairLogCommand deleteCommand = new DeletePairLogCommand(this);
        Global.commandManager.executeCommand(deleteCommand);
    }

    /**
     * A command class that allows the executing and undoing of project edits
     */
    private class PairLogEditCommand implements Command {
        private PairLog pLog;
        private Person logger;
        private Person partner;
        private LocalDateTime startTime;
        private double duration;
        private String description;
        private double effortLeftDifference;
        private Task task;

        private Person oldLogger;
        private Person oldPartner;
        private LocalDateTime oldStartTime;
        private double oldDuration;
        private String oldDescription;
        private double oldEffortLeftDifference;


        /**
         * Edit command
         * @param pairLog Log to edit
         * @param newLogger the logger to edit to
         * @param newPartner the partner to edit to
         * @param newStartDate the start date to edit to
         * @param newDuration the duration to edit to
         * @param newDescription the description to edit to
         * @param newEffortLeftDifference the new effort difference to edit to
         */
        protected PairLogEditCommand(PairLog pairLog, Person newLogger, Person newPartner, LocalDateTime newStartDate,
                                 double newDuration, String newDescription, double newEffortLeftDifference) {
            this.pLog = pairLog;
            this.logger = newLogger;
            this.startTime = newStartDate;
            this.duration = newDuration;
            this.description = newDescription;
            this.effortLeftDifference = newEffortLeftDifference;
            this.partner = newPartner;

            this.oldLogger = pairLog.getLogger();
            this.oldPartner = pairLog.getPartner();
            this.oldStartTime = pairLog.getStartDate();
            this.oldDuration = pairLog.getDurationInMinutes();
            this.oldDescription = pairLog.getDescription();
            this.oldEffortLeftDifference = pairLog.getEffortLeftDifferenceInMinutes();

            this.task = pairLog.getTask();
        }


        /**
         * Executes/Redoes the changes of the pair log edit
         */
        public void execute() {
            task.setEffortSpent(task.getEffortSpent() - pLog.getDurationInMinutes() + duration);
            pLog.logger = logger;
            pLog.startTime = startTime;
            pLog.duration = duration;
            pLog.description = description;
            pLog.duration = duration;
            pLog.effortLeftDifference = effortLeftDifference;
            pLog.partner = partner;
        }


        /**
         * Undoes the changes of the pair log edit
         */
        public void undo() {
            pLog.logger = oldLogger;
            pLog.startTime = oldStartTime;
            pLog.duration = oldDuration;
            pLog.description = oldDescription;
            pLog.effortLeftDifference = oldEffortLeftDifference;
            pLog.partner = oldPartner;
            task.setEffortSpent(task.getEffortSpent() + pLog.getDurationInMinutes() - duration);
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
                if (item.equivalentTo(pLog)) {
                    this.pLog = (PairLog) item;
                    mapped = true;
                }
            }
            return mapped;
        }
    }

    /**
     * A command class for allowing the deletion of logs
     */
    private class DeletePairLogCommand implements Command {
        private PairLog pLog;

        private double oldEffortSpent;

        /**
         * Constructor for the log deletion command.
         * @param pairLog the pair log to be deleted.
         */
        DeletePairLogCommand(PairLog pairLog) {
            this.pLog = pairLog;
            this.oldEffortSpent = pairLog.getTask().getEffortSpent();
        }


        /**
         * Executes the log deletion command.
         */
        public void execute() {
            this.pLog.task.getLogs().remove(this.pLog);
            this.pLog.task.setEffortSpent((this.oldEffortSpent - this.pLog.duration));
            this.pLog.logger.getLogs().remove(this.pLog);
            System.out.println(this.pLog.logger + " " + this.pLog);
        }


        /**
         * Undoes the log deletion command.
         */
        public void undo() {
            this.pLog.task.getLogs().add(this.pLog);
            this.pLog.task.setEffortSpent(this.oldEffortSpent);
            this.pLog.logger.getLogs().add(this.pLog);
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
                if (item.equivalentTo(pLog)) {
                    this.pLog = (PairLog) item;
                    mapped = true;
                }
            }
            return mapped;
        }
    }
}
