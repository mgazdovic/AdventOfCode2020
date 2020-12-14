/**
 * Advent Of Code 2020
 *  >>> www.adventofcode.com/2020
 * 
 * This file contains solutions to day 3.
 * 
 * @author Mislav.Gazdovic, mislav.gazdovic@gmail.com
 *
 */

package adventOfCode2020;

import java.util.Collection;

public class Day03 {
	
	public static int treeCount(char[][] grid, int hop_horinzontal, int hop_vertical) {		
		final int PATTERN_LENGTH = grid[0].length;
		final char TREE = '#';
		
		int treeCount = 0;
		for (int row = 0, col = 0; row < grid.length; row += hop_vertical, col = (col + hop_horinzontal) % PATTERN_LENGTH) 
		{
			if (grid[row][col] == TREE) treeCount++;
		}
	
		return treeCount;
	}
	
	private static char[][] getInput() {
		Collection<String> input = Util.getInputAsStringCollection("Inputs\\Day03.txt");
		char[][] grid = new char[input.size()][];
		
		int i = 0;
		for(String row : input) {
			grid[i++] = row.toCharArray();
		}
		
		return grid;
	}
	
	// Driver
	public static void main(String[] args) {
		char[][] grid = getInput();
		
		System.out.printf("The number of trees encountered is %d.\n", treeCount(grid, 3, 1));	// PART 1
		
		// Check the product of the following tree count results using hops:					// PART 2
		/*
	    Right 1, down 1.
	    Right 3, down 1. (This is the slope you already checked.)
	    Right 5, down 1.
	    Right 7, down 1.
	    Right 1, down 2.
		 */
		
		final int HORIZONTAL = 0;
		final int VERTICAL = 1;
		int[][] hops = {
	            {1, 1},
	            {3, 1},
	            {5, 1},
	            {7, 1},
	            {1, 2}
	        };
		
		long product = 1;
		for (int[] hop : hops) {
			product *= treeCount(grid, hop[HORIZONTAL], hop[VERTICAL]);
		}
		
		System.out.printf("The product of number of trees is %d.", product);				
	}

}
