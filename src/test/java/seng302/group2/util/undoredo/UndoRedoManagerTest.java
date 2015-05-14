package seng302.group2.util.undoredo;

import junit.framework.TestCase;
import seng302.group2.workspace.person.Person;

import java.util.Stack;

/**
 * A JUnit test class for the Undo/Redo Manager
 */
@Deprecated
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
     * @throws Exception superclass setUp exception
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        manager.emptyAll();
    }


    /**
     * Tests that the undo and redo stacks are emptied properly
     */
    public void testEmptyAll()
    {
        manager.getRedoStack().add(item1);
        manager.getUndoStack().add(item2);

        manager.emptyAll();

        assertEquals(new Stack<>(), manager.getUndoStack());
        assertEquals(new Stack<>(), manager.getRedoStack());
    }


    /**
     * Tests that the canUndo method returns expected results
     */
    public void testCanUndo()
    {
        manager.add(item1);
        assertTrue(manager.canUndo());
        manager.emptyAll();
        assertFalse(manager.canUndo());
    }


    /**
     * Tests that the canRedo method returns expected results
     */
    public void testCanRedo()
    {
        manager.add(item1);
        manager.undo();
        assertTrue(manager.canRedo());
        manager.emptyAll();
        assertFalse(manager.canRedo());
    }


    /**
     * Tests that the undo functionality works as expected
     */
    public void testUndo()
    {
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

    /**
     * Tests that the redo functionality works as expected
     */
    public void testRedo()
    {
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

    /**
     * Tests that items are added to the undo/redo stack(s) properly
     */
    public void testAdd()
    {
        manager.add(item1);

        Stack equiv = new Stack<UndoableItem>();
        equiv.push(item1);

        assertNotSame(new Stack<UndoableItem>(), manager.getUndoStack());
        assertEquals(equiv, manager.getUndoStack());
    }
    
    
//    /**
//     * A simple test to ensure the Undo/Redo functionality for person edit is working.
//     */
//    public void testPersonEdit()
//    {
//        TestClassUndoRedoManager testManager = new TestClassUndoRedoManager();
//        ArrayList<UndoableItem> undoActionsTest = new ArrayList<>();
//
//        Person personEdit = new Person("btm38", "Bronson", "McNaughton", "btm38@uclive.ac.nz",
//            "The coolest guy in the world!", new Date(19/12/1994));
//
//        UndoableItem shortNameEdit = new UndoableItem(personEdit,
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_SHORTNAME, personEdit.getShortName()),
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_SHORTNAME, "amc123"));
//        undoActionsTest.add(shortNameEdit);
//
//        UndoableItem firstNameEdit = new UndoableItem(personEdit,
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_FIRSTNAME, personEdit.getFirstName()),
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_FIRSTNAME, "Angus"));
//        undoActionsTest.add(firstNameEdit);
//
//        UndoableItem lastNameEdit = new UndoableItem(personEdit,
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_LASTNAME, personEdit.getLastName()),
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_LASTNAME, "McGurkinshaw"));
//        undoActionsTest.add(lastNameEdit);
//
//        UndoableItem emailEdit = new UndoableItem(personEdit,
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_EMAIL, personEdit.getEmail()),
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_EMAIL, "AngMcG@gmail.com"));
//        undoActionsTest.add(emailEdit);
//
//        UndoableItem descriptionEdit = new UndoableItem(personEdit,
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_DESCRIPTION, personEdit.getDescription()),
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_DESCRIPTION, "Who is Angus McGurkinshaw? Nobody really knows..."));
//        undoActionsTest.add(descriptionEdit);
//
//        testManager.add(new UndoableItem(personEdit,
//            new UndoRedoAction(
//                UndoRedoPerformer.UndoRedoProperty.PERSON_EDIT,
//                undoActionsTest),
//            new UndoRedoAction(
//                UndoRedoPerformer.UndoRedoProperty.PERSON_EDIT,
//                undoActionsTest)
//        ));
//
//        personEdit.setShortName("amc123");
//        personEdit.setFirstName("Angus");
//        personEdit.setLastName("McGurkinshaw");
//        personEdit.setEmail("AngMcG@gmail.com");
//        personEdit.setDescription("Who is Angus McGurkinshaw? Nobody really knows...");
//
//        testManager.undo();
//
//        assertEquals("btm38", personEdit.getShortName());
//        assertEquals("Bronson", personEdit.getFirstName());
//        assertEquals("McNaughton", personEdit.getLastName());
//        assertEquals("btm38@uclive.ac.nz", personEdit.getEmail());
//        assertEquals("The coolest guy in the world!", personEdit.getDescription());
//
//        testManager.redo();
//
//        assertEquals("amc123", personEdit.getShortName());
//        assertEquals("Angus", personEdit.getFirstName());
//        assertEquals("McGurkinshaw", personEdit.getLastName());
//        assertEquals("AngMcG@gmail.com", personEdit.getEmail());
//        assertEquals("Who is Angus McGurkinshaw? Nobody really knows...", personEdit.getDescription());
//    }
//
//
//    /**
//     * A simple test to ensure the Undo/Redo functionality for workspace edit is working.
//     */
//    public void testWorkspaceEdit()
//    {
//        TestClassUndoRedoManager testManager = new TestClassUndoRedoManager();
//        ArrayList<UndoableItem> undoActionsTest = new ArrayList<>();
//
//        Workspace workspaceEdit = new Workspace("Original Short Name", "Original Long Name", "Original Description");
//
//        UndoableItem shortNameEdit = new UndoableItem(workspaceEdit,
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.WORKSPACE_SHORTNAME, workspaceEdit.getShortName()),
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.WORKSPACE_SHORTNAME, "New Short Name"));
//        undoActionsTest.add(shortNameEdit);
//
//        UndoableItem longNameEdit = new UndoableItem(workspaceEdit,
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.WORKSPACE_LONGNAME, workspaceEdit.getLongName()),
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.WORKSPACE_LONGNAME, "New Long Name"));
//        undoActionsTest.add(longNameEdit);
//
//        UndoableItem descriptionEdit = new UndoableItem(workspaceEdit,
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.WORKSPACE_DESCRIPTION, workspaceEdit.getDescription()),
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.WORKSPACE_DESCRIPTION, "New Description"));
//        undoActionsTest.add(descriptionEdit);
//
//        testManager.add(new UndoableItem(workspaceEdit,
//            new UndoRedoAction(
//                UndoRedoPerformer.UndoRedoProperty.WORKSPACE_EDIT,
//                undoActionsTest),
//            new UndoRedoAction(
//                UndoRedoPerformer.UndoRedoProperty.WORKSPACE_EDIT,
//                undoActionsTest)
//        ));
//
//        workspaceEdit.setShortName("New Short Name");
//        workspaceEdit.setLongName("New Long Name");
//        workspaceEdit.setDescription("New Description");
//
//
//        testManager.undo();
//
//        assertEquals("Original Short Name", workspaceEdit.getShortName());
//        assertEquals("Original Long Name", workspaceEdit.getLongName());
//        assertEquals("Original Description", workspaceEdit.getDescription());
//
//        testManager.redo();
//
//        assertEquals("New Short Name", workspaceEdit.getShortName());
//        assertEquals("New Long Name", workspaceEdit.getLongName());
//        assertEquals("New Description", workspaceEdit.getDescription());
//    }
//
//
//    /**
//     * A simple test to ensure the Undo/Redo functionality for workspace edit is working
//     */
//    public void testProjectEdit()
//    {
//        TestClassUndoRedoManager testManager = new TestClassUndoRedoManager();
//        ArrayList<UndoableItem> undoActionsTest = new ArrayList<>();
//
//        Project projectEdit = new Project("Original Short Name", "Original Long Name", "Original Description");
//
//        UndoableItem shortNameEdit = new UndoableItem(projectEdit,
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PROJECT_SHORTNAME, projectEdit.getShortName()),
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PROJECT_SHORTNAME, "New Short Name"));
//        undoActionsTest.add(shortNameEdit);
//
//        UndoableItem longNameEdit = new UndoableItem(projectEdit,
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PROJECT_LONGNAME, projectEdit.getLongName()),
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PROJECT_LONGNAME, "New Long Name"));
//        undoActionsTest.add(longNameEdit);
//
//        UndoableItem descriptionEdit = new UndoableItem(projectEdit,
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PROJECT_DESCRIPTION, projectEdit.getDescription()),
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PROJECT_DESCRIPTION, "New Description"));
//        undoActionsTest.add(descriptionEdit);
//
//        testManager.add(new UndoableItem(projectEdit,
//            new UndoRedoAction(
//                UndoRedoPerformer.UndoRedoProperty.PROJECT_EDIT,
//                undoActionsTest),
//            new UndoRedoAction(
//                UndoRedoPerformer.UndoRedoProperty.PROJECT_EDIT,
//                undoActionsTest)
//        ));
//
//        projectEdit.setShortName("New Short Name");
//        projectEdit.setLongName("New Long Name");
//        projectEdit.setDescription("New Description");
//
//
//        testManager.undo();
//
//        assertEquals("Original Short Name", projectEdit.getShortName());
//        assertEquals("Original Long Name", projectEdit.getLongName());
//        assertEquals("Original Description", projectEdit.getDescription());
//
//        testManager.redo();
//
//        assertEquals("New Short Name", projectEdit.getShortName());
//        assertEquals("New Long Name", projectEdit.getLongName());
//        assertEquals("New Description", projectEdit.getDescription());
//    }
//
//
//    /**
//     * A simple test to ensure the Undo/Redo functionality for skill edit is working.
//     */
//    public void testSkillEdit()
//    {
//        TestClassUndoRedoManager testManager = new TestClassUndoRedoManager();
//        ArrayList<UndoableItem> undoActionsTest = new ArrayList<>();
//
//        Skill skillEdit = new Skill("Original Short Name", "Original Description");
//
//        UndoableItem shortNameEdit = new UndoableItem(skillEdit,
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.SKILL_SHORTNAME, skillEdit.getShortName()),
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.SKILL_SHORTNAME, "New Short Name"));
//        undoActionsTest.add(shortNameEdit);
//
//        UndoableItem descriptionEdit = new UndoableItem(skillEdit,
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.SKILL_DESCRIPTION, skillEdit.getDescription()),
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.SKILL_DESCRIPTION, "New Description"));
//        undoActionsTest.add(descriptionEdit);
//
//        testManager.add(new UndoableItem(skillEdit,
//            new UndoRedoAction(
//                UndoRedoPerformer.UndoRedoProperty.SKILL_EDIT,
//                undoActionsTest),
//            new UndoRedoAction(
//                UndoRedoPerformer.UndoRedoProperty.SKILL_EDIT,
//                undoActionsTest)
//        ));
//
//        skillEdit.setShortName("New Short Name");
//        skillEdit.setDescription("New Description");
//
//
//        testManager.undo();
//
//        assertEquals("Original Short Name", skillEdit.getShortName());
//        assertEquals("Original Description", skillEdit.getDescription());
//
//        testManager.redo();
//
//        assertEquals("New Short Name", skillEdit.getShortName());
//        assertEquals("New Description", skillEdit.getDescription());
//    }
//
//
//    /**
//     * A simple test to ensure the Undo/Redo functionality for team edit is working.
//     */
//    public void testTeamEdit()
//    {
//        TestClassUndoRedoManager testManager = new TestClassUndoRedoManager();
//        ArrayList<UndoableItem> undoActionsTest = new ArrayList<>();
//
//        Team teamEdit = new Team("Original Short Name", "Original Description");
//
//        UndoableItem shortNameEdit = new UndoableItem(teamEdit,
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.TEAM_SHORTNAME, teamEdit.getShortName()),
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.TEAM_SHORTNAME, "New Short Name"));
//        undoActionsTest.add(shortNameEdit);
//
//        UndoableItem descriptionEdit = new UndoableItem(teamEdit,
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.TEAM_DESCRIPTION, teamEdit.getDescription()),
//        new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.TEAM_DESCRIPTION, "New Description"));
//        undoActionsTest.add(descriptionEdit);
//
//        testManager.add(new UndoableItem(teamEdit,
//            new UndoRedoAction(
//                UndoRedoPerformer.UndoRedoProperty.TEAM_EDIT,
//                undoActionsTest),
//            new UndoRedoAction(
//                UndoRedoPerformer.UndoRedoProperty.TEAM_EDIT,
//                undoActionsTest)
//        ));
//
//        teamEdit.setShortName("New Short Name");
//        teamEdit.setDescription("New Description");
//
//
//        testManager.undo();
//
//        assertEquals("Original Short Name", teamEdit.getShortName());
//        assertEquals("Original Description", teamEdit.getDescription());
//
//        testManager.redo();
//
//        assertEquals("New Short Name", teamEdit.getShortName());
//        assertEquals("New Description", teamEdit.getDescription());
//    }
}