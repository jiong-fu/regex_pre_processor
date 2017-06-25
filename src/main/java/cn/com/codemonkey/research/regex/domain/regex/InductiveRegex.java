package cn.com.codemonkey.research.regex.domain.regex;

import static cn.com.codemonkey.research.regex.domain.regex.RegexHelper.LEFT_BRACKET;
import static cn.com.codemonkey.research.regex.domain.regex.RegexHelper.RIGHT_BRACKET;
import static cn.com.codemonkey.research.regex.domain.regex.RegexHelper.isIncorrectFormat;
import static org.apache.commons.lang3.StringUtils.contains;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * Abstraction of inductively defined regular expressions. User should not
 * decide which regular expression to instantiate, therefore the constructor is
 * made package private.
 *
 */
public final class InductiveRegex extends Regex {

	private final List<Regex> childRegexes = new ArrayList<>();
	private Occurence occurence = Occurence.ONCE;

	InductiveRegex(String inputString) {
		this(inputString, Occurence.ONCE);
	}

	InductiveRegex(String inputString, Occurence occurence) {
		super(inputString);
		this.occurence = occurence;
		this.childRegexes.addAll(generateChildRegexes(inputString));
	}

	public Occurence getOccurence() {
		return occurence;
	}

	@Override
	public List<Regex> getValue() {
		return childRegexes;
	}

	private List<Regex> generateChildRegexes(String regex) {
		List<Regex> childRegexes = new ArrayList<>();

		if (Regex.isInductive(regex)) {
			if (isIncorrectFormat(regex)) {
				throw new IllegalArgumentException("Regular is not correctly formatted.");
			}

			if (contains(regex, LEFT_BRACKET)) {
				int indexOfLeft = regex.indexOf(LEFT_BRACKET);
				int indexOfRight = regex.indexOf(RIGHT_BRACKET);
				int indexAfterBracket = indexOfRight + 1;
				String content = regex.substring(indexOfLeft + 1, indexOfRight);

				// Continue analyzing the part before the bracket
				childRegexes.addAll(generateChildRegexes(regex.substring(0, indexOfLeft)));

				// Continue analyzing the part within the bracket
				if (indexAfterBracket <= regex.length() - 1) {
					switch (regex.charAt(indexAfterBracket)) {
					case '+':
						childRegexes.add(new InductiveRegex(content, Occurence.ONE_OR_MORE));
						break;
					case '*':
						childRegexes.add(new InductiveRegex(content, Occurence.ZERO_OR_MORE));
						break;
					default:
						childRegexes.add(new InductiveRegex(content, Occurence.ONCE));
						break;
					}
				}

				// Continue analyzing the part after the bracket
				if (indexAfterBracket < regex.length() - 1) {
					switch (regex.charAt(indexAfterBracket)) {
					case '+':
					case '*':
						childRegexes.addAll(generateChildRegexes(regex.substring(indexAfterBracket + 1)));
						break;
					default:
						childRegexes.addAll(generateChildRegexes(regex.substring(indexOfRight + 1)));
						break;
					}
				}
			} else {
				if (contains(regex, '|')) {
					occurence = Occurence.SELECTIVE;
					String[] splits = StringUtils.split(regex, '|');

					for (String split : splits) {
						childRegexes.add(Regex.valueOf(split));
					}
				} else if (contains(regex, '+')) {
					int indexOfPlus = regex.indexOf('+');
					int indexBeforePlus = indexOfPlus - 1;

					if (indexBeforePlus > 0 && regex.charAt(indexBeforePlus) != '\\') {
						childRegexes.addAll(generateChildRegexes(regex.substring(0, indexBeforePlus)));
						childRegexes.add(new InductiveRegex(String.valueOf(regex.charAt(indexBeforePlus)),
								Occurence.ONE_OR_MORE));
						if (indexOfPlus + 1 <= regex.length()) {
							childRegexes.addAll(generateChildRegexes(regex.substring(indexOfPlus + 1)));
						}
					}
				} else if (contains(regex, '*')) {
					int indexOfStar = regex.indexOf('*');
					int indexBeforeStar = indexOfStar - 1;

					if (indexBeforeStar > 0 && regex.charAt(indexBeforeStar) != '\\') {
						childRegexes.addAll(generateChildRegexes(regex.substring(0, indexBeforeStar)));
						childRegexes.add(new InductiveRegex(String.valueOf(regex.charAt(indexBeforeStar)),
								Occurence.ZERO_OR_MORE));
						if (indexOfStar + 1 <= regex.length()) {
							childRegexes.addAll(generateChildRegexes(regex.substring(indexOfStar + 1)));
						}
					}
				}
			}
		} else {
			if (StringUtils.isNotBlank(regex)) {
				childRegexes.add(Regex.valueOf(regex));
			}
		}

		return childRegexes;
	}

	public enum Occurence {
		ONCE, ZERO_OR_MORE, ONE_OR_MORE, SELECTIVE;
	}

}
