package seng302.group2.scenes.control;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.workspace.SaharaItem;

import java.util.Collection;

/**
 * A custom searchable list view that highlights matching items when queried
 * Created by btm38 on 3/08/15.
 */
public class SearchableListView<SaharaItem> extends ListView<SaharaItem> implements SearchableControl {

    /**
     * Basic constructor for a SearchableListView
     */
    public SearchableListView() {
        super();

    }


    /**
     * Constructor for a SearchableListView that takes an initial set of items
     * @param listItems The initial items to add to the list
     */
    public SearchableListView(ObservableList<SaharaItem> listItems) {
        super(listItems);
    }


    /**
     * Constructor for a SearchableListView that takes an initial set of items and
     * @param listItems The initial items to add to the list
     * @param searchableControls A collection of searchable controls to add this control to
     */
    public SearchableListView(ObservableList<SaharaItem> listItems, Collection<SearchableControl> searchableControls) {
        super(listItems);
        searchableControls.add(this);

    }


    /**
     * Queries the contents of the list view, highlighting any contents that match
     * @param query The search string to query the list contents with
     * @return If a match was found for the query
     */
    @Override
    public boolean query(String query) {
        boolean foundList = false;

        this.setCellFactory(new Callback<ListView<SaharaItem>, ListCell<SaharaItem>>() {
            @Override
            public ListCell<SaharaItem> call(ListView<SaharaItem> param) {
                ListCell<SaharaItem> cell = new ListCell<SaharaItem>() {
                    @Override
                    public void updateItem(SaharaItem item, boolean empty)
                    {
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        }
                        else {
                            if (query.trim().isEmpty()) {
                                setText(item.toString());
                                setStyle("-fx-background-color: inherit");
                            }
                            else if (queryCell(query, item.toString())) {
                                setText(item.toString());
                                setStyle("-fx-background-color:" + SearchableControl.highlightColour + ";");
                            }
                            else {
                                setText(item.toString());
                                setStyle("-fx-background-color: inherit");
                            }
                        }
                    }
                };
                return cell;
            }
        });

        for (SaharaItem item : this.getItems()) {
            if (item.toString().toLowerCase().contains(query.toLowerCase())) {
                foundList = true;
            }
        }

        return foundList;
    }

    public boolean queryCell(String query, String string) {
        if(query.trim().isEmpty()) {
            return false;
        }

        if (string.toLowerCase().contains(query.toLowerCase()))
        {
            return true;
        }

        return false;
    }
}
