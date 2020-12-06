package adventOfCode2020;

import java.util.*;

public class Day03 {

	public static int treeCount(List<String> grid, int hop_horinzontal, int hop_vertical) {
		
		final int PATTERN_LENGTH = grid.get(0).length();
		final char TREE = '#';
		int treeCount = 0;
		
		for (int posX = 0, posY = 0; posY < grid.size(); posX = (posX + hop_horinzontal) % PATTERN_LENGTH, posY += hop_vertical) 
		{
			if (grid.get(posY).charAt(posX) == TREE) treeCount++;
		}
	
		return treeCount;
	}
	
	private static List<String> getInput() {
		List<String> grid = new ArrayList<String>();
		grid.addAll(Util.getInputAsStringCollection("Inputs\\Day03.txt"));
		return grid;
	}
	
	// Driver
	public static void main(String[] args) {
		
		final List<String> grid = getInput();
		
		System.out.printf("The number of trees encountered is %d.", treeCount(grid, 3, 1));	// PART 1
		System.out.println();
		
		// Check the product of the following tree count results using hops:				// PART 2
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
