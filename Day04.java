package adventOfCode2020;

import java.util.*;

public class Day04 {

	private static boolean passportIsValid(String passport, Set<String> requiredFields, boolean validateFieldValues) {
		int fieldIndex, fieldValueStartIndex, fieldValueEndIndex;
		String fieldValue;
		for (String field : requiredFields) {
			
			fieldIndex = passport.indexOf(field);
			if (fieldIndex == -1) return false;
			
			if (validateFieldValues) {
				
				fieldValueStartIndex = passport.indexOf(field, fieldIndex) + field.length() + 1;
				fieldValueEndIndex = passport.indexOf(" ", fieldValueStartIndex);
				fieldValue = passport.substring(fieldValueStartIndex, fieldValueEndIndex);

				// passport validation rules
				try {
					switch(field) {
						case "byr": 
							int birthYear = Integer.parseInt(fieldValue);
							if (birthYear < 1920 || birthYear > 2002) return false;
							break;
						case "iyr": 
							int issueYear = Integer.parseInt(fieldValue);
							if (issueYear < 2010 || issueYear > 2020) return false;
							break;
						case "eyr":
							int expirationYear = Integer.parseInt(fieldValue);
							if (expirationYear < 2020 || expirationYear > 2030) return false;
							break;
						case "hgt":
							int height_cm, height_in;
							
							if (fieldValue.contains("cm")) {
								height_cm = Integer.parseInt(fieldValue.substring(0, fieldValue.indexOf("cm")));
								if (height_cm < 150 || height_cm > 193) return false;
							}
							else if (fieldValue.contains("in")) {
								height_in = Integer.parseInt(fieldValue.substring(0, fieldValue.indexOf("in")));
								if (height_in < 59 || height_in > 76) return false;
							}
							else {
								return false;
							}
							break;
						case "hcl": // Hair Color -> a # followed by exactly six characters 0-9 or a-f
							if (!fieldValue.matches("#[a-f0-9]{6}"))  return false;
							break;
						case "ecl": // Eye Color -> exactly one of: amb blu brn gry grn hzl oth.
							if (!fieldValue.matches("amb|blu|brn|gry|grn|hzl|oth"))  return false;
							break;
						case "pid": // Passport ID -> a nine-digit number, including leading zeroes.
							if (!fieldValue.matches("[0-9]{9}"))  return false;
							break;
					  default:
						  	continue;
					}
				}
				catch (NumberFormatException e) {
					// System.out.println("Bad input...");
					return false;
				}
			}
			
			
		}
		
		// all validation checks passed -> passport is valid
		return true;
	}
	
	public static int countValidPassports(Collection<String> passports, Set<String> requiredFields, boolean validateFieldValues) {
		int count = 0;
		for (String passport : passports) {
			if (passportIsValid(passport, requiredFields, validateFieldValues)) count++;
		}
		return count;
	}
		
	private static List<String> getInput() {
		List<String> inputList = new ArrayList<String>();
		StringBuilder passport = new StringBuilder("");
		for (String item : Util.getInputAsStringCollection("Inputs\\Day04.txt"))
		{	
			if (!item.isBlank()) 
			{
				passport.append(item);
				passport.append(" ");
			}
			else 
			{
				inputList.add(passport.toString());
				passport = new StringBuilder("");
			}
		}
		if (passport.length() > 0)
			inputList.add(passport.toString());
		
		return inputList;
	}
	
	public static void main(String[] args) {
		
		final Collection<String> input = getInput();
		
		final Set<String> requiredFields = new HashSet<String>();
		requiredFields.add("byr");	// Birth Year
		requiredFields.add("iyr");	// Issue Year
		requiredFields.add("eyr");	// Expiration Year
		requiredFields.add("hgt");	// Height
		requiredFields.add("hcl");	// Hair Color
		requiredFields.add("ecl");	// Eye Color
		requiredFields.add("pid");	// Passport ID
		//requiredFields.add("cid:");	// Country ID -> ignored!
		
		System.out.printf("Valid passports without checking values: %d. ", countValidPassports(input, requiredFields, false));	// PART 1
		System.out.println();
		System.out.printf("Valid passports with checked values: %d. ", countValidPassports(input, requiredFields, true));		// PART 2
		
	}

}
