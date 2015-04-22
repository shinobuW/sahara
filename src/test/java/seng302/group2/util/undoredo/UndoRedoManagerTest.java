package seng302.group2.util.undoredo;

import junit.framework.TestCase;
import seng302.group2.workspace.person.Person;

import java.util.Stack;

/**
 * A JUnit test class for the Undo/Redo Manager
 */
public class UndoRedoManagerTest extends TestCase {

    UndoRedoManager manager = new UndoRedoManager();

    Person firstPerson = new Person();

    UndoableItem item1 = new UndoableItem(firstPerson,
            new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_SHORTNAME, "nameAfterAction"),
            new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_ADD, "nameBeforeAction"));

    UndoableItem item2 = new UndoableItem(firstPerson,
            new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_FIRSTNAME, "firstName"),
            new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_ADD, "changedFirstName"));

    /**
     * The setup for each test
     * @throws Exception
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        manager.emptyAll();
    }

    public void testEmptyAll() throws Exception {
        manager.getRedoStack().add(item1);
        manager.getUndoStack().add(item2);

        manager.emptyAll();

        assertEquals(new Stack<>(), manager.getUndoStack());
        assertEquals(new Stack<>(), manager.getRedoStack());
    }

    public void testCanUndo() throws Exception {
        manager.add(item1);
        assertTrue(manager.canUndo());
        manager.emptyAll();
        assertFalse(manager.canUndo());
    }

    public void testCanRedo() throws Exception {
        manager.add(item1);
        manager.undo();
        assertTrue(manager.canRedo());
        manager.emptyAll();
        assertFalse(manager.canRedo());
    }

    public void testUndo() throws Exception {
        manager.add(item1);
        manager.add(item2);

        manager.undo();

        Stack undoEx = new Stack<>();
        undoEx.add(item1);

        Stack redoEx = new Stack<>();
        redoEx.add(item2);

        assertEquals(undoEx, manager.getUndoStack());
        assertEquals(redoEx, manager.getRedoStack());
    }

    public void testRedo() throws Exception {
        manager.getRedoStack().add(item1);
        manager.getRedoStack().add(item2);

        manager.redo();

        Stack undoEx = new Stack<>();
        Stack redoEx = new Stack<>();
        redoEx.add(item1);
        undoEx.add(item2);

        assertEquals(undoEx, manager.getUndoStack());
        assertEquals(redoEx, manager.getRedoStack());
    }

    public void testAdd() throws Exception {
        manager.add(item1);

        Stack equiv = new Stack<UndoableItem>();
        equiv.push(item1);

        assertNotSame(new Stack<UndoableItem>(), manager.getUndoStack());
        assertEquals(equiv, manager.getUndoStack());
    }
}