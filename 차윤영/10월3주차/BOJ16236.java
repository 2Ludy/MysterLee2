import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * [아기 상어]
 * 
 * 공간의 상태: 0 (빈 칸), 1~6 (칸에 있는 물고기의 크기), 9 (아기 상어의 위치)
 * 아기 상어는 한 마리 존재
 * 
 * 물고기 M 마리와 아기 상어 1 마리가 크기를 가지고 있음
 * 아기 상어의 처음 크기는 2
 * 아기 상어는 1초에 상하좌우로 인접한 한 칸씩 이동
 * 물고기를 먹을 때 걸리는 시간은 없음 (이동과 동시에 먹음, 먹으면 빈 칸이 됨)
 * 아기 상어 < 물고기: 이동 X
 * 아기 상어 = 물고기: 먹을 수 X, 이동 O
 * 아기 상어 > 물고기: 먹을 수 O, 이동 O
 * 더 이상 먹을 수 있는 물고기가 없다면 엄마 상어에게 도움 요청
 * 먹을 수 있는 물고기 중 가장 가까운 물고기를 먹으러 감 (맨해튼 거리)
 * 물고기의 거리가 동일하다면 가장 위쪽 가장 왼쪽의 물고기 먹음
 * 자신의 크기만큼 물고기를 먹으면 크기가 1 증가 (크기가 2일 때, 물고기 2마리 -> 크기 3)
 * 
 * 아기 상어가 엄마 상어에게 도움을 요청하지 않고 물고기를 잡아먹을 수 있는 시간 출력
 */

public class BOJ16236 {
	
	static int N; // 공간의 크기, 2 이상 20 이하
	static int[][] map; // 공간의 상태
	
	static int[] dr = {-1, 0, 1, 0};
	static int[] dc = {0, 1, 0, -1};
	
	static int sr; // 아기 상어의 위치
	static int sc;
	static int eat; // 아기 상어가 먹은 물고기 수
	static int size; // 아기 상어의 크기
	static int time; // 시간
	static int[][] dist; // 아기상어로부터의 거리

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		sr = -1;
		sc = -1;
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == 9) {
					sr = i;
					sc = j;
				}
			}
		}
		
		size = 2; // 처음 아기 상어의 크기는 2
		eat = 0; // 먹은 물고기 수 초기화
		time = 0; // 시작 시간 초기화
		
		
		while (havefish()) {
			// 아기 상어가 이동하면서 받아올 정보, 이동한 칸, 이동 완료한 위치
			int[] info = findnear(); // r, c, 이동한 칸
			
			// 먹을 수 있는 물고기가 없으면 종료
			if (info[0] == -1) break;
			
			// 원래 아기 상어가 있던 곳은 빈 칸으로 처리
			map[sr][sc] = 0;
			sr = info[0];
			sc = info[1];
			// 먹은 물고기의 위치는 빈 칸으로 처리
			map[sr][sc] = 0;
			
			// 이동한 칸 만큼 시간에 더해주기
			time += info[2];
			// 먹은 물고기 추가
			eat++;
			
			// 만약 먹은 물고기가 아기 상어의 크기와 같다면
			if (eat == size) {
				// 먹은 물고기 수는 초기화
				eat = 0;
				// 아기 상어의 크기는 1 증가
				size++;
			}
		}

		System.out.println(time);
	}

	// 가장 가까운 위치의 먹을 수 있는 물고기 찾는 메서드
	static int[] findnear() {
		int[] ans = new int[] {-1, -1, Integer.MAX_VALUE};
		
		// 아기 상어보다 작은 물고기들의 아기 상어와의 위치 저장
		dist = new int[N][N];
		for (int i = 0; i < N; i++) {
			Arrays.fill(dist[i], Integer.MAX_VALUE);
		}
		
		bfs(sr, sc);
		
		// 가장 가까운 물고기 찾기
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				// 아기 상어보다 작은 물고기일 때
				if (map[i][j] > 0 && map[i][j] < size) {
					if (dist[i][j] < ans[2]) {
						ans = new int[] {i, j, dist[i][j]};
					} 
				}
				// 만약 거리가 같다면, 가장 위의 왼쪽의 물고기 선택
				else if (dist[i][j] == ans[2]) {
					if (i < ans[0] || (i == ans[0] && j < ans[1])) {
						ans = new int[] {i, j, dist[i][j]};
					} 
				}
			}
		}
		
		return ans;
	}

	static void bfs(int ssr, int ssc) {
		Queue<int[]> que = new LinkedList<>();
		que.offer(new int[] {ssr, ssc, 0});
		
		boolean[][] visited = new boolean[N][N];
		visited[ssr][ssc] = true;
		
		while (!que.isEmpty()) {
			int[] start = que.poll();
			int r = start[0];
			int c = start[1];
			int time = start[2];
			
			for (int d = 0; d < 4; d++) {
				int nr = r + dr[d];
				int nc = c + dc[d];
				
				if (!check(nr, nc)) continue;
				if (visited[nr][nc]) continue;
				if (map[nr][nc] > size) continue;
				
				visited[nr][nc] = true; // 방문 표시
				
				// 빈 칸이거나 아기 상어와 크기가 같은 물고기가 있다면 이동
				if (map[nr][nc] == 0 || map[nr][nc] == size) {
					que.offer(new int[] {nr, nc, time+1});
				} 
				// 아기 상어보다 크기가 작은 물고기가 있으면
				else if (map[nr][nc] < size) {
					// 해당 칸 까지 도달할 수 있는 최소 이동 거리 저장
					if (time + 1 < dist[nr][nc]) {
						dist[nr][nc] = time + 1;
					}
				}
			}
		}
		
	}

	// 아기 상어가 먹을 수 있는 물고기가 있는지 확인하는 메서드
	static boolean havefish() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				// 빈 칸이 아니면서, 아기 상어보다 작은 크기의 물고기가 있다면 true 반환
				if (map[i][j] > 0 && map[i][j] < size) return true;
			}
		}
		// 아기 상어보다 작은 물고기를 발견하지 못했다면 false 반환
		return false;
	}
	
	static boolean check(int r, int c) {
		return r >= 0 && r < N && c >= 0 && c < N;
	}

}
