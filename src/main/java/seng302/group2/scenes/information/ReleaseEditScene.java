/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import seng302.group2.App;
import seng302.group2.Global;
import static seng302.group2.Global.selectedTreeItem;
import seng302.group2.scenes.MainScene;
import static seng302.group2.scenes.MainScene.informationGrid;
import static seng302.group2.scenes.MainScene.treeView;
import seng302.group2.scenes.control.CustomDateField;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.listdisplay.TreeViewWithItems;
import seng302.group2.util.undoredo.UndoableItem;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;
import seng302.group2.workspace.release.Release;

/**
 *
 * @author Shinobu
 */
public class ReleaseEditScene
{
    public static GridPane getReleaseEditScene(Release currentRelease)
    {
        informationGrid = new GridPane();
        informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);
        informationGrid.setPadding(new Insets(25,25,25,25));

        Button btnCancel = new Button("Cancel");
        Button btnSave = new Button("Save");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(btnSave, btnCancel);

        RequiredField shortNameCustomField = new RequiredField("Short Name");
        CustomTextArea descriptionTextArea = new CustomTextArea("Description", 300);
        CustomDateField releaseDateField = new CustomDateField("Estimated Release Date");
        ObservableList<String> options = 
                FXCollections.observableArrayList(Global.currentWorkspace.getProjects().toString());
        final ComboBox projectCombobox = new ComboBox(options);
        
        shortNameCustomField.setText(currentRelease.getShortName());
        descriptionTextArea.setText(currentRelease.getDescription());

        informationGrid.add(shortNameCustomField, 0, 0);
        informationGrid.add(descriptionTextArea, 0, 1);
        informationGrid.add(releaseDateField, 0, 2);
        informationGrid.add(projectCombobox, 0, 3);
        informationGrid.add(buttons, 0,4);
        
        btnCancel.setOnAction((event) ->
            {
                App.content.getChildren().remove(informationGrid);
                ReleaseScene.getReleaseScene((Release) Global.selectedTreeItem.getValue());
                App.content.getChildren().add(informationGrid);

            });

        btnSave.setOnAction((event) ->
            {
                boolean correctShortName;
                
                if (shortNameCustomField.getText().equals(currentRelease.getShortName()))
                {
                    correctShortName = true;
                }
                else
                {
                    correctShortName = validateShortName(shortNameCustomField);
                }


                if (correctShortName)
                {
                    // Build Undo/Redo edit array.
                    ArrayList<UndoableItem> undoActions = new ArrayList<>();          
                    //TODO: undoredo
        
                    // Save the edits.        
                    currentRelease.setDescription(descriptionTextArea.getText());
                    currentRelease.setShortName(shortNameCustomField.getText());
                    
                    App.content.getChildren().remove(treeView);
                    App.content.getChildren().remove(informationGrid);
                    ReleaseScene.getReleaseScene(currentRelease);
                    MainScene.treeView = new TreeViewWithItems(new TreeItem());
                    ObservableList<TreeViewItem> children = observableArrayList();
                    children.add(Global.currentWorkspace);

                    MainScene.treeView.setItems(children);
                    MainScene.treeView.setShowRoot(false);

                    App.content.getChildren().add(treeView);
                    App.content.getChildren().add(informationGrid);
                    MainScene.treeView.getSelectionModel().select(selectedTreeItem);

                }
                else
                {
                    event.consume();
                }

            });

        return informationGrid;
    }
    
}
