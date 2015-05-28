/*
 * SENG302 Group 2
 */
package seng302.group2.workspace;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.dialog.CustomDialog;
import seng302.group2.scenes.listdisplay.Category;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.util.revert.Revert;
import seng302.group2.util.serialization.SerialBuilder;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.role.Role;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.team.Team;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Basic workspace class that acts as the root object for Sahara and represents a real-world
 * company or work group space.
 * @author Jordane Lew (jml168)
 */
@SuppressWarnings("deprecation")
public class Workspace extends TreeViewItem implements Serializable
{
    private String shortName;
    private String longName;
    private String description;
    private String lastSaveLocation = null;

    private transient boolean hasUnsavedChanges = true;
    private static Gson gson = SerialBuilder.getBuilder();

    // Workspace elements
    private transient ObservableList<Team> teams = observableArrayList();
    private List<Team> serializableTeams = new ArrayList<>();
    private transient ObservableList<Person> people = observableArrayList();
    private List<Person> serializablePeople = new ArrayList<>();
    private transient ObservableList<Skill> skills = observableArrayList();
    private List<Skill> serializableSkills = new ArrayList<>();
    private transient ObservableList<Project> projects = observableArrayList();
    private List<Project> serializableProjects = new ArrayList<>();
    private transient ObservableList<Role> roles = observableArrayList();
    private List<Role> serializableRoles = new ArrayList();


    /**
     * Enumerable save and load statuses.
     */
    public enum SaveLoadResult
    {
        SUCCESS,
        NULLWORKSPACE,
        IOEXCEPTION,
        NOFILESELECTED,     // Cancelled in save
        FILENOTFOUND        // File doesn't exist when opening
    }

    /**
     * Basic Workspace constructor.
     */
    public Workspace()
    {
        super("Workspace");
        this.shortName = "Untitled Workspace";
        this.longName = "Untitled Workspace";
        this.description = "A blank workspace.";
        this.serializablePeople = new ArrayList<>();
        this.serializableSkills = new ArrayList<>();
        this.serializableTeams = new ArrayList<>();
        this.serializableRoles = new ArrayList<>();

        this.createDefaultElements();

        addListeners();

        updateDefaultRevert();
    }


    /**
     * Basic workspace constructor with input.
     *
     * @param shortName   A unique short name to identify the Workspace
     * @param fullName    The full Workspace name
     * @param description A description of the Workspace
     */
    public Workspace(String shortName, String fullName, String description)
    {
        super(shortName);
        this.shortName = shortName;
        this.longName = fullName;
        this.description = description;

        this.createDefaultElements();

        addListeners();

        updateDefaultRevert();
    }


    private void updateDefaultRevert()
    {
        prepSerialization(this);
        String json = gson.toJson(this);
        Revert.updateRevertState(json);
    }


    /**
     * Adds the default elements, such as the product owner and scrum master skills and roles, as
     * well as the unassigned team, to the project.
     */
    private void createDefaultElements()
    {
        Team unassignedTeam = Team.createUnassignedTeam();
        this.addWithoutUndo(unassignedTeam);

        Skill productOwnerSkill = new Skill("Product Owner",
                "Knows how to work as a Teams Product Owner");
        this.getSkills().add(productOwnerSkill);

        ObservableList<Skill> poSkillList = observableArrayList();
        poSkillList.add(productOwnerSkill);

        Skill scrumMasterSkill = new Skill("Scrum Master", "Can be Scrum Master for a Team");
        this.getSkills().add(scrumMasterSkill);

        ObservableList<Skill> smSkillList = observableArrayList();
        smSkillList.add(scrumMasterSkill);

        Role scrumMaster = new Role("Scrum Master", Role.RoleType.SCRUM_MASTER,
                "The Scrum Master for a Team", smSkillList);
        this.getRoles().add(scrumMaster);

        Role productOwner = new Role(
                "Product Owner", Role.RoleType.PRODUCT_OWNER, "The Product Owner for a Team",
                poSkillList);
        this.getRoles().add(productOwner);

        Role developmentTeamMember = new Role(
                "Developer", Role.RoleType.DEVELOPMENT_TEAM_MEMBER,
                "A member of the Dev Team");
        this.getRoles().add(developmentTeamMember);
    }


