package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.TermRow;

/**
 * Read list of code terms in file
 * composed by the term code column
 * @author shahaal
 *
 */
public class ImportParser {

	private static final Logger LOGGER = LogManager.getLogger(ImportParser.class);
	
	private String currentLine;
	private BufferedReader reader;
	
	public ImportParser (String filename) {
		
		File file = new File( filename );
		
		if ( !file.exists() ) {
			LOGGER.error ( "The file " + filename + " does not exist" );
			return;
		}
		
		try {
			
			// prepare the reader
			reader = new BufferedReader( new FileReader( filename ) );
			
			// skip headers
			reader.readLine();
			
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error("Cannot open file=" + filename, e);
		}
	}
	
	public boolean hasNext() throws IOException {
		
		currentLine = reader.readLine();
		
		return currentLine != null;
	}
	
	/**
	 * Parse a line and get the current term
	 * @param inputFilename
	 * @param delim
	 * @throws IOException 
	 */
	public TermRow nextTerm() {

		try {
			if ( !hasNext() ) {
				reader.close();
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error("Cannot get next term", e);
		}

		// get the variables from the tokens
		String code = currentLine;

		// create the term row and return it
		return new TermRow(code);
	}
}

