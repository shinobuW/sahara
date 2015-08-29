package seng302.group2.scenes.control;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableRow;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;
import seng302.group2.scenes.control.search.*;
import seng302.group2.scenes.information.project.story.task.LoggingEffortPane;
import seng302.group2.workspace.project.story.tasks.Task;

import java.util.Collection;

/**
 * Created by crw73 on 26/08/15.
 */
public class PopOverTable<T> extends SearchableTable<T> {

    /**
     * Highlights a row if a matching query is found within that row. If there is no matching query,
     * the row's style is set to null (default).
     */
    private void updateRows() {
        setRowFactory(tv -> new TableRow<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                
                super.updateItem(item, empty);
                HBox content = new HBox();
                PopOver taskPopover = new PopOver();

                VBox taskContent = new VBox();
                taskContent.setPadding(new Insets(8, 8, 8, 8));
                if (item != null) {

                    Task currentTask = (Task) item;

                    taskPopover.setDetachedTitle(currentTask.toString());
                    VBox taskInfo = new VBox();


                    taskInfo.setBorder(null);
                    taskInfo.setPadding(new Insets(25, 25, 25, 25));

                    SearchableText title = new SearchableTitle(currentTask.getShortName());
                    SearchableText description = new SearchableText("Task Description: "
                            + currentTask.getDescription());
                    SearchableText impediments = new SearchableText("Impediments: " + currentTask.getImpediments());
                    SearchableText effortLeft = new SearchableText("Effort Left: "
                            + currentTask.getEffortLeftString());
                    SearchableText effortSpent = new SearchableText("Effort Spent: "
                            + currentTask.getEffortSpentString());
                    SearchableText taskState = new SearchableText("Task State: " + currentTask.getState());
                    SearchableText assignedPerson;
                    if (currentTask.getAssignee() == null) {
                        assignedPerson = new SearchableText("Assigned Person: ");
                    }
                    else {
                        assignedPerson = new SearchableText("Assigned Person: " + currentTask.getAssignee());
                    }

                    taskInfo.getChildren().addAll(
                            title,
                            description,
                            impediments,
                            effortLeft,
                            effortSpent,
                            taskState,
                            assignedPerson
                    );
                    taskInfo.setStyle(null);


                    ScrollPane taskWrapper = new ScrollPane();
                    LoggingEffortPane loggingPane = new LoggingEffortPane((Task) item,
                            taskPopover);
                    loggingPane.setStyle(null);

                    taskWrapper.setContent(loggingPane);

                    TitledPane collapsableInfoPane = new TitledPane("Task Info", taskInfo);
                    collapsableInfoPane.setPrefHeight(30);
                    collapsableInfoPane.setExpanded(true);
                    collapsableInfoPane.setAnimated(true);

                    TitledPane collapsableLoggingPane = new TitledPane("Task Logging", taskWrapper);
                    collapsableLoggingPane.setExpanded(true);
                    collapsableLoggingPane.setAnimated(true);


                    taskContent.getChildren().addAll(collapsableInfoPane, collapsableLoggingPane);
                    taskInfo.setStyle(" -fx-background: -fx-control-inner-background ;\n"
                            + "  -fx-background-color: -fx-table-cell-border-color, -fx-background ;\n");
                    taskWrapper.setStyle(" -fx-background: -fx-control-inner-background ;\n"
                            + "  -fx-background-color: -fx-table-cell-border-color, -fx-background ;\n" );

                    taskPopover.setContentNode(taskContent);
                    if (matchingItems.contains(item)) {
                        setStyle("-fx-background-color: " + SearchableControl.highlightColourString + "; ");
                    }
                    else {
                        setStyle(null);
                        //setStyle("-fx-background-color: " + Color.TRANSPARENT + ";");
                    }
                    this.setOnMouseClicked(event -> {

                            System.out.println("Is clicked");
                            if (taskPopover.isShowing()) {
                                taskPopover.hide();
                            }
                            else {
                                if (event.getClickCount() == 2) {
                                    taskPopover.show(this);
                                }
                            }
                            event.consume();
                        });
                }

                this.getChildren().add(content);
            }
        });
    }
}
