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
     * Edits the log to the given parameters.
     * @param logger the logger to edit to
     * @param partner the partner to edit to
     * @param startDate the start date to edit to
     * @param duration the duration to edit to
     * @param description the description to edit to
     * @param effortLeftDifference the effort difference to edit to
     */
    public void edit(Person logger, Person partner, LocalDateTime startDate, double duration, String description,
                     double effortLeftDifference) {
        PairLogEditCommand editCommand = new PairLogEditCommand(this, logger, partner, startDate,
            duration, description, effortLeftDifference);
        Global.commandManager.executeCommand(editCommand);
    }

    /**
     * Edits the logger of the log to the one given.
     * @param logger the logger to edit to
     */
    public void editLogger(Person logger) {
        PairLogLoggerEditCommand editCommand = new PairLogLoggerEditCommand(this, logger);
        Global.commandManager.executeCommand(editCommand);
    }


    /**
     * Edits the partner of the log to the one given
     * @param partner the partner to edit to
     */
    public void editPartner(Person partner) {
        PairLogPartnerEditCommand editCommand = new PairLogPartnerEditCommand(this, partner);
        Global.commandManager.executeCommand(editCommand);
    }


    /**
     * Edits the duration of the log to the one given.
     * @param duration the duration to edit to.
     */
    public void editDuration(double duration) {
        PairLogDurationEditCommand editCommand = new PairLogDurationEditCommand(this, duration);
        Global.commandManager.executeCommand(editCommand);
    }


    /**
     * Edits the description of the pair log to the given one
     * @param description the description to edit to
     */
    public void editDescription(String description) {
        PairLogDescriptionEditCommand editCommand = new PairLogDescriptionEditCommand(this, description);
        Global.commandManager.executeCommand(editCommand);
    }



    /**
     * A command class that allows the executing and undoing of pairLog edits
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

    /**
     * A command class that allows the executing and undoing of PairLog partner edits
     */
    private class PairLogPartnerEditCommand implements Command {
        private PairLog pLog;
        private Person partner;

        private Person oldPartner;


        /**
         * Constructor
         * @param pairLog
         * @param newPartner
         */
        protected PairLogPartnerEditCommand(PairLog pairLog, Person newPartner) {
            this.pLog = pairLog;
            this.partner = newPartner;

            this.oldPartner = pairLog.getPartner();
        }


        /**
         * Executes/Redoes the changes of the pair log edit
         */
        public void execute() {
            pLog.partner = partner;
        }


        /**
         * Undoes the changes of the pair log edit
         */
        public void undo() {
            pLog.partner = oldPartner;
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
     * A command class that allows the executing and undoing of PairLog logger edits
     */
    private class PairLogLoggerEditCommand implements Command {
        private PairLog pLog;
        private Person logger;
        private Person oldLogger;


        /**
         * Constructor
         * @param pairLog the PairLog to be edited
         * @param newLogger the new logger to edit to
         */
        protected PairLogLoggerEditCommand(PairLog pairLog, Person newLogger) {
            this.pLog = pairLog;
            this.logger = newLogger;
            this.oldLogger = pairLog.getLogger();
        }


        /**
         * Executes/Redoes the changes of the pair log logger edit
         */
        public void execute() {
            pLog.logger = logger;
        }


        /**
         * Undoes the changes of the pair log logger edit
         */
        public void undo() {
            pLog.logger = oldLogger;
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
     * A command class that allows the executing and undoing of PairLog description edits
     */
    private class PairLogDescriptionEditCommand implements Command {
        private PairLog pLog;
        private String description;
        private String oldDescription;

        protected PairLogDescriptionEditCommand(PairLog pairLog, String newDescription) {
            this.pLog = pairLog;
            this.description = newDescription;
            this.oldDescription = pairLog.getDescription();
        }


        /**
         * Executes/Redoes the changes of the pair log description edit
         */
        public void execute() {
            pLog.description = description;
        }


        /**
         * Undoes the changes of the pair log description edit
         */
        public void undo() {
            pLog.description = oldDescription;
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
     * A command class that allows the executing and undoing of PairLog duration edits
     */
    private class PairLogDurationEditCommand implements Command {
        private PairLog pLog;
        private double duration;
        private double oldDuration;


        protected PairLogDurationEditCommand(PairLog pairLog, double newDuration) {
            this.pLog = pairLog;
            this.duration = newDuration;
            this.oldDuration = pairLog.getDurationInMinutes();
        }


        /**
         * Executes/Redoes the changes of the pair log duration edit
         */
        public void execute() {
            task.setEffortSpent(task.getEffortSpent() - pLog.getDurationInMinutes() + duration);
            pLog.duration = duration;
        }


        /**
         * Undoes the changes of the pair log duration edit
         */
        public void undo() {
            pLog.duration = oldDuration;
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
}
