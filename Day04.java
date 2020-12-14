/**
 * Advent Of Code 2020
 *  >>> www.adventofcode.com/2020
 * 
 * This file contains solutions to day 6.
 * 
 * @author Mislav.Gazdovic, mislav.gazdovic@gmail.com
 *
 */

package adventOfCode2020;

import java.util.*;

public class Day06 {
	
	private static class Group {
		private Set<Person> persons = new HashSet<Person>();;
		
		public Group(Set<Person> persons) {
			this.persons = persons;
		}
				
		public Set<Person> getPersons() {
			return this.persons;
		}
		
		public int getNumberOfPersons() {
			return this.persons.size();
		}
	}
	
	private static class Person {
		private List<Character> answers = new ArrayList<Character>();
		
		public Person(String answers) {
			for (Character answer : answers.toCharArray()) {
				this.answers.add(answer);
			}
		}
		
		public List<Character> getAnswers() {
			return this.answers;
		}
	}
	
	public static int getUniqueAnswerCount(Set<Group> groups) {
		HashSet<Character> groupAnswerSet;
		
		int count = 0;
		for (Group group : groups) {
			groupAnswerSet = new HashSet<Character>();
			for (Person person : group.getPersons()) {
				for (Character answer : person.getAnswers()) {
					groupAnswerSet.add(answer);
				}
			}
			count += groupAnswerSet.size();
		}
		
		return count;
	}
	
	public static int getAllAnswerCount(Set<Group> groups) {
		HashMap<Character, Integer> groupAnswersCount;
		int count = 0;
		
		for (Group group : groups) {
			groupAnswersCount = new HashMap<Character, Integer>();
			for (Person person : group.getPersons()) {
				for (Character answer : person.getAnswers()) {
					if (groupAnswersCount.containsKey(answer)) {
						groupAnswersCount.put(answer, groupAnswersCount.get(answer) + 1);
					}
					else {
						groupAnswersCount.put(answer, 1);
					}
				}
			}
			for (Integer answerCount : groupAnswersCount.values()) {
				if (answerCount == group.getNumberOfPersons()) {
					count++;
				}
			}
		}
		
		return count;
	}
	
	private static Set<Group> getInput() {
		Set<Group> groups = new HashSet<Group>();
		Set<Person> groupPersons = new HashSet<Person>();
		
		for (String item : Util.getInputAsStringCollection("Inputs\\Day06.txt"))
		{	
			if (!item.isBlank()) 
			{
				groupPersons.add(new Person(item));
			}
			else 
			{
				groups.add(new Group(groupPersons));
				groupPersons = new HashSet<Person>();
			}
		}
		if (groupPersons.size() > 0)
			groups.add(new Group(groupPersons));
		
		return groups;
	}
	
	public static void main(String[] args) {
		Set<Group> input = getInput();
		
		System.out.printf("Total unique group answers count is %d.\n", getUniqueAnswerCount(input));	// PART 1
		System.out.printf("Total all group answers count is %d.", getAllAnswerCount(input));			// PART 2
	}

}
