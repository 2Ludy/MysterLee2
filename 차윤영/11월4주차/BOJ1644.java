package nov_4주차;

import java.util.Scanner;

/*
 * 하나 이상의 연속된 소수의 합으로 나타낼 수 있는 자연수
 * 연속된 소수의 합으로 나타낼 수 있는 경우의 수
 */

public class BOJ1644 {
	
	static int N; // 주어지는 자연수
	static int cnt; // 경우의 수 (답)
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		cnt = 0;
		
		// 소수 리스트륾 미리 생성
		boolean[] prime = new boolean[N+1];
		for (int i = 2; i <= N; i++) {
			prime[i] = true; // 일단 모두 true로 저장
		}
		for (int i = 0; i * i <= N; i++) {
			if (prime[i]) {
				// i가 소수라면, i의 배수들은 소수가 아님
				for (int j = i * i; j <= N; j += i) {
					prime[j] = false;
				}
			}
		}
		
		for (int i = 2; i <= N; i++) {
			int sum = 0;
			// 만약 해당 숫자가 소수라면 반복문 시작
			if (prime[i]) {
				for (int j = i; j <= N; j++) {
					// 소수라면 sum에 더함
					if (prime[j]) sum += j;
					// sum이 N과 같으면 경우의수 1 추가, 반복문 break
					if (sum == N) {
						cnt++;
						break;
					}
					// sum이 N보다 커지면 반복문 break
					if (sum > N) break;
				}
			}
		}
		
		System.out.println(cnt);
	}
	
}
