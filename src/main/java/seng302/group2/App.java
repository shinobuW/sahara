/*
 * SENG302 Group 2
 */
package seng302.group2;

import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng302.group2.project.Project;
import seng302.group2.scenes.MainScene;

/**
 * Hello world!
 * (With an added extra ;))
 */
public class App extends Application
{
    public static Project currentProject = Project.newBlankProject();
    public static Stage mainStage;
    public static Scene mainScene;
    
    
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
        primaryStage.setTitle("JavaFX Form");
        primaryStage.setWidth(0.75 * screenSize.getWidth());
        primaryStage.setHeight(0.75 * screenSize.getHeight());
        primaryStage.setMinHeight(0.25 * screenSize.getWidth());
        primaryStage.setMinWidth(0.25 * screenSize.getWidth());
        
        // Set the scene of the stage to the initial scene
        App.mainScene = MainScene.getMainScene();
        primaryStage.setScene(App.mainScene);
        mainStage = primaryStage;

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
