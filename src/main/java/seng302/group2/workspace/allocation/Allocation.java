package seng302.group2.workspace.allocation;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.w3c.dom.Element;
import seng302.group2.Global;
import seng302.group2.util.reporting.ReportGenerator;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.team.Team;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A class that represents allocations between teams and projects
 * Created by Jordane Lew and David Moseley on 7/05/15.
 */
public class Allocation extends SaharaItem implements Serializable, Comparable<Allocation> {
    private LocalDate startDate;
    private LocalDate endDate;
    private Project project;
    private Team team;

    public Allocation(Project project, Team team, LocalDate startDate, LocalDate endDate) {
        this.project = project;
        this.team = team;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Sets the start date of the allocation
     *
     * @param date Start date to set
     */
    public void editStartDate(LocalDate date) {
        AllocationEditCommand allocEdit = new AllocationEditCommand(this, date, endDate);
        Global.commandManager.executeCommand(allocEdit);

    }

    /**
     * Sets the end date of the allocation
     *
     * @param date End date to set
     */
    public void editEndDate(LocalDate date) {
        AllocationEditCommand allocEdit = new AllocationEditCommand(this, this.startDate, date);
        Global.commandManager.executeCommand(allocEdit);

    }

    /**
     * Gets the allocation end date
     *
     * @return The allocation end date
     */
    public LocalDate getEndDate() {
        return this.endDate;
    }

    /**
     * Gets the allocation start date
     *
     * @return The allocation start date
     */
    public LocalDate getStartDate() {
        return this.startDate;
    }

    /**
     * Gets the project in the assignment
     *
     * @return The project in the assignment
     */
    public Project getProject() {
        return this.project;
    }

    /**
     * Gets the team in the assignment
     *
     * @return The team in the assignment
     */
    public Team getTeam() {
        return this.team;
    }

    /**
     * Checks if the allocation is currently active
     *
     * @return A status representing the active time state of the allocation
     */
    public AllocationStatus getTimeState() {
        LocalDate now = LocalDate.now();

        if (this.getEndDate() != null) {
            if (now.isAfter(this.getEndDate())) {
                return AllocationStatus.PAST; // Is a past allocation
            }
            if (!now.isBefore(this.getStartDate()) && !now.isAfter(this.getEndDate())) {
                return AllocationStatus.CURRENT; // Is a current allocation
            }
        }

        if (!now.isBefore(this.getStartDate())) {
            return AllocationStatus.CURRENT; // Is a current allocation
        }
        else {
            return AllocationStatus.FUTURE; // Is a future allocation
        }
    }

    /**
     * Checks if the allocation is currently in place
     *
     * @return True if the allocation is current
     */
    public boolean isCurrentAllocation() {
        return (this.getTimeState() == AllocationStatus.CURRENT);
    }

    /**
     * Deleted the allocation and removes them from project and team
     */
    public void delete() {
        Command deleteAlloc = new DeleteAllocationCommand(this, this.project, this.team);
        Global.commandManager.executeCommand(deleteAlloc);
    }

    /**
     * Method for creating an XML element for the Allocation within report generation
     * @return element for XML generation
     */
    @Override
    public Element generateXML() {
        Element allocationElement = ReportGenerator.doc.createElement("allocation");

        //WorkSpace Elements
        Element allocatedTeam = ReportGenerator.doc.createElement("team-name");
        allocatedTeam.appendChild(ReportGenerator.doc.createTextNode(team.toString()));
        allocationElement.appendChild(allocatedTeam);

        Element allocatedProject = ReportGenerator.doc.createElement("project-name");
        allocatedProject.appendChild(ReportGenerator.doc.createTextNode(project.toString()));
        allocationElement.appendChild(allocatedProject);

        Element allocationStartDate = ReportGenerator.doc.createElement("allocation-start-date");
        allocationStartDate.appendChild(ReportGenerator.doc.createTextNode(getStartDate().format(
                Global.dateFormatter)));
        allocationElement.appendChild(allocationStartDate);

        Element allocationEndDate = ReportGenerator.doc.createElement("allocation-end-date");
        allocationEndDate.appendChild(ReportGenerator.doc.createTextNode(getEndDate().format(
                Global.dateFormatter)));
        allocationElement.appendChild(allocationEndDate);

        return allocationElement;
    }

    /**
     * Compares this allocation to another based on the start dates of the allocations
     * @return the comparison of the start dates of the two allocations
     */
    @Override
    public int compareTo(Allocation allocation) {
        LocalDate allocationStartDate = this.getStartDate();
        LocalDate allocation2StarDate = allocation.getStartDate();
        return allocationStartDate.compareTo(allocation2StarDate);
    }

    @Override
    public boolean equivalentTo(Object object) {
        if (!(object instanceof Allocation)) {
            return false;
        }
        if (object == this) {
            return true;
        }

        Allocation alloc = (Allocation)object;
        return new EqualsBuilder()
                .append(startDate, alloc.startDate)
                .append(endDate, alloc.endDate)
                .append(project, alloc.project)
                .append(team, alloc.project)
                .isEquals();
    }


    @Override
    public Set<SaharaItem> getItemsSet() {
        return new HashSet<>();
    }


    /**
     * An enumeration for allocation statuses
     */
    public enum AllocationStatus {
        CURRENT,
        PAST,
        FUTURE
    }


//    /**
//     * A comparator that returns the comparison of two story's priorities
//     */
//    public static Comparator<Allocation> AllocationStartDateComparator = (alloc1, alloc2) -> {
//        return alloc2.getEndDate().compareTo(alloc1.getStartDate());
//    };
//
//
//    /**
//     * A comparator that returns the comparison of two story's short names
//     */
//    public static Comparator<Allocation> AllocationEndDateComparator = (alloc1, alloc2) -> {
//        return alloc1.getEndDate().compareTo(alloc2.getEndDate());
//    };

    private class AllocationEditCommand implements Command {
        private Allocation allocation;

        private LocalDate startDate;
        private LocalDate endDate;

        private LocalDate oldStartDate;
        private LocalDate oldEndDate;


        protected AllocationEditCommand(Allocation alloc, LocalDate newStartDate,
                                        LocalDate newEndDate) {
            this.allocation = alloc;

            this.startDate = newStartDate;
            this.endDate = newEndDate;


            this.oldStartDate = allocation.startDate;
            this.oldEndDate = allocation.endDate;
        }

        /**
         * Executes/Redoes the changes of the allocation edit
         */
        public void execute() {
            allocation.startDate = startDate;
            allocation.endDate = endDate;
            Collections.sort(allocation.getProject().getTeamAllocations());
        }

        /**
         * Undoes the changes of the person edit
         */
        public void undo() {
            allocation.startDate = oldStartDate;
            allocation.endDate = oldEndDate;
            Collections.sort(allocation.getProject().getTeamAllocations());
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
                if (item.equivalentTo(allocation)) {
                    this.allocation = (Allocation) item;
                    mapped = true;
                }
            }
            return mapped;
        }
    }


    private class DeleteAllocationCommand implements Command {
        private Allocation allocation;
        private Project project;
        private Team team;

        DeleteAllocationCommand(Allocation allocation, Project project, Team team) {
            this.allocation = allocation;
            this.project = project;
            this.team = team;
        }

        public void execute() {
            team.getProjectAllocations().remove(allocation);
            project.getTeamAllocations().remove(allocation);
        }

        public void undo() {
            team.getProjectAllocations().add(allocation);
            project.getTeamAllocations().add(allocation);
        }

        /**
         * Searches the stateObjects to find an equal model class to map to
         * @param stateObjects A set of objects to search through
         * @return If the item was successfully mapped
         */
        @Override
        public boolean map(Set<SaharaItem> stateObjects) {
            boolean mapped_alloc = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(allocation)) {
                    this.allocation = (Allocation) item;
                    mapped_alloc = true;
                }
            }
            boolean mapped_proj = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(project)) {
                    this.project = (Project) item;
                    mapped_proj = true;
                }
            }
            boolean mapped_team = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(team)) {
                    this.team = (Team) item;
                    mapped_team = true;
                }
            }
            return mapped_alloc && mapped_proj && mapped_team;
        }
    }
}
