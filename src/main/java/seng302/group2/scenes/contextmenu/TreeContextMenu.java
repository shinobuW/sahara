package seng302.group2.scenes.contextmenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.WindowEvent;
import seng302.group2.Global;
import seng302.group2.project.Project;
import seng302.group2.project.team.person.Person;
import seng302.group2.scenes.listdisplay.Category;

/**
 * Created by Jordane on 14/04/2015.
 */
public class TreeContextMenu extends ContextMenu
{

    public TreeContextMenu()
    {
        MenuItem createItem = new MenuItem("Create new...");
        createItem.setOnAction(e ->
            {
                if (Global.selectedTreeItem.getValue().getClass() == Category.class)
                {
                    System.out.println("Cat: " + Global.selectedTreeItem.getValue().toString());
                }
                else if (Global.selectedTreeItem.getValue().getClass() == Person.class)
                {
                    System.out.println("Pers: " + Global.selectedTreeItem.getValue().toString());
                }
                else if (Global.selectedTreeItem.getValue().getClass() == Project.class)
                {
                    System.out.println("Proj: " + Global.selectedTreeItem.getValue().toString());
                }
                else
                {
                    System.out.println("Other");
                }
            });

        this.getItems().addAll(createItem);
    }
}
