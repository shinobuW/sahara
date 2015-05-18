package seng302.group2.workspace.story;

import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.TreeViewItem;

import java.io.Serializable;
import java.time.LocalDate;

import javafx.collections.ObservableList;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.skills.Skill;

/**
 * Created by swi67 on 6/05/15.
 */
public class Story extends TreeViewItem implements Serializable, Comparable<Story>
{
    private String shortName;
    private String longName;
    private String description;
    private String creator;
    private Project project;

    /**
     * Basic Story constructor
     */
    public Story()
    {
        this.shortName = "Untitled Story";
        this.longName = "Untitled Story";
        this.description = "";
        this.creator = null;
        this.project = null;
    }

    /**
     * Story Constructor with all fields
     * @param shortName short name to identify the story
     * @param longName long name 
     * @param description description 
     * @param creator creator of the story
     * @param project project the story belongs to 
     */
    public Story(String shortName, String longName, String description, String creator,
            Project project)
    {
        this.shortName = shortName;
        this.longName = longName;
        this.description = description;
        this.creator = creator;
        this.project = project;
    }

    /**
     * Gets the short name of the story
     * @return the short name
     */
    public String getShortName()
    {
        return this.shortName;
    }

    /**
     * Gets the long name of the story
     * @return the long name
     */
    public String getLongName()
    {
        return this.longName;
    }

    /**
     * Gets the description of the story
     * @return description
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * Gets the person who created the story
     * @return name of the person
     */
    public String getCreator()
    {
        return this.creator;
    }
    
    public Project getProject()
    {
        return this.project;
    }

    /**
     * Sets the short name of the story
     * @param shortName short name to be set
     */
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

    /**
     * Sets the long name of the story
     * @param longName long name to be set
     */
    public void setLongName(String longName)
    {
        this.longName = longName;
    }

    /**
     * Sets the description of the story
     * @param description description to be set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Sets the person who created the story
     * @param creator name of the person to be set.
     */
    public void setCreator(String creator)
    {
        this.creator = creator;
    }
    
    /**
     * Sets the project the story belongs to
     * @param project project to set to
     */
    public void setProject(Project project)
    {
        this.project = project;
        project.add(this);
    }
    
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
    public int compareTo(Story compareStory)
    {
        String story1ShortName = this.getShortName().toUpperCase();
        String story2ShortName = compareStory.getShortName().toUpperCase();
        return story1ShortName.compareTo(story2ShortName);
    }
    
    /**
     * An overridden version for the String representation of a Story
     * @return The short name of the Story
     */
    @Override
    public String toString()
    {
        return this.shortName;
    }


    /**
     * Creates a Story edit command and executes it with the Global Command Manager, updating
     * the story with the new parameter values.
     * @param newShortName The new short name
     * @param newDescription The new description
     * @param newProject The new project
     */
    public void edit(String newShortName, String newDescription, Project newProject)
    {
        Command relEdit = new StoryEditCommand(this, newShortName, newDescription, newProject);
        Global.commandManager.executeCommand(relEdit);
    }

    /**
     * Deletes a story from the given project.
     */
    public void deleteStory()
    {
        Command command = new DeleteStoryCommand(this);
        Global.commandManager.executeCommand(command);
    }

    /**
     * A command class that allows the executing and undoing of story edits
     */
    private class StoryEditCommand implements Command
    {
        private Story story;
        private String shortName;
        private String description;
        private Project project;
        private String oldShortName;
        private String oldDescription;
        private Project oldProject;

        private StoryEditCommand(Story story, String newShortName, String newDescription,
                                   Project newProject)
        {
            this.story = story;
            this.shortName = newShortName;
            this.description = newDescription;
            this.project = newProject;
            this.oldShortName = story.shortName;
            this.oldDescription = story.description;
            this.oldProject = story.project;
        }

        /**
         * Executes/Redoes the changes of the story edit
         */
        public void execute()
        {
            story.shortName = shortName;
            story.description = description;
            story.project = project;

        }

        /**
         * Undoes the changes of the story edit
         */
        public void undo()
        {
            story.shortName = oldShortName;
            story.description = oldDescription;
            story.project = oldProject;
        }
    }

    private class DeleteStoryCommand implements Command
    {
        private Story story;
        private Project proj;

        DeleteStoryCommand(Story story)
        {
            this.story = story;
            this.proj = story.getProject();
        }

        public void execute()
        {
            System.out.println("Exec Story Delete");
            proj.getStories().remove(story);
            //release.setProject(null);
        }

        public void undo()
        {
            System.out.println("Undone Story Delete");
            proj.getStories().add(story);
            //release.setProject(proj);
        }
    }
}
