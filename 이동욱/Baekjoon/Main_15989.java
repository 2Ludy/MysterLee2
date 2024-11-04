package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main_15989 {  // https://www.acmicpc.net/problem/15989  1,2,3 더하기 4

	/*  해당 문제는 백준 p,2293 동전 1 문제와 동일한데,
	 *  해당 문제에는 정수가 [1,2,3] 3가지로 정해져 있으므로 더 쉽다...
	 *  코인문제와 동일한 점화식 이용하여 풀이
	 */


	static int T;  // 테스트케이스
	static int N;  //  목표 정수
	static int[] nums;  // 목표 정수를 만들기 위한 카운팅 배열
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		for(int t=1; t<=T; t++) {
			N = Integer.parseInt(br.readLine());
			nums = new int[N+1]; // 카운팅 배열은 N번째 인덱스까지 도달해야 하므로 N+1로 초기화
			nums[0] = 1;  //  0번째 인덱스 1로 초기화
			
			for(int i=1; i<=3; i++) { // 정수가 1,2,3 으로 정해져 있으므로... for문 1,2,3
				for(int j=i; j<=N; j++) {
					nums[j] += nums[j-i];  // 카운팅 배열 갱신해주기 
				}
			}
			
			System.out.println(nums[N]); // 마지막 인덱스 출력
		}
	}
}
