package ui_toolbar;

/**
 * Listener to observe the press actions of
 * ToolBar items.
 * @author shahaal
 *
 */
public interface ToolbarListener {
	
	/**
	 * Function called when a item is pressed in the ToolBar.
	 * @param code a unique code which identifies the ToolBar button
	 */
	public void buttonPressed(int code);
}
