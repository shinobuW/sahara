package seng302.group2.scenes.control;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;

/**
 * Created by swi67 on 31/08/15.
 */
public class ComboBoxCell extends TableCell<Object, String> {
    /**
     * *A subclass of TableCell to bind combo box to the cell
     * to allow for editing
     */
    private ComboBox<Object> comboBox;
    private ObservableList items;

    /**
     * Constructor
     * @param itemList items to populate the combo box with
     */
    public ComboBoxCell(ObservableList itemList) {
        this.items = itemList;
    }

    /**
     * Sets the cell to a combo box when focused on.
     */
    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createCombo();
            setGraphic(comboBox);

            if (!getText().isEmpty()) {
                comboBox.setValue(getType());
            }
            else {
                comboBox.setValue(null);
            }
            Platform.runLater(() -> {
                    comboBox.requestFocus();
                });
        }
    }

    /**
     * Resets the cell to a label on cancel
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setGraphic(null);
    }

    /**
     * Updates the item
     * @param item the item to update to
     * @param empty if the cell is empty
     */
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        }
        else {
            if (isEditing()) {
                if (comboBox != null) {
                    comboBox.setValue(getType());
                }
                setText(getItem());
                setGraphic(comboBox);
            }
            else {
                setText(getItem());
                setGraphic(null);
            }
        }
    }

    /**
     * Gets the selected item
     * @return the selected item as a class instance
     */
    private Object getType() {
        Object selected = null;
        for (Object saharaItem : items) {
            if (saharaItem.toString() == getItem()) {
                selected = saharaItem;
            }
        }
        return selected;
    }

    /**
     * Creates the combo box and populates it with the itemList. Updates the value in the cell.
     */
    private void createCombo() {
        comboBox = new ComboBox<Object>(this.items);
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
}
