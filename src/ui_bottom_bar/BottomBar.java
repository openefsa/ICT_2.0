package ui_bottom_bar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class BottomBar {
	
	// main shell
	private Shell shell;
	private Display display;

	// widgets
	private Text totTerms;
	private Label loadingStatus;
	private ProgressBar progressBar;
	
	private final int MAX_PB = 100;
	
	/**
	 * Initialise the BottomBar
	 * 
	 * @param shell shell on which creating the BottomBar
	 */
	public BottomBar(Shell shell) {
		this.shell = shell;
		this.display = shell.getDisplay();
		createBottomBar();
	}

	/**
	 * Create the toolbar
	 * 
	 * @param shell
	 * @return
	 */
	public void createBottomBar() {

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new GridLayout(2, true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(new GridLayout(2, false));
		composite_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));

		Label termsLabel = new Label(composite_1, SWT.NONE);
		termsLabel.setText("Terms ");
		
		GridData gd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gd.minimumWidth = 80;
		
		totTerms = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
		totTerms.setLayoutData(gd);
		totTerms.setText("0");
		totTerms.setEditable(false);

		Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayout(new GridLayout(2, false));
		composite_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));

		loadingStatus = new Label(composite_2, SWT.NONE);
		loadingStatus.setLayoutData(gd);
		loadingStatus.setText("Complete");

		progressBar = new ProgressBar(composite_2, SWT.NONE);

	}

	/**
	 * update the progress bar selection
	 * 
	 * @param value
	 */
	public void updateProgress(int value) {
		
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				progressBar.setSelection(value);
			}
		});
	}

	/**
	 * update progress bar when process completed
	 */
	public void updateProgressFinish() {
		display.asyncExec(new Runnable() {

			@Override
			public void run() {

				progressBar.setSelection(0);
				progressBar.setMaximum(1);

				// tableViewer.getTable().setRedraw(true);

				loadingStatus.setText("Complete");
				
			}
		});
	}

	/**
	 * set maximum value for progress bar
	 * 
	 * @param i
	 */
	public void setProgressMaximum() {
		this.progressBar.setMaximum( MAX_PB );
	}

	/**
	 * @param totTerms the termNumber to set
	 */
	public void setTermNumber(String totTerms) {
		this.totTerms.setText(totTerms);
	}

	/**
	 * @param loadingStatus the loadingStatus to set
	 */
	public void setLoadingStatus(String status) {
		this.loadingStatus.setText(status);
	}
}
