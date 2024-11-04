package 이동욱.SWEA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class SWEA_1226 {
	
	static int T = 10, M = 16;
	static int N, sr, sc;  // N, 시작 r, 시작 c
	static int[] dr = {1, -1, 0, 0}; 
	static int[] dc = {0, 0, 1, -1}; // 상 하 우 좌 순서
	static char[][] map;  // 맵 배열
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		a : for(int t=1; t<=T; t++) {
			N = Integer.parseInt(br.readLine());
			map = new char[16][16]; // 맵 배열 크기 16으로 고정
			for(int i=0; i<M; i++) {
				char[] cs = br.readLine().toCharArray(); // 맵 입력
				for(int j=0; j<M; j++) {
					map[i][j] = cs[j];
					if(map[i][j] == '2') { // 시작 지점 받기
						sr = i;
						sc = j;
					}
				}
			} // 맵 생성
			
			Queue<int []> que = new LinkedList<>(); 
			que.offer(new int[] {sr, sc}); // bfs 시작
			while(!que.isEmpty()) {
				int[] nums = que.poll(); // r, c 뽑기
				int r = nums[0];
				int c = nums[1];
				for(int d=0; d<4; d++) { // 4방향 돌리기
					int nr = r+dr[d];
					int nc = c+dc[d];
					if(map[nr][nc] == '3') { // '3' 즉 탈출 지점이라면.....
						System.out.println("#" + N + " " + 1); // 탈출가능
						continue a; // 위 출력 후 다음 테이스 케이스로 넘어감
					}
					if(map[nr][nc] != '0') continue; // 0이 아니라면 갈 수 없으므로 continue
					map[nr][nc] = '1'; // 이미 지나온 곳은 1로 만들기(굳이 되돌아 갈 필요 없기 때문)
					que.offer(new int[] {nr,nc}); // 위 모두 통과하면 r, c 넣어주기
				}
			}
			System.out.println("#" + N + " " + 0); // 통과 못하고 굶어 죽었으므로 여기서 출력
		}
	}
}
