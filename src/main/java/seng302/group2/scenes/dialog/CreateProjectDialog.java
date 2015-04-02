package seng302.group2.scenes.dialog;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.dialog.Dialog;
import seng302.group2.Global;
import seng302.group2.project.Project;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.LimitedTextField;
import seng302.group2.scenes.control.RequiredField;

/**
 *
 * @author David Moseley drm127
 */
@SuppressWarnings("deprecation")
public class CreateProjectDialog 
{
    public static void show()
    {
        Dialog dialog = new Dialog(null, "Create New Project");
        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);
        
        TextField shortNameField = new LimitedTextField(20);
        TextField longNameField = new TextField();
        TextArea descriptionField = new TextArea();
        descriptionField.setPrefRowCount(5);
        descriptionField.setWrapText(true);
        
        Button btnCreate = new Button("Create");
        Button btnCancel = new Button("Cancel");
        
        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(btnCreate, btnCancel);
        
        grid.getChildren().add(new RequiredField("Short Name"));
        grid.getChildren().add(new RequiredField("Long Name"));
        grid.getChildren().add(new CustomTextArea("Project Description"));
        grid.getChildren().add(buttons);
        
        btnCreate.setOnAction((event) ->
            {
                String shortName = shortNameField.getText();
                String longName = longNameField.getText();
                String description = descriptionField.getText();
                
                int valid = validation(shortName, longName, description);
                
                if (valid == 1)
                {
                    Project project = new Project(shortName, longName, description);
                    Global.currentProject = project;
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
        
        valid = 1;
        return valid;
        
    }
}
