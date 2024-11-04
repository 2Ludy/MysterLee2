/*
[ Notion url ]
https://www.notion.so/Algol-115634ecbbc48048a999c7f49465ed3b?p=11d634ecbbc480c2953be894ab76e79a&pm=s
 */

package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_2293 { // 동전1
	// n가지 동전의 가치로 합이 k가 되도록 하는 경우의 수 구하기
	// 동전은 몇 개라도 사용 가능
	static int n,k;
	static int[] coinValues,dp;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		n = Integer.parseInt(st.nextToken()); // 동전 종류의 수
		k = Integer.parseInt(st.nextToken()); // 목표 가치 합
		coinValues = new int[n+1]; // 각 동전의 가치 배열
		dp = new int[k+1]; // 경우의 수를 담을 dp배열
		dp[0] = 1; // 0원 경우의 수는 1
		
		for (int i = 1; i < n+1; i++) {
			coinValues[i] = Integer.parseInt(br.readLine());
			// 현재 동전으로 만들 수 있는 모든 가치 
			for (int j = coinValues[i]; j < k+1 ; j++) {
				// j : 현재 가치
				// dp[j] : 현재 가치를 만드는 경우의 수
				dp[j] += dp[j - coinValues[i]];
			}
		}
		
		System.out.println(dp[k]);
		
	}
}
