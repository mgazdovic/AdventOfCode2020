package adventOfCode2020;

import java.util.*;

public class Day05 {

	public static int getMissingSeatID(Collection<String> input) {
		HashSet<Integer> seatIDs = getSeatIDs(input);
		int maxSeatID = getMaxSeatID(input);
		
		for (int ID = 1; ID < maxSeatID; ID++) {
			if (!seatIDs.contains(ID) && seatIDs.contains(ID-1) && seatIDs.contains(ID+1)) {
				return ID;
			}
		}
		
		// not found
		return -1;
	}
	
	private static HashSet<Integer> getSeatIDs (Collection<String> input) {
		HashSet<Integer> seatIDs = new HashSet<Integer>();
		for (String seatPartitioning : input) {
			seatIDs.add(getSeatID(seatPartitioning));
		}
		return seatIDs;
	}
	
	public static int getMaxSeatID(Collection<String> input) {
		int maxSeatID = 0;
		for (String seatPartitioning : input) {
			maxSeatID = Math.max(getSeatID(seatPartitioning), maxSeatID);
		}
		return maxSeatID;
	}
	
	private static int getSeatID(String partitioning) {
		
		if (partitioning.length() != 10) {
			throw new IllegalArgumentException("Partitioning string must be 10 characters long...");
		}
		
		String rowPartitioning = partitioning.substring(0, 7); 	// 7 letters (Front/Back)
		String colPartitioning = partitioning.substring(7, 10);	// 3 letters (Left/Right)
		
		int row = partitionBinarySearch(rowPartitioning, 'F', 'B'); 	
		int col = partitionBinarySearch(colPartitioning, 'L', 'R');	
	
		// seat ID = 8 * row + col
		return 8 * row + col;
	}
	
	private static int partitionBinarySearch(String partitioning, char lowerPartition, char upperPartition) {
		int partitionCount = partitioning.length();
		
		int lower = 0;
		int upper = ((int)Math.pow(2, partitionCount) - 1);
				
		int pos = (lower + upper) >>> 1;
		for (int i = 0; i < partitionCount; i++) {
			if (partitioning.charAt(i) == lowerPartition) {
				upper = pos;
			}
			else if (partitioning.charAt(i) == upperPartition) {
				lower = pos + 1;
			}
			else {
				throw new IllegalArgumentException("Partitioning string contains illegal characters.");
			}	
			// System.out.println("Look in positions " + lower + " to " + upper);
			pos = (lower + upper) >>> 1;
		}
				
		return pos;
	}
	
	private static Collection<String> getInput() {
		List<String> stringList = new ArrayList<String>();
		stringList.addAll(Util.getInputAsStringCollection("Inputs\\Day05.txt"));
		return stringList;
	}
	
	// Driver
	public static void main(String[] args) {
		final Collection<String> input = getInput();
		
		System.out.printf("Max seat ID is %d.", getMaxSeatID(input));			// PART 1
		System.out.println();
		System.out.printf("Missing seat ID is %d.", getMissingSeatID(input));	// PART 2
		
	}

}
