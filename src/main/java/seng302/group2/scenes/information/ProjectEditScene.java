package seng302.group2.scenes.information;

import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import seng302.group2.App;
import seng302.group2.project.Project;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import static seng302.group2.App.informationGrid;
import seng302.group2.Global;
import static seng302.group2.Global.selectedTreeItem;
import static seng302.group2.scenes.dialog.CreatePersonDialog.validateName;
import static seng302.group2.scenes.dialog.CreatePersonDialog.validateShortName;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.listdisplay.TreeViewWithItems;

/**
 * Created by btm38 on 7/04/15.
 */
public class ProjectEditScene
{
    /**
     * Gets the Project Edit information display
     * @return The Project Edit information display
     */
    public static GridPane getProjectEditScene()
    {

        Project currentProject = (Project) selectedTreeItem.getValue();
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

        RequiredField shortNameCustomField = new RequiredField("Short Name", 300);
        RequiredField longNameCustomField = new RequiredField("Long Name", 300);
        CustomTextArea descriptionTextArea = new CustomTextArea("Project Description");
        
        shortNameCustomField.setText(currentProject.getShortName());
        longNameCustomField.setText(currentProject.getLongName());
        descriptionTextArea.setText(currentProject.getDescription());

        informationGrid.add(shortNameCustomField, 0, 0);
        informationGrid.add(longNameCustomField, 0, 1);
        informationGrid.add(descriptionTextArea, 0, 2);
        informationGrid.add(buttons, 0, 3);


        btnCancel.setOnAction((event) ->
            {
                App.content.getChildren().remove(App.informationGrid);
                ProjectScene.getProjectScene();
                App.content.getChildren().add(App.informationGrid);

            });

        btnSave.setOnAction((event) ->
            {
                boolean correctShortName = validateShortName(shortNameCustomField);
                boolean correctLongName = validateName(longNameCustomField);


                if (correctShortName && correctLongName)
                {
                    currentProject.setDescription(descriptionTextArea.getText());
                    currentProject.setShortName(shortNameCustomField.getText());
                    currentProject.setLongName(longNameCustomField.getText());
                    App.content.getChildren().remove(App.treeView);
                    App.content.getChildren().remove(App.informationGrid);
                    ProjectScene.getProjectScene();
                    App.treeView = new TreeViewWithItems(new TreeItem());
                    ObservableList<TreeViewItem> children = observableArrayList();
                    children.add(Global.currentProject);

                    App.treeView.setItems(children);
                    App.treeView.setShowRoot(false);

                    App.content.getChildren().add(App.treeView);
                    App.content.getChildren().add(App.informationGrid);
                    App.treeView.getSelectionModel().select(selectedTreeItem);

                }
                else
                {
                    event.consume();
                }

            });

        return App.informationGrid;
    }
}
