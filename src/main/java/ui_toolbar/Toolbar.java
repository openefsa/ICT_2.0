package ui_toolbar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

public class Toolbar {

	// codes to identify the items (used for listeners)
	public static final int ADD_ROW_BTN = 0;
	public static final int DECODE_BTN = 1;
	public static final int CLEAN_BTN = 2;
	public static final int PASTE_BTN = 3;
	public static final int EXPORT_BTN = 4;

	private ToolbarListener listener;

	private Shell shell;

	/**
	 * Initialise the ToolBar
	 * 
	 * @param shell shell on which creating the ToolBar
	 */
	public Toolbar(Shell shell) {
		this.shell = shell;
		createToolbar();
	}

	/**
	 * Create the ToolBar
	 * 
	 * @param shell
	 */
	public void createToolbar() {

		if (shell.isDisposed())
			return;

		// create the group components
		Group group = new Group(shell, SWT.NONE);
		group.setLayout(new RowLayout());
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		group.setText("Toolbar");

		// add new row button
		Button btnAddRow = new Button(group, SWT.PUSH);
		btnAddRow.setText("Add row");
		btnAddRow.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (listener != null)
					listener.buttonPressed(ADD_ROW_BTN);
			}
		});

		// Decode button
		Button btnDecode = new Button(group, SWT.PUSH);
		btnDecode.setText("Decode");
		btnDecode.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (listener != null)
					listener.buttonPressed(DECODE_BTN);
			}
		});

		// Clean all button
		Button btnCleanAll = new Button(group, SWT.PUSH);
		btnCleanAll.setText("Clean all");
		btnCleanAll.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (listener != null)
					listener.buttonPressed(CLEAN_BTN);
			}
		});

		// Paste codes button
		Button btnPasteCodes = new Button(group, SWT.PUSH);
		btnPasteCodes.setText("Paste codes");
		btnPasteCodes.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (listener != null)
					listener.buttonPressed(PASTE_BTN);
			}
		});

		// Export codes button
		Button btnExportCodes = new Button(group, SWT.PUSH);
		btnExportCodes.setText("Export Codes");
		btnExportCodes.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (listener != null)
					listener.buttonPressed(EXPORT_BTN);

			}
		});
	}

	/**
	 * Listener to observe the buttons in the ToolBar
	 * 
	 * @param fileListener
	 */
	public void setToolbarListener(ToolbarListener toolbarListener) {
		this.listener = toolbarListener;
	}
}
