package seng302.group2.workspace.team;

import seng302.group2.workspace.project.Project;

import java.util.Date;

/**
 *
 * Created by Jordane Lew & David Moseley on 7/05/15.
 */
public class Allocation
{
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


    public Date getEndDate()
    {
        return this.endDate;
    }

    public Date getStartDate()
    {
        return this.startDate;
    }

    public Project getProject()
    {
        return this.project;
    }

    public Team getTeam()
    {
        return this.team;
    }



    public static AllocationStatus isCurrentAllocation(Allocation alloc)
    {
        Date now = new Date();

        if (alloc.getEndDate() != null)
        {
            if (now.after(alloc.getEndDate()))
            {
                return AllocationStatus.PAST; // Is a past allocation
            }
            if (!now.before(alloc.getStartDate()) && !now.after(alloc.getEndDate()))
            {
                return AllocationStatus.CURRENT; // Is a current allocation
            }
        }

        if (!now.before(alloc.getStartDate()))
        {
            return AllocationStatus.CURRENT; // Is a current allocation
        }
        else
        {
            return AllocationStatus.FUTURE; // Is a future allocation
        }
    }
}
