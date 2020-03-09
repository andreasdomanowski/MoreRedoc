package moreredoc.utils.fileutils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

public class CsvReader {
	
	private static Logger logger = Logger.getLogger(CsvReader.class);
	
	/**
	 * Not to be instantiated
	 */
	private CsvReader(){
		
	}

	/**
	 * Parses a csv file and breaks it down into a list of lists, which contain each rows values
	 * @param filepath Path to the input file
	 * @param delimiter delimiter of the csv, normally a comma
	 * @return
	 */
	public static List<List<String>> readCsv(String filepath, String delimiter) {
		List<List<String>> result = new ArrayList<>();

		String line = "";

		try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
			
			line = br.readLine();

			while (line != null) {
				result.add(Arrays.asList(line.split(delimiter)));
				line = br.readLine();
			}

		} catch (IOException e) {
			logger.error("Error while parsing CSV" + e);
		}
		
		return result;

	}
	

}
