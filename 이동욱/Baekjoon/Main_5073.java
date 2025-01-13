package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_5073 {

	static String[] answer;
	static int[] input;

	public static void main(String[] args) throws Exception {
		answer = new String[4];
		answer[0] = "Equilateral";
		answer[1] = "Isosceles";
		answer[2] = "Scalene";
		answer[3] = "Invalid";

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while(true) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			input = new int[3];

			for(int i=0; i<3; i++) input[i] = Integer.parseInt(st.nextToken());

			Arrays.sort(input);

			if(input[2] == 0) return;

			if(input[0]+input[1]<=input[2]) {
				System.out.println(answer[3]);

			}else if(input[0] == input[2]) {
				System.out.println(answer[0]);

			}else if((input[0] == input[1]) || input[1] == input[2]) {
				System.out.println(answer[1]);

			}else {
				System.out.println(answer[2]);
			}
		}
	}
}
