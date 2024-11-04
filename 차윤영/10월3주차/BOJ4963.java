import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * [BOJ 2963 섬의 개수]
 * 
 * 한 정사각형과 가로, 세로, 대각선으로 연결된 사각형은 걸어갈 수 있는 사각형
 * 두 정사각형이 같은 섬 = 걸어갈 수 있는 경로가 있다
 * 섬의 개수 파악
 */

public class BOJ4963 {
	
	static int w, h; // 너비, 높이 (50 이하의 양의 정수)
	static int[][] map; // 0: 바다, 1: 땅
	static int ans;
	
	static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1}; // 위부터 시계방향
	static int[] dc = {0, 1, 1, 1, 0, -1, -1, -1};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 가장 처음의 w, h 입력 받기
		w = Integer.parseInt(st.nextToken());
		h = Integer.parseInt(st.nextToken());
		
		while (w != 0 && h != 0) {
			// map 초기화, 입력
			map = new int[h][w];
			for (int i = 0; i < h; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < w; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			// flood fill을 2부터 시작하기 위한 cnt 초기화 (++cnt 이용)
			int cnt = 1;
			
			// map을 탐색하면서 여전히 1인 구역을 발견하면, flood fill을 진행할 bfs 메서드 호출
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
					if (map[i][j] == 1) {
						bfs(i, j, ++cnt);
					}
				}
			}
			
			// cnt를 2부터 시작했으므로, ans = cnt - 2 + 1
			ans = cnt - 2 + 1;
			
			sb.append(ans).append("\n");
			
			// 다음 테스트 케이스를 진행할 w, h 입력받은 후, 둘 다 0이면 break
			st = new StringTokenizer(br.readLine());
			w = Integer.parseInt(st.nextToken());
			h = Integer.parseInt(st.nextToken());
		}
		System.out.println(sb);
	}
	
	static void bfs(int sr, int sc, int cnt) {
		Queue<int[]> que = new LinkedList<>();
		// queue에 시작점 삽입
		que.offer(new int[] {sr, sc});
		// 시작점을 cnt로 저장
		map[sr][sc] = cnt;
		while (!que.isEmpty()) {
			int[] start = que.poll();
			int r = start[0];
			int c = start[1];
			
			for (int d = 0; d < 8; d++) {
				// 해당 지점의 인접한 곳에 대해
				int nr = r + dr[d];
				int nc = c + dc[d];
				
				// 범위를 벗어난다면 continue
				if (!check(nr, nc)) continue;
				
				// 이미 다른 cnt로 채워졌거나, 바다이면 continue
				if (map[nr][nc] != 1) continue;
				
				// 그게 아니라면, queue에 삽입, map에 cnt로 저장
				que.offer(new int[] {nr, nc});
				map[nr][nc] = cnt;
			}
		}
		
	}

	// (r, c)가 범위 내에 있음을 확인하는 메서드
	static boolean check(int r, int c) {
		return r >= 0 && r < h && c >= 0 && c < w;
	}

}
