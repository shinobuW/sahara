package seng302.group2.scenes.information.project.backlog;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import seng302.group2.App;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.story.Story;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * The information tab for a backlog
 * Created by cvs20 on 19/05/15.
 */
public class BacklogInfoTab extends Tab {
    public BacklogInfoTab(Backlog currentBacklog) {
        final Stage stage;
        this.setText("Basic Information");
        Pane basicInfoPane = new VBox(10);

        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        Label title = new TitleLabel(currentBacklog.getLongName());

        Button btnEdit = new Button("Edit");
        Button btnView = new Button("View");
        Button btnHighlight = new Button("Highlight");

        HBox buttonHBox = new HBox();
        buttonHBox.spacingProperty().setValue(10);
        buttonHBox.alignmentProperty().set(Pos.TOP_LEFT);
        buttonHBox.getChildren().addAll(btnView, btnHighlight);

        TableView<Story> storyTable = new TableView<>();
        storyTable.setEditable(true);
        storyTable.setPrefWidth(500);
        storyTable.setPrefHeight(200);
        storyTable.setPlaceholder(new Label("There are currently no stories in this backlog."));
        storyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Story> data = observableArrayList();
        data.addAll(currentBacklog.getStories());

        TableColumn storyCol = new TableColumn("Story");
        storyCol.setCellValueFactory(new PropertyValueFactory<Story, String>("shortName"));
        storyCol.prefWidthProperty().bind(storyTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        TableColumn priorityCol = new TableColumn("Priority");
        priorityCol.setCellValueFactory(new PropertyValueFactory<Story, Integer>("priority"));
        priorityCol.prefWidthProperty().bind(storyTable.widthProperty()
                .subtract(2).divide(100).multiply(20));

        TableColumn readyCol = new TableColumn("Ready");
        readyCol.setCellValueFactory(new PropertyValueFactory<Story, Boolean>("ready"));
        readyCol.prefWidthProperty().bind(storyTable.widthProperty()
                .subtract(2).divide(100).multiply(20));

        storyTable.setItems(data);
        storyTable.getColumns().addAll(priorityCol, storyCol, readyCol);

        basicInfoPane.getChildren().add(title);
        basicInfoPane.getChildren().add(new Label("Short Name: "
                + currentBacklog.getShortName()));
        basicInfoPane.getChildren().add(new Label("Backlog Description: "
                + currentBacklog.getDescription()));
        basicInfoPane.getChildren().add(new Label("Project: "
                + currentBacklog.getProject().toString()));


        if (currentBacklog.getProductOwner() == null) {
            basicInfoPane.getChildren().add(new Label("Backlog Product Owner: "
                    + ""));
        }
        else {
            basicInfoPane.getChildren().add(new Label("Backlog Product Owner: "
                    + currentBacklog.getProductOwner()));
        }

        basicInfoPane.getChildren().add(new Label("Estimation Scale: "
                + currentBacklog.getScale()));

        basicInfoPane.getChildren().add(new Separator());

        basicInfoPane.getChildren().add(new Label("Stories: "));
        basicInfoPane.getChildren().add(storyTable);

        basicInfoPane.getChildren().add(buttonHBox);
        basicInfoPane.getChildren().add(btnEdit);

        btnEdit.setOnAction((event) -> {
                currentBacklog.switchToInfoScene(true);
            });

        btnView.setOnAction((event) -> {
                if (storyTable.getSelectionModel().getSelectedItem() != null) {
                    App.mainPane.selectItem(storyTable.getSelectionModel().getSelectedItem());
                }
            });


        btnHighlight.setOnAction((event) -> {
                storyCol.setCellFactory(new Callback<TableColumn<Story, String>, TableCell<Story, String>>() {
                    @Override
                    public TableCell<Story, String> call(TableColumn<Story, String> param) {
                        TableCell<Story, String> cell = new TableCell<Story, String>() {
                            public void updateItem(String string, boolean empty) {
                                if (string != null) {
                                    setStyle("-fx-background-color: green");
                                    setText(string);
                                }
                            }
                        };
                        return cell;
                    }
                });

                priorityCol.setCellFactory(new Callback<TableColumn<Story, Integer>, TableCell<Story, Integer>>() {
                    @Override
                    public TableCell<Story, Integer> call(TableColumn<Story, Integer> param) {
                        TableCell<Story, Integer> cell = new TableCell<Story, Integer>() {
                            public void updateItem(Integer item, boolean empty) {
                                if (item != null) {
                                    setStyle("-fx-background-color: green");
                                    setText(item.toString());
                                }
                            }
                        };
                        return cell;
                    }
                });

                readyCol.setCellFactory(new Callback<TableColumn<Story, Boolean>, TableCell<Story, Boolean>>() {
                    @Override
                    public TableCell<Story, Boolean> call(TableColumn<Story, Boolean> param) {
                        TableCell<Story, Boolean> cell = new TableCell<Story, Boolean>() {
                            public void updateItem(Boolean item, boolean empty) {
                                if (item != null) {
                                    if (item == false) {
                                        setStyle("-fx-background-color: red");
                                    }
                                    if (item == true) {
                                        setStyle("-fx-background-color: green");
                                    }
                                    setText(item.toString());
                                }
                                /*else {
                                    setStyle("-fx-background-color: red");
                                    setText(item.toString());
                                }
                                */
                            }
                        };
                        return cell;
                    }
                });
            });
    }
}
