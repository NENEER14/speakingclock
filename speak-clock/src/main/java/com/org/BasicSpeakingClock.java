package com.org;

import org.apache.commons.lang3.StringUtils;

public class BasicSpeakingClock implements SpeakingClock {

	private static final String[] UNITS_ARRAY = { "zero", "one", "two", "three", "four", "five", "six", "seven",
			"eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen",
			"eighteen", "nineteen" };
	private static final String[] TENS_ARRAY = { "", "", "twenty", "thirty", "forty", "fifty" };

	@Override
	public String convert(final String time) throws InvalidTimeException {
		this.validate(time);

		// add its to string builder
		StringBuilder sb = new StringBuilder("It's ");

		// if its midnight add it to string builder
		if (time.equals("00:00")) {
			sb.append("Midnight");
		} else if (time.equals("12:00")) {
			// if its midday add it to string builder
			sb.append("Midday");
		} else {
			// split time by :
			String[] timeSplit = time.split(":");

			// convert hours into words
			Integer hours = Integer.parseInt(timeSplit[0]);
			String hoursStr = this.convertInt(hours);
			sb.append(hoursStr + " ");

			// convert minutes into words
			Integer minutes = Integer.parseInt(timeSplit[1]);
			String minutesStr = this.convertInt(minutes);
			sb.append(minutesStr);
		}

		return sb.toString();
	}

	/**
	 * Convert the given integer into words
	 *
	 * @param number Integer, represents a number to convert
	 * @return String represents a number in words
	 */
	private String convertInt(final Integer number) {
		if (number < 20)
			return UNITS_ARRAY[number];
		return TENS_ARRAY[number / 10] + ((number % 10 > 0) ? " " + convertInt(number % 10) : "");
	}

	/**
	 * Validate the given time
	 *
	 * @param time String, representing time
	 * @throws InvalidTimeException
	 */
	private void validate(final String time) throws InvalidTimeException {
		// validate that the time is not blank
		if (StringUtils.isBlank(time)) {
			throw new InvalidTimeException("Time is blank");
		}

		// validate that the time is of the right format
		if (time.length() != 5 || !time.contains(":")) {
			throw new InvalidTimeException("Invalid Time format, should be HH:mm");
		}

		String[] timeSplit = time.split(":");

		// validate that the hours are an integer
		@SuppressWarnings("removal")
		Integer hours = new Integer(0);
		try {
			hours = Integer.parseInt(timeSplit[0]);
		} catch (NumberFormatException e) {
			throw new InvalidTimeException("Invalid Hours");
		}

		// validate that the hours are in the range
		if (hours < 0 || hours > 23) {
			throw new InvalidTimeException("Invalid Hours");
		}

		// validate that the minutes are an integer
		@SuppressWarnings("removal")
		Integer minutes = new Integer(0);
		try {
			minutes = Integer.parseInt(timeSplit[1]);
		} catch (NumberFormatException e) {
			throw new InvalidTimeException("Invalid Minutes");
		}

		// validate that the minutes are in the range
		if (minutes < 0 || minutes > 59) {
			throw new InvalidTimeException("Invalid Minutes");
		}
	}
}
