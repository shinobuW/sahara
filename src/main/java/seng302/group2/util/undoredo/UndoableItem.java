/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.undoredo;

/**
 * An item that holds information about objects and actions to perform undo/redo with.
 * @author jml168
 */
public class UndoableItem
{
    private Object host;
    private UndoRedoAction undoAction;
    private UndoRedoAction redoAction;
    
    
    /**
     * Basic constructor for and undoable item.
     * @param host The host object that the property of, has been changed
     * @param undoAction The value of the property before the change
     * @param redoAction  The value of the property after the change
     */
    public UndoableItem(Object host, UndoRedoAction undoAction, UndoRedoAction redoAction)
    {
        this.host = host;
        this.undoAction = undoAction;
        this.redoAction = redoAction;
    }
    
    
    /**
     * Gets the host object of the undoable item.
     * @return The host object
     */
    public Object getHost()
    {
        return host;
    }

    
    /**
     * Gets the undo action of the undoable item.
     * @return The undo action
     */
    public UndoRedoAction getUndoAction()
    {
        return undoAction;
    }

    /**
     * Gets the redo action of the undoable item.
     * @return The redo action
     */
    public UndoRedoAction getRedoAction()
    {
        return redoAction;
    }
}
