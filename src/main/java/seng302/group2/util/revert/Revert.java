package seng302.group2.util.revert;

import seng302.group2.Global;
import seng302.group2.workspace.Workspace;

/**
 * Created by crw73 on 12/05/15.
 */
public class Revert
{

    private static Workspace revertState;

    public static void revertWorkspace()
    {
        if (revertState != null)
        {
            System.out.println(revertState.getProjects());
            System.out.println(revertState.getPeople());
            System.out.println(revertState.getTeams());
            Global.currentWorkspace = revertState;
        }
    }

    public static Workspace getRevertState()
    {
        return revertState;
    }

    public static void updateRevertState()
    {
        revertState = Global.currentWorkspace.getCopy();
        System.out.println(revertState.getProjects());
        System.out.println(revertState.getPeople());
        System.out.println(revertState.getTeams());
    }
}
