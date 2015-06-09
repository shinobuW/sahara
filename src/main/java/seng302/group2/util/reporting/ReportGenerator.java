/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.reporting;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import seng302.group2.Global;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.allocation.Allocation;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.release.Release;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.LocalDate;

/**
 * @author crw73
 */
public class ReportGenerator {
    private static DocumentBuilderFactory docFactory = null;
    private static DocumentBuilder docBuilder = null;
    private static Document doc = null;

    public static boolean generateReport() {
        try {
            docFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();

            Element report = doc.createElement("status-report");

            //Header
            Element header = doc.createElement("header");
            Element title = doc.createElement("report-title");
            title.appendChild(doc.createTextNode(Global.currentWorkspace.getShortName()));
            Element date = doc.createElement("report-creation-date");
            date.appendChild(doc.createTextNode(LocalDate.now().format(Global.dateFormatter)));
            header.appendChild(title);
            header.appendChild(date);
            report.appendChild(header);

            //WorkSpace Node
            Element workspaceElement = generateWorkSpace(Global.currentWorkspace);
            //System.out.println(Global.currentWorkspace);
            report.appendChild(workspaceElement);
            doc.appendChild(report);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Export Report");
            if (Global.lastSaveLocation != null && !Global.lastSaveLocation.equals("")) {
                fileChooser.setInitialDirectory(new File(Global.lastSaveLocation));
            }
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("XML Report", "*.xml")
            );

            File selectedFile;

            try {
                selectedFile = fileChooser.showSaveDialog(new Stage());
            }
            catch (IllegalArgumentException e) {
                // The file directory is invalid, try again with 'root'
                //System.out.println("Bad directory");
                fileChooser.setInitialDirectory(new File("/"));
                selectedFile = fileChooser.showSaveDialog(new Stage());
            }

            if (selectedFile != null) {
                String file_name = selectedFile.toString();
                if (!file_name.endsWith(".xml")) {
                    file_name += ".xml";
                }
                StreamResult result = new StreamResult(file_name);

                // Output to console for testing
                // StreamResult result = new StreamResult(System.out);

                transformer.transform(source, result);

                //System.out.println("File exported!");
            }
            else {
                //System.out.println("Export aborted (by user or error? :()");
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            //System.out.println("Error exporting ");
        }
        return true;
    }

    private static Element generateWorkSpace(Workspace workspace) {
        Element workSpaceElement = doc.createElement("workspace");

        //WorkSpace Elements
        Element workSpaceShortName = doc.createElement("identifier");
        workSpaceShortName.appendChild(doc.createTextNode(workspace.getShortName()));
        workSpaceElement.appendChild(workSpaceShortName);

        Element workSpaceLongName = doc.createElement("long-name");
        workSpaceLongName.appendChild(doc.createTextNode(workspace.getLongName()));
        workSpaceElement.appendChild(workSpaceLongName);

        Element workSpaceDescription = doc.createElement("description");
        workSpaceDescription.appendChild(doc.createTextNode(workspace.getDescription()));
        workSpaceElement.appendChild(workSpaceDescription);

        Element projectElements = doc.createElement("projects");
        for (Project project : workspace.getProjects()) {
            Element projectElement = generateProject(project);
            projectElements.appendChild(projectElement);
        }
        workSpaceElement.appendChild(projectElements);

        Element roleElements = doc.createElement("roles");
        for (Role role : workspace.getRoles()) {
            Element roleElement = generateRole(role);
            roleElements.appendChild(roleElement);

        }
        workSpaceElement.appendChild(roleElements);

        Element teamElements = doc.createElement("unassigned-teams");
        for (Team team : workspace.getTeams()) {
            if (team.getCurrentAllocation() == null && !team.isUnassignedTeam()) {
                System.out.println(team + " Team name");
                Element teamElement = generateTeam(team);
                teamElements.appendChild(teamElement);
            }
        }
        workSpaceElement.appendChild(teamElements);

        Element peopleElements = doc.createElement("unassigned-people");
        for (Team team : workspace.getTeams()) {
            if (team.isUnassignedTeam()) {
                for (Person person : team.getPeople()) {
                    Element personElement = generatePerson(person);
                    peopleElements.appendChild(personElement);
                }
            }
        }
        workSpaceElement.appendChild(peopleElements);

        Element skillElements = doc.createElement("unassigned-skills");
        for (Skill skill : workspace.getSkills()) {
            boolean assigned = false;
            for (Person person : workspace.getPeople()) {
                if (person.getSkills().contains(skill)) {
                    assigned = true;
                    break;
                }
            }
            if (!assigned) {
                Element skillElement = generateSkill(skill);
                skillElements.appendChild(skillElement);
            }
        }
        workSpaceElement.appendChild(skillElements);

        return workSpaceElement;
    }

