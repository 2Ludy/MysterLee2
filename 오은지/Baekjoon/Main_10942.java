package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_10942 { // 팰린드롬?
	static int N, M, S, E, res;
	static int[] nums;
	static boolean[][] dp;
	
	public static void main(String[] args) throws Exception {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine()); // 주어질 수열의 크기
		nums = new int[N+1]; // 주어진 수열
		dp = new boolean[N+1][N+1]; // 팰린드롬 확인 배열
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N; i++) {
			nums[i] = Integer.parseInt(st.nextToken());
		}

		// 길이 1
		for (int i = 1; i <= N; i++) {
			dp[i][i] = true;
		}
		
		// 길이 2
		for (int i = 1; i < N; i++) {
			if(nums[i] == nums[i+1]) {
				dp[i][i+1] = true;
			}
		}
		
		// 길이 3 이상 팰린드롬
		for (int i = 3; i <= N; i++) {
			for (int start = 1; start <= N-i+1; start++) {
				int end = start+i-1;
				if(nums[start] == nums[end] && dp[start+1][end-1]) {
					dp[start][end] = true;
				}
			}
		}
		
		M = Integer.parseInt(br.readLine()); // 질문 개수
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			S = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());
			sb.append(dp[S][E] ? "1" : "0").append("\n");
		}
		System.out.println(sb);
	}


}
