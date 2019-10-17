package decoder;

import java.io.FileNotFoundException;

import dcf_manager.Dcf.DcfType;

/**
 * exception for MTX catalogue not found in the database
 * @author shahaal
 *
 */
public class MTXNotFoundException extends FileNotFoundException {
	private static final long serialVersionUID = -45955835909711360L;

	public MTXNotFoundException(String mtxCode, DcfType type) {
		super();
	}
}