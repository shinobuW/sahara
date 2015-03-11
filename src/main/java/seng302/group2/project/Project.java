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
 *
 * @author cvs20
 */
public class Project
{
    private String shortName;
    private String fullName;
    private String description;
        
    public Project(String shortName, String fullName, String description)
    {
        this.shortName = shortName;
        this.fullName = fullName;
        this.description = description;
    }
    
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
