package seng302.group2.scenes.information.tag;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomTextField;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableListView;
import seng302.group2.workspace.tag.Tag;

import java.util.HashSet;
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
        if (tagListView.getItems().size() > 0) {
            this.constructDetail();
        }
        else {
            //TODO make a :( placeholder
        }
    }


    private void constructList() {
        this.getItems().remove(listPane);
        listPane = new VBox(8);
        listPane.setPadding(new Insets(8));
        listPane.setMaxWidth(292);

        tagListView = new SearchableListView<>(tagList, searchControls);
        tagListView.setPrefHeight(584);
        tagListView.getSelectionModel().getSelectedItems().addListener(
                (ListChangeListener<Tag>) change -> {
                if (tagListView.getItems().size() > 0) {
                    constructDetail();
                }
            });

        // Sort here if needed
        if (tagListView.getItems().size() > 0) {
            tagListView.getSelectionModel().select(0);
        }

        HBox labelBox = new HBox();
        labelBox.setAlignment(Pos.CENTER_LEFT);
        labelBox.getChildren().add(new Text("New Tag"));

        TextField newTagField = new TextField();

        newTagField.setPromptText("Buggy");
        Button addNewTagButton = new Button("Create");
        HBox newTagBox = new HBox(8);
        HBox.setHgrow(newTagField, Priority.ALWAYS);
        newTagBox.getChildren().addAll(labelBox, newTagField, addNewTagButton);

        addNewTagButton.setOnAction(event -> {
            newTagField.setText(newTagField.getText().trim());
            for (Tag tag : Global.currentWorkspace.getTags()) {
                if (tag.getName().equals(newTagField.getText())) {
                    // Tag already exists TODO: Dialog
                    return;
                }
            }

            Tag newTag = new Tag(newTagField.getText());
            Global.currentWorkspace.add(newTag);
        });

        listPane.getChildren().addAll(tagListView, newTagBox);

        this.getItems().add(0, listPane);
    }


    private void constructDetail() {
        this.getItems().remove(detailsPane);
        detailsPane = new VBox(8);
        detailsPane.setPadding(new Insets(8));
        detailsPane.setMaxWidth(292);

        // Find the selected tag and create its tag node
        Tag selectedTag = tagListView.getSelectionModel().getSelectedItem();

        TagCellNode cellNode = new TagCellNode(selectedTag);
        detailsPane.getChildren().add(cellNode);

        // Create the editable option controls
        RequiredField tagNameField = new RequiredField("Tag Name", searchControls);
        tagNameField.getTextField().setPromptText("fix");
        tagNameField.setText(selectedTag.getName());
        tagNameField.getTextField().addEventFilter(Event.ANY, event -> {
                // @Dave Update the displayed cell node with the name
                cellNode.setName(tagNameField.getText());
            });

        ColorPicker colorPicker = new ColorPicker(selectedTag.getColor());
        colorPicker.setOnAction(event -> {
                cellNode.setColor(colorPicker.getValue());
                // @Dave Update the displayed cell node with the colour
            });

        // Group all of the controls into the details pane
        detailsPane.getChildren().addAll(tagNameField, colorPicker);


        // Create buttons
        Button saveButton = new Button("Save Tag");
        Button cancelButton = new Button("Cancel Changes");

        saveButton.setOnAction(event -> {
                // @Dave create and execute edit
                boolean nameUnchanged = tagNameField.getText().equals(selectedTag.getName());
                boolean colorUnchanged = colorPicker.getValue() == (selectedTag.getColor());

                if (nameUnchanged && colorUnchanged) {
                    event.consume();
                }

                selectedTag.edit(tagNameField.getText(), colorPicker.getValue());

                constructList();
                tagListView.getSelectionModel().select(selectedTag);
            });

        cancelButton.setOnAction(event -> constructDetail());

        HBox buttonsBox = new HBox(8);
        VBox.setVgrow(buttonsBox, Priority.ALWAYS);
        buttonsBox.getChildren().addAll(saveButton, cancelButton);
        detailsPane.getChildren().add(buttonsBox);


        this.getItems().add(detailsPane);
    }
}
