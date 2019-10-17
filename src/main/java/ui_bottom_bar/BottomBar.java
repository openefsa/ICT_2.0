package ui_bottom_bar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import utilities.GlobalUtil;

public class BottomBar {
	
	// main shell
	private Shell shell;
	private Display display;

	// widgets
	private Label loadingStatus, totTerms;
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
	 * Create the BottomBar
	 * 
	 * @param shell
	 * @return
	 */
	public void createBottomBar() {

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new GridLayout(2, true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		// inner left composite
		Composite composite1 = new Composite(composite, SWT.NONE);
		composite1.setLayout(new GridLayout(2, false));
		
		Label termsLabel = new Label(composite1, SWT.NONE);
		termsLabel.setText("Terms ");
		
		totTerms = new Label(composite1, SWT.BORDER | SWT.SHADOW_IN  | SWT.CENTER);
		GridData data = new GridData();
		data.widthHint = 100;
		totTerms.setLayoutData(data);
		totTerms.setText("0");
		
		// inner right composite
		Composite composite2 = new Composite(composite, SWT.NONE);
		composite2.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, false));
		composite2.setLayout(new GridLayout(2, false));
		
		loadingStatus = new Label(composite2, SWT.NONE);
		data = new GridData();
		data.horizontalAlignment = SWT.RIGHT;
		data.widthHint = 65;
		loadingStatus.setLayoutData(data);
		loadingStatus.setText("Complete ");

		progressBar = new ProgressBar(composite2, SWT.NONE);
		data = new GridData();
		data.widthHint = 200;
		progressBar.setLayoutData(data);
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

				loadingStatus.setText("Complete");
				
				// unlock user input
				GlobalUtil.setShellCursor(shell, SWT.CURSOR_ARROW);
				
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
		
		// lock user input
		GlobalUtil.setShellCursor(shell, SWT.CURSOR_WAIT);
		
		this.loadingStatus.setText(status);
	}
}
