package seng302.group2.scenes.control.search;

import javafx.scene.control.Tab;

import java.util.Collection;

/**
 * An abstract class for searchableTabs. Includes a base implementation of the query method.
 * Created by jml168 on 31/07/15.
 */
public abstract class SearchableTab extends Tab {

    /**
     * Abstract method.
     * Each inheriting class should implement this method to return a collection of their searchable controls.
     * @return A collection of a tabs searchable controls.
     */
    public abstract Collection<SearchableControl> getSearchableControls();

    /**
     * Checks to see if the query matches any string in the searchable controls on this tab.
     * @param query The string to be matched within the tab.
     * @return if the query was found
     */
    public boolean query(String query) {
        boolean found = false;
        for (SearchableControl control : getSearchableControls()) {
            if (control.query(query)) {
                found = true;
            }
        }
        return found;
    }

    /**
     * Checks to see if the query matches any string in the searchable controls on this tab.
     * @param query The string to be matched within the tab.
     * @return if the query was found
     */
    public int advancedQuery(String query, SearchType searchType) {
        int count = 0;
        for (SearchableControl control : getSearchableControls()) {
            if (control.advancedQuery(query, searchType)) {
                count++;
            }
        }
        return count;
    }


    /**
     * Selects the Tab based on the tab index
     * @param index The index of the tab
     * @return
     */
    public boolean select(int index) {
        try {
            getTabPane().getSelectionModel().select(index);
            return true;
        }
        catch (IndexOutOfBoundsException ex) {
            return false;
        }
    }


    /**
     * Selects a tab based on a tab
     * @return
     */
    public boolean select() {
        getTabPane().getSelectionModel().select(this);
        return true;
    }
}
