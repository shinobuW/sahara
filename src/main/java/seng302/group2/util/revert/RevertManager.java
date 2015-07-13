package seng302.group2.util.revert;

import com.google.gson.Gson;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.util.serialization.SerialBuilder;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.workspace.Workspace;

import java.util.Stack;

/**
 * Allows {@link seng302.group2.workspace.workspace.Workspace}s to be reverted to the last given state (as a
 * json serialised workspace).
 * Created by crw73 on 12/05/15.
 */
public class RevertManager {
    private static String revertWorkspace;
    private static Stack<Command> revertUndos;
    private static Gson gson = SerialBuilder.getBuilder();

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

    /**
     * Updates the reverted state to the current workspace
     */
    public static void updateRevertState() {
        Workspace.prepSerialization(Global.currentWorkspace);
        String json = gson.toJson(Global.currentWorkspace);
        updateRevertState(json);
    }
}
