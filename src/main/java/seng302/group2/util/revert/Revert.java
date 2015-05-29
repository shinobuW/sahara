package seng302.group2.util.revert;

import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.util.serialization.SerialBuilder;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.Workspace;

import java.util.Stack;

/**
 * Allows {@link seng302.group2.workspace.Workspace}s to be reverted to the last given state (as a
 * json serialised workspace).
 * Created by crw73 on 12/05/15.
 */
public class Revert
{
    private static String revertWorkspace;
    private static Object revertUndos;

    /**
     * Reverts to the last revert state by deserialising the last workspace state and making it the
     * current workspace
     */
    public static void revertWorkspace()
    {
        System.out.println("called");
        if (revertWorkspace != null)
        {
            Workspace currentWorkspace = SerialBuilder.getBuilder().fromJson(revertWorkspace,
                    Workspace.class);
            Workspace.postDeserialization(currentWorkspace);

            Global.currentWorkspace = currentWorkspace;

            Global.commandManager.clear();
            Global.commandManager.setUndos((Stack<Command>) revertUndos);
            App.refreshMainScene();
        }
    }

    /**
     * Updates the reverted state to the given json string
     * @param json The json serialization of the state to update to
     */
    public static void updateRevertState(String json)
    {
        revertWorkspace = json;
        if (Global.commandManager != null)
        {
            revertUndos = Global.commandManager.getUndos().clone();
            System.out.println(revertUndos);
        }
    }
}
