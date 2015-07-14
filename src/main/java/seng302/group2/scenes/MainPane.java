package seng302.group2.scenes;

import javafx.application.Platform;
import javafx.collections.FXCollections;
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
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.categories.Category;
import seng302.group2.workspace.categories.subCategory.SubCategory;

import static seng302.group2.App.content;


/**
 * A class for holding JavaFX scenes used in the workspace
 *
 * @author Jordane Lew (jml168)
 */
public class MainPane extends BorderPane {
    private TreeViewWithItems<SaharaItem> treeView;
    private Pane informationPane = new Pane();
    private ScrollPane contentPane;
    private boolean menuHidden = false;
    private double[] dividerPositions;

    /**
     * The default constructor of the main pane that performs basic initialisation
     */
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

        //content.setDividerPositions(0.20);
        Platform.runLater(() -> content.setDividerPositions(0.225));
    }

    /**
     * Refreshes the tree menu of the main pane
     */
    public void refreshTree() {
        if (treeView == null) {
            // Create the tree view
            treeView = new TreeViewWithItems<SaharaItem>(new TreeItem<>());
            treeView.setShowRoot(false);
        }

        SaharaItem selectedItem = null;
        if (treeView.getSelectionModel().getSelectedItem() != null) {
            selectedItem = treeView.getSelectionModel().getSelectedItem().getValue();
        }

        treeView.setItems(FXCollections.observableArrayList(Global.currentWorkspace));

        // Select the selected tree item in the tree
        if (selectedItem != null) {
            treeView.selectItem(selectedItem);
        }
        else if (Global.selectedTreeItem != null && Global.selectedTreeItem.getValue() != null) {
            treeView.selectItem((SaharaItem) Global.selectedTreeItem.getValue());
        }
    }

    /**
     * Toggles if the tree menu is present in the pane
     */
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

    /**
     * Sets the content pane child to the given node
     * @param node The node to replace the current content pane
     */
    public void setContent(Node node) {
        contentPane.setContent(node);
    }

    /**
     * Refreshes the content child of the pane
     */
    public void refreshContent() {
        SaharaItem selected = (SaharaItem) Global.selectedTreeItem.getValue();
        if (selected == null) {
            return;
        }

        // Refresh based on the selected item's type
        if (selected instanceof SubCategory) {
            selected.switchToCategoryScene((Category)selected);
        }
        else if (selected instanceof Category) {
            selected.switchToCategoryScene();
        }
        else {
            selected.switchToInfoScene();
        }
    }

    /**
     * Refreshes each of the child elements in the pane
     */
    public void refreshAll() {
        refreshTree();
        refreshContent();
    }

    /**
     * Performs the initialisation tasks of the main pane including adding children elements and styling
     */
    private void init() {
        MenuBar menuBar = MainMenuBar.getMainMenuBar();
        this.setTop(menuBar);
        this.refreshTree();

        contentPane = new ScrollPane(informationPane);
        contentPane.setFitToHeight(true);
        contentPane.setFitToWidth(true);
        contentPane.setStyle("-fx-background-color:transparent;");
        this.setContent(new WorkspaceScene(Global.currentWorkspace));

        this.heightProperty().addListener((arg0, arg1, arg2) -> {
                App.content.setPrefHeight(arg2.doubleValue());
            });
    }

    /**
     * Selects the given item inside the pane (by selecting it through the tree as if it were clicked)
     * @param item The item to select
     */
    public void selectItem(SaharaItem item) {
        treeView.selectItem(item);
    }
}