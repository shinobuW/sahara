package seng302.group2.scenes.information.project.story.task;

/**
 * Created by swi67 on 4/09/15.
 */

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import org.controlsfx.control.PopOver;
import seng302.group2.scenes.control.Tooltip;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.tasks.Task;

import java.util.SortedSet;
import java.util.TreeSet;

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
        final ImageView warningImage = defineWarningImage(task);

        tableCell.setOnMouseEntered(me -> {
                tableCell.setCursor(Cursor.HAND); //Change cursor to hand
            });
        tableCell.setOnMouseExited(me -> {
                tableCell.setCursor(Cursor.DEFAULT); //Change cursor to hand
            });

        PopOver impedimentPopOver = new PopOver();
        impedimentPopOver.setDetachedTitle(task.getShortName() + "'s Impediments");
        SortedSet<Task.TASKSTATE> availableStatuses = new TreeSet<>();
        try {
            for (Task.TASKSTATE state : Task.getImpedingStates()) {
                availableStatuses.add(state);
            }
        }
        catch (NullPointerException ex) {
        }

        // Leave the combobox without a type so we can add a blank '(none)' without having to create a new task state
        ComboBox impedimentCombo = new ComboBox<>();
        impedimentCombo.getItems().add("(none)");
        impedimentCombo.getItems().addAll(availableStatuses);
        // Make default selection
        if (impedimentCombo.getItems().contains(task.getState())) {
            impedimentCombo.getSelectionModel().select(task.getState());
        }
        else {
            impedimentCombo.getSelectionModel().select("(none)");
        }


        VBox impedimentsVBox = new VBox(4);
        SearchableText impedimentsLabel = new SearchableText("Impediments: ");
        TextArea impedimentsTextArea = new TextArea(task.getImpediments());
        impedimentsTextArea.setPrefSize(240, 80);
        impedimentsTextArea.setWrapText(true);
        impedimentsTextArea.setEditable(true);
        impedimentsVBox.getChildren().addAll(impedimentsLabel, impedimentsTextArea);

        Button impedimentSaveButton = new Button("Save Impediments");
        impedimentSaveButton.setAlignment(Pos.CENTER_RIGHT);
        impedimentSaveButton.setOnAction(event -> {
                Task.TASKSTATE selectedState = null;
                Object selectedObject = impedimentCombo.getSelectionModel().getSelectedItem();
                if (selectedObject == null || !selectedObject.toString().equals("(none)")) {
                    selectedState = (Task.TASKSTATE) selectedObject;
                }
                task.editImpedimentState(selectedState, impedimentsTextArea.getText());
                 //updatedWarningImage = defineWarningImage(task);
                impedimentPopOver.hide();
            });


        SearchableText statusLabel = new SearchableText("Status: ");
        statusLabel.setTextAlignment(TextAlignment.LEFT);
        HBox impedimentComboLabel = new HBox(8);
        impedimentComboLabel.setAlignment(Pos.CENTER_RIGHT);
        impedimentComboLabel.getChildren().addAll(
                statusLabel,
                impedimentCombo
        );


        VBox impedimentChangeNode = new VBox(8);
        impedimentChangeNode.setAlignment(Pos.CENTER_RIGHT);
        impedimentChangeNode.setPadding(new Insets(8,8,8,8));
        impedimentChangeNode.getChildren().addAll(
                impedimentComboLabel,
                impedimentsVBox,
                impedimentSaveButton
        );
        impedimentPopOver.setContentNode(impedimentChangeNode);

        tableCell.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                impedimentPopOver.show(tableCell);
                event.consume();
            });

        return warningImage;
    }

    private ImageView defineWarningImage(Task task) {
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
        return warningImage;
    }

}
