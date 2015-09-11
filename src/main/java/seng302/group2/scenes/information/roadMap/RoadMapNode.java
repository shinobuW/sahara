/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information.roadMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.spreadsheet.Grid;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.MainPane;
import seng302.group2.scenes.control.CustomInfoLabel;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchType;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.release.Release;
import seng302.group2.workspace.project.sprint.Sprint;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.roadMap.RoadMap;

/**
 *
 * @author crw73
 */
public class RoadMapNode extends HBox implements SearchableControl {
    
    List<SearchableControl> searchControls = new ArrayList<>();

    
    public RoadMapNode(RoadMap currentRoadMap) {
        
        SearchableText shortNameField = new SearchableText(currentRoadMap.getShortName());

        this.getChildren().addAll(
                shortNameField
        );
        
        for (Release release : currentRoadMap.getReleases()) {
            this.getChildren().add(createReleaseNode(release));
        }

        // Add items to pane & search collection
        Collections.addAll(searchControls,
                shortNameField  
        );
    }
    
    private Node createReleaseNode(Release release) {
        VBox releaseNode = new VBox();
        HBox releaseContent = new HBox();
        HBox releaseChildren = new HBox();
        
        SearchableText shortNameField = new SearchableText(release.getShortName());
        
        shortNameField.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    App.mainPane.selectItem(release);
                    release.switchToInfoScene();
                }
                event.consume();
            });
        
        
        
        for (Project proj : Global.currentWorkspace.getProjects()) {
            for (Sprint sprint : proj.getSprints()) {
                if (sprint.getRelease().equals(release)) {
                    releaseChildren.getChildren().add(createSprintNode(sprint));
                }
            }
        }
        
        releaseContent.getChildren().add(shortNameField);
        releaseNode.getChildren().addAll(
                releaseContent,
                releaseChildren
        );

        // Add items to pane & search collection
        Collections.addAll(searchControls,
                shortNameField  
        );
        
        return releaseNode;
    }
    
    private Node createSprintNode(Sprint sprint) {
        VBox sprintNode = new VBox();
        HBox sprintContent = new HBox();
        GridPane sprintChildren = new GridPane();
        int xCounter = 0;
        int yCounter = 0;
        
        SearchableText shortNameField = new SearchableText(sprint.getGoal());
        
        shortNameField.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    App.mainPane.selectItem(sprint);
                    sprint.switchToInfoScene();
                }
                event.consume();
            });
        
        sprintNode.getChildren().addAll(
                shortNameField
        );
        
        List<Node> releaseNodeList = new ArrayList<>();
        for (Story story : sprint.getStories()) {
            sprintChildren.add(createStoryNode(story), xCounter, yCounter);
            xCounter++;
            if (xCounter == 3) {
                yCounter++;
                xCounter = 0;
            }
        }

        sprintContent.getChildren().addAll(shortNameField);
        sprintNode.getChildren().addAll(
                sprintContent,
                sprintChildren
        );
        // Add items to pane & search collection
        Collections.addAll(searchControls,
                shortNameField
        );
        
        return sprintNode;
    }
    
    private Node createStoryNode(Story story) {
        VBox storyNode = new VBox();
        SearchableText shortNameField = new SearchableText(story.getShortName());
        
        shortNameField.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    App.mainPane.selectItem(story);
                    story.switchToInfoScene();
                }
                event.consume();
            });
        
        
        storyNode.getChildren().addAll(
                shortNameField
        );

        // Add items to pane & search collection
        Collections.addAll(searchControls,
                shortNameField  
        );
        
        return storyNode;
    }

    @Override
    public boolean query(String query) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int advancedQuery(String query, SearchType searchType) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
