package seng302.group2.scenes.information.project.story.task;

/**
 * Created by swi67 on 4/09/15.
 */

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import org.controlsfx.control.PopOver;
import seng302.group2.scenes.control.Tooltip;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.tasks.Task;

/**
 * A cell used to show the Impediments status.
 */
public class ImpedimentsTableCell extends TableCell<Object, String> {
    public Node popUp;
    public Story story;

    /**
     * Constructor
     * @param story The currently selected story
     */
    public ImpedimentsTableCell(Story story) {
        this.story = story;
    }

    /**
     * Updates the item
     *
     * @param item  the item to update to
     * @param empty if the cell is empty
     */
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null || getTask() == null) {
            setText(null);
            setGraphic(null);
        }
        else {
            this.popUp = createImpedimentsNode(getTask(), this);
            setGraphic(popUp);
        }
    }

    public Task getTask() {
        Task result = null;
        for (Task task : this.story.getTasks()) {
            if (task.getShortName().equals(getItem())) {
                result = task;
            }
        }
        return result;
    }


    private Node createImpedimentsNode(Task task, TableCell tableCell) {
        // Impediments icon
        ImageView warningImage;
        if (Task.getImpedingStates().contains(task.getState()) || !task.getImpediments().isEmpty()) {
            warningImage = new ImageView("icons/dialog-cancel.png");
            if (task.getState() == Task.TASKSTATE.BLOCKED) {
                if (!task.getImpediments().isEmpty()) {
                    Tooltip.create("This task is currently blocked, with the following impediments:\n"
                            + task.getImpediments(), warningImage, 50);
                }
                else {
                    Tooltip.create("This task is currently blocked", warningImage, 50);
                }
            }
            else if (task.getState() == Task.TASKSTATE.DEFERRED) {
                if (!task.getImpediments().isEmpty()) {
                    //System.out.println(task.getImpediments());
                    Tooltip.create("This task has been deferred, and has the following impediments:\n"
                            + task.getImpediments(), warningImage, 50);
                }
                else {
                    Tooltip.create("This task has been deferred", warningImage, 50);
                }
            }
            else {
                Tooltip.create("This task has the following impediments:\n" + task.getImpediments(),
                        warningImage, 50);
            }
        }
        else {
            warningImage = new ImageView("icons/dialog-cancel-empty.png");
            Tooltip.create("This task has no impediments or blockages", warningImage, 50);
        }

        tableCell.setOnMouseEntered(me -> {
                tableCell.setCursor(Cursor.HAND); //Change cursor to hand
            });
        tableCell.setOnMouseExited(me -> {
                tableCell.setCursor(Cursor.DEFAULT); //Change cursor to hand
            });


        PopOver impedimentPopOver = new PopOver();
        impedimentPopOver.setDetachedTitle(task.getShortName() + "'s Impediments");
        tableCell.setOnMouseClicked(me -> {
                impedimentPopOver.show(tableCell);
            });

        VBox impedimentsVBox = new VBox();
        impedimentsVBox.setPadding(new Insets(8, 8, 8, 8));
        Label impediments = new Label();
        impediments.setTextFill(Color.BLACK);
        Label impedimentsLabel = new Label("There are no impediments");
        impedimentsLabel.setTextFill(Color.BLACK);
        impedimentsVBox.getChildren().add(impedimentsLabel);

        if (!task.getImpediments().isEmpty()) {
            impedimentsLabel.setText("Impediments: ");
            impedimentsLabel.setStyle("-fx-font-weight: bold");
            impediments.setText(task.getImpediments());
            impediments.setMaxWidth(220);
            impediments.setWrapText(true);
            impedimentsVBox.getChildren().addAll(impediments);
        }

        SearchableText statusLabel = new SearchableText("Status: ");
        statusLabel.setTextAlignment(TextAlignment.LEFT);
        HBox impedimentComboLabel = new HBox(2);
        impedimentComboLabel.setAlignment(Pos.CENTER_RIGHT);
        impedimentComboLabel.getChildren().addAll(
                statusLabel
        );

        impedimentPopOver.setContentNode(impedimentsVBox);

        return warningImage;
    }
}

