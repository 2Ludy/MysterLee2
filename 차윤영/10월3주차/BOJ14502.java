import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * [연구소]
 * N x M 크기의 연구소
 * 일부 칸은 바이러스가 존재 -> 상하좌우 인접한 빈 칸으로 퍼져나감
 * 새로 새울 수 있는 벽의 수: 3 (반드시 세워야 함)
 * 바이러스의 개수: 2 이상 10 이하
 */

public class BOJ14502 {
	
	static int N, M; // 3 이상 8 이하
	static int[][] map; // 0: 빈칸, 1: 벽, 2: 바이러스
	static int[][] copy;
	static int max; // 안전 영역의 최대 크기
	// 빈 칸의 위치를 저장할 리스트
	static List<int[]> zero;
	
	// 빈 칸 중 3 칸 고르기
	static int[] pick;
	
	static int[] dr = {-1, 0, 1, 0};
	static int[] dc = {0, 1, 0, -1};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 입력
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		copy = new int[N][M];
		zero = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				// 만약 빈 칸이라면 해당 위치를 리스트에 배열로 저장
				if (map[i][j] == 0) zero.add(new int[] {i, j});
			}
		}
		// 벽을 세울 세 칸 선택 (리스트의 인덱스를 저장)
		pick = new int[3];
		// 안전 구역의 최댓값 0으로 초기화
		max = 0;
		
		// 리스트에 저장된 빈 칸 중 순서 없이 3개를 고를 combination 메서드
		combi(0, 0);
		
		System.out.println(max);
	}
	
	static void combi(int cnt, int start) {
		// 3개를 고르면 고른 빈 칸을 벽으로 바꾸는 메서드 실행, return
		if (cnt == 3) {
			change();
			return;
		}
		for (int i = start; i < zero.size(); i++) {
			pick[cnt] = i;
			combi(cnt+1, i+1);
		}
		
	}

	// 받은 인덱스의 list 위치가 벽으로 바뀌었을 때 안전한 위치의 개수 파악
	static void change() {
		// map을 깊은 복사 한 copy 배열을 이용해 메서드 진행
		for (int i = 0; i < N; i++) {
			System.arraycopy(map[i], 0, copy[i], 0, M);
		}
		for (int i = 0; i < 3; i++) {
			int tmp = pick[i];
			// combination으로 고른 빈 칸의 위치 반환
			int[] dir = zero.get(tmp);
			int tr = dir[0];
			int tc = dir[1];
			// 해당 위치를 벽으로 변경
			copy[tr][tc] = 1;
		}
		// 바뀐 지도에 대해 바이러스가 퍼지는 메서드
		fillvirus();
		// 바이러스가 퍼진 후 안전구역을 세는 메서드를 통해 반환받은 값과 max값 비교
		max = Math.max(max, findsafe());
	}
	
	// 해당 칸이 바이러스인 곳에 대하여 인접한 빈 칸을 바이러스로 채우는 메서드
	static void fillvirus() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				// 바이러스인 칸에 대하여 BFS 메서드 실행
				if (copy[i][j] == 2) virusbfs(i, j);
			}
		}
	}
	
	private static void virusbfs(int sr, int sc) {
		Queue<int[]> que = new LinkedList<>();
		que.offer(new int[] {sr, sc});
		
		while (!que.isEmpty()) {
			int[] start = que.poll();
			int r = start[0];
			int c = start[1];
			
			for (int d = 0; d < 4; d++) {
				int nr = r + dr[d];
				int nc = c + dc[d];
				
				// 배열의 범위를 벗어나는 위치이면 continue
				if (!check(nr, nc)) continue;
				// 벽이면 continue
				if (copy[nr][nc] == 1) continue;
				
				// 만약 바이러스와 인접한 빈 칸이면
				if (copy[nr][nc] == 0) {
					// 바이러스로 채워지고
					copy[nr][nc] = 2;
					// Queue에 해당 위치 삽입
					que.offer(new int[] {nr, nc});
				}	
			}
		}
	}

	// 안전 구역의 수를 구하는 메서드
	static int findsafe() {
		int cnt = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (copy[i][j] == 0) cnt++;
			}
		}
		
		return cnt;
	}
	
	// 배열의 범위를 벗어나는지 체크
	static boolean check(int r, int c) {
		return r >= 0 && r < N && c >= 0 && c < M;
	}

}
