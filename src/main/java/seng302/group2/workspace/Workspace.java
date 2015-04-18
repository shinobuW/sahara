/*
 * SENG302 Group 2
 */
package seng302.group2.workspace;

import seng302.group2.scenes.listdisplay.Category;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.ArrayList;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;
import seng302.group2.workspace.person.Person;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.util.serialization.SerialBuilder;
import seng302.group2.util.undoredo.UndoRedoAction;
import seng302.group2.util.undoredo.UndoRedoPerformer;
import seng302.group2.util.undoredo.UndoableItem;

/**
 * Basic workspace class that acts as the root object for Sahara and represents a real-world
 * company or work group space.
 * @author Jordane Lew (jml168)
 */
@SuppressWarnings("deprecation")
public class Workspace extends TreeViewItem implements Serializable
{
    private String shortName;
    private String longName;
    private String description;
    private String lastSaveLocation = null;

    private transient boolean hasUnsavedChanges = true;
    private static Gson gson = SerialBuilder.getBuilder();

    // Workspace elements
    private transient ObservableList<TreeViewItem> teams = observableArrayList();
    private ArrayList<Team> serializableTeams = new ArrayList<>();
    private transient ObservableList<TreeViewItem> people = observableArrayList();
    private ArrayList<Person> serializablePeople = new ArrayList<>();
    private transient ObservableList<TreeViewItem> skills = observableArrayList();
    private ArrayList<Skill> serializableSkills = new ArrayList<>();
    //private transient ObservableList<TreeViewItem> projects = observableArrayList();
    //private ArrayList<Project> serializableProjects = new ArrayList<>();


    /**
     * Enumerable save and load statuses.
     */
    public enum SaveLoadResult
    {
        SUCCESS,
        NULLWORKSPACE,
        IOEXCEPTION,
        NOFILESELECTED,     // Cancelled in save
        FILENOTFOUND        // File doesn't exist when opening
    }
    
