package seng302.group2.scenes.control.search;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A searchable TextFlow that allows querying to highlight and alert the user of text they are trying to find through
 * other search elements such as a SearchBox.
 * Created by Jordane on 27/07/2015.
 */
public class SearchableText extends TextFlow implements SearchableControl {
    List<TextFlow> texts = new ArrayList<>();
    String styleDefault = "-fx-background-color: inherit;";
    String styleInject = "";
    String styleHighlighted = "-fx-background-color:" + SearchableControl.highlightColour + ";";

    /**
     * Creates a SearchableText TextFlow element
     */
    public SearchableText() {
        texts.add(new TextFlow());
        updateFlow();
    }

    /**
     * Creates a SearchableText TextFlow element
     * @param content The initial string text of the SearchableText
     */
    public SearchableText(String content) {
        texts.add(new TextFlow(new Text(content)));
        updateFlow();
    }


    /**
     * Creates a SearchableText TextFlow element with the applied JavaFX style string
     * @param content The string text for the SearchableText to adopt
     * @param collection The collection to add this text to (on the fly)
     */
    public SearchableText(String content, Collection<SearchableControl> collection) {
        collection.add(this);
        texts.clear();
        texts.add(new TextFlow(new Text(content)));
        updateFlow();
    }


    /**
     * Creates a SearchableText TextFlow element with the applied JavaFX style string
     * @param content The string text for the SearchableText to adopt
     * @param style The style for the SearchableText to adopt
     */
    public SearchableText(String content, String style) {
        this.styleInject = style;
        this.setStyle(styleInject);
        texts.add(new TextFlow(new Text(content)));
        updateFlow();
    }


    /**
     * Creates a SearchableText TextFlow element with the applied JavaFX style string
     * @param content The string text for the SearchableText to adopt
     * @param style The style for the SearchableText to adopt
     * @param collection The collection to add this text to (on the fly)
     */
    public SearchableText(String content, String style, Collection<SearchableControl> collection) {
        collection.add(this);
        this.styleInject = style;
        this.setStyle(styleInject);
        texts.add(new TextFlow(new Text(content)));
        updateFlow();
    }


    /**
     * Sets the current text of the SearchableText to the string given
     * @param content The string text for the SearchableText to adopt
     */
    public void setText(String content) {
        texts.clear();
        texts.add(new TextFlow(new Text(content)));
        updateFlow();
    }


    /**
     * Updates the text inside the SearchableText to match any updated children
     */
    void updateFlow() {
        this.getChildren().clear();
        this.getChildren().remove(0, this.getChildren().size());
        this.getChildren().addAll(texts);
    }


    /**
     * Stitches all current Text nodes together into a single unformatted Text node
     * @return the stitched Text node
     */
    private Text stitch() {
        String content = "";
        for (TextFlow tFlow : texts) {
            content += ((Text)(tFlow.getChildren().get(0))).getText();
        }
        texts.clear();
        Text stitched = new Text(content);
        texts.add(new TextFlow(stitched));

        updateFlow();

        return stitched;
    }


    /**
     * Performs a basic string query on the SearchableText element, highlighting any matches with a flashy styling.
     * Queries are case-insensitive
     * @param query The string to query
     * @return If at least one match was found
     */
    @Override
    public boolean query(String query) {
        query = query.toLowerCase();
        Text text = stitch();
        String content = text.getText();

        //System.out.println("whole text: " + content);

        int index = content.toLowerCase().indexOf(query);

        if (index == -1 || query.trim().isEmpty()) {
            for (TextFlow flow : texts) {
                flow.setStyle(styleDefault + styleInject);
            }
            updateFlow();
            return false;  // Query not in text, keep a single, stitched Text node.
        }

        List<TextFlow> builtText = new ArrayList<>();
        while (index != -1) {
            Text matchText = new Text(content.substring(index, index + query.length()));
            TextFlow match = new TextFlow(matchText);
            match.setStyle(styleInject + styleHighlighted);
            builtText.add(new TextFlow(new Text(content.substring(0, index))));  // Start
            builtText.add(match);  // Query match
            builtText.add(new TextFlow(new Text(content.substring(index + query.length(), content.length()))));  // End

            content = content.substring(index + query.length());
            index = content.toLowerCase().indexOf(query);
            if (index != -1) {
                builtText.remove(builtText.size() - 1);
            }
        }

        texts.clear();
        texts.addAll(builtText);

        updateFlow();

        return true;
    }
}
