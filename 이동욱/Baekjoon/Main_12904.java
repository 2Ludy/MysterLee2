package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main_12904 {

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String S = br.readLine();
		StringBuilder T = new StringBuilder(br.readLine());

		while (T.length() > S.length()) {
			if (T.charAt(T.length() - 1) == 'A') {
				T.setLength(T.length() - 1);
			} else {
				T.setLength(T.length() - 1);
				T.reverse();
			}
		}
		System.out.println(T.toString().equals(S) ? 1 : 0);
	}
}
