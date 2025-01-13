package baekjoon;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1157 {
	
	static String s;
	static int[] alpha;
	static char c;
	static int max;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		s = br.readLine().toUpperCase();
		alpha = new int[26];

		for(int i=0; i<s.length(); i++) {
			alpha[s.charAt(i)-'A']++;
		}

		c = '?';
		max = 0;

		for(int i=0; i<26; i++) {
			if(alpha[i] > max) {
				max = alpha[i];
				c = (char)(i+'A');
			}else if(alpha[i] == max) {
				c = '?';
			}
		}

		System.out.println(c);

	}
}
