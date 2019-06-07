package ui_main_menu;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

/**
 * create the main menu for about
 * 
 * @author shahaal
 *
 */
public class AboutMenu {

	public static final int NOTES_MI = 0;

	private MenuListener listener;

	/**
	 * about menu in main menu
	 * 
	 * @param menu
	 */
	public AboutMenu(Menu menu) {
		create(menu);
	}

	/**
	 * Listener called when a button of the menu is pressed
	 * 
	 * @param listener
	 */
	public void setListener(MenuListener listener) {
		this.listener = listener;
	}

	/**
	 * Create the about menu with all the sub menu items
	 * 
	 * @param menu
	 */
	public void create(Menu menu) {

		MenuItem aboutItem = new MenuItem(menu, SWT.CASCADE);
		aboutItem.setText("About");

		Menu toolsMenu = new Menu(menu);

		aboutItem.setMenu(toolsMenu);

		// add notes
		addNotesMI(toolsMenu);

		// TODO to finish

		aboutItem.setEnabled(false);
	}

	/**
	 * Add a menu item for settings
	 * 
	 * @param menu
	 */
	private void addNotesMI(Menu menu) {

		final MenuItem settingsItem = new MenuItem(menu, SWT.NONE);
		settingsItem.setText("Release notes");

		settingsItem.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				System.out.println("Release notes selected");

				if (listener != null)
					listener.buttonPressed(NOTES_MI);
			}

		});

	}
}
