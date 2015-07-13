package seng302.group2.workspace.categories;

import javafx.collections.ObservableList;
import org.w3c.dom.Element;
import seng302.group2.Global;
import seng302.group2.scenes.dialog.CreatePersonDialog;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.scenes.sceneswitch.switchStrategies.category.PersonCategorySwitchStrategy;
import seng302.group2.util.reporting.ReportGenerator;

import java.util.HashSet;
import java.util.Set;

/**
 * A category that has the current workspace's people as children
 * Created by Jordane on 7/06/2015.
 */
public class PeopleCategory extends Category {
    public PeopleCategory() {
        super("People");
        setCategorySwitchStrategy(new PersonCategorySwitchStrategy());
    }

    /**
     * Method for creating an XML element for the People within report generation
     * @return element for XML generation
     */
    @Override
    public Element generateXML() {
        Element peopleElements = ReportGenerator.doc.createElement("unassigned-people");
        for (Object item : getChildren()) {
            if (ReportGenerator.generatedItems.contains((SaharaItem) item)) {
                Element xmlElement = ((SaharaItem) item).generateXML();
                if (xmlElement != null) {
                    peopleElements.appendChild(xmlElement);
                }
                ReportGenerator.generatedItems.remove(item);
            }
        }
        return peopleElements;
    }

    @Override
    public Set<SaharaItem> getItemsSet() {
        return new HashSet<>();
    }

    @Override
    public ObservableList getChildren() {
        return Global.currentWorkspace.getPeople();
    }

    @Override
    public void showCreationDialog() {
        CreatePersonDialog.show();
    }
}
