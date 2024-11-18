// https://velog.io/@2ludy/boj2589
package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_2589 { 
	
	static int R, C;
	static char[][] map; // map을 char 자료형으로 받음
	static int[] dr = {-1, 1, 0, 0}; // 상 하 좌 우 
	static int[] dc = {0, 0, -1, 1};
	static int max; // 
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		map = new char[R][C];
		
		for(int i=0; i<R; i++) {
			String s = br.readLine();
			for(int j=0; j<C; j++) {
				map[i][j] = s.charAt(j);
			}
		}
		
		max = 0;
		
		for(int i=0; i<R; i++) {
			for(int j=0; j<C; j++) {
				if(map[i][j] == 'L') {
					Queue<int[]> que = new LinkedList<>();
					que.offer(new int[] {i, j, 0});
					boolean[][] visited = new boolean[R][C];
					visited[i][j] = true;
					while(!que.isEmpty()) {
						int[] nums = que.poll();
						int r = nums[0];
						int c = nums[1];
						int dis = nums[2];
						max = Math.max(max, dis);
						
						for(int d=0; d<4; d++) {
							int nr = r+dr[d];
							int nc = c+dc[d];
							if(!check(nr,nc)) continue;
							if(map[nr][nc] == 'W') continue;
							if(visited[nr][nc]) continue;
							visited[nr][nc] = true;
							que.offer(new int[] {nr, nc, dis+1});
						}
					}
				}
			}
		}
		System.out.println(max);
	}

	private static boolean check(int r, int c) {
		return r>=0 && r<R && c>=0 && c<C;
	}
}
