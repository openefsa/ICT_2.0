package model;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

public class TermRowLabelProvider implements ITableLabelProvider, ITableColorProvider {
	
	@Override
	public void addListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Image getColumnImage(Object arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object arg0, int arg1) {
		
		String result = "";
		
        TermRow term = (TermRow) arg0;
        switch (arg1) {
            case 0:  // COMPLETED_COLUMN
            	result = term.getFreeText();
                break;
            case 1 :
                result = term.getCode();
                break;
            }
        
        return result;
	}

	@Override
	public Color getBackground(Object arg0, int arg1) {
		return (arg1 == 2) ?((TermRow) arg0).getRisk() : null;
	}

	@Override
	public Color getForeground(Object arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
