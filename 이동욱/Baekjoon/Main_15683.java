// https://velog.io/@2ludy/boj15683
package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_15683 {
	
	static int N, M, min;
	static int[][] map;
	static int count, size;
	static List<int[]> is5;
	static List<int[]> list;
	static List<int[]>[] recovery;
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	static int[] p;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		count = 0;
		is5 = new ArrayList<>();
		list = new ArrayList<>();
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 0) {
					count++;
				}else if(map[i][j] == 5) {
					is5.add(new int[] {i,j});
				}else if(map[i][j] != 6) {
					list.add(new int[] {i,j});
				}
			}
		}
		
		if(is5.size() > 0) {
			for(int i=0; i<is5.size(); i++) {
				int[] nums = is5.get(i);
				int numr = nums[0];
				int numc = nums[1];
				direction1(numr,numc);
				direction2(numr,numc);
				direction3(numr,numc);
				direction4(numr,numc);
			}
		}
		
		recovery = new ArrayList[list.size()];
		for(int i=0; i<list.size(); i++) {
			recovery[i] = new ArrayList<>();
		}
		min = count;
		logic(0, count);
		System.out.println(min);
	}

	private static void logic(int idx, int tmp) {
		if(idx == list.size()) {
			min = Math.min(tmp, min);
			return;
		}
		
		int[] nums = list.get(idx);
		int r = nums[0];
		int c = nums[1];
		int value = map[r][c];
		int newTmp = 0;
		
		if(value == 1) {
			newTmp = logic1(r,c,idx);
			logic(idx+1, tmp-newTmp);
			newTmp=0;
			recovery(idx);
			
			newTmp = logic2(r,c,idx);
			logic(idx+1, tmp-newTmp);
			newTmp=0;
			recovery(idx);
			
			newTmp = logic3(r,c,idx);
			logic(idx+1, tmp-newTmp);
			newTmp=0;
			recovery(idx);
			
			newTmp = logic4(r,c,idx);
			logic(idx+1, tmp-newTmp);
			newTmp=0;
			recovery(idx);
			
		}else if(value == 2) {
			
			newTmp = logic1(r,c,idx);
			newTmp += logic2(r,c,idx);
			logic(idx+1, tmp-newTmp);
			newTmp=0;
			recovery(idx);
			
			newTmp = logic3(r,c,idx);
			newTmp += logic4(r,c,idx);
			logic(idx+1, tmp-newTmp);
			newTmp=0;
			recovery(idx);
			
		}else if(value == 3) {
			newTmp = logic1(r,c,idx);
			newTmp += logic3(r,c,idx);
			logic(idx+1, tmp-newTmp);
			newTmp=0;
			recovery(idx);
			
			newTmp = logic1(r,c,idx);
			newTmp += logic4(r,c,idx);
			logic(idx+1, tmp-newTmp);
			newTmp=0;
			recovery(idx);
			
			newTmp = logic2(r,c,idx);
			newTmp += logic4(r,c,idx);
			logic(idx+1, tmp-newTmp);
			newTmp=0;
			recovery(idx);
			
			newTmp = logic2(r,c,idx);
			newTmp += logic3(r,c,idx);
			logic(idx+1, tmp-newTmp);
			newTmp=0;
			recovery(idx);
			
		}else if(value == 4) {
			newTmp = logic1(r,c,idx);
			newTmp += logic2(r,c,idx);
			newTmp += logic3(r,c,idx);
			logic(idx+1, tmp-newTmp);
			newTmp=0;
			recovery(idx);
			
			newTmp = logic2(r,c,idx);
			newTmp += logic3(r,c,idx);
			newTmp += logic4(r,c,idx);
			logic(idx+1, tmp-newTmp);
			
			recovery(idx);
			
			newTmp = logic3(r,c,idx);
			newTmp += logic4(r,c,idx);
			newTmp += logic1(r,c,idx);
			logic(idx+1, tmp-newTmp);
			newTmp=0;
			recovery(idx);
			
			newTmp = logic4(r,c,idx);
			newTmp += logic1(r,c,idx);
			newTmp += logic2(r,c,idx);
			logic(idx+1, tmp-newTmp);
			newTmp=0;
			recovery(idx);
		}
		
	}
	
	private static int logic1(int r, int c, int idx) { // 오른쪽 방향
		int num = 0;
		for(int j=c+1; j<M; j++) {
			if(map[r][j] == 0) {
				map[r][j] = 7;
				num++;
				recovery[idx].add(new int[] {r,j});
			}else if(map[r][j] == 6) {
				break;
			}
		}
		return num;
	}
	
	private static int logic2(int r, int c, int idx) { // 왼쪽 방향
		int num = 0;
		for(int j=c-1; j>=0; j--) {
			if(map[r][j] == 0) {
				map[r][j] = 7;
				num++;
				recovery[idx].add(new int[] {r,j});
			}else if(map[r][j] == 6) {
				break;
			}
		}
		return num;
	}
	
	private static int logic3(int r, int c, int idx) { // 위쪽 방향
		int num = 0;
		for(int i=r-1; i>=0; i--) {
			if(map[i][c] == 0) {
				map[i][c] = 7;
				num++;
				recovery[idx].add(new int[] {i, c});
			}else if(map[i][c] == 6) {
				break;
			}
		}
		return num;
	}
	
	private static int logic4(int r, int c, int idx) { // 아래쪽 방향
		int num = 0;
		for(int i=r+1; i<N; i++) {
			if(map[i][c] == 0) {
				map[i][c] = 7;
				num++;
				recovery[idx].add(new int[] {i, c});
			}else if(map[i][c] == 6) {
				break;
			}
		}
		return num;
	}
	
	private static void recovery(int idx) { 
		for(int[] nums : recovery[idx]) {
			int r = nums[0];
			int c = nums[1];
			map[r][c] = 0;
		}
		recovery[idx].clear();
	}
	


	private static void direction1(int r, int c) { // 오른쪽 방향
		for(int j=c+1; j<M; j++) {
			if(map[r][j] == 0) {
				map[r][j] = 7;
				count--;
			}else if(map[r][j] == 6) {
				return;
			}
		}
	}

	private static void direction2(int r, int c) { // 왼쪽 방향
		for(int j=c-1; j>=0; j--) {
			if(map[r][j] == 0) {
				map[r][j] = 7;
				count--;
			}else if(map[r][j] == 6) {
				return;
			}
		}
	}

	private static void direction3(int r, int c) { // 위쪽 방향
		for(int i=r-1; i>=0; i--) {
			if(map[i][c] == 0) {
				map[i][c] = 7;
				count--;
			}else if(map[i][c] == 6) {
				return;
			}
		}
	}

	private static void direction4(int r, int c) { // 아래쪽 방향
		for(int i=r+1; i<N; i++) {
			if(map[i][c] == 0) {
				map[i][c] = 7;
				count--;
			}else if(map[i][c] == 6) {
				return;
			}
		}
	}
}
