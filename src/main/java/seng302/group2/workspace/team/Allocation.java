package seng302.group2.workspace.team;

import seng302.group2.workspace.project.Project;

import java.util.Date;

/**
 * A class that represents allocations between teams and projects
 * Created by Jordane Lew and David Moseley on 7/05/15.
 */
public class Allocation
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


    private Date startDate;
    private Date endDate;
    private Project project;
    private Team team;


    /**
     * Gets the allocation end date
     * @return The allocation end date
     */
    public Date getEndDate()
    {
        return this.endDate;
    }


    /**
     * Gets the allocation start date
     * @return The allocation start date
     */
    public Date getStartDate()
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
        Date now = new Date();

        if (this.getEndDate() != null)
        {
            if (now.after(this.getEndDate()))
            {
                return AllocationStatus.PAST; // Is a past allocation
            }
            if (!now.before(this.getStartDate()) && !now.after(this.getEndDate()))
            {
                return AllocationStatus.CURRENT; // Is a current allocation
            }
        }

        if (!now.before(this.getStartDate()))
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
}