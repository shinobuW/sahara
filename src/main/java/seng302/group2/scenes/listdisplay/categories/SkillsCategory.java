package seng302.group2.scenes.listdisplay.categories;

import javafx.collections.ObservableList;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.Category;
import seng302.group2.scenes.sceneswitch.switchStrategies.category.SkillsCategorySwitchStrategy;

/**
 * A category that has the current workspace's roles as children
 * Created by Jordane on 7/06/2015.
 */
public class SkillsCategory extends Category
{
    public SkillsCategory()
    {
        super("Skills");
        setSwitchStrategy(new SkillsCategorySwitchStrategy());
    }

    @Override
    public ObservableList getChildren()
    {
        return Global.currentWorkspace.getSkills();
    }
}
