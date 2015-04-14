package seng302.group2.scenes.contextmenu;

import java.text.MessageFormat;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.project.skills.Skill;
import seng302.group2.project.team.Team;
import seng302.group2.project.team.person.Person;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.information.PersonEditScene;
import seng302.group2.scenes.information.SkillEditScene;
import seng302.group2.scenes.information.TeamEditScene;

/**
 *ContextMenu class for instances of Person, Skill and Team
 * @author swi67
 */
public class ElementTreeContextMenu extends ContextMenu
{
    /** Constructor. Sets the event for contextMenu buttons
     */
    public ElementTreeContextMenu()
    {
        MenuItem deleteItem = new MenuItem("Delete...");
        MenuItem editItem = new MenuItem("Edit...");
        
        String categoryName = "";
        
        if (Global.selectedTreeItem.getValue().getClass() == Person.class)
        {
            categoryName = "Person";
        }
        else if (Global.selectedTreeItem.getValue().getClass() == Skill.class)
        {
            categoryName = "Skill";
        }
        else if (Global.selectedTreeItem.getValue().getClass() == Team.class)
        {
            categoryName = "Team";
        }
        final String className = categoryName;
        
        //"Edit" button event
        editItem.setOnAction(e ->
            {
                showEditScene(className);
            });
        
        //"Delete" button event
        deleteItem.setOnAction(e -> 
            {
                showDeleteDialog(className);
            });

        this.getItems().addAll(editItem, deleteItem);
    }
    
    /** Displays the edit scene for given element
     * @param categoryName Type of Category
     */
    public static void showEditScene(String categoryName)
    {
        App.content.getChildren().remove(MainScene.informationGrid);
        
        switch (categoryName)
        {
            case "Person":           
                PersonEditScene.getPersonEditScene((Person) Global.selectedTreeItem.getValue());
                break;
            case "Skill":
                SkillEditScene.getSkillEditScene();
                break;
            case "Teams":
                TeamEditScene.getTeamEditScene();
                break;
            default:
                break;
        }
        App.content.getChildren().add(MainScene.informationGrid);
    }
    
    /**Shows a confirm dialog box for element deletion
     * @param categoryName Type of Category
     */
    public static void showDeleteDialog(String categoryName)
    {
        Action response = Dialogs.create()
            .title("Delete item?")
            .message(MessageFormat.format("Do you want to delete {0}: {1}", categoryName, 
                    Global.selectedTreeItem.getValue().getClass().toString()))
            .showConfirm();
        
        //To Do for deletion story
    }
}
