package seng302.group2.scenes.control;

import javafx.scene.control.TextField;

/**
 * Created by jml168 on 26/07/15.
 */
public class SearchBox extends TextField {
    public SearchBox() {

        // Round the edges

        this.setPromptText("Search...");

        //this.setHeight(10);
        this.setStyle("-fx-border-radius: 12 12 12 12;"
                        + "-fx-background-radius: 12 12 12 12; "
                        + "-fx-background-image: url('images/search.png');"
                        + "-fx-background-repeat: no-repeat; "
                        + "-fx-background-position: right center; "
        );

    }
}
