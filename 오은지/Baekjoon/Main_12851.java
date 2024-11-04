/*
[ Notion url ]
https://www.notion.so/Algol-115634ecbbc48048a999c7f49465ed3b?p=121634ecbbc480249b38f7a49df56e3d&pm=s
*/
package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_12851 { // 숨바꼭질2 
	// 이동방법 3가지 -> +1, -1, *2
	// 1. 가장 빨리 도달하는 시간
	// 2. 가장 빠른 시간으로 찾는 방법의 수
	static int N,K,minTime,cnt;
	static int[] time;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 시작 위치
		K = Integer.parseInt(st.nextToken()); // 목표 위치
		time = new int[100001]; // 인덱스의 위치에 도달 시간 기록
		minTime = 0; // 최소 시간
		cnt = 0; // 경우의 수
				
		if(N == K) {
			System.out.println(0);
			System.out.println(1);
		} else {
			bfs(N);
			System.out.println(minTime+"\n"+cnt);
		}
		
	}

	static void bfs(int n) {
		Queue<Integer> que = new LinkedList<Integer>();
		que.offer(n);
		time[n] = 1;
		
		while(!que.isEmpty()) {
			int cur = que.poll();
			
			// 현재위치 시간이 최소 시간 초과했거나, 최소시간을 이미 찾았다면 탐색 중지
			if(minTime < time[cur] && minTime != 0) {
				return;
			}
					
			for (int d = 0; d < 3; d++) {
				int next;
				
				if(d == 0) {
					next = cur + 1;
				} else if(d == 1) {
					next = cur - 1;
				} else {
					next = cur * 2;
				}

				// 경계 체크
				if(next < 0 || next >= time.length) continue;
				
				// 목표 도달
				if(next == K) {
					if(minTime == 0) { // 첫 도달
						minTime = time[cur];
						cnt = 1;
					} else if(time[cur] == minTime) { // 재방문
						cnt++;
					}
				}

				// 방문하지 않았거나(0), 같은 시간 시간에 도달했어도 큐 진행
				if(time[next] == 0 || time[next] == time[cur] + 1) {
				    que.offer(next);
				    time[next] = time[cur] + 1;
				}
				
			}
			
		} // while
	}

}
