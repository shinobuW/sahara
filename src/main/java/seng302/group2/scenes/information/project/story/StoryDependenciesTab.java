package seng302.group2.scenes.information.project.story;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.workspace.project.story.Story;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by cvs20 on 13/07/15.
 */
public class StoryDependenciesTab extends Tab {
    
    /**
     * Constructor for the Story Dependencies Tab.
     * 
     * @param currentStory The currently selected story
     */
    public StoryDependenciesTab(Story currentStory) {
        this.setText("Dependencies");
        Pane dependenciesPane = new VBox(10);  // The pane that holds the basic info
        dependenciesPane.setBorder(null);
        dependenciesPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(dependenciesPane);
        this.setContent(wrapper);

        TableView<Story> dependantsTable = new TableView<>();
        dependantsTable.setEditable(true);
        dependantsTable.setPrefWidth(500);
        dependantsTable.setPrefHeight(200);
        dependantsTable.setPlaceholder(new Label("There are currently no stories dependant on this story."));
        dependantsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Story> dataDependencies = observableArrayList();
        dataDependencies.addAll(currentStory.getDependentOnThis());

        TableColumn storyCol = new TableColumn("Dependant On This");
        storyCol.setCellValueFactory(new PropertyValueFactory<Story, String>("shortName"));
        storyCol.prefWidthProperty().bind(dependantsTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        TableColumn priorityCol = new TableColumn("Priority");
        priorityCol.setCellValueFactory(new PropertyValueFactory<Story, Integer>("priority"));
        priorityCol.prefWidthProperty().bind(dependantsTable.widthProperty()
                .subtract(2).divide(100).multiply(20));

        dependantsTable.setItems(dataDependencies);
        dependantsTable.getColumns().addAll(storyCol, priorityCol);

        TableView<Story> dependsTable = new TableView<>();
        dependsTable.setEditable(true);
        dependsTable.setPrefWidth(500);
        dependsTable.setPrefHeight(200);
        dependsTable.setPlaceholder(new Label("This story is not currently dependant on another"));
        dependsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Story> dataDependants = observableArrayList();
        dataDependants.addAll(currentStory.getDependentOn());

        TableColumn storyCol2 = new TableColumn("Dependent On");
        storyCol2.setCellValueFactory(new PropertyValueFactory<Story, String>("shortName"));
        storyCol2.prefWidthProperty().bind(dependsTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        TableColumn priorityCol2 = new TableColumn("Priority");
        priorityCol2.setCellValueFactory(new PropertyValueFactory<Story, Integer>("priority"));
        priorityCol2.prefWidthProperty().bind(dependsTable.widthProperty()
                .subtract(2).divide(100).multiply(20));

        dependsTable.setItems(dataDependants);
        dependsTable.getColumns().addAll(storyCol2, priorityCol2);

        Button btnView = new Button("View");

        btnView.setOnAction((event) -> {
                if (dependsTable.getSelectionModel().getSelectedItem() != null) {
                    App.mainPane.selectItem(dependsTable.getSelectionModel().getSelectedItem());
                }
                if (dependantsTable.getSelectionModel().getSelectedItem() != null) {
                    App.mainPane.selectItem(dependantsTable.getSelectionModel().getSelectedItem());
                }
            });

        dependenciesPane.getChildren().add(dependsTable);
        dependenciesPane.getChildren().add(dependantsTable);
        dependenciesPane.getChildren().add(btnView);
    }
}