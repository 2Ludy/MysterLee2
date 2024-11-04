package study_1005;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * 지하터널정보
 * 0: 터널이 없음
 * 1: 상하좌우
 * 2: 상하
 * 3: 좌우
 * 4: 상우
 * 5: 하우
 * 6: 하좌
 * 7: 상좌
 * 
 * 1시간 후 맨홀 위치로 이동
 * 이 후 한 시간에 한 칸씩 이동 가능 (터널 이용)
 * 맨홀은 항상 터널이 있는 곳에 위치
 */

public class study_swea_1953 {
	
	static int T; // 테스트 케이스 수
	static int N, M; // 가로세로 크기, 5 이상 50 이하
	static int R, C; // 맨홀의 위치
	static int L; // 탈출 후 소요시간
	static int[][] map; // 지하 터널 지도 정보
	
	static int[] dr = {-1, 0, 1, 0}; // 상, 우, 하, 좌
	static int[] dc = {0, 1, 0, -1};
	static int[][] tunnel = {
			{}, {0, 1, 2, 3}, {0, 2}, {1, 3}, {0, 1},
			{1, 2}, {2, 3}, {0, 3}
	}; // 각 번호별 이어진 위치 저장

	static boolean[][] visited; // 해당 위치 방문 여부 저장
	
	static int ans; // 소요 시간동안 이동할 수 있는 칸의 수

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			// 입력값
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			R = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			L = Integer.parseInt(st.nextToken());
			
			map = new int[N][M];
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < M; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			visited = new boolean[N][M];
			
			// 정수형을 반환하는 bfs 메서드 실행
			ans = bfs(R, C);
			
			StringBuilder sb = new StringBuilder();
			sb.append("#").append(tc).append(" ").append(ans);
			System.out.println(sb);
		}

	}

	static int bfs(int sr, int sc) {
		Queue<int[]> que = new LinkedList<>();
		// Queue에 저장할 정수형 배열 {r, c, 시간}
		// 맨홀의 위치는 1시간 후에 도달
		que.offer(new int[] {sr, sc, 1});
		visited[sr][sc] = true;
		
		// bfs 메서드로 반환할 이동할 수 있는 칸의 수 초기화
		int cnt = 0;
		
		while (!que.isEmpty()) {
			int[] start = que.poll();
			int r = start[0];
			int c = start[1];
			int time = start[2];
			
			// 만약 소요시간을 넘어간 시간에 해당 위치에 도달했다면 continue
			if (time > L) continue;
			// 해당 위치에 도닳할 수 있다면, 이동 가능한 칸 수 +1
			cnt++;
			
			// 해당 위치의 터널이 몇 번 터널인지 파악
			int type = map[r][c];
			
			for (int d : tunnel[type]) {
				// d: 해당 터널에서 이동할 수 있는 방향
				// 델타를 이용해 이동할 칸의 위치 파악
				int nr = r + dr[d];
				int nc = c + dc[d];
				
				// 만약 map 범위를 벗어났다면 continue
				if (!check(nr, nc)) continue;
				// 만약 터널이 존재하지 않는 위치라면 continue
				if (map[nr][nc] == 0) continue;
				
				// 만약 해당 칸으로 이동할 수 있는데
				if (isConnected(type, d, map[nr][nc])) {
					// 이미 방문했던 칸이라면 continue
					if (visited[nr][nc]) continue;
					// 방문한 적 없는 칸이라면 방문 체크
					visited[nr][nc] = true;
					// Queue에 해당 칸과 1이 더해진 시간 삽입
					que.offer(new int[] {nr, nc, time + 1});
				}
				
			}
		}
		
		return cnt;
		
	}
	
	static boolean isConnected(int currType, int dir, int nextType) {
		// currType: 현재 위치의 터널 번호
		// dir: 이동할 방향
		// nextType: 해당 방향으로 갔을 떄 존재하는 터널의 번호
		switch (dir) {
		case 0: // 상
			// 위로 이동할 경우 1, 2, 5, 6번의 터널로만 이동 가능
			return nextType == 1 || nextType == 2 || nextType == 5 || nextType == 6;
		case 1: // 우
			// 오른쪽으로 이동할 경우 1, 3, 6, 7번의 터널로만 이동 가능
			return nextType == 1 || nextType == 3 || nextType == 6 || nextType == 7;
		case 2: // 하
			// 아래으로 이동할 경우 1, 2, 4, 7번의 터널로만 이동 가능
			return nextType == 1 || nextType == 2 || nextType == 4 || nextType == 7;
		case 3: //좌
			// 왼쪽으로 이동할 경우 1, 3, 4, 5번의 터널로만 이동 가능
			return nextType == 1 || nextType == 3 || nextType == 4 || nextType == 5;
		}
		
		return false;
	}

	// 배열의 범위를 벗어나는지 확인하는 메서드
	static boolean check(int r, int c) {
		// r의 범위: 0 ~ N-1
		// c의 범위: 0 ~ M-1
		return r >= 0 && r < N && c >= 0 && c < M;
	}

}
