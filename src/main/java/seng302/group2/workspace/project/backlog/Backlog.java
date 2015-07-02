package seng302.group2.workspace.project.backlog;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.apache.commons.lang.builder.EqualsBuilder;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.sceneswitch.switchStrategies.workspace.project.BacklogInformationSwitchStrategy;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.story.Story;

import java.io.Serializable;
import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by cvs20 on 19/05/15.
 */
public class Backlog extends TreeViewItem implements Serializable, Comparable<Backlog> {
    private String shortName;
    private String longName;
    private String description;
    private Person productOwner;
    private transient ObservableList<Story> stories = observableArrayList();
    private List<Story> serializableStories = new ArrayList<>();
    private Project project;
    private String scale = "Fibonacci";


    /**
     * Basic Backlog constructor
     */
    public Backlog() {
        this.shortName = "Untitled Backlog";
        this.longName = "Untitled Backlog";
        this.description = "";
        this.productOwner = null;
        this.project = null;

        setInformationSwitchStrategy(new BacklogInformationSwitchStrategy());
    }

    @Override
    public Set<TreeViewItem> getItemsSet() {
        Set<TreeViewItem> items = new HashSet<>();
        items.addAll(stories);
        return items;
    }


    /**
     * Backlog Constructor with all fields
     *
     * @param shortName    short name to identify the story
     * @param longName     long name
     * @param description  description
     * @param productOwner product owner of the backlog
     * @param project      the project the backlog belongs to
     */
    public Backlog(String shortName, String longName, String description,
                   Person productOwner, Project project, String scale) {
        this.shortName = shortName;
        this.longName = longName;
        this.description = description;
        this.productOwner = productOwner;
        this.project = project;
        this.scale = scale;

        setInformationSwitchStrategy(new BacklogInformationSwitchStrategy());
    }


    /**
     * Adds listeners to the backlog stories for sorting
     */
    public void addListeners() {
        stories.addListener((ListChangeListener<Story>) change ->
            {
                if (change.next() && !change.wasPermutated()) {
                    Collections.sort(stories, Story.StoryPriorityComparator);
                }
            });
    }


    // <editor-fold defaultstate="collapsed" desc="Getters">

    /**
     * Gets the short name of the Backlog
     *
     * @return the short name
     */
    public String getShortName() {
        return this.shortName;
    }

    /**
     * Sets the short name of the Backlog
     *
     * @param shortName short name to be set
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * Gets the long name of the Backlog
     *
     * @return the long name
     */
    public String getLongName() {
        return this.longName;
    }

    /**
     * Sets the long name of the Backlog
     *
     * @param longName long name to be set
     */
    public void setLongName(String longName) {
        this.longName = longName;
    }

    /**
     * Gets the description of the Backlog
     *
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets the serializable stories of a backlog
     *
     * @return the serializable stories
     */

    /**
     * Sets the description of the Backlog
     *
     * @param description description to be set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the product owner of the Backlog
     *
     * @return productOwner
     */
    public Person getProductOwner() {
        return this.productOwner;
    }

    /**
     * Gets the estimation scale used in the Backlog
     *
     * @return Estimation scale name
     */
    public String getScale() {
        return this.scale;
    }

    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Setters">

    /**
     * Sets the product owner of the Backlog
     *
     * @param productOwner description to be set
     */
    public void setProductOwner(Person productOwner) {
        this.productOwner = productOwner;
    }

    /**
     * Gets the project the Backlog is assigned to
     *
     * @return The description of the team
     */
    public Project getProject() {
        return this.project;
    }

    /**
     * Sets the Backlog's project
     *
     * @param project the project the backlog has been added to
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * Gets the backlogs list of stories
     *
     * @return The ObservableList of stories
     */
    public ObservableList<Story> getStories() {
        this.serializableStories.clear();
        for (Object item : this.stories) {
            this.serializableStories.add((Story) item);
        }
        return this.stories;
    }

    public List<Story> getSerializableStories() {
        return serializableStories;
    }

    /**
     * Adds a story to the backlogs list of stories
     *
     * @param story Story to add
     */
    public void add(Story story) {
        Command addStory = new AddStoryCommand(this, story);
        Global.commandManager.executeCommand(addStory);
    }

    //</editor-fold>


    /**
     * Prepares the backlog to be serialized.
     */
    public void prepSerialization() {
        serializableStories.clear();
        for (Story story : stories) {
            this.serializableStories.add(story);
        }
    }


    /**
     * Deserialization post-processing.
     */
    public void postDeserialization() {
        stories.clear();
        for (Story story : serializableStories) {
            this.stories.add(story);
        }

        Collections.sort(this.stories, Story.StoryPriorityComparator);
    }


