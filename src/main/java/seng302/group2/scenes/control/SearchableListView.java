package seng302.group2.scenes.control;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.workspace.skills.Skill;

import java.util.Collection;

/**
 * A custom searchable list view that highlights matching items when queried
 * Created by btm38 on 3/08/15.
 */
public class SearchableListView<T> extends ListView<T> implements SearchableControl {
    private ListView<T> listView = new ListView<>();

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
    public SearchableListView(ObservableList<T> listItems) {
        super(listItems);
    }


    /**
     * Constructor for a SearchableListView that takes an initial set of items and
     * @param listItems The initial items to add to the list
     * @param searchableControls A collection of searchable controls to add this control to
     */
    public SearchableListView(ObservableList<T> listItems, Collection<SearchableControl> searchableControls) {
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

        if (query.trim().isEmpty()) {

            return false;
        }
        boolean foundList = false;
        System.out.println(listView.getItems());
        for (T item : listView.getItems()) {
            if (item.toString().toLowerCase().contains(query.toLowerCase())) {
                foundList = true;
            }
        }

        if (foundList) {
            this.setStyle("-fx-border-color: " + SearchableControl.highlightColour + ";");

        }

        return foundList;

    }
}
