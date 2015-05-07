/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.workspace;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import seng302.group2.Global;

/**
 *
 * @author crw73
 */
public class ReportGenerator {
    
    public static boolean generateReport() {
	try{
	    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

	    // root elements
	    Document doc = docBuilder.newDocument();
	    
	    //WorkSpace Node
	    Element rootElement = doc.createElement("workspace");
	    doc.appendChild(rootElement);
	    
	    //WorkSpace Elements
	    Attr workSpaceShortName = doc.createAttribute("identifier");
	    workSpaceShortName.setValue(Global.currentWorkspace.getShortName());
	    rootElement.setAttributeNode(workSpaceShortName);
	    
	    Attr workSpaceLongName = doc.createAttribute("long-name");
	    workSpaceLongName.setValue(Global.currentWorkspace.getLongName());
	    rootElement.setAttributeNode(workSpaceLongName);
	    
	    Attr workSpaceDescription = doc.createAttribute("description");
	    workSpaceDescription.setValue(Global.currentWorkspace.getDescription());
	    rootElement.setAttributeNode(workSpaceDescription);
	    

	    // staff elements
	    Element staff = doc.createElement("Staff");
	    rootElement.appendChild(staff);

	    // set attribute to staff element
	    Attr attr = doc.createAttribute("id");
	    attr.setValue("1");
	    staff.setAttributeNode(attr);

	    // shorten way
	    // staff.setAttribute("id", "1");

	    // firstname elements
	    Element firstname = doc.createElement("firstname");
	    firstname.appendChild(doc.createTextNode("yong"));
	    staff.appendChild(firstname);

	    // lastname elements
	    Element lastname = doc.createElement("lastname");
	    lastname.appendChild(doc.createTextNode("mook kim"));
	    staff.appendChild(lastname);

	    // nickname elements
	    Element nickname = doc.createElement("nickname");
	    nickname.appendChild(doc.createTextNode("mkyong"));
	    staff.appendChild(nickname);

	    // salary elements
	    Element salary = doc.createElement("salary");
	    salary.appendChild(doc.createTextNode("100000"));
	    staff.appendChild(salary);

	    // write the content into xml file
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    DOMSource source = new DOMSource(doc);
	    StreamResult result = new StreamResult(new File("C:\\file.xml"));

	    // Output to console for testing
	    // StreamResult result = new StreamResult(System.out);

	    transformer.transform(source, result);

	    System.out.println("File saved!");
	}
	catch (Exception e) {
	    
	}
	return true;
    }
    
}
