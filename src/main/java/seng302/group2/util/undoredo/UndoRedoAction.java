/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.undoredo;

import seng302.group2.util.undoredo.UndoRedoPerformer.UndoRedoProperty;

/**
 * An action class that holds the property state before and after a value change.
 * @author jml168
 */
@Deprecated
public class UndoRedoAction
{
    private UndoRedoProperty property;
    private Object value;
    
    public UndoRedoAction(UndoRedoProperty property, Object value)
    {
        this.property = property;
        this.value = value;
    }

    
    /**
     * Gets the changed property of the undo/redo action.
     * @return The property that has been changed
     */
    public UndoRedoProperty getProperty()
    {
        return property;
    }

    
    /**
     * Gets the changed value of the undo/redo property.
     * @return The change value of the property that has been changed
     */
    public Object getValue()
    {
        return value;
    }

}
