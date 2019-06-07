package ui_main_component;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import model.TermRowContentProvider;
import model.TermRowLabelProvider;

public class TablePanel {

	// Table column names/properties
	public static final String FREE = "Free Text";
	public static final String CODE = "FoodEx2 Codes";
	public static final String RISK = "Risk";

	public static final String[] PROPS = { FREE, CODE };

	private SashForm sashForm;

	private Table table;

	private TableViewer tableViewer;

	/**
	 * Initialise the TablePanel
	 * 
	 * @param sashForm
	 */
	public TablePanel(SashForm sashForm) {
		this.sashForm = sashForm;
		createTablePanel();
	}

	/**
	 * Create the TablePanel UI
	 * 
	 */
	public void createTablePanel() {

		// left group for catalogue label, search bar and table
		Composite leftGroup = new Composite(sashForm, SWT.NONE);
		leftGroup.setLayoutData(new GridData(SWT.WRAP, SWT.FILL, false, false));
		leftGroup.setLayout(new GridLayout());

		// initialise the table viewer
		tableViewer = new TableViewer(leftGroup, SWT.VIRTUAL | SWT.BORDER | SWT.FULL_SELECTION);

		// Set the content providers
		tableViewer.setContentProvider(new TermRowContentProvider());
		tableViewer.setLabelProvider(new TermRowLabelProvider());

		// set table layout
		table = tableViewer.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH));

		// create the table columns
		new TableColumn(table, SWT.NONE).setText(FREE);
		new TableColumn(table, SWT.NONE).setText(CODE);
		new TableColumn(table, SWT.NONE).setText(RISK);

		// pack the columns
		for (int i = 0, n = table.getColumnCount(); i < n; i++) {
			table.getColumn(i).pack();
		}

		// set table properties
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		// create the cell editors
		CellEditor[] editors = new CellEditor[2];
		editors[0] = new TextCellEditor(table);
		editors[1] = new TextCellEditor(table);
		
		// set the editors, cell modifier, and column properties
		tableViewer.setColumnProperties(PROPS);
		tableViewer.setCellModifier(new TermCellModifier(tableViewer));
		tableViewer.setCellEditors(editors);

	}

	/**
	 * @return the tableViewer
	 */
	public TableViewer getTableViewer() {
		return tableViewer;
	}

	/**
	 * clean the non editable columns
	 */
	public void cleanNotEditableColumns() {

		for (TableItem item : table.getItems()) {
			for (int i = 2; i < table.getColumnCount(); i++)
				item.setText(i, "");
		}

	}
}
