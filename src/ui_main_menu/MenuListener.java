package ui_main_menu;

/**
 * Listener to observe the press actions of
 * menu items.
 * @author shahaal
 *
 */
public interface MenuListener {
	
	/**
	 * Function called when a menu item is pressed.
	 * @param code a unique code which identifies the menuitem
	 */
	public void buttonPressed(int code);
}
