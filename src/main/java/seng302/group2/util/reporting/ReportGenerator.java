/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.util.reporting;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import seng302.group2.Global;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.release.Release;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

/**
 *
 * @author crw73
 */
public class ReportGenerator 
{
    private static DocumentBuilderFactory docFactory = null;
    private static DocumentBuilder docBuilder = null;
    private static Document doc = null;
    
    public static boolean generateReport() 
    {
	try
        {
	    docFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();
	    
	    //WorkSpace Node
	    Element rootElement = generateWorkSpace(Global.currentWorkspace);
	    doc.appendChild(rootElement);
	    
	    

	    // write the content into xml file
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    DOMSource source = new DOMSource(doc);
            
            
            
            
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Export Report");
            if (Global.lastSaveLocation != null && Global.lastSaveLocation != "")
            {
                fileChooser.setInitialDirectory(new File(Global.lastSaveLocation));
            }
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("XML Report", "*.xml")
            );
            
            File selectedFile = null;
            
            try
            {
                selectedFile = fileChooser.showSaveDialog(new Stage());
            }
            catch (IllegalArgumentException e)
            {
                // The file directory is invalid, try again with 'root'
                System.out.println("Bad directory");
                fileChooser.setInitialDirectory(new File("/"));
                selectedFile = fileChooser.showSaveDialog(new Stage());
            }
            
            if (selectedFile != null)
            {
                StreamResult result = new StreamResult(selectedFile);

                // Output to console for testing
                // StreamResult result = new StreamResult(System.out);

                transformer.transform(source, result);

                System.out.println("File exported!");
            }
            else
            {
                System.out.println("Export aborted (by user or error? :()");
            }
            
        }
	catch (Exception e) 
        {
	    System.out.println("Error exporting");
	}
	return true;
    }
    
    private static Element generateWorkSpace(Workspace workspace)
    {
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
        for (Project project : workspace.getProjects())
        {
            Element projectElement = generateProject(project);
	    projectElements.appendChild(projectElement);
        }
        workSpaceElement.appendChild(projectElements);
        
        Element roleElements = doc.createElement("roles");
        for (Role role : workspace.getRoles())
        {
            Element roleElement = generateRole(role);
            roleElements.appendChild(roleElement);
            
        }
        workSpaceElement.appendChild(roleElements);
        
        Element teamElements = doc.createElement("unassigned-teams");
        for (Team team : workspace.getTeams())
        {
            if (team.getProject() == null && !team.isUnassignedTeam())
            {
                Element teamElement = generateTeam(team);
                teamElements.appendChild(teamElement);
            }
        }
        workSpaceElement.appendChild(teamElements);
        
        Element peopleElements = doc.createElement("unassigned-people");
        for (Team team : workspace.getTeams())
        {
            if (team.isUnassignedTeam())
            {
                for (Person person : team.getPeople())
                {
                    Element personElement = generatePerson(person);
                    peopleElements.appendChild(personElement);
                }
            }
        }
        workSpaceElement.appendChild(peopleElements);
        
        Element skillElements = doc.createElement("unassigned-skills");
        for (Skill skill : workspace.getSkills())
        {
            boolean assigned = false;
            for (Person person : workspace.getPeople())
            {
                if (person.getSkills().contains(skill))
                {
                    assigned = true;
                    break;
                }
            }
            if (assigned == false)
            {
                Element skillElement = generateSkill(skill);
                skillElements.appendChild(skillElement);
            }
        }
        workSpaceElement.appendChild(skillElements);
        
        return workSpaceElement;
    }
    
    private static Element generateProject(Project project)
    {
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
        
        Element teamElements = doc.createElement("teams");
        for (Team team : project.getTeams())
        {
            Element teamElement = generateTeam(team);
	    teamElements.appendChild(teamElement);
        }
        projectElement.appendChild(teamElements);
        
        Element releaseElements = doc.createElement("releases");
        for (Release release : project.getReleases())
        {
            Element releaseElement = generateRelease(release);
	    releaseElements.appendChild(releaseElement);
        }
        projectElement.appendChild(releaseElements);
        
        return projectElement;
    }
    
    private static Element generateTeam(Team team)
    {
        Element teamElement = doc.createElement("team");

        //WorkSpace Elements
        Element teamShortName = doc.createElement("identifier");
        teamShortName.appendChild(doc.createTextNode(team.getShortName()));
        teamElement.appendChild(teamShortName);

        Element teamDescription = doc.createElement("description");
        teamDescription.appendChild(doc.createTextNode(team.getDescription()));
        teamElement.appendChild(teamDescription);
        
        Element productOwnerElement = doc.createElement("product-owner");
        Element teamProductOwner = generatePerson(team.getProductOwner());
        productOwnerElement.appendChild(teamProductOwner);
        teamElement.appendChild(productOwnerElement);
        
        Element scrumMasterElement = doc.createElement("scrum-master");
        Element teamScrumMaster = generatePerson(team.getScrumMaster());
        scrumMasterElement.appendChild(teamScrumMaster);
        teamElement.appendChild(scrumMasterElement);
        
        Element devElement = doc.createElement("devs");
        for (Person person : team.getPeople())
        {
            if (person.getRole() != null)
            {
                if (person.getRole().getType() == Role.RoleType.DevelopmentTeamMember)
                {
                    Element personElement = generatePerson(person);
                    devElement.appendChild(personElement);
                }
            }
        }
        teamElement.appendChild(devElement);
        
        Element othersElement = doc.createElement("others");
        for (Person person : team.getPeople())
        {
            if (person.getRole() != null)
            {
                if (person.getRole().getType() == Role.RoleType.Others)
                {
                    Element personElement = generatePerson(person);
                    othersElement.appendChild(personElement);
                }
            }
            if (person.getRole() == null) 
            {
                Element personElement = generatePerson(person);
                othersElement.appendChild(personElement);
            }
        }
        teamElement.appendChild(othersElement);
        
        return teamElement;
    }
    
    private static Element generateRelease(Release release)
    {
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
    
    private static Element generatePerson(Person person)
    {
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
        
        for (Skill skill : person.getSkills())
        {
            Element skillElement = generateSkill(skill);
	    personElement.appendChild(skillElement);
        }
        
        return personElement;
    }
    
    private static Element generateRole(Role role)
    {
        Element roleElement = doc.createElement("role");

        //WorkSpace Elements
        Element roleShortName = doc.createElement("identifier");
        roleShortName.appendChild(doc.createTextNode(role.getShortName()));
        roleElement.appendChild(roleShortName);

        Element roleDescription = doc.createElement("description");
        roleDescription.appendChild(doc.createTextNode(role.getDescription()));
        roleElement.appendChild(roleDescription);
        
        Element roleRequiredSkills = doc.createElement("required-skills");
        for (Skill skill : role.getRequiredSkills())
        {
            Element skillElement = generateSkill(skill);
	    roleRequiredSkills.appendChild(skillElement);
        }
        roleElement.appendChild(roleRequiredSkills);
                
        return roleElement;
    }
    
    private static Element generateSkill(Skill skill)
    {
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
    
}
