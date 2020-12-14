/**
 * Advent Of Code 2020
 *  >>> www.adventofcode.com/2020
 * 
 * This file contains solutions to day 4.
 * 
 * @author Mislav.Gazdovic, mislav.gazdovic@gmail.com
 *
 */

package adventOfCode2020;

import java.util.*;

public class Day04 {

	private static class Passport {
		private String passport;
		
		private Set<String> requiredFields = new HashSet<String>();
		
		public Passport(String passport) {
			this.passport = passport;
			
			// Add required fields
			addRequiredField("byr");		// Birth Year
			addRequiredField("iyr");		// Issue Year
			addRequiredField("eyr");		// Expiration Year
			addRequiredField("hgt");		// Height
			addRequiredField("hcl");		// Hair Color
			addRequiredField("ecl");		// Eye Color
			addRequiredField("pid");		// Passport ID
			//addRequiredField.add("cid:");	// Country ID -> ignored!
		}
				
		private void addRequiredField(String field) {
			requiredFields.add(field.toLowerCase());
		}
		
		public boolean isValid(boolean validateFieldValues) {
			for (String field : requiredFields) {
				if (!isValidField(field, validateFieldValues)) return false;
			}
			
			// All fields valid
			return true;
		}
		
		private boolean isValidField(String field, boolean validateFieldValues) {
			int fieldIndex = passport.indexOf(field);
			if (fieldIndex == -1) return false;
			
			if (validateFieldValues) {
				
				int fieldValueStartIndex = passport.indexOf(field, fieldIndex) + field.length() + 1;
				int fieldValueEndIndex = passport.indexOf(" ", fieldValueStartIndex);
				String fieldValue = passport.substring(fieldValueStartIndex, fieldValueEndIndex);

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
						  	
					}
				}
				catch (NumberFormatException e) {
					// System.out.println("Bad input...");
					return false;
				}
			}
			
			// all validation checks passed -> passport is valid
			return true;
			
		}
		
	}
	
	public static int countValidPassports(Collection<Passport> passports, boolean validateFieldValues) {
		int count = 0;
		for (Passport passport : passports) {
			if (passport.isValid(validateFieldValues)) count++;
		}
		
		return count;
	}
		
	private static Collection<Passport> getInput() {
		Collection<Passport> input = new ArrayList<Passport>();
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
				input.add(new Passport(passport.toString()));
				passport = new StringBuilder("");
			}
		}
		if (passport.length() > 0)
			input.add(new Passport(passport.toString()));
		
		return input;
	}
	
	public static void main(String[] args) {
		Collection<Passport> input = getInput();
		final boolean CHECK_VALUES = true;
		
		System.out.printf("Valid passports without checking values: %d.\n", countValidPassports(input, !CHECK_VALUES));	// PART 1
		System.out.printf("Valid passports with checked values: %d.", countValidPassports(input, CHECK_VALUES));		// PART 2
		
	}

}
