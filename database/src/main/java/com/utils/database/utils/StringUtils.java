package com.utils.database.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class StringUtils {

	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	public static String convertHump(String variable, boolean firstUpper) {
		List<Character> characters = new ArrayList<>(variable.length());
		boolean nextUpper = firstUpper;
		Pattern pattern = Pattern.compile("^[0-9a-zA-Z]$");
		for (Character ch : variable.toCharArray()) {
			if (pattern.matcher(ch + "").find()) {
				
				if (Character.isUpperCase(ch) && !nextUpper)
					characters.add((char) (ch + 32));
				else if (!Character.isUpperCase(ch) && nextUpper)
					characters.add((char) (ch - 32));
				else
					characters.add(ch);
				nextUpper = false;
			} else
				nextUpper = true;
		}
		StringBuilder builder = new StringBuilder();
		characters.forEach(builder::append);
		return builder.toString();
	}
}
