package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main_1157 { // 단어 공부
	static String str;
	static int[] cnt;
	static int max;
	static char res;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		str = br.readLine().toUpperCase(); // 입력 문자 대문자화
		cnt = new int[26]; // 알파벳 저장 배열
		
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			cnt[ch-'A']++;
		}
		
		max = -1;
		res = '?';
		
		for (int i = 0; i < 26; i++) {
			if(cnt[i] > max) {
				max = cnt[i];
				res = (char)(i+'A');
			} else if(cnt[i] == max) {
				res = '?';
			}
		}
		
		System.out.println(res);
	}
}
