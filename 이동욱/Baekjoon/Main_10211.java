package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_10211 {
	
	static int T, N;
	static int max;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());

		for(int t=0; t<T; t++) {
			N = Integer.parseInt(br.readLine());
			int[] nums = new int[N];
			int[] dp = new int[N];
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int i=0; i<N; i++) {
				nums[i] = Integer.parseInt(st.nextToken());
			}

			dp[0] = nums[0];
			max = dp[0];
			for(int i=1; i<N; i++) {
				dp[i] = Math.max(nums[i], nums[i]+dp[i-1]);
				max = Math.max(max, dp[i]);
			}
			System.out.println(max);
		}
	}
}