    /**
     * Basic Workspace constructor.
     */
    public Workspace()
    {
        super("Untitled");
        this.shortName = "Untitled";
        this.longName = "Untitled Workspace";
        this.description = "A blank workspace.";
        this.serializablePeople = new ArrayList<>();
        this.serializableSkills = new ArrayList<>();
        this.serializableTeams = new ArrayList<>();
	Team temp = new Team("Unassigned People", 
					"All the people unassigned to a team");
	serializableTeams.add(temp);
	teams.add(temp);
    }
    
    
    /**
     * Basic workspace constructor.
     * @param shortName A unique short name to identify the Workspace
     * @param fullName The full Workspace name
     * @param description A description of the Workspace
     */
    public Workspace(String shortName, String fullName, String description)
    {
        super(shortName);
        this.shortName = shortName;
        this.longName = fullName;
        this.description = description;
	Team temp = new Team("Unassigned People", 
					"All the people unassigned to a team");
	serializableTeams.add(temp);
	teams.add(temp);
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="Getters">
    
    /**
     * Gets if the workspace has unsaved changes.
     * @return true or false depending if the workspace has unsaved changes
     */
    public boolean getHasUnsavedChanges()
    {
        return hasUnsavedChanges;
    }
    
    
    /**
     * Gets the workspace's short name.
     * @return The short name of the workspace
     */
    public String getShortName()
    {
        return this.shortName;
    }
    
    
    /**
     * Gets the workspace's long name.
     * @return The long name of the workspace
     */
    public String getLongName()
    {
        return this.longName;
    }
    
    
    /**
     * Gets the workspace's description.
     * @return The description of the workspace
     */
    public String getDescription()
    {
        return this.description;
    }
    
    
    /**
     * Gets the workspace's list of Persons.
     * @return The people associated with the workspace
     */
    public ObservableList<TreeViewItem> getPeople()
    {
        return this.people;
    }
    
    /**
     * Gets the workspace's list of Skills.
     * @return The skills associated with a workspace
     */
    public ObservableList<TreeViewItem> getSkills()
    {
        return this.skills;
    }
    
    /**
     * Gets the workspace's list of Teams.
     * @return The teams associated with a workspace
     */
    public ObservableList<TreeViewItem> getTeams()
    {
        return this.teams;
    }
    
    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="Setters">
    
    /**
     * Marks the workspace as not having unsaved changes.
     */
    public void setUnchanged()
    {
        this.hasUnsavedChanges = false;
    }
    
    
    /**
     * Marks the workspace as having unsaved changes.
     */
    public void setChanged()
    {
        this.hasUnsavedChanges = true;
    }
    
    
    /**
     * Sets the workspace's short name.
     * @param shortName The new short name for the workspace
     */
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }
    
    
    /**
     * Sets the workspace's long name.
     * @param longName The new long name for the workspace
     */
    public void setLongName(String longName)
    {
        this.longName = longName;
    }
    
    
    /**
     * Sets the workspace's description.
     * @param description The new description for the workspace
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    //</editor-fold>
    
    
    /**
     * Saves the current workspace as a file specified by the user.
     * @param workspace The workspace to save
     * @param saveAs If acting as save as
     * @return The corresponding SaveLoadResult status of the process
     */
    public static SaveLoadResult saveWorkspace(Workspace workspace, boolean saveAs)
    {
        // If there is no current workspace open, display a dialog and skip saving
        if (workspace == null)
        {
            Dialogs.create()
                    .title("No open workspace")
                    .message("There is currently no workspace open to save")
                    .showWarning();
                    
            return SaveLoadResult.NULLWORKSPACE;
        }
        
        workspace = Workspace.prepSerialization(workspace);
        
        if (saveAs || workspace.lastSaveLocation == null || workspace.lastSaveLocation.equals(""))
        {
            // Prime a FileChooser
            FileChooser fileChooser = new FileChooser();
            if (Global.lastSaveLocation != null && Global.lastSaveLocation != "")
            {
                System.out.println("last save dir: " + Global.lastSaveLocation);
                fileChooser.setInitialDirectory(new File(Global.lastSaveLocation));
            }
            fileChooser.setInitialFileName(workspace.shortName);
            fileChooser.setTitle("Save Workspace");
            fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Workspace Files", "*.proj")
            );

            // Open the FileChooser to choose the save location of the workspace
            File selectedFile = null;
            try
            {
                selectedFile = fileChooser.showSaveDialog(new Stage());
            }
            catch (IllegalArgumentException e)
            {
                // The file directory is invalid, try again with 'root'
                System.out.println("Bad directory");
                fileChooser.setInitialDirectory(new File("/"));
                selectedFile = fileChooser.showSaveDialog(new Stage());
            }

            if (selectedFile == null)
            {
                return SaveLoadResult.FILENOTFOUND;  // Most likely user cancellation
            }
            workspace.lastSaveLocation = selectedFile.toString();
            
            // Ensure the extension is .proj (Linux issue)
            if (!workspace.lastSaveLocation.endsWith(".proj"))
            {
                workspace.lastSaveLocation = workspace.lastSaveLocation + ".proj";
            }
        }
        
