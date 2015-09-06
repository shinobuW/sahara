package seng302.group2.scenes.dialog;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.MainPane;
import seng302.group2.scenes.SearchResultPane;
import seng302.group2.scenes.control.CustomTextField;
import seng302.group2.scenes.control.search.SearchableText;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class to create a popup dialog for advanced search
 * Created by btm38 on 17/08/15.
 */
public class CreateSearchPopOver extends PopOver {


    /**
     * Displays the dialog box for advanced search
     */
    public CreateSearchPopOver() {
        this.setDetachedTitle("Search Workspace");
        this.setMinWidth(400);
        this.setMinHeight(250);
        this.setDetached(true);

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER_RIGHT);
        Button btnSearch = new Button("Search");
        Button btnCancel = new Button("Cancel");
        buttons.spacingProperty().setValue(10);
        Insets insets = new Insets(0, 20, 0, 20);
        buttons.setPadding(insets);
        buttons.getChildren().addAll(btnCancel, btnSearch);
        btnSearch.setDisable(true);

        VBox grid = new VBox();
        Insets insets1 = new Insets(20, 20, 20, 20);
        grid.spacingProperty().setValue(10);
        grid.setPadding(insets1);

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
                new Separator(), onlySearchLabel, checkBoxPane, buttons);

        //Add grid of controls to dialog
        this.setContentNode(grid);

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


        searchField.getTextField().textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (newValue.equals("")) {
                        btnSearch.setDisable(true);
                    }
                    else {
                        btnSearch.setDisable(false);
                    }
                }
            });

        btnSearch.setOnAction(event -> {
                this.hide();
                String searchText = searchField.getText();
                List<String> checkedItems = getCheckedItems(workspaceSearchCheck,
                        modelCheckBoxes);
                PopOver resultsPopOver = new SearchResultPane(checkedItems, searchText);

                resultsPopOver.show(App.mainStage);

            });

        btnCancel.setOnAction(event -> {
                Global.advancedSearchExists = false;
                this.hide();
            });




    }


    /**
     * Gets the checked item.
     * @param workspaceSearchCheck The check box for searching through the whole workspace
     * @param modelCheckBoxes List of check boxes in the search dialog
     * @returna list of checked items
     */
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
