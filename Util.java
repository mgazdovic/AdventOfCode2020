package adventOfCode2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Util {
	/// Helper method to read input file and return a string collection
	public static Collection<String> getInputAsStringCollection(String path) {
		try {
			Collection<String> input = new ArrayList<String>();
			for (String row : Files.readAllLines(Paths.get(path)))
			{
				input.add(row);
			}
			return input;
		} catch (IOException e) {
			System.out.println("Error reading input file...");
			e.printStackTrace();
			return null;
		}
	}
}
