package seng302.group2.util.revert;

import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.util.serialization.SerialBuilder;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.Workspace;

import java.util.Set;
import java.util.Stack;

/**
 * Allows {@link seng302.group2.workspace.Workspace}s to be reverted to the last given state (as a
 * json serialised workspace).
 * Created by crw73 on 12/05/15.
 */
public class RevertManager {
    private static String revertWorkspace;
    private static Stack<Command> revertUndos;

    /**
     * Reverts to the last revert state by deserialising the last workspace state and making it the
     * current workspace
     */
    public static void revertWorkspace() {
        if (revertWorkspace != null) {
            Workspace currentWorkspace = SerialBuilder.getBuilder().fromJson(revertWorkspace,
                    Workspace.class);
            Workspace.postDeserialization(currentWorkspace);

            Global.currentWorkspace = currentWorkspace;

            for (Command command : revertUndos) {
                command.map(currentWorkspace.getItemsSet());
            }

            Global.commandManager.clear();
            Global.commandManager.setUndos(revertUndos);

            App.refreshMainScene();
        }
    }

    /**
     * Updates the reverted state to the given json string
     *
     * @param json The json serialization of the state to update to
     */
    public static void updateRevertState(String json) {
        revertWorkspace = json;
        if (Global.commandManager != null) {
            revertUndos = Global.commandManager.getUndoCloneStack();
        }
    }
}