    /**
     * Adds listeners to the workspace's property lists, primarily for sorting.
     */
    public void addListeners()
    {
        people.addListener((ListChangeListener<Person>) change ->
            {
                if (change.next() && !change.wasPermutated())
                {
                    Collections.sort(people);
                }
            });

        teams.addListener((ListChangeListener<Team>) change ->
            {
                if (change.next() && !change.wasPermutated())
                {
                    Collections.sort(teams);
                }
            });

        projects.addListener((ListChangeListener<Project>) change ->
            {
                if (change.next() && !change.wasPermutated())
                {
                    Collections.sort(projects);
                }
            });

        skills.addListener((ListChangeListener<Skill>) change ->
            {
                if (change.next() && !change.wasPermutated())
                {
                    Collections.sort(skills);
                }
            });
    }



    // <editor-fold defaultstate="collapsed" desc="Getters">

    /**
     * Gets if the workspace has unsaved changes.
     *
     * @return true or false depending if the workspace has unsaved changes
     */
    public boolean getHasUnsavedChanges()
    {
        return hasUnsavedChanges;
    }


    /**
     * Gets the workspace's short name.
     *
     * @return The short name of the workspace
     */
    public String getShortName()
    {
        return this.shortName;
    }


    /**
     * Gets the workspace's long name.
     *
     * @return The long name of the workspace
     */
    public String getLongName()
    {
        return this.longName;
    }


    /**
     * Gets the workspace's description.
     *
     * @return The description of the workspace
     */
    public String getDescription()
    {
        return this.description;
    }


    /**
     * Gets the workspace's list of projects.
     *
     * @return The projects associated with the workspace
     */
    public ObservableList<Project> getProjects()
    {
        return this.projects;
    }


    /**
     * Gets the workspace's list of Persons.
     *
     * @return The people associated with the workspace
     */
    public ObservableList<Person> getPeople()
    {
        return this.people;
    }

    /**
     * Gets the workspace's list of Skills.
     *
     * @return The skills associated with a workspace
     */
    public ObservableList<Skill> getSkills()
    {
        return this.skills;
    }

    /**
     * Gets the workspace's list of Teams.
     *
     * @return The teams associated with a workspace
     */
    public ObservableList<Team> getTeams()
    {
        return this.teams;
    }

    /**
     * Gets the workspace's list of Roles
     *
     * @return the Roles associated with a workspace
     */
    public ObservableList<Role> getRoles()
    {
        return this.roles;
    }

    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="Setters">

    /**
     * Marks the workspace as not having unsaved changes.
     */
    public void setUnchanged()
    {
        this.hasUnsavedChanges = false;
    }


    /**
     * Marks the workspace as having unsaved changes.
     */
    public void setChanged()
    {
        this.hasUnsavedChanges = true;
    }


