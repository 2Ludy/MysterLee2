package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_10211 { // Maximum Subarray
	static int T,N,maxSum;
	static int[] arr,dp;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		StringTokenizer st;
		
		for (int t = 0; t < T; t++) {
			N = Integer.parseInt(br.readLine());
			arr = new int[N];
			dp = new int[N];
			maxSum = 0;
			st = new StringTokenizer(br.readLine());
			
			for (int i = 0; i < N; i++) {
				arr[i] = Integer.parseInt(st.nextToken());				
			}
			
			dp[0] = arr[0];
			maxSum = arr[0];
			
			for (int i = 1; i < N; i++) {
				dp[i] = Math.max(dp[i-1] + arr[i], arr[i]);
				maxSum = Math.max(maxSum, dp[i]);
			}
			
			System.out.println(maxSum);
		}
		
		
	}
}
