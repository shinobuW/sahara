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
public class TagsTableCell extends TableCell<Tag, ObservableList<Tag>> {
    private TagField tagField;
    private TagLabel tagLabel;
    private HBox cell = new HBox();
    private AcceptanceCriteria ac;
    private Story story;

    /**
     * Constructor for tag table cell
     */
    public TagsTableCell(Story story) {
        this.story = story;
        tagLabel = new TagLabel(getTableView().getSelectionModel().getSelectedItems());
    }

    /**
     * Start editing in the tag field
     */
    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createTagField();


            if (!getText().isEmpty()) {
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
        if (empty) {
            setText(null);
            setGraphic(null);
        }
        else {
            cell.getChildren().clear();
            if (isEditing()) {
                if (!getItem().isEmpty()) {
                    tagField.setTags(getItem());
                }
                cell.getChildren().addAll(tagField);
            }
            else {
                // TODO BRONSOn
                if (tagField == null) {
                    setItem(null);
                }
                else if (getTableView().getSelectionModel().getSelectedItems() != null) {
                    tagLabel.construct(getTableView().getSelectionModel().getSelectedItems());
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
