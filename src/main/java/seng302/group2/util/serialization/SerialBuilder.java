package seng302.group2.util.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.graph.GraphAdapterBuilder;
import javafx.collections.ObservableList;
import seng302.group2.project.Project;
import seng302.group2.project.skills.Skill;
import seng302.group2.project.team.Team;
import seng302.group2.project.team.person.Person;
import seng302.group2.scenes.listdisplay.TreeViewItem;

/**
 * Created by Jordane on 12/04/2015.
 */
public class SerialBuilder
{
    public static Gson getBuilder()
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        new GraphAdapterBuilder()
                .addType(TreeViewItem.class)
                .addType(ObservableList.class)
                .addType(Project.class)
                .addType(Person.class)
                .addType(Skill.class)
                .addType(Team.class)
                .registerOn(gsonBuilder);
        return gsonBuilder.create();
    }
}
