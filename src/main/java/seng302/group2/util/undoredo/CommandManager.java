package seng302.group2.util.undoredo;

import seng302.group2.App;
import seng302.group2.scenes.MainScene;

import java.util.Stack;

/**
 * A command manager that tracks a stack of undo/redo commands, and allows their execution.
 * Created by Jordane on 7/05/2015.
 */
public class CommandManager
{
    private Stack<Command> undos = new Stack<>();
    private Stack<Command> redos = new Stack<>();

    /**
     * Executes the given command
     * @param c The command to execute
     */
    public void executeCommand(Command command)
    {
        command.execute();
        undos.push(command);
        redos.clear();
    }

    /**
     * Checks if the undo stack has items that can be undone
     * @return If the undo stack isn't empty
     */
    public boolean isUndoAvailable()
    {
        return !undos.empty();
    }

    /**
     * Undoes the last action added to the undo stack
     */
    public void undo()
    {
        if (isUndoAvailable())
        {
            Command command = undos.pop();
            command.undo();
            redos.push(command);
            MainScene.treeView.refresh();
        }
    }

    /**
     * Checks if the redo stack has items that can be redone
     * @return If the redo stack isn't empty
     */
    public boolean isRedoAvailable()
    {
        return !redos.empty();
    }

    /**
     * Redoes the last action added to the redo stack
     */
    public void redo()
    {
        if (isRedoAvailable())
        {
            Command command = redos.pop();
            command.execute();
            undos.push(command);
            MainScene.treeView.refresh();
        }
    }

    /**
     * Clears the undo and redo stacks.
     */
    public void clear()
    {
        redos.empty();
        undos.empty();
    }
}
