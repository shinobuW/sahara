package seng302.group2.scenes.information.project.sprint;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.controlsfx.control.PopOver;
import seng302.group2.scenes.control.Tooltip;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTableRow;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.information.project.story.task.LoggingEffortPane;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.story.tasks.Task;
import seng302.group2.workspace.team.Team;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A ListCell extension for the neat displaying of tasks on the scrum board view
 * Created by Jordane on 23/07/2015.
 */
public class ScrumBoardTaskCell extends ListCell<Task> implements SearchableControl {

    ListView<Task> parentTable = null;
    Task interactiveTask = null;
    ScrumboardTab tab = null;

    Set<SearchableControl> searchControls = new HashSet<>();

    public ScrumBoardTaskCell(ListView<Task> parentTable, ScrumboardTab tab) {
        this.parentTable = parentTable;
        this.tab = tab;
        tab.searchControls.add(this);
    }

    @Override
    public void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);
        if (task != null) {
            // Setup the cell graphic (HBox)
            setGraphic(new ScrumBoardTaskCellNode(task, this));
        }
        else {
            //System.out.println("Item is null");
            setGraphic(null);
        }

        this.setOnDragDetected(event -> {
                interactiveTask = this.getItem();
                Dragboard dragBoard = this.startDragAndDrop(TransferMode.MOVE);
                dragBoard.setDragView(this.snapshot(null, null));
                ClipboardContent content = new ClipboardContent();
                content.putString("");
                dragBoard.setContent(content);
            });

        this.setOnDragOver(event -> {
                int dropIndex;

                if (this.isEmpty()) {
                    dropIndex = this.parentTable.getItems().size() - 1;
                }
                else {
                    dropIndex = this.getIndex();
                }

                //parentTable.getItems().add(dropIndex, interactiveTask);
                tab.hoverIndex = dropIndex;
            });

        setTextOverrun(OverrunStyle.CLIP);
    }

    @Override
    public boolean query(String query) {
        boolean result = false;
        for (SearchableControl control : searchControls) {
            if (control.query(query)) {
                result = true;
            }
        }
        return result;
    }
}