package main;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import main.Decoder.MtxNotFoundException;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

// TODO deprecated
public class UI_Deprecated {

	private static final int RiskColumn = 4;
	private static final int WarningsColumn = 3;
	protected Shell shell;
	private Table table;
	private Decoder decoder;
	private Text termNumber;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UI_Deprecated window = new UI_Deprecated();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * return the string contained in the clipboard
	 * 
	 * @return
	 */
	private String getClipBoard() {
		try {
			return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedFlavorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {

		shell = new Shell();
		shell.setSize(1200, 720);
		shell.setText("EFSA ICT 2.0");
		shell.setLayout(new GridLayout(1, false));

		// ********** Main Menu **********
		addMainMenu();

		// ********** Toolbar ************
		addToolbar();

		// ********** Table **************
		addMainTable();

		// ********** Progress Bar *******
		addProgressBar();

		// ********** Decoder *******
		try {
			decoder = new Decoder("MTX", false);
		} catch (MtxNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * add the main menu and the menu items
	 */
	private void addMainMenu() {

		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		// File menu
		MenuItem fileMenu = new MenuItem(menu, SWT.CASCADE);
		fileMenu.setText("File");

		Menu menu_1 = new Menu(fileMenu);
		fileMenu.setMenu(menu_1);

		MenuItem loadCodesItem = new MenuItem(menu_1, SWT.NONE);
		loadCodesItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		loadCodesItem.setText("Load codes");

		MenuItem exitItem = new MenuItem(menu_1, SWT.NONE);
		exitItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		exitItem.setText("Exit");

		// Tools menu
		MenuItem toolsMenu = new MenuItem(menu, SWT.CASCADE);
		toolsMenu.setText("Tools");

		Menu menu_2 = new Menu(toolsMenu);
		toolsMenu.setMenu(menu_2);

		MenuItem settingsItem = new MenuItem(menu_2, SWT.NONE);
		settingsItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		settingsItem.setText("Settings");

		// About menu
		MenuItem aboutMenu = new MenuItem(menu, SWT.CASCADE);
		aboutMenu.setText("About");

		Menu menu_3 = new Menu(aboutMenu);
		aboutMenu.setMenu(menu_3);

		MenuItem releaseNotesItem = new MenuItem(menu_3, SWT.NONE);
		releaseNotesItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		releaseNotesItem.setText("Release notes");

		// Help menu
		MenuItem helpMenu = new MenuItem(menu, SWT.CASCADE);
		helpMenu.setText("Help");

		Menu menu_4 = new Menu(helpMenu);
		helpMenu.setMenu(menu_4);

		MenuItem guidelineItem = new MenuItem(menu_4, SWT.NONE);
		guidelineItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		guidelineItem.setText("Guideline");

		MenuItem ictItem = new MenuItem(menu_4, SWT.NONE);
		ictItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		ictItem.setText("ICT");
	}

	/**
	 * add the toolbar component with the buttons
	 */
	private void addToolbar() {

		Group grpToolbar = new Group(shell, SWT.NONE);
		RowLayout rl_grpToolbar = new RowLayout(SWT.HORIZONTAL);
		rl_grpToolbar.spacing = 5;
		rl_grpToolbar.pack = false;
		rl_grpToolbar.fill = true;
		rl_grpToolbar.wrap = false;
		grpToolbar.setLayout(rl_grpToolbar);
		grpToolbar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpToolbar.setText("Toolbar");

		Button btnAddRow = new Button(grpToolbar, SWT.NONE);
		btnAddRow.setText("Add row");
		btnAddRow.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// add new row to the table
				//new TableItem(table, SWT.NONE);
				new TableItem(table, SWT.NONE).setText(1, "a057v");//debug
				new TableItem(table, SWT.NONE).setText(1, "a056v");//debug
			}
		});

		// Decode button
		Button btnDecode = new Button(grpToolbar, SWT.NONE);
		btnDecode.setText("Decode");
		btnDecode.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {

				// clean all cells except the editable ones
				cleanEditableColumns();
				
				// iterate each row in the table
				for (TableItem item : table.getItems()) {

					// get the foodex2 code from the specific column
					String fullCode = item.getText(1);

					// perform the check
					decoder.performCheck(fullCode, false);

					// template var to contain last message
					String message = item.getText(WarningsColumn);

					ArrayList<String> mex = (ArrayList<String>) Arrays.asList(decoder.getMessages());
					for (String msg : mex) {
						// append new line if not last element
						if (mex.indexOf(msg) != mex.size() - 1) {
							message += msg + "\n";
						}
						else
							message += msg;
					}
					
					// add the warnings
					item.setText(WarningsColumn, message);

					// set the forground of the warning col to red
					item.setForeground(WarningsColumn, decoder.getTxtColor());
					
					// rebuild the cell in multiline and redraw the dimensions
					updateMultilineCell();
					
					// set the risk level
					item.setBackground(RiskColumn, decoder.getRiskColor());

				}
			}
		});

