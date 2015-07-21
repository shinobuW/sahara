package seng302.group2.scenes.control;

import javafx.scene.control.TreeItem;
import seng302.group2.scenes.treeView.TreeViewWithItems;
import seng302.group2.workspace.SaharaItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jordane on 21/07/2015.
 */
public class HierarchyTracker {
    /**
     * A map to keep track of if items are expanded or not. True if expanded.
     */
    private static Map<SaharaItem, Boolean> collapseMap = new HashMap<>();

    /**
     * Refreshes the mapping of the tree items and if they are expanded or not.
     * @param tree The tree to map items for
     */
    public static void refreshMap(TreeViewWithItems<SaharaItem> tree) {
        for (TreeItem<SaharaItem> item : tree.getTreeItems()) {
            collapseMap.put(item.getValue(), item.isExpanded());
        }
        System.out.println(collapseMap);
    }

    /**
     * Restores the mapping of the tree items from the dictionary of expanded items.
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
     * @param item The item to check
     * @return If the item is expanded
     */
    public static Boolean isExpanded(SaharaItem item) {
        if (collapseMap.containsKey(item)) {
            return collapseMap.get(item);
        } else {
            return Boolean.TRUE;
        }
    }

    /**
     * Expands all items in the tree by mapping all of the items inside of it to true, and then
     * restoring the map state from the dictionary
     * @param tree The tree to expand all items of
     */
    public static void expandAll(TreeViewWithItems<SaharaItem> tree) {
        for (TreeItem<SaharaItem> item : tree.getTreeItems()) {
            collapseMap.put(item.getValue(), Boolean.TRUE);
        }
        restoreMap(tree);
    }
}
