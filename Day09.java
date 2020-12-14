/**
 * Advent Of Code 2020
 *  >>> www.adventofcode.com/2020
 * 
 * This file contains solutions to day 9.
 * 
 * @author Mislav.Gazdovic, mislav.gazdovic@gmail.com
 *
 */

package adventOfCode2020;

import java.util.*;

public class Day09 {

	public static Long getEncryptionWeakness(ArrayList<Long> input, Long targetSum) {
		int lowIndex = 0, highIndex = 1;
		Long sum = input.get(lowIndex) + input.get(highIndex);
		boolean found = false;
		boolean highIndexDone = false, lowIndexDone = false, indexDiffOK = true;
		while (!highIndexDone || !lowIndexDone) {
			if (sum.compareTo(targetSum) == 0) {
				found = true;
				break;
			}
			if ((sum.compareTo(targetSum) < 0 && !highIndexDone) || (!indexDiffOK)) {
				sum += input.get(++highIndex);
				highIndexDone = highIndex == input.size() - 1;
			}
			else {
				sum -= input.get(lowIndex++);
				lowIndexDone = lowIndex == input.size() - 1;
			}
			indexDiffOK = (highIndex - lowIndex) > 0; // both indexes are inclusive, so this enforces a minimum of 2 elements
		}
		
		if (!found) return null;
		
		Long min = input.get(lowIndex), max = input.get(lowIndex), current;
		for (int i = lowIndex + 1; i <= highIndex; i++) {
			current = input.get(i);
			min = Math.min(current, min);
			max = Math.max(current, max);
		}
		
		return min + max;
	}
	
	public static Long getFirstInvalid(ArrayList<Long> input, int preambleSize) {
		if (preambleSize > input.size()) throw new IllegalArgumentException("Preamble size cannot be larger than input list size.");
		if (preambleSize <= 0) throw new IllegalArgumentException("Preamble size has to be greater than 0.");
		
		HashSet<Long> preamble = new HashSet<Long>();
		
		Iterator<Long> iter = input.iterator();
		while (preamble.size() < preambleSize) {
			preamble.add(iter.next());
		}
		
		Long current;
		int preambleIndex = 0;
		while (iter.hasNext()) {
			current = iter.next();
			if (Day01.TwoSum(preamble, current) == null) {
				return current;
			}
			preamble.remove(input.get(preambleIndex++));
			preamble.add(current);
		}
		
		return null;
	}
	
	private static ArrayList<Long> getInput() {
		ArrayList<Long> numList = new ArrayList<Long>();
		
		for (String num : Util.getInputAsStringCollection("Inputs\\Day09.txt")) {
			numList.add(Long.parseLong(num));
		}
		
		return numList;
	}
	
	// Driver
	public static void main(String[] args) {
		ArrayList<Long> input = getInput();
		final int PREAMBLE_SIZE = 25;
		
		Long firstInvalid = getFirstInvalid(input, PREAMBLE_SIZE); 
		
		System.out.printf("First invalid value is %d.\n", firstInvalid);								// PART 1
		System.out.printf("Encryption weakness is %d.", getEncryptionWeakness(input, firstInvalid));	// PART 2
	}

}