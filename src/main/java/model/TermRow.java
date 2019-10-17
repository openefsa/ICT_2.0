package model;

import org.eclipse.swt.graphics.Color;

/**
 * the class represents the row object in the table
 * 
 * @author shahaal
 *
 */
public class TermRow extends ModelObject {

	private Color risk;
	private String addText, code, warning;

	/**
	 * Constructs an empty term
	 */
	public TermRow() {
		this("New Term", "", "", null);
	}

	/**
	 * Constructs term only code
	 */
	public TermRow(String code) {
		this("", code, "", null);
	}

	/**
	 * constructor with additional text
	 * 
	 * @param text
	 * @param code
	 * @param warning
	 * @param risk
	 */
	public TermRow(String text, String code, String warning, Color risk) {
		super();
		setFreeText(text);
		setCode(code);
		setWarning(warning);
		setRisk(risk);
	}

	/**
	 * @return the risk
	 */
	public Color getRisk() {
		return risk;
	}

	/**
	 * @param risk the risk to set
	 */
	public void setRisk(Color risk) {
		firePropertyChange("risk", this.risk, this.risk = risk);
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		firePropertyChange("code", this.code, this.code = code);
	}

	/**
	 * @return the warning
	 */
	public String getWarning() {
		return warning;
	}

	/**
	 * @param warning the warning to set
	 */
	public void setWarning(String warning) {
		firePropertyChange("warning", this.warning, this.warning = warning);
	}

	/**
	 * @return the addText
	 */
	public String getFreeText() {
		return addText;
	}

	/**
	 * @param addText the addText to set
	 */
	public void setFreeText(String addText) {
		firePropertyChange("addText", this.addText, this.addText = addText);
	}

	/**
	 * construct obj from another one
	 * 
	 * @param term
	 */
	public TermRow(TermRow term) {
		this(term.addText, term.code, term.warning, term.risk);
	}
}
