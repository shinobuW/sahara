package seng302.group2.scenes.listdisplay.categories;

import javafx.collections.ObservableList;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.Category;
import seng302.group2.scenes.sceneswitch.switchStrategies.category.ProjectCategorySwitchStrategy;

/**
 * A category that has the current workspace's projects as children
 * Created by Jordane on 7/06/2015.
 */
public class ProjectCategory extends Category
{
    public ProjectCategory()
    {
        super("Projects");
        setSwitchStrategy(new ProjectCategorySwitchStrategy());
    }

    @Override
    public ObservableList getChildren()
    {
        return Global.currentWorkspace.getProjects();
    }
}
