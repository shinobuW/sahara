package seng302.group2.scenes.information.story;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.workspace.acceptanceCriteria.AcceptanceCriteria;
import seng302.group2.workspace.story.Story;


//* Created by Shinobu on 30/05/2015.

public class StoryAcTab extends Tab {
    public StoryAcTab(Story story) {
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

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.BOTTOM_RIGHT);
        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        buttons.getChildren().addAll(addButton, deleteButton);

        Label title = new TitleLabel("Acceptance Criteria");

        TableColumn descriptionCol = new TableColumn("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<AcceptanceCriteria, String>("Text"));

        TableColumn stateCol = new TableColumn("State");
        stateCol.setCellValueFactory(new PropertyValueFactory<AcceptanceCriteria, String>("State"));

        acTable.setItems(data);
        TableColumn[] columns = {descriptionCol, stateCol};
        acTable.getColumns().setAll(columns);

        CustomTextArea descriptionTextArea = new CustomTextArea("Description:");

        addButton.setOnAction((event) -> {
                if (descriptionTextArea.getText() != null) {
                    String description = descriptionTextArea.getText();
                    AcceptanceCriteria newAc = new AcceptanceCriteria(description, story);
                    story.add(newAc);
                }
                else {
                    event.consume();
                }
            });

        deleteButton.setOnAction((event) -> {
                AcceptanceCriteria selectedAc = acTable.getSelectionModel().getSelectedItem();
                if (selectedAc != null) {
                    Action response = Dialogs.create()
                            .title("Delete Acceptance Criteria")
                            .message("Do you really want to delete this Acceptance Criteria?")
                            .showConfirm();

                    if (response == org.controlsfx.dialog.Dialog.ACTION_YES) {
                        selectedAc.delete();
                    }
                    else if (response == org.controlsfx.dialog.Dialog.ACTION_NO) {
                        event.consume();
                    }
                }
            });

        acPane.getChildren().addAll(acTable, descriptionTextArea, buttons);
    }

}
