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
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchResultCellNode;
import seng302.group2.scenes.control.search.SearchType;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.information.person.PersonScene;
import seng302.group2.scenes.information.project.ProjectScene;
import seng302.group2.scenes.information.project.backlog.BacklogScene;
import seng302.group2.scenes.information.project.release.ReleaseScene;
import seng302.group2.scenes.information.project.sprint.SprintScene;
import seng302.group2.scenes.information.project.story.StoryScene;
import seng302.group2.scenes.information.role.RoleScene;
import seng302.group2.scenes.information.skill.SkillScene;
import seng302.group2.scenes.information.team.TeamScene;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.release.Release;
import seng302.group2.workspace.project.sprint.Sprint;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

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

        HBox radioBox = new HBox();
        ToggleGroup group = new ToggleGroup();
        RadioButton normalBtn = new RadioButton("Normal");
        normalBtn.setToggleGroup(group);
        normalBtn.setSelected(true);
        RadioButton regexBtn = new RadioButton("Regular Expression");
        regexBtn.setToggleGroup(group);
        RadioButton wildcardBtn = new RadioButton("Wildcard");
        wildcardBtn.setToggleGroup(group);

        radioBox.getChildren().addAll(normalBtn, regexBtn, wildcardBtn);

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

        grid.getChildren().addAll(searchField, radioBox, new Separator(), workspaceSearchCheck, onlySearchLabel,
                checkBoxPane, buttons);

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
                if (normalBtn.isSelected()) {
                    this.hide();
                    String searchText = searchField.getText();
                    List<String> checkedItems = getCheckedItems(workspaceSearchCheck,
                            modelCheckBoxes);
                    PopOver resultsPopOver = new SearchResultPane(runSearch(checkedItems, searchText,
                            SearchType.NORMAL), searchText, SearchType.NORMAL);

                    resultsPopOver.show(App.mainStage);
                }
                else if (regexBtn.isSelected()) {
                    this.hide();
                    String searchText = searchField.getText();
                    List<String> checkedItems = getCheckedItems(workspaceSearchCheck,
                            modelCheckBoxes);
                    PopOver resultsPopOver = new SearchResultPane(runSearch(checkedItems, searchText,
                            SearchType.REGEX), searchText, SearchType.REGEX);

                    resultsPopOver.show(App.mainStage);

                }



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
     * @return a list of checked items
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

    /**
     * Runs the search through the checked items and constructs the SearchResultCellNode to be added to the search
     * result pane
     * @param checkedItems items that are being searched through
     * @param searchText the text to be searched
     * @param searchType the type of the search
     * @return a list of SearchResultCellNode
     */
    public List<SearchResultCellNode> runSearch(List<String> checkedItems, String searchText, SearchType searchType) {

        List<SearchResultCellNode> results = new ArrayList<>();

        for (String item : checkedItems) {
            if (item.equals("Projects")) {
                for (Project proj : Global.currentWorkspace.getProjects()) {
                    TrackedTabPane scene =  new ProjectScene(proj);
                    Map<SearchableTab, Integer> searchResults = scene.advancedQuery(searchText, searchType);
                    for (SearchableTab tab : searchResults.keySet()) {
                        SearchResultCellNode searchResult = new SearchResultCellNode(proj, searchText, tab,
                                searchResults.get(tab), scene);
                        if (!(searchResult == null)) {
                            results.add(searchResult);
                        }
                    }
                }
            }
            else if (item.equals("Releases")) {
                for (Project proj : Global.currentWorkspace.getProjects()) {
                    for (Release release : proj.getReleases()) {
                        TrackedTabPane scene = new ReleaseScene(release);
                        Map<SearchableTab, Integer> searchResults = scene.advancedQuery(searchText, searchType);
                        for (SearchableTab tab : searchResults.keySet()) {
                            SearchResultCellNode searchResult = new SearchResultCellNode(release, searchText,
                                    tab, searchResults.get(tab), scene);
                            if (!(searchResult == null)) {
                                results.add(searchResult);
                            }
                        }
                    }
                }
            }
            else if (item.equals("Backlogs")) {
                for (Project proj : Global.currentWorkspace.getProjects()) {
                    for (Backlog backlog : proj.getBacklogs()) {
                        TrackedTabPane scene = new BacklogScene(backlog);
                        Map<SearchableTab, Integer> searchResults = scene.advancedQuery(searchText, searchType);
                        for (SearchableTab tab : searchResults.keySet()) {
                            SearchResultCellNode searchResult = new SearchResultCellNode(backlog, searchText,
                                    tab, searchResults.get(tab), scene);
                            if (!(searchResult == null)) {
                                results.add(searchResult);
                            }
                        }
                    }
                }
            }
            else if (item.equals("Stories")) {
                for (Project proj : Global.currentWorkspace.getProjects()) {
                    for (Backlog backlog : proj.getBacklogs()) {
                        for (Story story : backlog.getStories()) {
                            TrackedTabPane scene = new StoryScene(story);
                            Map<SearchableTab, Integer> searchResults = scene.advancedQuery(searchText, searchType);
                            for (SearchableTab tab : searchResults.keySet()) {
                                SearchResultCellNode searchResult = new SearchResultCellNode(story, searchText,
                                        tab, searchResults.get(tab), scene);
                                if (!(searchResult == null)) {
                                    results.add(searchResult);
                                }
                            }
                        }
                    }
                }
            }
            else if (item.equals("Sprints")) {
                for (Project proj : Global.currentWorkspace.getProjects()) {
                    for (Sprint sprint : proj.getSprints()) {
                        TrackedTabPane scene = new SprintScene(sprint);
                        Map<SearchableTab, Integer> searchResults = scene.advancedQuery(searchText, searchType);
                        for (SearchableTab tab : searchResults.keySet()) {
                            SearchResultCellNode searchResult = new SearchResultCellNode(sprint, searchText,
                                    tab, searchResults.get(tab), scene);
                            if (!(searchResult == null)) {
                                results.add(searchResult);
                            }
                        }
                    }
                }
            }
            else if (item.equals("Teams")) {
                for (Team team : Global.currentWorkspace.getTeams()) {
                    TrackedTabPane scene = new TeamScene(team);
                    Map<SearchableTab, Integer> searchResults = scene.advancedQuery(searchText, searchType);
                    for (SearchableTab tab : searchResults.keySet()) {
                        SearchResultCellNode searchResult = new SearchResultCellNode(team, searchText, tab,
                                searchResults.get(tab), scene);
                        if (!(searchResult == null)) {
                            results.add(searchResult);
                        }
                    }
                }
            }
            else if (item.equals("People")) {
                for (Person person : Global.currentWorkspace.getPeople()) {
                    TrackedTabPane scene = new PersonScene(person);
                    Map<SearchableTab, Integer> searchResults = scene.advancedQuery(searchText, searchType);
                    for (SearchableTab tab : searchResults.keySet()) {
                        SearchResultCellNode searchResult = new SearchResultCellNode(person, searchText,
                                tab, searchResults.get(tab), scene);
                        if (!(searchResult == null)) {
                            results.add(searchResult);
                        }
                    }
                }
            }
            else if (item.equals("Roles")) {
                for (Role role : Global.currentWorkspace.getRoles()) {
                    TrackedTabPane scene = new RoleScene(role);
                    Map<SearchableTab, Integer> searchResults = scene.advancedQuery(searchText, searchType);
                    for (SearchableTab tab : searchResults.keySet()) {
                        SearchResultCellNode searchResult = new SearchResultCellNode(role, searchText,
                                tab, searchResults.get(tab), scene);
                        if (!(searchResult == null)) {
                            results.add(searchResult);
                        }
                    }
                }
            }
            else if (item.equals("Skills")) {
                for (Skill skill : Global.currentWorkspace.getSkills()) {
                    TrackedTabPane scene = new SkillScene(skill);
                    Map<SearchableTab, Integer> searchResults = scene.advancedQuery(searchText, searchType);
                    for (SearchableTab tab : searchResults.keySet()) {
                        SearchResultCellNode searchResult = new SearchResultCellNode(skill, searchText,
                                tab, searchResults.get(tab), scene);
                        if (!(searchResult == null)) {
                            results.add(searchResult);
                        }
                    }
                }
            }
        }
        return results;
    }

}
