package 이동욱.SWEA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class SWEA_1953 {
	
	static int T, N, M, sr, sc, L, count;
	static int[][] map; // 맵 생성
	static boolean[][] visited; // 방문 배열
	static int[] dr = {-1, 0, 1, 0}; // 위 오른쪽 아래 왼쪽
	static int[] dc = {0, 1, 0, -1};
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine()); // 테스트케이스 받기
		for(int t=1; t<=T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken()); // 세로 크기 N
			M = Integer.parseInt(st.nextToken()); // 가로 크기 M
			sr = Integer.parseInt(st.nextToken()); // 시작 r 위치 sr
			sc = Integer.parseInt(st.nextToken()); // 시작 c 위치 sc
			L = Integer.parseInt(st.nextToken()); // 탈출 후 소요 시간 L
			
			map = new int[N][M]; // 맵 초기화
			visited = new boolean[N][M]; // 방문 배열 초기화
			for(int i=0; i<N; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<M; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			} // 맵 입력 완료
			
			if(L == 1) {
				System.out.println("#" + t + " " + 1);
				continue;
			} // 만약 L이 1이라면 무조건 처음 한곳에만 위치 가능하므로 1 출력 후 종료
			
			Queue<int[]> que = new LinkedList<>(); // 인덱스 순서대로 r, c, time
			que.offer(new int[] {sr, sc, 1}); // 처음 위치 넣기
			visited[sr][sc] = true; // 처음 위치 방문배열 true 설정
			count = 1; // 첫 위치 넣었으니 갈 수 있는 count 1로 초기화
			while(!que.isEmpty()) {  // bfs 시작
				int[] nums = que.poll(); // int 배열 poll 하기
				int r = nums[0]; // 현재 위치 r
				int c = nums[1]; // 현재 위치 c
				int time = nums[2]; // 현재 소욛된 시간
				int type = map[r][c]; // 현재 위치 지하 터널 타입
				
				for(int d=0; d<4; d++) { // 다음 위치 
					int nr = r + dr[d]; // 다음 위치 r
					int nc = c + dc[d]; // 다음 위치 c
					if(!check(nr,nc)) continue; // 배열 인덱스 넘어가면 continue
					if(visited[nr][nc]) continue; // 방문 했으면 continue
					if(map[nr][nc] == 0) continue; // 다음 map이 0이면 무조건 못가므로 continue
					if(!checkType(type,nr,nc,d)) continue; // 현재 map과 이동할 map 이 연결되어있는지 check
					visited[nr][nc] = true; // 이제 방문 했으니 true 만들기
					count++; // 방문한 곳은 탈주범이 있을 수 있는 곳으로 count+1 해주기
					if(time+1 == L) { // 만약 time+1이 L과 같다면 그 다음은 움직일 수 없으므로 que에 추가하지 않고 continue
						continue;
					}else { // time+1이 L과 같지 않다면 que에 추가 
						que.offer(new int[] {nr, nc, time+1});
					}
				}
			}
			System.out.println("#" + t + " " + count); // 출력
		}
	}

	private static boolean checkType(int type, int r, int c, int direction) { // 현재 map과 이동할 map 이 연결되어있는지 check 메서드
		int nextType = map[r][c]; // 이동할 위치의 지하 터널 타입
		if(direction == 0) { // 위 방향
			if(type == 1 || type == 2 || type == 4 || type == 7) { // 현재 위치의 위쪽 방향으로 이동 가능하여야 함
				if(nextType == 1 || nextType == 2 || nextType == 5 || nextType == 6) return true; // 다음 위치의 아래쪽이 뚫려있어야 함 
			}
		}else if(direction == 1) { // 오른쪽 방향
			if(type == 1 || type == 3 || type == 4 || type == 5) { // 현재 위치의 오른쪽 방향으로 이동 가능하여야 함
				if(nextType == 1 || nextType == 3 || nextType == 6 || nextType == 7) return true; // 다음 위치의 왼쪽이 뚫려있어야 함
			}
		}else if(direction == 2) { // 아래 방향
			if(type == 1 || type == 2 || type == 5 || type == 6) { // 현재 위치의 아래 방향으로 이동 가능하여야 함
				if(nextType == 1 || nextType == 2 || nextType == 4 || nextType == 7) return true; // 다음 위치의 위쪽이 뚫려있어야 함
			}
		}else if(direction == 3) { // 왼쪽 방향
			if(type == 1 || type == 3 || type == 6 || type == 7) { // 현재 위치의 왼쪽 방향으로 이동 가능하여야 함
				if(nextType == 1 || nextType == 3 || nextType == 4 || nextType == 5) return true; // 다음 위치의 오른쪽이 뚫려있어야 함
			}
		}

		return false; // 빠져 나가면 false
	}

	static boolean check(int r, int c) { // 배열 인덱스 체크 로직
		return r>=0 && r<N && c>=0 && c<M;
	}
}
