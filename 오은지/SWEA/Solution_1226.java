/*
문제해설 Notion
https://www.notion.so/swea-1226-1-115634ecbbc48012a3adc366754486b2
*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Solution_1226 { // 미로1 (bfs)
	static int T,x,y;
	static int[][] map;
	static boolean[][] visited;
	static int[] dr = {-1,0,1,0};
	static int[] dc = {0,1,0,-1};	
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		for (int t = 1; t <= 10; t++) {
			T = Integer.parseInt(br.readLine());
			map = new int[16][16];
			visited = new boolean[16][16];
			x = 0; // 시작 위치 초기화
			y = 0; // 시작 위치 초기화
			
			// 미로 입력
			for (int i = 0; i < 16; i++) {
				char[] tmp = br.readLine().toCharArray();
				for (int j = 0; j < 16; j++) {
					map[i][j] = tmp[j]-'0';
					if(map[i][j] == 2) { // 시작 위치 찾기
						x = i;
						y = j;
					}
				}
			}
			
			boolean res = bfs(x,y);
			sb.append("#").append(T).append(" ").append(res ? 1 : 0).append("\n");
			
		} // tc		
		System.out.print(sb);		
	}

	static boolean bfs(int x, int y) {
		Queue<int[]> que = new LinkedList<>();
		que.offer(new int[] {x,y});
		visited[x][y] = true;
		
		while(!que.isEmpty()) {
			int[] cur = que.poll();
			for (int d = 0; d < 4; d++) {
				int nr = cur[0] + dr[d];
				int nc = cur[1] + dc[d];
				if(!check(nr,nc) || visited[nr][nc]) continue;
				if(map[nr][nc] == 3) return true; // 도착점(3)에 도달 
				visited[nr][nc] = true;
				que.offer(new int[] {nr,nc});
			}
		}
		return false; // 도착점에 도달하지 못하면 false
	}

	static boolean check(int nr, int nc) {
		return nr >= 0 && nr < 16 && nc >= 0 && nc < 16 && map[nr][nc] != 1;
	}


}
