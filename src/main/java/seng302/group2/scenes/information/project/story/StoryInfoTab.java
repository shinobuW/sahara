package seng302.group2.scenes.information.project.story;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.workspace.project.story.Story;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * The story information tab.
 * Created by drm127 on 17/05/15.
 */
public class StoryInfoTab extends Tab {
    public StoryInfoTab(Story currentStory) {

        this.setText("Basic Information");

        Pane basicInfoPane = new VBox(10);

        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        Label title = new TitleLabel(currentStory.getShortName());

        Button btnEdit = new Button("Edit");

        TableView<Story> dependenciesTable = new TableView<>();
        dependenciesTable.setEditable(true);
        dependenciesTable.setPrefWidth(500);
        dependenciesTable.setPrefHeight(200);
        dependenciesTable.setPlaceholder(new Label("There are currently no stories dependant on this story."));
        dependenciesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Story> dataDependencies = observableArrayList();
        dataDependencies.addAll(currentStory.getDependencies());

        TableColumn storyCol = new TableColumn("Dependants");
        storyCol.setCellValueFactory(new PropertyValueFactory<Story, String>("shortName"));
        storyCol.prefWidthProperty().bind(dependenciesTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        dependenciesTable.setItems(dataDependencies);
        dependenciesTable.getColumns().addAll(storyCol);

        TableView<Story> dependantsTable = new TableView<>();
        dependantsTable.setEditable(true);
        dependantsTable.setPrefWidth(500);
        dependantsTable.setPrefHeight(200);
        dependantsTable.setPlaceholder(new Label("This story is not currently dependant on another"));
        dependantsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Story> dataDependants = observableArrayList();
        dataDependants.addAll(currentStory.getDepedants());

        TableColumn storyCol2 = new TableColumn("Depends On");
        storyCol2.setCellValueFactory(new PropertyValueFactory<Story, String>("shortName"));
        storyCol2.prefWidthProperty().bind(dependantsTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        dependantsTable.setItems(dataDependants);
        dependantsTable.getColumns().addAll(storyCol2);

        basicInfoPane.getChildren().add(title);
        basicInfoPane.getChildren().add(new Label("Story Description: "
                + currentStory.getDescription()));
        basicInfoPane.getChildren().add(new Label("Project: "
                + currentStory.getProject().toString()));
        basicInfoPane.getChildren().add(new Label("Priority: "
                + currentStory.getPriority()));
        basicInfoPane.getChildren().add(new Label("Estimate: "
                + currentStory.getEstimate()));
        basicInfoPane.getChildren().add(new Label("State: "
                + currentStory.getReadyState()));
        basicInfoPane.getChildren().add(new Label("Story Creator: "
                + currentStory.getCreator()));
        basicInfoPane.getChildren().add(dependantsTable);
        basicInfoPane.getChildren().add(dependenciesTable);
        basicInfoPane.getChildren().add(btnEdit);

        btnEdit.setOnAction((event) -> {
                currentStory.switchToInfoScene(true);
            });
    }

}