    /**
     * Sets the workspace's short name.
     *
     * @param shortName The new short name for the workspace
     */
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }


    /**
     * Sets the workspace's long name.
     *
     * @param longName The new long name for the workspace
     */
    public void setLongName(String longName)
    {
        this.longName = longName;
    }


    /**
     * Sets the workspace's description.
     *
     * @param description The new description for the workspace
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    //</editor-fold>


    /**
     * Saves the current workspace as a file specified by the user.
     *
     * @param workspace The workspace to save
     * @param saveAs    If acting as save as
     * @return The corresponding SaveLoadResult status of the process
     */
    public static SaveLoadResult saveWorkspace(Workspace workspace, boolean saveAs)
    {
        // If there is no current workspace open, display a dialog and skip saving
        if (workspace == null)
        {
            Dialogs.create()
                    .title("No open workspace")
                    .message("There is currently no workspace open to save")
                    .showWarning();

            return SaveLoadResult.NULLWORKSPACE;
        }

        workspace = Workspace.prepSerialization(workspace);

        if (workspace.lastSaveLocation != null)
        {
            File f = new File(workspace.lastSaveLocation);
            if (!f.exists())
            {
                CustomDialog.showDialog("File doesn't exist", "The file no longer exists. Please"
                        + " choose another save location", Alert.AlertType.ERROR);
                saveAs = true;
            }
        }

        if (saveAs || workspace.lastSaveLocation == null || workspace.lastSaveLocation.equals(""))
        {
            // Prime a FileChooser
            FileChooser fileChooser = new FileChooser();
            if (Global.lastSaveLocation != null && Global.lastSaveLocation != "")
            {
                //System.out.println("last save dir: " + Global.lastSaveLocation);
                fileChooser.setInitialDirectory(new File(Global.lastSaveLocation));
            }
            fileChooser.setInitialFileName(workspace.shortName);
            fileChooser.setTitle("Save Workspace");
            fileChooser.getExtensionFilters().addAll(
                    new ExtensionFilter("Workspace Files", "*.proj")
            );

            // Open the FileChooser to choose the save location of the workspace
            File selectedFile = null;
            try
            {
                selectedFile = fileChooser.showSaveDialog(new Stage());
            }
            catch (IllegalArgumentException e)
            {
                // The file directory is invalid, try again with 'root'
                System.out.println("Bad directory");
                fileChooser.setInitialDirectory(new File("/"));
                selectedFile = fileChooser.showSaveDialog(new Stage());
            }

            if (selectedFile == null)
            {
                return SaveLoadResult.FILENOTFOUND;  // Most likely user cancellation
            }
            workspace.lastSaveLocation = selectedFile.toString();

            // Ensure the extension is .proj (Linux issue)
            if (!workspace.lastSaveLocation.endsWith(".proj"))
            {
                workspace.lastSaveLocation = workspace.lastSaveLocation + ".proj";
            }
        }
        
        /* GSON SERIALIZATION */
        try (Writer writer = new FileWriter(workspace.lastSaveLocation))
        {
            gson.toJson(workspace, writer);
            writer.close();

            String json = gson.toJson(workspace);
            //System.out.println(json);
            Revert.updateRevertState(json);

            Global.setCurrentWorkspaceUnchanged();
            Global.lastSaveLocation = Paths.get(workspace.lastSaveLocation).getParent().toString();

            //TESTING
            //System.out.println("WORKSPACE LOCATION: " + workspace.lastSaveLocation);
            //System.out.println("GLOBAL LOCATION: " + Global.lastSaveLocation);


            App.refreshMainScene();
            Global.commandManager.trackSave();

            return SaveLoadResult.SUCCESS;
        }
        catch (IOException e)
        {
            Dialogs.create()
                    .title("Error Saving")
                    .message("An error occurred while trying to save the file")
                    .showException(e);
            return SaveLoadResult.IOEXCEPTION;
        }

    }


    /**
     * Loads a workspace specified by the user into Global.currentWorkspace.
     *
     * @return The corresponding SaveLoadResult status of the process
     */
    public static SaveLoadResult loadWorkspace()
    {
        // Prime a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Workspace");
        if (Global.lastSaveLocation != null && Global.lastSaveLocation != "")
        {
            //System.out.println("last save dir: " + Global.lastSaveLocation);
            fileChooser.setInitialDirectory(new File(Global.lastSaveLocation));
        }
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Workspace Files", "*.proj")
        );

        // Open the FileChooser and try saving the Workspace as the user specifies
        File selectedFile = null;
        try
        {
            selectedFile = fileChooser.showOpenDialog(new Stage());
        }
        catch (IllegalArgumentException e)
        {
            // The file directory is invalid, try again with 'root'
            System.out.println("Bad directory");
            fileChooser.setInitialDirectory(new File("/"));
            selectedFile = fileChooser.showOpenDialog(new Stage());
        }

        if (selectedFile != null)
        {
            /* GSON DESERIALIZATION */
            try (Reader reader = new FileReader(selectedFile))
            {
                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuilder json = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    json.append(line);
                }
                //System.out.println(json);

                Global.currentWorkspace = gson.fromJson(json.toString(), Workspace.class);

                Revert.updateRevertState(json.toString());

                reader.close();
            }
            catch (FileNotFoundException e)
            {
                Dialogs.create()
                        .title("File Not Found")
                        .message("The specified file could not be found.")
                        .showWarning();
                return SaveLoadResult.FILENOTFOUND;
            }
            catch (JsonSyntaxException e)
            {
                e.printStackTrace();
                Dialogs.create()
                        .title("File outdated")
                        .message("The specified file was created with a deprecated version of "
                                + "Sahara.\nThe file cannot be loaded.")
                        .showError();
                return SaveLoadResult.FILENOTFOUND;
            }
            catch (IOException e)
            {
                Dialogs.create()
                        .title("Error Loading")
                        .message("An error occurred while trying to load the file.")
                        .showException(e);
                return SaveLoadResult.IOEXCEPTION;
            }

            Global.currentWorkspace.lastSaveLocation = selectedFile.toString();
            Global.lastSaveLocation = Paths.get(Global.currentWorkspace.lastSaveLocation)
                    .getParent().toString();

            Workspace.postDeserialization(Global.currentWorkspace);

            App.refreshMainScene();

            Global.commandManager.clear();
            Global.commandManager.trackSave();

            MainScene.treeView.selectItem(Global.currentWorkspace);

            return SaveLoadResult.SUCCESS;
        }
        else
        {
            return SaveLoadResult.FILENOTFOUND;  // Was null, probably cancelled action?
        }
    }


    /**
     * Adds a Person to the Workspace's list of Persons.
     *
     * @param person The person to add
     */
    public void add(Person person)
    {
        Command command = new AddPersonCommand(this, person, Global.getUnassignedTeam());
        Global.commandManager.executeCommand(command);
    }


    /**
     * Adds a Person to the Workspace's list of Persons without an undoable command
     * @param person the person to add
     */
    public void addWithoutUndo(Person person)
    {
        this.people.add(person);
    }



    /**
     * Adds a Skill to the Workspace's list of Skills.
     *
     * @param skill The skill to add
     */
    public void add(Skill skill)
    {
        if (skill.toString().equals("Product Owner")
                || skill.toString().equals("Scrum Master"))
        {
            this.skills.add(skill);
            return;
        }

        Command addSkill = new AddSkillCommand(Global.currentWorkspace, skill);
        Global.commandManager.executeCommand(addSkill);
    }


    /**
     * Adds a Team to the Workspace's list of Teams.
     *
     * @param team The team to add
     */
    public void add(Team team)
    {
        Command command = new AddTeamCommand(this, team);
        Global.commandManager.executeCommand(command);
    }


    /**
     * Adds a Team to the Workspace's list of Teams without an undoable command
     *
     * @param team The team to add
     */
    public void addWithoutUndo(Team team)
    {
        this.teams.add(team);
    }


    /**
     * Adds the given project to the workspace
     * @param proj The project to add to the workspace
     */
    public void add(Project proj)
    {
        Command command = new AddProjectCommand(this, proj);
        Global.commandManager.executeCommand(command);
    }

    /**
     * Adds the project to the given workspace without creating an undoable command
     * @param proj The project to add to the workspace
     */
    public void addWithoutUndo(Project proj)
    {
        this.getProjects().add(proj);
    }


    /**
     * Removes a Team from the Workspace's list of Teams.
     *
     * @param team The team to remove
     */
    /*public void remove(Team team)
    {
        if (team.isUnassignedTeam())
        {
            return;
        }

        // Add the undo action to the stack
        Global.undoRedoMan.add(new UndoableItem(
                team,
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.TEAM_DEL, null),
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.TEAM_DEL, null)
        ));

        this.teams.remove(team);
    }*/


    /**
     * Removes a Team from the Workspace's list of Teams without an undoable command
     *
     * @param team The team to remove
     */
    public void removeWithoutUndo(Team team)
    {
        if (team.isUnassignedTeam())
        {
            return;
        }

        this.teams.remove(team);
    }


    /**
     * Removes a Project from the Workspace's list of Projects.
     *
     * @param project The project to remove
     */
    /*public void remove(Project project)
    {
        // Add the undo action to the stack
        Global.undoRedoMan.add(new UndoableItem(
                project,
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_DEL, null),
                new UndoRedoAction(UndoRedoPerformer.UndoRedoProperty.PERSON_DEL, null)
        ));

        this.projects.remove(project);
    }*/


    /**
     * Removes a Project from the Workspace's list of Projects without an undoable command
     * @param project The project to remove
     */
    public void removeWithoutUndo(Project project)
    {
        this.projects.remove(project);
    }


    /**
     * Adds a Role to the Workspace's list of Roles.
     *
     * @param role The role to add
     */
    public void add(Role role)
    {
        if (role.getType() == Role.RoleType.SCRUM_MASTER
                || role.getType() == Role.RoleType.PRODUCT_OWNER
                || role.getType() == Role.RoleType.DEVELOPMENT_TEAM_MEMBER)
        {
            this.roles.add(role);
            return;
        }

        this.roles.add(role);
    }


    /**
     * Removes a Role from the Workspace's list of Roles.
     * @param role The role to remove
     */
    public void remove(Role role)
    {
        this.roles.remove(role);
    }


    /**
     * Gets a list of categories of the workspace based on the workspace's lists.
     *
     * @return A list of categories of the workspace
     */
    public ObservableList<TreeViewItem> getCategories()
    {
        // Prime the list
        ObservableList<TreeViewItem> root = observableArrayList();

        // Make the categories
        Category projectCategory = new Category("Projects");
        Category teamsCategory = new Category("Teams");
        Category peopleCategory = new Category("People");
        Category rolesCategory = new Category("Roles");
        Category skillCategory = new Category("Skills");

        // Add the categories
        root.add(projectCategory);
        root.add(teamsCategory);
        root.add(peopleCategory);
        root.add(rolesCategory);
        root.add(skillCategory);

        return root;
    }


    /**
     * Perform pre-serialization steps
     * 1) Transform ObservableLists into ArrayLists for serialization.
     *
     * @param workspace The workspace for intended serialization
     * @return A serializable version of the given workspace
     */
    public static Workspace prepSerialization(Workspace workspace)
    {
        workspace.serializablePeople.clear();
        for (Person item : workspace.people)
        {
            item.prepSerialization();
            workspace.serializablePeople.add(item);
        }

        workspace.serializableProjects.clear();
        for (Project item : workspace.projects)
        {
            item.prepSerialization();
            workspace.serializableProjects.add(item);
        }

        workspace.serializableSkills.clear();
        for (Skill item : workspace.skills)
        {
            workspace.serializableSkills.add(item);
        }

        workspace.serializableTeams.clear();
        for (Team item : workspace.teams)
        {
            item.prepSerialization();
            workspace.serializableTeams.add(item);
        }

        workspace.serializableRoles.clear();
        for (Role item : workspace.roles)
        {
            item.prepSerialization();
            workspace.serializableRoles.add(item);
        }

        return workspace;
    }


    /**
     * Perform post-deserialization steps (performs on Global.currentWorkspace for now).
     * 1) Transform ArrayLists back into ObservableLists
     * 2) Add listeners back to the workspace (and recursively project..etc.)
     * @param workspace The workspace to prepare
     */
    public static void postDeserialization(Workspace workspace)
    {
        workspace.people = observableArrayList();
        for (Person item : workspace.serializablePeople)
        {
            item.postSerialization();
            workspace.people.add(item);
        }

        for (Project item : workspace.serializableProjects)
        {
            item.postSerialization();
            workspace.projects.add(item);
        }

        workspace.skills = observableArrayList();
        for (Skill item : workspace.serializableSkills)
        {
            workspace.skills.add(item);
        }

        workspace.teams = observableArrayList();
        for (Team item : workspace.serializableTeams)
        {
            item.postSerialization();
            workspace.teams.add(item);
        }

        workspace.roles = observableArrayList();
        for (Role item : workspace.serializableRoles)
        {
            item.postSerialization();
            workspace.roles.add(item);
        }

        // Unset saved changes flag, we just opened the workspace.
        workspace.hasUnsavedChanges = false;

        // Adds listeners to the workspace (mainly for sorting lists)
        workspace.addListeners();
        for (Project proj : workspace.getProjects())
        {
            proj.addListeners();
        }
    }


    /**
     * An overridden version for the String representation of a Workspace.
     *
     * @return The short name of the Workspace
     */
    @Override
    public String toString()
    {
        return this.shortName;
    }


    /**
     * Gets the children (categories) of the workspace.
     *
     * @return the children (categories) of the workspace
     */
    @Override
    public ObservableList<TreeViewItem> getChildren()
    {
        return getCategories();
    }


    /**
     * Creates a Workspace edit command and executes it with the Global Command Manager, updating
     * the workspace with the new parameter values.
     * @param newShortName The new short name
     * @param newLongName The new long name
     * @param newDescription The new description
     */
    public void edit(String newShortName, String newLongName, String newDescription)
    {
        Command wsedit = new WorkspaceEditCommand(this, newShortName, newLongName, newDescription);
        Global.commandManager.executeCommand(wsedit);
    }


    /**
     * A command class that allows the executing and undoing of workspace edits
     */
    private class WorkspaceEditCommand implements Command
    {
        private Workspace ws;
        private String shortName;
        private String longName;
        private String description;
        private String oldShortName;
        private String oldLongName;
        private String oldDescription;

        private WorkspaceEditCommand(Workspace ws, String newShortName, String newLongName,
                                     String newDescription)
        {
            this.ws = ws;
            this.shortName = newShortName;
            this.longName = newLongName;
            this.description = newDescription;
            this.oldShortName = ws.shortName;
            this.oldLongName = ws.longName;
            this.oldDescription = ws.description;
        }

        /**
         * Executes/Redoes the changes of the workspace edit
         */
        public void execute()
        {
            ws.shortName = shortName;
            ws.longName = longName;
            ws.description = description;
            App.refreshWindowTitle();  // The project name may have changed
        }

        /**
         * Undoes the changes of the workspace edit
         */
        public void undo()
        {
            ws.shortName = oldShortName;
            ws.longName = oldLongName;
            ws.description = oldDescription;
            App.refreshWindowTitle();  // The project name may have changed
        }
    }


    private class AddProjectCommand implements Command
    {
        private Project proj;
        private Workspace ws;

        AddProjectCommand(Workspace ws, Project proj)
        {
            this.proj = proj;
            this.ws = ws;
        }

        public void execute()
        {
            ws.getProjects().add(proj);
        }

        public void undo()
        {
            ws.getProjects().remove(proj);
        }
    }
    
    
    private class AddTeamCommand implements Command
    {
        private Team team;
        private Workspace ws;

        AddTeamCommand(Workspace ws, Team team)
        {
            this.team = team;
            this.ws = ws;
        }

        public void execute()
        {
            ws.getTeams().add(team);
        }

        public void undo()
        {
            ws.getTeams().remove(team);
        }
    }


    private class AddPersonCommand implements Command
    {
        private Person person;
        private Workspace ws;
        private Team unassingedTeam;

        AddPersonCommand(Workspace ws, Person person, Team unassingedTeam)
        {
            this.person = person;
            this.ws = ws;
            this.unassingedTeam = unassingedTeam;
        }

        public void execute()
        {
            ws.getPeople().add(person);
            unassingedTeam.getPeople().add(person);
        }

        public void undo()
        {
            unassingedTeam.getPeople().remove(person);
            ws.getPeople().remove(person);
            if (Global.getUnassignedTeam() != null)
            {
                Global.getUnassignedTeam().getPeople().remove(person);
            }
        }
    }

    private class AddSkillCommand implements Command
    {
        private Skill skill;
        private Workspace ws;

        AddSkillCommand(Workspace ws, Skill skill)
        {
            this.skill = skill;
            this.ws = ws;
        }

        public void execute()
        {
            ws.getSkills().add(skill);
        }

        public void undo()
        {
            ws.getSkills().remove(skill);
        }
    }
}
