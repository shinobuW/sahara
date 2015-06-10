package seng302.group2.scenes.listdisplay.categories;

import javafx.collections.ObservableList;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.sceneswitch.switchStrategies.category.ProjectCategoryCategorySwitchStrategy;

import java.util.Set;

/**
 * A category that has the current workspace's projects as children
 * Created by Jordane on 7/06/2015.
 */
public class ProjectCategory extends Category {
    public ProjectCategory() {
        super("Projects");
        setCategorySwitchStrategy(new ProjectCategoryCategorySwitchStrategy());
    }

    @Override
    public Set<TreeViewItem> getItemsSet() {
        return null;
    }

    @Override
    public ObservableList getChildren() {
        return Global.currentWorkspace.getProjects();
    }
}
