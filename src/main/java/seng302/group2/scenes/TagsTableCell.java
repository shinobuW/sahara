package seng302.group2.scenes;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import seng302.group2.scenes.control.search.TagField;
import seng302.group2.scenes.control.search.TagLabel;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.acceptanceCriteria.AcceptanceCriteria;
import seng302.group2.workspace.tag.Tag;

/**
 * A cell used for storing tags
 * Created by btm38 on 21/09/15.
 */
public class TagsTableCell extends TableCell<AcceptanceCriteria, ObservableList<Tag>> {
    private TagField tagField;
    private TagLabel tagLabel = new TagLabel();
    private HBox cell = new HBox();
    private AcceptanceCriteria ac;
    private Story story;

    /**
     * Constructor for tag table cell
     */
    public TagsTableCell(Story story) {
        this.story = story;
        if (getTableView() != null) {
            tagLabel = new TagLabel(getTableView().getSelectionModel().getSelectedItem().getTags());
        }
    }

    /**
     * Start editing in the tag field
     */
    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createTagField();

            if (getItem() != null && !getItem().isEmpty()) {
                tagField.setTags(getItem());
            }
            else {
                tagField.setTags(null);
            }

            cell.getChildren().clear();
            cell.getChildren().add(tagField);
            Platform.runLater(() -> {
                tagField.requestFocus();
            });
        }
    }

    /**
     * Cancel the editing of the cell.
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        cell.getChildren().clear();
        cell.getChildren().add(tagLabel);
        setGraphic(cell);
    }

    /**
     * Updates the item
     *
     * @param item  the item to update to
     * @param empty if the cell is empty
     */
    @Override
    public void updateItem(ObservableList<Tag> item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        }
        else {
            cell.getChildren().clear();
            if (isEditing()) {
                System.out.println("get item = " + getItem());
                if (getItem() != null && !getItem().isEmpty()) {
                    System.out.println("we are inside the loop");
                    tagField.setTags(getItem());
                }
                cell.getChildren().addAll(tagField);
            }
            else {
                if (getTableView().getSelectionModel().getSelectedItem() != null) {
                    tagLabel.constructAC(getTableView().getSelectionModel().getSelectedItem());
                }

                cell.getChildren().add(tagLabel);
            }
            setGraphic(cell);
        }
    }

    /**
     * Creates a time textfield for use in the editing cell
     */
    private void createTagField() {
        tagField = new TagField();
        tagField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        tagField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0,
                                Boolean arg1, Boolean arg2) {
                if (!arg2) {
                    if (tagField.getTags() != null) {
                        commitEdit(tagField.getTags());
                    }
                    else {
                        commitEdit(null);
                    }
                }
                else {
                    updateItem(getItem(), false);
                }
            }
        });
    }

    private void createTagLabel() {
        tagLabel = new TagLabel(ac.getTags());
    }

    private ObservableList<Tag> getCurrTags() {
        if (getItem().isEmpty()) {
            return getItem();
        }
        else {
            return getItem() == null ? null : getItem();
        }
    }
}
