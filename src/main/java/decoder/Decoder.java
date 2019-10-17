package decoder;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import business_rules.TermRules;
import catalogue.Catalogue;
import catalogue_browser_dao.CatalogueDAO;
import dcf_manager.Dcf.DcfType;
import utilities.GlobalUtil;

/**
 * class used for interact with catalogue and get errors from the given FoodEx2
 * codes
 * 
 * @author shahaal
 *
 */
public class Decoder extends TermRules {

	private Display display;

	// risk
	private Color red, orange, yellow, green, currentRisk;

	// warning message
	private String messages;

	// warning level
	private WarningLevel warningLevel;

	public Decoder() {

		// set current display for colours
		display = Display.getCurrent();
		// initialise database and catalogues
		initDatabase();
		// initialise attributes
		initDecoder();
	}

	/**
	 * initialise the catalogue on which to check the codes and the database
	 */
	private void initDatabase() {

		CatalogueDAO catDao = new CatalogueDAO();

		// only in production environment the tool will perform the check
		DcfType catType = DcfType.PRODUCTION; // DcfType.LOCAL for checking also in local catalogues

		// set the catalogue code to use
		String catCode = "MTX";

		// get catalogue by its code and type
		Catalogue mtx = catDao.getLastVersionByCode(catCode, catType);

		// launch exception if not found
		if (mtx == null)
			new MTXNotFoundException(catCode, catType).printStackTrace();

		// set the current catalogue
		currentCat = mtx;

		// load data in memory
		System.err.println("Loading catalogue data into RAM...");
		currentCat.loadData();

	}

	/**
	 * initialise the decoder's external files and risk colours
	 */
	private void initDecoder() {

		// load external files in memory
		forbiddenProcesses = loadForbiddenProcesses(GlobalUtil.getBRData());
		warningMessages = loadWarningMessages(GlobalUtil.getBRMessages());

		// initialise the colours and attributes
		initColors();
		initAttributes();
	}

	/**
	 * initialise risk colours
	 */
	private void initColors() {

		red = display.getSystemColor(SWT.COLOR_RED);
		orange = new Color(display, 255, 165, 0);
		yellow = display.getSystemColor(SWT.COLOR_YELLOW);
		green = display.getSystemColor(SWT.COLOR_GREEN);
	}

	/**
	 * initialise the attributes of the class
	 * 
	 */
	private void initAttributes() {
		messages = "";
		warningLevel = WarningLevel.NONE;
		currentRisk = green;
	}

	/**
	 * check the code and print out the results
	 * 
	 * @param fullCode
	 * @param stdOut
	 */
	public void performCheck(String fullCode, boolean stdOut) {
		// initialise the attributes
		initAttributes();
		// perform the check on the code passed in input
		performWarningChecks(fullCode, stdOut, true);
	}

	/**
	 * @return the currentRisk
	 */
	public Color getCurrentRisk() {
		return currentRisk;
	}

	/**
	 * @return the messages
	 */
	public String getMessages() {
		return messages;
	}

	@Override
	protected void printWarning(WarningEvent event, String postMessageString, boolean attachDatetime, boolean stdOut) {

		// if no error return
		if (event == WarningEvent.BaseTermSuccessfullyAdded)
			return;

		// create the warning message to be printed
		String message = createMessage(event, postMessageString, attachDatetime);

		// add warning to the list to print
		messages += message + "\n";

		// get the warning levels for making colours
		WarningLevel semaphoreLevel = getSemaphoreLevel(event);

		Color warningColor; // semaphore colour

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
		if (semaphoreLevel.ordinal() >= warningLevel.ordinal()) {

			// update the currentWarningLevel
			warningLevel = semaphoreLevel;

			// update the colour
			currentRisk = warningColor;
		}
	}

}