		// Clean all button
		Button btnCleanAll = new Button(grpToolbar, SWT.NONE);
		btnCleanAll.setText("Clean all");
		btnCleanAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {

				// remove all the elements of the table
				table.removeAll();

			}
		});

		// Paste codes button
		Button btnPasteCodes = new Button(grpToolbar, SWT.NONE);
		btnPasteCodes.setText("Paste codes");
		btnPasteCodes.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {

				String text = getClipBoard();

				// warn the user if clipboard is empty
				if (text.isEmpty() || text == null) {
					MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION);
					mb.setText("Information");
					mb.setMessage("Nothing to copy");
					mb.open();
					return;
				}

				// split by new line
				List<String> clipboard = Arrays.asList(text.split("\\r?\\n"));

				// add code one by one to the column
				for (String code : clipboard)
					new TableItem(table, SWT.NONE).setText(1, code);

			}
		});

		// Export codes button
		Button btnExportCodes = new Button(grpToolbar, SWT.NONE);
		btnExportCodes.setText("Export Codes");
		btnExportCodes.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {

			}
		});
	}

	/**
	 * clean the editable cells
	 */
	protected void cleanEditableColumns() {
		for(TableItem item :  table.getItems()) {
			for(int i = 2; i<table.getColumnCount();i++)
				item.setText(i, "");
		}
		
	}

	/**
	 * this method allow to rebuild the cell in multilines
	 */
	protected void updateMultilineCell() {
		/*
		 * NOTE: MeasureItem, PaintItem and EraseItem are called repeatedly. Therefore,
		 * it is critical for performance that these methods be as efficient as
		 * possible.
		 */
		Listener paintListener = new Listener() {
			public void handleEvent(Event event) {

				// enable multiline only to warning column
				if (event.index != WarningsColumn)
					return;

				switch (event.type) {
				case SWT.MeasureItem: {
					TableItem item = (TableItem) event.item;
					String text = item.getText(event.index);
					Point size = event.gc.textExtent(text);
					event.width = size.x;
					event.height = Math.max(event.height, size.y);
					break;
				}
				case SWT.PaintItem: {
					TableItem item = (TableItem) event.item;
					String text = item.getText(event.index);
					Point size = event.gc.textExtent(text);
					int offset2 = event.index == 0 ? Math.max(0, (event.height - size.y) / 2) : 0;
					event.gc.drawText(text, event.x, event.y + offset2, true);
					break;
				}
				case SWT.EraseItem: {
					event.detail &= ~SWT.FOREGROUND;
					break;
				}
				}
			}
		};

		table.addListener(SWT.MeasureItem, paintListener);
		table.addListener(SWT.PaintItem, paintListener);
		table.addListener(SWT.EraseItem, paintListener);

		// pack the warnings column
		table.getColumn(WarningsColumn).pack();

	}

	/**
	 * add the main table
	 */
	private void addMainTable() {

		table = new Table(shell, SWT.VIRTUAL | SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		// create the table columns
		String[] colHeaders = { "Add Text", "FoodEx2 Codes", "Interpreted code", "Warnings", "Risk", "Corex", "Statef",
				"Bese + implicit facets", "Code before changes" };

		List<String> headers = Arrays.asList(colHeaders);

		for (String header : headers) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			// column.setWidth(100);
			column.setText(header);
			column.pack();
		}

		// create the table editor
		final TableEditor editor = new TableEditor(table);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;

		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent arg0) {

				// loose the focus as clicking out the row
				Control old = editor.getEditor();

				if (old != null)
					old.dispose();
			}

			@Override
			public void mouseDown(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {

				Control old = editor.getEditor();

				if (old != null)
					old.dispose();

				Point pt = new Point(e.x, e.y);

				final TableItem item = table.getItem(pt);

				if (item == null)
					return;

				int column = -1;
				for (int i = 0, n = table.getColumnCount(); i < n; i++) {
					Rectangle rect = item.getBounds(i);
					if (rect.contains(pt)) {
						column = i;
						break;
					}
				}

				// only the editable columns can be modified
				if (column > 1 || column < 0)
					return;

				final Text text = new Text(table, SWT.NONE);
				// text.setForeground(item.getForeground());

				text.setText(item.getText(column));
				// text.setForeground(item.getForeground());
				text.selectAll();
				// text.setFocus();

				editor.minimumWidth = text.getBounds().width;

				editor.setEditor(text, item, column);

				final int col = column;
				text.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent event) {
						item.setText(col, text.getText());
					}
				});

			}
		});

	}

	/**
	 * add the progress bar at the bottom
	 */
	private void addProgressBar() {

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new GridLayout(2, true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(new GridLayout(2, false));
		composite_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		Label termsLabel = new Label(composite_1, SWT.NONE);
		termsLabel.setText("Terms ");

		termNumber = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY);
		termNumber.setEditable(false);
		termNumber.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		
		Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayout(new GridLayout(2, false));
		composite_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));

		Label lblLoadingStatus = new Label(composite_2, SWT.NONE);
		lblLoadingStatus.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblLoadingStatus.setText("Loading status ");

		ProgressBar progressBar = new ProgressBar(composite_2, SWT.NONE);
		progressBar.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
	}
}
