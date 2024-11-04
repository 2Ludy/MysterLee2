// https://velog.io/@2ludy/boj14502

package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_14502 {
	
	static int N, M;  // 가로 세로 받기
	static int[][] map;  // 연구소 지도
	static int[] dr = {-1, 1, 0, 0}; // 상 하 좌 우
	static int[] dc = {0, 0, -1, 1};
	static boolean[][] visited; // 방문배열 - bfs 돌릴때 map을 변경하면 나중에 관리하기 힘드므로 방문배열로 관리
	static int max, count; // 안정영역의 최대 크기 max, 안전영역의 총 개수 count
	static List<int[]> safeList;  // 안전 영역의 모든 좌표를 담을 리스트
	static List<int[]> virusList; // 바이러스가 위치한 모든 좌표를 담을 리스트 
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][M]; 
		safeList = new ArrayList<>();
		virusList = new ArrayList<>();
		count = 0; // 안전영역의 총 개수 0으로 초기화
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 0) { // 안전 영역 이라면
					safeList.add(new int[] {i,j}); // safeList에 좌표 담고
					count++; // 안전영역의 개수 카운트 해주기
				}else if(map[i][j] == 2) { // 바이러스 라면
					virusList.add(new int[] {i,j});  // virusList에 좌표 담기 
				}
			}
		}
		max = 0;  // 안전 영역은 무조건 0보다 크거나 같으므로 0으로 초기화
		
		nCr(0,0); // 로직 시작
		
		System.out.println(max); // 출력
	}

	private static void nCr(int idx, int cnt) {  // 안전영역 중 3개를 뽑아 벽(1) 로 만들기 위한 로직
		if(cnt == 3) { // 만약 벽을 3개 만들었다면 
			bfs(); // bfs 돌려서 max 갱신 후
			return; // 탈출
		}
		
		for(int i=idx; i<safeList.size(); i++) {  // 조합이므로 i는 idx부터... safList의 사이즈까지
			int[] nums = safeList.get(i);  // list 가져오기
			int r = nums[0]; // 행
			int c = nums[1]; // 열
			map[r][c] = 1; // 해당 좌표에 벽 세우기
			nCr(i+1, cnt+1); // 다음 조합으로 넘어가기
			map[r][c] = 0; // 조합이 끝나고 return 되어 돌아왔다면... 1로 만들었던 좌표 0으로 다시 되돌리기
		}
	}

	private static void bfs() {
		Queue<int[]> que = new LinkedList<>(); // que 초기화
		visited = new boolean[N][M]; // 방문배열은 bfs 돌릴때마다 초기화하는것이 좋으므로 여기서 초기화
		for(int i=0; i<virusList.size(); i++) {
			que.offer(virusList.get(i)); // 큐에 virus 좌표 모두 넣기
		}
		int infect = 0; // 바이러스가 전염될 영역을 담을 변수
		while(!que.isEmpty()) { // que의 내용물이 없어질때까지 bfs 돌리기..
			int[] nums = que.poll();
			int r = nums[0];
			int c = nums[1];
			for(int d=0; d<4; d++) { // 4방향 탐색
				int nr = r+dr[d];
				int nc = c+dc[d];
				if(!check(nr,nc)) continue; // 인덱스 넘기면 continue
				if(map[nr][nc] != 0) continue; // map이 0이 아니라면 갈 필요 없으므로 continue
				if(visited[nr][nc]) continue; // 방문 배열이 true 라면 continue
				visited[nr][nc] = true; // 모두 통과하였으므로 virus가 도달할 수 있는 영역, true로 갱신
				infect++; // 감연된 영역이 늘었으므로, 카운팅
				que.offer(new int[] {nr, nc});
			}
		}
		int safe = count-infect-3; // 안전 영역의 크기는 (원래 안전 영역의 크기) - (바이러스 침투 당한 영역) - (벽 3개 ) 이므로...
		max = Math.max(safe, max); // 갱신
	}

	private static boolean check(int r, int c) { // 인덱스 체크
		return r>=0 && r<N && c>=0 && c<M;
	}
}
