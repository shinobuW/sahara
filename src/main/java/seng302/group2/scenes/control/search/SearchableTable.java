package seng302.group2.scenes.control.search;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import jdk.nashorn.internal.codegen.CompilerConstants;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.story.Story;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;

/**
 * Created by jml168 on 11/08/15.
 */
public class SearchableTable<T> extends TableView<T> implements SearchableControl {


    ObservableList<T> data = FXCollections.observableArrayList();


    public SearchableTable() {
        super();
    }

    public SearchableTable(Collection<T> data) {
        super();
        setData(data);
    }


    public void setData(Collection<T> data) {
        this.data.clear();
        this.data.addAll(data);
        setItems(this.data);
    }




    @Override
    public boolean query(String query) {
        if (query.isEmpty()) {
            this.setItems(this.data);
        }

        ObservableList<T> tableItems = FXCollections.observableArrayList();
        ObservableList<TableColumn<T,?>> cols = this.getColumns();
        //ObservableList<TableColumn<T, ?>> cols = this.getColumns();
        for (T aData : data) {
            for (TableColumn<T, ?> col : cols) {
                String cellValue = col.getCellData(aData).toString();
                cellValue = cellValue.toLowerCase();
                if (cellValue.contains(query.trim().toLowerCase())) {
                    tableItems.add(aData);
                    System.out.println(aData);
                }
            }

        }



        this.setRowFactory(new Callback<TableView<T>,TableRow<T>>() {
            @Override
            public TableRow<T> call(TableView<T> tableView) {
                final TableRow<T> row = new TableRow<T>() {
                    @Override
                    protected void updateItem(T item, boolean empty) {
                        super.updateItem(item, empty);

                        System.out.println(item.toString());

                        if (tableItems.contains(item)) {
                            setStyle("-fx-background-color: red;");
                        }
                        else {
                            setStyle("-fx-background-color: inherit;");
                        }
                    }
                };
                return row;
            }

        });



        //this.setItems(tableItems);




        return false;
    }
}
