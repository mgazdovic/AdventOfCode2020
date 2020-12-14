/**
 * Advent Of Code 2020
 *  >>> www.adventofcode.com/2020
 * 
 * This file contains utility methods to fetch and validate input data. 
 * 
 * @author Mislav.Gazdovic, mislav.gazdovic@gmail.com
 *
 */

package adventOfCode2020;

import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Util {
	
	/**
	* Return a string collection (one item per row) from file at specified path. 
	*
	* @param path  Relative path to the input file
	* @throws IOException if reading file is unsuccessful
	*/
	public static Collection<String> getInputAsStringCollection(String path) {
		try {
			Collection<String> input = new ArrayList<String>();
			for (String row : Files.readAllLines(Paths.get(path)))
			{
				input.add(row);
			}
			return input;
		} catch (IOException e) {
			System.out.format("Error reading input file: \n%s\n", e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	* Checks if input collection contains any elements. 
	*
	* @throws IllegalArgumentException if input collection is null or empty.
	*/
	public static void notEmptyOrThrow(Collection<?> collection) {
		if (collection == null || collection.size() == 0) {
			throw new IllegalArgumentException("Collection has to contain at least one element...");
		}
	}
}
