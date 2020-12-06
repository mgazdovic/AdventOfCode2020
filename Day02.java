package adventOfCode2020;

import java.util.*;

public class Day02 {

	public static int pwdRuleLetterOccurences(Collection<String> passwords) {
		
		int passwordOkCount = 0;
		
		int minCount, maxCount;
		char letter;
		String password;
		int dashIndex = 0;
		int colonIndex = 0;
		for(String item : passwords) {
			
			// parse string to get rule details, for example: 
			// 9-10 g: bgbgjggpvgpgpggg
			
			dashIndex = item.indexOf('-');
			colonIndex = item.indexOf(':');
			
			minCount = Integer.parseInt(item.substring(0, dashIndex));
			maxCount = Integer.parseInt(item.substring(dashIndex + 1, colonIndex - 2));
			letter = item.charAt(colonIndex - 1);
			password = item.substring(colonIndex + 2);
			
			// count the number of letter instances within item
			int count = 0;
			for (int i = 0; i < password.length(); i++) {
				if (password.charAt(i) == letter) count++;
			}
			
			if (count >= minCount && count <= maxCount) {
				passwordOkCount++;
			}		
		}
		
		return passwordOkCount;
	}
	
	public static int pwdRuleLetterPositions(Collection<String> passwords) {
		
		int passwordOkCount = 0;
		
		int firstPosition, secondPosition;
		char firstLetter, secondLetter, letter;
		String password;
		int dashIndex = 0;
		int colonIndex = 0;
		for(String item : passwords) {
			
			// parse string to get rule details, for example: 
			// 9-10 g: bgbgjggpvgpgpggg
			
			dashIndex = item.indexOf('-');
			colonIndex = item.indexOf(':');
			
			password = item.substring(colonIndex + 2);
			
			firstPosition = Integer.parseInt(item.substring(0, dashIndex));			
			firstLetter = password.charAt(firstPosition - 1); 		// one-based position -> zero-based index
			
			secondPosition = Integer.parseInt(item.substring(dashIndex + 1, colonIndex - 2));
			secondLetter = password.charAt(secondPosition - 1); 	// one-based position -> zero-based index
			
			letter = item.charAt(colonIndex - 1);
			
			// exactly one letter has to match for the password to be OK
			if ((firstLetter == letter) ^ (secondLetter == letter)) {
				passwordOkCount++;
			}		
		}
		
		return passwordOkCount;
	}
		
	private static Collection<String> getInput() {
		List<String> stringList = new ArrayList<String>();
		stringList.addAll(Util.getInputAsStringCollection("Inputs\\Day02.txt"));
		return stringList;
	}
	
	// Driver
	public static void main(String[] args) {
		
		final Collection<String> input = getInput();
			
		System.out.printf("The number of correct passwords for occurence rule is %d.", pwdRuleLetterOccurences(input));	// PART 1
		System.out.println();
		System.out.printf("The number of correct passwords for position rule is %d.", pwdRuleLetterPositions(input));	// PART 2
		
	}

}
