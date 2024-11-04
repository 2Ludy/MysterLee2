// https://velog.io/@2ludy/boj17144
package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_17144 {
	
	static int R, C, T; 
	static int[][] map;
	static int[][] copy; // map을 copy하여 임시로 사용할 2차원 배열
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	static int sr; // 공기 청정기의 행 위치를 담을 변수(열은 고정되어있으므로)
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		map = new int[R][C];
		copy = new int[R][C];
		
		for(int i=0; i<R; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<C; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == -1) sr = i; // 만약 -1이 입력된다면 공기청정기가 있는곳 즉, 공기청정기는 (sr-1,0)/(sr,0) 두칸에 있는것임.
			}
		}
		
		int time = 0; // 시간이 얼마나 지났는지 체크할 변수 (0초부터 시작)
		while(time < T) { // 시간이 T만큼 지나는 순간 종료
			spreadDust(); // 먼지의 확산을 위한 메서드
			upAirCleaner(); // 공기 청정기의 윗영역 순환
			downAirCleaner(); // 공기 청정기의 아래영역 순환
			time++; // 위의 모든 과정이 끝나면, 1초가 흐른것으로 time+1
		}
		int sum = 0; // 남아있는 미세먼지의 양을 구하기 위한 변수
		for(int i=0; i<R; i++) {
			for(int j=0; j<C; j++) {
				sum+=map[i][j]; // 모든 행을 더해줘
			}
		}
		System.out.println(sum+2); // 근데 sum+2로 출력해야해 // 공기청정기 -1영역이 2개가 있으므로 !
	}

	private static void downAirCleaner() { // 공기청정기의 아래영역 순환 (시계방향)
		copy[sr][1] = 0; // 공기 청정기의 바로 오른쪽칸은 미세먼지가 존재할 수 없으므로 0
		for(int j=2; j<C; j++) {
			copy[sr][j] = map[sr][j-1]; // 한칸씩 오른쪽으로 이동
		}
		for(int i=sr+1; i<R; i++) {
			copy[i][C-1] = map[i-1][C-1]; // 한칸씩 아래쪽으로 이동
		}
		for(int j=C-2; j>=0; j--) {
			copy[R-1][j] = map[R-1][j+1]; // 한칸씩 왼쪽으로 이동
		}
		for(int i=R-2; i>sr; i--) {
			copy[i][0] = map[i+1][0]; // 한칸씩 위쪽으로 이동
		}
		resultMap(); // copy에 결과를 다시 map에 복사해주는 메서드
	}

	private static void upAirCleaner() { // 공기 청정기의 윗 영역 순환(반시계방향)
		copy[sr-1][1] = 0; // 공기 청정기의 바로 오른쪽칸은 미세먼지가 존재할 수 없으므로 0
		for(int j=2; j<C; j++) {
			copy[sr-1][j] = map[sr-1][j-1]; // 한칸씩 오른쪽으로 이동
		}
		for(int i=sr-2; i>=0; i--) {
			copy[i][C-1] = map[i+1][C-1]; // 한칸씩 위쪽으로 이동
		}
		for(int j=C-2; j>=0; j--) {
			copy[0][j] = map[0][j+1]; // 한칸씩 왼쪽으로 이동
		}
		for(int i=1; i<sr-1; i++) {
			copy[i][0] = map[i-1][0]; // 한칸씩 아래쪽으로 이동
		}
		resultMap(); // copy에 결과를 다시 map에 복사해주는 메서드
	}

	private static void spreadDust() { // 미세먼지의 확산을 위한 메서드
		cloneMap(); // 현재 map 상태를 copy에 복사해주는 메서드
		for(int i=0; i<R; i++) {
			for(int j=0; j<C; j++) {
				if(map[i][j] > 0) { // map 전체를 순환하며 해당 칸이 0보다 크다면..
					int count = 0; // 주위 확산되어야 할 칸이 몇개인지 세기위한 변수
					int dust = map[i][j]/5; // 확산될 미세먼지의 양
					for(int d=0; d<4; d++) { // 4방향 탐색
						int r = i+dr[d];
						int c = j+dc[d];
						if(!check(r,c)) continue; // 인덱스 체크
						if(map[r][c] == -1) continue; // 만약 공기청정기가 있다면 갈 수 없음
						copy[r][c] += dust; // copy배열의 해당칸에 확산된 미세먼지의 양을 더해줌
						count++; // 다른칸에 확산되었으므로 count + 1
					}
					copy[i][j] -= dust*count; // 미세먼지가 확산된양만큼 해당 칸에서 미세먼지의 양 줄여줘
				}
			}
		}
		resultMap(); // copy에 결과를 다시 map에 복사해주는 메서드
		
	}

	private static boolean check(int r, int c) { // 인덱스 체크 
		return r>=0 && r<R && c>=0 && c<C;
	}

	private static void resultMap() { // copy에 결과를 다시 map에 복사해주는 메서드
		for(int i=0; i<R; i++) {
			map[i] = copy[i].clone();
		}
		
	}
 
	private static void cloneMap() { // 현재 map 상태를 copy에 복사해주는 메서드ㄴ
		for(int i=0; i<R; i++) {
			copy[i] = map[i].clone();
		}
		
	}
}