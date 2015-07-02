package seng302.group2.util.undoredo;

import seng302.group2.scenes.listdisplay.TreeViewItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by jml168 on 2/07/15.
 */
public class CumulativeCommand implements Command {

    List<Command> commands = new ArrayList<>();

    /**
     * Adds a command to the list of cumulative commands
     * @param command The command to add
     * @return The result of adding the command to the cumulative commands collection
     */
    public boolean add(Command command) {
        return commands.add(command);
    }

    @Override
    public void execute() {
        for (Command command : commands) {
            command.execute();
        }
    }

    @Override
    public void undo() {
        for (Command command : commands) {
            command.undo();
        }
    }

    @Override
    public boolean map(Set<TreeViewItem> stateObjects) {
        boolean result = true;
        for (Command command : commands) {
            if (!command.map(stateObjects)) {
                result = false;
            }
        }
        return result;
    }
}