    private static Element generateProject(Project project) {
        Element projectElement = doc.createElement("project");

        //WorkSpace Elements
        Element projectShortName = doc.createElement("identifier");
        projectShortName.appendChild(doc.createTextNode(project.getShortName()));
        projectElement.appendChild(projectShortName);

        Element projectLongName = doc.createElement("long-name");
        projectLongName.appendChild(doc.createTextNode(project.getLongName()));
        projectElement.appendChild(projectLongName);

        Element projectDescription = doc.createElement("description");
        projectDescription.appendChild(doc.createTextNode(project.getDescription()));
        projectElement.appendChild(projectDescription);

        Element teamElements = doc.createElement("current-teams");
        for (Team team : project.getCurrentTeams()) {
            Allocation currentAllocation = team.getCurrentAllocation();
            Element teamElement = generateAllocatedTeam(team, currentAllocation);
            teamElements.appendChild(teamElement);
        }
        projectElement.appendChild(teamElements);

        Element teamPreviousElements = doc.createElement("previous-teams");
        for (Allocation allocation : project.getPastAllocations()) {
            Element teamElement = generateAllocation(allocation);
            teamPreviousElements.appendChild(teamElement);
        }
        projectElement.appendChild(teamPreviousElements);

        Element teamFutureElements = doc.createElement("future-teams");
        for (Allocation allocation : project.getFutureAllocations()) {
            Element teamElement = generateAllocation(allocation);
            teamFutureElements.appendChild(teamElement);
        }
        projectElement.appendChild(teamFutureElements);

        Element releaseElements = doc.createElement("releases");
        for (Release release : project.getReleases()) {
            Element releaseElement = generateRelease(release);
            releaseElements.appendChild(releaseElement);
        }
        projectElement.appendChild(releaseElements);

        Element backlogElements = doc.createElement("backlogs");
        for (Backlog backlog : project.getBacklogs()) {
            Element backlogElement = generateBacklog(backlog);
            backlogElements.appendChild(backlogElement);
        }
        projectElement.appendChild(backlogElements);

        Element storyElements = doc.createElement("stories");
        for (Story story : project.getUnallocatedStories()) {
            Element storyElement = generateStory(story);
            storyElements.appendChild(storyElement);

        }
        projectElement.appendChild(storyElements);

        return projectElement;
    }

    private static Element generateAllocatedTeam(Team team, Allocation allocation) {

        Element teamElement = doc.createElement("team");

        //WorkSpace Elements
        Element teamShortName = doc.createElement("identifier");
        teamShortName.appendChild(doc.createTextNode(team.getShortName()));
        teamElement.appendChild(teamShortName);

        Element teamDescription = doc.createElement("description");
        teamDescription.appendChild(doc.createTextNode(team.getDescription()));
        teamElement.appendChild(teamDescription);

        Element teamStartDate = doc.createElement("allocation-start-date");
        teamStartDate.appendChild(doc.createTextNode(allocation.getStartDate().format(
                Global.dateFormatter)));
        teamElement.appendChild(teamStartDate);

        Element teamEndDate = doc.createElement("allocation-end-date");
        teamEndDate.appendChild(doc.createTextNode(allocation.getEndDate().format(
                Global.dateFormatter)));
        teamElement.appendChild(teamEndDate);

        Element productOwnerElement = doc.createElement("product-owner");
        if (team.getProductOwner() != null) {
            Element teamProductOwner = generatePerson(team.getProductOwner());
            productOwnerElement.appendChild(teamProductOwner);
        }
        teamElement.appendChild(productOwnerElement);

        Element scrumMasterElement = doc.createElement("scrum-master");
        if (team.getProductOwner() != null) {
            Element teamScrumMaster = generatePerson(team.getScrumMaster());
            scrumMasterElement.appendChild(teamScrumMaster);
        }
        teamElement.appendChild(scrumMasterElement);

        Element devElement = doc.createElement("devs");
        for (Person person : team.getPeople()) {
            if (person.getRole() != null) {
                if (person.getRole().getType() == Role.RoleType.DEVELOPMENT_TEAM_MEMBER) {
                    Element personElement = generatePerson(person);
                    devElement.appendChild(personElement);
                }
            }
        }
        teamElement.appendChild(devElement);

        Element othersElement = doc.createElement("others");
        for (Person person : team.getPeople()) {
            if (person.getRole() != null) {
                if (person.getRole().getType() == Role.RoleType.OTHER) {
                    Element personElement = generatePerson(person);
                    othersElement.appendChild(personElement);
                }
            }
            if (person.getRole() == null) {
                Element personElement = generatePerson(person);
                othersElement.appendChild(personElement);
            }
        }
        teamElement.appendChild(othersElement);

        return teamElement;
    }

