/*
 * SENG302 Group 2
 */
package seng302.group2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import seng302.group2.scenes.MainScene;
import seng302.group2.util.config.ConfigLoader;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.Workspace.SaveLoadResult;

/**
 * The executable class for Sahara.
 */
@SuppressWarnings("deprecation")
public class App extends Application
{
    public static SplitPane content;
    
    public static Stage mainStage;
    public static Scene mainScene;
    
    /**
     * Refreshes the main scene GUI.
     */
    public static void refreshMainScene()
    {
        App.content = new SplitPane();
        content.setDividerPositions(0.2);
        App.mainScene = MainScene.getMainScene();
        mainStage.setScene(App.mainScene);
        App.refreshWindowTitle();
    }


    /**
     * Refreshes the title of the window to show the name of the current workspace, if any.
     */
    public static void refreshWindowTitle()
    {
        if (App.mainStage == null)
        {
            return;
        }
        if (Global.currentWorkspace == null)
        {
            App.mainStage.titleProperty().set("Sahara");
        }
        else
        {
            if (Global.currentWorkspace.getHasUnsavedChanges())
            {
                App.mainStage.titleProperty().set("Sahara: " + Global.currentWorkspace.getLongName()
                        + "*");
            }
            else
            {
                App.mainStage.titleProperty().set("Sahara: "
                        + Global.currentWorkspace.getLongName());
            }
        }
    }
    
    
    /**
     * The GUI setup and launch of the workspace.
     * @param primaryStage primary stage
     */
    @Override
    public void start(Stage primaryStage)
    {
        Global.currentWorkspace = new Workspace();

        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        // The title of the window
        primaryStage.setTitle("Sahara");
      
        primaryStage.setWidth(1060);
        primaryStage.setHeight(640);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(500);
	
	//primaryStage.setWidth(0.75 * screenSize.getWidth());
        //primaryStage.setHeight(0.75 * screenSize.getHeight());
        //primaryStage.setMinHeight(0.25 * screenSize.getWidth());
        //primaryStage.setMinWidth(0.25 * screenSize.getWidth());
	
        primaryStage.getIcons().add(new Image("file:images/icon.png"));
        
        // Set the scene of the stage to the initial scene
        content = new SplitPane();
        App.mainScene = MainScene.getMainScene();
        primaryStage.setScene(App.mainScene);
        mainStage = primaryStage;

        // Load the config
        ConfigLoader.loadConfig();
        
        // Exit button handling
        Platform.setImplicitExit(false);
        primaryStage.setOnCloseRequest(event ->
            {
                exitApp();
                event.consume();
            });

        // Show the stage/window
        App.refreshWindowTitle();
        mainStage.show();
    }


    /**
     * The closure and application tear-down method that should be executed on closure of the
     * application from any branch of the project code
     */
    public static void exitApp()
    {
        ConfigLoader.saveConfig();
        if (!Global.currentWorkspace.getHasUnsavedChanges())
        {
            System.exit(0);
            return;  // Clean from a method POV
        }

        Action response = Dialogs.create()
                .title("Save Workspace?")
                .message("Would you like to save your changes to the current workspace?")
                .showConfirm();

        if (response == Dialog.ACTION_YES)
        {
            SaveLoadResult saved = Workspace.saveWorkspace(Global.currentWorkspace, false);
            if (saved == SaveLoadResult.SUCCESS)
            {
                // Save configuration again as settings may change on save of workspace
                ConfigLoader.saveConfig();
                System.exit(0);
            }
        }
        else if (response == Dialog.ACTION_NO)
        {
            System.exit(0);
        }
    }

    
    /**
     * The main entry of the application.
     * @param args arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}
