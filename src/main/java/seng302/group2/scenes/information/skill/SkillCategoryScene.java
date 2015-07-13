package seng302.group2.scenes.information.skill;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.dialog.CreateSkillDialog;
import seng302.group2.scenes.information.project.backlog.BacklogCategoryTab;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.categories.subCategory.project.BacklogCategory;
import seng302.group2.workspace.workspace.Workspace;

import javax.sound.midi.Track;

import static seng302.group2.scenes.MainScene.informationPane;
import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;

/**
 * A class for displaying all skills currently created in a workspace.
 *
 * @author David Moseley, Bronson McNaughton
 */
public class SkillCategoryScene extends TrackedTabPane{
    public SkillCategoryScene(Workspace currentWorkspace) {
        super(TrackedTabPane.ContentScene.SKILL_CATEGORY);

        // Define and add the tabs
        Tab categoryTab = new SkillCategoryTab(currentWorkspace);

        this.getTabs().addAll(categoryTab);
    }
}