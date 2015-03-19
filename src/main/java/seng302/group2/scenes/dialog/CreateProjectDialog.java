package seng302.group2.scenes.dialog;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import seng302.group2.App;
import seng302.group2.project.Project;

/**
 *
 * @author David Moseley drm127
 */
public class CreateProjectDialog 
{
    public static void show()
    {
        Dialog dialog = new Dialog(null, "Create New Project");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        
        TextField shortNameField = new TextField();
        TextField longNameField = new TextField();
        TextArea descriptionField = new TextArea();
        descriptionField.setPrefRowCount(15);
        descriptionField.setWrapText(true);
        
        Button btnCreate = new Button("Create");
        Button btnCancel = new Button("Cancel");
        
        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(btnCreate, btnCancel);
        
        grid.add(new Label("Short Name: "), 0, 0);
        grid.add(shortNameField, 1, 0);
        grid.add(new Label("Longer Name: "), 0, 1);
        grid.add(longNameField, 1, 1);
        grid.add(new Label("Project Description: "), 0, 2);
        grid.add(descriptionField, 1, 2);
        grid.add(buttons, 1, 3);
        
        btnCreate.setOnAction((event) ->
            {
                String shortName = shortNameField.getText();
                String longName = longNameField.getText();
                String description = descriptionField.getText();
                
                int valid = validation(shortName, longName, description);
                
                if (valid == 1)
                {
                    Project project = new Project(shortName, longName, description);
                    System.out.println("Testing" + shortName);
                    App.currentProject = project;
                    dialog.hide();
                }
                else
                {
                    event.consume();
                }
            });
        
        btnCancel.setOnAction((event) ->
            {
                dialog.hide();
            });
        
        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(grid);
        dialog.show();
    }
    
    private static int validation(String shortName, String longName, String description)
    {
        int valid = 0;
        
        if (shortName.equals("") || longName.equals("") || description.equals(""))
        {
            Dialogs.create()
                .title("Need more info!")
                .message("You must enter a short name, long name, and"
                        + " description for your new project.")
                .showInformation();
        }
        else
        {
            valid = 1;
        }
        
        return valid;
        
    }
}
