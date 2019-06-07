package model;

import org.eclipse.swt.graphics.Color;

public class TermRow {

	private Color risk;
	private String addText, code, warning;

	/**
	 * Constructs an empty term
	 */
	public TermRow() {
		this("New Term", "Insert the code here", "", null);
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
		this.addText = text;
		this.code = code;
		this.warning = warning;
		this.risk = risk;
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
		this.risk = risk;
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
		this.code = code;
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
		this.warning = warning;
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
		this.addText = addText;
	}
}
