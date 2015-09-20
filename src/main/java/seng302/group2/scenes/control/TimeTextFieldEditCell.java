package seng302.group2.scenes.control;

import javafx.scene.control.TableCell;
import seng302.group2.workspace.SaharaItem;

/**
 * Custom table cell with a time text field
 * Created by swi67 on 20/09/15.
 */
public class TimeTextFieldEditCell extends TableCell<SaharaItem, String> {
    private TimeTextField timeTextField;



    public TimeTextFieldEditCell() {

    }

//    /**
//     * Start editing the date picker cell
//     */
//    @Override
//    public void startEdit() {
//        if (!isEmpty()) {
//            super.startEdit();
//            createTimeTextField();
//            setGraphic(datePicker);
//
//            if (!getText().isEmpty()) {
//                timeTextField.(LocalDate.parse(getText(),
//                        DateTimeFormatter.ofPattern("dd/MM/yyyy")));
//            }
//            else {
//                timeTextField.setValue(null);
//            }
//            Platform.runLater(() -> {
//                timeTextField.requestFocus();
//            });
//
//        }
//    }

    /**
     * Cancel the editing of the cell.
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setGraphic(null);
    }
}
