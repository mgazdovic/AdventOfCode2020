package adventOfCode2020;

import java.util.*;

public class Day06 {

	public static int getUniqueAnswerCount(List<List<String>> input) {
		HashSet<Character> groupAnswerSet;
		int count = 0;
		
		for (List<String> group : input) {
			groupAnswerSet = new HashSet<Character>();
			for (String answers : group) {
				for (Character answer : answers.toCharArray()) {
					groupAnswerSet.add(answer);
				}
			}
			count += groupAnswerSet.size();
		}
		
		return count;
	}
	
	public static int getAllAnswerCount(List<List<String>> input) {
		HashMap<Character, Integer> groupAnswersCount;
		int count = 0;
		
		for (List<String> group : input) {
			groupAnswersCount = new HashMap<Character, Integer>();
			for (String answers : group) {
				for (Character answer : answers.toCharArray()) {
					if (groupAnswersCount.containsKey(answer)) {
						groupAnswersCount.put(answer, groupAnswersCount.get(answer) + 1);
					}
					else {
						groupAnswersCount.put(answer, 1);
					}
				}
			}
			for (Integer answerCount : groupAnswersCount.values()) {
				if (answerCount == group.size()) {
					count++;
				}
			}
		}
		
		return count;
	}
	
	private static List<List<String>> getInput() {
		List<List<String>> groups = new ArrayList<List<String>>();
		List<String> answers = new ArrayList<String>();
		
		for (String item : Util.getInputAsStringCollection("Inputs\\Day06.txt"))
		{	
			if (!item.isBlank()) 
			{
				answers.add(item);
			}
			else 
			{
				groups.add(answers);
				answers = new ArrayList<String>();
			}
		}
		if (answers.size() > 0)
			groups.add(answers);
		
		return groups;
	}
	
	public static void main(String[] args) {
		final List<List<String>> input = getInput();
		
		System.out.printf("Total unique group answers count is %d.", getUniqueAnswerCount(input));	// PART 1
		System.out.println();
		System.out.printf("Total all group answers count is %d.", getAllAnswerCount(input));		// PART 2
	}

}
