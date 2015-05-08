package seng302.group2.scenes.information;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.dialog.CreatePersonDialog;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.Workspace;

import static seng302.group2.scenes.MainScene.informationGrid;
import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;

/**
 * A class for displaying all people currently created in a workspace.
 * @author David Moseley
 */
public class PersonCategoryScene
{
    /**
     * Gets the Person Category Scene
     * @param currentWorkspace The workspace currently being used
     * @return The person category info scene
     */
    public static Pane getPersonCategoryScene(Workspace currentWorkspace)
    {
        informationGrid = new VBox(10);
        /*informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);*/
        informationGrid.setPadding(new Insets(25,25,25,25));
        Label title = new Label("People in " + currentWorkspace.getShortName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnView = new Button("View");
        Button btnDelete = new Button("Delete");
        Button btnCreate = new Button("Create New Person");

        HBox selectionButtons = new HBox();
        selectionButtons.spacingProperty().setValue(10);
        selectionButtons.getChildren().add(btnView);
        selectionButtons.getChildren().add(btnDelete);
        selectionButtons.getChildren().add(btnCreate);
        selectionButtons.setAlignment(Pos.TOP_LEFT);

        //HBox createButton = new HBox();
        //createButton.getChildren().add(btnCreate);
        //createButton.setAlignment(Pos.CENTER_RIGHT);

        ListView personBox = new ListView(currentWorkspace.getPeople());
        personBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        personBox.setMaxWidth(275);

        informationGrid.getChildren().add(title);
        informationGrid.getChildren().add(personBox);
        informationGrid.getChildren().add(selectionButtons);

        //informationGrid.getChildren().add(createButton);

        btnView.setOnAction((event) ->
            {
                if (personBox.getSelectionModel().getSelectedItem() != null)
                {
                    MainScene.treeView.selectItem((TreeViewItem)
                            personBox.getSelectionModel().getSelectedItem());
                }
            });


        btnDelete.setOnAction((event) ->
            {
                if (personBox.getSelectionModel().getSelectedItem() != null)
                {
                    showDeleteDialog((TreeViewItem) 
                        personBox.getSelectionModel().getSelectedItem());
                }
            });

        btnCreate.setOnAction((event) ->
            {
                CreatePersonDialog.show();
            });

        return MainScene.informationGrid;
    }
}
