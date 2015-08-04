package seng302.group2.scenes.control.search;

import java.util.Collection;

/**
 * Created by Jordane on 4/08/2015.
 */
public class SearchableTitle extends SearchableText {

    public SearchableTitle() {
        super("", "-fx-font-size: 150%;");
    }

    public SearchableTitle(String content) {
        super(content, "-fx-font-size: 150%;");
    }

    public SearchableTitle(String content, Collection<SearchableControl> collection) {
        super(content, "-fx-font-size: 150%;", collection);
    }

    public SearchableTitle(String content, String style) {
        super(content, "-fx-font-size: 150%;" + style);
    }
}
