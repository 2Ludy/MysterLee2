/*
[ Notion url ]
https://www.notion.so/Algol-115634ecbbc48048a999c7f49465ed3b?p=11d634ecbbc480c2953be894ab76e79a&pm=s
 */

package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_12865 { // 평범한 배낭 (dp)
	static int N,K,W,V;
	static List<int[]> list;
	static int[] dp;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken()); // 물품 개수
		K = Integer.parseInt(st.nextToken()); // 최대 무게
		list = new ArrayList<>();
		dp = new int[K+1];
		
		// N개의 물건 정보 입력
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			W = Integer.parseInt(st.nextToken()); // 물건 무게
			V = Integer.parseInt(st.nextToken()); // 물건 가치
			
			list.add(new int[] {W,V});
		}
		
		for (int i = 0; i < N; i++) {
			int weight = list.get(i)[0];
			int value = list.get(i)[1];
			
			for (int j = K; j >= weight ; j--) {
				dp[j] = Math.max(dp[j], dp[j-weight] + value);
			}
		}

		System.out.println(dp[K]);
	}
}
