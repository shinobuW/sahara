package seng302.group2.scenes.listdisplay.categories;

import javafx.collections.ObservableList;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.Category;
import seng302.group2.scenes.sceneswitch.switchStrategies.category.TeamCategorySwitchStrategy;

/**
 * A category that has the current workspace's roles as children
 * Created by Jordane on 7/06/2015.
 */
public class TeamsCategory extends Category
{
    public TeamsCategory()
    {
        super("Teams");
        setSwitchStrategy(new TeamCategorySwitchStrategy());
    }

    @Override
    public ObservableList getChildren()
    {
        return Global.currentWorkspace.getTeams();
    }
}
