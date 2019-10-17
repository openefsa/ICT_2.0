package main;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import decoder.Decoder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import model.TermRow;
import model.TermRowTableModel;
import ui_bottom_bar.BottomBar;
import ui_main_component.TablePanel;
import ui_main_component.TermPanel;
import ui_main_component.TermPanelListener;
import ui_main_menu.FileMenu;
import ui_main_menu.MainMenu;
import ui_main_menu.MenuListener;
import ui_toolbar.Toolbar;
import ui_toolbar.ToolbarListener;
import utilities.ExportParser;
import utilities.GlobalUtil;
import utilities.ImportParser;
import utilities.Utility;

public class MainPanel {

	protected Shell shell;
	private Display display;

	private MainMenu menu;
	private Toolbar toolbar;
	private TablePanel tablePanel;
	private TermPanel termPanel;

	// The data model
	private TermRowTableModel model;
	private Decoder decoder;
	private TableViewer tableViewer;

	// bottom bar widgets
	// ????
	private int totPercent = 0, percent = 0;
	private BottomBar bottomBar;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			MainPanel window = new MainPanel();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {

		display = Display.getDefault();

		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			
			createContents();
			shell.open();
			shell.layout();

			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		});
	}

	/**
	 * Create contents of the window.
	 * 
	 * @throws InterruptedException
	 * @throws MtxNotFoundException
	 */
	protected void createContents() {

		shell = new Shell();
		shell.setSize(1200, 720);
		shell.setMaximized(true);
		shell.setText("EFSA ICT 2.0");
		shell.setLayout(new GridLayout(1, false));

		// initialise the model and the decoder
		model = new TermRowTableModel();

		decoder = initializeDecoder();

		// add all components to shell
		addWidgets();

	}

	/**
	 * add the widgets to the main window
	 */
	private void addWidgets() {

		// Main Menu
		addMainMenu();

		// ToolBar
		addToolBar();

		// Main Table
		addMainTable();

		// BottomBar
		addBottomBar();

	}

	/**
	 * Add the main menu to the shell
	 * 
	 * @param shell
	 */
	private void addMainMenu() {

		// create the main menu
		menu = new MainMenu(shell);

		// set listener for file
		menu.setFileListener(new MenuListener() {

			@Override
			public void buttonPressed(int code) {

				switch (code) {

				// import codes is selected
				case FileMenu.IMPORT_CODES_MI:

					// show message before importing
					MessageBox mb = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
					mb.setText("Import codes");
					mb.setMessage(
							"Please note that all the terms in the table will be removed.\nDo you want to continue?");

					if (mb.open() != SWT.YES)
						return;
					
					// show a file dialog filtering csv files
					String filename = GlobalUtil.showFileDialog(shell, "Import codes", new String[] { "*.csv" },
							SWT.OPEN);

					// clean the table
					clearTable();

					// initialise the parser
					ImportParser parser = new ImportParser(filename);

					// add all the terms to the model
					TermRow currentTerm;
					while ((currentTerm = parser.nextTerm()) != null)
						model.add(currentTerm);
					
					// update the UI
					update();

					// show message when process completed
					GlobalUtil.showDialog(shell, "Import codes", "Codes imported successfully!", SWT.ICON_INFORMATION);

					break;
				default:
					break;
				}
			}
		});

		if (shell.getMenu() != null)
			shell.getMenu().dispose();

		// initialise the main menu with all the sub menus and menu items
		shell.setMenuBar(menu.createMainMenu());
	}

	/**
	 * add the {@link Toolbar}
	 */
	private void addToolBar() {

		// initialise and create the ToolBar
		toolbar = new Toolbar(shell);

		// set listener for file
		toolbar.setToolbarListener(new ToolbarListener() {

			@Override
			public void buttonPressed(int code) {

				// execute the relative command
				switch (code) {
				case Toolbar.ADD_ROW_BTN:
					addRow();
					break;
				case Toolbar.DECODE_BTN:
					decodeAll();
					break;
				case Toolbar.CLEAN_BTN:
					clearTable();
					break;
				case Toolbar.PASTE_BTN:
					pasteCodes();
					break;
				case Toolbar.EXPORT_BTN:
					exportCodes();
					break;
				default:
					break;
				}
			}
		});
	}

	/**
	 * add the main table
	 */
	private void addMainTable() {
		
		/* TODO function to be developed (need to filter table data 
		// add the filter components
		Label lblFilter = new Label(composite, SWT.NONE);
		lblFilter.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFilter.setText("Show ");

		// values to the combo
		Combo combo = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo.setItems(new String[] { "All", "Warnings", "None" });
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		combo.select(0);
		*/
		
		// create the sash form which allows the resize
		SashForm sashForm = new SashForm(shell, SWT.BORDER | SWT.SMOOTH);
		sashForm.setSashWidth(2);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// left group for catalogue label, search bar and table
		tablePanel = new TablePanel(sashForm);
		tableViewer = tablePanel.getTableViewer();

		// add selection listener
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {

				// if the selection is empty or bad instance return
				if (event.getSelection().isEmpty() || !(event.getSelection() instanceof IStructuredSelection))
					return;

				// get the selected term row
				TermRow term = getSelectedTermRow();

				// fill the termPanel with term info
				termPanel.setTerm(term);

			}
		});

		// add the term property panel
		termPanel = new TermPanel(sashForm);

		// add listener to the termPanel
		termPanel.setButtonListener(new TermPanelListener() {

			@Override
			public void buttonPressed(int code) {

				switch (code) {

				// check button selected
				case TermPanel.CHECK_BTN:
					checkTerm();
					break;
				// fix button selected
				case TermPanel.FIX_BTN:
					fixTerm();
					break;
				default:
					break;
				}
			}
		});

		// update the table
		update();

		// set the weights of the components
		sashForm.setWeights(new int[] { 3, 2 });

	}

	/**
	 * add the progress bar at the bottom
	 */
	private void addBottomBar() {
		bottomBar = new BottomBar(shell);
	}

	// ****** ToolBar methods

	/**
	 * add empty record to the table
	 * 
	 */
	protected void addRow() {

		// add new term to the list
		model.add(new TermRow());

		// update the table
		update();
	}

	/**
	 * Decode all the records one by one and fill the table
	 * 
	 */
	protected void decodeAll() {

		// clear non editable columns
		tablePanel.cleanNotEditableColumns();

		percent = (int) Math.round(0.1 * model.getSize()); // increase pb each 10% of total
		totPercent = percent;

		// set progress bar
		bottomBar.setProgressMaximum();
		bottomBar.setLoadingStatus("Analysing ... ");

		// create task
		Runnable task = new Runnable() {

			@Override
			public void run() {

				int percentage = 0;

				// iterate each element in the model
				for (int i = 0; i < model.getSize(); i++) {

					// get the term at the specified index
					TermRow term = model.getTermAt(i);

					// get the term code
					String fullCode = term.getCode();

					// remove all spaces and new lines from the code
					fullCode = fullCode.replaceAll("\\s","");
					
					// check the code without printing on external file
					decoder.performCheck(fullCode, false);
					
					// set the new code
					term.setCode(fullCode);
					
					// template var to contain last message
					term.setWarning(decoder.getMessages());
					
					// set the risk level
					term.setRisk(decoder.getCurrentRisk());

					// update the new term in the model
					model.setTermAt(i, term);

					// update the ProgressBar each 10%
					if (i >= totPercent) {
						totPercent += percent;
						percentage += 10;
						bottomBar.updateProgress(percentage);
					}
				}

				// update the table
				update();

				// update the progress bar
				bottomBar.updateProgressFinish();

			}
		};

		// run the task
		Thread t = new Thread(task);
		t.start();

	}

	/**
	 * copy the (only) codes from clip board directly into the table
	 */
	protected void pasteCodes() {

		// get text from clip board
		String text = Utility.getClipboard();

		// warn the user if clip board is empty
		if (text.isEmpty() || text == null) {
			GlobalUtil.showDialog(shell, "Paste codes", "Nothing to copy", SWT.ICON_INFORMATION);
			return;
		}

		// split by new line
		List<String> clipboard = Arrays.asList(text.split("\\r?\\n"));

		// add code one by one to the end of the model
		for (String code : clipboard)
			model.add(new TermRow(code));

		// update the table
		update();

	}

	/**
	 * allow to export all the codes present under the foodex2 column
	 */
	protected void exportCodes() {

		// show a file dialog where to save file
		String filename = GlobalUtil.showFileDialog(shell, "Export codes", new String[] { "*.csv" }, SWT.SAVE);

		if (filename == null)
			return;

		// initialise the export parser
		ExportParser parser = new ExportParser(filename, model.getTerms());

		try {

			// convert the list of codes in CSV and save in external file
			parser.saveToFile();
			// show message when process completed
			GlobalUtil.showDialog(shell, "Export codes", "Codes exported successfully!", SWT.ICON_INFORMATION);

		} catch (IOException e) {

			// show message when process completed
			GlobalUtil.showDialog(shell, "Import codes", "Something went wrong during the export!\nPlease try again.",
					SWT.ICON_ERROR);
			e.printStackTrace();

		}

	}

	/**
	 * check a specific term using the BRs
	 */
	protected void checkTerm() {

		// get the code from the text field
		String fullCode = termPanel.getCode();
		
		// remove all spaces and new lines from the code
		fullCode = fullCode.replaceAll("\\s","");
		
		// check the code without printing on external file
		decoder.performCheck(fullCode, false);

		// get new warnings and canvas colour
		String warnings = decoder.getMessages();
		Color risk = decoder.getCurrentRisk();

		TermRow term = new TermRow("", fullCode, warnings, risk);

		// fill the right group
		termPanel.setTerm(term);

	}

	/**
	 * fix the changed term and save it
	 */
	private void fixTerm() {

		// get index of the term
		int index = tableViewer.getTable().getSelectionIndex();

		// get the updated term
		TermRow term = termPanel.updateTerm(model.getTermAt(index));

		// update the term in the analysed list
		model.setTermAt(index, term);

		// update the term in the table
		update();

	}

	/**
	 * return the selected row term
	 * 
	 * @return
	 */
	private TermRow getSelectedTermRow() {

		// get the table selection index
		int index = tableViewer.getTable().getSelectionIndex();

		// get the selected term row (if nothing selected just return first)
		return model.getTermAt(index);
	}

	/**
	 * the method clean the table and the list of terms in memory
	 * 
	 */
	protected void clearTable() {

		// remove terms
		model.clear();

		// update table UI
		update();

		// clean term panel
		termPanel.clear();
	}

	/**
	 * Update the table with the terms
	 * 
	 * @param model
	 * 
	 * @param team  the team
	 */
	public void update() {

		display.asyncExec(new Runnable() {
			public void run() {

				// Set the table viewer's input
				tableViewer.setInput(model);

				// refresh the table content
				tableViewer.refresh();

				if (bottomBar != null) {
					// update the total terms inserted
					bottomBar.setTermNumber(String.valueOf(model.getSize()));
				}
			}
		});

	}

	/**
	 * initialise the decoder object used for checking the FoodEx2 codes
	 * 
	 * @throws InterruptedException
	 * @throws MtxNotFoundException
	 */
	private Decoder initializeDecoder() {
		return decoder = new Decoder();
	}

}
