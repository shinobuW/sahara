/*
 * SENG302 Group 2
 */
package seng302.group2.project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

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
     * @param scene The current scene to display the dialog
     * @param project The project to save
     * @throws IOException Error initializing the FileWriter for the file
     */
    public static void Save(Scene scene, Project project) throws IOException
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Project Files", "*.proj")
        );
        
        File selectedFile = fileChooser.showSaveDialog((Stage) scene.getWindow());
        if (selectedFile != null)
        {
            Writer writer = new FileWriter(selectedFile);

            Gson gson = new GsonBuilder().create();
            gson.toJson(project, writer);

            writer.close();
        }
        
    }
}
