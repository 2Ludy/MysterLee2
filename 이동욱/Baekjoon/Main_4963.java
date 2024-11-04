<<<<<<< HEAD
// https://velog.io/@2ludy/boj4963

=======
>>>>>>> 3d99af76bcff0505af872cb4b99eac5457394497
package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_4963 { 
	
	static int N, M;
	static int[][] map;
<<<<<<< HEAD
	static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1}; 
=======
	static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
>>>>>>> 3d99af76bcff0505af872cb4b99eac5457394497
	static int[] dc = {0, 1, 1, 1, 0, -1, -1, -1};
	static int count;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
<<<<<<< HEAD
		while(true) { // 계속 돌릴꺼야 N 과 M에 0이 입력될때 까지
=======
		while(true) {
>>>>>>> 3d99af76bcff0505af872cb4b99eac5457394497
			StringTokenizer st = new StringTokenizer(br.readLine());
			M = Integer.parseInt(st.nextToken());
			N = Integer.parseInt(st.nextToken());
			
<<<<<<< HEAD
			if(N == 0 && M == 0) return; // 두개 모두 0 이 입력되었다면 종료
			
			map = new int[N][M]; // 맵 초기화
=======
			if(N == 0 && M == 0) return;
			
			map = new int[N][M];
>>>>>>> 3d99af76bcff0505af872cb4b99eac5457394497
			
			for(int i=0; i<N; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<M; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			} // 맵 생성
			
<<<<<<< HEAD
			count = 1; // 섬의 개수 1로 초기화 (만약 섬이 있다고 판별되는순간 count++ 되어 2부터 시작. 즉, 섬의 번호는 2번부터 시작)
=======
			count = 1;
>>>>>>> 3d99af76bcff0505af872cb4b99eac5457394497
			
			Queue<int[]> que = new LinkedList<>();
			
			for(int i=0; i<N; i++) {
<<<<<<< HEAD
				for(int j=0; j<M; j++) { 
					if(map[i][j] == 1) { // 만약 1이라면 로직 시작
						count++; // 여기서 카운트 해주기
						map[i][j] = count; // 해당 좌표의 값 count로 바꿔주기
						que.offer(new int[] {i, j}); // que에 넣어서 bfs 시작
						while(!que.isEmpty()) { 
							int[] nums = que.poll();
							int r = nums[0];
							int c = nums[1];
							for(int d=0; d<8; d++) { // 8방향으로 도달가능
								int nr = r+dr[d];
								int nc = c+dc[d];
								if(!check(nr,nc)) continue; // 인덱스 체크
								if(map[nr][nc] != 1) continue; // 만약 1이 아니라면 갈 수 없으므로
								map[nr][nc] = count; //해당 좌표의 값 count로 바꿔주기
=======
				for(int j=0; j<M; j++) {
					if(map[i][j] == 1) {
						count++;
						map[i][j] = count;
						que.offer(new int[] {i, j});
						while(!que.isEmpty()) {
							int[] nums = que.poll();
							int r = nums[0];
							int c = nums[1];
							for(int d=0; d<8; d++) {
								int nr = r+dr[d];
								int nc = c+dc[d];
								if(!check(nr,nc)) continue;
								if(map[nr][nc] != 1) continue;
								map[nr][nc] = count;
>>>>>>> 3d99af76bcff0505af872cb4b99eac5457394497
								que.offer(new int[] {nr,nc});
							}
						}
					}
				}
			}
<<<<<<< HEAD
			System.out.println(count-1); // 섬의 번호가 2부터 시작했으므로 -1하여 섬의 개수 출력
		}
	}

	private static boolean check(int r, int c) { // 인덱스 체크
=======
			System.out.println(count-1);
		}
	}

	private static boolean check(int r, int c) {
>>>>>>> 3d99af76bcff0505af872cb4b99eac5457394497
		return r>=0 && r<N && c>=0 && c<M;
	}
}
