/*
 * SENG302 Group 2
 */
package seng302.group2;

import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import seng302.group2.project.Project;
import seng302.group2.project.Project.SaveLoadResult;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.listdisplay.TreeViewWithItems;
import seng302.group2.util.config.ConfigLoader;

/**
 * Hello world!
 * (With an added extra ;))
 */
@SuppressWarnings("deprecation")
public class App extends Application
{
    public static HBox content = new HBox();
    
    public static Stage mainStage;
    public static Scene mainScene;
    
    /* Moved into Global
    public static Project currentProject = new Project();
    public static TreeItem selectedTreeItem = new TreeItem();
    public static UndoRedoManager undoRedoMan = new UndoRedoManager();
    */
    
    
    /**
     * Refreshes the main scene GUI
     */
    public static void refreshMainScene()
    {
        App.content = new HBox();
        App.mainScene = MainScene.getMainScene();
        mainStage.setScene(App.mainScene);
        App.refreshWindowTitle();
    }
    
    
    /**
     * Refreshes the title of the window to show the name of the current project, if any
     */
    public static void refreshWindowTitle()
    {
        if (App.mainStage == null)
        {
            return;
        }
        if (Global.currentProject == null)
        {
            App.mainStage.titleProperty().set("Sahara");
        }
        else
        {
            if (Global.currentProject.getHasUnsavedChanges())
            {
                App.mainStage.titleProperty().set("Sahara: " + Global.currentProject.getLongName() 
                        + "*");
            }
            else
            {
                App.mainStage.titleProperty().set("Sahara: " + Global.currentProject.getLongName());
            }
        }
    }
    
    
    /**
     * The GUI setup and launch of the project
     * @param primaryStage primary stage
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
        primaryStage.getIcons().add(new Image("file:images/icon.png"));
        
        // Set the scene of the stage to the initial scene
        App.mainScene = MainScene.getMainScene();
        primaryStage.setScene(App.mainScene);
        mainStage = primaryStage;

        // Load the config
        ConfigLoader.loadConfig();
        
        // Exit button handling
        Platform.setImplicitExit(false);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() 
        {
            public void handle(WindowEvent event)
            {
                ConfigLoader.saveConfig();
                if (!Global.currentProject.getHasUnsavedChanges())
                {
                    System.exit(0);
                }    
                Action response = Dialogs.create()
                    .title("Save Project?")
                    .message("Would you like to save your changes to the current project?")
                    .showConfirm();
                
                if (response == Dialog.ACTION_YES)
                {
                    SaveLoadResult saved = Project.saveProject(Global.currentProject, false);
                    if (saved == SaveLoadResult.SUCCESS)
                    {
                        System.exit(0);
                    }
                    else
                    {
                        event.consume();
                    }
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
        App.refreshWindowTitle();
        mainStage.show();
    }
    
    
    /**
     * The main entry of the project
     * @param args arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}