        /* GSON SERIALIZATION */
        try (Writer writer = new FileWriter(workspace.lastSaveLocation))
        {
            //Gson gson = new GsonBuilder().create();
            gson.toJson(workspace, writer);
            writer.close();
            Global.setCurrentWorkspaceUnchanged();
            Global.lastSaveLocation = Paths.get(workspace.lastSaveLocation).getParent().toString();

            //TESTING
            System.out.println("WORKSPACE LOCATION: " + workspace.lastSaveLocation);
            System.out.println("GLOBAL LOCATION: " + Global.lastSaveLocation);

            return SaveLoadResult.SUCCESS;
        }
        catch (IOException e)
        {
            Dialogs.create()
                .title("Error Saving")
                .message("An error occurred while trying to save the file")
                .showException(e);
            return SaveLoadResult.IOEXCEPTION;
        }

    }
    
    
    /**
     * Loads a workspace specified by the user into Global.currentWorkspace.
     * @return The corresponding SaveLoadResult status of the process
     */
    public static SaveLoadResult loadWorkspace()
    {
        // Prime a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Workspace");
        if (Global.lastSaveLocation != null && Global.lastSaveLocation != "")
        {
            System.out.println("last save dir: " + Global.lastSaveLocation);
            fileChooser.setInitialDirectory(new File(Global.lastSaveLocation));
        }
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Workspace Files", "*.proj")
        );
        
        // Open the FileChooser and try saving the Workspace as the user specifies
        File selectedFile = null;
        try
        {
            selectedFile = fileChooser.showOpenDialog(new Stage());
        }
        catch (IllegalArgumentException e)
        {
            // The file directory is invalid, try again with 'root'
            System.out.println("Bad directory");
            fileChooser.setInitialDirectory(new File("/"));
            selectedFile = fileChooser.showOpenDialog(new Stage());
        }

        if (selectedFile != null)
        {
            /* GSON DESERIALIZATION */
            try (Reader reader = new FileReader(selectedFile))
            {
                Global.currentWorkspace = gson.fromJson(reader, Workspace.class);
                reader.close();
            }
            catch (FileNotFoundException e)
            {
                Dialogs.create()
                    .title("File Not Found")
                    .message("The specified file could not be found.")
                    .showWarning();
                return SaveLoadResult.FILENOTFOUND;
            }
            catch (IOException e)
            {
                Dialogs.create()
                    .title("Error Loading")
                    .message("An error occurred while trying to load the file.")
                    .showException(e);
                return SaveLoadResult.IOEXCEPTION;
            }
            
            Workspace.postDeserialization();
            if (Global.appRunning())
            {
                App.refreshMainScene();
            }
            Global.undoRedoMan.emptyAll();
            return SaveLoadResult.SUCCESS;
        }
        else
        {
            return SaveLoadResult.FILENOTFOUND;  // Was null, probably cancelled action?
        }
    }
    
    
    /**
     * Adds a Person to the Workspace's list of Persons.
     * @param person The person to add
     */
    public void add(Person person)
    {
        // Add the undo action to the stack
        Global.undoRedoMan.add(new UndoableItem(
                person,
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON, null), 
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON, null)
                ));
        
        this.people.add(person);
    }


    /**
     * Removes a Person to the Workspace's list of Persons.
     * @param person The person to remove
     */
    public void remove(Person person)
    {
        // TODO: UndoRedo stack items for REMOVALS of whole people
        /*Global.undoRedoMan.add(new UndoableItem(
                person,
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON, null),
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON, null)
        ));*/

        this.people.remove(person);
    }
    
    
    /**
     * Adds a Skill to the Workspace's list of Skills.
     * @param skill The skill to add
     */
    public void add(Skill skill)
    {
        //Add the undo action to the stack
        Global.undoRedoMan.add(new UndoableItem(
                skill,
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.SKILL, null),
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.SKILL, null)
                ));
        
        this.skills.add(skill);
    }


    /**
     * Removes a Skill to the Workspace's list of Skills.
     * @param skill The skill to remove
     */
    public void remove(Skill skill)
    {
        // TODO: UndoRedo stack items for removals of whole skills
        /*Global.undoRedoMan.add(new UndoableItem(
                person,
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON, null),
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON, null)
        ));*/

        this.skills.remove(skill);
    }


    /**
     * Adds a Team to the Workspace's list of Teams.
     * @param team The team to add
     */
    public void add(Team team)
    {
        //Add the undo action to the stack
        Global.undoRedoMan.add(new UndoableItem(
                team,
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.TEAM, null),
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.TEAM, null)
                ));
        
        this.teams.add(team);
    }


    /**
     * Removes a Team to the Workspace's list of Teams.
     * @param team The team to remove
     */
    public void remove(Team team)
    {
        // TODO: UndoRedo stack items for removals of whole teams
        /*Global.undoRedoMan.add(new UndoableItem(
                person,
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON, null),
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON, null)
        ));*/

        this.skills.remove(team);
    }

    
    /**
     * Gets a list of categories of the workspace based on the workspace's lists.
     * @return A list of categories of the workspace
     */
    public ObservableList<TreeViewItem> getCategories()
    {
        // Prime the list
        ObservableList<TreeViewItem> root = observableArrayList();
        
        // Make the categories
        Category projectCategory = new Category("Projects");
        Category peopleCategory = new Category("People");
        Category teamsCategory = new Category("Teams");
        Category rolesCategory = new Category("Roles");
        Category skillCategory = new Category("Skills");
        
        // Add the categories
        root.add(projectCategory);
        root.add(peopleCategory);
        root.add(teamsCategory);
        root.add(rolesCategory);
        root.add(skillCategory);

        return root;
    }
    
    
    /**
     * Perform pre-serialization steps
     * 1) Transform ObservableLists into ArrayLists for serialization.
     * @param workspace The workspace for intended serialization
     * @return A serializable version of the given workspace
     */
    public static Workspace prepSerialization(Workspace workspace)
    {
        workspace.serializablePeople.clear();
        for (Object item : workspace.people)
        {
            workspace.serializablePeople.add((Person)item);
        }
        
        workspace.serializableSkills.clear();
        for (Object item : workspace.skills)
        {
            workspace.serializableSkills.add((Skill)item);
        }
        
        workspace.serializableTeams.clear();
        for (Object item : workspace.teams)
        {
            workspace.serializableTeams.add((Team) item);
        }
        
        // Prepare for the serialization of persons (skills)
        for (Object item : workspace.people)
        {
            Person person = (Person) item;
            person.prepSerialization();
        }

        // Prepare for the serialization of teams (people)
        for (Object item : workspace.teams)
        {
            Team team = (Team) item;
            team.prepSerialization();
        }

        // Also perform again for any other deeper observables
        
        return workspace;
    }
    
    
    /**
     * Perform post-deserialization steps (performs on Global.currentWorkspace for now).
     * 1) Transform ArrayLists back into ObservableLists
     */
    public static void postDeserialization()
    {
        Global.currentWorkspace.people = observableArrayList();
        for (Person item : Global.currentWorkspace.serializablePeople)
        {
            Global.currentWorkspace.people.add(item);
        }
        
        Global.currentWorkspace.skills = observableArrayList();
        for (Skill item : Global.currentWorkspace.serializableSkills)
        {
            Global.currentWorkspace.skills.add(item);
        }
        
        Global.currentWorkspace.teams = observableArrayList();
        for (Team item : Global.currentWorkspace.serializableTeams)
        {
            Global.currentWorkspace.teams.add(item);
        }

        // Prepare for the serialization of persons
        for (Object item : Global.currentWorkspace.serializablePeople)
        {
            Person person = (Person) item;
            person.postSerialization();
        }

        // Prepare for the serialization of teams
        for (Object item : Global.currentWorkspace.serializableTeams)
        {
            Team team = (Team) item;
            team.postSerialization();
        }


        // Also for any other deeper observables
        // eg. for (TreeViewItem item : Global.currentWorkspace.team.people) {...}

        // Unset saved changes flag, we just opened the workspace.
        Global.currentWorkspace.hasUnsavedChanges = false;
    }
    
    
    /**
     * An overridden version for the String representation of a Workspace.
     * @return The short name of the Workspace
     */
    @Override
    public String toString()
    {
        return this.shortName;
    }
    
    
    /**
     * Gets the children (categories) of the workspace.
     * @return the children (categories) of the workspace
     */
    @Override
    public ObservableList<TreeViewItem> getChildren()
    {
        return getCategories();
    }
}
