/**
 * Advent Of Code 2020
 *  >>> www.adventofcode.com/2020
 * 
 * This file contains solutions to day 10.
 * 
 * @author Mislav.Gazdovic, mislav.gazdovic@gmail.com
 *
 */

package adventOfCode2020;

import java.util.Arrays;
import java.util.HashSet;

public class Day10 {

	public static Integer getJoltDiffProduct(HashSet<Integer> input, int maxJoltDiff) {
		Util.notEmptyOrThrow(input);
		
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		
		for (Integer num : input) {
			min = Math.min(num, min);
			max = Math.max(num, max);
		}
		
		if (maxJoltDiff <= 0) throw new IllegalArgumentException("Max jolt diff must be greater than 0.");
		
		int diff = 1; // initialize diff to additional adapter (0)
		int[] count = new int[maxJoltDiff + 1]; // count[1] corresponds to diff = 1 (count[0] not used)
		for(int i = min; i <= max; i++) {
			if (!input.contains(i)) {
				diff++;
				continue;
			}
			if (diff > maxJoltDiff) {
				return null;
			}
			count[diff]++;
			diff = 1;
		}
		
		count[3]++; // one extra adapter with diff 3 accounted for
			
		return count[1] * count[3];
	}
	
	public static Long getDistinctArrangementCount(HashSet<Integer> input, int maxJoltDiff) {
		Util.notEmptyOrThrow(input);
		
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for (Integer num : input) {
			min = Math.min(num, min);
			max = Math.max(num, max);
		}
		
		if (maxJoltDiff <= 0) throw new IllegalArgumentException("Max jolt diff must be greater than 0.");
		if (max > min + maxJoltDiff * input.size()) return 0L; // terminate early if not possible to find a chain
		
		int adapterRange = max - min + 1;
		Long[] dp = new Long[adapterRange + 1];
		Arrays.fill(dp, 0L);
		
		dp[0] = 1L; // base case (additional adapter 0)
		for (int i = min; i <= max; i++) {
			int index = i - min + 1; // corresponding index in dp array
			Long dpPrev = 0L;
			for (int diff = 1; diff <= maxJoltDiff; diff++) { 
				int adapter = i-diff;
				int prevIndex = index - diff;
				if ((input.contains(adapter) || adapter == 0) && (prevIndex >= 0)) { 
					dpPrev += dp[prevIndex];
				}
				
			}
			dp[index] += dpPrev; // dp = sum of previous solutions (up to maxJoltDiff levels before)
		}
		
		return dp[adapterRange];
	}
	
	private static HashSet<Integer> getInput() {
		HashSet<Integer> numSet = new HashSet<Integer>();
		
		for (String item : Util.getInputAsStringCollection("Inputs\\Day10.txt"))
		{
			numSet.add(Integer.parseInt(item));
		}
		
		return numSet;
	}
	
	// Driver
	public static void main(String[] args) {
		HashSet<Integer> input = getInput();
		final int MAX_JOLT_DIFF = 3;
		
		System.out.printf("The jolt diff 1 and 3 product is %d.\n", getJoltDiffProduct(input, MAX_JOLT_DIFF));			// PART 1
		System.out.printf("Number of distinct arrangements is %d.", getDistinctArrangementCount(input, MAX_JOLT_DIFF));	// PART 2
	}

}