package seng302.group2.scenes.information.tag;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.controlsfx.control.PopOver;
import seng302.group2.Global;
import seng302.group2.scenes.control.PopOverTip;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableListView;
import seng302.group2.scenes.validation.ValidationStyle;
import seng302.group2.workspace.tag.Tag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A Pane for the creation and management of Tags.
 * Created by drm127 on 14/09/15.
 */
public class TagManagementPane extends SplitPane {

    Set<SearchableControl> searchControls = new HashSet<>();

    ListView<Tag> tagListView;
    ObservableList<Tag> tagList = Global.currentWorkspace.getAllTags();

    VBox detailsPane = new VBox(8);
    VBox listPane = new VBox(8);


    public TagManagementPane() {
        this.setPrefSize(600, 400);
        this.setDividerPositions(0.5);

        construct();
    }


    private void construct() {
        this.getChildren().clear();
        this.constructList();
        this.constructDetail();
    }


    private void constructList() {
        this.getItems().remove(listPane);
        listPane = new VBox(8);
        listPane.setPadding(new Insets(8));
        listPane.setMaxWidth(292);

        tagList.addAll(new Tag("crap"), new Tag("hash"));

        tagListView = new SearchableListView<>(tagList, searchControls);
        tagListView.setPrefHeight(584);
        tagListView.getSelectionModel().getSelectedItems().addListener(
                (ListChangeListener<Tag>) change -> constructDetail());

        // Sort here if needed
        tagListView.getSelectionModel().select(0);
        listPane.getChildren().add(tagListView);

        this.getItems().add(listPane);
    }


    private void constructDetail() {
        this.getItems().remove(detailsPane);
        detailsPane = new VBox(8);
        detailsPane.setPadding(new Insets(8));
        detailsPane.setMaxWidth(292);

        // Find the selected tag and create its tag node
        Tag selectedTag = tagListView.getSelectionModel().getSelectedItem();

        if (selectedTag != null) {
            TagCellNode cellNode = new TagCellNode(selectedTag);
            detailsPane.getChildren().add(cellNode);

            // Create the editable option controls
            RequiredField tagNameField = new RequiredField("Tag Name", searchControls);
            tagNameField.getTextField().setPromptText("fix");
            tagNameField.setText(selectedTag.getName());
            tagNameField.getTextField().addEventFilter(Event.ANY, event -> {
                    // @Dave Update the displayed cell node with the name
                });

            ColorPicker colorPicker = new ColorPicker(selectedTag.getColor());
            colorPicker.setOnAction(event -> {
                    Color newColor = colorPicker.getValue();
                    // @Dave Update the displayed cell node with the colour
                });

            // Group all of the controls into the details pane
            detailsPane.getChildren().addAll(tagNameField, colorPicker);
        }

        // Create buttons
        Button saveButton = new Button("Save Tag");
        Button cancelButton = new Button("Cancel Changes");

        saveButton.setOnAction(event -> {
                // @Dave create and execute edit
            });

        cancelButton.setOnAction(event -> constructDetail());

        HBox buttonsBox = new HBox(8);
        VBox.setVgrow(buttonsBox, Priority.ALWAYS);
        buttonsBox.getChildren().addAll(saveButton, cancelButton);
        detailsPane.getChildren().add(buttonsBox);


        this.getItems().add(detailsPane);
    }
}
