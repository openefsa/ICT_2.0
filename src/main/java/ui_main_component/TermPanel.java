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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import model.TermRow;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;

public class TermPanel {

	// command codes
	public static final int CHECK_BTN = 0;
	public static final int FIX_BTN = 1;

	// UI components
	private SashForm sashForm;
	private Button checkButton;
	private Button fixButton;

	private Text warningsField;
	private Canvas riskCanvas;
	private Text codeField;

	// listener
	private TermPanelListener buttonListener;

	// selected term to display (writable allow to modify obj)
	private WritableValue<TermRow> value = new WritableValue<TermRow>();;

	/**
	 * Initialise the right component
	 * 
	 * @param sashForm
	 */
	public TermPanel(SashForm sashForm) {
		this.sashForm = sashForm;
		sashForm.getDisplay();
		createTermProperty();
	}

	/**
	 * add components to the right group
	 */
	private void createTermProperty() {

		// right panel
		Composite rightGroup = new Composite(sashForm, SWT.NONE);
		//rightGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		rightGroup.setLayout(new GridLayout(2, true));

		GridData gridData;
		
		// label for warnings
		Label lblWarnings = new Label(rightGroup, SWT.LEFT);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		lblWarnings.setLayoutData(gridData);
		lblWarnings.setText("Warnings");

		// data layout for warnings
		//GridData warningsLayout = new GridData(SWT.FILL, SWT.FILL, true, true);
		//warningsLayout.minimumHeight = 100;

		// warning field
		warningsField = new Text(rightGroup, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.MULTI | SWT.READ_ONLY);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 2;
		warningsField.setLayoutData(gridData);
		warningsField.setEditable(false);

		// label for risk level
		Label lblRisk = new Label(rightGroup, SWT.HORIZONTAL | SWT.LEFT);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		lblRisk.setLayoutData(gridData);
		lblRisk.setText("Risk Level");

		// data layout for risk level
		//GridData riskLayout = new GridData(SWT.FILL, SWT.FILL, true, true);
		//riskLayout.minimumHeight = 100;

		// risk field
		riskCanvas = new Canvas(rightGroup, SWT.BORDER);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 2;
		riskCanvas.setLayoutData(gridData);

		// label for term code
		Label lblCode = new Label(rightGroup, SWT.HORIZONTAL | SWT.LEFT);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		lblCode.setLayoutData(gridData);
		lblCode.setText("Term Code");

		// code field
		codeField = new Text(rightGroup, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 2;
		codeField.setLayoutData(gridData);

		// check button
		checkButton = new Button(rightGroup, SWT.NONE);
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		checkButton.setLayoutData(gridData);
		checkButton.setText("Check");

		// listener to check button
		checkButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (buttonListener != null)
					buttonListener.buttonPressed(CHECK_BTN);
			}
		});

		// fix button
		fixButton = new Button(rightGroup, SWT.NONE);
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		fixButton.setLayoutData(gridData);
		fixButton.setText("Fix");

		// listener to fix button
		fixButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (buttonListener != null)
					buttonListener.buttonPressed(FIX_BTN);
			}
		});

		// disable the buttons
		enableButtons(false);

		// bind object with widgets
		bindValues();

	}

	/**
	 * fill all widgets with term information
	 * 
	 * @param term
	 */
	public void setTerm(TermRow term) {

		// update the observable object
		value.setValue(term);

		// disable button
		enableButtons(true);

	}

	/**
	 * clean all widgets
	 */
	public void clear() {

		// clean writable value
		value.setValue(null);
		
		// disable buttons
		enableButtons(false);
	}

	/**
	 * enable/disable buttons on right group
	 * 
	 * @param enable
	 */
	public void enableButtons(boolean enable) {
		checkButton.setEnabled(enable);
		fixButton.setEnabled(enable);
	}

	/**
	 * Called when the buttons are pressed
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

	/**
	 * update the term passed in input with those in the term panel
	 * 
	 * @param termAt
	 * @return
	 */
	public TermRow updateTerm(TermRow term) {

		// update the term information
		term.setCode(getCode());
		term.setWarning(getWarnings());
		term.setRisk(getRisk());

		return term;

	}

	/**
	 * the method bind the term attributes with the corresponding field
	 */
	@SuppressWarnings("unchecked")
	private void bindValues() {

		// The DataBindingContext object will manage the data bindings
		DataBindingContext ctx = new DataBindingContext();

		// bind warnings
		IObservableValue<?> widgetValue = WidgetProperties.text(SWT.Modify).observe(warningsField);
		IObservableValue<?> modelValue = BeanProperties.value("warning").observeDetail(value);
		ctx.bindValue(widgetValue, modelValue);

		// bind risk
		widgetValue = WidgetProperties.background().observe(riskCanvas);
		modelValue = BeanProperties.value("risk").observeDetail(value);
		ctx.bindValue(widgetValue, modelValue);

		// bind code
		widgetValue = WidgetProperties.text(SWT.Modify).observe(codeField);
		modelValue = BeanProperties.value("code").observeDetail(value);
		ctx.bindValue(widgetValue, modelValue);

	}
}
