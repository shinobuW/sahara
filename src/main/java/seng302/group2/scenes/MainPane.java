package seng302.group2.scenes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.information.workspace.WorkspaceScene;
import seng302.group2.scenes.listdisplay.TreeViewWithItems;
import seng302.group2.scenes.menu.MainMenuBar;
import seng302.group2.workspace.HierarchyData;
import seng302.group2.workspace.SaharaItem;

import static seng302.group2.App.content;


/**
 * A class for holding JavaFX scenes used in the workspace
 *
 * @author Jordane Lew (jml168)
 */
public class MainPane extends BorderPane {
    private TreeViewWithItems treeView;
    private Pane informationPane = new Pane();
    private ScrollPane contentPane;
    private boolean menuHidden = false;
    private double[] dividerPositions;

    public MainPane() {
        this.init();

        if (contentPane == null) {
            contentPane = new ScrollPane();
        }

        content.getItems().clear();
        if (!menuHidden) {
            content.getItems().add(treeView);
        }
        content.getItems().add(contentPane);

        this.setCenter(content);

        //content.setDividerPositions(0.225);
        //Platform.runLater(() -> content.setDividerPositions(0.225));
    }

    public void refreshTree() {
        if (treeView == null) {
            treeView = new TreeViewWithItems(new TreeItem());
        }
        // Create the display menu from the workspace tree
        ObservableList<SaharaItem> children = FXCollections.observableArrayList();
        children.add(Global.currentWorkspace);
        treeView.setShowRoot(false);
        treeView.setItems(children);

        // Select the selected tree item in the tree
        if (Global.selectedTreeItem == null || Global.selectedTreeItem.getValue() == null) {
            treeView.selectItem(Global.currentWorkspace);
        }
        else {
            treeView.selectItem((HierarchyData)Global.selectedTreeItem.getValue());
        }
    }

    public void toggleTree() {
        if (content.getItems().contains(treeView)) {
            dividerPositions = content.getDividerPositions();
            content.getItems().remove(treeView);
        }
        else {
            content.getItems().add(0, treeView);
            content.setDividerPositions(dividerPositions);
        }
    }


    public void setContent(Node node) {
        contentPane.setContent(node);
    }


    private void init() {
        content.setDividerPositions(0.2);
        MenuBar menuBar = MainMenuBar.getMainMenuBar();
        this.setTop(menuBar);
        this.refreshTree();

        contentPane = new ScrollPane(informationPane);
        contentPane.setFitToHeight(true);
        contentPane.setFitToWidth(true);
        contentPane.setStyle("-fx-background-color:transparent;");
        this.setContent(new WorkspaceScene(Global.currentWorkspace));

        this.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0,
                                Number arg1, Number arg2) {
                App.content.setPrefHeight(arg2.doubleValue());
            }
        });
    }
}
