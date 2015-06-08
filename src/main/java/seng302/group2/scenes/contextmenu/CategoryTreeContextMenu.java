package seng302.group2.scenes.contextmenu;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import seng302.group2.Global;
import seng302.group2.scenes.dialog.*;

/**
 * Created by Jordane on 14/04/2015.
 * Edited by swi67 14/04/2015
 */
public class CategoryTreeContextMenu extends ContextMenu {

    /**
     * Constructor for context menu for categories
     */
    public CategoryTreeContextMenu() {
        MenuItem createItem = new MenuItem("Create new...");
        createItem.setDisable(false);
        createItem.setOnAction(e -> {
            showCreateNewDialog(Global.selectedTreeItem.getValue().toString());
        });

        this.getItems().addAll(createItem);
    }


    /**
     * Constructor for context menu for categories
     *
     * @param enabled the visibility of the context menu
     */
    public CategoryTreeContextMenu(Boolean enabled) {
        MenuItem createItem = new MenuItem("Create new...");
        createItem.setDisable(!enabled);
        createItem.setOnAction(e -> {
                showCreateNewDialog(Global.selectedTreeItem.getValue().toString());
            });

        this.getItems().addAll(createItem);
    }

    /**
     * Displays the appropriate dialogBox for creating a new element
     *
     * @param category Type of category
     */
    private static void showCreateNewDialog(String category) {
        switch (category) {
            case "Projects":
                CreateProjectDialog.show();
                break;
            case "People":
                CreatePersonDialog.show();
                break;
            case "Skills":
                CreateSkillDialog.show();
                break;
            case "Teams":
                CreateTeamDialog.show();
                break;
            case "Releases":
                CreateReleaseDialog.show();
                break;
            case "Unassigned Stories":
                CreateStoryDialog.show();
                break;
            case "Backlog":
                CreateBacklogDialog.show();
                break;
            default:
                System.out.println("Create dialog for that category not implemented (yet?)");
                break;
        }
    }
}
