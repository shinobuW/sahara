/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.undoredo;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * A class for managing undo and redo actions.
 * @author jml168
 */
public class UndoRedoManager
{
    private Stack<UndoableItem> undoStack;
    private Stack<UndoableItem> redoStack;
    
    
    /**
     * Basic constructor for a undo/redo manager.
     */
    public UndoRedoManager()
    {
        undoStack =  new Stack<>();
        redoStack = new Stack<>();
    }

    
    /**
     * Checks the undo action can be performed by trying to peek.
     * @return If the undo action can be performed
     */
    public boolean canUndo()
    {
        try
        {
            undoStack.peek();
            return true;
        }
        catch (EmptyStackException ex)
        {
        }
        return false;
    }
    
    
     /**
     * Checks the redo action can be performed by trying to peek.
     * @return If the redo action can be performed
     */
    public boolean canRedo()
    {
        try
        {
            redoStack.peek();
            return true;
        }
        catch (EmptyStackException ex)
        {
        }
        return false;
    }
    
    /**
     * Undoes the last action in the undo stack
     */
    public void undo()
    {
        UndoableItem item = undoStack.pop();
        UndoRedoPerformer.undo(item);
        redoStack.push(item);
    }
    
    
    /**
     * Re-does the last action in the undo stack
     */
    public void redo()
    {
        UndoableItem item = redoStack.pop();
        UndoRedoPerformer.redo(item);
        undoStack.push(item);
    }
    
    
    /**
     * Adds a new item into the undo stack
     * @param item 
     */
    public void add(UndoableItem item)
    {
        undoStack.add(item);
        redoStack.empty();
    }

}
