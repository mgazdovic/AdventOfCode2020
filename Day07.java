/**
 * Advent Of Code 2020
 *  >>> www.adventofcode.com/2020
 * 
 * This file contains solutions to day 7.
 * 
 * @author Mislav.Gazdovic, mislav.gazdovic@gmail.com
 *
 */

package adventOfCode2020;

import java.util.*;

public class Day07 {

	public static class Bag {
		private Map<Bag, Integer> contents = new HashMap<Bag, Integer>();
		private Set<Bag> parents = new HashSet<Bag>();
		private String color;
		
		public Bag(String color) {
			this.color = color;
		}
		
		public String getColor() {
			return this.color;
		}
		
		public Set<Bag> getParents() {
			return parents;
		}
		
		public Set<Bag> getUniquePredecessors() {
			Set<Bag> predecessors = new HashSet<Bag>();
			getUniquePredecessors(this, predecessors, new HashSet<Bag>());
			return predecessors;
		}
		
		private void getUniquePredecessors(Bag bag, Set<Bag> predecessors, Set<Bag> memo) {
			if (!bag.hasParents()) return;
			if (memo.contains(bag)) return;
			for (Bag parent : bag.getParents()) {
				predecessors.add(parent);
				getUniquePredecessors(parent, predecessors, memo);
			}
			
			memo.add(bag);
		}
		
		public int getSuccessorBagCount() {
			return getSuccessorBagCount(this, new HashMap<Bag, Integer>());
		}
		
		private int getSuccessorBagCount(Bag bag, Map<Bag, Integer> memo) {
			if (bag.getContents().size() == 0) { 
				memo.put(bag, 0);
				return 0;
			}
			if (memo.containsKey(bag)) return memo.get(bag);
			
			int count = 0; int childCount = 0;
			for (Bag child : bag.getContents().keySet()) {
				childCount = bag.getContents().get(child);
				count += childCount * ( 1 + getSuccessorBagCount(child, memo) ); // bag (1) + all contents within (recursively)
			}
			
			memo.put(bag, count);
			return count;
		}
		
		private void addParent(Bag bag) {
			this.parents.add(bag);
		}
		
		public boolean hasParents() {
			return !parents.isEmpty();
		}
		
		public void add(Bag bag, int addCount) {
			int bagCount = contents.getOrDefault(bag, 0) + addCount;
			contents.put(bag, bagCount);
			bag.addParent(this);
		}
		
		public Map<Bag, Integer> getContents() {
			return contents;
		}
		
	}
	
	public static Map<String, Bag> getInput() {
		try {
			Map<String, Bag> bags = new HashMap<String, Bag>();	
			
			for (String description : Util.getInputAsStringCollection("Inputs\\Day07.txt")) {
								
				int bagColorEndIndex = description.indexOf("bags") - 1;
				String bagColor = description.substring(0, bagColorEndIndex);
								
				if (!bags.containsKey(bagColor)) {
					bags.put(bagColor, new Bag(bagColor));
				}
				Bag bag = bags.get(bagColor);
				
				if (description.contains("no other bags")) continue;
				
				int index = description.indexOf("contain") + 8; // 8 = "contain" + (one) space
				while (true) {
					int count = Integer.parseInt(description.substring(index, index + 1));
					bagColorEndIndex = description.indexOf("bag", index) - 1;
					bagColor = description.substring(index + 2, bagColorEndIndex);
					
					if (!bags.containsKey(bagColor)) {
						bags.put(bagColor, new Bag(bagColor));
					}
					bag.add(bags.get(bagColor), count);
					
					index = description.indexOf(',', bagColorEndIndex);
					if (index == -1) break; // no next comma found, done
					index += 2; // 2 = ", "
				}
				
			}
			
			return bags;
		}
		catch (Exception e) {
			 throw new IllegalArgumentException("Bag creation failed, bad description input...\n" + e.getMessage());
		}	
	}
			
	// Driver	
	public static void main(String[] args) {
		Map<String, Bag> bags = getInput();
		Bag bag = bags.get("shiny gold"); 
					
		System.out.printf("Total predecessor bag count is %d.\n", bag.getUniquePredecessors().size());	// PART 1
		System.out.printf("Total successor bag count is %d.", bag.getSuccessorBagCount());				// PART 2
	}

}