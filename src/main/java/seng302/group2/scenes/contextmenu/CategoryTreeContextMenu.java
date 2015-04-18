package seng302.group2.scenes.contextmenu;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import seng302.group2.Global;
import seng302.group2.scenes.dialog.CreatePersonDialog;
import seng302.group2.scenes.dialog.CreateSkillDialog;
import seng302.group2.scenes.dialog.CreateTeamDialog;
import seng302.group2.scenes.listdisplay.Category;
import seng302.group2.workspace.project.Project;

/**
 * Created by Jordane on 14/04/2015.
 * Edited by swi67 14/04/2015
 */
public class CategoryTreeContextMenu extends ContextMenu
{

    public CategoryTreeContextMenu()
    {
        MenuItem createItem = new MenuItem("Create new...");
        //String selectedTreeItem = Global.selectedTreeItem.getValue().toString();
        createItem.setOnAction(e ->
            {
                if (Global.selectedTreeItem.getValue().getClass() == Category.class)
                {
                    showCreateNewDialog(Global.selectedTreeItem.getValue().toString());
                }
            });

        this.getItems().addAll(createItem);
    }
    
    /**
     * Displays the appropriate dialogBox for creating a new element
     * @param category Type of category
     */
    public static void showCreateNewDialog(String category)
    {
        switch (category)
        {
            case "Projects":
                //TODO: Actual gui popup
                Global.currentWorkspace.add(new Project());
                //CreateProjectDialog.show();
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
            default:
                break;
        }
    }
}
