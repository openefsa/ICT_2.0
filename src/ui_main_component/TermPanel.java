package ui_main_component;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import model.TermRow;

public class TermPanel {
	
	public static final int CHECK_BTN = 0;
	public static final int FIX_BTN = 1;
	
	private SashForm sashForm;
	private Display display;

	// buttons
	private Button checkButton;
	private Button fixButton;

	// right group widgets
	private Text warningsField;
	private Canvas riskCanvas;
	private Text codeField;
	
	private TermPanelListener buttonListener;

	/**
	 * Initialise the right component
	 * 
	 * @param sashForm
	 */
	public TermPanel(SashForm sashForm) {
		this.sashForm = sashForm;
		this.display = sashForm.getDisplay();
		createTermProperty();
	}

	/**
	 * add components to the right group
	 */
	private void createTermProperty() {

		// group which contains hierarchy selector, tree viewer and tab folder
		Composite rightGroup = new Composite(sashForm, SWT.NONE);
		GridLayout gl_rightGroup = new GridLayout(1, false);
		rightGroup.setLayout(gl_rightGroup);

		Label lblNewLabel = new Label(rightGroup, SWT.LEFT);
		lblNewLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		lblNewLabel.setText("Warnings");

		GridData gd;

		warningsField = new Text(rightGroup, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.MULTI | SWT.READ_ONLY);
		warningsField.setEditable(false);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd.minimumHeight = 100;
		warningsField.setLayoutData(gd);

		Label lblNewLabel_1 = new Label(rightGroup, SWT.HORIZONTAL | SWT.LEFT);
		lblNewLabel_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		lblNewLabel_1.setText("Risk Level");

		riskCanvas = new Canvas(rightGroup, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd.minimumHeight = 100;
		riskCanvas.setLayoutData(gd);

		Label lblNewLabel_2 = new Label(rightGroup, SWT.HORIZONTAL | SWT.LEFT);
		lblNewLabel_2.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		lblNewLabel_2.setText("Possible New Code");

		codeField = new Text(rightGroup, SWT.BORDER);
		codeField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));

		/*
		 * codeField.addModifyListener(new ModifyListener() { public void
		 * modifyText(ModifyEvent event) { // Get the widget whose text was modified
		 * Text text = (Text) event.widget; System.out.println(text.getText()); } })
		 */;

		checkButton = new Button(rightGroup, SWT.NONE);
		checkButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		checkButton.setText("Check");

		checkButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (buttonListener != null)
					buttonListener.buttonPressed(CHECK_BTN);
			}
		});

		fixButton = new Button(rightGroup, SWT.NONE);
		fixButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		fixButton.setText("Fix");

		fixButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (buttonListener != null)
					buttonListener.buttonPressed(FIX_BTN);
			}
		});

		enableButtons(false);

	}

	/**
	 * use this method for filling the field of the right group
	 * 
	 * @param term
	 */
	public void setTerm(TermRow term) {
		
		if(term == null)
			return;
		
		String warn = term.getWarning();
		String code = term.getCode();
		Color risk = term.getRisk();

		// if all fields are null return
		if (warn == null && code == null && risk == null)
			return;

		warningsField.setText(warn);
		riskCanvas.setBackground(risk);
		codeField.setText(code);
		
		// enable editing buttons
		enableButtons(true);

	}

	/**
	 * clean all fields
	 */
	public void clear() {
		warningsField.setText("");
		riskCanvas.setBackground(display.getSystemColor(SWT.COLOR_GRAY));
		codeField.setText("");
		checkButton.setEnabled(false);
		fixButton.setEnabled(false);
	}

	/**
	 * enable/disable buttons on right group
	 */
	public void enableButtons(boolean enable) {
		checkButton.setEnabled(enable);
		fixButton.setEnabled(enable);
	}

	/**
	 * Called when the check button is pressed
	 * 
	 * @param listener
	 */
	public void setButtonListener(TermPanelListener listener) {
		this.buttonListener = listener;
	}

	/**
	 * get the code field text
	 * 
	 * @return
	 */
	public String getCode() {
		return codeField.getText();
	}

	/**
	 * get the risk of the term
	 * 
	 * @return
	 */
	public Color getRisk() {
		return riskCanvas.getBackground();
	}

	/**
	 * get the warnings of the term
	 * 
	 * @return
	 */
	public String getWarnings() {
		return warningsField.getText();
	}
}
