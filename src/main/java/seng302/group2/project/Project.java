/*
 * SENG302 Group 2
 */
package seng302.group2.project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.Dialog;
import java.awt.JobAttributes.DialogType;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Optional;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import seng302.group2.App;
import seng302.group2.project.team.person.Person;
import seng302.group2.scenes.listdisplay.TreeViewItem;

/**
 * Basic project class that acts as the root object for Sahara and represents a real-world project
 * @author Jordane Lew (jml168)
 */
public class Project extends TreeViewItem implements Serializable
{
    private String shortName;
    private String longName;
    private String description;
    private transient ObservableList<TreeViewItem> people = observableArrayList();
    private ArrayList<TreeViewItem> serializablePeople = new ArrayList<>();
    
    
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
     * @throws IOException Error initializing the FileWriter for the file
     */
    public static void saveCurrentProject() throws IOException
    {
        // If there is no current project open, display a dialog and skip saving
        if (App.currentProject == null)
        {
            // TODO: Display dialog that no project is open
            // return;
        }
        
        Project.preSerialization();
        
        // Prime a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Project");
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Project Files", "*.proj")
        );
        
        // Open the FileChooser and try loading the user selected Project
        File selectedFile = fileChooser.showSaveDialog(new Stage());
        if (selectedFile != null)
        {
            /* JAVA SERIALIZATION
            FileOutputStream fileOutStream = new FileOutputStream(selectedFile);
            ObjectOutputStream  objOutStream = new ObjectOutputStream(fileOutStream);
            objOutStream.writeObject(App.currentProject);
            */
            
            /* GSON SERIALIZATION */
            try (Writer writer = new FileWriter(selectedFile))
            {
                Gson gson = new GsonBuilder().create();
                gson.toJson(App.currentProject, writer);
                writer.close();
            }
        }
    }
    
    
    /**
     * Loads a project specified by the user
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void loadProject() throws FileNotFoundException, IOException
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
            /* JAVA DESERIALIZATION
            FileInputStream fileInpStream = new FileInputStream(selectedFile);
            InputStream buffer = new BufferedInputStream(fileInpStream);
            ObjectInput objInput = new ObjectInputStream(buffer);
            
            try
            {
                App.currentProject = (Project) objInput.readObject();
            }
            catch (ClassNotFoundException e)
            {
                // Class not found
            }
            */
            
            /* GSON DESERIALIZATION */
            try (Reader reader = new FileReader(selectedFile))
            {
                Gson gson = new GsonBuilder().create();
                App.currentProject = gson.fromJson(reader, Project.class);
                reader.close();
            }
            
            Project.postDeserialization();
        }
        
        App.refreshMainScene();
    }
    
    
    /**
     * Adds a Person to the Project's list of Persons
     * @param person 
     */
    public void addPerson(Person person)
    {
        this.people.add(person);
        
        // Add it to the display menu as well
        /*this.treeViewItem.getChildren().add(
                new TreeViewData(person.getShortName(), person, person.getClass()
                ));*/
        //App.refreshMainScene();
    }
    
    
    /**
     * Gets a list of categories of the project based on the project's lists
     * @return A list of categories of the project
     */
    public ObservableList<TreeViewItem> getCategories()
    {
        ObservableList<TreeViewItem> root = observableArrayList();
        
        Category people = new Category("People");
        root.add(people);
        
        return root;
    }
    
    
    /**
     * Perform pre-serialization steps
     * - Transform ObservableLists into ArrayLists for serialization
     */
    public static void preSerialization()
    {
        App.currentProject.serializablePeople.clear();
        for (TreeViewItem item : App.currentProject.people)
        {
            App.currentProject.serializablePeople.add(item);
        }
        
        // Also for any other deeper observables
    }
    
    
    /**
     * Perform post-deserialization steps
     * - Transform ArrayLists back into ObservableLists
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
