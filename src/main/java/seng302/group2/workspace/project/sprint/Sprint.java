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
 * Created by jml168 on 26/07/15.
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




    public Team getTeam() {
        return team;
    }

    public Backlog getBacklog() {
        return backlog;
    }

    public Project getProject() {
        return backlog.getProject();
    }




    public String getShortName() {
        return goal;
    }

    public String getLongName() {
        return longName;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }




    @Override
    public Set<SaharaItem> getItemsSet() {
        // Nothing new is really created under this hierarchy (yet), just referenced from somewhere else
        return new HashSet<>();
    }

    @Override
    public Element generateXML() {
        // TODO
        return null;
    }

    @Override
    public String toString() {
        return goal;
    }
}
