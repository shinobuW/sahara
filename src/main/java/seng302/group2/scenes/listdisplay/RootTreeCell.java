/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.listdisplay;

import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TextFieldTreeCell;
import seng302.group2.App;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.dialog.CreatePersonDialog;
import seng302.group2.scenes.information.PersonEditScene;

/**
 *
 * @author swi67
 */
public class RootTreeCell extends TreeCell<String>
{
    private ContextMenu rootContextMenu = new ContextMenu();
    
    public RootTreeCell()
    {
        MenuItem newPersonMenu = new MenuItem("New");
        MenuItem editMenu = new MenuItem("Edit");
        MenuItem deleteMenu = new MenuItem("Delete");
        
        rootContextMenu.getItems().addAll(newPersonMenu, editMenu, deleteMenu);
        
        newPersonMenu.setOnAction((ActionEvent event) ->
            {
                CreatePersonDialog.show();
            });


//        editMenu.setOnAction((ActionEvent event) ->
//            {
//                App.content.getChildren().remove(MainScene.informationGrid);
//                PersonEditScene.getPersonEditScene();
//                App.content.getChildren().add(MainScene.informationGrid);
//
//            });
        
        setContextMenu(rootContextMenu);
    }
    
//    @Override
//    public void updateItem(TreeViewItem item, boolean empty) 
//    {
//        //super.updateItem(item, empty);
//
//        //setContextMenu(rootContextMenu);
//    }
}
