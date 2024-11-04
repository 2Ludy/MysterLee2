import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
 * [마법사 상어와 파이어볼]
 * 4 ≤ N ≤ 50
 * 0 ≤ M ≤ N2
 * 1 ≤ K ≤ 1,000
 * 1 ≤ r, c ≤ N
 * 1 ≤ m ≤ 1,000
 * 1 ≤ s ≤ 1,000
 * 0 ≤ d ≤ 7
 * 
 * N x N 격자에 파이어볼 M개 발사
 * i번 파이어볼의 위치 (ri, ci), 질량 mi, 속력 si, 방향 di
 * 
 * 1. 방향 d로 속력 s만큼 이동
 * 2. 이동이 끝난 후 2개 이상의 파이어볼이 있는 칸에서는
 * -> 같은 칸에 있는 파이어볼이 하나로 합쳐진 후 4개의 파이어볼로 나누어짐
 * -> 질량 = 질량합/5, 속력 = 속력합/파이어볼개수
 * -> 파이어볼의 방향이 모두 홀수 or 모두 짝수 -> 방향 0, 2, 4, 6
 * -> 그렇지 않으면 방향 1, 3, 5, 7
 * K번 이동 후 남아있는 파이어볼 질량의 합
 */

public class BOJ20057 {
	
	// 격자 크기, 파이어볼의 개수, 명령 횟수
	static int N, M, K; 
	// 파이어볼 정보 {r, c, m, s, d}
	static List<int[]> info; 
	// 명령 후 남아있는 파이어볼 질량의 합
	static int sum; 
	
	// 8방향 델타 ('상'부터 시계방향)
	static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1}; 
	static int[] dc = {0, 1, 1, 1, 0, -1, -1, -1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		info = new ArrayList<>();

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			// 좌표는 0부터 N-1까지
			int r = Integer.parseInt(st.nextToken()) - 1; 
			int c = Integer.parseInt(st.nextToken()) - 1;
			int m = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			info.add(new int[] {r, c, m, s, d});
		}
		
		// K번 명령 수행
		for (int turn = 0; turn < K; turn++) { 
			turn();
		}
		
		sum = 0;
		for (int[] ball : info) {
			// 남아있는 파이어볼의 질량 총합
			sum += ball[2]; 
		}
		
		System.out.println(sum);
	}

	static void turn() {
		// 파이어볼의 위치와 정보 저장하기 위한 맵
		List<int[]>[][] map = new ArrayList[N][N];
		// N x N만큼의 리스트로 초기화
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				map[i][j] = new ArrayList<>();
			}
		}
		// 각 파이어볼을 해당 방향으로 속력만큼 이동, 맵에 저장
		for (int[] ball : info) {
			int r = ball[0];
			int c = ball[1];
			int m = ball[2];
			// 속도 N의 배수는 동일한 위치 => 나머지로 제한
			int s = ball[3] % N; 
			int d = ball[4];
			// 0번 행과 N-1번 행, 0번 열과 N-1번 열은 연결되어있으므로 
			// 배열의 범위를 벗어나는 경우 반대쪽으로 이동
			// 마이너스가 있을 수 있으므로 N을 더한 후 모듈러스 이용
			r = (r + s * dr[d] + N) % N;
			c = (c + s * dc[d] + N) % N;
			// map의 해당 위치에 {질량, 속도, 방향} 저장
			map[r][c].add(new int[] {m, s, d}); 
		}
		
		// 기존 파이어볼 정보 초기화
		info.clear(); 
		
		// 각 칸에서 파이어볼 처리
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				List<int[]> balls = map[i][j];
				// 해당 위치에 파이어볼이 없으면 continue
				if (balls.size() == 0) continue; 
				// 해당 위치에 파이어볼이 하나만 있으면
				if (balls.size() == 1) { 
					int[] tmp = balls.get(0);
					// 해당 위치와 원래의 정보로 info에 저장
					info.add(new int[] {i, j, tmp[0], tmp[1], tmp[2]});
				} else { // 해당 위치에 파이어볼이 여러개 있으면
					int totalM = 0; // 질량 총합 초기화
					int totalS = 0; // 속도 총합 초기화
					int evenCnt = 0; // 짝수, 홀수 방향 카운트
					int oddCnt = 0; 
					
					for (int[] ball : balls) {
						// 질량의 합
						totalM += ball[0];
						// 속도의 합
						totalS += ball[1];
						// 방향이 모두 짝수인지 홀수인지 판별
						if (ball[2] % 2 == 0) evenCnt++;
						else oddCnt++;
					}
					
					int newMass = totalM / 5; // 새로운 질량
					// 새로운 질량이 0인 경우를 제외해야함
					if (newMass > 0) { 
						// 새로운 속도
						int newSpeed = totalS / balls.size(); 
						// 모두 홀수이거나 모두 짝수이면 방향 0부터 시작
						int newDir = (oddCnt == 0 || evenCnt == 0) ? 0 : 1;
						// 한 곳에 모인 파이어볼은 4개로 나누어져 4개의 방향을 가짐
						for (int k = 0; k < 4; k++) {
							info.add(new int[] {i, j, newMass, newSpeed, newDir + k*2});
						}
					}
				}
				
			}
		}
	}

}
