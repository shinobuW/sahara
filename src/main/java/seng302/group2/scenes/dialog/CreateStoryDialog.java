/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.dialog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.dialog.Dialog;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.listdisplay.TreeViewItem;

import static seng302.group2.util.validation.NameValidator.validateName;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;
import static seng302.group2.util.validation.NumberFieldValidator.validateNumberField;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.story.Story;

/**
 * Class to create a pop up dialog for creating a story
 * @author swi67
 */
public class CreateStoryDialog
{
    /**
     * Displays the Dialog box for creating a story.
     */
    public static void show()
    {
        // Initialise Dialog and GridPane
        Dialog dialog = new Dialog(null, "New Story");
        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);

        // Initialise Input fields
        Button btnCreate = new Button("Create");
        Button btnCancel = new Button("Cancel");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(btnCreate, btnCancel);

        // Add elements to grid
        RequiredField shortNameCustomField = new RequiredField("Short Name");
        RequiredField longNameCustomField = new RequiredField("Long Name");
        RequiredField creatorCustomField = new RequiredField("Creator");
        CustomTextArea descriptionTextArea = new CustomTextArea("Description");
        CustomComboBox projectComboBox = new CustomComboBox("Project", true);
        RequiredField priorityNumberField = new RequiredField("Priority");
        
        String firstItem = Global.currentWorkspace.getProjects().get(0).toString();
        projectComboBox.setValue(firstItem);

        for (TreeViewItem project : Global.currentWorkspace.getProjects())
        {
            projectComboBox.addToComboBox(project.toString());
        }

        grid.getChildren().addAll(shortNameCustomField, longNameCustomField, creatorCustomField, 
                priorityNumberField, projectComboBox, descriptionTextArea, buttons);

        // Create button event
        btnCreate.setOnAction((event) ->
            {
                boolean correctShortName = validateShortName(shortNameCustomField);
                boolean correctLongName = validateName(longNameCustomField);
                boolean correctCreator = validateName(creatorCustomField);
                boolean correctPriority = validateNumberField(priorityNumberField);

                if (correctShortName && correctLongName && correctCreator && correctPriority)
                {
                    //get user input
                    String shortName = shortNameCustomField.getText();
                    String longName = longNameCustomField.getText();
                    String creator = creatorCustomField.getText();
                    String description = descriptionTextArea.getText();
                    Integer priority = Integer.parseInt(priorityNumberField.getText());
                 
                    Project project = new Project();
                    for (TreeViewItem item : Global.currentWorkspace.getProjects())
                    {
                        if (item.toString().equals(projectComboBox.getValue()))
                        {
                            project = (Project)item;
                        }
                    }
                    Story story = new Story(shortName, longName, description, creator, project,
                            priority);
                    project.add(story);
                    dialog.hide();
                }
                else
                {
                    event.consume();
                }
            });

        // Cancel button event
        btnCancel.setOnAction((event) ->
                dialog.hide());

        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(grid);
        dialog.show();
    }
    
}
