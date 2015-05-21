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
import seng302.group2.scenes.control.*;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.util.validation.DateValidator;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.release.Release;

import java.time.LocalDate;

import static seng302.group2.util.validation.DateValidator.isCorrectDateFormat;
import static seng302.group2.util.validation.DateValidator.stringToDate;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 *
 * @author Shinobu
 */
public class CreateReleaseDialog
{
    /**
     * Shows the release creation dialog
     */
    public static void show()
    {
        if (Global.currentWorkspace.getProjects().isEmpty())
        {
            // There are no projects to create releases for
            return;
        }
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

        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Description:");
        CustomDatePicker releaseDateField = new CustomDatePicker("Estimated Release Date:", false);
        CustomComboBox projectComboBox = new CustomComboBox("Project:", true);
        
        String firstItem = Global.currentWorkspace.getProjects().get(0).toString();
        projectComboBox.setValue(firstItem);

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

                boolean correctShortName = validateShortName(shortNameCustomField, null);

                Project project = new Project();
                for (TreeViewItem item : Global.currentWorkspace.getProjects())
                {
                    if (item.toString().equals(projectComboBox.getValue()))
                    {
                        project = (Project)item;
                    }
                }

                if (correctShortName)
                {
                    LocalDate releaseDate = releaseDateField.getValue();


                    if (releaseDate == null)
                    {
                        releaseDate = null;
                        Release release = new Release(shortName, description, releaseDate, project);
                        project.add(release);
                        dialog.hide();   
                    }
                    else
                    {
                        if (!DateValidator.isFutureDate(releaseDate))
                        {
                            releaseDateField.showErrorField("Date must be a future date");
                        }
                        else
                        {
                            Release release = new Release(shortName, description, releaseDate, 
                                    project);
                            project.add(release);
                            dialog.hide();
                        }
                    }
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
     * Shows the release creation dialog defaulting to the given project
     * @param defaultProject The default project to create the release in
     */
    public static void show(Project defaultProject)
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
        CustomComboBox projectComboBox = new CustomComboBox("Project", true);

        //String firstItem = Global.currentWorkspace.getProjects().get(0).getShortName();
        //projectComboBox.setValue(firstItem);

        for (TreeViewItem project : Global.currentWorkspace.getProjects())
        {
            projectComboBox.addToComboBox(project.toString());
        }
        projectComboBox.setValue(defaultProject.toString());

        grid.getChildren().add(shortNameCustomField);
        grid.getChildren().add(descriptionTextArea);
        grid.getChildren().add(releaseDateField);
        grid.getChildren().add(projectComboBox);
        grid.getChildren().add(buttons);

        btnCreate.setOnAction((event) ->
            {
                String shortName = shortNameCustomField.getText();
                String description = descriptionTextArea.getText();

                boolean correctDate = isCorrectDateFormat(releaseDateField);
                boolean correctShortName = validateShortName(shortNameCustomField, null);

                Project project = new Project();

                for (TreeViewItem item : Global.currentWorkspace.getProjects())
                {
                    if (item.toString().equals(projectComboBox.getValue()))
                    {
                        project = (Project) item;
                    }
                }

                if (correctShortName && correctDate)
                {
                    String releaseDateString = releaseDateField.getText();

                    LocalDate releaseDate;
                    if (releaseDateString.isEmpty())
                    {
                        releaseDate = null;
                        Release release = new Release(shortName, description, releaseDate, project);
                        project.add(release);
                        dialog.hide();
                    }
                    else
                    {
                        releaseDate = stringToDate(releaseDateString);
                        if (!DateValidator.isFutureDate(releaseDate))
                        {
                            releaseDateField.showErrorField("Date must be a future date");
                        }
                        else
                        {
                            Release release = new Release(shortName, description, releaseDate,
                                    project);
                            project.add(release);
                            dialog.hide();
                        }
                    }
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
