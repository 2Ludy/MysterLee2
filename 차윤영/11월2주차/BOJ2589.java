import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * [보물섬]
 * 직사각형의 보물섬에 육지와 땅이 표시
 * 이동은 상하좌우로 이웃한 육지로만
 * 한 칸 이동에 한 시간
 * 보물: 최단거리로 이동할 때 가장 긴 시간이 걸리는 육지 두 곳에 나뉘어 묻힘
 * (같은 곳을 두 번 지나가거나, 멀리 돌아가면 안됨)
 * 보물이 뭍힌 두 곳 간의 최단 거리 이동 시간 출력
 */

public class BOJ2589 {
	
	static int N, M; // 세로, 가로 크기
	static char[][] map; // L: 육지, W: 바다
	static int max; // 보물이 묻힌 두 곳 간의 거리
	
	static int[] dr = {-1, 0, 1, 0}; // 상, 우, 하, 좌
	static int[] dc = {0, 1, 0, -1};
	

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new char[N][M];
		for (int i = 0; i < N; i++) {
			map[i] = br.readLine().toCharArray();
		}
		
		// 답을 0으로 초기화
		max = 0;
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] == 'L') {
					bfs(i, j);
				} 
			}
		}
		
		System.out.println(max);
	}


	static void bfs(int sr, int sc) {
		// sr, sc에서 해당 위치까지의 최단거리를 구할 맵
		int[][] minDist = new int[N][M];
		for (int i = 0; i < N; i++) {
			// 일단 최댓값으로 초기화
			Arrays.fill(minDist[i], Integer.MAX_VALUE);
		}
		Queue<int[]> que = new LinkedList<>();
		que.offer(new int[] {sr, sc, 0}); // r, c, 거리
		// sr, sc는 거리 0
		minDist[sr][sc] = 0;
		
		while (!que.isEmpty()) {
			int[] start = que.poll();
			int r = start[0];
			int c = start[1];
			int l = start[2];
			
			for (int d = 0; d < 4; d++) {
				int nr = r + dr[d];
				int nc = c + dc[d];
				
				// 범위를 벗어나면 continue
				if (!check(nr, nc)) continue;
				// 해당 위치가 물이면 continue
				if (map[nr][nc] == 'W') continue;
				
				// 저장된 거리보다 더 가까운 길이 있다면 다시 저장
				if (minDist[nr][nc] > l + 1) {
					que.offer(new int[] {nr, nc, l+1});
					minDist[nr][nc] = l+1;
				}
			}
		}
		
		// sr, sc에서의 가장 먼 육지를 찾는 로직
		int tmpMax = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (minDist[i][j] != Integer.MAX_VALUE) {
					tmpMax = Math.max(tmpMax, minDist[i][j]);
				}
			}
		}

		// 해당 위치가 보물이 있는 곳인지 아닌지 판별
		max = Math.max(tmpMax, max);
	}
	
	static boolean check(int r, int c) {
		return r >= 0 && r < N && c >= 0 && c < M;
	}

}
