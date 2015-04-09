/*
 * SENG302 Group 2
 */
package seng302.group2.project;

import seng302.group2.scenes.listdisplay.Category;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.project.skills.Skill;
import seng302.group2.project.team.Team;
import seng302.group2.project.team.person.Person;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.util.undoredo.UndoRedoAction;
import seng302.group2.util.undoredo.UndoRedoPerformer;
import seng302.group2.util.undoredo.UndoableItem;

/**
 * Basic project class that acts as the root object for Sahara and represents a real-world project
 * @author Jordane Lew (jml168)
 */
@SuppressWarnings("deprecation")
public class Project extends TreeViewItem implements Serializable
{
    private String shortName;
    private String longName;
    private String description;
    private String lastSaveLocation = null;
    private transient boolean hasUnsavedChanges = true;
    private transient ObservableList<TreeViewItem> teams = observableArrayList();
    private ArrayList<Person> serializableTeam = new ArrayList<>();
    private transient ObservableList<TreeViewItem> people = observableArrayList();
    private ArrayList<Person> serializablePeople = new ArrayList<>();
    private transient ObservableList<TreeViewItem> skills = observableArrayList();
    private ArrayList<Skill> serializableSkills = new ArrayList<>();
    
    /**
     * Enumerable save and load statuses
     */
    public enum SaveLoadResult
    {
        SUCCESS,
        NULLPROJECT,
        IOEXCEPTION,
        NOFILESELECTED,     // Cancelled in save
        FILENOTFOUND        // File doesn't exist when opening
    }
    
    /**
     * Basic Project constructor
     */
    public Project()
    {
        super("Untitled");
        this.shortName = "Untitled";
        this.longName = "Untitled Project";
        this.description = "A blank project.";
        this.serializablePeople = new ArrayList<>();
        this.serializableSkills = new ArrayList<>();
        this.serializableTeam = new ArrayList<>();
    }
    
    
    /**
     * Basic project constructor
     * @param shortName A unique short name to identify the Project
     * @param fullName The full Project name
     * @param description A description of the Project
     */
    public Project(String shortName, String fullName, String description)
    {
        super(shortName);
        this.shortName = shortName;
        this.longName = fullName;
        this.description = description;
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="Getters">
    
    /**
     * Gets if the project has unsaved changes.
     * @return true or false depending if the project has unsaved changes
     */
    public boolean getHasUnsavedChanges()
    {
        return hasUnsavedChanges;
    }
    
    
    /**
     * Gets the project's short name
     * @return The short name of the project
     */
    public String getShortName()
    {
        return this.shortName;
    }
    
    
    /**
     * Gets the project's long name
     * @return The long name of the project
     */
    public String getLongName()
    {
        return this.longName;
    }
    
    
    /**
     * Gets the project's description
     * @return The description of the project
     */
    public String getDescription()
    {
        return this.description;
    }
    
    
    /**
     * Gets the project's list of Persons
     * @return The people associated with the project
     */
    public ObservableList<TreeViewItem> getPeople()
    {
        return this.people;
    }
    
    /**
     * Gets the project's list of Skills
     * @return The skills associated with a project
     */
    public ObservableList<TreeViewItem> getSkills()
    {
        return this.skills;
    }
    
    /**
     * Gets the project's list of Skills
     * @return The skills associated with a project
     */
    public ObservableList<TreeViewItem> getTeams()
    {
        return this.teams;
    }
    
    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="Setters">
    
    /**
     * Marks the project as not having unsaved changes
     */
    public void setUnchanged()
    {
        this.hasUnsavedChanges = false;
    }
    
    
    /**
     * Marks the project as having unsaved changes
     */
    public void setChanged()
    {
        this.hasUnsavedChanges = true;
    }
    
    
    /**
     * Sets the project's short name
     * @param shortName The new short name for the project
     */
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }
    
    
    /**
     * Sets the project's long name
     * @param longName The new long name for the project
     */
    public void setLongName(String longName)
    {
        this.longName = longName;
    }
    
    
    /**
     * Sets the project's description
     * @param description The new description for the project
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    //</editor-fold>
    
    
    /**
     * Saves the current project as a file specified by the user
     * @param project The project to save
     * @param saveAs If acting as save as
     * @return The corresponding SaveLoadResult status of the process
     */
    public static SaveLoadResult saveProject(Project project, boolean saveAs)
    {
        // If there is no current project open, display a dialog and skip saving
        if (project == null)
        {
            Dialogs.create()
                    .title("No open project")
                    .message("There is currently no project open to save")
                    .showWarning();
                    
            return SaveLoadResult.NULLPROJECT;
        }
        
        project = Project.preSerialization(project);
        
        if (saveAs || project.lastSaveLocation == null || project.lastSaveLocation.equals(""))
        {
            // Prime a FileChooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Project");
            fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Project Files", "*.proj")
            );

            // Open the FileChooser to choose the save location of the project
            File selectedFile = fileChooser.showSaveDialog(new Stage());
            if (selectedFile == null)
            {
                return SaveLoadResult.FILENOTFOUND;  // Most likely user cancelation;
            }
            project.lastSaveLocation = selectedFile.toString();
            
            // Ensure the extension is .proj (Linux issue)
            if (!project.lastSaveLocation.endsWith(".proj"))
            {
                project.lastSaveLocation = project.lastSaveLocation + ".proj";
            }
        }
        
