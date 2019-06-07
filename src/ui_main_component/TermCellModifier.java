package ui_main_component;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Item;

import model.TermRow;

/**
 * This class represents the cell modifier for the term selected in the table
 */
public class TermCellModifier implements ICellModifier {

	private Viewer viewer;

	public TermCellModifier(Viewer viewer) {
		this.viewer = viewer;
	}

	/**
	 * Returns whether the property can be modified
	 * 
	 * @param element  the element
	 * @param property the property
	 * @return boolean
	 */
	public boolean canModify(Object element, String property) {
		// Allow editing of all values (otherwise specify below he columns)
		return true;
	}

	/**
	 * Returns the value for the property
	 * 
	 * @param element  the element
	 * @param property the property
	 * @return Object
	 */
	public Object getValue(Object element, String property) {
		
		TermRow t = (TermRow) element;
		if (TablePanel.FREE.equals(property))
			return t.getFreeText();
		else if (TablePanel.CODE.equals(property))
			return t.getCode();
		else
			return null;
	}

	/**
	 * Modifies the element
	 * 
	 * @param element  the element
	 * @param property the property
	 * @param value    the value
	 */
	public void modify(Object element, String property, Object value) {
		
		if (element instanceof Item)
			element = ((Item) element).getData();

		TermRow t = (TermRow) element;
		if (TablePanel.FREE.equals(property))
			t.setFreeText((String) value);
		else if (TablePanel.CODE.equals(property))
			t.setCode((String) value);
		
		// Force the viewer to refresh
		viewer.refresh();
		
	}
}
