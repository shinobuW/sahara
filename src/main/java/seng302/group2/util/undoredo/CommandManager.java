package seng302.group2.util.undoredo;

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
     * @param command The command to execute
     */
    public void executeCommand(Command command)
    {
        command.execute();
        undos.push(command);
        redos.clear();
        System.out.println("added: " + command.toString());
    }

    /**
     * Checks if the undo stack has items that can be undone
     * @return If the undo stack isn't empty
     */
    public boolean isUndoAvailable()
    {
        System.out.println("undos avail?" + undos);
        return !undos.isEmpty();
    }

    /**
     * Undoes the last action added to the undo stack
     */
    public void undo()
    {
        if (isUndoAvailable())
        {
            Command command = undos.pop();
            System.out.println("undo: " + command);
            command.undo();
            redos.push(command);
            try
            {
                MainScene.treeView.refresh();
            }
            catch (ExceptionInInitializerError | NoClassDefFoundError e)
            {
                // Thrown in tests because app is not actually running
                // Worst case of catch: tree doesn't update properly
            }
        }
    }

    /**
     * Checks if the redo stack has items that can be redone
     * @return If the redo stack isn't empty
     */
    public boolean isRedoAvailable()
    {
        return !redos.isEmpty();
    }

    /**
     * Redoes the last action added to the redo stack
     */
    public void redo()
    {
        if (isRedoAvailable())
        {
            Command command = redos.pop();
            System.out.println("redo: " + command);
            command.execute();
            undos.push(command);
            try
            {
                MainScene.treeView.refresh();
            }
            catch (ExceptionInInitializerError | NoClassDefFoundError e)
            {
                // Thrown in tests because app is not actually running
                // Worst case of catch: tree doesn't update properly
            }
        }
    }

    /**
     * Finds the number of undo items on the stack
     * @return the size of the undo stack
     */
    public int numUndos()
    {
        return undos.size();
    }

    /**
     * Finds the number of redo items on the stack
     * @return the size of the redo stack
     */
    public int numRedos()
    {
        return redos.size();
    }

    /**
     * Clears the undo and redo stacks.
     */
    public void clear()
    {
        redos = new Stack<>();
        undos = new Stack<>();
    }
}
