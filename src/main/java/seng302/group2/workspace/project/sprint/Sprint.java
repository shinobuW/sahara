package seng302.group2.workspace.project.sprint;

import org.w3c.dom.Element;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.release.Release;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.team.Team;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A class to hold information around scrum sprints
 */
public class Sprint extends SaharaItem {

    private Backlog backlog = null;
    private Team team = null;
    private Release release = null;
    private Set<Story> stories = new HashSet<>();

    private String goal = "Untitled Sprint/Goal";
    private String longName = "Untitled Sprint/Goal";
    private String description = "";
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = LocalDate.now().plusWeeks(2);


    /**
     * Gets the team allocated to the sprint
     * @return The team allocated to the sprint
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Gets the backlog allocated to the sprint
     * @return The backlog allocated to the sprint
     */
    public Backlog getBacklog() {
        return backlog;
    }

    /**
     * Gets the project allocated to the sprint (implicitly via the backlog)
     * @return The project allocated to the sprint
     */
    public Project getProject() {
        return backlog.getProject();
    }


    /**
     * Gets the short name/label of the sprint
     * @return The sprint goal
     */
    public String getShortName() {
        return goal;
    }

    /**
     * Gets the full name of the sprint
     * @return The long name of the sprint
     */
    public String getLongName() {
        return longName;
    }

    /**
     * Gets the description of the sprint
     * @return The description of the sprint
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the start date of the sprint
     * @return The sprint's start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Gets the end date of the sprint
     * @return The sprint's end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }


    /**
     * Gets the underlying item set in the hierarchy of sprints
     * @return A blank set (as no <b>new</b> items appear under the sprint hierarchy)
     */
    @Override
    public Set<SaharaItem> getItemsSet() {
        // Nothing new is really created under this hierarchy (yet), just referenced from somewhere else
        return new HashSet<>();
    }

    /**
     * Generate the XML report section for sprints and sprint information
     * @return The XML elements for reporting sprints
     */
    @Override
    public Element generateXML() {
        // TODO
        return null;
    }

    /**
     * Gets the string representation of a sprint
     * @return The sprint goal/label
     */
    @Override
    public String toString() {
        return goal;
    }
}
