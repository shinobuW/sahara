package seng302.group2.scenes.information.person;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.scenes.control.search.*;
import seng302.group2.scenes.dialog.CreatePersonDialog;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.workspace.Workspace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;

/**
 * A class for displaying a tab showing data on all the people in the workspace.
 * Created by btm38 on 13/07/15.
 */
public class PersonCategoryTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();
    /**
     * Constructor for PersonCategoryTab class.
     * @param currentWorkspace The current workspace
     */
    public PersonCategoryTab(Workspace currentWorkspace) {
        this.setText("Basic Information");
        Pane categoryPane = new VBox(10);
        categoryPane.setBorder(null);
        categoryPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(categoryPane);
        this.setContent(wrapper);
        SearchableText title = new SearchableTitle("People in " + currentWorkspace.getShortName(), searchControls);

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

        SearchableListView personBox = new SearchableListView(currentWorkspace.getPeople());
        personBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        personBox.setMaxWidth(275);

        categoryPane.getChildren().add(title);
        categoryPane.getChildren().add(personBox);
        categoryPane.getChildren().add(selectionButtons);

        Collections.addAll(searchControls, title, personBox);

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
                javafx.scene.control.Dialog creationDialog = new CreatePersonDialog();
                creationDialog.show();
            });
    }

    /**
     * Gets all the searchable controls on this tab.
     * @return a collection of all the searchable controls on this tab.
     */
    @Override
    public Collection<SearchableControl> getSearchableControls() {
        return searchControls;
    }
}