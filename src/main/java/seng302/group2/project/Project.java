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
import java.io.Writer;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import seng302.group2.App;

/**
 * Basic project class
 * @author Jordane Lew (jml168)
 */
public class Project
{
    private String shortName;
    private String longName;
    private String description;
    
    
    /**
     * Basic project constructor
     * @param shortName
     * @param fullName
     * @param description 
     */
    public Project(String shortName, String fullName, String description)
    {
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
     * Saves the given project as a file
     * @throws IOException Error initializing the FileWriter for the file
     */
    public static void saveCurrentProject() throws IOException
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Project");
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Project Files", "*.proj")
        );
        
        File selectedFile = fileChooser.showSaveDialog(new Stage());
        if (selectedFile != null)
        {
            Writer writer = new FileWriter(selectedFile);

            Gson gson = new GsonBuilder().create();
            gson.toJson(App.currentProject, writer);

            writer.close();
        }
    }
    
    
    public static void loadProject() throws FileNotFoundException, IOException
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Project");
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Project Files", "*.proj")
        );
        
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null)
        {
            Reader reader = new FileReader(selectedFile);

            Gson gson = new GsonBuilder().create();
            App.currentProject = gson.fromJson(reader, Project.class);

            reader.close();
        }
    }
}
