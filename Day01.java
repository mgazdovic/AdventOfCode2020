package adventOfCode2020;

import java.util.*;
import java.io.IOException;

public class Day01 {
	
	public static long TwoSum(HashSet<Integer> input, int targetSum) {
		for (int num : input)
		{
			if (input.contains(targetSum - num))
				return num*(targetSum - num);
		}
				
		throw new IllegalArgumentException("The input does not contain targetSum!");
	}
	
	public static long ThreeSum(HashSet<Integer> input, int targetSum) {	
		for (int num : input)
		{
			try {
				return num * TwoSum(input, targetSum - num);
			}
			catch (IllegalArgumentException e) {
				continue;
			}
		}
		
		throw new IllegalArgumentException("The input does not contain targetSum!");
	}
	
	private static HashSet<Integer> getInput() {
		HashSet<Integer> numSet = new HashSet<Integer>();
		for (String item : Util.getInputAsStringCollection("Inputs\\Day01.txt"))
		{
			numSet.add(Integer.parseInt(item));
		}
		return numSet;
	}
	
		
	// Driver
	public static void main(String[] args) throws IOException {

		final int TARGET = 2020;
		final HashSet<Integer> input = getInput();
		
		System.out.printf("The TwoSum result is %d. ", TwoSum(input, TARGET));		// PART 1
		System.out.println();
		System.out.printf("The ThreeSum result is %d. ", ThreeSum(input, TARGET));	// PART 2
	}
		
}


