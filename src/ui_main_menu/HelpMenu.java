package ui_main_menu;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

/**
 * create the main menu for help
 * 
 * @author shahaal
 *
 */
public class HelpMenu {

	public static final int GUIDELINES_MI = 0;
	public static final int ICT_MI = 1;

	private MenuListener listener;

	/**
	 * help menu in main menu
	 * 
	 * @param menu
	 */
	public HelpMenu(Menu menu) {
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
	 * Create the help menu with all the sub menu items
	 * 
	 * @param menu
	 */
	public void create(Menu menu) {

		MenuItem helpItem = new MenuItem(menu, SWT.CASCADE);
		helpItem.setText("Help");

		Menu toolsMenu = new Menu(menu);

		helpItem.setMenu(toolsMenu);

		// add guidelines
		addGuidelinesMI(toolsMenu);
		
		// add guidelines
		addIctMI(toolsMenu);

		// TODO to finish

		helpItem.setEnabled(false);
	}

	/**
	 * Add a menu item for guidelines
	 * 
	 * @param menu
	 */
	private void addGuidelinesMI(Menu menu) {

		final MenuItem settingsItem = new MenuItem(menu, SWT.NONE);
		settingsItem.setText("Guidelines");

		settingsItem.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				System.out.println("Guidelines selected");

				if (listener != null)
					listener.buttonPressed(GUIDELINES_MI);
			}

		});

	}
	
	/**
	 * Add a menu item for ICT
	 * 
	 * @param menu
	 */
	private void addIctMI(Menu menu) {

		final MenuItem settingsItem = new MenuItem(menu, SWT.NONE);
		settingsItem.setText("ICT");

		settingsItem.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				System.out.println("ICT selected");

				if (listener != null)
					listener.buttonPressed(ICT_MI);
			}

		});

	}
}
