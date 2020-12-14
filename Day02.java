/**
 * Advent Of Code 2020
 *  >>> www.adventofcode.com/2020
 * 
 * This file contains solutions to day 2.
 * 
 * @author Mislav.Gazdovic, mislav.gazdovic@gmail.com
 *
 */

package adventOfCode2020;

import java.util.*;

public class Day02 {

	private static class PasswordRule {
		private String rule;
		
		private int dashIndex;
		private int colonIndex;
		private int minCount;
		private int maxCount;
		private char letter;
		private String password;
		
		private int firstPosition;
		private int secondPosition;
		char firstLetter; 
		char secondLetter; 
		
		public PasswordRule(String rule) {
			this.rule = rule;
			this.parseRule();
		}
		
		private void parseRule() {
			// parse string to get rule details, for example: 
			// 9-10 g: bgbgjggpvgpgpggg
			
			dashIndex = rule.indexOf('-');
			colonIndex = rule.indexOf(':');
			
			try {
				minCount = Integer.parseInt(rule.substring(0, dashIndex));
				maxCount = Integer.parseInt(rule.substring(dashIndex + 1, colonIndex - 2));
				letter = rule.charAt(colonIndex - 1);
				password = rule.substring(colonIndex + 2);
				
				firstPosition = Integer.parseInt(rule.substring(0, dashIndex));			
				firstLetter = password.charAt(firstPosition - 1); // one-based position -> zero-based index
				secondPosition = Integer.parseInt(rule.substring(dashIndex + 1, colonIndex - 2));
				secondLetter = password.charAt(secondPosition - 1); // one-based position -> zero-based index
			}
			catch (Exception e) {
				System.out.printf("Bad password rule: \n%s\n" + e.getMessage());
				minCount = -1;
				maxCount = -1;
				letter = ' ';
				password = " ";
				firstPosition = -1;
				firstLetter = ' ';
				secondPosition = -1;
				secondLetter = ' ';
			}
			
		}
		
		public boolean isValidByLetterCount() {
			int count = 0;
			for (int i = 0; i < password.length(); i++) {
				if (password.charAt(i) == letter) count++;
			}
			
			return count >= minCount && count <= maxCount;
		}
		
		public boolean isValidByLetterPositioning() {
			return (firstLetter == letter) ^ (secondLetter == letter);
		}
		
	}
	
	public static int pwdRuleLetterOccurences(Collection<PasswordRule> passwords) {
		Util.notEmptyOrThrow(passwords);
		
		int passwordOkCount = 0;
		for(PasswordRule password : passwords) {
			if (password.isValidByLetterCount()) passwordOkCount++;	
		}
		
		return passwordOkCount;
	}
	
	public static int pwdRuleLetterPositions(Collection<PasswordRule> passwords) {
		Util.notEmptyOrThrow(passwords);
		
		int passwordOkCount = 0;
		for(PasswordRule password : passwords) {
			if (password.isValidByLetterPositioning()) passwordOkCount++;
		}
		
		return passwordOkCount;
	}
		
	private static Collection<PasswordRule> getInput() {
		List<PasswordRule> passwordRules = new ArrayList<PasswordRule>();
		for (String passwordRule : Util.getInputAsStringCollection("Inputs\\Day02.txt")) {
			passwordRules.add(new PasswordRule(passwordRule));
		}
		return passwordRules;
	}
	
	// Driver
	public static void main(String[] args) {
		
		Collection<PasswordRule> input = getInput();
			
		System.out.printf("The number of correct passwords for occurence rule is %d.\n", pwdRuleLetterOccurences(input));	// PART 1
		System.out.printf("The number of correct passwords for position rule is %d.", pwdRuleLetterPositions(input));		// PART 2
		
	}

}
