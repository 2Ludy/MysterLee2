package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_11057 {
	
	static int N;
	static long[][] dp;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		dp = new long[N+1][10];

		for (int i = 0; i <= 9; i++) {
			dp[1][i] = 1;
		}

		for (int i = 2; i <= N; i++) {
			for (int j = 0; j <= 9; j++) {
				for (int k = 0; k <= j; k++) {
					dp[i][j] = (dp[i][j] + dp[i-1][k]) % 10007;
				}
			}
		}

		long result = 0;
		for (int i = 0; i <= 9; i++) {
			result = (result + dp[N][i])%10007;
		}

		System.out.println(result);
	}
}