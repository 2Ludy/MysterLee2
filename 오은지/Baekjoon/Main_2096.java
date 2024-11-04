/*
[ Notion url ]
https://www.notion.so/Algol-115634ecbbc48048a999c7f49465ed3b?p=11d634ecbbc480c2953be894ab76e79a&pm=s
 */

package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_2096 { // 내려가기 (dp)
	static int N,max,min;
	static int[][] map,maxSum,minSum;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N+1][3]; // 입력 숫자 배열 (dp를 위한 +1)
		
		for (int i = 1; i < N+1; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 3; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		maxSum = new int[N+1][3]; // 최대합 dp배열
		minSum = new int[N+1][3]; // 최소합 dp배열
		
		for (int i = 1; i < N+1; i++) {
			// 각 줄마다 세 위치에 대한 최대/최소값 구하기
			maxSum[i][0] += Math.max(maxSum[i-1][0], maxSum[i-1][1]) + map[i][0];
			maxSum[i][1] += Math.max(Math.max(maxSum[i-1][0], maxSum[i-1][1]), maxSum[i-1][2]) + map[i][1];		
			maxSum[i][2] += Math.max(maxSum[i-1][1], maxSum[i-1][2]) + map[i][2];
			
			minSum[i][0] += Math.min(minSum[i-1][0], minSum[i-1][1]) + map[i][0];
			minSum[i][1] += Math.min(Math.min(minSum[i-1][0], minSum[i-1][1]), minSum[i-1][2]) + map[i][1];		
			minSum[i][2] += Math.min(minSum[i-1][1], minSum[i-1][2]) + map[i][2];
		}
		
		max = Integer.MIN_VALUE;
		min = Integer.MAX_VALUE;
		
		for (int i = 0; i < 3; i++) {
			max = Math.max(max, maxSum[N][i]);
			min = Math.min(min, minSum[N][i]);
		}
		
		System.out.println(max + " " + min);		
	}
}
