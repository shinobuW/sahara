package seng302.group2.scenes.information.project.story.task;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import org.controlsfx.control.PopOver;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.tasks.Task;
import seng302.group2.workspace.team.Team;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A Table cell which displays a person icon and the assignee's name. Combo box is displayed on edit.
 * Created by swi67 on 4/09/15.
 */
public class AssigneeTableCell extends TableCell<Object, String> {
    /**
     * A cell used to show the Assignee status.
     */

    public Node assigneeHBox;
    public Story story;
    private ComboBox<Object> comboBox;
    private ObservableList items;

    /**
     * Constructor
     * @param story The currently selected story
     */
    public AssigneeTableCell(Story story, ObservableList itemList) {
        this.story = story;
        this.items = itemList;
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

        if (empty || item == null) {

            setGraphic(null);
        }
        else {
            if (isEditing()) {
                System.out.println("is editing");
                if (comboBox != null) {
                    comboBox.setValue(getType());
                }
                setGraphic(comboBox);
            }
            else {
                if (getTask() != null) {
                    this.assigneeHBox = createAssigneeNode(getTask(), this);
                }
                else if ((Task) getTableView().getSelectionModel().getSelectedItem() != null) {
                    this.assigneeHBox =
                            createAssigneeNode((Task) getTableView().getSelectionModel().getSelectedItem(), this);

                }
                setGraphic(assigneeHBox);

            }
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

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createCombo();

            if (getTask().getAssignee() != null) {
                System.out.println("this is meow");
                System.out.println(getTask().getAssignee());
                comboBox.setValue(getTask().getAssignee());
            }
            setGraphic(comboBox);
            Platform.runLater(() -> {
                    comboBox.requestFocus();
                });
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setGraphic(this.assigneeHBox);
    }

    private void createCombo() {
        comboBox = new ComboBox<>(this.items);
        comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        comboBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0,
                                Boolean arg1, Boolean arg2) {
                if (!arg2) {
                    if (comboBox.getValue() != null) {
                        commitEdit(comboBox.getValue().toString());
                    }
                    else {
                        commitEdit("");
                    }
                }
                else {
                    updateItem(getItem(), false);
                }
            }
        });
    }

    /**
     * Gets the selected item
     * @return the selected item as a class instance
     */
    private Object getType() {
        Object selected = null;
        for (Object saharaItem : items) {
            if (saharaItem.toString().equals(getItem())) {
                selected = saharaItem;
            }
        }
        return selected;
    }

    /**
     * Creates a
     * @param task currently selected task
     * @param tableCell the cell the assignee node is set on
     * @return
     */
    private Node createAssigneeNode(Task task, TableCell tableCell) {
        HBox assigneeHBox = new HBox(10);
        ImageView assigneeImage;
        System.out.println(task);
        if (task.getAssignee() != null) {
            assigneeImage = new ImageView("icons/person.png");
            seng302.group2.scenes.control.Tooltip.create(task.getAssignee().getFullName(), assigneeImage, 50);
        }
        else {
            assigneeImage = new ImageView("icons/person_empty.png");
            seng302.group2.scenes.control.Tooltip.create("This task is unassigned", assigneeImage, 50);
        }

        tableCell.setOnMouseEntered(me -> {
                tableCell.setCursor(Cursor.HAND); //Change cursor to hand
            });
        tableCell.setOnMouseExited(me -> {
                tableCell.setCursor(Cursor.DEFAULT); //Change cursor to hand
            });

        PopOver assignPopOver = new PopOver();
        assignPopOver.setDetachedTitle(task.getShortName() + " Assignment");
        SortedSet<Person> availableAssignees = new TreeSet<>();
        try {
            for (Team team : task.getStory().getProject().getCurrentTeams()) {
                availableAssignees.addAll(team.getPeople());
            }
        }
        catch (NullPointerException ex) {
        }
        Person nonePerson = new Person();
        nonePerson.setShortName("(none)");
        availableAssignees.add(nonePerson);

        ComboBox<Person> assigneeCombo = new ComboBox<>();
        assigneeCombo.getItems().addAll(availableAssignees);
        assigneeCombo.getSelectionModel().select(task.getAssignee());
        assigneeImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (assignPopOver.isShowing()) {
                    assignPopOver.hide();
                }
                else {
                    assignPopOver.show(assigneeImage);
                }
                event.consume();
            });

        Button assigneeSaveButton = new Button("Save Assignee");
        assigneeSaveButton.setAlignment(Pos.CENTER_RIGHT);
        assigneeSaveButton.setOnAction(event -> {
                Person selectedPerson = assigneeCombo.getSelectionModel().getSelectedItem();
                if (selectedPerson.equals(nonePerson)) {
                    selectedPerson = null;
                }
                task.editAssignee(selectedPerson);
                assignPopOver.hide();
            });

        SearchableText assigneeLabel = new SearchableText("Assignee: ");
        assigneeLabel.setTextAlignment(TextAlignment.LEFT);
        HBox assigneeComboLabel = new HBox(8);
        assigneeComboLabel.setAlignment(Pos.CENTER_RIGHT);
        assigneeComboLabel.getChildren().addAll(
                assigneeLabel,
                assigneeCombo
        );
        VBox assigneeChangeNode = new VBox(8);
        assigneeChangeNode.setAlignment(Pos.CENTER_RIGHT);
        assigneeChangeNode.setPadding(new Insets(8, 8, 8, 8));
        assigneeChangeNode.getChildren().addAll(
                assigneeComboLabel,
                assigneeSaveButton
        );
        assignPopOver.setContentNode(assigneeChangeNode);
        assigneeHBox.getChildren().addAll(assigneeImage);
        if (task.getAssignee() != null) {
            assigneeHBox.getChildren().add(new Label(task.getAssignee().toString()));
        }
        return assigneeHBox;
    }

}
