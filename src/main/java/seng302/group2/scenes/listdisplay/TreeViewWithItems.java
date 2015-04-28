package seng302.group2.scenes.listdisplay;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.WeakListChangeListener;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.contextmenu.CategoryTreeContextMenu;
import seng302.group2.scenes.contextmenu.ElementTreeContextMenu;
import seng302.group2.scenes.information.*;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;
import sun.applet.Main;

import java.util.HashMap;
import java.util.Map;


/**
 * This class extends the {@link TreeView} to use items as a data source.
 * This allows you to treat a {@link TreeView} in a similar way as a
 * {@link javafx.scene.control.ListView} or {@link javafx.scene.control.TableView}.
 * Each item in the list must implement the {@link HierarchyData} interface, in order to map the
 * recursive nature of the tree data to the tree view.
 * Each change in the underlying data (adding, removing, sorting) will then be automatically
 * reflected in the UI.
 *
 * @author Christian Schudt (modified by Jordane Lew)
 * @param <T> The type of treeview items
 */
public class TreeViewWithItems<T extends HierarchyData<T>> extends TreeView<T>
{
    /**
     * Keep hard references for each listener, so that they don't get garbage collected too soon.
     */
    private final Map<TreeItem<T>, ListChangeListener<T>> hardReferences = new HashMap<TreeItem<T>,
            ListChangeListener<T>>();

    /**
     * Also store a reference from each tree item to its weak listeners, so that the listener can be
     * removed, when the tree item gets removed.
     */
    private final Map<TreeItem<T>, WeakListChangeListener<T>> weakListeners =
            new HashMap<TreeItem<T>, WeakListChangeListener<T>>();

    private ObjectProperty<ObservableList<? extends T>> items =
            new SimpleObjectProperty<ObservableList<? extends T>>(this, "items");


    /**
     * Creates the tree view.
     */
    public TreeViewWithItems()
    {
        super();
        init();
    }


    /**
     * Creates the tree view with a given root.
     *
     * @param root The root tree item.
     * @see TreeView#TreeView(javafx.scene.control.TreeItem)
     */
    public TreeViewWithItems(TreeItem<T> root)
    {
        super(root);
        init();
    }


