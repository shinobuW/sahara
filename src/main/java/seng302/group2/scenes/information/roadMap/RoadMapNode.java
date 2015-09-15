/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information.roadMap;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.PopOverTip;
import seng302.group2.scenes.control.search.SearchType;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.release.Release;
import seng302.group2.workspace.project.sprint.Sprint;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.roadMap.RoadMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author crw73
 */
public class RoadMapNode extends VBox implements SearchableControl {
    
    List<SearchableControl> searchControls = new ArrayList<>();

    
    public RoadMapNode(RoadMap currentRoadMap) {
        HBox roadMapContent = new HBox();
        Insets insetsContent = new Insets(5, 15, 5, 5);
        roadMapContent.setPadding(insetsContent);
        roadMapContent.setStyle("-fx-background-color: rgba(255, 116, 10, 0.62); -fx-border-radius: 5 5 5 5; "
                + "-fx-background-radius: 0 5 5 5");
        HBox roadMapChildren = new HBox();
        
        SearchableText shortNameField = new SearchableText(currentRoadMap.getShortName());
        
        roadMapContent.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !(((SaharaItem) Global.selectedTreeItem.getValue())
                        .equals(currentRoadMap))) {
                    App.mainPane.selectItem(currentRoadMap);
//                    currentRoadMap.switchToInfoScene();
                }
                event.consume();
            });

        roadMapContent.getChildren().addAll(
                shortNameField
        );
        
        for (Release release : currentRoadMap.getReleases()) {
            roadMapChildren.getChildren().add(createReleaseNode(release));
        }

        this.getChildren().addAll(
                roadMapContent,
                roadMapChildren
        );
        // Add items to pane & search collection
        Collections.addAll(searchControls,
                shortNameField  
        );
    }
    
    private Node createReleaseNode(Release release) {
        VBox releaseNode = new VBox();
        Insets insetsNode = new Insets(5, 5, 5, 0);
        releaseNode.setPadding(insetsNode);
        HBox releaseContent = new HBox();
        Insets insetsContent = new Insets(5, 15, 5, 5);
        releaseContent.setPadding(insetsContent);
        releaseContent.setStyle("-fx-background-color: rgba(11, 0, 255, 0.62); -fx-border-radius: 5 5 5 5; "
                + "-fx-background-radius: 0 5 5 5");

        HBox releaseChildren = new HBox();

        SearchableText shortNameField = new SearchableText(release.getShortName());
        SearchableText releaseDate = new SearchableText("   " + release.getDateString());

        releaseContent.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    App.mainPane.selectItem(release);
                    release.switchToInfoScene();
                }
                event.consume();
            });

        VBox releaseVBox = new VBox();
        Text releaseEndText = new Text("End date of " + release.getShortName() + " is " + release.getDateString());
        releaseVBox.getChildren().addAll(releaseEndText);
        PopOverTip releaseTip = new PopOverTip(releaseContent, releaseVBox);
        releaseTip.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);

        for (Project proj : Global.currentWorkspace.getProjects()) {
            for (Sprint sprint : proj.getSprints()) {
                if (sprint.getRelease().equals(release)) {
                    releaseChildren.getChildren().add(createSprintNode(sprint));
                }
            }
        }
        
        releaseContent.getChildren().addAll(shortNameField, releaseDate);
        releaseContent.setAlignment(Pos.CENTER);
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
        Insets insets = new Insets(5, 5, 5, 0);
        sprintNode.setPadding(insets);
        HBox sprintContent = new HBox();
        Insets insetsContent = new Insets(5, 15, 5, 5);
        sprintContent.setPadding(insetsContent);
        sprintContent.setStyle("-fx-background-color: rgba(100, 255, 124, 0.83); -fx-border-radius: 5 5 5 5; "
                + "-fx-background-radius: 0 5 5 5");

        GridPane sprintChildren = new GridPane();
        int xCounter = 0;
        int yCounter = 0;
        
        SearchableText shortNameField = new SearchableText(sprint.getGoal());

        VBox sprintVBox = new VBox();
        Text sprintStartText = new Text("Start date of " + sprint.getGoal() + " is " + sprint.getStartDateString());
        Text sprintEndText = new Text("End date of " + sprint.getGoal() + " is " + sprint.getEndDateString());
        sprintVBox.getChildren().addAll(sprintStartText, sprintEndText);
        PopOverTip sprintTip = new PopOverTip(sprintContent, sprintVBox);
        sprintTip.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);


        sprintContent.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    App.mainPane.selectItem(sprint);
                    sprint.switchToInfoScene();
                }
                event.consume();
            });
        
        sprintNode.getChildren().addAll(
                shortNameField
        );
        
        for (Story story : sprint.getStories()) {
            sprintChildren.add(createStoryNode(story), xCounter, yCounter);
            xCounter++;
            if (xCounter == 3) {
                yCounter++;
                xCounter = 0;
            }
        }

        sprintContent.getChildren().addAll(shortNameField);
        sprintContent.setAlignment(Pos.CENTER);
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
        storyNode.setAlignment(Pos.CENTER);
        Insets insetsNode = new Insets(5, 5, 5, 0);
        storyNode.setPadding(insetsNode);
        VBox storyContent = new VBox();
        Insets insetsContent = new Insets(5, 15, 5, 5);
        storyContent.setPadding(insetsContent);
        storyContent.setStyle("-fx-background-color: rgba(255, 7, 0, 0.56); -fx-border-radius: 5 5 5 5; "
                + "-fx-background-radius: 0 5 5 5");
        SearchableText shortNameField = new SearchableText(story.getShortName());
        
        storyNode.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    App.mainPane.selectItem(story);
                    story.switchToInfoScene();
                }
                event.consume();
            });


        VBox storyVBox = new VBox();
        Text storyPoint = new Text(story.getShortName() + "'s estimate is " + story.getEstimate());
        storyVBox.getChildren().addAll(storyPoint);
        PopOverTip storyTip = new PopOverTip(storyNode, storyVBox);
        storyTip.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);


        storyContent.getChildren().addAll(
                shortNameField
        );

        storyNode.getChildren().addAll(
                storyContent
        );

        // Add items to pane & search collection
        Collections.addAll(searchControls,
                shortNameField  
        );
        
        return storyNode;
    }
    
    public List<SearchableControl> getSearchableControls() {
        return searchControls;
    }

    @Override
    public boolean query(String query) {
        boolean found = false;
        for (SearchableControl control : getSearchableControls()) {
            if (control.query(query)) {
                found = true;
            }
        }
        return found;
    }

    @Override
    public int advancedQuery(String query, SearchType searchType) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
