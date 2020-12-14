/**
 * Advent Of Code 2020
 *  >>> www.adventofcode.com/2020
 * 
 * This file contains solutions to day 8.
 * 
 * @author Mislav.Gazdovic, mislav.gazdovic@gmail.com
 *
 */

package adventOfCode2020;

import java.util.*;

public class Day08 {

	private static class Instruction {
		private String instruction = "";
		private final int SPACE_INDEX = 3; // "acc +1" -> Space always at instruction index 3
		
		public Instruction(String instruction) {
			this.instruction = instruction;
		}
		
		public String getOperation() {
			return instruction.substring(0, SPACE_INDEX);
		}
		
		public void setOperation(String operation) {
			this.instruction = operation + (getArgument() >= 0 ? "+" : "-") + getArgument();
		}
		
		public int getArgument() {
			try {
				return Integer.parseInt(instruction.substring(SPACE_INDEX + 1, instruction.length()));
			}
			catch (Exception e) {
				return 0;
			}
		}
	}
	
	public static Integer getAccValue(Map<Integer, Instruction> instructionLines, boolean getLoopValue) {
		Map<Integer, Integer> lineExecutionCount = new HashMap<Integer, Integer>();
		
		int instructionLine = 1;
		String operation = "";
		int argument = 0;
		int accumulator = 0;
		
		while (true) {
			if (!instructionLines.containsKey(instructionLine)) {
				// successfully processed all!
				return accumulator;
			}
			
			operation = instructionLines.get(instructionLine).getOperation();
			argument = instructionLines.get(instructionLine).getArgument();
			
			if (lineExecutionCount.containsKey(instructionLine)) {
				// infinite loop reached, return null if getLoopValue is false
				return getLoopValue ? accumulator : null; 
			}
			
			lineExecutionCount.put(instructionLine, 1);
			
			switch(operation) {
				case "nop": 
					// No OPeration
					instructionLine += 1;
					break;
				case "acc":
					// accumulator value update
					accumulator += argument;
					instructionLine += 1;
					break;
				case "jmp":
					// jump offset
					instructionLine += argument;
			  default:
				  	continue;
			}	
		}
	}
	
	public static Integer getAccValueFixed(Map<Integer, Instruction> instructionLines) {
		for (Instruction instruction : instructionLines.values()) {
			if (instruction.getOperation().equals("nop")) {
				
				instruction.setOperation("jmp"); // try "nop" -> "jmp"
				Integer accValue = getAccValue(instructionLines, false);
				instruction.setOperation("nop"); // restore
				
				if (accValue != null) {
					return accValue;
				}
			}
			if (instruction.getOperation().equals("jmp")) {
				
				instruction.setOperation("nop"); // try "jmp" -> "nop"
				Integer accValue = getAccValue(instructionLines, false);
				instruction.setOperation("jmp"); // restore
				
				if (accValue != null) {
					return accValue;
				}
			}
		}
		
		return null;
	}
	
	private static Map<Integer, Instruction> getInput() {
		Map<Integer, Instruction> instructionLines = new HashMap<Integer, Instruction>();
		
		int lineNo = 1;
		for (String instruction : Util.getInputAsStringCollection("Inputs\\Day08.txt")) {
			instructionLines.put(lineNo++, new Instruction(instruction));
		}
		
		return instructionLines;
	}
	
	// Driver
	public static void main(String[] args) {
		Map<Integer, Instruction> input = getInput();
		
		System.out.printf("Accumulator value before just before infinite loop is %d.\n", getAccValue(input, true));	// PART 1
		System.out.printf("Accumulator value with fixed instruction is %d.", getAccValueFixed(input));				// PART 2
	}

}