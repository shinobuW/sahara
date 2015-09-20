package seng302.group2.scenes.information.tag;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableListView;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.workspace.tag.Tag;

import java.util.HashSet;
import java.util.Set;

import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;

/**
 * A Pane for the creation and management of Tags.
 * Created by drm127 on 14/09/15.
 */
public class TagManagementPane extends SplitPane {

    private PopOver popOver;
    Set<SearchableControl> searchControls = new HashSet<>();

    ListView<Tag> tagListView;
    ObservableList<Tag> tagList = Global.currentWorkspace.getAllTags();

    VBox detailsPane = new VBox(8);
    VBox listPane = new VBox(8);


    public TagManagementPane() {
        this.setPrefSize(665, 400);
        this.setDividerPositions(0.5);

        construct();
    }

    public TagManagementPane(PopOver po) {
        this.setPrefSize(665, 400);
        this.setDividerPositions(0.5);
        this.popOver = po;
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
        detailsPane.setMaxWidth(357);

        // Find the selected tag and create its tag node
        Tag selectedTag = tagListView.getSelectionModel().getSelectedItem();

        //HBox titleBox = new HBox();
        SearchableText title = new SearchableText("Edit Tag:", searchControls);
        title.injectStyle("-fx-font-weight: bold");
        detailsPane.getChildren().add(title);

        HBox cellBox = new HBox();
        cellBox.setPrefHeight(100);
        cellBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(cellBox, Priority.ALWAYS);

        TagCellNode cellNode = new TagCellNode(selectedTag);
        cellNode.setAlignment(Pos.CENTER);
        cellBox.getChildren().add(cellNode);
        detailsPane.getChildren().add(cellBox);

        // Create the editable option controls
        RequiredField tagNameField = new RequiredField("Tag Name:", searchControls);
        tagNameField.getTextField().setPromptText("fix");
        tagNameField.setText(selectedTag.getName());
        tagNameField.getTextField().addEventFilter(Event.ANY, event -> {
                // @Dave Update the displayed cell node with the name
                cellNode.setName(tagNameField.getText());
            });

        HBox colorBox = new HBox();
        HBox labelBox = new HBox();

        SearchableText tagColorLabel = new SearchableText("Tag Colour:", searchControls);
        tagColorLabel.injectStyle("-fx-font-weight: bold");
        labelBox.getChildren().add(tagColorLabel);
        labelBox.setAlignment(Pos.BOTTOM_LEFT);
        labelBox.spacingProperty().setValue(0);
        HBox.setHgrow(labelBox, Priority.ALWAYS);
        VBox.setVgrow(labelBox, Priority.ALWAYS);
        ColorPicker colorPicker = new ColorPicker(selectedTag.getColor());
        colorPicker.setOnAction(event -> {
                cellNode.setColor(colorPicker.getValue());
            });
        colorBox.getChildren().addAll(labelBox, colorPicker);

        // Group all of the controls into the details pane
        detailsPane.getChildren().addAll(tagNameField, colorBox);


        // Create buttons
        Button saveButton = new Button("Save Changes");
        Button cancelButton = new Button("Cancel Changes");
        Button deleteButton = new Button("Delete Tag");

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

        deleteButton.setOnAction(event -> {
            if (popOver != null) {
                Node parent = popOver.getOwnerNode();
                double x = popOver.getX();
                double y = popOver.getY();
                popOver.hide();
                showDeleteDialog(selectedTag);
                popOver.setDetached(true);
                Platform.runLater(() -> {
                    //popOver.setWidth(width);
                    //popOver.setHeight(height);
                    popOver.show(parent, x, y);
                });
            }
            else {
                showDeleteDialog(selectedTag);
            }
        });


        HBox buttonsBox = new HBox(8);
        VBox.setVgrow(buttonsBox, Priority.ALWAYS);
        buttonsBox.getChildren().addAll(deleteButton, cancelButton, saveButton);
        detailsPane.getChildren().addAll(buttonsBox);

        buttonsBox.setAlignment(Pos.BOTTOM_RIGHT);


        this.getItems().add(detailsPane);
    }
}
