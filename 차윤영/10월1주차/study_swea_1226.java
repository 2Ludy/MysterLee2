package study_1005;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

/*
 * 16 x 16 크기의 map
 * 0: 길
 * 1: 벽
 * 2: 출발점
 * 3: 도착점
 * 출발점에서 도착점까지 이동이 가능하면 1, 불가능하면 0 출력
 */

public class study_swea_1226 {
	
	static int T = 10;
	static int tc;
	static int[][] map; // 16 x 16 / 1: 벽, 0: 길, 2: 출발점, 3: 도착점
	static int ans; // 가능하면 1, 불가능하면 0
	static int[] start; // 시작점 위치 {sr, sc}
	static int[] end; // 도착점 위치 {er, ec}
	
	static int[] dr = {-1, 0, 1, 0}; // 상, 우, 하, 좌 순서
	static int[] dc = {0, 1, 0, -1};
	
	static boolean[][] visited;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for (int t = 1; t <= T; t++) {
			tc = Integer.parseInt(br.readLine());
			map = new int[16][16];
			
			start = new int[2];
			end = new int[2];
			
			// 입력값
			for (int i = 0; i < 16; i++) {
				String tmp = br.readLine();
				for (int j = 0; j < 16; j++) {
					map[i][j] = tmp.charAt(j) - '0';
					// 출발점일 경우 위치를 start에 저장
					if (map[i][j] == 2) { 
						start[0] = i;
						start[1] = j;
					} 
					// 도착점일 경우 위치를 end에 저장
					else if (map[i][j] == 3) { 
						end[0] = i;
						end[1] = j;
					}
				}
			}
			
			// map의 크기와 동일한 boolean 배열 선언
			visited = new boolean[16][16]; 
			
			// 답이 0이라고 설정 -> 이후 도착점으로의 이동이 가능하면 1로 변경
			ans = 0; 
			
			// 시작점에서 bfs 메서드 시작
			bfs(start[0], start[1]); 
			
			StringBuilder sb = new StringBuilder();
			sb.append("#").append(tc).append(" ").append(ans);
			System.out.println(sb);
		}

	}

	static void bfs(int sr, int sc) {
		Queue<int[]> que = new LinkedList<>();
		que.offer(new int[] {sr, sc});
		visited[sr][sc] = true;
		
		while (!que.isEmpty()) {
			int[] start = que.poll();
			int r = start[0];
			int c = start[1];
			
			// 도착점에 도달할 경우 메서드 종료
			if (r == end[0] && c == end[1]) { 
				ans = 1;
				return;
			}
			
			for (int d = 0; d < 4; d++) {
				int nr = r + dr[d];
				int nc = c + dc[d];
				
				// 범위를 벗어날 경우 continue
				if (!check(nr, nc)) continue; 
				// 이동할 수 없는 벽일 경우 continue
				if (map[nr][nc] == 1) continue; 
				// 이미 방문한 곳이라면 continue
				if (visited[nr][nc]) continue; 
				
				que.offer(new int[] {nr, nc});
				visited[nr][nc] = true;
				
			}
		}
		
	}
	
	// 배열의 범위를 벗어나는지 확인하는 메서드
	static boolean check(int r, int c) {
		// r의 범위: 0 ~ 16-1
		// c의 범위: 0 ~ 16-1
		return r >= 0 && r < 16 && c >= 0 && c < 16;
	}

}
