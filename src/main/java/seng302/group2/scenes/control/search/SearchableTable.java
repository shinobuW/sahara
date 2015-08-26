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
    SearchableTableRow<T> tableRowType = new SearchableTableRow<>(this);


    /**
     * Basic constructor.
     */
    public SearchableTable() {
        super();
        updateRows();
    }


    /**
     * Basic constructor. Sets the data of the table to that provided in the parameter.
     * @param data the new data of the table
     */
    public SearchableTable(ObservableList<T> data) {
        super();
        updateRows();
        setData(data);
    }


    /**
     * Basic constructor. Sets the data of the table to that provided in the parameter.
     * @param data the new data of the table
     * @param rowFactory the table row factory that is used for the table. Default is SearchableTableRow
     */
    public SearchableTable(ObservableList<T> data, SearchableTableRow<T> rowFactory) {
        super();
        this.data = data;
    }


    /**
     * Basic constructor. Sets the data of the table to that provided in the parameter.
     * @param data the new data of the table
     * @param searchableControls The collection of searchable controls to add this control to
     */
    public SearchableTable(Collection<T> data, Collection<SearchableControl> searchableControls) {
        super();
        searchableControls.add(this);
        updateRows();
        setData(data);
    }


    /**
     * Basic constructor. Sets the data of the table to that provided in the parameter.
     * @param data the new data of the table
     * @param rowFactory the table row factory that is used for the table. Default is SearchableTableRow
     * @param searchableControls The collection of searchable controls to add this control to
     */
    public SearchableTable(Collection<T> data, SearchableTableRow<T> rowFactory,
                           Collection<SearchableControl> searchableControls) {
        super();
        searchableControls.add(this);
        this.tableRowType = rowFactory;
        updateRows();
        setData(data);
    }


    /**
     * Clears any exsisting data of a SearchableTable, then sets a new set of data.
     * @param data The data to be set to the table
     */
    public void setData(Collection<T> data) {
        this.data.clear();
        this.data.addAll(data);
        setItems(this.data);
    }


    /**
     * Checks all the elements in the table to see if they have a matching string.
     * @param query the string to be queried
     * @return whether or not a matching string was found
     */
    @Override
    public boolean query(String query) {
        //Set<T> tableItems = new HashSet<>();
        matchingItems.clear();
        if (!query.isEmpty()) {
            ObservableList<TableColumn<T, ?>> cols = this.getColumns();

            for (T aData : this.getItems()) {

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


    /**
     * Highlights a row if a matching query is found within that row. If there is no matching query,
     * the row's style is set to null (default).
     */
    private void updateRows() {
        setRowFactory(tv -> new TableRow<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);

                if (item != null) {
                    if (matchingItems.contains(item)) {
                        setStyle("-fx-background-color: " + SearchableControl.highlightColourString + "; ");
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
