import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ2293 {
	
	static int n, k;
	static int[] value;
	static int[] dp; // dp[i] = j -> i원을 만드는데 가능한 경우의 수: j

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 입력
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		
		value = new int[n];
		for (int i = 0; i < n; i++) {
			value[i] = Integer.parseInt(br.readLine());
		}
		// 동전의 가치를 오름차순으로 정렬
		Arrays.sort(value);
		
		// 0 ~ K원까지 경우의 수를 구하는 dp 배열 생성
		dp = new int[k+1];
		
		// 0원을 고르는 경우의 수는 1
		dp[0] = 1;
		for (int i = 0; i < n; i++) {
			// 해당 동전의 가치에 대해
			int t = value[i];
			// 만들고자 하는 금액이 이 동전의 가치보다 클 때, 
			for (int j = t; j <= k; j++) {
				// j원을 만드는 경우의 수에 j-t원을 만드는 경우의 수를 더해줌
				dp[j] += dp[j-t];
			}
		}
		
		System.out.println(dp[k]);
		
	}

}