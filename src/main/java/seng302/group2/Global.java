package seng302.group2;

import javafx.scene.control.TreeItem;
import seng302.group2.util.undoredo.CommandManager;
import seng302.group2.util.undoredo.UndoRedoManager;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.team.Team;

import java.text.SimpleDateFormat;

/**
 * TODO
 * @author Jordane
 */
public final class Global
{
    public static Workspace currentWorkspace = new Workspace();
    public static TreeItem selectedTreeItem = new TreeItem();
    public static String lastSaveLocation = "/";
    public static SimpleDateFormat datePattern = new SimpleDateFormat("dd/MM/yyyy");

    public static UndoRedoManager undoRedoMan = new UndoRedoManager(); // Becoming deprecated
    public static CommandManager commandManager = new CommandManager();


    /**
     * Gets the unassigned team
     * @return the unassigned team
     */
    public static Team getUnassignedTeam()
    {
        if (currentWorkspace.getTeams() == null || currentWorkspace.getTeams().isEmpty())
        {
            return null;
        }
        for (Team team : currentWorkspace.getTeams())
        {
            if (team.isUnassignedTeam())
            {
                return team;
            }
        }
        return null;
    }


    /**
     * Mark the current workspace as changed.
     */
    public static void setCurrentWorkspaceChanged()
    {
        currentWorkspace.setChanged();
        App.refreshWindowTitle();
    }


    /**
     * Mark the current workspace as unchanged.
     */
    public static void setCurrentWorkspaceUnchanged()
    {
        currentWorkspace.setUnchanged();
        App.refreshWindowTitle();
    }

}
