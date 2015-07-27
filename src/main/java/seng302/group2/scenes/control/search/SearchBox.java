package seng302.group2.scenes.control.search;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import seng302.group2.App;
import seng302.group2.scenes.information.skill.SkillInfoTab;
import seng302.group2.scenes.information.skill.SkillScene;

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
                        + "-fx-background-image: url('/icons/search.png');"
                        + "-fx-background-repeat: no-repeat; "
                        + "-fx-background-position: right 6px center; "
        );

        this.setOnKeyReleased(ke -> {
            String text = "Key Typed: " + ke.getCharacter();
            if (ke.isAltDown()) {
                text += " , alt down";
            }
            if (ke.isControlDown()) {
                text += " , ctrl down";
            }
            if (ke.isMetaDown()) {
                text += " , meta down";
            }
            if (ke.isShiftDown()) {
                text += " , shift down";
            }
            if (App.mainPane.getContent() instanceof SkillScene) {

                System.out.println("Querying: " + this.getText());
                if (!this.getText().isEmpty()) {
                    ((SkillInfoTab) ((SkillScene) App.mainPane.getContent()).getCurrentTab()).query(this.getText());
                }

                //System.out.println(App.mainPane.getContent().getClass() + ", " + (ke.getCode() == KeyCode.ENTER));
            }

        });

    }
}
