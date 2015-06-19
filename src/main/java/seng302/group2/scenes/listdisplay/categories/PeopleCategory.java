package seng302.group2.scenes.listdisplay.categories;

import javafx.collections.ObservableList;
import seng302.group2.Global;
import seng302.group2.scenes.dialog.CreatePersonDialog;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.sceneswitch.switchStrategies.category.PersonCategorySwitchStrategy;

import java.util.HashSet;
import java.util.Set;

/**
 * A category that has the current workspace's people as children
 * Created by Jordane on 7/06/2015.
 */
public class PeopleCategory extends Category {
    public PeopleCategory() {
        super("People");
        setCategorySwitchStrategy(new PersonCategorySwitchStrategy());
    }

    @Override
    public Set<TreeViewItem> getItemsSet() {
        return new HashSet<>();
    }

    @Override
    public ObservableList getChildren() {
        return Global.currentWorkspace.getPeople();
    }

    @Override
    public void showCreationDialog() {
        CreatePersonDialog.show();
    }
}
