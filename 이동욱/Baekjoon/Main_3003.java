package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Main_3003 {

	static int[] chess;

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		chess = new int[]{1, 1, 2, 2, 2, 8};

		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<6; i++) {
			int n = Integer.parseInt(st.nextToken());
			sb.append(chess[i] - n).append(" ");
		}
		System.out.println(sb);
	}
}