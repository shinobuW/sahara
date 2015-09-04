package seng302.group2.scenes;

import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import seng302.group2.Global;
import seng302.group2.scenes.control.search.SearchResultCellNode;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.workspace.allocation.Allocation;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.release.Release;
import seng302.group2.workspace.project.sprint.Sprint;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.acceptanceCriteria.AcceptanceCriteria;
import seng302.group2.workspace.project.story.tasks.Log;
import seng302.group2.workspace.project.story.tasks.Task;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * A window for displaying the results of a search.
 * Created by drm127 on 4/09/15.
 */
public class SearchResultPane extends BorderPane {

    private Pane resultPane = new Pane();

    public SearchResultPane(List<String> checkedItems, String searchText) {
        SearchableText searchResultTitle = new SearchableText("Search Results");
        List<SearchResultCellNode> results = runSearch(checkedItems, searchText);
        ListView<SearchResultCellNode> resultView = new ListView<>();
        resultView.setPrefHeight(490);
        resultView.setPrefWidth(490);
        for (SearchResultCellNode result : results) {
            resultView.getItems().add(result);
        }
        resultPane.getChildren().addAll(searchResultTitle, resultView);
        this.setCenter(resultPane);
    }

    public List<SearchResultCellNode> runSearch(List<String> checkedItems, String searchText) {

        List<SearchResultCellNode> results = new ArrayList<>();

        for (String item : checkedItems) {
            if (item.equals("Projects")) {
                for (Project proj : Global.currentWorkspace.getProjects()) {
                    SearchResultCellNode searchResult = proj.search(searchText);
                    if (!(searchResult == null)) {
                        results.add(searchResult);
                    }
                }
            }
            else if (item.equals("Releases")) {
                for (Project proj : Global.currentWorkspace.getProjects()) {
                    for (Release release : proj.getReleases()) {
                        SearchResultCellNode searchResult = release.search(searchText);
                        if (!(searchResult == null)) {
                            results.add(searchResult);
                        }
                    }
                }
            }
            else if (item.equals("Backlogs")) {
                for (Project proj : Global.currentWorkspace.getProjects()) {
                    for (Backlog backlog : proj.getBacklogs()) {
                        SearchResultCellNode searchResult = backlog.search(searchText);
                        if (!(searchResult == null)) {
                            results.add(searchResult);
                        }
                    }
                }
            }
            else if (item.equals("Stories")) {
                for (Project proj : Global.currentWorkspace.getProjects()) {
                    for (Story story : proj.getAllStories()) {
                        SearchResultCellNode searchResult = story.search(searchText);
                        if (!(searchResult == null)) {
                            results.add(searchResult);
                        }
                    }
                }
            }
            else if (item.equals("Tasks")) {
                for (Project proj : Global.currentWorkspace.getProjects()) {
                    for (Story story : proj.getAllStories()) {
                        for (Task task : story.getTasks()) {
                            SearchResultCellNode searchResult = task.search(searchText);
                            if (!(searchResult == null)) {
                                results.add(searchResult);
                            }
                        }
                    }
                }
            }
            else if (item.equals("Sprints")) {
                for (Project proj : Global.currentWorkspace.getProjects()) {
                    for (Sprint sprint : proj.getSprints()) {
                        SearchResultCellNode searchResult = sprint.search(searchText);
                        if (!(searchResult == null)) {
                            results.add(searchResult);
                        }
                    }
                }
            }
            else if (item.equals("Teams")) {
                for (Team team : Global.currentWorkspace.getTeams()) {
                    SearchResultCellNode searchResult = team.search(searchText);
                    if (!(searchResult == null)) {
                        results.add(searchResult);
                    }
                }
            }
            else if (item.equals("People")) {
                for (Person person : Global.currentWorkspace.getPeople()) {
                    SearchResultCellNode searchResult = person.search(searchText);
                    if (!(searchResult == null)) {
                        results.add(searchResult);
                    }
                }
            }
            else if (item.equals("Roles")) {
                for (Role role : Global.currentWorkspace.getRoles()) {
                    SearchResultCellNode searchResult = role.search(searchText);
                    if (!(searchResult == null)) {
                        results.add(searchResult);
                    }
                }
            }
            else if (item.equals("Skills")) {
                for (Skill skill : Global.currentWorkspace.getSkills()) {
                    SearchResultCellNode searchResult = skill.search(searchText);
                    if (!(searchResult == null)) {
                        results.add(searchResult);
                    }
                }
            }
            else if (item.equals("Allocations")) {
                for (Project proj : Global.currentWorkspace.getProjects()) {
                    for (Allocation allocation : proj.getTeamAllocations()) {
                        SearchResultCellNode searchResult = allocation.search(searchText);
                        if (!(searchResult == null)) {
                            results.add(searchResult);
                        }
                    }
                }
            }
            else if (item.equals("Acceptance Criteria")) {
                for (Project proj : Global.currentWorkspace.getProjects()) {
                    for (Story story : proj.getAllStories()) {
                        for (AcceptanceCriteria ac : story.getAcceptanceCriteria()) {
                            SearchResultCellNode searchResult = ac.search(searchText);
                            if (!(searchResult == null)) {
                                results.add(searchResult);
                            }
                        }
                    }
                }
            }
            else if (item.equals("Logs")) {
                for (Project proj : Global.currentWorkspace.getProjects()) {
                    for (Story story : proj.getAllStories()) {
                        for (Task task : story.getTasks()) {
                            for (Log log : task.getLogs()) {
                                SearchResultCellNode searchResult = log.search(searchText);
                                if (!(searchResult == null)) {
                                    results.add(searchResult);
                                }
                            }
                        }
                    }
                }
            }
        }
        return results;
    }
}
