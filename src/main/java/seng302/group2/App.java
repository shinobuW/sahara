/*
 * SENG302 Group 2
 */
package seng302.group2;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;


/**
 * Hello world!
 * (With an added extra ;))
 */
public class App extends Application
{
    /**
     * The GUI setup and launch of the project
     * @param primaryStage 
     */
    @Override
    public void start(Stage primaryStage) {
        // The title of the window
        primaryStage.setTitle("JavaFX Form");
        
        // Set the scene of the stage to the initial scene
        primaryStage.setScene(Scenes.getInitialScene());
        
        // Show the stage/window
        primaryStage.show();
    }
    
    /**
     * The main entry of the project
     * @param args 
     */
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        System.out.println( "Senpai was here." );
	System.out.println( "Spooky :^)" );

        launch(args);
    }
}
