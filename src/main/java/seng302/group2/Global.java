package seng302.group2;

import javafx.scene.control.TreeItem;
import seng302.group2.project.Project;
import seng302.group2.util.undoredo.UndoRedoManager;

/**
 * TODO
 * @author Jordane
 */
public final class Global
{
    public static Project currentProject = new Project();
    public static TreeItem selectedTreeItem = new TreeItem();
    public static UndoRedoManager undoRedoMan = new UndoRedoManager();
    public static String lastSaveLocation = "/";


    /**
     * Checks if the app is running (via context of the App)
     * This method is to stop NullExceptions when testing because there is no App context.
     * @return true if the app is running, else false.
     */
    public static boolean appRunning()
    {

        try
        {
            // Try and refresh the window title.
            App.refreshWindowTitle();
        }
        catch (Exception ex)
        {
            // Assume no App running.
            return false;
        }
        // No exceptions, assumed App is running.
        return true;
    }


    /**
     * Mark the current project as changed.
     */
    public static void setCurrentProjectChanged()
    {
        if (Global.appRunning())
        {
            currentProject.setChanged();
            App.refreshWindowTitle();
        }
    }


    /**
     * Mark the current project as unchanged.
     */
    public static void setCurrentProjectUnchanged()
    {
        if (Global.appRunning())
        {
            currentProject.setUnchanged();
            App.refreshWindowTitle();
        }
    }
}
