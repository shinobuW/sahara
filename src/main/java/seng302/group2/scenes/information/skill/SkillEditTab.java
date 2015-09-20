package seng302.group2.scenes.information.skill;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.util.validation.ShortNameValidator;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.tag.Tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A class for displaying a tab used to edit skills.
 * Created by btm38 on 30/07/15.
 */
public class SkillEditTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();
    Skill currentSkill;
    RequiredField shortNameCustomField = new RequiredField("Short Name:");
    CustomTextArea descriptionTextArea = new CustomTextArea("Skill Description:", 300);

    /**
     * Constructor for the SkillEditTab class. This constructor creates a JavaFX ScrollPane
     * which is populated with relevant controls and then shown.
     *
     * @param currentSkill The skill being edited
     */
    public SkillEditTab(Skill currentSkill) {
        this.currentSkill = currentSkill;
        this.construct();
    }

    /**
     * Gets all the searchable controls on this tab.
     * @return a collection of all the searchable controls on this tab.
     */
    @Override
    public Collection<SearchableControl> getSearchableControls() {
        return searchControls;
    }

    @Override
    public void construct() {
        // Tab Settings
        this.setText("Edit Skill");
        Pane editPane = new VBox(10);
        editPane.setBorder(null);
        editPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(editPane);
        this.setContent(wrapper);

        // Create Controls

        shortNameCustomField.setMaxWidth(275);
        descriptionTextArea.setMaxWidth(275);

        shortNameCustomField.setText(currentSkill.getShortName());
        descriptionTextArea.setText(currentSkill.getDescription());

        // Add items to pane & search collection
        editPane.getChildren().addAll(shortNameCustomField, descriptionTextArea);
        Collections.addAll(searchControls, shortNameCustomField, descriptionTextArea);
    }

    /**
     * Cancels the edit
     */
    public void cancel() {
        currentSkill.switchToInfoScene();
    }

    /**
     * Changes the values depending on what the user edits
     */
    public void done() {
        boolean shortNameUnchanged = shortNameCustomField.getText().equals(
                currentSkill.getShortName());
        boolean descriptionUnchanged = descriptionTextArea.getText().equals(
                currentSkill.getDescription());

        if (shortNameUnchanged && descriptionUnchanged) {
            currentSkill.switchToInfoScene();
            return;
        }

        boolean correctShortName = ShortNameValidator.validateShortName(shortNameCustomField,
                currentSkill.getShortName());

        ArrayList<Tag> tags = new ArrayList<>();

        if (correctShortName) {
            currentSkill.edit(shortNameCustomField.getText(),
                    descriptionTextArea.getText(),
                    tags
            );

            Collections.sort(Global.currentWorkspace.getSkills());
            currentSkill.switchToInfoScene();
            App.mainPane.refreshTree();

        }
    }
}

