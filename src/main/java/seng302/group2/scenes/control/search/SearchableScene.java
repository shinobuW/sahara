package seng302.group2.scenes.control.search;

import javafx.scene.control.TabPane;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A searchable scene that extends TabPane. Contains a set of SearchableTabs
 * which can be iterated over to search through.
 * Created by jml168 on 31/07/15.
 */
public abstract class SearchableScene extends TabPane {

    public abstract Collection<SearchableTab> getSearchableTabs();

    /**
     * Searches each tab of the scene to try and find a match
     * @param query the query string
     * @return a collection of tabs with items found on them
     */
    public Set<SearchableTab> query(String query) {
        Set<SearchableTab> matches = new HashSet<>();

        for (SearchableTab tab : getSearchableTabs()) {
            if (tab.query(query)) {
                matches.add(tab);
            }
        }

        return matches;
    }
}
