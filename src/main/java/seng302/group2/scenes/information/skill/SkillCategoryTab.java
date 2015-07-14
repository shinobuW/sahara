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
 * A class for displaying a tab showing data on all the skills in the workspace.
 * Created by btm38 on 13/07/15.
 */
public class SkillCategoryTab extends Tab {
    /**
     * Constructor for SkillCategoryTab class.
     * @param currentWorkspace The current workspace
     */
    public SkillCategoryTab(Workspace currentWorkspace) {
        this.setText("Basic Information");
        Pane categoryPane = new VBox(10);
        categoryPane.setBorder(null);
        categoryPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(categoryPane);
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

        ListView skillBox = new ListView<>(currentWorkspace.getSkills());
        skillBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        skillBox.setMaxWidth(275);

        categoryPane.getChildren().add(title);
        categoryPane.getChildren().add(skillBox);
        categoryPane.getChildren().add(selectionButtons);


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
