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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomInfoLabel;
import seng302.group2.scenes.control.search.SearchType;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableText;
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
        SearchableText shortNameField = new SearchableText(release.getShortName());
        releaseNode.getChildren().addAll(
                shortNameField
        );
        
        for (Project proj : Global.currentWorkspace.getProjects()) {
            for (Sprint sprint : proj.getSprints()) {
                if (sprint.getRelease().equals(release)) {
                    releaseNode.getChildren().add(createSprintNode(sprint));
                }
            }
        }

        // Add items to pane & search collection
        Collections.addAll(searchControls,
                shortNameField  
        );
        
        return releaseNode;
    }
    
    private Node createSprintNode(Sprint sprint) {
        VBox sprintNode = new VBox();
        SearchableText shortNameField = new SearchableText(sprint.getGoal());
        
        sprintNode.getChildren().addAll(
                shortNameField
        );
        
        List<Node> releaseNodeList = new ArrayList<>();
        for (Story story : sprint.getStories()) {
            sprintNode.getChildren().add(createStoryNode(story));
        }

        // Add items to pane & search collection
        Collections.addAll(searchControls,
                shortNameField  
        );
        
        return sprintNode;
    }
    
    private Node createStoryNode(Story story) {
        VBox storyNode = new VBox();
        SearchableText shortNameField = new SearchableText(story.getShortName());
        
        
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
