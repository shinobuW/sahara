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
import seng302.group2.workspace.SaharaItem;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

/**
 * @author crw73
 */
public class ReportGenerator {
    private static DocumentBuilderFactory docFactory = null;
    private static DocumentBuilder docBuilder = null;
    public static Document doc = null;
    public static List<SaharaItem> generatedItems = null;
    public static int iterator = 0;

    //Constructor only exists to setup basic report elements for tests
    public ReportGenerator() {
        try {
            docFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();
        }
        catch (Exception e) {

        }
    }

    public static boolean generateReport(List<SaharaItem> checkedItems) {
        generatedItems = checkedItems;
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
            SaharaItem item = generatedItems.get(0);
            Element xmlElement = item.generateXML();
            report.appendChild(xmlElement);

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

}
