package main;

import catalogue.Catalogue;
import catalogue_browser_dao.CatalogueDAO;
import dcf_manager.Dcf.DcfType;

import java.io.FileNotFoundException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import business_rules.TermRules;
import utilities.GlobalUtil;

/**
 * The class uses the BR provided by the Catalogue browser in order to interpret
 * FoodEx2 codes and check their correctness
 * 
 * @author shahaal
 */
public class Decoder extends TermRules {

	private Color red, orange, yellow, green;

	// messages
	private String messages;

	private WarningLevel currentWarningLevel;

	private Color riskLevel = Display.getCurrent().getSystemColor(SWT.COLOR_GREEN);

	public Decoder(String mtxCode, boolean local) throws Decoder.MtxNotFoundException, InterruptedException {
		
		CatalogueDAO catDao = new CatalogueDAO();
		DcfType type = local ? DcfType.LOCAL : DcfType.PRODUCTION;

		Catalogue mtx = catDao.getLastVersionByCode(mtxCode, type);

		if (mtx == null) {
			throw new MtxNotFoundException(mtxCode, type);
		}
		currentCat = mtx;

		System.err.println("Loading catalogue data into RAM...");

		currentCat.loadData();

		loadFileData();

		setColors(Display.getDefault());
	}

	/**
	 * check the code and print out the result
	 * 
	 * @param fullCode
	 * @param stdOut
	 */
	public void performCheck(String fullCode, boolean stdOut) {
		messages = "";
		currentWarningLevel = WarningLevel.NONE;
		riskLevel = green;
		performWarningChecks(fullCode, stdOut);
	}

	public class MtxNotFoundException extends FileNotFoundException {
		/**
		 * 
		 */
		private static final long serialVersionUID = -45955835909711360L;

		public MtxNotFoundException(String mtxCode, DcfType type) {
			super();
		}
	}

	private void loadFileData() throws InterruptedException {

		forbiddenProcesses = loadForbiddenProcesses(GlobalUtil.getBRData());
		warningMessages = loadWarningMessages(GlobalUtil.getBRMessages());

	}

	/**
	 * Print the warning messages
	 * 
	 * @param event
	 * @param postMessageString
	 * @param attachDatetime
	 * @param stdOut
	 */
	protected void printWarning(WarningEvent event, String postMessageString, boolean attachDatetime, boolean stdOut) {

		// if no error return
		if (event == WarningEvent.BaseTermSuccessfullyAdded)
			return;

		// create the warning message to be printed
		String message = createMessage(event, postMessageString, attachDatetime);

		// add warning to the list to print
		messages += message + "|";

		// get the warning levels for making colours
		WarningLevel semaphoreLevel = getSemaphoreLevel(event);

		Color warningColor; // semaphore color

		// semaphore colour based on warning level
		switch (semaphoreLevel) {
		case HIGH:
			warningColor = orange;
			break;
		case LOW:
			warningColor = yellow;
			break;
		case NONE:
			warningColor = green;
			break;
		default:
			warningColor = red;
		}

		// if the warning level of this message is greater than or equal the previous
		if (semaphoreLevel.ordinal() >= currentWarningLevel.ordinal()) {

			// update the currentWarningLevel
			currentWarningLevel = semaphoreLevel;

			// update the color
			riskLevel = warningColor;
		}
	}

	/**
	 * get the risk level
	 * 
	 * @return
	 */
	public WarningLevel getCurrentWarningLevel() {
		return currentWarningLevel;
	}

	/**
	 * @return the messages
	 */
	public String getMessages() {
		return messages.replace('|', '\n');
	}

	/**
	 * @return the riskLevel
	 */
	public Color getRiskColor() {
		return riskLevel;
	}

	public Color getTxtColor() {
		return red;
	}

	/**
	 * set the warning colors
	 */
	private void setColors(Display display) {
		red = display.getSystemColor(SWT.COLOR_RED);
		orange = new Color(display, 255, 165, 0);
		yellow = display.getSystemColor(SWT.COLOR_YELLOW);
		green = display.getSystemColor(SWT.COLOR_GREEN);
	}
}
