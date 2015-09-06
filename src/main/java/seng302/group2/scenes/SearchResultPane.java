package seng302.group2.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchResultCellNode;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.dialog.CreateSearchPopOver;
import seng302.group2.scenes.information.person.PersonScene;
import seng302.group2.scenes.information.project.ProjectScene;
import seng302.group2.scenes.information.project.backlog.BacklogScene;
import seng302.group2.scenes.information.project.release.ReleaseScene;
import seng302.group2.scenes.information.project.sprint.SprintScene;
import seng302.group2.scenes.information.project.story.StoryScene;
import seng302.group2.scenes.information.role.RoleScene;
import seng302.group2.scenes.information.skill.SkillScene;
import seng302.group2.scenes.information.team.TeamScene;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.release.Release;
import seng302.group2.workspace.project.sprint.Sprint;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A window for displaying the results of a search.
 * Created by drm127 on 4/09/15.
 */
public class SearchResultPane extends PopOver {


    /**
     * Constructor
     * @param checkedItems List of items that are ticked in the advanced search window
     * @param searchText The text being searched for
     */
    public SearchResultPane(List<String> checkedItems, String searchText) {
        Global.advancedSearchExists = true;
        this.setDetachedTitle("Search Results");
        this.setMinWidth(500);
        this.setMinHeight(500);
        VBox content = new VBox();
        List<SearchResultCellNode> results = runSearch(checkedItems, searchText);
        ListView<SearchResultCellNode> resultView = new ListView<>();
        resultView.setPrefHeight(490);
        resultView.setPrefWidth(490);
        for (SearchResultCellNode result : results) {
            resultView.getItems().add(result);
        }

        resultView.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    SaharaItem selectedItem = (SaharaItem) resultView.getSelectionModel().getSelectedItem().getItem();
                    App.mainPane.selectItem(selectedItem);
                    Tab selectedTab = (SearchableTab) resultView.getSelectionModel().getSelectedItem().getTab();
                    ((TrackedTabPane) resultView.getSelectionModel().getSelectedItem().getSearchableScene())
                            .select(selectedTab);
                    selectedItem.switchToInfoScene();
                    MainPane.getToolBar().search(searchText);
                }
                event.consume();
            });

        HBox buttons = new HBox();
        Button btnClose = new Button("Close");
        Button btnBack = new Button("Back");
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.spacingProperty().setValue(10);
        Insets insets = new Insets(10, 20, 10, 20);
        buttons.setPadding(insets);
        buttons.getChildren().addAll(btnBack, btnClose);

        content.getChildren().addAll(resultView, buttons);

        btnClose.setOnAction(event -> {
                this.hide();
                Global.advancedSearchExists = false;
            });

        btnBack.setOnAction(event -> {
                this.hide();
                PopOver searchPopOver = new CreateSearchPopOver();
                if (!Global.advancedSearchExists) {
                    Global.advancedSearchExists = true;
                    searchPopOver.show(App.mainStage);
                }
            });

        this.setOnHiding(event -> {
                MainPane.getToolBar().search("");
                Global.advancedSearchExists = false;

            });

        this.setContentNode(content);
        this.setDetached(true);

    }

    /**
     * Runs the search through the checked items and constructs the SearchResultCellNode to be added to the search
     * result pane
     * @param checkedItems items that are being searched through
     * @param searchText the text to be searched
     * @return a list of SearchResultCellNode
     */
    public List<SearchResultCellNode> runSearch(List<String> checkedItems, String searchText) {

        List<SearchResultCellNode> results = new ArrayList<>();

        for (String item : checkedItems) {
            if (item.equals("Projects")) {
                for (Project proj : Global.currentWorkspace.getProjects()) {
                    TrackedTabPane scene =  new ProjectScene(proj);
                    Map<SearchableTab, Integer> searchResults = scene.advancedQuery(searchText);
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
                        Map<SearchableTab, Integer> searchResults = scene.advancedQuery(searchText);
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
                        Map<SearchableTab, Integer> searchResults = scene.advancedQuery(searchText);
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
                            Map<SearchableTab, Integer> searchResults = scene.advancedQuery(searchText);
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
                        Map<SearchableTab, Integer> searchResults = scene.advancedQuery(searchText);
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
                    Map<SearchableTab, Integer> searchResults = scene.advancedQuery(searchText);
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
                    Map<SearchableTab, Integer> searchResults = scene.advancedQuery(searchText);
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
                    Map<SearchableTab, Integer> searchResults = scene.advancedQuery(searchText);
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
                    Map<SearchableTab, Integer> searchResults = scene.advancedQuery(searchText);
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
