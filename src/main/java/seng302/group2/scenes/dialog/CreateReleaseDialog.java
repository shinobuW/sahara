/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.dialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.dialog.Dialog;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomDateField;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import static seng302.group2.util.validation.DateValidator.validateBirthDate;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.release.Release;

/**
 *
 * @author Shinobu
 */
public class CreateReleaseDialog
{
    public static void show()
    {       
        Dialog dialog = new Dialog(null, "New Release");
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
        CustomTextArea descriptionTextArea = new CustomTextArea("Description");
        CustomDateField releaseDateField = new CustomDateField("Estimated Release Date");
        CustomComboBox projectComboBox = new CustomComboBox("Project");
        
        for (TreeViewItem project : Global.currentWorkspace.getProjects())
        {
            projectComboBox.addToComboBox(project.toString());
        }
        
        grid.getChildren().add(shortNameCustomField);
        grid.getChildren().add(descriptionTextArea);
        grid.getChildren().add(releaseDateField);
        grid.getChildren().add(projectComboBox);
        grid.getChildren().add(buttons);
        
        btnCreate.setOnAction((event) ->
            {
                
                String shortName = shortNameCustomField.getText();
                String description = descriptionTextArea.getText();
                
                boolean correctDate = validateBirthDate(releaseDateField);
                boolean correctShortName = validateShortName(shortNameCustomField);
                
                Project project = new Project();
                for (TreeViewItem item : Global.currentWorkspace.getProjects())
                {
                    if (item.toString().equals(shortName))
                    {
                        project = (Project)item;
                    }
                }
                
                if (correctShortName && correctDate)
                {
                    String releaseDateString = releaseDateField.getText();

                    Date releaseDate;
                    if (releaseDateString.isEmpty())
                    {
                        releaseDate = null;
                    }
                    else
                    {
                        releaseDate = stringToDate(releaseDateString);
                    }

                    Release release = new Release(shortName, description, releaseDate, project);
                    project.addRelease(release);
                    //Global.currentWorkspace.add(release);
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
    
    /**
     * Converts strings to dates
     * @param releaseDateString the birth date string
     * @return the parsed date
     */
    public static Date stringToDate(String releaseDateString)
    {
        Date releaseDate = new Date();
        try
        {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            df.setLenient(false);
            df.parse(releaseDateString);
            releaseDate = df.parse(releaseDateString);
        }
        catch (ParseException e)
        {
            System.out.println("Error parsing date");
        }
        return releaseDate;
    }
    
}
