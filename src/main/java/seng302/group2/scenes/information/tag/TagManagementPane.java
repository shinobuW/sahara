package seng302.group2.scenes.information.tag;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.controlsfx.control.PopOver;
import seng302.group2.Global;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableListView;
import seng302.group2.workspace.tag.Tag;

import java.awt.*;
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


    public TagManagementPane() {
        VBox content = new VBox(8);
        content.setPadding(new Insets(8));

    }


    private void construct() {
        this.getChildren().clear();
        this.constructList();
    }



    private void constructList() {
        tagListView = new SearchableListView<>(tagList, searchControls);
        tagListView.selectionModelProperty().addListener(event -> {
            constructDetail();
        });
        // Sort here if needed
        tagListView.getSelectionModel().select(0);
        this.getChildren().add(tagListView);
    }


    private void constructDetail() {

        // Find the selected tag and create its tag node
        Tag selectedTag = tagListView.getSelectionModel().getSelectedItem();
        TagCellNode cellNode = new TagCellNode(selectedTag);

        // Create the editable option controls
        RequiredField tagNameField = new RequiredField("Tag Name", searchControls);
        tagNameField.setText("#" + selectedTag.getName());

        ColorPicker colorPicker = new ColorPicker(selectedTag.getColor());
        colorPicker.setOnAction(event -> {
                Color newColor = colorPicker.getValue();
                System.out.println("New Color's RGB = " + newColor.getRed() + " "
                        + newColor.getGreen() + " " + newColor.getBlue());
        });

        // Group all of the controls into a details pane
        VBox detailsPane = new VBox(8);
        detailsPane.getChildren().addAll(cellNode, tagNameField, colorPicker);
    }
}
