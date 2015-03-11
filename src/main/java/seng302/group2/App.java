/*
 * SENG302 Group 2
 */
package seng302.group2;

import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import seng302.group2.project.Project;
import seng302.group2.project.scenes.MainScene;

/**
 * Hello world!
 * (With an added extra ;))
 */
public class App extends Application
{
    
    public static Project currentProject;
    
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
        primaryStage.setScene(MainScene.getMainScene());

        // Show the stage/window
        primaryStage.show();
    }
    
    /**
     * The main entry of the project
     * @param args 
     */
    public static void main(String[] args)
    {
        System.out.println( "Hello World!" );
        System.out.println( "Senpai was here." );
        System.out.println( "Spooky :^)" );
        System.out.println( "Checked out in Netbeans IDE" );
	System.out.println( "EOF errors are caused by Java8 lambda expressions - ignore them" );

        launch(args);
    }
}
