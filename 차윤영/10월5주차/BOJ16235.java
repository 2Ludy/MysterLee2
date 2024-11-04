import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * [인구 이동]
 * N x N 크기의 땅
 * 각 칸은 하나의 나라, 각 나라의 인구 제시
 * 인접한 나라 사이에는 국경선 존재
 * 인구 이동이 진행 -> 더 이상 인구 이동이 없을 때 까지 지속
 * 1. 국경선을 공유하는 두 나라의 인구 차이가 L 이상 R 이하 -> 국경선을 하루동안 연다
 * 2. 열어야하는 국경선이 모두 열렸다면 인구이동 시작
 * 3. 국경선이 열려있어 인접한 칸 만을 이용해 이동 가능, 그 나라를 하루동안 연합이라고 함
 * 4. 연합을 이루는 각 칸의 인구수는 (연합의 인구수) / (연합을 이루는 칸 수)가 됨 -> 소수점 버림
 * 5. 연합 해체, 국경선 닫음
 */

public class BOJ16235 {
	
	// (N: 1 이상 50 이하, L, R: 1 이상 100 이하)
	static int N, L, R; 
	// 각 나라의 인구
	static int[][] popul; 
	// 열었는지 안열었는지 확인할 맵
	static int[][] open; 
	static boolean[][] visited;
	// 각 구역의 정보를 저장할 배열
	static int[][] nums; 
	static int ans; // 답
	
	// 델타 (상, 우, 하, 좌)
	static int[] dr = {-1, 0, 1, 0}; 
	static int[] dc = {0, 1, 0, -1};

public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 땅의 크기
		L = Integer.parseInt(st.nextToken()); // 인구 차이 최솟값
		R = Integer.parseInt(st.nextToken()); // 인구 차이 최댓값
		
		popul = new int[N][N]; // 각 나라의 인구수 저장
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				popul[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		ans = 0;
		
		while (true) {
			// 각 나라가 몇 번 째 구역인지를 구분할 배열
			open = new int[N][N];
			// bfs 메서드에서 방문된 적이 있는지 체크할 배열
			visited = new boolean[N][N];
			int cnt = 0;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					// 만약 방문한 적이 없는 나라라면
					if (!visited[i][j]) {
						// 인구 이동이 가능한 구역을 찾는 bfs 메서드
						// 같은 구역의 나라를 cnt 숫자로 묶음
						bfs(i, j, ++cnt);
					}
				}
			}
			
			// {해당 구역의 나라 수, 해당 구역의 인구 총합, 해당 구역의 평균 인구 수} 저장
			nums = new int[cnt+1][3]; 
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					int tmp = open[i][j];
					nums[tmp][0]++; // 나라 수 세기
					nums[tmp][1] += popul[i][j]; // 인구 수 더하기
				}
			}
			
			// 만약 모든 국경이 닫혀, N*N 개의 구역으로 나누어지면 while문 종료
			if (cnt == N*N) break;
			// 그렇지 않다면 횟수 추가
			ans++;

			
			for (int i = 0; i <= cnt; i++) {
				// 하나의 나라만 있는 구역이라면 continue
				if (nums[i][0] <= 1) continue;
				// 두 개 이상의 나라가 있는 구역이라면 평균 인구 수 저장
				nums[i][2] = nums[i][1] / nums[i][0];
			}
			
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					// 해당 나라가 몇 번째 구역인지
					int tmp = open[i][j];
					// 그 구역에 존재하는 유일한 나라라면 continue
					if (nums[tmp][0] == 1) continue;
					// 그렇지 않다면 평균 인구 수로 분배
					popul[i][j] = nums[tmp][2];
				}
			}
			
		}
		
		
		System.out.println(ans);
	}

	static void bfs(int sr, int sc, int num) {
		// 큐 생성
		Queue<int[]> que = new LinkedList<>();
		que.offer(new int[] {sr, sc});
		// 시작한 곳의 구역 설정
		open[sr][sc] = num;
		// 방문 체크
		visited[sr][sc] = true;
		while (!que.isEmpty()) {
			int[] start = que.poll();
			int r = start[0];
			int c = start[1];
			for (int d = 0; d < 4; d++) {
				int nr = r + dr[d];
				int nc = c + dc[d];
				// 배열의 범위를 벗어나면 continue
				if (!check(nr, nc)) continue;
				// 이미 bfs 메서드에서 확인한 나라이면 continue
				if (visited[nr][nc]) continue;
				// 해당 나라와 인접한 나라의 인구 차이
				int diff = Math.abs(popul[nr][nc] - popul[r][c]);
				// 인구 차이가 L 이상 R 이하이면
				if (diff >= L && diff <= R) {
					// 인접한 나라를 동일한 구역으로 지정
					open[nr][nc] = num;
					// 방문 체크
					visited[nr][nc] = true;
					// 큐에 삽입
					que.offer(new int[] {nr, nc});
				}
			}
		}
	
	}

	// 배열의 범위를 벗어나는지 체크
	static boolean check(int r, int c) {
		return r >= 0 && r < N && c >= 0 && c < N;
	}

}