    /**
     * Gets the children of the TreeViewItem
     *
     * @return The items of the TreeViewItem
     */
    @Override
    public ObservableList getChildren() {
        return this.getStories();
    }


    /**
     * A comparator for backlogs based on short names
     *
     * @param backlog The backlog to compare to
     * @return The result of the comparison of the short name strings
     */
    @Override
    public int compareTo(Backlog backlog) {
        String story1ShortName = this.getShortName();
        String story2ShortName = backlog.getShortName();
        return story1ShortName.compareTo(story2ShortName);
    }

    /**
     * An overridden version for the String representation of a Backlog
     *
     * @return The short name of the Backlog
     */
    @Override
    public String toString() {
        return this.shortName;
    }

    @Override
    public boolean equivalentTo(Object object) {
        if (!(object instanceof Backlog)) {
            return false;
        }
        if (object == this) {
            return true;
        }

        Backlog backlog = (Backlog)object;
        return new EqualsBuilder()
                .append(shortName, backlog.shortName)
                .append(longName, backlog.longName)
                .append(description, backlog.description)
                .append(productOwner, backlog.productOwner)
                .append(project, backlog.project)
                .append(scale, backlog.scale)
                .isEquals();
    }


    /**
     * Creates a Backlog edit command and executes it with the Global Command Manager, updating
     * the Backlog with the new parameter values.
     *
     * @param newShortName    The new short name
     * @param newLongName     The new long name
     * @param newDescription  The new description
     * @param newProductOwner The new product owner
     * @param newProject      The new project
     * @param newScale        The new estimation scale
     */
    public void edit(String newShortName, String newLongName, String newDescription,
                     Person newProductOwner, Project newProject, String newScale, Collection<Story> newStories) {
        Command relEdit = new BacklogEditCommand(this, newShortName, newLongName, newDescription,
                newProductOwner, newProject, newScale, newStories);
        Global.commandManager.executeCommand(relEdit);
    }

    /**
     * Deletes a backlog from the given project.
     */
    public void deleteBacklog() {
        Command command = new DeleteBacklogCommand(this);
        Global.commandManager.executeCommand(command);
    }

    /**
     * A command class that allows the executing and undoing of backlog edits
     */
    private class BacklogEditCommand implements Command {
        private Backlog backlog;

        private String shortName;
        private String longName;
        private String description;
        private Person productOwner;
        private Project project;
        private String scale;
        private Collection<Story> stories = new HashSet<>();

        private String oldShortName;
        private String oldLongName;
        private String oldDescription;
        private Person oldProductOwner;
        private Project oldProject;
        private String oldScale;
        private Collection<Story> oldStories = new HashSet<>();

        private BacklogEditCommand(Backlog backlog, String newShortName, String newLongName,
                                   String newDescription, Person newProductOwner,
                                   Project newProject, String newScale, Collection<Story> newStories) {
            this.backlog = backlog;

            this.shortName = newShortName;
            this.longName = newLongName;
            this.description = newDescription;
            this.productOwner = newProductOwner;
            this.project = newProject;
            this.scale = newScale;
            this.stories.addAll(newStories);

            this.oldShortName = backlog.shortName;
            this.oldLongName = backlog.longName;
            this.oldDescription = backlog.description;
            this.oldProductOwner = backlog.productOwner;
            this.oldProject = backlog.project;
            this.oldScale = backlog.scale;
            this.oldStories.addAll(backlog.stories);
        }

        /**
         * Executes/Redoes the changes of the backlog edit
         */
        public void execute() {
            backlog.shortName = shortName;
            backlog.longName = longName;
            backlog.description = description;
            backlog.productOwner = productOwner;
            backlog.project = project;
            backlog.scale = scale;

            backlog.stories.removeAll(oldStories);
            backlog.stories.addAll(stories);

            Set<Story> allStories = new HashSet<>();
            allStories.addAll(stories);
            allStories.addAll(oldStories);
            for (Story story : allStories) {
                if (!stories.contains(story)) {
                    // Not a story in the backlog
                    if (project != null) {
                        project.getUnallocatedStories().add(story);
                    }
                    story.setProject(project);
                    story.setBacklog(null);
                }
                else {
                    // In the backlog
                    if (project != null) {
                        project.getUnallocatedStories().remove(story);
                    }
                    story.setProject(project);
                    story.setBacklog(backlog);
                }
            }

            Collections.sort(backlog.stories, Story.StoryPriorityComparator);
        }

