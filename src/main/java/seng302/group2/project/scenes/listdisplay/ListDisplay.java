/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.project.scenes.listdisplay;


import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import seng302.group2.App;
import seng302.group2.project.team.person.Person;

/**
 *
 * @author crw73
 */
public class ListDisplay
{
    
    public static TreeItem getPeopleTree()
    {
        TreeItem<Object> peopleItem = new TreeItem<>("People");
        for (Person person : App.currentProject.getPeople()) 
        {
            TreeItem<Object> personItem = new TreeItem<>(person);            
            peopleItem.getChildren().add(personItem);
        }
        return peopleItem;
    }
    
    public static TreeView getProjectTree() 
    {
        /*
        App.currentProject = new Project("shortname", "longname", "desc");
        App.currentProject.addPerson(new Person());
        App.currentProject.addPerson(new Person());
        */
        
        // Return a non-populated TreeView if there is no project open
        if (App.currentProject == null)
        {
            return new TreeView();
        }

        TreeItem<Object> projectItem = new TreeItem<>(App.currentProject);
        projectItem.setExpanded(true);
        
        // Adds people to the tree
        projectItem.getChildren().add(ListDisplay.getPeopleTree());
        
        TreeView<Object> tree = new TreeView<>(projectItem);        
        return tree;
    }
}
