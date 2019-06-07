package model;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * This class provides the content for the table
 */

public class TermRowContentProvider implements IStructuredContentProvider {

  /**
   * Gets the elements for the table
   * 
   * @param arg0
   *            the model
   * @return Object[]
   */
  public Object[] getElements(Object arg0) {
    // Returns all the players in the specified team
    return ((TermRowTableModel) arg0).getTerms().toArray();
  }

  /**
   * Disposes any resources
   */
  public void dispose() {
    // We don't create any resources, so we don't dispose any
  }

  /**
   * Called when the input changes
   * 
   * @param viewer
   *            the parent viewer
   * @param newInput
   *            the old input
   * @param newInput
   *            the new input
   */
  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	  //ignore
  }
}