    /**
     * Initializes the tree view.
     */
    private void init()
    {
        setContextMenu(new CategoryTreeContextMenu());

        rootProperty().addListener(new ChangeListener<TreeItem<T>>()
        {
            @Override
            public void changed(ObservableValue<? extends TreeItem<T>> observableValue,
                    TreeItem<T> oldRoot, TreeItem<T> newRoot)
            {
                clear(oldRoot);
                updateItems();
            }
        });

        setItems(FXCollections.<T>observableArrayList());

        /* Do not use ChangeListener, because it won't trigger if old list equals new list (but in
        fact different references). */
        items.addListener(new InvalidationListener()
        {
            @Override
            public void invalidated(Observable observable)
            {
                clear(getRoot());
                updateItems();
            }
        });

        /* Sets the App.selectedTreeItem when a new selection is made, and sets the information
         * shown in the main pane to the selected item's details */
        this.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
        {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue)
            {
                TreeItem<Object> selectedItem = (TreeItem<Object>) newValue;
                Global.selectedTreeItem = selectedItem;
                //System.out.println(App.selectedTreeItem.getValue().getClass());  // testing

                //Updates the display pane to be pane for the selectItem
                if (Global.selectedTreeItem == null
                        || Global.selectedTreeItem.getValue() == null)
                {
                    // Nothing is selected, make a default selection?
                    App.content.getChildren().remove(MainScene.informationGrid);
                    //WorkspaceScene.getWorkspaceScene((Workspace)
                    //Global.selectedTreeItem.getValue());
                    App.content.getChildren().add(MainScene.informationGrid);
                }
                if (Global.selectedTreeItem.getValue() instanceof Person)
                {
                    App.content.getChildren().remove(MainScene.informationGrid);
                    PersonScene.getPersonScene((Person) Global.selectedTreeItem.getValue());
                    App.content.getChildren().add(MainScene.informationGrid);
                    setContextMenu(new ElementTreeContextMenu());

                } else if (Global.selectedTreeItem.getValue() instanceof Project)
                {
                    App.content.getChildren().remove(MainScene.informationGrid);
                    ProjectScene.getProjectScene(
                            (Project) Global.selectedTreeItem.getValue());
                    App.content.getChildren().add(MainScene.informationGrid);
                    setContextMenu(new ElementTreeContextMenu());

                } else if (Global.selectedTreeItem.getValue() instanceof Workspace)
                {
                    App.content.getChildren().remove(MainScene.informationGrid);
                    WorkspaceScene.getWorkspaceScene((Workspace)
                            Global.selectedTreeItem.getValue());
                    App.content.getChildren().add(MainScene.informationGrid);
                } else if (Global.selectedTreeItem.getValue() instanceof Skill)
                {
                    App.content.getChildren().remove(MainScene.informationGrid);
                    SkillScene.getSkillScene((Skill) Global.selectedTreeItem.getValue());
                    App.content.getChildren().add(MainScene.informationGrid);
                    setContextMenu(new ElementTreeContextMenu());
                } else if (Global.selectedTreeItem.getValue() instanceof Team)
                {
                    App.content.getChildren().remove(MainScene.informationGrid);
                    TeamScene.getTeamScene((Team) Global.selectedTreeItem.getValue());
                    App.content.getChildren().add(MainScene.informationGrid);
                    setContextMenu(new ElementTreeContextMenu());
                } else if (Global.selectedTreeItem.getValue() instanceof Category)
                {
                    if (Global.selectedTreeItem.getValue().toString().equals("Roles"))
                    {
                        setContextMenu(null);
                    } else if (Global.selectedTreeItem.getValue().toString().equals("People"))
                    {
                        App.content.getChildren().remove(MainScene.informationGrid);
                        PersonCategoryScene.getPersonCategoryScene(Global.currentWorkspace);
                        App.content.getChildren().add(MainScene.informationGrid);
                        setContextMenu(new CategoryTreeContextMenu());
                    } else
                    {
                        setContextMenu(new CategoryTreeContextMenu());
                    }
                } else if (Global.selectedTreeItem.getValue() instanceof Role)
                {
                    App.content.getChildren().remove(MainScene.informationGrid);
                    RoleScene.getRoleScene((Role) Global.selectedTreeItem.getValue());
                    App.content.getChildren().add(MainScene.informationGrid);
                    setContextMenu(new ElementTreeContextMenu());
                }
            }
        });
    }


    /**
     * Removes all listener from a root.
     *
     * @param root The root.
     */
    private void clear(TreeItem<T> root)
    {
        if (root != null)
        {
            for (TreeItem<T> treeItem : root.getChildren())
            {
                removeRecursively(treeItem);
            }

            removeRecursively(root);
            root.getChildren().clear();
        }
    }


    /**
     * Updates the items
     */
    private void updateItems()
    {
        if (getItems() != null)
        {
            for (T value : getItems())
            {
                getRoot().getChildren().add(addRecursively(value));
            }

            ListChangeListener<T> rootListener = getListChangeListener(getRoot().getChildren());
            WeakListChangeListener<T> weakListChangeListener =
                    new WeakListChangeListener<T>(rootListener);
            hardReferences.put(getRoot(), rootListener);
            weakListeners.put(getRoot(), weakListChangeListener);
            getItems().addListener(weakListChangeListener);
        }
    }


    /**
     * Gets a {@link javafx.collections.ListChangeListener} for a {@link TreeItem}. It listens to
     * changes on the underlying list and updates the UI accordingly.
     * @param treeItemChildren The associated tree item's children list.
     * @return The listener.
     */
    private ListChangeListener<T> getListChangeListener(
            final ObservableList<TreeItem<T>> treeItemChildren)
    {
        return new ListChangeListener<T>()
        {
            @Override
            public void onChanged(final Change<? extends T> change)
            {
                while (change.next())
                {
                    if (change.wasUpdated())
                    {
                        // http://javafx-jira.kenai.com/browse/RT-23434
                        continue;
                    }
                    if (change.wasRemoved())
                    {
                        for (int i = change.getRemovedSize() - 1; i >= 0; i--)
                        {
                            removeRecursively(treeItemChildren.remove(change.getFrom() + i));
                        }
                    }
                    // If items have been added
                    if (change.wasAdded())
                    {
                        // Get the new items
                        for (int i = change.getFrom(); i < change.getTo(); i++)
                        {
                            treeItemChildren.add(i, addRecursively(change.getList().get(i)));
                        }
                    }
                    // If the list was sorted.
                    if (change.wasPermutated())
                    {
                        // Store the new order.
                        Map<Integer, TreeItem<T>> tempMap = new HashMap<Integer, TreeItem<T>>();

                        for (int i = change.getTo() - 1; i >= change.getFrom(); i--)
                        {
                            int a = change.getPermutation(i);
                            tempMap.put(a, treeItemChildren.remove(i));
                        }

                        getSelectionModel().clearSelection();

                        // Add the items in the new order.
                        for (int i = change.getFrom(); i < change.getTo(); i++)
                        {
                            treeItemChildren.add(tempMap.remove(i));
                        }
                    }
                }
            }
        };
    }


    /**
     * Removes the listener recursively.
     * @param item The tree item.
     * @return the item removed
     */
    private TreeItem<T> removeRecursively(TreeItem<T> item)
    {
        if (item.getValue() != null && item.getValue().getChildren() != null)
        {

            if (weakListeners.containsKey(item))
            {
                item.getValue().getChildren().removeListener(weakListeners.remove(item));
                hardReferences.remove(item);
            }
            for (TreeItem<T> treeItem : item.getChildren())
            {
                removeRecursively(treeItem);
            }
        }
        return item;
    }


    /**
     * Adds the children to the tree recursively.
     * @param value The initial value.
     * @return The tree item.
     */
    private TreeItem<T> addRecursively(T value)
    {
        TreeItem<T> treeItem = new TreeItem<T>();
        treeItem.setValue(value);
        treeItem.setExpanded(true);

        if (value != null && value.getChildren() != null)
        {
            ListChangeListener<T> listChangeListener =
                    getListChangeListener(treeItem.getChildren());
            WeakListChangeListener<T> weakListener =
                    new WeakListChangeListener<T>(listChangeListener);
            value.getChildren().addListener(weakListener);

            hardReferences.put(treeItem, listChangeListener);
            weakListeners.put(treeItem, weakListener);
            for (T child : value.getChildren())
            {
                treeItem.getChildren().add(addRecursively(child));
            }
        }
        return treeItem;
    }


    /**
     * Gets the observable list of items
     * @return The observable list of items
     */
    public ObservableList<? extends T> getItems()
    {
        return items.get();
    }


    /**
     * Sets items for the tree.
     * @param items The list.
     */
    public void setItems(ObservableList<? extends T> items)
    {
        this.items.set(items);
    }


    /**
     * Scans the entire tree from the root and selects the item if it is found.
     * @param item The (TreeViewItem) item to select
     */
    public void selectItem(T item)
    {
        selectItem(item, this.getRoot());
    }


    /**
     * Scans the tree and compares the item to the root TreeItem, if they match, select the TreeItem
     * If not, recursively check the children of the TreeItem. If the item exists in the tree, it
     * will eventually be selected through the depth-first search.
     * @param item The (TreeViewItem) item to select
     * @param root The root node to start checking, usually this.getRoot()
     */
    public void selectItem(T item, TreeItem<T> root)
    {
        for (TreeItem<T> treeItem : root.getChildren())
        {
            if (treeItem.getValue() == item)
            {
                getSelectionModel().select(treeItem);
            }
            else
            {
                selectItem(item, treeItem);
            }
        }
    }

}