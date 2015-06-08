package seng302.group2.scenes.listdisplay.categories;

import javafx.collections.ObservableList;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.Category;
import seng302.group2.scenes.sceneswitch.switchStrategies.category.PersonCategoryCategorySwitchStrategy;

/**
 * A category that has the current workspace's people as children
 * Created by Jordane on 7/06/2015.
 */
public class PeopleCategory extends Category {
    public PeopleCategory() {
        super("People");
        setCategorySwitchStrategy(new PersonCategoryCategorySwitchStrategy());
    }

    @Override
    public ObservableList getChildren() {
        return Global.currentWorkspace.getPeople();
    }
}
