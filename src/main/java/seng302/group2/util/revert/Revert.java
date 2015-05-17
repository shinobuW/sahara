package seng302.group2.util.revert;

import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.workspace.Workspace;

/**
 * Created by crw73 on 12/05/15.
 */
public class Revert
{

    private static Workspace revertWorkspace;
    private static boolean revertState = false;

    public static void revertWorkspace()
    {
        if (revertWorkspace != null)
        {
            Global.currentWorkspace = revertWorkspace;
            Global.commandManager.clear();
            revertState = false;
            App.refreshMainScene();

        }
    }

    public static Workspace getRevertWorkspace()
    {
        return revertWorkspace;
    }

    public static boolean getRevertState()
    {
        return revertState;
    }

    public static void updateRevertWorkspace(Workspace workspace)
    {
        revertWorkspace = workspace;
        revertState = true;

    }
}
