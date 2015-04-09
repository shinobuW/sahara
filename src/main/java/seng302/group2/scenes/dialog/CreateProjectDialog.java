package seng302.group2.scenes.dialog;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.dialog.Dialog;
import seng302.group2.Global;
import seng302.group2.project.Project;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import static seng302.group2.util.validation.NameValidator.validateName;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

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
               
        Button btnCreate = new Button("Create");
        Button btnCancel = new Button("Cancel");
        
        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(btnCreate, btnCancel);
        
        RequiredField shortNameCustomField = new RequiredField("Short Name");
        RequiredField longNameCustomField = new RequiredField("Long Name");
        CustomTextArea descriptionTextArea = new CustomTextArea("Project Description");
        
        grid.getChildren().add(shortNameCustomField);
        grid.getChildren().add(longNameCustomField);
        grid.getChildren().add(descriptionTextArea);
        grid.getChildren().add(buttons);
        
        btnCreate.setOnAction((event) ->
            {
                boolean correctShortName = validateShortName(shortNameCustomField);
                boolean correctLongName = validateName(longNameCustomField);
                
                String shortName = shortNameCustomField.getText();
                String longName = longNameCustomField.getText();
                String description = descriptionTextArea.getText();
                
                if (correctShortName && correctLongName)
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
}
