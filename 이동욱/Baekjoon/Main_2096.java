package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Main_2096 { // https://www.acmicpc.net/problem/2096 내려가기
	
	/*
	 * 밑으로 내려갈때 인급한 수로만 이동 가능 > 그렇다면 밑에있는 값은 인접한 위의 수들과 계산한 값임
	 * 
	 * 최소값과 최대값을 담을 배열을 각각 만들어서
	 * 최소값과 최대값 배열의 0번 인덱스 행은 scroes 값을 넣어주고
	 * 1번 인덱스 행 부터는 위의 행과 값을 비교해서 값을 넣어주며 진행
	 * 
	 */
	
	static int N;
	static int[][] scores; // 각 단계별 점수를 담을 배열
	static int[][] min; // 각 단계별 최소값을 담을 배열 
	static int[][] max; // 각 단계별 최대값을 담을 배열
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		scores = new int[N][3]; // 점수 배열 초기화
		min = new int[N][3];  // 최소값 배열 초기화
		max = new int[N][3];  // 최대값 배열 초기화
		
		for(int i=0; i<N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j=0; j<3; j++) {
				scores[i][j] = Integer.parseInt(st.nextToken()); // scores 배열 값 넣기
			}
		}
		
		min[0] = scores[0].clone(); // min 배열의 0번 인덱스 행의 값은 scroes 배열의 0번 인덱스 값과 동일
		max[0] = scores[0].clone(); // max 배열의 0번 인덱스 행의 값은 scroes 배열의 0번 인덱스 값과 동일
		
		for(int i=1; i<N; i++) { // 0번 인덱스 행은 채워져 있으니 1번 인덱스의 행 부터
			min[i][0] = Math.min(min[i-1][0], min[i-1][1]) + scores[i][0];  // 인접한 위의 두 행과 비교한 값과 더하기
			min[i][1] = Math.min(min[i-1][0], Math.min(min[i-1][1], min[i-1][2])) + scores[i][1]; // 인접한 위의 세 행과 비교한 값과 더하기
			min[i][2] = Math.min(min[i-1][1], min[i-1][2]) + scores[i][2]; // 인접한 위의 두 행과 비교한 값과 더하기
			
			max[i][0] = Math.max(max[i-1][0], max[i-1][1]) + scores[i][0]; // 인접한 위의 두 행과 비교한 값과 더하기
			max[i][1] = Math.max(max[i-1][0], Math.max(max[i-1][1], max[i-1][2])) + scores[i][1]; // 인접한 위의 세 행과 비교한 값과 더하기
			max[i][2] = Math.max(max[i-1][1], max[i-1][2]) + scores[i][2]; // 인접한 위의 두 행과 비교한 값과 더하기
		}
		
		int minScore = Math.min(min[N-1][0], Math.min(min[N-1][1], min[N-1][2])); // 가장 마지막 인덱스의 행 값 비교하여 min 값 찾기
		int maxScore = Math.max(max[N-1][0], Math.max(max[N-1][1], max[N-1][2])); // 가장 마지막 인덱스의 행 값 비교하여 max 값 찾기
	
		System.out.println(maxScore + " " + minScore); // 출력
	}
}
