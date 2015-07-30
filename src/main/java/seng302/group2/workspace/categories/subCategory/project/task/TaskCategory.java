package seng302.group2.workspace.categories.subCategory.project.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.w3c.dom.Element;
import seng302.group2.scenes.dialog.CreateStoryDialog;
import seng302.group2.scenes.sceneswitch.switchStrategies.category.subCategory.project.subCategory.sprint.TaskCategorySwitchStrategy;
import seng302.group2.util.reporting.ReportGenerator;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.categories.subCategory.SubCategory;
import seng302.group2.workspace.project.sprint.Sprint;

/**
 * Created by cvs20 on 28/07/15.
 */
public class TaskCategory extends SubCategory {
    /**
     * Basic constructor for a TreeView category
     *
     * @param sprint The parent project of this release category
     */
    public TaskCategory(Sprint sprint) {
        super("Unassigned Tasks", sprint);
        setCategorySwitchStrategy(new TaskCategorySwitchStrategy());
    }

    /**
     * Gets the story category's sprint
     *
     * @return the story category's sprint
     */
    public Sprint getSprint() {
        return (Sprint) parent;
    }


    /**
     * Method for creating an XML element for the Team within report generation
     * @return element for XML generation
     */
    @Override
    public Element generateXML() {
        Element storyElements = ReportGenerator.doc.createElement("unassigned-stories");
        for (Object item : getChildren()) {
            if (ReportGenerator.generatedItems.contains((SaharaItem) item)) {
                Element xmlElement = ((SaharaItem) item).generateXML();
                if (xmlElement != null) {
                    storyElements.appendChild(xmlElement);
                }
                ReportGenerator.generatedItems.remove(item);
            }
        }
        return storyElements;
    }

    /**
     * Gets the children of the category
     *
     * @return the children of the category
     */
    @Override
    public ObservableList getChildren() {
        if (parent == null) {
            return FXCollections.observableArrayList();
        }
        return ((Sprint) parent).getUnallocatedTasks();
    }

    /**
     * Shows the Story Creation dialog.
     */
    @Override
    public void showCreationDialog() {
        new CreateStoryDialog();
    }
}
