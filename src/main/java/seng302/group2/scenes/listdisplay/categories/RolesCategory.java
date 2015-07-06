package seng302.group2.scenes.listdisplay.categories;

import javafx.collections.ObservableList;
import org.w3c.dom.Element;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.sceneswitch.switchStrategies.category.RoleCategorySwitchStrategy;
import seng302.group2.util.reporting.ReportGenerator;

import java.util.HashSet;
import java.util.Set;

/**
 * A category that has the current workspace's roles as children
 * Created by Jordane on 7/06/2015.
 */
public class RolesCategory extends Category {
    public RolesCategory() {
        super("Roles");
        setCategorySwitchStrategy(new RoleCategorySwitchStrategy());
    }

    @Override
    public Set<TreeViewItem> getItemsSet() {
        return new HashSet<>();
    }

    @Override
    public ObservableList getChildren() {
        return Global.currentWorkspace.getRoles();
    }

    /**
     * Method for creating an XML element for the Role within report generation
     * @return element for XML generation
     */
    @Override
    public Element generateXML() {
        Element roleElements = ReportGenerator.doc.createElement("roles");
        for (Object item : getChildren()) {
            if (ReportGenerator.generatedItems.contains((TreeViewItem) item)) {
                Element xmlElement = ((TreeViewItem) item).generateXML();
                if (xmlElement != null) {
                    roleElements.appendChild(xmlElement);
                }
                ReportGenerator.generatedItems.remove(item);
            }
        }
        return roleElements;
    }


    @Override
    public void showCreationDialog() {
        //TODO For role creation
    }
}
