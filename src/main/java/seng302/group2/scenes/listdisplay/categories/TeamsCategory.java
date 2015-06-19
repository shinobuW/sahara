package seng302.group2.scenes.listdisplay.categories;

import javafx.collections.ObservableList;
import seng302.group2.Global;
import seng302.group2.scenes.dialog.CreateTeamDialog;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.sceneswitch.switchStrategies.category.TeamCategoryCategorySwitchStrategy;

import java.util.HashSet;
import java.util.Set;

/**
 * A category that has the current workspace's roles as children
 * Created by Jordane on 7/06/2015.
 */
public class TeamsCategory extends Category {
    public TeamsCategory() {
        super("Teams");
        setCategorySwitchStrategy(new TeamCategoryCategorySwitchStrategy());
    }

    @Override
    public Set<TreeViewItem> getItemsSet() {
        return new HashSet<>();
    }

    @Override
    public ObservableList getChildren() {
        return Global.currentWorkspace.getTeams();
    }

    @Override
    public void showCreationDialog() {
        CreateTeamDialog.show();
    }
}