        /* GSON SERIALIZATION */
        try (Writer writer = new FileWriter(project.lastSaveLocation))
        {
            Gson gson = new GsonBuilder().create();
            gson.toJson(project, writer);
            writer.close();
            Global.setCurrentProjectUnchanged();
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
     * Loads a project specified by the user into Global.currentProject
     * @return The corresponding SaveLoadResult status of the process
     */
    public static SaveLoadResult loadProject()
    {
        // Prime a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Project");
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Project Files", "*.proj")
        );
        
        // Open the FileChooser and try saving the Project as the user specifies
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null)
        {
            /* GSON DESERIALIZATION */
            try (Reader reader = new FileReader(selectedFile))
            {
                Gson gson = new GsonBuilder().create();
                Global.currentProject = gson.fromJson(reader, Project.class);
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
            
            Project.postDeserialization();
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
     * Adds a Person to the Project's list of Persons
     * @param person The person to add
     */
    public void addPerson(Person person)
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
     * Adds a Skill to the Project's list of Skills
     * @param skill The skill to add
     */
    public void addSkill(Skill skill)
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
     * Adds a Skill to the Project's list of Skills
     * @param skill The skill to add
     */
    public void addTeam(Team team)
    {
        //Add the undo action to the stack
        Global.undoRedoMan.add(new UndoableItem(
                team,
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.SKILL, null),
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.SKILL, null)
                ));
        
        this.teams.add(team);
    }
    
    /**
     * Gets a list of categories of the project based on the project's lists
     * @return A list of categories of the project
     */
    public ObservableList<TreeViewItem> getCategories()
    {
        // Prime the list
        ObservableList<TreeViewItem> root = observableArrayList();
        
        // Make the categories
        Category peopleCategory = new Category("People");
        Category skillCategory = new Category("Skills");
        Category teamsCategory = new Category("Teams");
        
        // Add the categories
        root.add(peopleCategory); //teams.add(people)
        root.add(skillCategory);
        root.add(teamsCategory);
        
        return root;
    }
    
    
    /**
     * Perform pre-serialization steps
     * 1) Transform ObservableLists into ArrayLists for serialization
     * @param project The project for intended serialization
     * @return A serializable version of the given project
     */
    public static Project preSerialization(Project project)
    {
        project.serializablePeople.clear();
        for (Object item : project.people)
        {
            project.serializablePeople.add((Person)item);
        }
        
        project.serializableSkills.clear();
        for (Object item : project.skills)
        {
            project.serializableSkills.add((Skill)item);
        }
        
        project.serializableTeam.clear();
        for (Object item : project.teams)
        {
            project.serializableTeam.add((Person)item);
        }
        
        
        // Also perform again for any other deeper observables
        
        return project;
    }
    
    
    /**
     * Perform post-deserialization steps (performs on Global.currentProject for now).
     * 1) Transform ArrayLists back into ObservableLists
     */
    public static void postDeserialization()
    {
        Global.currentProject.people = observableArrayList();
        for (Person item : Global.currentProject.serializablePeople)
        {
            Global.currentProject.people.add(item);
        }
        
        Global.currentProject.skills = observableArrayList();
        for (Skill item : Global.currentProject.serializableSkills)
        {
            Global.currentProject.skills.add(item);
        }
        
        Global.currentProject.teams = observableArrayList();
        for (Person item : Global.currentProject.serializableTeam)
        {
            Global.currentProject.teams.add(item);
        }
        
        
        // Also for any other deeper observables
        // eg. for (TreeViewItem item : Global.currentProject.team.people) {...}

        // Unset saved changes flag, we just opened the project.
        Global.currentProject.hasUnsavedChanges = false;
    }
    
    
    /**
     * An overridden version for the String representation of a Project
     * @return The short name of the Project
     */
    @Override
    public String toString()
    {
        return this.shortName;
    }
    
    
    /**
     * Gets the children (categories) of the project
     * @return the children (categories) of the project
     */
    @Override
    public ObservableList<TreeViewItem> getChildren()
    {
        return getCategories();
    }
}
