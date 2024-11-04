// https://velog.io/@2ludy/boj16234

package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_16234 {
	
	static int N, L, R;
	static int[][] popu; // 각 나라에 인구수를 저장할 배열
	static boolean[][] visited; // 로직 돌때 해당 나라에 방문 했는지 체크하기 위한 배열
	static int time; // 인구이동이 며칠동안 발생하는지를 다루기 위한 변수
	static int[] dr = {1, 0, -1, 0}; // 하 우 상 좌
	static int[] dc = {0, 1, 0, -1};
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken()); // 입력
		L = Integer.parseInt(st.nextToken()); // 입력
		R = Integer.parseInt(st.nextToken()); // 입력
		
		popu = new int[N][N]; // 입력
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				popu[i][j] = Integer.parseInt(st.nextToken()); // 인구 수 입력 받기
			}
		} // 끝
		
		while(true) { // 일단 계속 돌고, 더 이상 인구 이동이 불가능하면 밑에서 탈출
			boolean moved = false; // 인구 이동이 일어났는지를 판별하는 변수
			visited = new boolean[N][N]; // 방문 했니? 배열 여기서 초기화(1일, 1일을 기준으로 관리해야 하므로) 하여 bfs 관리
			
			for(int i=0; i<N; i++) { 
				for(int j=0; j<N; j++) { // 배열을 돌면서
					if(visited[i][j]) continue; // 방문하지 않은곳을 찾아
					Queue<int[]> que = new LinkedList<>(); // bfs(연결되어있는지 판별하는 que)
					Queue<int[]> pos = new LinkedList<>(); // 이놈은 연결이 된 좌표들을 전부다 offer 함
					int sum = popu[i][j]; // 해당 영역의 인구수의 총합을 구하기 위한 변수
					que.offer(new int[] {i,j}); // 위에서 좌표 찾았으니 que에 넣어줘
					pos.offer(new int[] {i,j}); // 그리고 pos에도 넣어줘
					visited[i][j] = true; // 방문 했으니 방문 배열 true로 만들어줘
					while(!que.isEmpty()) { // bfs 돌려
						int[] nums = que.poll(); // que에서 뽑아
						int r = nums[0]; // r좌표
						int c = nums[1]; // c좌표
						for(int d=0; d<4; d++) { // 주변 탐색을 위해
							int nr = r+dr[d]; // 새로운 r좌표
							int nc = c+dc[d]; // 새로운 c좌표
							if(!check(nr,nc)) continue; // 인덱스 배열 체크하기
 							if(visited[nr][nc]) continue; // 방문했니? 방문했으면 볼 필요 없어
							if(!subpop(popu[r][c], popu[nr][nc])) continue; // 두 영역의 인구수 차가 L명 이상, R명 이하니?
							sum += popu[nr][nc]; // 위에 모두 충족하면 로직 시작, sum 인구수 총합 갱신 해주기
							visited[nr][nc] = true; // 방문했으니 방문처리
							que.offer(new int[] {nr,nc}); // que에 넣고~~
							pos.offer(new int[] {nr,nc}); // pos에도 넣기~~
						}
					}
					if(pos.size() > 1) { // 만약 pos의 size가 1이면 주변에 인구 이동이 가능한 나라가 없으므로 통과
						moved = true; // 인구 이동이 일어났다는 것으로 moved를 true로 만들어줌
						sum /= pos.size(); // 인구수를 나누기 위해 (연합의 인구수) / (연합을 이루고 있는 칸의 개수) 해주기
						while(!pos.isEmpty()) { // pos가 비어있지 않을떄까지 계속 빼자
							int[] nums = pos.poll(); // 빼서
							int r = nums[0];
							int c = nums[1];
							popu[r][c] = sum; // 해당 칸을 인구수를 갱신해주자
						}
					}
				}
			}
			if(!moved) {
				System.out.println(time); // 만약 인구 이동이 없으면 출력하고 끝
				return;
			}
			time++;  // 다 통과하면 하루 지나야 하므로 time+1 해주기
		}
		
	}

	private static boolean subpop(int p1, int p2) { // 두 영역의 인구수 차가 L명 이상, R명 이하니? 를 구하는 메서드
		int p = Math.abs(p1 - p2); // 차를 절대값으로 구해서
		return p >= L && p <= R; // 해당 조건에 만족하면 true를 리턴 하게 만들어줌
	}

	private static boolean check(int r, int c) { // 배열의 인덱스 안에 들어가는지 체크하기 위한 메서드
		return r>=0 && r<N && c>=0 && c<N; // 인덱스 체크하여 조건에 만족하면 true를 리턴
	}

}