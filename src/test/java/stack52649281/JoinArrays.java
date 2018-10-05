package stack52649281;

import org.junit.Test;

public class JoinArrays {
	@Test // No clue what this is? Google it - "junit", "tdd",...!
	public void testjoinArrays() {
		String[] h1 = { "header1", "string1", "string2", "string3" };
		String[] h2 = { "header2", "string4", "string5", "string6" };
		String[] h3 = { "header3", "string7", "string8", "string9" };
		String result = arraysToString(h1, h2, h3);
		String expected = "header1,header2,header3\nstring1,string4,string7\nstring2,string5,string8\nstring3,string6,string9\n";
		org.junit.Assert.assertEquals(expected, result);
		System.out.println(result);
	}

	public String arraysToString(String[] first, String[]... arrays) {
		for (String[] cur : arrays) {
			if (cur.length != first.length) {
				throw new IllegalArgumentException("Precondition failed. Arrays do not have the same length.");
			}
		}
		StringBuilder strb = new StringBuilder();
		for (int i = 0; i < first.length; i++) {
			strb.append(first[i]);
			for (String[] array : arrays) {
				strb.append(",");
				strb.append(array[i]);
			}
			strb.append("\n");
		}
		return strb.toString();
	}
}
