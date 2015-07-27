package seng302.group2.scenes.control.search;

import javafx.scene.control.TextField;
import seng302.group2.App;
import seng302.group2.scenes.information.skill.SkillInfoTab;
import seng302.group2.scenes.information.skill.SkillScene;

/**
 * A stylised TextField that also performs a basic search query on the current scene content through a key listener
 * Created by jml168 on 26/07/15.
 */
public class SearchBox extends TextField {
    /**
     * Creates a search box element for searching text in the current content scene
     */
    public SearchBox() {
        this.setPromptText("Search...");

        // Rounds the edges, adds an icon, and makes it look like a search box!
        this.setStyle("-fx-border-radius: 12 12 12 12;"
                        + "-fx-background-radius: 12 12 12 12; "
                        + "-fx-background-image: url('/icons/search.png');"
                        + "-fx-background-repeat: no-repeat; "
                        + "-fx-background-position: right 6px center; "
        );

        // Adds some search functionality on the release of a key (so it includes what was entered)
        this.setOnKeyReleased(ke -> {
                if (App.mainPane.getContent() instanceof SkillScene) {  // TODO Abstract out to some searchable GUI
                    System.out.println("Querying: " + this.getText());
                    ((SkillInfoTab) ((SkillScene) App.mainPane.getContent()).getCurrentTab()).query(this.getText());
                }
            });
    }
}
