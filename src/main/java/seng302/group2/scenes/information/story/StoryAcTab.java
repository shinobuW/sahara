package seng302.group2.scenes.information.story;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.workspace.acceptanceCriteria.AcceptanceCriteria;
import seng302.group2.workspace.story.Story;


 //* Created by Shinobu on 30/05/2015.

public class StoryAcTab extends Tab
{
    public StoryAcTab(Story story)
    {
        this.setText("Acceptance Criteria");
        Pane acPane = new VBox(10);  // The pane that holds the basic info
        acPane.setBorder(null);
        acPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(acPane);
        this.setContent(wrapper);

        TableView<AcceptanceCriteria> acTable = new TableView();
        acTable.setEditable(true);
        acTable.fixedCellSizeProperty();
        acTable.setPrefWidth(700);
        acTable.setPrefHeight(400);
        acTable.setPlaceholder(new Label("This project has no Acceptance Criteria."));
        acTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList<AcceptanceCriteria> data = story.getAcceptanceCriteria();
        acPane.getChildren().addAll(acTable);
    }



}
