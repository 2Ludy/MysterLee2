package baekjoon;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_15683 { // 감시
	// 6 : 벽 -> 통과 불가	
	// 사각 지대의 최소 크기를 구하기
	static int N, M, min;
	static int[] dir; // cctv 방향 저장
	static int[][] map, copyMap;
	static List<CCTV> list;
	static boolean[][] visited;
	static int[] dr = {-1,0,1,0};
	static int[] dc = {0,1,0,-1};
	
	static class CCTV {
		int r, c, type;

		public CCTV(int r, int c, int type) {
			this.r = r;
			this.c = c;
			this.type = type;
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		list = new ArrayList<>();
		min = Integer.MAX_VALUE;
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] >= 1 && map[i][j] < 6) {
					list.add(new CCTV(i,j,map[i][j]));
				}
			}
		}
		
		dir = new int[list.size()];
		dfs(0);		
		System.out.println(min);
	}

	static void dfs(int idx) {
		if(idx == list.size()) {
			copyMap = new int[N][M];
			for (int i = 0; i < N; i++) {
				copyMap[i] = map[i].clone();
			}
			
			for (int i = 0; i < list.size(); i++) {
				CCTV cctv = list.get(i);
				
				switch(cctv.type) {
				case 1 :
					// 한 방향 이동
					watch(cctv.r, cctv.c, dir[i]);
					break;
				case 2 :
					// 두 방향 (서로 반대방향) -> 수평, 수직
					watch(cctv.r, cctv.c, dir[i]);
					watch(cctv.r, cctv.c, (dir[i]+2)%4);
					break;
				case 3 :
					// 두 방향 (서로 90도 방향)
					watch(cctv.r, cctv.c, dir[i]);
					watch(cctv.r, cctv.c, (dir[i]+1)%4);
					break;
				case 4 :
					// 세 방향
					watch(cctv.r, cctv.c, dir[i]);
					watch(cctv.r, cctv.c, (dir[i]+1)%4);
					watch(cctv.r, cctv.c, (dir[i]+2)%4);	
					break;
				case 5 :
					// 네 방향
					watch(cctv.r, cctv.c, 0);
					watch(cctv.r, cctv.c, 1);
					watch(cctv.r, cctv.c, 2);
					watch(cctv.r, cctv.c, 3);
					break;
				}
			}
			
			blind();
			return; 
		}
		
		for (int d = 0; d < 4; d++) {
			dir[idx] = d;
			dfs(idx+1);
		}
			
	}

	static void watch(int r, int c, int d) {
		int nr = r + dr[d];
        int nc = c + dc[d];
        
        while(nr >= 0 && nc >= 0 && nr < N && nc < M && copyMap[nr][nc] != 6) {
            if(copyMap[nr][nc] == 0) {
                copyMap[nr][nc] = -1;
            }
            nr += dr[d];
            nc += dc[d];
        }
	}

	static void blind() {
		int cnt = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if(copyMap[i][j] == 0) cnt++;
			}
		}
		min = Math.min(min, cnt);
	}
		
}
