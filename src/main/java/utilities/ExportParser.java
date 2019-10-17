package utilities;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.TermRow;

/**
 * class used for exporting FoodEx2 codes from the tool to an external file that
 * can be used for submission
 * 
 * @author shahaal
 *
 */
public class ExportParser {

	private String CSV_FILE_NAME = null;
	private List<String> dataLines = new ArrayList<>();

	public ExportParser(String filename, List<TermRow> list) {
		this.CSV_FILE_NAME = filename;
		// fill the list of codes
		for(TermRow term : list)
			this.dataLines.add(term.getCode());
	}
	
	/**
	 * escape special chars if found any
	 * 
	 * @param data
	 * @return
	 */
	public String escapeSpecialCharacters(String data) {
		String escapedData = data.replaceAll("\\R", " ");
		if (data.contains(",") || data.contains("\"") || data.contains("'")) {
			data = data.replace("\"", "\"\"");
			escapedData = "\"" + data + "\"";
		}
		return escapedData;
	}

	/**
	 * the method convert a list of string in string delimited by comma
	 * 
	 * @param data
	 * @return
	 */
	public String convertToCSV(String data) {
		return Stream.of(data).map(this::escapeSpecialCharacters).collect(Collectors.joining(","));
	}

	public void saveToFile() throws IOException {

		File csvOutputFile = new File(CSV_FILE_NAME);
		try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
			dataLines.stream().map(this::convertToCSV).forEach(pw::println);
		}

		assertTrue(csvOutputFile.exists());
	}
}
