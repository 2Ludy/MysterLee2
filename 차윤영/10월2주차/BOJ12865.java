import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * [평범한 배낭]
 * 
 * N 개의 물건에 무게 W와 가치 V가 존재
 * 최대 K만큼의 무게를 넣을 수 있는 배낭일 때
 * 가치의 최대 합은?
 */

public class BOJ12865 {
	
	static int N, K;
	static int[][] bag; // 무게 (1 이상, 100000이하), 가치 (0 이상, 1000 이하)
	static int[] dp;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 입력
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		bag = new int[N+1][2];
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			bag[i][0] = Integer.parseInt(st.nextToken()); // 무게
			bag[i][1] = Integer.parseInt(st.nextToken()); // 가치
		}
		
		// 해당 무게에 대한 최대 가치를 저장할 배열 생성
		dp = new int[K+1];

		for (int i = 1; i <= N; i++) {
			// 물건 i에 대해, 물건 i를 포함하는 경우와 포함하지 않는 경우의 최대 가치를 비교
			int W = bag[i][0];
			int V = bag[i][1];
			// 무게를 거꾸로 사용 -> 중복 방지
			for (int j = K; j >= W; j--) {
				dp[j] = Math.max(dp[j], dp[j-W]+V);
			}
		}
		
		System.out.println(dp[K]);
	}

}
