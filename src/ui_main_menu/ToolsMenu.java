package ui_main_menu;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

/**
 * create the main menu for tools
 * 
 * @author shahaal
 *
 */
public class ToolsMenu {

	public static final int SETTINGS_MI = 0;

	private MenuListener listener;

	/**
	 * Tools menu in main menu
	 * 
	 * @param menu
	 */
	public ToolsMenu(Menu menu) {
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
	 * Create the tools menu with all the sub menu items
	 * 
	 * @param menu
	 */
	public void create(Menu menu) {

		MenuItem toolsItem = new MenuItem(menu, SWT.CASCADE);
		toolsItem.setText("Tools");

		Menu toolsMenu = new Menu(menu);

		toolsItem.setMenu(toolsMenu);

		// add settings
		addSettingsMI(toolsMenu);

		// TODO to finish

		toolsItem.setEnabled(false);
	}

	/**
	 * Add a menu item for settings
	 * 
	 * @param menu
	 */
	private void addSettingsMI(Menu menu) {

		final MenuItem settingsItem = new MenuItem(menu, SWT.NONE);
		settingsItem.setText("Settings");

		settingsItem.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				System.out.println("Settings selected");

				if (listener != null)
					listener.buttonPressed(SETTINGS_MI);
			}

		});

	}
}
