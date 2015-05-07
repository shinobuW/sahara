/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.workspace;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import seng302.group2.Global;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
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
	    StreamResult result = new StreamResult(new File("C:\\file.xml"));

	    // Output to console for testing
	    // StreamResult result = new StreamResult(System.out);

	    transformer.transform(source, result);

	    System.out.println("File exported!");
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
        
        for (Project project : workspace.getProjects())
        {
            Element projectElement = generateProject(project);
	    workSpaceElement.appendChild(projectElement);
        }
        
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
        
        for (Team team : project.getTeams())
        {
            Element teamElement = generateTeam(team);
	    projectElement.appendChild(teamElement);
        }
        
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
        }
        teamElement.appendChild(othersElement);
        
        return teamElement;
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
