package model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TermRowTableModel {

	public List<TermRow> terms;
	
	public TermRowTableModel() {
		terms=new LinkedList<TermRow>();
	}
	
	/**
	 * Adds a term
	 * 
	 * @param term the term to add
	 * @return boolean
	 */
	public boolean add(TermRow term) {
		return terms.add(term);
	}

	/**
	 * Gets the terms
	 * 
	 * @return List
	 */
	public List<TermRow> getTerms() {
		return Collections.unmodifiableList(terms);
	}

	/**
	 * remove everything from the model
	 */
	public void clear() {
		terms.clear();		
	}

	/**
	 * return a term in the specified index
	 * @param index
	 * @return
	 */
	public TermRow getTermAt(int index) {
		return terms.get(index);
	}

	/**
	 * set a term in the specified index
	 * @param index
	 * @param term
	 */
	public void setTermAt(int index, TermRow term) {
		terms.set(index, term);
	}

	/**
	 * return the size of the list of terms
	 * @return
	 */
	public int getSize() {
		return terms.size();
	}
}
