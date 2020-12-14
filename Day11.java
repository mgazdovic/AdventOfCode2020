/**
 * Advent Of Code 2020
 *  >>> www.adventofcode.com/2020
 * 
 * This file contains solutions to day 11.
 * 
 * @author Mislav.Gazdovic, mislav.gazdovic@gmail.com
 *
 */

package adventOfCode2020;

import java.util.*;

public class Day11 {

	final static char FLOOR = '.';
	final static char EMPTY = 'L';
	final static char OCCUPIED = '#';
	
	public static int getStableCount(char[][] input, char state, boolean onlyAdjecant, int countTreshold) {
		if (input == null || input.length == 0 || input[0].length == 0) 
			return 0;
		
		char[][] seats = getDeepCopy(input);
		char[][] prev = seats; // save previous reference
		
		while (true) {
			seats = updateAllSeats(seats, onlyAdjecant, countTreshold); 
			if (seats == prev) break; // same instance (no updates) --> stable state
			prev = seats;		
		}
		
		return getSeatCount(seats, state);
	}
	
	private static char[][] getDeepCopy(char[][] existing) {
		if (existing == null || existing.length == 0 || existing[0].length == 0)
			throw new IllegalArgumentException("Input has to contains elements...");
		
		int rowCount = existing.length;
		int colCount = existing[0].length;
		
		char[][] copy = new char[rowCount][colCount];
		for(int row = 0; row < rowCount; row++) {
			for (int col = 0; col < colCount; col++) {
				copy[row][col] = existing[row][col];
			}
		}
		
		return copy;
	}
	
	private static int getSeatCount(char[][] seats, char state) {
		int count = 0;
		for(int row = 0; row < seats.length; row++) {
			for (int col = 0; col < seats[0].length; col++) {
				if (seats[row][col] == state) count++;
			}
		}
		
		return count;
	}
	
	private static char[][] updateAllSeats(char[][] seats, boolean onlyAdjecant, int countTreshold) {
		char[][] updated = getDeepCopy(seats);
		
		boolean isUpdated = false;
		for(int row = 0; row < seats.length; row++) {
			for (int col = 0; col < seats[0].length; col++) {
				char seat = seats[row][col];		
				if ((seat == EMPTY) && getNeighbourCount(seats, row, col, OCCUPIED, onlyAdjecant) == 0) {
					updated[row][col] = OCCUPIED;
					isUpdated = true;
				}
				else if ((seat == OCCUPIED) && getNeighbourCount(seats, row, col, OCCUPIED, onlyAdjecant) >= countTreshold) {
					updated[row][col] = EMPTY;
					isUpdated = true;
				}
			}
		}
		
		// return same instance (reference) if no updates, new instance otherwise
		return isUpdated ? updated : seats;
	}
		
	private static int getNeighbourCount(char[][] seats, int row, int col, char state, boolean onlyAdjecant) {
		int[][] neighbour = 
			{
				{-1, -1}, 	// upper-left
				{-1, 0}, 	// upper
				{-1, 1}, 	// upper-right
				{0, 1}, 	// right
				{1, 1}, 	// lower-right
				{1, 0}, 	// lower
				{1, -1}, 	// lower-left
				{0, -1}		// left
			};
		
		int minRow = 0, minCol = 0;
		int maxRow = seats.length - 1; 
		int maxCol = seats[0].length - 1;
		
		int count = 0;
		int checkRow, checkCol;
		for (int i = 0; i < neighbour.length; i++) {
			checkRow = row + neighbour[i][0];
			checkCol = col + neighbour[i][1];
			if (checkRow < minRow || checkCol < minCol || checkRow > maxRow || checkCol > maxCol) continue; // out of bounds
			if (!onlyAdjecant) {
				int nextRow, nextCol;
				while (seats[checkRow][checkCol] == FLOOR) {
					nextRow = checkRow + neighbour[i][0];
					nextCol = checkCol + neighbour[i][1];
					if (nextRow < minRow || nextCol < minCol || nextRow > maxRow || nextCol > maxCol) break; // out of bounds
					checkRow = nextRow;
					checkCol = nextCol;
				}
			}
			if (seats[checkRow][checkCol] == state) count++;
		}
		
		return count;
	}
	
	private static char[][] getInput() {
		Collection<String> rows = Util.getInputAsStringCollection("Inputs\\Day11.txt");
		char[][] grid = new char[rows.size()][];
		
		int rowNum = 0;
		for (String row : rows)
		{
			grid[rowNum++] = row.toCharArray();
		}
		
		return grid;
	}
	
	// Driver
	public static void main(String[] args) {
		final char[][] input = getInput();
		final boolean ONLY_ADJECANT = true;
			
		System.out.printf("The stable occupied count is %d.\n", getStableCount(input, OCCUPIED, ONLY_ADJECANT, 4));				// PART 1
		System.out.printf("The extended stable occupied count is %d. ", getStableCount(input, OCCUPIED, !ONLY_ADJECANT, 5));	// PART 2
	}

}