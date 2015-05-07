package seng302.group2.util.undoredo;

/**
 * The interface for undoable items that follows as an implementation of the command pattern.
 * Created by Jordane on 7/05/2015.
 */
public interface Command
{
    public void execute();
    public void undo();
}