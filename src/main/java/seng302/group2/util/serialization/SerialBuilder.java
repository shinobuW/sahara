package seng302.group2.util.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.collections.ObservableList;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

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
                .addType(Workspace.class)
                .addType(Person.class)
                .addType(Skill.class)
                .addType(Team.class)
                .registerOn(gsonBuilder);
        return gsonBuilder.create();
    }
}
