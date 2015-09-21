package seng302.group2.scenes.treeView;

import javafx.scene.control.TreeItem;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.workspace.SaharaItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jordane on 21/07/2015.
 */
public class HierarchyTracker {

    private static Boolean collapsedDefault = Boolean.FALSE;

    /**
     * A map to keep track of if items are expanded or not. True if expanded.
     */
    private static Map<SaharaItem, Boolean> collapseMap = new HashMap<>();


    /**
     * Analyses items in the workspace (usually after a deserialization,) and adds their expansion values to the
     * tracking map
     */
    public static void expandWorkspace() {
        collapseMap.put(Global.currentWorkspace, true);
    }


    /**
     * Refreshes the mapping of the tree items and if they are expanded or not.
     *
     * @param tree The tree to map items for
     */
    public static void refreshMap(TreeViewWithItems<SaharaItem> tree) {
        for (TreeItem<SaharaItem> item : tree.getTreeItems()) {
            System.out.println(item + " " + item.isExpanded());
            collapseMap.put(item.getValue(), item.isExpanded());
        }
    }

    /**
     * Restores the mapping of the tree items from the internal dictionary of expanded items.
     *
     * @param tree The tree to restore the mapping of
     */
    public static void restoreMap(TreeViewWithItems<SaharaItem> tree) {
        for (TreeItem<SaharaItem> item : tree.getTreeItems()) {
            item.setExpanded(isExpanded(item.getValue()));
        }
    }

    /**
     * Checks to see if an item in the tree is expanded or not. If the items
     * aren't in the dictionary, they are treated as expanded by default.
     *
     * @param item The item to check
     * @return If the item is expanded
     */
    public static Boolean isExpanded(SaharaItem item) {
        if (collapseMap.containsKey(item)) {
            return collapseMap.get(item);
        }
        else {
            return collapsedDefault;
        }
    }

    /**
     * Expands all items in the tree by mapping all of the items inside of it to true, and then
     * restoring the map state from the dictionary
     *
     * @param tree The tree to expand all items of
     */
    public static void expandAll(TreeViewWithItems<SaharaItem> tree) {
        for (TreeItem<SaharaItem> item : tree.getTreeItems()) {
            collapseMap.put(item.getValue(), Boolean.TRUE);
        }
        restoreMap(tree);
    }
}
