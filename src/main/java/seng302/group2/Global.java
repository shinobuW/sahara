/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2;

import javafx.scene.control.TreeItem;
import seng302.group2.project.Project;
import seng302.group2.util.undoredo.UndoRedoManager;

/**
 *
 * @author Jordane
 */
public final class Global
{
    public static Project currentProject = new Project();
    public static TreeItem selectedTreeItem = new TreeItem();
    public static UndoRedoManager undoRedoMan = new UndoRedoManager();
}
