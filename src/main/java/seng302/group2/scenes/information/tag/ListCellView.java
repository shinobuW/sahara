package seng302.group2.scenes.information.tag;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import seng302.group2.scenes.control.Tooltip;
import seng302.group2.workspace.tag.Tag;


import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;

/**
 * A custom list cell for the tags
 * Created by Bronson Laptop on 20/09/2015.
 */
public class ListCellView extends ListCell<Tag> {
    HBox cell = new HBox(10);
    HBox deleteCell = new HBox(0);

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
     */
    public Node createDeletionNode(Tag tag) {
        ImageView deletionImage = new ImageView("icons/dialog-cancel.png");
        Tooltip.create("Delete Tag", deletionImage, 50);

        deletionImage.setOnMouseEntered(me -> {
            this.getScene().setCursor(Cursor.HAND); //Change cursor to hand
        });
        deletionImage.setOnMouseExited(me -> {
            this.getScene().setCursor(Cursor.DEFAULT); //Change cursor to hand
        });

        deletionImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            showDeleteDialog(tag);
            event.consume();
        });


        return deletionImage;

    }



}
