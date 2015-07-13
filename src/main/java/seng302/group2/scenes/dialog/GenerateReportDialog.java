/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.dialog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.dialog.Dialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seng302.group2.Global;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.util.reporting.ReportGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to create a pop up dialog for creating a skill.
 *
 * @author drm127
 */
@SuppressWarnings("deprecation")
public class GenerateReportDialog {
    static transient Logger logger = LoggerFactory.getLogger(GenerateReportDialog.class);

    /**
     * Displays the Dialog box for creating a skill.
     */
    public static void show() {
        Dialog dialog = new Dialog(null, "Generate Report");
        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);

        Button btnGenerate = new Button("Generate");
        Button btnCancel = new Button("Cancel");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(btnGenerate, btnCancel);



        TreeItem<SaharaItem> root = new CheckBoxTreeItem<>(Global.currentWorkspace);
        addChildren(root);
        root.setExpanded(true);

        TreeView<SaharaItem> treeView = new TreeView<>(root);
        treeView.setCellFactory(CheckBoxTreeCell.<SaharaItem>forTreeView());


        grid.getChildren().addAll(treeView, buttons);


        btnGenerate.setOnAction((event) -> {
                List<SaharaItem> checkedItems = getCheckedItems(root);
                // TODO: Plug in custom generation method
                logger.info(checkedItems.toString());
                if (!checkedItems.isEmpty()) {
                    ReportGenerator.generateReport(checkedItems);
                    dialog.hide();
                }
            });


        btnCancel.setOnAction((event) -> dialog.hide());

        dialog.setResizable(true);
        dialog.setIconifiable(false);
        dialog.setContent(grid);
        dialog.show();
    }


    /**
     * Adds children of the SaharaItem to its TreeItem wrapper recursively
     * @param root The root tree item
     */
    protected static void addChildren(TreeItem<SaharaItem> root) {
        if (root.getValue() == null || root.getValue().getChildren() == null) {
            return;
        }
        for (SaharaItem item : root.getValue().getChildren()) {
            TreeItem<SaharaItem> child = new CheckBoxTreeItem<>(item);
            root.getChildren().add(child);
            addChildren(child);
        }
    }


    protected static List<SaharaItem> getCheckedItems(TreeItem<SaharaItem> root) {

        List<SaharaItem> checkedItems = new ArrayList<>();

        if (((CheckBoxTreeItem)root).isSelected() || ((CheckBoxTreeItem)root).isIndeterminate() ) {
            checkedItems.add(root.getValue());
        }

        for (TreeItem item : root.getChildren()) {
            checkedItems.addAll(getCheckedItems(item));
        }

        return checkedItems;
    }
}
