package seng302.group2.scenes.information.skill;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.dialog.CreateSkillDialog;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.workspace.Workspace;

import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;

/**
 * Shows the Skill Category Tab, which has information about all the skills in the workspace.
 * Created by btm38 on 13/07/15.
 */
public class SkillCategoryTab extends Tab {
    /**
     * Basic constructor for the SkillCategoryTab.
     * @param currentWorkspace the current workspace
     */
    public SkillCategoryTab(Workspace currentWorkspace) {
        this.setText("Basic Information");
        Pane skillPane = new VBox(10);
        skillPane.setBorder(null);
        skillPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(skillPane);
        this.setContent(wrapper);
        Label title = new TitleLabel("Skills in " + currentWorkspace.getShortName());

        Button btnView = new Button("View");
        Button btnDelete = new Button("Delete");
        Button btnCreate = new Button("Create New Skill");

        HBox selectionButtons = new HBox();
        selectionButtons.spacingProperty().setValue(10);
        selectionButtons.getChildren().add(btnView);
        selectionButtons.getChildren().add(btnDelete);
        selectionButtons.getChildren().add(btnCreate);
        selectionButtons.setAlignment(Pos.TOP_LEFT);

        ListView skillBox = new ListView(currentWorkspace.getSkills());
        skillBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        skillBox.setMaxWidth(275);

        skillPane.getChildren().add(title);
        skillPane.getChildren().add(skillBox);
        skillPane.getChildren().add(selectionButtons);


        btnView.setOnAction((event) -> {
                if (skillBox.getSelectionModel().getSelectedItem() != null) {
                    App.mainPane.selectItem((SaharaItem)
                            skillBox.getSelectionModel().getSelectedItem());
                }
            });

        btnDelete.setOnAction((event) -> {
                if (skillBox.getSelectionModel().getSelectedItem() != null) {
                    showDeleteDialog((SaharaItem)
                            skillBox.getSelectionModel().getSelectedItem());
                }
            });

        btnCreate.setOnAction((event) -> {
                CreateSkillDialog.show();
            });
    }
}

