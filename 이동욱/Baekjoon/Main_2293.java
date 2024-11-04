package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_2293 { // https://www.acmicpc.net/problem/2293 // 동전1

	/*  dp로 풀어 나갈 것인데, 어떠한 동전들이 있을때 
	 *  목표로 만들 금액에 대한 점화식은
	 *  목표로 만들 금액에 대한 경우의수 = [목표금액-어떠한 동전의 값]을 계산한 금액에 대한 경우의 수를 모두 더한 값
	 *  으로 볼 수 있다.
	 *  따라서 2차원으로 접근하여 가장 마지막 인덱스를 더해줘도 되지만,
	 *  반복문 2개를 이용하여 
	 *  1차원 인덱스로 접근하여 동전 한 종류씩 갱신해줘도 된다.
	 */
	
	static int n, k;
	static int[] coins, count; // 코인의 값 배열, 목표 금액에 따른 카운팅 배열
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		coins = new int[n]; // 코인 배열
		
		for(int i=0; i<n; i++) {
			coins[i] = Integer.parseInt(br.readLine());
		}
		
		count = new int[k+1]; // 목표 금액인 k까지 인덱스가 가야 하므로 k+1로 초기화
		count[0] = 1;  // 아무 동전도 사용하지 않는 경우 1로 초기화
		
		for(int i=0; i<n; i++) { // 코인 순서를 나타낼 배열
			for(int j=coins[i]; j<=k; j++) { // 해당 코인에 대한 점화식을 이용하여 count 배열을 계속 갱신해줌
				count[j] += count[j-coins[i]];
			}
		}
		System.out.println(count[k]); // 갱신 완료 후 출력
	}
}
