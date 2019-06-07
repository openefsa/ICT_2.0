package ui_main_component;

/**
 * Listener to observe the press actions of
 * buttons in right group.
 * @author shahaal
 *
 */
public interface TermPanelListener {
	
	/**
	 * Function called when a item is pressed in the ToolBar.
	 * @param code a unique code which identifies the ToolBar button
	 */
	public void buttonPressed(int code);
}
