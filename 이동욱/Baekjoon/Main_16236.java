// https://velog.io/@2ludy/boj16236

package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_16236 {
	
	/*
	 * 흠.. .아이디어를 어떻게 짜야하지 흠...
	 * 일단 가져가야 할 정보는 r 좌표, c 좌표, distance, 아기 상어 크기 size 
	 * Priorityque로 만들어서 distance관리해서 distance 짧은순으로 나오도록 하고
	 * 만약 먹을 수 있는 물고기가 나오는 순간 거기로 이동하는게 맞을듯?
	 * 그리고 bfs 파괴하는거지
	 * 만약 bfs가 굶어 죽는다면? 엄마 도움 받아야하니까 끝//
	 * 모든 아기상어의 수 count 해서 이걸로 while문으로 끝까지 돌리면 될듯??
	 * 최종 distance가 결국 time
	 */
	
	static int N;
	static int[][] map; 
	static int[] dr = {-1, 0, 0, 1};  //0 = 위, 1 = 왼쪽, 2 = 오른쪽, 3 = 아래
	static int[] dc = {0, -1, 1, 0}; 
	static boolean[][][] visited; // r 좌표, c 좌표, 남겨진 상어 개수로 방문 체크 생성
	static int count; // 0과 9가 아닌 숫자(다른상어)들이 몇개인지 판별하는 변수 
	static int dis; // 거리를 갱신하기 위한 변수 = 어처피 걸린 시간과 동일
	static int sr, sc; // 시작지점
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N][N]; // 맵 초기화
		count = 0; // 카운팅 변수 초기화
		for(int i=0; i<N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 9) { // 시작지점
					sr = i;
					sc = j;
					map[sr][sc] = 0; // 시작지점 또한 지나갈 수 있는 영역이므로 계산하기 편하게 0으로 변경
				}
				if(map[i][j] >0 && map[i][j]<9) count++;  // 다른 상어들의 개수 갱신
			}
		}
		dis = 0; // 총 거리를 갱신하기 위한 변수 0으로 초기화
		Queue<int[]> que = new LinkedList<>();
		
		visited = new boolean[N][N][count+1]; // 다른상어의 총 개수 ~ 0 까지 가야하므로 index를 count+1로 초기화 
		que.offer(new int[] {sr, sc, 0, 2, 0}); // que에 담기 r좌표, c좌표, 거리, 아기상어 사이즈, 먹이 몇개먹었니?
		visited[sr][sc][count] = true; // 담았으므로 true 만들어주기
		
		while(!que.isEmpty()) {
			PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() { // 여기서 pq 초기화 해당 pq 는 que가 다 돌고 어떤 위치로 가는것이 맞는지 판별하기 위한 que

				@Override
				public int compare(int[] o1, int[] o2) {
					if(o1[2] == o2[2]) { // 거리가 같다면
						if(o1[0] == o2[0]) return o1[1] - o2[1]; // 둘다 가장 위에 있다면 더 왼쪽에 있는 상어로 가자
						return o1[0]-o2[0]; // 가장 위에 있는 놈으로 가자
					}
					return o1[2]-o2[2]; // 거리가 가까운 놈으로 가자
				}
				
			});
			
			int queSize = que.size(); // 현재 que에 담겨져있는 개수
			if(count == 0) { // 만약 더이상 먹을 상어가 없다면 탈출
				System.out.println(dis);
				return;
			}
			
			for(int i=0; i<queSize; i++) { // que의 사이즈만큼 돌려주기. (어디로 갈지 위치를 정하기 위해)
				
				int[] nums = que.poll();
				int r = nums[0]; // r 좌표
				int c = nums[1]; // c 좌표
				int distance = nums[2]; // 거리
				int size = nums[3];  // 아기상어의 현재 크기
				int eat = nums[4]; // 얼마나 상어를 먹었는지
				
				for(int d=0; d<4; d++) {
					int nr = r+dr[d];
					int nc = c+dc[d];
					if(!check(nr,nc)) continue; // 인덱스 넘어가면 continue
					if(map[nr][nc] > size) continue;  //  아기상어보다 다른 상어가 크면 못가
					if(visited[nr][nc][count]) continue; // 방문 했으면 갈 필요 없어
					
					if(map[nr][nc] == size || map[nr][nc] == 0) { // 사이즈와 같거나 0 이면 통과만 가능
						que.offer(new int[] {nr, nc, distance+1, size, eat}); // que에 거리 +1만 하여 다시 담기
						visited[nr][nc][count] = true; // 방문했당
						
					}else{ // 위에가 아니라면 상어를 먹었다는것임.
						pq.offer(new int[] {nr, nc, distance, size, eat}); // 아니라면 pq에 넣어서 조금 있다가 판별 하자.
					}
				}
			}
			if(pq.size() > 0) {  //  pq안에 담긴게 있으면 돌리면돼
				int[] pqnums = pq.poll();
				int pqr = pqnums[0]; // pq에 담긴 r좌표
				int pqc = pqnums[1]; // pq에 담긴 c좌표
				int pqdistance = pqnums[2]; // pq에 담긴 거리
				int pqsize = pqnums[3]; // pq에 담긴 아기상어의 현재 사이즈
				int pqeat = pqnums[4]; // pq에 담긴 얼마나 상어를 먹었니
				
				int newEat = pqeat+1; // pq에 담긴것은 상어를 먹었을때
				
				if(newEat == pqsize) { // 만약 해당 상어를 먹었을때 아기상어의 size와 먹은 개수가 같다면 size+1로 업그레이드 되어야 함
					count--; // 상어를 한마리 먹었으니 마이너스
					dis = pqdistance+1; // 거리 갱신해주기
					
					map[pqr][pqc] = 0; // 상어를 먹어서 없어졌으니 0
					que.clear(); // pq에서 다시 시작해야하므로 que에 뭔가가 담겨있다면 초기화
					que.offer(new int[] {pqr, pqc, pqdistance+1, pqsize+1, 0}); // que에 현재 위치 넣어주기
					visited[pqr][pqc][count] = true; // 방문 했어 
				}else { // 아직 먹은 개수가 사이즈에 도달 못했다면 먹은 개수 갱신해주면 돼
					count--;  // 상어를 한마리 먹었으니 마이너스
					dis = pqdistance+1; // 거리 갱신해주기
					
					map[pqr][pqc] = 0; // 상어를 먹어서 없어졌으니 0
					que.clear(); // pq에서 다시 시작해야하므로 que에 뭔가가 담겨있다면 초기화
					que.offer(new int[] {pqr, pqc, pqdistance+1, pqsize, newEat}); // que에 현재 위치 넣어주기
					visited[pqr][pqc][count] = true; // 방문했어
				}
			}
		}
		System.out.println(dis); // 위의 로직에서 내려왔다면, 더 이상 갈 곳이 없는 경우이므로 출력
	}

	private static boolean check(int r, int c) { // 인덱스 체큰
		return r>=0 && r<N && c>=0 && c<N;
	}
}