        /**
         * Undoes the changes of the backlog edit
         */
        public void undo() {
            backlog.shortName = oldShortName;
            backlog.longName = oldLongName;
            backlog.description = oldDescription;
            backlog.productOwner = oldProductOwner;
            backlog.project = oldProject;
            backlog.scale = oldScale;

            backlog.stories.removeAll(stories);
            backlog.stories.addAll(oldStories);

            Set<Story> allStories = new HashSet<>();
            allStories.addAll(stories);
            allStories.addAll(oldStories);
            for (Story story : allStories) {
                if (!oldStories.contains(story)) {
                    // Not a story in the backlog
                    if (oldProject != null) {
                        oldProject.getUnallocatedStories().add(story);
                    }
                    story.setProject(oldProject);
                    story.setBacklog(null);
                }
                else {
                    // In the backlog
                    if (oldProject != null) {
                        oldProject.getUnallocatedStories().remove(story);
                    }
                    story.setProject(oldProject);
                    story.setBacklog(backlog);
                }
            }

            Collections.sort(backlog.stories, Story.StoryPriorityComparator);
        }

        /**
         * Searches the stateObjects to find an equal model class to map to
         * @param stateObjects A set of objects to search through
         * @return If the item was successfully mapped
         */
        @Override // BL, PO, Proj, Stories?
        public boolean map(Set<TreeViewItem> stateObjects) {
            boolean mapped_bl = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(backlog)) {
                    this.backlog = (Backlog) item;
                    mapped_bl = true;
                }
            }
            boolean mapped_po = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(productOwner)) {
                    this.productOwner = (Person) item;
                    mapped_po = true;
                }
            }
            boolean mapped_old_po = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(oldProductOwner)) {
                    this.oldProductOwner = (Person) item;
                    mapped_old_po = true;
                }
            }
            boolean mapped_proj = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(project)) {
                    this.project = (Project) item;
                    mapped_proj = true;
                }
            }
            boolean mapped_old_proj = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(oldProject)) {
                    this.oldProject = (Project) item;
                    mapped_old_proj = true;
                }
            }


            // Story collections
            for (Story story : stories) {
                for (TreeViewItem item : stateObjects) {
                    if (item.equivalentTo(story)) {
                        stories.remove(story);
                        stories.add((Story)item);
                        break;
                    }
                }
            }

            for (Story story : oldStories) {
                for (TreeViewItem item : stateObjects) {
                    if (item.equivalentTo(story)) {
                        oldStories.remove(story);
                        oldStories.add((Story)item);
                        break;
                    }
                }
            }

            return mapped_po && mapped_bl && mapped_proj && mapped_old_proj && mapped_old_po;
        }
    }

    private class DeleteBacklogCommand implements Command {
        private Backlog backlog;
        private Project proj;

        DeleteBacklogCommand(Backlog backlog) {
            this.backlog = backlog;
            this.proj = backlog.getProject();
        }

        public void execute() {
            //System.out.println("Exec Backlog Delete");
            proj.getBacklogs().remove(backlog);
            backlog.setProject(null);
            //release.setProject(null);
        }

        public void undo() {
            //System.out.println("Undone Backlog Delete");
            proj.getBacklogs().add(backlog);
            backlog.setProject(proj);
            //release.setProject(proj);
        }

        @Override // BL, PO, Proj, Stories?
        public boolean map(Set<TreeViewItem> stateObjects) {
            boolean mapped_bl = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(backlog)) {
                    this.backlog = (Backlog) item;
                    mapped_bl = true;
                }
            }
            boolean mapped_proj = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(project)) {
                    this.proj = (Project) item;
                    mapped_proj = true;
                }
            }
            return mapped_bl && mapped_proj;
        }
    }

    private class AddStoryCommand implements Command {
        private Project proj;
        private Backlog backlog;
        private Story story;

        AddStoryCommand(Backlog backlog, Story story) {
            //this.proj = proj;
            this.backlog = backlog;
            this.story = story;
        }

        public void execute() {
            /*if (proj != null)
            {
                proj.getBacklogs().add(backlog);  // Why? This is add story to bl, not bl to proj
            }*/
            backlog.stories.add(story);
            if (backlog.getProject() != null) {
                backlog.getProject().getUnallocatedStories().remove(story);
            }
        }

        public void undo() {
            /*if (proj != null)
            {
                proj.getBacklogs().remove(backlog);
            }*/
            backlog.stories.remove(story);
            if (backlog.getProject() != null) {
                backlog.getProject().getUnallocatedStories().add(story);
            }
        }

        @Override // BL, Proj, Story
        public boolean map(Set<TreeViewItem> stateObjects) {
            boolean mapped_bl = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(backlog)) {
                    this.backlog = (Backlog) item;
                    mapped_bl = true;
                }
            }
            boolean mapped_proj = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(project)) {
                    this.proj = (Project) item;
                    mapped_proj = true;
                }
            }
            boolean mapped_story = false;
            for (TreeViewItem item : stateObjects) {
                if (item.equivalentTo(story)) {
                    this.story = (Story) item;
                    mapped_story = true;
                }
            }
            return mapped_bl && mapped_proj && mapped_story;
        }
    }
}




