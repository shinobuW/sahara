package seng302.group2.scenes.information.person;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.dialog.CreatePersonDialog;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.workspace.Workspace;

import static seng302.group2.scenes.MainScene.informationPane;
import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;

/**
 * A class for displaying all people currently created in a workspace.
 *
 * @author David Moseley
 */
public class PersonCategoryScene {
    /**
     * Gets the Person Category Scene
     *
     * @param currentWorkspace The workspace currently being used
     * @return The person category info scene
     */
    public static ScrollPane getPersonCategoryScene(Workspace currentWorkspace) {
        informationPane = new VBox(10);

        informationPane.setPadding(new Insets(25, 25, 25, 25));
        Label title = new TitleLabel("People in " + currentWorkspace.getShortName());

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

        informationPane.getChildren().add(title);
        informationPane.getChildren().add(personBox);
        informationPane.getChildren().add(selectionButtons);

        //informationPane.getChildren().add(createButton);

        btnView.setOnAction((event) -> {
                if (personBox.getSelectionModel().getSelectedItem() != null) {
                    App.mainPane.selectItem((SaharaItem)
                            personBox.getSelectionModel().getSelectedItem());
                }
            });


        btnDelete.setOnAction((event) -> {
                if (personBox.getSelectionModel().getSelectedItem() != null) {
                    showDeleteDialog((SaharaItem)
                            personBox.getSelectionModel().getSelectedItem());
                }
            });

        btnCreate.setOnAction((event) -> {
                CreatePersonDialog.show();
            });

        ScrollPane wrapper = new ScrollPane(informationPane);
        wrapper.setStyle("-fx-background-color:transparent;");
        return wrapper;
    }
}
