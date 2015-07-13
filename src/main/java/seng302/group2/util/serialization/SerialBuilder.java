package seng302.group2.util.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.collections.ObservableList;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.workspace.Workspace;
import seng302.group2.workspace.allocation.Allocation;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.release.Release;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

import java.time.LocalDate;

/**
 * Created by Jordane on 12/04/2015.
 */
public class SerialBuilder {
    public static Gson getBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        new GraphAdapterBuilder()
                .addType(SaharaItem.class)
                .addType(ObservableList.class)
                .addType(Workspace.class)
                .addType(Person.class)
                .addType(Skill.class)
                .addType(Team.class)
                .addType(Project.class)
                .addType(Release.class)
                .addType(Role.class)
                .addType(Allocation.class)
                .addType(Story.class)
                .addType(LocalDate.class)
                .addType(Backlog.class)

                        //TODO add any new classes

                .registerOn(gsonBuilder);
        return gsonBuilder.create();
    }
}
