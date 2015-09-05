package seng302.group2.scenes.control.search;

import java.util.Collection;

/**
 * A class that provides functionality for titles to be searched. Inherits from SearchableText
 * and provides constructors that set specified css for titles.
 * Created by Jordane on 4/08/2015.
 */
public class SearchableTitle extends SearchableText {

    /**
     * Basic constructor for SearchableTitle. Calls super on a null string, and sets the font size to 150%.
     */
    public SearchableTitle() {
        super("", "-fx-font-size: 150%;");
    }


    /**
     * Creates a SearchableTitle element sets the provided strings font size to 150%.
     * @param content The string text for the SearchableText to adopt
     */
    public SearchableTitle(String content) {
        super(content, "-fx-font-size: 150%;");
    }

    /**
     * Creates a SearchableTitle element sets the provided strings font size to 150%.
     * @param content The string text for the SearchableText to adopt
     * @param collection collection The collection to add this text to
     */
    public SearchableTitle(String content, Collection<SearchableControl> collection) {
        super(content, "-fx-font-size: 150%;", collection);
    }

    /**
     * Creates a SearchableTitle element with a specified style.
     * @param content The string text for the SearchableText to adopt
     * @param style The css styling to apply to the string
     */
    public SearchableTitle(String content, String style) {
        super(content, "-fx-font-size: 150%;" + style);
    }


    @Override
    public boolean advancedQuery(String query) {
        boolean found = false;
        if (this.query(query)) {
            found = true;
        }
        return found;
    }
}
