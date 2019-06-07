package ui_main_menu;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import messages.Messages;

/**
 * File menu for the main menu.
 * 
 * @author shahaal
 *
 */
public class FileMenu {

	// codes to identify the menu items (used for listeners)
	public static final int IMPORT_CODES_MI = 0;
	public static final int EXIT_MI = 1;

	private MenuListener listener;

	private MainMenu mainMenu;
	/**
	 * Set the listener to the file menu
	 * 
	 * @param listener
	 */
	public void setListener(MenuListener listener) {
		this.listener = listener;
	}

	/**
	 * Initialise the main file menu passing the main menu which contains it and the
	 * menu in which we want to create the file menu
	 * 
	 * @param mainMenu
	 * @param menu
	 */
	public FileMenu(MainMenu mainMenu, Menu menu) {
		this.mainMenu = mainMenu;
		create(menu);
	}

	/**
	 * Create a file menu
	 * 
	 * @param menu
	 */
	public void create(Menu menu) {

		// create FILE Menu and its sub menu items
		Menu fileMenu = new Menu(menu);

		MenuItem fileItem = new MenuItem(menu, SWT.CASCADE);
		fileItem.setText("File");
		fileItem.setMenu(fileMenu);

		// create cat
		addImportCodesMI(fileMenu);

		// add separator
		new MenuItem(fileMenu, SWT.SEPARATOR);

		// add exit option
		addExitMI(fileMenu);
		
	}

	/**
	 * Add a menu item to import a catalogue database from a ecf file
	 * 
	 * @param menu
	 * @return
	 */
	private void addImportCodesMI(final Menu menu) {

		final MenuItem importCodesMI = new MenuItem(menu, SWT.PUSH);
		importCodesMI.setText("Import codes");

		importCodesMI.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (listener != null)
					listener.buttonPressed(IMPORT_CODES_MI);
			}
		});
	}

	/**
	 * Add a menu item which allows exiting the application
	 * 
	 * @param menu
	 */
	private void addExitMI(final Menu menu) {

		final MenuItem exitItem = new MenuItem(menu, SWT.NONE);
		exitItem.setText(Messages.getString("BrowserMenu.ExitAppCmd"));

		exitItem.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				mainMenu.getShell().close();

				if (listener != null)
					listener.buttonPressed(EXIT_MI);
			}

		});

	}
}
