package seng302.group2.scenes.dialog;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.scenes.SearchResultPane;
import seng302.group2.scenes.control.CustomTextField;
import seng302.group2.scenes.control.search.SearchableText;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Class to create a popup dialog for advanced search
 * Created by btm38 on 17/08/15.
 */
public class CreateSearchDialog extends javafx.scene.control.Dialog<Map<String, String>> {

    /**
     * Displays the dialog box for advanced search
     */
    public CreateSearchDialog() {
        this.setTitle("Search Workspace");
        this.getDialogPane().setStyle(" -fx-max-width:400px; -fx-max-height: 250px; -fx-pref-width: 400px; "
                + "-fx-pref-height: 250px;");

        ButtonType btnTypeSearch = new ButtonType("Search", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(btnTypeSearch, ButtonType.CANCEL);

        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);

        CustomTextField searchField = new CustomTextField("Search for:");

        CheckBox workspaceSearchCheck = new CheckBox("Search entire workspace");
        workspaceSearchCheck.setSelected(true);

        SearchableText onlySearchLabel = new SearchableText("Only Search:");
        GridPane checkBoxPane = new GridPane();
        checkBoxPane.setHgap(10);
        checkBoxPane.setVgap(10);

        List<CheckBox> modelCheckBoxes = new ArrayList<>();

        CheckBox projectSearchCheck = new CheckBox("Projects");
        checkBoxPane.add(projectSearchCheck, 0, 0);
        modelCheckBoxes.add(projectSearchCheck);

        CheckBox releaseSearchCheck = new CheckBox("Releases");
        checkBoxPane.add(releaseSearchCheck, 1, 0);
        modelCheckBoxes.add(releaseSearchCheck);

        CheckBox backlogSearchCheck = new CheckBox("Backlogs");
        checkBoxPane.add(backlogSearchCheck, 2, 0);
        modelCheckBoxes.add(backlogSearchCheck);

        CheckBox storySearchCheck = new CheckBox("Stories");
        checkBoxPane.add(storySearchCheck, 0, 1);
        modelCheckBoxes.add(storySearchCheck);

        CheckBox taskSearchCheck = new CheckBox("Tasks");
        checkBoxPane.add(taskSearchCheck, 1, 1);
        modelCheckBoxes.add(taskSearchCheck);

        CheckBox sprintSearchCheck = new CheckBox("Sprints");
        checkBoxPane.add(sprintSearchCheck, 2, 1);
        modelCheckBoxes.add(sprintSearchCheck);

        CheckBox teamSearchCheck = new CheckBox("Teams");
        checkBoxPane.add(teamSearchCheck, 0, 2);
        modelCheckBoxes.add(teamSearchCheck);

        CheckBox personSearchCheck = new CheckBox("People");
        checkBoxPane.add(personSearchCheck, 1, 2);
        modelCheckBoxes.add(personSearchCheck);

        CheckBox roleSearchCheck = new CheckBox("Roles");
        checkBoxPane.add(roleSearchCheck, 2, 2);
        modelCheckBoxes.add(roleSearchCheck);

        CheckBox skillSearchCheck = new CheckBox("Skills");
        checkBoxPane.add(skillSearchCheck, 0, 3);
        modelCheckBoxes.add(skillSearchCheck);

        CheckBox allocationSearchCheck = new CheckBox("Allocations");
        checkBoxPane.add(allocationSearchCheck, 1, 3);
        modelCheckBoxes.add(allocationSearchCheck);

        CheckBox acceptanceCriteriaSearchCheck = new CheckBox("Acceptance Criteria");
        checkBoxPane.add(acceptanceCriteriaSearchCheck, 2, 3);
        modelCheckBoxes.add(acceptanceCriteriaSearchCheck);

        CheckBox logSearchCheck = new CheckBox("Logs");
        checkBoxPane.add(logSearchCheck, 0, 4);
        modelCheckBoxes.add(logSearchCheck);

        grid.getChildren().addAll(searchField, workspaceSearchCheck,
                new Separator(), onlySearchLabel, checkBoxPane);

        //Add grid of controls to dialog
        this.getDialogPane().setContent(grid);

        EventHandler eh = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (event.getSource() instanceof CheckBox) {
                    CheckBox chk = (CheckBox) event.getSource();
                    if ("Search entire workspace".equals(chk.getText())
                            && chk.isSelected()) {
                        for (CheckBox box : modelCheckBoxes) {
                            box.setSelected(false);
                        }
                    }
                    else if (chk.isSelected()) {
                        workspaceSearchCheck.setSelected(false);
                    }
                    else if (!chk.isSelected()) {
                        boolean anyTicked = false;
                        for (CheckBox box : modelCheckBoxes) {
                            if (box.isSelected()) {
                                anyTicked = true;
                            }
                        }
                        if (!anyTicked) {
                            workspaceSearchCheck.setSelected(true);
                        }
                    }
                }
            }
        };

        workspaceSearchCheck.setOnAction(eh);
        for (CheckBox box : modelCheckBoxes) {
            box.setOnAction(eh);
        }


        Node searchButton = this.getDialogPane().lookupButton(btnTypeSearch);
        searchButton.setDisable(true);

        searchField.getTextField().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equals("")) {
                    searchButton.setDisable(true);
                }
                else {
                    searchButton.setDisable(false);
                }
            }
        });

        this.setResultConverter(b -> {
                if (b == btnTypeSearch) {
                    String searchText = searchField.getText();
                    List<String> checkedItems = getCheckedItems(workspaceSearchCheck,
                            modelCheckBoxes);
                    SearchResultPane resultPane = new SearchResultPane(checkedItems, searchText);
                    App.showSearchResults(resultPane);
                    this.close();
                }
                return null;
            });
        this.setResizable(false);
        this.show();
    }

    protected static List<String> getCheckedItems(CheckBox workspaceSearchCheck,
                                                  List<CheckBox> modelCheckBoxes) {
        List<String> checkedItems = new ArrayList<>();

        if (workspaceSearchCheck.isSelected()) {
            checkedItems.add("Projects");
            checkedItems.add("Releases");
            checkedItems.add("Backlogs");
            checkedItems.add("Stories");
            checkedItems.add("Tasks");
            checkedItems.add("Sprints");
            checkedItems.add("Teams");
            checkedItems.add("People");
            checkedItems.add("Roles");
            checkedItems.add("Skills");
            checkedItems.add("Allocations");
            checkedItems.add("Acceptance Criteria");
            checkedItems.add("Logs");
        }
        else {
            for (CheckBox checkBox : modelCheckBoxes) {
                if (checkBox.isSelected()) {
                    checkedItems.add(checkBox.getText());
                }
            }
        }
        return checkedItems;
    }
}
