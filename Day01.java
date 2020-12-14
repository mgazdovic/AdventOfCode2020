/**
 * Advent Of Code 2020
 *  >>> www.adventofcode.com/2020
 * 
 * This file contains solutions to day 1.
 * 
 * @author Mislav.Gazdovic, mislav.gazdovic@gmail.com
 *
 */

package adventOfCode2020;

import java.util.HashSet;

public class Day01 {
	
	
	public static Long TwoSum(HashSet<Long> numSet, long targetSum) {
		Util.notEmptyOrThrow(numSet);
		if (numSet.size() == 1) return null;
			
		for (long num : numSet) {
			if (numSet.contains(targetSum - num)) {
				return Long.valueOf(num*(targetSum - num));
			}
		}
		
		return null;
	}
	
	
	public static Long ThreeSum(HashSet<Long> numSet, long targetSum) {	
		Util.notEmptyOrThrow(numSet);
		
		Long twoSum;
		for (long num : numSet) {
			twoSum = TwoSum(numSet, targetSum - num);
			if (twoSum == null) continue;
			return num * twoSum;
		}
		
		return null;
	}
	
	private static HashSet<Long> getInput() {
		HashSet<Long> numSet = new HashSet<Long>();
		
		for (String item : Util.getInputAsStringCollection("Inputs\\Day01.txt")) {
			numSet.add(Long.parseLong(item));
		}
		
		return numSet;
	}
		
	// Driver
	public static void main(String[] args) {
		Long targetSum = 2020L;
		HashSet<Long> numSet = getInput();
		
		System.out.printf("The TwoSum result is %d.\n", TwoSum(numSet, targetSum));	// PART 1
		System.out.printf("The ThreeSum result is %d. ", ThreeSum(numSet, targetSum));	// PART 2
	}
		
}
