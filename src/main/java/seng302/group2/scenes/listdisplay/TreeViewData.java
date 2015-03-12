/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.listdisplay;

import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import seng302.group2.project.Project;
import seng302.group2.project.team.person.Person;

/**
 *
 * @author Jordane
 */
public class TreeViewData implements HierarchyData<TreeViewData>
{
    private String name;
    private Object object;
    private Class type;
    private ObservableList<TreeViewData> children = observableArrayList();
    
    
    public TreeViewData(String name, Object object, Class type)
    {
        this.name = name;
        this.object = object;
        this.type = type;
        this.type = object.getClass();
        
        if (type == Project.class)
        {
            System.out.println("here's a proj!");
            
            Project project = (Project) object;
            project.setTreeViewItem(this);
            
            for (Person person : project.getPeople())
            {
                children.add(new TreeViewData(person.getShortName(), person, person.getClass()));
            }
        }
    }
    
    
    @Override
    public ObservableList<TreeViewData> getChildren()
    {
        return children;
    }
    
    
    @Override
    public String toString()
    {
        return this.name;
    }
}
