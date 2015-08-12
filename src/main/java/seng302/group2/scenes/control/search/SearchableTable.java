package seng302.group2.scenes.control.search;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import java.util.Collection;

/**
 * Created by jml168 on 11/08/15.
 */
public class SearchableTable<T> extends TableView<T> implements SearchableControl {


    ObservableList<T> data = FXCollections.observableArrayList();
    ObservableList<T> matchingItems = FXCollections.observableArrayList();


    public SearchableTable() {
        super();
        updateRows();
    }

    public SearchableTable(Collection<T> data) {
        super();
        updateRows();
        setData(data);
    }




    public void setData(Collection<T> data) {
        this.data.clear();
        this.data.addAll(data);
        setItems(this.data);
    }




    @Override
    public boolean query(String query) {
        //Set<T> tableItems = new HashSet<>();
        matchingItems.clear();
        if (!query.isEmpty()) {
            ObservableList<TableColumn<T, ?>> cols = this.getColumns();

            for (T aData : data) {
                for (TableColumn<T, ?> col : cols) {
                    String cellValue = col.getCellData(aData).toString();
                    cellValue = cellValue.toLowerCase();
                    if (cellValue.contains(query.trim().toLowerCase())) {
                        matchingItems.add(aData);
                    }
                }
            }
        }

        updateRows();
        return !matchingItems.isEmpty();
    }



    private void updateRows() {
        setRowFactory(tv -> new TableRow<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);

                if (item != null) {
                    System.out.println("item : " + item.toString());
                    System.out.println("matching items : " + matchingItems);
                    if (matchingItems.contains(item)) {
                        setStyle("-fx-background-color: " + SearchableControl.highlightColour + "; ");
                    }
                    else {
                        setStyle(null);
                        //setStyle("-fx-background-color: " + Color.TRANSPARENT + ";");
                    }
                }
            }
        });
    }
}