    private static Element generateAllocation(Allocation allocation) {
        Element allocationElement = doc.createElement("team");

        //WorkSpace Elements
        Element allocatedTeam = doc.createElement("team-name");
        allocatedTeam.appendChild(doc.createTextNode(allocation.getTeam().toString()));
        allocationElement.appendChild(allocatedTeam);

        Element allocationStartDate = doc.createElement("allocation-start-date");
        allocationStartDate.appendChild(doc.createTextNode(allocation.getStartDate().format(
                Global.dateFormatter)));
        allocationElement.appendChild(allocationStartDate);

        Element allocationEndDate = doc.createElement("allocation-end-date");
        allocationEndDate.appendChild(doc.createTextNode(allocation.getEndDate().format(
                Global.dateFormatter)));
        allocationElement.appendChild(allocationEndDate);

        return allocationElement;
    }

    private static Element generateTeam(Team team) {
        Element teamElement = doc.createElement("team");

        //WorkSpace Elements
        Element teamShortName = doc.createElement("identifier");
        teamShortName.appendChild(doc.createTextNode(team.getShortName()));
        teamElement.appendChild(teamShortName);

        Element teamDescription = doc.createElement("description");
        teamDescription.appendChild(doc.createTextNode(team.getDescription()));
        teamElement.appendChild(teamDescription);

        Element productOwnerElement = doc.createElement("product-owner");
        if (team.getProductOwner() != null) {
            Element teamProductOwner = generatePerson(team.getProductOwner());
            productOwnerElement.appendChild(teamProductOwner);
        }
        teamElement.appendChild(productOwnerElement);

        Element scrumMasterElement = doc.createElement("scrum-master");
        if (team.getProductOwner() != null) {
            Element teamScrumMaster = generatePerson(team.getScrumMaster());
            scrumMasterElement.appendChild(teamScrumMaster);
        }
        teamElement.appendChild(scrumMasterElement);

        Element devElement = doc.createElement("devs");
        for (Person person : team.getPeople()) {
            if (person.getRole() != null) {
                if (person.getRole().getType() == Role.RoleType.DEVELOPMENT_TEAM_MEMBER) {
                    Element personElement = generatePerson(person);
                    devElement.appendChild(personElement);
                }
            }
        }
        teamElement.appendChild(devElement);

        Element othersElement = doc.createElement("others");
        for (Person person : team.getPeople()) {
            if (person.getRole() != null) {
                if (person.getRole().getType() == Role.RoleType.OTHER) {
                    Element personElement = generatePerson(person);
                    othersElement.appendChild(personElement);
                }
            }
            if (person.getRole() == null) {
                Element personElement = generatePerson(person);
                othersElement.appendChild(personElement);
            }
        }
        teamElement.appendChild(othersElement);

        return teamElement;
    }

    private static Element generateRelease(Release release) {
        Element releaseElement = doc.createElement("release");

        //WorkSpace Elements
        Element releaseShortName = doc.createElement("identifier");
        releaseShortName.appendChild(doc.createTextNode(release.getShortName()));
        releaseElement.appendChild(releaseShortName);

        Element releaseDescription = doc.createElement("description");
        releaseDescription.appendChild(doc.createTextNode(release.getDescription()));
        releaseElement.appendChild(releaseDescription);

        Element releaseDate = doc.createElement("release-date");
        releaseDate.appendChild(doc.createTextNode(release.getDateString()));
        releaseElement.appendChild(releaseDate);

        return releaseElement;
    }

