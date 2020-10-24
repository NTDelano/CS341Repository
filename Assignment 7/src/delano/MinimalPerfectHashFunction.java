package delano;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

/**
 * The Class MinimalPerfectHashFunction. The purpose is to create a MPHF using Cichelli's Method on the keywords examined
 */
public class MinimalPerfectHashFunction {

	/** The list of the keywords for the function. */
	public static List<String> keys = new ArrayList<>();
	
	/** The value map of each keyword based on the first and last letter values */
	public static HashMap<String, Integer> wordsValuesMap = new HashMap<>();
	
	/** The value map of each keyword after applying Cichelli's Method */
	public static HashMap<String, Integer> testValuesMap = new HashMap<>();
	
	/** The values, or frequency, of each letter as the first or lest character in every keyword */
	public static HashMap<Character, Integer> letterValuesMap = new HashMap<>();
	
	/** The g values map, determined through the h function */
	public static HashMap<Character, Integer> gValuesMap = new HashMap<>();
	
	/** Begin each g value as a 0 */
	public static int g = 0;
	
	/** The max value allowable for g values, can be changed */
	public static int max = 4;

	/**
	 * Instantiates a new minimal perfect hash function.
	 *
	 * @param list of keywords we are creating our MPHF after
	 */
	public MinimalPerfectHashFunction(List<String> list) {
		keys = list;
		CountLettersFirstAndLast();
		InitializeGValues();
		CichellisMethod();
	}

	/**
	 * Application of the Cichellis method.
	 */
	private void CichellisMethod() {

		//Examines each keyword
		for (String word : wordsValuesMap.keySet()) {
			//runs as long as the h(keyword) has already been documented
			while (testValuesMap.containsValue(h(word)) == true) {

				//get the first and last characters of the word
				Character firstChar = word.charAt(0);
				Character lastChar = word.charAt(word.length() - 1);
				//first examine if the g value of the first char is no greater than the max
				if (gValuesMap.get(firstChar) <= max) {
					//if it is less than the max, increase the g value of the letter by one
					gValuesMap.put(firstChar, gValuesMap.get(firstChar) + 1);

				} else { //if max g is reached for the first letter, beginning adding the g value of the last character
					gValuesMap.put(lastChar, gValuesMap.get(lastChar) + 1);
				}
				//Stops the while loop if the h(keyword) isn't mapped yet
				if (testValuesMap.containsValue(h(word)) == false) {
					break;
				}
			}
			//otherwise, set the keywords value in the hash map as h(keyword)
			testValuesMap.put(word, h(word));
		}
	}

	/**
	 * Count the frequency of letters from the first and last characters of every keyword.
	 */
	public static void CountLettersFirstAndLast() {
		//for each keyword
		for (int i = 0; i < keys.size(); i++) {
			//get the first and lest letter
			char first = keys.get(i).charAt(0);
			char last = keys.get(i).charAt(keys.get(i).length() - 1);

			//if the letter isn't map, set the counter for the letter at 1
			if (!letterValuesMap.containsKey(first)) {
				letterValuesMap.put(first, 1);
			} else {//otherwise, just add one more to the counter
				letterValuesMap.put(first, letterValuesMap.get(first) + 1);
			}
			//if the letter isn't map, set the counter for the letter at 1
			if (!letterValuesMap.containsKey(last)) {
				letterValuesMap.put(last, 1);
			} else {
				letterValuesMap.put(last, letterValuesMap.get(last) + 1);
			}

		}
		//Then order the keywords with their frequency value by descending order
		letterValuesMap = letterSort(letterValuesMap);
		
		//for each keyword
		for (int i = 0; i < keys.size(); i++) {
			//get the first and last letter
			char first = keys.get(i).charAt(0);
			char last = keys.get(i).charAt(keys.get(i).length() - 1);
			//add up the frequency count of the first and last letter
			int sum = letterValuesMap.get(first) + letterValuesMap.get(last);
			//The word is given the value based on the sum
			wordsValuesMap.put(keys.get(i), sum);

		}
		//Sort words values by descending order
		wordsValuesMap = wordSort(wordsValuesMap);
	}

	/**
	 * Letter sort. Sorts the frequency value of each first and last letter within the keywords
	 *
	 * @param assortment the hash map containing each letter and their frequency value
	 * @return the hash map with the values in descending order
	 */
	private static HashMap<Character, Integer> letterSort(HashMap<Character, Integer> assortment) {
		//create a new list for the keys and values
		List<Map.Entry<Character, Integer>> list = new LinkedList<Map.Entry<Character, Integer>>(assortment.entrySet());
		//Sort the values, with higher value first, and lower second.
		Collections.sort(list, new Comparator<Map.Entry<Character, Integer>>() {
			public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// put data from sorted list to hashmap
		HashMap<Character, Integer> temp = new LinkedHashMap<Character, Integer>();
		for (Map.Entry<Character, Integer> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		return temp;
	}

	/**
	 * Word sort. Sorts the keyword and the sum of the first and last letter by descending order
	 *
	 * @param assortment the hash map containing each keyword and the sum of the first and last letter
	 * @return the hash map with the values in descending order
	 */
	private static HashMap<String, Integer> wordSort(HashMap<String, Integer> assortment) {
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(assortment.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// put data from sorted list to hashmap
		HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		return temp;
	}

	/**
	 * h function = = (length(word) + g*(firstletter(word)) + g*(lastletter(word))) % size
	 *
	 * @param word the keyword running through the h function
	 * @return the int value resulting from the h function
	 */
	public int h(String word) {

		char first = word.charAt(0);
		int gFirst = gValuesMap.get(first);

		char last = word.charAt(word.length() - 1);
		int gLast = gValuesMap.get(last);

		return (word.length() + gFirst + gLast) % keys.size();

	}

	/**
	 * Initialize G values. Set every letter from the frequency hash map to 0 as g values initially are set to 0
	 */
	public static void InitializeGValues() {
		for (char c : letterValuesMap.keySet()) {
			gValuesMap.put(c, 0);
		}

	}
	
	/**
	 * Keyword values.
	 *
	 * @return the hash map with each keyword and their respective value based on the MPHF
	 */
	public HashMap<String, Integer> keywordValues() {
		return testValuesMap;
	}
	
	/**
	 * G values.
	 *
	 * @return the hash map with each frequent letter and their respective value based on the MPHF
	 */
	public HashMap<Character, Integer> gValues() {
		return gValuesMap;
	}
}
