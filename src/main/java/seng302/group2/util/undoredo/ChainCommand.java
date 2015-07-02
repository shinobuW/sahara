package seng302.group2.util.undoredo;

import seng302.group2.scenes.listdisplay.TreeViewItem;

import java.util.Set;

/**
 * Created by jml168 on 2/07/15.
 */
public class ChainCommand implements Command {

    Command innerCommand;

    @Override
    public void execute() {
        innerCommand.execute();
    }

    @Override
    public void undo() {
        innerCommand.undo();
    }

    @Override
    public boolean map(Set<TreeViewItem> stateObjects) {
        return innerCommand.map(stateObjects);
    }
}
