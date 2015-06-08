package seng302.group2.scenes.sceneswitch.switchStrategies;

import seng302.group2.scenes.listdisplay.TreeViewItem;

/**
 * An interface for information switching strategies
 * Created by jml168 on 8/06/15.
 */
public interface InformationSwitchStrategy {
    void switchScene(TreeViewItem item);
    void switchScene(TreeViewItem item, boolean editScene);
}