    private static Element generatePerson(Person person) {
        Element personElement = doc.createElement("person");

        //WorkSpace Elements
        Element teamShortName = doc.createElement("identifier");
        teamShortName.appendChild(doc.createTextNode(person.getShortName()));
        personElement.appendChild(teamShortName);

        Element teamFirstName = doc.createElement("first-name");
        teamFirstName.appendChild(doc.createTextNode(person.getFirstName()));
        personElement.appendChild(teamFirstName);

        Element teamLastName = doc.createElement("last-name");
        teamLastName.appendChild(doc.createTextNode(person.getLastName()));
        personElement.appendChild(teamLastName);

        Element teamEmail = doc.createElement("email");
        teamEmail.appendChild(doc.createTextNode(person.getEmail()));
        personElement.appendChild(teamEmail);

        Element teamBirthDate = doc.createElement("birth-date");
        teamBirthDate.appendChild(doc.createTextNode(person.getDateString()));
        personElement.appendChild(teamBirthDate);

        Element teamDescription = doc.createElement("description");
        teamDescription.appendChild(doc.createTextNode(person.getDescription()));
        personElement.appendChild(teamDescription);

        for (Skill skill : person.getSkills()) {
            Element skillElement = generateSkill(skill);
            personElement.appendChild(skillElement);
        }

        return personElement;
    }

    private static Element generateRole(Role role) {
        Element roleElement = doc.createElement("role");

        //WorkSpace Elements
        Element roleShortName = doc.createElement("identifier");
        roleShortName.appendChild(doc.createTextNode(role.getShortName()));
        roleElement.appendChild(roleShortName);

        Element roleDescription = doc.createElement("description");
        roleDescription.appendChild(doc.createTextNode(role.getDescription()));
        roleElement.appendChild(roleDescription);

        Element roleRequiredSkills = doc.createElement("required-skills");
        for (Skill skill : role.getRequiredSkills()) {
            Element skillElement = generateSkill(skill);
            roleRequiredSkills.appendChild(skillElement);
        }
        roleElement.appendChild(roleRequiredSkills);

        return roleElement;
    }

    private static Element generateSkill(Skill skill) {
        Element skillElement = doc.createElement("skill");

        //WorkSpace Elements
        Element skillShortName = doc.createElement("identifier");
        skillShortName.appendChild(doc.createTextNode(skill.getShortName()));
        skillElement.appendChild(skillShortName);

        Element skillDescription = doc.createElement("description");
        skillDescription.appendChild(doc.createTextNode(skill.getDescription()));
        skillElement.appendChild(skillDescription);

        return skillElement;
    }

    private static Element generateStory(Story story) {
        Element storyElement = doc.createElement("story");

        //WorkSpace Elements
        Element storyShortName = doc.createElement("identifier");
        storyShortName.appendChild(doc.createTextNode(story.getShortName()));
        storyElement.appendChild(storyShortName);

        Element storyLongName = doc.createElement("long-name");
        storyLongName.appendChild(doc.createTextNode(story.getLongName()));
        storyElement.appendChild(storyLongName);

        Element storyDescription = doc.createElement("description");
        storyDescription.appendChild(doc.createTextNode(story.getDescription()));
        storyElement.appendChild(storyDescription);

        Element storyCreator = doc.createElement("creator");
        storyCreator.appendChild(doc.createTextNode(story.getCreator()));
        storyElement.appendChild(storyCreator);

        Element storyPriority = doc.createElement("priority");
        storyPriority.appendChild(doc.createTextNode(story.getPriority().toString()));
        storyElement.appendChild(storyPriority);

        return storyElement;
    }

    private static Element generateBacklog(Backlog backlog) {
        Element backlogElement = doc.createElement("backlog");

        //WorkSpace Elements
        Element backlogShortName = doc.createElement("identifier");
        backlogShortName.appendChild(doc.createTextNode(backlog.getShortName()));
        backlogElement.appendChild(backlogShortName);

        Element backlogLongName = doc.createElement("long-name");
        backlogLongName.appendChild(doc.createTextNode(backlog.getLongName()));
        backlogElement.appendChild(backlogLongName);

        Element backlogDescription = doc.createElement("description");
        backlogDescription.appendChild(doc.createTextNode(backlog.getDescription()));
        backlogElement.appendChild(backlogDescription);

        Element backlogProductOwner = doc.createElement("product-owner");
        backlogProductOwner.appendChild(doc.createTextNode(backlog.getProductOwner().toString()));
        backlogElement.appendChild(backlogProductOwner);

        Element backlogStories = doc.createElement("stories");
        for (Story story : backlog.getStories()) {
            Element storyElement = generateStory(story);
            backlogStories.appendChild(storyElement);
        }
        backlogElement.appendChild(backlogStories);

        return backlogElement;
    }
}
