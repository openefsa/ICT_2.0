package ui_main_menu;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

/**
 * Main menu of ICT 2.0. The menu bar is subdivided in different sections and
 * allows to perform several editing and non editing operations
 * 
 * @author shahaal
 * 
 */
public class MainMenu {

	// the shell which contains the menu
	private Shell shell;

	private Menu mainMenu; // the main menu bar
	private FileMenu file; // file menu
	private ToolsMenu tools; // tools menu
	private AboutMenu about; // about menu
	private HelpMenu help;
	private MenuListener fileListener;
	private MenuListener toolsListener;
	private MenuListener aboutListener;
	private MenuListener helpListener;

	/**
	 * Initialise the main menu
	 * 
	 * @param shell shell on which creating the menu
	 */
	public MainMenu(Shell shell) {
		this.shell = shell;
	}

	/**
	 * Create the main menu
	 * 
	 * @param Menu
	 * @return
	 */
	public Menu createMainMenu() {

		if (shell.isDisposed())
			return null;

		// Add a menu bar
		mainMenu = new Menu(shell, SWT.BAR);
		
		// File menu
		file = new FileMenu(this, mainMenu);
		file.setListener(fileListener);

		// Tools menu
		tools = new ToolsMenu(mainMenu);
		tools.setListener(toolsListener);

		// About menu
		about = new AboutMenu(mainMenu);
		about.setListener(aboutListener);

		// Help menu
		help = new HelpMenu(mainMenu);
		help.setListener(helpListener);
		
		return mainMenu;
	}

	public void dispose() {
		mainMenu.dispose();
	}

	/**
	 * Refresh all the menu
	 */
	public void refresh() {

		shell.getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				// refresh the state of elements
				
			}
		});
	}

	/**
	 * Get the shell related to the main menu
	 * 
	 * @return
	 */
	public Shell getShell() {
		return shell;
	}

	/**
	 * Listener to observe file menu items
	 * 
	 * @param fileListener
	 */
	public void setFileListener(MenuListener fileListener) {
		this.fileListener = fileListener;
	}

	/**
	 * Listener to observe tools menu item
	 * 
	 * @param toolsListener
	 */
	public void setToolsListener(MenuListener toolsListener) {
		this.toolsListener = toolsListener;
	}
	
	/**
	 * Listener to observe about menu item
	 * 
	 * @param toolsListener
	 */
	public void setAboutListener(MenuListener aboutListener) {
		this.aboutListener = aboutListener;
	}
	
	/**
	 * Listener to observe tools menu item
	 * 
	 * @param toolsListener
	 */
	public void setHelpListener(MenuListener helpListener) {
		this.helpListener = helpListener;
	}
}
