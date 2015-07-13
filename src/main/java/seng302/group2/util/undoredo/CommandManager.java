package seng302.group2.util.undoredo;

import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.workspace.SaharaItem;

import java.util.Set;
import java.util.Stack;

/**
 * A command manager that tracks a stack of undo/redo commands, and allows their execution.
 * Created by Jordane on 7/05/2015.
 */
public class CommandManager {
    private Stack<Command> undos = new Stack<>();
    private Stack<Command> redos = new Stack<>();
    private Command lastSaveCommand = null;

    /**
     * sets the local undo Stack
     *
     * @param undos Current stack of undo commands
     */
    public void setUndos(Stack<Command> undos) {
        this.undos = undos;
        //System.out.println("added: " + command.toString());
    }


    /**
     * Executes the given command
     *
     * @param command The command to execute
     */
    public void executeCommand(Command command) {
        Global.setCurrentWorkspaceChanged();
        command.execute();
        undos.push(command);
        redos.clear();
        //System.out.println("added: " + command.toString());
    }

    /**
     * Checks if the undo stack has items that can be undone
     *
     * @return If the undo stack isn't empty
     */
    public boolean isUndoAvailable() {
        boolean available = true;

        // The case that we just opened a ws and created the saveTrackerCommand
        if (undos.isEmpty()) {
            available = false;
        }
        else if (undos.size() == 1 && undos.peek() == lastSaveCommand) {
            Global.setCurrentWorkspaceUnchanged();
            available = false;
        }

        return available;
    }

    /**
     * Undoes the last action added to the undo stack
     */
    public void undo() {
        if (isUndoAvailable()) {
            if (undos.peek().getClass() == SaveTrackerCommand.class) {
                // Bring over the save tracker
                redos.push(undos.pop());
                if (redos.peek() == lastSaveCommand) {
                    Global.setCurrentWorkspaceUnchanged();
                }
                if (!isUndoAvailable()) {
                    return;  // Return if it's the last item
                }
                else {
                    undo();
                    return;
                }
            }


            // Normal undo
            Command command = undos.pop();
            //System.out.println("undo: " + command);
            command.undo();

            redos.push(command);

            // Check if we are back to the last save
            if (!undos.isEmpty() && undos.peek() == lastSaveCommand) {
                Global.setCurrentWorkspaceUnchanged();
            }
            else {
                Global.setCurrentWorkspaceChanged();
            }

            refreshTree();
        }
    }


    /**
     * Refreshes the tree in the case that the application is running
     */
    public void refreshTree() {
        try {
            App.mainPane.refreshTree();
        }
        catch (ExceptionInInitializerError | NoClassDefFoundError | NullPointerException e) {
            // Caused because it was called without the context of the application running, ie. Unit
            // tests.
        }
    }


    /**
     * Checks if the redo stack has items that can be redone
     *
     * @return If the redo stack isn't empty
     */
    public boolean isRedoAvailable() {
        return !redos.isEmpty();
    }


    /**
     * Redoes the last action added to the redo stack
     */
    public void redo() {
        if (isRedoAvailable()) {
            if (redos.peek().getClass() == SaveTrackerCommand.class) {
                undos.push(redos.pop());
                if (!isRedoAvailable()) {
                    return;
                }
            }

            Command command = redos.pop();
            //System.out.println("redo: " + command);
            command.execute();
            undos.push(command);

            if (isRedoAvailable() && redos.peek().getClass() == SaveTrackerCommand.class) {
                undos.push(redos.pop());
                if (undos.peek() == lastSaveCommand) {
                    Global.setCurrentWorkspaceUnchanged();
                }
                else {
                    Global.setCurrentWorkspaceChanged();
                }
            }
            else {
                Global.setCurrentWorkspaceChanged();
            }

            refreshTree();
        }
    }

    /**
     * Finds the number of undo items on the stack
     *
     * @return the size of the undo stack
     */
    public int numUndos() {
        return undos.size();
    }


    /**
     * Finds the number of redo items on the stack
     *
     * @return the size of the redo stack
     */
    public int numRedos() {
        return redos.size();
    }


    /**
     * Clears the undo and redo stacks.
     */
    public void clear() {
        redos = new Stack<>();
        undos = new Stack<>();
    }


    /**
     * Adds an empty command that tracks the last save (unsaved changes)
     */
    public void trackSave() {
        Command lastSave = new SaveTrackerCommand();
        lastSaveCommand = lastSave;
        this.executeCommand(lastSave);
        Global.setCurrentWorkspaceUnchanged();
    }


    /**
     * Clones and returns the current undo stack
     * @return A clone of the current undo stack
     */
    public Stack<Command> getUndoCloneStack() {
        return (Stack<Command>) undos.clone();
    }


    /**
     * A fake command to keep track of saves
     */
    private class SaveTrackerCommand implements Command {

        @Override
        public void execute() {
            // Do nothing
        }

        @Override
        public void undo() {
            // Do nothing
        }

        @Override
        public boolean map(Set<SaharaItem> stateObjects) {
            // Do nothing
            return true;
        }
    }
}
