package seng302.group2.workspace.story;

import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.TreeViewItem;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;

import javafx.collections.ObservableList;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.backlog.Backlog;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.skills.Skill;

/**
 * Created by swi67 on 6/05/15.
 */
public class Story extends TreeViewItem implements Serializable
{
    private String shortName;
    private String longName;
    private String description;
    private String creator;
    private Project project;
    private Integer priority;
    private Backlog backlog;

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
     * @param priority the projects priority
     */
    public Story(String shortName, String longName, String description, String creator,
            Project project, Integer priority)
    {
        this.shortName = shortName;
        this.longName = longName;
        this.description = description;
        this.creator = creator;
        this.project = project;
        this.priority = priority;
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

    /**
     * Gets the priority of the project
     * @return an integer representing the priority
     */
    public Integer getPriority()
    {
        return this.priority;
    }

    /**
     * Gets the project this story belongs to.
     * @return the project
     */
    public Project getProject()
    {
        return this.project;
    }

    /**
     * Gets the backlog this story belongs to.
     * @return the backlog
     */
    public Backlog getBacklog()
    {
        return this.backlog;
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
     * Sets the backlog the story belongs to
     * @param backlog the backlog to set to
     */
    public void setBacklog(Backlog backlog)
    {
        if (backlog == null)
        {
            this.backlog = null;
        }
        else
        {
            this.backlog = backlog;
        }
    }

    /**
     * Sets the priority of the story
     * @param priority the priority
     */
    public void setPriority(Integer priority)
    {
        this.priority = priority;
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
    
    
//    // TODO write javadoc.
//    @Override
//    public int compareTo(Story compareStory)
//    {
//        String story1ShortName = this.getShortName();
//        String story2ShortName = compareStory.getShortName();
//        return story1ShortName.compareTo(story2ShortName);
//    }


    // TODO
    public static Comparator<Story> StoryPriorityComparator = new Comparator<Story>()
    {
        @Override
        public int compare(Story story1, Story story2)
        {
            Integer story1Priority = story1.getPriority();
            Integer story2Priority = story2.getPriority();

            return story2Priority.compareTo(story1Priority);
        }
    };

    // TODO
    public static Comparator<Story> StoryNameComparator = new Comparator<Story>()
    {
        @Override
        public int compare(Story story1, Story story2)
        {
            String story1ShortName = story1.getShortName();
            String story2ShortName = story2.getShortName();
            return story1ShortName.compareTo(story2ShortName);
        }
    };
    
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
     * @param newPriority The new priority
     */
    public void edit(String newShortName, String newLongName, String newDescription,
                     Project newProject, Integer newPriority, Backlog newBacklog)
    {
        Command relEdit = new StoryEditCommand(this, newShortName, newLongName,
                newDescription, newProject, newPriority, newBacklog);
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
        private String longName;
        private String description;
        private Project project;
        private Integer priority;
        private Backlog backlog;

        private String oldShortName;
        private String oldLongName;
        private String oldDescription;
        private Project oldProject;
        private Integer oldPriority;
        private Backlog oldBacklog;

        private StoryEditCommand(Story story, String newShortName, String newLongName,
            String newDescription, Project newProject, Integer newPriority, Backlog newBacklog)
        {
            this.story = story;
            this.shortName = newShortName;
            this.longName = newLongName;
            this.description = newDescription;
            this.project = newProject;
            this.priority = newPriority;
            this.backlog = newBacklog;

            this.oldShortName = story.shortName;
            this.oldLongName = story.longName;
            this.oldDescription = story.description;
            this.oldProject = story.project;
            this.oldPriority = story.priority;
            this.oldBacklog = story.backlog;
        }

        /**
         * Executes/Redoes the changes of the story edit
         */
        public void execute()
        {
            story.shortName = shortName;
            story.longName = longName;
            story.description = description;
            story.project = project;
            story.priority = priority;
            story.backlog = backlog;
            Collections.sort(project.getStories(), Story.StoryNameComparator);
            Collections.sort(backlog.getStories(), Story.StoryPriorityComparator);
        }

        /**
         * Undoes the changes of the story edit
         */
        public void undo()
        {
            story.shortName = oldShortName;
            story.longName = oldLongName;
            story.description = oldDescription;
            story.project = oldProject;
            story.priority = oldPriority;
            story.backlog = oldBacklog;
            Collections.sort(project.getStories(), Story.StoryNameComparator);
            Collections.sort(backlog.getStories(), Story.StoryPriorityComparator);
        }
    }

    /**
     * A class for implementing story deletion in the Command undo/redo structure.
     */
    private class DeleteStoryCommand implements Command
    {
        private Story story;
        private Project proj;

        /**
         * Contructor for a story deletion command.
         * @param story The story to delete
         */
        DeleteStoryCommand(Story story)
        {
            this.story = story;
            this.proj = story.getProject();
        }

        /**
         * Executes the deletion of a story.
         */
        public void execute()
        {
            System.out.println("Exec Story Delete");
            proj.getStories().remove(story);
            //release.setProject(null);
        }

        /**
         * Undoes the deletion of a story.
         */
        public void undo()
        {
            System.out.println("Undone Story Delete");
            proj.getStories().add(story);
            //release.setProject(proj);
        }
    }
}
