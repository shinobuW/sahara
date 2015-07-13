package seng302.group2.workspace.categories;

import javafx.collections.ObservableList;
import org.w3c.dom.Element;
import seng302.group2.Global;
import seng302.group2.scenes.dialog.CreateTeamDialog;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.scenes.sceneswitch.switchStrategies.category.TeamCategoryCategorySwitchStrategy;
import seng302.group2.util.reporting.ReportGenerator;
import seng302.group2.workspace.team.Team;

import java.util.HashSet;
import java.util.Set;

/**
 * A category that has the current workspace's roles as children
 * Created by Jordane on 7/06/2015.
 */
public class TeamsCategory extends Category {
    public TeamsCategory() {
        super("Teams");
        setCategorySwitchStrategy(new TeamCategoryCategorySwitchStrategy());
    }

    /**
     * Method for creating an XML element for the Team within report generation
     * @return element for XML generation
     */
    @Override
    public Element generateXML() {
        Element teamElements = ReportGenerator.doc.createElement("unassigned-teams");
        for (Object item : getChildren()) {
            if (ReportGenerator.generatedItems.contains((SaharaItem) item) && !((Team) item).isUnassignedTeam()) {
                Element xmlElement = ((SaharaItem) item).generateXML();
                if (xmlElement != null) {
                    teamElements.appendChild(xmlElement);
                }
                ReportGenerator.generatedItems.remove(item);
            }
        }
        return teamElements;
    }

    @Override
    public Set<SaharaItem> getItemsSet() {
        return new HashSet<>();
    }

    @Override
    public ObservableList getChildren() {
        return Global.currentWorkspace.getTeams();
    }

    @Override
    public void showCreationDialog() {
        CreateTeamDialog.show();
    }
}
