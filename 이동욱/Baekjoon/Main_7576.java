package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_7576 {
	
	static int M, N;
	static int[][] map;
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	static int BeforeTomato;
	static int day;
	static Queue<int[]> que;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());

		BeforeTomato = 0;
		day = 0;
		que = new LinkedList<>();
		map = new int[N][M];

		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<M; j++) {
				int num = Integer.parseInt(st.nextToken());
				if(num == 0) {
					BeforeTomato++;
				} else if(num == 1) {
					que.offer(new int[] {i,j});
				}
				map[i][j] = num;
			}
		}

		while(!que.isEmpty()) {
			if(BeforeTomato == 0) {
				System.out.println(day);
				return;
			}
			int size = que.size();
			for(int i=0; i<size; i++) {
				int[] nums = que.poll();
				int r = nums[0];
				int c = nums[1];
				for(int d=0; d<4; d++) {
					int nr = r+dr[d];
					int nc = c+dc[d];

					if(!check(nr,nc)) continue;
					if(map[nr][nc] != 0) continue;

					map[nr][nc] = 1;
					que.offer(new int[] {nr,nc});
					BeforeTomato--;
				}
			}
			day++;
		}


		if(BeforeTomato != 0) {
			System.out.println(-1);
		}else {
			System.out.println(day);
		}


	}

	static boolean check(int r, int c) {
		return r >= 0 && r<N && c>=0 && c<M;
	}
}
