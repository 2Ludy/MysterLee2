package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_10026 {

	static int N;
	static int[][] map; // R = -1, G = -2, B = -3
	static int[][] map2;
	static int[] dr = {-1, 0, 1, 0};
	static int[] dc = {0, -1, 0, 1};
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		map2 = new int[N][N];
		for(int i=0; i<N; i++) {
			String s = br.readLine();
			for(int j=0; j<N; j++) {
				char c = s.charAt(j);
				if(c == 'R') {
					map[i][j] = -1;
					map2[i][j] = -1;
				}else if(c == 'G') {
					map[i][j] = -2;
					map2[i][j] = -1;
				}else {
					map[i][j] = -3;
					map2[i][j] = -2;
				}
			}
		}

		int num = 0;

		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(map[i][j] == -1 || map[i][j] == -2 || map[i][j] == -3) {
					int color = map[i][j];
					map[i][j] = num++;
					Queue<int[]> que = new LinkedList<>();
					que.offer(new int[] {i,j});
					while(!que.isEmpty()) {
						int[] nums = que.poll();
						int r = nums[0];
						int c = nums[1];
						for(int d=0; d<4; d++) {
							int nr = r+dr[d];
							int nc = c+dc[d];
							if(!check(nr,nc)) continue;
							if(map[nr][nc] != color) continue;
							map[nr][nc] = num;
							que.offer(new int[] {nr,nc});
						}
					}
				}
			}
		}

		int num2 = 0;

		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(map2[i][j] == -1 || map2[i][j] == -2) {
					int color = map2[i][j];
					map2[i][j] = num2++;
					Queue<int[]> que = new LinkedList<>();
					que.offer(new int[] {i,j});
					while(!que.isEmpty()) {
						int[] nums = que.poll();
						int r = nums[0];
						int c = nums[1];
						for(int d=0; d<4; d++) {
							int nr = r+dr[d];
							int nc = c+dc[d];
							if(!check(nr,nc)) continue;
							if(map2[nr][nc] != color) continue;
							map2[nr][nc] = num2;
							que.offer(new int[] {nr,nc});
						}
					}
				}
			}
		}
		System.out.println(num + " " + num2);
	}
	static boolean check(int r, int c) {
		return r>=0 && r<N && c>=0 && c<N;
	}
}
