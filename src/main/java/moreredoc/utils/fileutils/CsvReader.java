package moreredoc.utils.fileutils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvReader {

	/**
	 * Parses a csv file and breaks it down into a list of lists, which contain each rows values
	 * @param filepath Path to the input file
	 * @param delimiter delimiter of the csv, normally a comma
	 * @return
	 */
	public List<List<String>> readCsv(String filepath, String delimiter) {
		List<List<String>> result = new ArrayList<List<String>>();

		String line = "";

		try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
			
			line = br.readLine();

			while (line != null) {
				result.add(Arrays.asList(line.split(delimiter)));
				line = br.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;

	}
	

}
