/*
 * SENG302 Group 2
 */
package seng302.group2.project;

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
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;
import seng302.group2.App;
import seng302.group2.project.team.person.Person;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.util.undoredo.UndoRedoAction;
import seng302.group2.util.undoredo.UndoRedoPerformer;
import seng302.group2.util.undoredo.UndoableItem;

/**
 * Basic project class that acts as the root object for Sahara and represents a real-world project
 * @author Jordane Lew (jml168)
 */
public class Project extends TreeViewItem implements Serializable
{
    private String shortName;
    private String longName;
    private String description;
    private String lastSaveLocation = null;
    private transient ObservableList<TreeViewItem> people = observableArrayList();
    private ArrayList<TreeViewItem> serializablePeople = new ArrayList<>();
    
    /**
     * Enumerable save and load statuses
     */
    public enum SaveLoadResult
    {
        SUCCESS,
        NULLPROJECT,
        IOEXCEPTION,
        NOFILESELECTED,  // Cancelled in save
        FILENOTFOUND  // File doesn't exist when opening
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
     * Gets a project's short name
     * @return The short name of the project
     */
    public String getShortName()
    {
        return this.shortName;
    }
    
    
    /**
     * Gets a project's long name
     * @return The long name of the project
     */
    public String getLongName()
    {
        return this.longName;
    }
    
    
    /**
     * Gets a project's description
     * @return The description of the project
     */
    public String getDescription()
    {
        return this.description;
    }
    
    
    /**
     * Gets a project's list of Persons
     * @return The short name of the project
     */
    public ObservableList<TreeViewItem> getPeople()
    {
        return this.people;
    }
    
    
    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="Setters">
    
    /**
     * Sets a project's short name
     * @param shortName The new short name for the project
     */
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }
    
    
    /**
     * Sets a project's long name
     * @param longName The new long name for the project
     */
    public void setLongName(String longName)
    {
        this.longName = longName;
    }
    
    
    /**
     * Sets a project's description
     * @param description The new description for the project
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    //</editor-fold>
    
    
    /**
     * Saves the current project as a file specified by the user
     * @return The corresponding SaveLoadResult status of the process
     */
    public static SaveLoadResult saveProject(Project project, boolean saveAs)
    {
        // If there is no current project open, display a dialog and skip saving
        if (project == null)
        {
            Action response = Dialogs.create()
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
            App.projectChanged = false;
            return SaveLoadResult.SUCCESS;
        }
        catch (IOException e)
        {
            Action response = Dialogs.create()
                .title("Error Saving")
                .message("An error occurred while trying to save the file")
                .showException(e);
            return SaveLoadResult.IOEXCEPTION;
        }

    }
    
    
    /**
     * Loads a project specified by the user into App.currentProject
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
                App.currentProject = gson.fromJson(reader, Project.class);
                reader.close();
            }
            catch (FileNotFoundException e)
            {
                Action response = Dialogs.create()
                    .title("File Not Found")
                    .message("The specified file could not be found.")
                    .showWarning();
                return SaveLoadResult.FILENOTFOUND;
            }
            catch (IOException e)
            {
                Action response = Dialogs.create()
                    .title("Error Loading")
                    .message("An error occurred while trying to load the file.")
                    .showException(e);
                return SaveLoadResult.IOEXCEPTION;
            }
            
            Project.postDeserialization();
            App.refreshMainScene();
            App.undoRedoMan.emptyAll();
            return SaveLoadResult.SUCCESS;
        }
        else
        {
            return SaveLoadResult.FILENOTFOUND;  // Was null, probably cancelled action?
        }
    }
    
    
    /**
     * Adds a Person to the Project's list of Persons
     * @param person 
     */
    public void addPerson(Person person)
    {
        // Add the undo action to the stack
        App.undoRedoMan.add(new UndoableItem(
                person,
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON, null), 
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON, null)
                ));
        
        this.people.add(person);
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
        Category people = new Category("People");
        //Category teams = new Category("Teams");
        //Category skills = new Category("Skills");
        
        // Add the categories
        root.add(people); //teams.add(people)
        //root.add(teams);
        //root.add(skills);
        
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
        for (TreeViewItem item : project.people)
        {
            project.serializablePeople.add(item);
        }
        
        // Also perform again for any other deeper observables
        
        return project;
    }
    
    
    /**
     * Perform post-deserialization steps (performs on App.currentProject for now).
     * 1) Transform ArrayLists back into ObservableLists
     */
    public static void postDeserialization()
    {
        App.currentProject.people = observableArrayList();
        for (TreeViewItem item : App.currentProject.serializablePeople)
        {
            App.currentProject.people.add(item);
        }
        
        // Also for any other deeper observables
        // eg. for (TreeViewItem item : App.currentProject.team.people) {...}
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
