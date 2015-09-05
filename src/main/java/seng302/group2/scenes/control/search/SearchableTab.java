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
     * @param isAdvanced If the search is an advanced search
     * @return if the query was found
     */
    public boolean query(String query, boolean isAdvanced) {
        boolean found = false;
        if (!isAdvanced) {
            for (SearchableControl control : getSearchableControls()) {
                if (control.query(query)) {
                    found = true;
                }
            }
        }
        else {
            for (SearchableControl control : getSearchableControls()) {
                if (control.advancedQuery(query)) {
                    found = true;
                }
            }
        }
        return found;
    }

    /**
     * Checks to see if the query matches any string in the searchable controls on this tab.
     * @param query The string to be matched within the tab.
     * @return if the query was found
     */
    public boolean advancedQuery(String query) {
        boolean found = false;

        return found;
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
