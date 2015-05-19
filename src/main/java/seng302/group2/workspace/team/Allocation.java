package seng302.group2.workspace.team;

import javafx.collections.ObservableList;
import seng302.group2.Global;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.skills.Skill;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A class that represents allocations between teams and projects
 * Created by Jordane Lew and David Moseley on 7/05/15.
 */
public class Allocation implements Serializable
{
    /**
     * An enumeration for allocation statuses
     */
    public enum AllocationStatus
    {
        CURRENT,
        PAST,
        FUTURE
    }


    private LocalDate startDate;
    private LocalDate endDate;
    private Project project;
    private Team team;

    public Allocation(Project project, Team team, LocalDate startDate, LocalDate endDate)
    {
        this.project = project;
        this.team = team;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Sets the start date of the allocation
     * @param date Start date to set
     */
    public void editStartDate(LocalDate date)
    {
        this.startDate = date;

        AllocationEditCommand allocEdit = new AllocationEditCommand(this, date, endDate);
        Global.commandManager.executeCommand(allocEdit);

    }

    /**
     * Sets the end date of the allocation
     * @param date End date to set
    */
    public void editEndDate(LocalDate date)
    {
        this.endDate = date;

        AllocationEditCommand allocEdit = new AllocationEditCommand(this, startDate, date);
        Global.commandManager.executeCommand(allocEdit);

    }

    /**
     * Gets the allocation end date
     * @return The allocation end date
     */
    public LocalDate getEndDate()
    {
        return this.endDate;
    }


    /**
     * Gets the allocation start date
     * @return The allocation start date
     */
    public LocalDate getStartDate()
    {
        return this.startDate;
    }

    /**
     * Gets the project in the assignment
     * @return The project in the assignment
     */
    public Project getProject()
    {
        return this.project;
    }

    /**
     * Gets the team in the assignment
     * @return The team in the assignment
     */
    public Team getTeam()
    {
        return this.team;
    }


    /**
     * Checks if the allocation is currently active
     * @return A status representing the active time state of the allocation
     */
    public AllocationStatus getTimeState()
    {
        LocalDate now = LocalDate.now();

        if (this.getEndDate() != null)
        {
            if (now.isAfter(this.getEndDate()))
            {
                return AllocationStatus.PAST; // Is a past allocation
            }
            if (!now.isBefore(this.getStartDate()) && !now.isAfter(this.getEndDate()))
            {
                return AllocationStatus.CURRENT; // Is a current allocation
            }
        }

        if (!now.isBefore(this.getStartDate()))
        {
            return AllocationStatus.CURRENT; // Is a current allocation
        }
        else
        {
            return AllocationStatus.FUTURE; // Is a future allocation
        }
    }


    /**
     * Checks if the allocation is currently in place
     * @return True if the allocation is current
     */
    public boolean isCurrentAllocation()
    {
        return (this.getTimeState() == AllocationStatus.CURRENT);
    }

    private class AllocationEditCommand implements Command
    {
        private Allocation allocation;

        private LocalDate startDate;
        private LocalDate endDate;

        private LocalDate oldStartDate;
        private LocalDate oldEndDate;

        private ObservableList<Skill> oldSkills;

        protected AllocationEditCommand(Allocation alloc, LocalDate newStartDate,
                                        LocalDate newEndDate)
        {
            this.allocation = alloc;

            this.startDate = newStartDate;
            this.endDate = newEndDate;


            this.oldStartDate = allocation.startDate;
            this.oldEndDate = allocation.endDate;
        }

        /**
         * Executes/Redoes the changes of the allocation edit
         */
        public void execute()
        {
            allocation.startDate = startDate;
            allocation.endDate = endDate;
        }

        /**
         * Undoes the changes of the person edit
         */
        public void undo()
        {
            allocation.startDate = oldStartDate;
            allocation.endDate = oldEndDate;
        }
    }
}
