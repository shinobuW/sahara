/*
 * SENG302 Group 2
 */
package seng302.group2;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import seng302.group2.project.Project;
import seng302.group2.scenes.MainScene;

/**
 * Hello world!
 * (With an added extra ;))
 */
public class App extends Application
{
    public static Project currentProject = new Project();
    public static Stage mainStage;
    public static Scene mainScene;
    public static Object selectedTreeItem = null;
    
    
    /**
     * Refreshes the main scene GUI
     */
    public static void refreshMainScene()
    {
        App.mainScene = MainScene.getMainScene();
        mainStage.setScene(App.mainScene);
    }
    
    
    /**
     * The GUI setup and launch of the project
     * @param primaryStage 
     */
    @Override
    public void start(Stage primaryStage)
    {   
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        // The title of the window
        primaryStage.setTitle("Sahara");
        primaryStage.setWidth(0.75 * screenSize.getWidth());
        primaryStage.setHeight(0.75 * screenSize.getHeight());
        primaryStage.setMinHeight(0.25 * screenSize.getWidth());
        primaryStage.setMinWidth(0.25 * screenSize.getWidth());
        
        // Set the scene of the stage to the initial scene
        App.mainScene = MainScene.getMainScene();
        primaryStage.setScene(App.mainScene);
        mainStage = primaryStage;
        Platform.setImplicitExit(false);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() 
        {
            public void handle(WindowEvent event)
            {
                Action response = Dialogs.create()
                    .title("Save Project?")
                    .message("Would you like to save your changes to the current project?")
                    .showConfirm();
                
                if (response == Dialog.ACTION_YES)
                {
                    try
                    {
                        Project.saveCurrentProject();
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                    System.exit(0);
                }
                else if (response == Dialog.ACTION_NO)
                {
                    System.exit(0);
                }
                else
                {
                    event.consume();
                }
            }
        });
        
        // Show the stage/window
        mainStage.show();
    }
    
    
    /**
     * The main entry of the project
     * @param args 
     */
    public static void main(String[] args)
    {
        System.out.println("Hello World, Welcome to Sahara!");
        launch(args);
    }
}
