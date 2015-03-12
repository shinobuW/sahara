/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.project.scenes.listdisplay;


import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import seng302.group2.App;
import seng302.group2.project.Project;
import seng302.group2.project.team.person.Person;

/**
 *
 * @author crw73
 */
public class ListDisplay
{
    
   
    
    public static TreeView getListDisplay() 
    {
    
        
        Person newP1 = new Person();
        Person newP2 = new Person();
        
        Project proj = new Project("shortname", "longname", "desc");
        proj.addPerson(newP1);
        proj.addPerson(newP2);
        
        App.currentProject = proj;

        TreeItem<Object> rootItem = new TreeItem<>(proj);
        rootItem.setExpanded(true);
        Project rootProject = (Project) rootItem.getValue();
        for (Person person : rootProject.getPeople()) 
        {
            TreeItem<Object> personItem = new TreeItem<>(person);            
            rootItem.getChildren().add(personItem);
        }        
        TreeView<Object> tree = new TreeView<>(rootItem);        
        
       // primaryStage.setScene(new Scene(root, 300, 250));
        //primaryStage.show();
        return tree;
    }
}
