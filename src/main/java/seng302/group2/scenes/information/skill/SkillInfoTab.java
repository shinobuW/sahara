package seng302.group2.scenes.information.skill;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.workspace.skills.Skill;

import java.util.ArrayList;
import java.util.List;


/**
 * The skill information tab
 * Created by jml168 on 11/05/15.
 */
public class SkillInfoTab extends Tab {

    List<SearchableText> searchableTexts = new ArrayList<>();

    SearchableText title = new SearchableText("Short Name");


    private void init() {
        searchableTexts.add(title);
    }


    public boolean query(String query) {
        boolean found = false;
        for (SearchableText text : searchableTexts) {
            if (text != null && text.query(query)) {
                found = true;
            }
        }
        return found;
    }


    /**
     * Constructor for the SkillInfoTab class.
     * @param currentSkill the current skill for which information will be displayed
     */
    public SkillInfoTab(Skill currentSkill) {
        init();

        this.setText("Basic Information");

        Pane basicInfoPane = new VBox(10);  // The pane that holds the basic info
        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        title.setText(currentSkill.getShortName());

        Button btnEdit = new Button("Edit");

        basicInfoPane.getChildren().add(title);
        basicInfoPane.getChildren().add(new Label("Skill Description: "
                + currentSkill.getDescription()));

        basicInfoPane.getChildren().add(btnEdit);

        if (currentSkill.getShortName().equals("Product Owner")
                || currentSkill.getShortName().equals("Scrum Master")) {
            btnEdit.setDisable(true);
        }

        btnEdit.setOnAction((event) -> {
                currentSkill.switchToInfoScene(true);
            });
    }
}
