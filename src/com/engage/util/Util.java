package com.engage.util;

import android.content.Context;
import android.widget.Toast;

public class Util {

	/**
	 * Checks to see if there is white space within the input (both username and
	 * email). If there is, then it is an invalid input. Returns true if there
	 * is white space, false if there is no white space.
	 * 
	 * @param input
	 * @return
	 */
	public static boolean containsWhiteSpace(String input) {
		if (input != null) {
			for (int i = 0; i < input.length(); i++) {
				if (Character.isWhitespace(input.charAt(i))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks to see if the inputed email is vaild by checking to see if there
	 * is an "@" or a "." If the email is vaild returns true and returns false
	 * if the email is not vaild.
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isValidEmail(String email) {
		String atChar = "@";
		String dotChar = ".";
		if (email != null) {
			int index1 = email.indexOf(atChar);
			int index2 = email.indexOf(dotChar);
			if ((index1 != -1) && (index2 != -1)) {
				// contatins @ and a . so vaild
				return true;
			}
		}
		return false;
	}
	
	public static void displayToastMessage(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	public static void displayToastMessage(Context context, int messageId) {
		Toast.makeText(context, messageId, Toast.LENGTH_SHORT).show();
	}
}
