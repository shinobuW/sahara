package seng302.group2.scenes.information.project.backlog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.control.search.SearchableTitle;
import seng302.group2.scenes.dialog.CreateBacklogDialog;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.categories.subCategory.project.BacklogCategory;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.role.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;


/**
 * A class for displaying a tab showing data on all the backlogs in the current project.
 * Created by btm38 on 13/07/15.
 */
public class BacklogCategoryTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();
    /**
     * Constructor for BacklogCategoryTab class.
     * @param selectedCategory The current selected category
     */
    public BacklogCategoryTab(BacklogCategory selectedCategory) {
        this.setText("Basic Information");
        Pane categoryPane = new VBox(10);
        categoryPane.setBorder(null);
        categoryPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(categoryPane);
        this.setContent(wrapper);

        SearchableText title = new SearchableTitle("Backlogs in " + selectedCategory.getProject().toString());

        Button btnView = new Button("View");
        Button btnDelete = new Button("Delete");
        Button btnCreate = new Button("Create New Backlog");

        HBox selectionButtons = new HBox();
        selectionButtons.spacingProperty().setValue(10);
        selectionButtons.getChildren().add(btnView);
        selectionButtons.getChildren().add(btnDelete);
        selectionButtons.getChildren().add(btnCreate);
        selectionButtons.setAlignment(Pos.TOP_LEFT);


        ListView backlogBox = new ListView(selectedCategory.getProject().getBacklogs());
        backlogBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        backlogBox.setMaxWidth(450);

        boolean poExists = false;
        for (Person person : Global.currentWorkspace.getPeople()) {
            if (person.getSkills().containsAll(Role.getRoleFromType(Role.RoleType.PRODUCT_OWNER)
                    .getRequiredSkills())) {
                poExists = true;
            }
        }
        btnCreate.setDisable(!poExists);


        categoryPane.getChildren().add(title);
        categoryPane.getChildren().add(backlogBox);
        categoryPane.getChildren().add(selectionButtons);

        btnView.setOnAction((event) -> {
                if (backlogBox.getSelectionModel().getSelectedItem() != null) {
                    App.mainPane.selectItem((SaharaItem)
                            backlogBox.getSelectionModel().getSelectedItem());
                }
            });


        btnDelete.setOnAction((event) -> {
                if (backlogBox.getSelectionModel().getSelectedItem() != null) {
                    showDeleteDialog((SaharaItem) backlogBox
                            .getSelectionModel().getSelectedItem());
                }
            });

        btnCreate.setOnAction((event) -> {
                javafx.scene.control.Dialog creationDialog = new CreateBacklogDialog();
                creationDialog.show();
            });

        Collections.addAll(searchControls, title);
    }

    /**
     * Gets all the searchable controls on this tab.
     * @return a collection of all the searchable controls on this tab.
     */
    @Override
    public Collection<SearchableControl> getSearchableControls() {
        return searchControls;
    }
}
