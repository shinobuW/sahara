package seng302.group2.workspace.backlog;

import javafx.collections.ObservableList;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;

import java.io.Serializable;

/**
 * Created by cvs20 on 19/05/15.
 */
public class Backlog extends TreeViewItem implements Serializable, Comparable<Backlog>
{
    private String shortName;
    private String longName;
    private String description;
    private Person productOwner;
    private Project project;

    /**
     * Basic Backlog constructor
     */
    public Backlog()
    {
        this.shortName = "Untitled Backlog";
        this.longName = "Untitled Backlog";
        this.description = "";
        this.productOwner = null;
        this.project = null;
    }

    /**
     * Backlog Constructor with all fields
     * @param shortName short name to identify the story
     * @param longName long name
     * @param description description
     * @param productOwner product owner of the backlog
     */
    public Backlog(String shortName, String longName, String description,
                   Person productOwner, Project project)
    {
        this.shortName = shortName;
        this.longName = longName;
        this.description = description;
        this.productOwner = productOwner;
        this.project = project;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters">

    /**
     * Gets the short name of the Backlog
     * @return the short name
     */
    public String getShortName()
    {
        return this.shortName;
    }

    /**
     * Gets the long name of the Backlog
     * @return the long name
     */
    public String getLongName()
    {
        return this.longName;
    }

    /**
     * Gets the description of the Backlog
     * @return description
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     *
     * Gets the product owner of the Backlog
     * @return productOwner
     */
    public Person getProductOwner()
    {
        return this.productOwner;
    }

    /**
     * Gets the project the Backlog is assigned to
     *
     * @return The description of the team
     */
    public Project getProject()
    {
        return this.project;
    }

    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Setters">

    /**
     * Sets the short name of the Backlog
     * @param shortName short name to be set
     */
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

    /**
     * Sets the long name of the Backlog
     * @param longName long name to be set
     */
    public void setLongName(String longName)
    {
        this.longName = longName;
    }

    /**
     * Sets the description of the Backlog
     * @param description description to be set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Sets the product owner of the Backlog
     * @param productOwner description to be set
     */
    public void setProductOwner(Person productOwner)
    {
        this.productOwner = productOwner;
    }

    /**
     * Sets the Backlog's project
     *
     * @param project the project the backlog has been added to
     */
    public void setProject(Project project)
    {
        this.project = project;
    }

    //</editor-fold>

    /**
     * Gets the children of the TreeViewItem
     * @return The items of the TreeViewItem
     */
    @Override
    public ObservableList<TreeViewItem> getChildren()
    {
        return null;
    }

    // TODO write javadoc.
    @Override
    public int compareTo(Backlog backlog)
    {
        String story1ShortName = this.getShortName().toUpperCase();
        String story2ShortName = backlog.getShortName().toUpperCase();
        return story1ShortName.compareTo(story2ShortName);
    }

    /**
     * An overridden version for the String representation of a Backlog
     * @return The short name of the Backlog
     */
    @Override
    public String toString()
    {
        return this.shortName;
    }

    /**
     * Creates a Backlog edit command and executes it with the Global Command Manager, updating
     * the Backlog with the new parameter values.
     * @param newShortName The new short name
     * @param newLongName The new long name
     * @param newDescription The new description
     * @param newProductOwner The new product owner
     * @param newProject The new project
     *
     */
    public void edit(String newShortName, String newLongName, String newDescription,
                     Person newProductOwner, Project newProject)
    {
        Command relEdit = new BacklogEditCommand(this, newShortName, newLongName, newDescription,
                newProductOwner, newProject);
        Global.commandManager.executeCommand(relEdit);
    }

    /**
     * Deletes a backlog from the given project.
     */
    public void deleteBacklog()
    {
        Command command = new DeleteBacklogCommand(this);
        Global.commandManager.executeCommand(command);
    }

    /**
     * A command class that allows the executing and undoing of backlog edits
     */
    private class BacklogEditCommand implements Command
    {
        private Backlog backlog;
        private String shortName;
        private String longName;
        private String description;
        private Person productOwner;
        private Project project;

        private String oldShortName;
        private String oldLongName;
        private String oldDescription;
        private Person oldProductOwner;
        private Project oldProject;

        private BacklogEditCommand(Backlog backlog, String newShortName, String newLongName,
                                   String newDescription, Person newProductOwner,
                                   Project newProject)
        {
            this.backlog = backlog;
            this.shortName = newShortName;
            this.longName = newLongName;
            this.description = newDescription;
            this.productOwner = newProductOwner;
            this.project = newProject;

            this.oldShortName = backlog.shortName;
            this.oldLongName = backlog.longName;
            this.oldDescription = backlog.description;
            this.oldProductOwner = backlog.productOwner;
            this.oldProject = backlog.project;
        }

        /**
         * Executes/Redoes the changes of the backlog edit
         */
        public void execute()
        {
            backlog.shortName = shortName;
            backlog.longName = longName;
            backlog.description = description;
            backlog.productOwner = productOwner;
            backlog.project = project;
        }

        /**
         * Undoes the changes of the backlog edit
         */
        public void undo()
        {
            backlog.shortName = oldShortName;
            backlog.longName = oldLongName;
            backlog.description = oldDescription;
            backlog.productOwner = oldProductOwner;
            backlog.project = oldProject;
        }
    }

    private class DeleteBacklogCommand implements Command
    {
        private Backlog backlog;
        private Project proj;

        DeleteBacklogCommand(Backlog backlog)
        {
            this.backlog = backlog;
            this.proj = backlog.getProject();
        }

        public void execute()
        {
            System.out.println("Exec Backlog Delete");
            proj.getBacklog().remove(backlog);
            //release.setProject(null);
        }

        public void undo()
        {
            System.out.println("Undone Backlog Delete");
            proj.getBacklog().add(backlog);
            //release.setProject(proj);
        }
    }
}




