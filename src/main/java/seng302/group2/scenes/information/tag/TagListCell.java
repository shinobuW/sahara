package seng302.group2.scenes.information.tag;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.controlsfx.control.PopOver;
import seng302.group2.App;
import seng302.group2.scenes.control.Tooltip;
import seng302.group2.workspace.tag.Tag;


import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;

/**
 * A custom list cell for the tags
 * Created by Bronson Laptop on 20/09/2015.
 */
public class TagListCell extends ListCell<Tag> {
    HBox cell = new HBox(10);
    HBox deleteCell = new HBox(0);
    PopOver popOver;

    public TagListCell(PopOver popover) {
        this.popOver = popover;
    }

    @Override
    protected void updateItem(Tag t, boolean bln) {
        super.updateItem(t, bln);
        if (t != null) {
            deleteCell.setAlignment(Pos.BASELINE_RIGHT);
            System.out.println("Draw string");
            Node deletionNode = createDeletionNode(t);
            Label tagName = new Label(t.getName());
            deleteCell.getChildren().add(deletionNode);
            cell.getChildren().addAll(tagName, deleteCell);
            setGraphic(cell);
        }
    }

    /**
     * Creates a deletion node
     * @param tag The tag to be deleted
     */
    public Node createDeletionNode(Tag tag) {
        ImageView deletionImage = new ImageView("icons/trashcan.png");
        Tooltip.create("Delete Tag", deletionImage, 50);

        deletionImage.setOnMouseEntered(me -> {
            this.getScene().setCursor(Cursor.HAND); //Change cursor to hand
        });
        deletionImage.setOnMouseExited(me -> {
            this.getScene().setCursor(Cursor.DEFAULT); //Change cursor to hand
        });

        deletionImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (popOver != null) {
                Node parent = popOver.getOwnerNode();
                double x = popOver.getX();
                double y = popOver.getY();
                popOver.hide();
                showDeleteDialog(tag);
                popOver.setDetached(true);
                Platform.runLater(() -> {
                    PopOver taggingPopOver = new PopOver();
                    taggingPopOver.setDetachedTitle("Tag Management");
                    taggingPopOver.setDetached(true);
                    taggingPopOver.setContentNode(new TagManagementPane(taggingPopOver));
                    Platform.runLater(() -> taggingPopOver.show(App.content, App.mainStage.getX()
                                    + App.mainStage.getWidth() / 2 - 300,
                            App.mainStage.getY() + App.mainStage.getHeight() / 2 - 200));

//                    Tag newTag = managementPane.tagListView.getSelectionModel().getSelectedItem();
//
//                    if ((managementPane).tagListView.getItems().contains(tag)) {
//                        (managementPane).tagListView.getSelectionModel().select(tag);
                    });
            }
            else {
                showDeleteDialog(tag);
            }
        });

        return deletionImage;
    }



}